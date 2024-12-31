package com.luv2code.pbl4.jobseekerapplication.controller.admin;


import com.luv2code.pbl4.jobseekerapplication.crawler.JobCrawler;
import com.luv2code.pbl4.jobseekerapplication.dto.ListResult;
import com.luv2code.pbl4.jobseekerapplication.entity.Company;
import com.luv2code.pbl4.jobseekerapplication.entity.Industry;
import com.luv2code.pbl4.jobseekerapplication.entity.Job;
import com.luv2code.pbl4.jobseekerapplication.entity.Location;
import com.luv2code.pbl4.jobseekerapplication.service.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
@Controller
@RequestMapping("/admin")
public class AdminJobController {
    private final CompanyService companyService;
    private final JobService jobService;
    private final LocationService locationService;
    private final IndustryService industryService;
    private final JobCrawler jobCrawler;

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

        model.addAttribute("search", search);
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
                " <div class=\"add-new-record-button-wrapper col l-12\">\n" +
                "                    <a href=\"them-cong-viec\" class=\"add-new-record-button\">\n" +
                "                        <i class=\"add-new-record-button-icon fa-solid fa-plus\"></i>\n" +
                "                    </a>\n" +
                "                </div>" +
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
                    "                                <a class=\"table-action-button table-action-button--show\" href=\"xem-chi-tiet?jobId="+job.getJobId()+"\">\n" +
                    "                                    <i class=\"fa-solid fa-circle-info\"></i>\n" +
                    "                                </a>" +
                    "                               <a class=\"table-action-button table-action-button--change\"\n" +
                    "                                     href=\"sua-viec-lam?jobId=" + job.getJobId() + "\">\n" +
                    "                                    <i class=\"fa-solid fa-pen\"></i>\n" +
                    "                                </a>\n" +
                    "                                <div\n" +
                    "                                        class=\"table-action-button table-action-button--delete\"\n" +
                    "                                        data-url=\"xoa-viec-lam?jobId=" + job.getJobId() + "&currentPage=" + pageNo+"\"\n" +
                    "                                        data-message=\"Bạn có muốn xóa công việc này không?\"\n" +
                    "                                        onclick=\"handleDelete(this)\">\n" +
                    "                                    <i class=\"fa-solid fa-trash\"></i>\n" +
                    "                                </div>" +
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
                        "            </div>";


        return result;
    }

    @GetMapping("/them-cong-viec")
    public String sendAddJobForm(Model model) {
        List<Company> companies = companyService.findAll();
        List<Industry> industries = industryService.findAll();
        List<Location> locations = locationService.findAll();



        model.addAttribute("locations", locations);
        model.addAttribute("industries", industries);
        model.addAttribute("mode", "MODE_JOBS");
        model.addAttribute("companies", companies);
        return "admin/AddJobForm";
    }


    @PostMapping("/them-cong-viec")
    public String addJob(@RequestParam(required = true) String selectedIndustryIds,
                         @RequestParam(required = true) String selectedLocationIds,
                         @RequestParam(required = true) String jobTitle,
                         @RequestParam(required = true) String jobType,
                         @RequestParam(required = true) String experienceLevel,
                         @RequestParam(required = true) String careerLevel,
                         @RequestParam(required = true) LocalDate expirationDate,
                         @RequestParam(required = true) int companyId,
                         @RequestParam(required = true) String jobUrl,
                         @RequestParam(required = true) Double salary,
                         @RequestParam(required = true) String salaryCurrency,
                         @RequestParam("job-topic-name[]") List<String> jobTopicNames,
                         @RequestParam("job-topic-description[]") List<String> jobTopicDescs,
                         Model model) {


        Job job = new Job();

        List<String> locationIdList = Arrays.asList(selectedLocationIds.split(","));
        for(String s : locationIdList) {
            int locationId = Integer.parseInt(s);
            Location location = locationService.findById(locationId);
            job.addLocation(location);
        }



        List<String> industryIdList = Arrays.asList(selectedIndustryIds.split(","));
        for(String s : industryIdList) {
            int industryId = Integer.parseInt(s);
            Industry industry = industryService.findById(industryId);
            job.addIndustry(industry);
        }


        StringBuilder htmlOutput = new StringBuilder();

        for (int i = 0; i < jobTopicNames.size(); i++) {
            htmlOutput.append("<div class='job-description__item'>\n")
                    .append("<h3>").append(jobTopicNames.get(i)).append("</h3>\n")
                    .append("<div class='job-description__item-content'>\n")
                    .append("<ul>\n");

            String[] lines = jobTopicDescs.get(i).split("\n");


            for (String line : lines) {
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }

                htmlOutput.append("<li>").append(line).append("</li>\n");
            }




            htmlOutput.append("</ul>\n").append("</div>\n").append("</div>\n");
        }



        job.setJobTitle(jobTitle);
        job.setJobType(jobType);
        job.setCareerLevel(careerLevel);
        job.setExperienceLevel(Integer.parseInt(experienceLevel));
        job.setCompany(companyService.findById(companyId));
        job.setExpirationDate(expirationDate);
        job.setSalary(salary);
        job.setSalaryCurrency(salaryCurrency);
        job.setJobUrl(jobUrl);
        job.setJobDescription(htmlOutput.toString());

        jobService.save(job);

        System.out.println(job);

        return showJobs(1, 10, "", "", -1, -1, null, "", null, 0.0, model);
    }

    @GetMapping ("/xoa-viec-lam")
    public String deleteJob(@RequestParam(required = true) int jobId,
                            @RequestParam(required = false, defaultValue = "1") int currentPage,
                            Model model) {
        jobService.deleteById(jobId);
        model.addAttribute("currentPage", currentPage);
        return showJobs(currentPage, 10, "", "", -1, -1, null, "", null, 0.0, model);
    }
    @GetMapping("/xem-chi-tiet")
    public String showJobDetail(@RequestParam(required = true) int jobId, Model model) {
        Job job = jobService.findById(jobId);




        List<String> topicNames = new ArrayList<>();
        List<String> topicDescs = new ArrayList<>();

        getRawJobDescription(job, topicNames, topicDescs);

        model.addAttribute("job", job);
        model.addAttribute("topicNames", topicNames);
        model.addAttribute("topicDescs", topicDescs);
        return "admin/ViewJob";
    }

    @GetMapping("/sua-viec-lam")
    public String sendEditJobForm(@RequestParam(required = true) int jobId, Model model) {
        Job job = jobService.findById(jobId);
        List<Company> companies = companyService.findAll();
        List<Industry> industries = industryService.findAll();
        List<Location> locations = locationService.findAll();
        String selectedLocationIds = job.getLocations().stream()
                .map(location -> String.valueOf( location.getLocationId()))
                .collect(Collectors.joining(","));

        String selectedIndustryIds = job.getIndustries().stream()
                .map(industry -> String.valueOf( industry.getIndustryId()))
                .collect(Collectors.joining(","));


        List<String> topicNames = new ArrayList<>();
        List<String> topicDescs = new ArrayList<>();
        getRawJobDescription(job, topicNames, topicDescs);


        model.addAttribute("topicNames", topicNames);
        model.addAttribute("topicDescs", topicDescs);
        model.addAttribute("selectedIndustryIds", selectedIndustryIds);
        model.addAttribute("selectedLocationIds", selectedLocationIds);
        model.addAttribute("locations", locations);
        model.addAttribute("industries", industries);
        model.addAttribute("mode", "MODE_JOBS");
        model.addAttribute("companies", companies);
        model.addAttribute("job", job);
        return "admin/EditJobForm";
    }

    @PostMapping("/sua-viec-lam")
    public String updateJob(@RequestParam(required = true) int jobId,
                        @RequestParam(required = true) String selectedIndustryIds,
                         @RequestParam(required = true) String selectedLocationIds,
                         @RequestParam(required = true) String jobTitle,
                         @RequestParam(required = true) String jobType,
                         @RequestParam(required = true) String experienceLevel,
                         @RequestParam(required = true) String careerLevel,
                         @RequestParam(required = true) LocalDate expirationDate,
                         @RequestParam(required = true) int companyId,
                         @RequestParam(required = true) String jobUrl,
                         @RequestParam(required = true) Double salary,
                         @RequestParam(required = true) String salaryCurrency,
                         @RequestParam("job-topic-name[]") List<String> jobTopicNames,
                         @RequestParam("job-topic-description[]") List<String> jobTopicDescs,
                         Model model) {


        Job job = jobService.findById(jobId);
        job.setLocations(new ArrayList<>());
        job.setIndustries(new ArrayList<>());

        List<String> locationIdList = Arrays.asList(selectedLocationIds.split(","));
        for(String s : locationIdList) {
            int locationId = Integer.parseInt(s);
            Location location = locationService.findById(locationId);
            job.addLocation(location);
        }



        List<String> industryIdList = Arrays.asList(selectedIndustryIds.split(","));
        for(String s : industryIdList) {
            int industryId = Integer.parseInt(s);
            Industry industry = industryService.findById(industryId);
            job.addIndustry(industry);
        }


        StringBuilder htmlOutput = new StringBuilder();

        for (int i = 0; i < jobTopicNames.size(); i++) {
            htmlOutput.append("<div class='job-description__item'>\n")
                    .append("<h3>").append(jobTopicNames.get(i)).append("</h3>\n")
                    .append("<div class='job-description__item-content'>\n")
                    .append("<ul>\n");

            String[] lines = jobTopicDescs.get(i).split("\n");


            for (String line : lines) {
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }

                line = line.replaceAll("^[^a-zA-Z]+", "");
                htmlOutput.append("<li>").append(line).append("</li>\n");
            }




            htmlOutput.append("</ul>\n").append("</div>\n").append("</div>\n");
        }


        job.setJobDescription(htmlOutput.toString());



        job.setJobTitle(jobTitle);
        job.setJobType(jobType);
        job.setCareerLevel(careerLevel);
        job.setExperienceLevel(Integer.parseInt(experienceLevel));
        job.setCompany(companyService.findById(companyId));
        job.setExpirationDate(expirationDate);
        job.setSalary(salary);
        job.setSalaryCurrency(salaryCurrency);
        job.setJobUrl(jobUrl);
        jobService.save(job);

        System.out.println(job);

        return showJobs(1, 10, "", "", -1, -1, null, "", null, 0.0, model);
    }

    public void getRawJobDescription(Job job,List<String> topicNames, List<String> topicDescs) {
        Document document = Jsoup.parse(job.getJobDescription());


        Elements items = document.select(".job-description__item");

        for (Element item : items) {
            String topicName = item.select("h3").text();
            topicNames.add(topicName);

            Elements itemDesc = item.select(".job-description__item--content");
            StringBuilder descriptions = new StringBuilder();
            if(!(itemDesc.select("ul li").isEmpty())) {
                Elements itemDescs = itemDesc.select("ul li");
                for(Element itemDesc1 : itemDescs) {
                    descriptions.append(itemDesc1.text()).append("\n");
                }
            }else if(!(itemDesc.select("p").isEmpty())){
                Elements itemDescs = itemDesc.select("p");
                for(Element itemDesc1 : itemDescs) {
                    descriptions.append(itemDesc1.text()).append("\n");
                }

            }else {
                descriptions.append(itemDesc.text()).append("\n");
            }


            topicDescs.add(descriptions.toString());
        }

    }

}