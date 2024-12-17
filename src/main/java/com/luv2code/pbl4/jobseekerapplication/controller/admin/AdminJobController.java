package com.luv2code.pbl4.jobseekerapplication.controller.admin;


import com.luv2code.pbl4.jobseekerapplication.crawler.JobCrawler;
import com.luv2code.pbl4.jobseekerapplication.dto.ListResult;
import com.luv2code.pbl4.jobseekerapplication.entity.Company;
import com.luv2code.pbl4.jobseekerapplication.entity.Job;
import com.luv2code.pbl4.jobseekerapplication.service.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminJobController {
    private final CompanyService companyService;
    private JobService jobService;
    private LocationService locationService;
    private IndustryService industryService;
    private JobCrawler jobCrawler;

    @Autowired
    public AdminJobController(JobService jobService, LocationService locationService, IndustryService industryService, JobCrawler jobCrawler, CompanyService companyService) {
        this.jobService = jobService;
        this.locationService = locationService;
        this.industryService = industryService;
        this.jobCrawler = jobCrawler;
        this.companyService = companyService;
    }


    @GetMapping("/viec-lam")
    public String showJobs(@RequestParam(defaultValue = "1", required = false) int pageNo,
                           @RequestParam(defaultValue = "10", required = false) int pageSize,
                           @RequestParam(required = false) String search,
                           @RequestParam(required = false) String sortBy,
                           @RequestParam(defaultValue = "-1", required = false) int industryId,
                           @RequestParam(defaultValue = "-1", required = false) int locationId,
                           @RequestParam(required = false) List<String> jobTypes,
                           @RequestParam(required = false) String experienceLevel,
                           @RequestParam(required = false) List<String> careerLevels,
                           @RequestParam(defaultValue = "0.0", required = false) Double salary,
                           Model model) {
        getJobs(pageNo, pageSize, search, sortBy, industryId, locationId, jobTypes, experienceLevel, careerLevels, salary, model);
        return "admin/JobList";

    }

    @GetMapping("/viec-lam/them")
    public String addJob(@RequestParam(defaultValue = "1", required = false) String jobTitle,
                         @RequestParam(defaultValue = "1", required = false) String jobDescription,
                         @RequestParam(required = false) int companyId,
                         @RequestParam(defaultValue = "1", required = false) String jobType,
                         @RequestParam(defaultValue = "0", required = false) int experienceLevel,
                         @RequestParam(defaultValue = "1", required = false) String careerLevel,
                         @RequestParam(required = false) LocalDate postedDate,
                         @RequestParam(required = false) LocalDate expirationDate,
                         @RequestParam(defaultValue = "1", required = false) String jobUrl,
                         @RequestParam(defaultValue = "0", required = false) Double salary,
                         @RequestParam(defaultValue = "VND", required = false) String salaryCurrency,
                         Model model) {

        Job job = new Job();
        job.setJobTitle(jobTitle);
        job.setJobDescription(convertToHtml(jobDescription));
        job.setCompany(companyService.findById(companyId));
        job.setJobType(jobType);
        job.setExperienceLevel(experienceLevel);
        job.setCareerLevel(careerLevel);
        job.setPostedDate(postedDate);
        job.setExpirationDate(expirationDate);
        job.setJobUrl(jobUrl);
        job.setSalary(salary);
        job.setSalaryCurrency(salaryCurrency);
        jobService.save(job);


        List<Company> companies = companyService.findAll();
        model.addAttribute("companies", companies);
        model.addAttribute("companyName", "alo");
        model.addAttribute("companyId", 10);
        getJobs(1, 10, "", "", -1, -1, null, "", null, 0.0, model);
        return "admin/JobList";
    }

    public static String convertToHtml(String inputText) {

        String[] lines = inputText.split("\n");
        StringBuilder htmlOutput = new StringBuilder();

        for (String line : lines) {
            line = line.trim();
            if (line.isEmpty()) {
                continue;
            }

            if (line.equals(line.toUpperCase())) {
                htmlOutput.append("<h3>").append(line).append("</h3>\n");
            } else {
                htmlOutput.append("<p>").append(line).append("</p>\n");
            }
        }

        return htmlOutput.toString();
    }


    @GetMapping("/load-viec-lam")
    @ResponseBody
    public String getJobs(@RequestParam(defaultValue = "0", required = false) int pageNo,
                          @RequestParam(defaultValue = "10", required = false) int pageSize,
                          @RequestParam(required = false) String search,
                          @RequestParam(required = false) String sortBy,
                          @RequestParam(defaultValue = "-1", required = false) int industryId,
                          @RequestParam(defaultValue = "-1", required = false) int locationId,
                          @RequestParam(required = false) List<String> jobTypes,
                          @RequestParam(required = false) String experienceLevel,
                          @RequestParam(required = false) List<String> careerLevels,
                          @RequestParam(defaultValue = "0.0", required = false) Double salary,
                          Model model) {
        ListResult<Job> listResult = jobService.getAllJobsWithSortByColumnAndSearch(pageNo, pageSize, search, sortBy, industryId, locationId, jobTypes, experienceLevel, careerLevels, salary);
        List<Job> jobs = listResult.getItems();
        Long totalItems = listResult.getTotalItems();
        int pageNum = listResult.getTotalPages(pageSize);
        List<Company> companies = companyService.findAll();
        model.addAttribute("companies", companies);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("pageNum", pageNum);
        model.addAttribute("jobs", jobs);
        model.addAttribute("industryId", industryId);
        model.addAttribute("locationId", locationId);
        model.addAttribute("mode", "MODE_JOBS");
        String result = "";
        result += "<div class=\"row\">\n" +
                "                <div class=\"add-new-record-button-wrapper col l-12\">\n" +
                "                    <div class=\"add-new-record-button\">\n" +
                "                        <i class=\"add-new-record-button-icon fa-solid fa-plus\"></i>\n" +
                "                    </div>\n" +
                "                </div>\n" +
                "    \n" +
                "            </div>" +
                "<div class=\"row\">\n" +
                "\n" +
                "                <div class=\"main-content-table-wrapper\">\n" +
                "                    <table class=\"main-content-table\">\n" +
                "                        <tr>\n" +
                "                            <th>Mã </th>\n" +
                "                            <th>Tên công việc</th>\n" +
                "                            <th>Công ty</th>\n" +
                "                            <th>Thao tác</th>\n" +
                "                        </tr>\n";


        for (Job job : jobs) {
            result += "<tr >\n" +
                    "                            <td>\n" +
                    "                                <p class=\"main-content-table-text\" > " + job.getJobId() + "</p>\n" +
                    "                            </td>\n" +
                    "                            <td >\n" +
                    "                                <p class=\"main-content-table-text\" > " + job.getJobTitle() + "</p>\n" +
                    "                            </td>\n" +
                    "                            <td >\n" +
                    "                                <p class=\"main-content-table-text\"> " + job.getCompany().getCompanyName() + " </p>\n" +
                    "                            </td>\n" +
                    "                            <td class=\"table-action-button-wrapper\">\n" +
                    "                                <div class=\"table-action-button table-action-button--show\"\n" +
                    "                                     data-job-id=\""+ job.getJobId()+ "\">\n" +
                    "                                    <i class=\"fa-solid fa-circle-info\"></i>\n" +
                    "                                </div>" +
                    "                                <div class=\"table-action-button table-action-button--change\"><i class=\"fa-solid fa-pen\"></i></div>\n" +
                    "                                <div class=\"table-action-button table-action-button--delete\"><i class=\"fa-solid fa-trash\"></i></div>\n" +
                    "                            </td>\n" +
                    "                        </tr>\n";
        }
        result += "       </table>\n" +
                "<div class=\"app-main-navigate\">\n" +
                "                                <ul  class=\"app-main-navigate-list\">\n" +


                ((pageNo > 1) ?
                        ("                                    <li class=\"app-main-navigate-item\">\n" +
                                "\n" +
                                "                                        <button  class=\"app-main-navigate-item-btn\" value=\"/admin/load-viec-lam?pageNo=" + (pageNo - 1) + ("&industryId=" + industryId + "&") + ("locationId=" + locationId) + "\">\n" +
                                "                                            <i class=\"fa-solid fa-chevron-left\"></i>\n" +
                                "                                        </button>\n" +
                                "                                    </li>\n")

                        :

                        "");

        for (int i = 1; i <= pageNum; i++) {
            result +=
                    "                                    <li" +
                            "                                        class=\"app-main-navigate-item " + ((pageNo == i) ? "active\" " : "\" ") + ">\n" +
                            "                                        <button class=\"app-main-navigate-item-btn\" value=\"/admin/load-viec-lam?pageNo=" + i + ("&industryId=" + industryId + "&") + ("locationId=" + locationId) + "\">" + i + "</button>\n" +
                            "                            </li>\n";

        }


        result +=
                ((pageNo < pageNum) ?
                        "<li class=\"app-main-navigate-item\">\n" +
                                "                                        <button class=\"app-main-navigate-item-btn\" value=\"/admin/load-viec-lam?pageNo=" + (pageNo + 1) + ("&industryId=" + industryId + "&") + ("locationId=" + locationId) + "\">\n" +
                                "                                            <i class=\"fa-solid fa-chevron-right\"></i>\n" +
                                "                                        </button>\n" +
                                "</li>\n"

                        :
                        "")
                        +
                        "                                </ul>\n" +
                        "                            </div>";


        result +=
                "                </div>\n" +
                        "\n" +
                        "                <div class=\"col hide\">\n" +
                        "                    <form action=\"\" class=\"main-form\">\n" +
                        "                        <div class=\"form-group\">\n" +
                        "                            <label for=\"jobTitle\">Tên công việc</label>\n" +
                        "                            <input type=\"text\" id=\"jobTitle\" name=\"jobTitle\" class=\"form-control\">\n" +
                        "                        </div>\n" +
                        "                        <div class=\"form-group\">\n" +
                        "                            <label for=\"jobDescription\">Mô tả</label>\n" +
                        "                            <textarea id=\"jobDescription\" name=\"jobDescription\" class=\"form-control\"></textarea>\n" +
                        "                        </div>\n" +
                        "                        <div class=\"form-group\">\n" +
                        "                            <label for=\"company\">Công ty</label>\n" +
                        "                            <input type=\"text\" id=\"company\" name=\"company\" class=\"form-control\">\n" +
                        "                        </div>\n" +
                        "                        <div class=\"form-group\">\n" +
                        "                            <label for=\"jobType\">Loại công việc</label>\n" +
                        "                            <input type=\"text\" id=\"jobType\" name=\"jobType\" class=\"form-control\">\n" +
                        "                        </div>\n" +
                        "                        <div class=\"form-group\">\n" +
                        "                            <label for=\"experienceLevel\">Năm kinh nghiệm</label>\n" +
                        "                            <input type=\"number\" id=\"experienceLevel\" name=\"experienceLevel\" class=\"form-control\">\n" +
                        "                        </div>\n" +
                        "                        <div class=\"form-group\">\n" +
                        "                            <label for=\"careerLevel\">Vị trí công việc</label>\n" +
                        "                            <select id=\"careerLevel\" name=\"careerLevel\" class=\"form-control\">\n" +
                        "                                <option value=\"Intern\">Intern</option>\n" +
                        "                                <option value=\"Junior\">Junior</option>\n" +
                        "                                <option value=\"Mid\">Mid</option>\n" +
                        "                                <option value=\"Senior\">Senior</option>\n" +
                        "                                <option value=\"Lead\">Lead</option>\n" +
                        "                                <option value=\"Manager\">Manager</option>\n" +
                        "                            </select>\n" +
                        "                        </div>\n" +
                        "                        <div class=\"form-group\">\n" +
                        "                            <label for=\"postedDate\">Ngày đăng</label>\n" +
                        "                            <input type=\"date\" id=\"postedDate\" name=\"postedDate\" class=\"form-control\">\n" +
                        "                        </div>\n" +
                        "                        <div class=\"form-group\">\n" +
                        "                            <label for=\"expirationDate\">Ngày hết hạn</label>\n" +
                        "                            <input type=\"date\" id=\"expirationDate\" name=\"expirationDate\" class=\"form-control\">\n" +
                        "                        </div>\n" +
                        "                        <div class=\"form-group\">\n" +
                        "                            <label for=\"jobUrl\">Nguồn</label>\n" +
                        "                            <input type=\"url\" id=\"jobUrl\" name=\"jobUrl\" class=\"form-control\">\n" +
                        "                        </div>\n" +
                        "                        <div class=\"form-group\">\n" +
                        "                            <label for=\"salary\">Mức Lương</label>\n" +
                        "                            <input type=\"number\" id=\"salary\" name=\"salary\" class=\"form-control\">\n" +
                        "                        </div>\n" +
                        "                        <div class=\"form-group\">\n" +
                        "                            <label for=\"salaryCurrency\">Đơn vị tiền tệ</label>\n" +
                        "                            <input type=\"text\" id=\"salaryCurrency\" name=\"salaryCurrency\" class=\"form-control\">\n" +
                        "                        </div>\n" +
                        "                        <button type=\"submit\" class=\"btn btn-primary\">Thêm</button>\n" +
                        "                    </form>" +
                        "                </div>\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "            </div>";


        return result;
    }

    @GetMapping("/xem-thong-tin")
    @ResponseBody
    public String getJob(@RequestParam( required = true) int jobId) {
        Job job = jobService.findById(jobId);
        String result = "";
        Document document = Jsoup.parse(job.getJobDescription());

        // Extract and format the content
        StringBuilder formattedText = new StringBuilder();
        for (Element section : document.select(".job-description__item, .job-description__item--content")) {
            // Process <h3> (Section Title)
            Element title = section.selectFirst("h3");
            if (title != null) {
                formattedText.append(title.text().toUpperCase()).append("\n");
            }

            // Process the content
            for (Element content : section.select("p, div")) {
                String text = content.text().trim();
                if (!text.isEmpty()) {
                    formattedText.append(text).append("\n");
                }
            }
            formattedText.append("\n");
        }

        result += "                     <div class=\"form-group\">\n" +
                "                            <label for=\"jobTitle\">Tên công việc</label>\n" +
                "                            <input type=\"text\" id=\"jobTitle\" name=\"jobTitle\" class=\"form-control\" value=\"" + job.getJobTitle()+ "\">\n" +
                "                        </div>\n" +
                "                        <div class=\"form-group\">\n" +
                "                            <label for=\"jobDescription\">Mô tả</label>\n" +
                "                            <textarea id=\"jobDescription\" name=\"jobDescription\" class=\"form-control\" placeholder=\"Hãy viết tiêu đề IN HOA\" value=\"oizzoi\">"+formattedText.toString().trim()+"</textarea>\n" +
                "                        </div>\n" +
                "\n" +
                "\n" +
                "                        <label for=\"search-bar--main-section-category-trigger\" class=\"search-bar--main-section\">\n" +
                "                            <input hidden type=\"checkbox\" id=\"search-bar--main-section-category-trigger\" name=\"search-bar--main-section-category-trigger\">\n" +
                "                            <i class=\"search-bar--main-section-icon fa-solid fa-briefcase\"></i>\n" +
                "                            <div class=\"search-bar--main-section-text\">\n" +
                "\n" +                          job.getCompany().getCompanyName()+
                "                            </div>\n" +
                "                            <i class=\"search-bar--main-section-icon--down fa-solid fa-chevron-down\"></i>\n" +
                "                            <i class=\"search-bar--main-section-icon--up fa-solid fa-chevron-up\"></i>\n" +
                "\n" +
                "\n" +
                "                        </label>\n" +
                "\n" +
                "                        <div class=\"form-group\">\n" +
                "\n" +
                "                            <label for=\"jobType\">Loại công việc</label>\n" +
                "                            <input type=\"text\" id=\"jobType\" name=\"jobType\" class=\"form-control\" value=\""+job.getJobType()+"\">\n" +

                "                        </div>\n" +
                "                        <div class=\"form-group\">\n" +
                "                            <label for=\"experienceLevel\">Năm kinh nghiệm</label>\n" +
                "                            <input type=\"number\" id=\"experienceLevel\" name=\"experienceLevel\" class=\"form-control\" value=\""+job.getExperienceLevel()+"\">\n" +
                "                        </div>\n" +
                "                        <div class=\"form-group\">\n" +
                "                            <label for=\"careerLevel\">Vị trí công việc</label>\n" +

                "                            <input type=\"text\" id=\"careerLevel\" name=\"careerLevel\" class=\"form-control\" value=\""+job.getCareerLevel()+"\">\n" +
                "                        </div>\n" +
                "                        <div class=\"form-group\">\n" +
                "                            <label for=\"postedDate\">Ngày đăng</label>\n" +
                "                            <input type=\"date\" id=\"postedDate\" name=\"postedDate\" class=\"form-control\" value=\""+job.getPostedDate()+"\">\n" +
                "                        </div>\n" +
                "                        <div class=\"form-group\">\n" +
                "                            <label for=\"expirationDate\">Ngày hết hạn</label>\n" +
                "                            <input type=\"date\" id=\"expirationDate\" name=\"expirationDate\" class=\"form-control\" value=\""+job.getExpirationDate()+"\">\n" +
                "                        </div>\n" +
                "                        <div class=\"form-group\">\n" +
                "                            <label for=\"jobUrl\">Nguồn</label>\n" +
                "                            <input type=\"url\" id=\"jobUrl\" name=\"jobUrl\" class=\"form-control\" value=\""+job.getJobUrl()+"\">\n" +
                "                        </div>\n" +
                "                        <div class=\"form-group\">\n" +
                "                            <label for=\"salary\">Mức Lương</label>\n" +
                "                            <input type=\"number\" id=\"salary\" name=\"salary\" class=\"form-control\" value=\""+job.getSalary()+"\">\n" +
                "                        </div>\n" +
                "                        <div class=\"form-group\">\n" +
                "                            <label for=\"salaryCurrency\">Đơn vị tiền tệ</label>\n" +
                "                            <input type=\"text\" id=\"salaryCurrency\" name=\"salaryCurrency\" class=\"form-control\" value=\""+job.getSalaryCurrency()+"\">\n" +
                "                        </div>\n";
        return result;

    }
}