package com.luv2code.pbl4.jobseekerapplication.controller.web;

import com.luv2code.pbl4.jobseekerapplication.crawler.JobCrawler;
import com.luv2code.pbl4.jobseekerapplication.dto.ListResult;
import com.luv2code.pbl4.jobseekerapplication.entity.Industry;
import com.luv2code.pbl4.jobseekerapplication.entity.Job;
import com.luv2code.pbl4.jobseekerapplication.entity.Location;
import com.luv2code.pbl4.jobseekerapplication.service.IndustryService;
import com.luv2code.pbl4.jobseekerapplication.service.JobService;
import com.luv2code.pbl4.jobseekerapplication.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;


import java.awt.datatransfer.SystemFlavorMap;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/viec-lam")
public class JobController {
    private JobService jobService;
    private LocationService locationService;
    private IndustryService industryService;
    private JobCrawler jobCrawler;

    @Autowired
    public JobController(JobService jobService,LocationService locationService, IndustryService industryService, JobCrawler jobCrawler) {
        this.jobService = jobService;
        this.locationService = locationService;
        this.industryService = industryService;
        this.jobCrawler = jobCrawler;
    }

    @GetMapping("/crawl")
    public String showJobDetails(Model model) {

        jobCrawler.crawlAllJobWithWebSource();
        return "web/index";
    }

    @GetMapping("")
    public String showJobs(Model model) {
        showJobListWithSort(1,10,"","", -1,-1, null,"", null,0.0,model);

        return "web/index";
    }


    @GetMapping("/{jobId}")
    public String showJobDetail(@PathVariable("jobId") int jobId, Model model) {
        Job theJob = jobService.findById(jobId);
        model.addAttribute("job", theJob);
        //redirect to the /employees/list
        return "web/job_infor_page";
    }

    @GetMapping("/tim-viec-lam")
    @ResponseBody
    public String showJobListWithSort(@RequestParam(defaultValue = "0", required = false) int pageNo,
                                      @RequestParam(defaultValue = "10", required = false) int pageSize,
                                      @RequestParam(required = false) String search,
                                      @RequestParam(required = false) String sortBy,
                                      @RequestParam(defaultValue = "-1", required = false) int industryId,
                                      @RequestParam(defaultValue = "-1", required = false) int locationId,
                                      @RequestParam(required = false) List<String> jobTypes,
                                      @RequestParam(required = false) String experienceLevel,
                                      @RequestParam(required = false) List<String> careerLevels,
                                      @RequestParam(defaultValue = "0", required = false) Double salary,
                                      Model model) {
        ListResult<Job> listResult = jobService.getAllJobsWithSortByColumnAndSearch(pageNo, pageSize, search, sortBy, industryId, locationId,jobTypes, experienceLevel, careerLevels, salary);
        List<Job> jobs = listResult.getItems();
        System.out.println(jobs.size() + "  ----");
        Long totalItems = listResult.getTotalItems();
        int pageNum = listResult.getTotalPages(pageSize);
        String industryName = "", locationName ="";
        if(industryId != -1){
            industryName = industryService.findById(industryId).getIndustryName();
        }
        if(locationId != -1){
            locationName = locationService.findById(locationId).getName();
        }

        if(jobTypes == null) {
            jobTypes = new ArrayList<>();
        }
        if(careerLevels == null) {
            careerLevels = new ArrayList<>();
        }

        List<Industry> listIndustry = industryService.findAll();
        List<Location> listLocations = locationService.findAll();

        model.addAttribute("industries", listIndustry);
        model.addAttribute("locations", listLocations);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("pageNum", pageNum);
        model.addAttribute("jobs", jobs);
        model.addAttribute("industryId", industryId);
        model.addAttribute("locationId", locationId);
        model.addAttribute("industryName", industryName);
        model.addAttribute("locationName", locationName);
        model.addAttribute("searchKey", search);
        model.addAttribute("jobTypes", jobTypes);
        model.addAttribute("experienceLevel", experienceLevel);
        model.addAttribute("careerLevels", careerLevels);
        model.addAttribute("salary", salary);

        String result = "";
        String noResultTag  = "";
        if(jobs.size() == 0) {
            noResultTag = "                                <div class=\"no-result-img\" style=\"background-image: url(/img/no-result.png);\"></div>";
            result += noResultTag;
        }else {
            result +="<div class=\"result-overview\">\n" +
                    "                                <h1 class=\"result-overview-records-count\" > "+ totalItems+" Jobs</h1>\n" +
                    "                                <input hidden type=\"checkbox\" class=\"result-overview-sort-input\" id=\"result-overview-sort-input\">\n" +
                    "                                <div class=\"result-overview-sort\">\n" +
                    "\n" +
                    "                                    <p class=\"result-overview-sort-text\">Sort by:</p>\n" +
                    "                                    <div class=\"result-overview-sort-principle\">\n" +
                    "\n" +
                    "                                        <label for=\"result-overview-sort-input\" class=\"result-overview-sort-current-principle\">Newest</label>\n" +
                    "                                        <ul class=\"result-overview-sort-principle-list\">\n" +
                    "                                            <li class=\"result-overview-sort-principle-item active\">\n" +
                    "                                                Newest\n" +
                    "                                            </li>\n" +
                    "                                            <li class=\"result-overview-sort-principle-item\">\n" +
                    "                                                Oldest\n" +
                    "                                            </li>\n" +
                    "                                            <li class=\"result-overview-sort-principle-item\">\n" +
                    "                                                Lương từ thấp đến cao\n" +
                    "                                            </li>\n" +
                    "                                            <li class=\"result-overview-sort-principle-item\">\n" +
                    "                                                Lương từ cao đến thấp\n" +
                    "                                            </li>\n" +
                    "                                        </ul>\n" +
                    "                                    </div>\n" +
                    "                                </div>\n" +
                    "                            </div>";


        }


            result +="<div class=\"app-main-job-list-container\">\n" +
                "<ul id=\"app-main-job-list\" class=\"app-main-job-list\">";
        for (Job job : jobs) {
            String locations = "";
            for(Location location : job.getLocations()) {
                locations += location.getName() +", ";
            }
            if (!locations.isEmpty()) {
                locations = locations.substring(0, locations.length() - 2);
            }

            result +=
                    "<li class=\"app-main-job-item\">\n" +
                            "  <a href=\"\\viec-lam\\"+ job.getJobId()+"\" th:title=\""+ job.getJobTitle() +"\" class=\"app-main-job-item-link\">" +
                            "    <div class=\"app-main-job-item-description\">\n" +
                            "        <div class=\"app-main-job-item-infor\">\n" +
                            "            <div class=\"app-main-job-item-company-img\" style=\"background-image: url(/img" + job.getCompany().getCompanyLogo() + ")\"></div>\n" +
                            "            <div class=\"app-main-job-item-overview-infor\">\n" +
                            "                <div class=\"app-main-job-item-job-title-wrapper\">\n" +
                            "                    <h1 class=\"app-main-job-item-job-title\">" + job.getJobTitle() + "</h1>\n" +
                            "                </div>\n" +
                            "                <p class=\"app-main-job-item-company-name-wrapper\">\n" +
                            "                    by\n" +
                            "                    <h4 class=\"app-main-job-item-company-name\">" + job.getCompany().getCompanyName() + "</h4>\n" +
                            "                </p>\n" +
                            "            </div>\n" +
                            "        </div>\n" +
                            "        <div class=\"app-main-job-item-main-infor\">\n" +
                            "            <div class=\"app-main-job-item-main-infor-tag hightlight\">\n" +
                            "                <p class=\"app-main-job-item-main-infor-tag-text\">" + job.getJobType() + "</p>\n" +
                            "            </div>\n" +
                            (locations.isEmpty() ?


                                    ""
                                    :
                                    "            <div  class=\"app-main-job-item-main-infor-tag app-main-job-item-main-infor-tag-limit\">\n" +
                                            "                <i class=\"app-main-job-item-main-infor-tag-icon fa-solid fa-location-dot\"></i>\n" +
                                            "                <span class=\"app-main-job-item-main-infor-tag-text\">" +locations+ "</span>\n" +
                                            "            </div>\n")+



                            "            <div class=\"app-main-job-item-main-infor-tag\">\n" +
                            "                <span class=\"app-main-job-item-main-infor-tag-text\">" +( job.getSalary() == 0 ? "Chưa cập nhật" : (job.getSalaryCurrency().equalsIgnoreCase("VND") ? job.getSalary() + " triệu": job.getSalary() + " " + job.getSalaryCurrency() )) + "</span>\n" +
                            "            </div>\n" +
                            "        </div>\n" +
                            "    </div>\n" +
                            "    <div class=\"app-main-job-item-action-and-date-left\">\n" +
                            "        <div class=\"app-main-job-item-action liked\">\n" +
                            "            <i class=\"app-main-job-item-action-icon app-main-job-item-action-icon--empty fa-regular fa-heart\"></i>\n" +
                            "            <i class=\"app-main-job-item-action-icon app-main-job-item-action-icon--filled fa-solid fa-heart\"></i>\n" +
                            "        </div>\n" +
                            "        <div class=\"app-main-job-item-day-left\">\n" +
                            "            <p class=\"app-main-job-item-day-left-text\">Đến ngày " + job.getExpirationDate() + "</p>\n" +
                            "        </div>\n" +
                            "    </div>\n" +
                            "  </a>\n" +
                            "</li>";

        }
        result +=
                "</ul>" +
                        "</div>";



        // XỬ LÝ PHẦN NAVIGATE


        result+= "<div class=\"app-main-navigate\">\n" +
                "                                <ul  class=\"app-main-navigate-list\">\n" +


                ((pageNo > 1) ?
                        ("                                    <li class=\"app-main-navigate-item\">\n" +
                                "\n" +
                                "                                        <button  class=\"app-main-navigate-item-btn\" value=\"/viec-lam/tim-viec-lam?pageNo="+(pageNo-1)+("&industryId="+industryId + "&") +("locationId="+locationId)+"\">\n" +
                                "                                            <i class=\"fa-solid fa-chevron-left\"></i>\n" +
                                "                                        </button>\n" +
                                "                                    </li>\n")

                        :

                        "");

        for (int i = 1; i <= pageNum; i++) {
            result +=
                    "                                    <li" +
                            "                                        class=\"app-main-navigate-item "+ ((pageNo == i) ? "active\" " : "\" ")+">\n" +
                            "                                        <button class=\"app-main-navigate-item-btn\" value=\"/viec-lam/tim-viec-lam?pageNo="+i+("&industryId="+industryId + "&") +("locationId="+locationId)+"\">"+i+"</button>\n" +
                            "                            </li>\n";

        }



        result+=
                ((pageNo < pageNum) ?
                        "<li class=\"app-main-navigate-item\">\n" +
                                "                                        <button class=\"app-main-navigate-item-btn\" value=\"/viec-lam/tim-viec-lam?pageNo="+(pageNo +1)+("&industryId="+industryId + "&")  +("locationId="+locationId)+"\">\n" +
                                "                                            <i class=\"fa-solid fa-chevron-right\"></i>\n" +
                                "                                        </button>\n" +
                                "</li>\n"

                        :
                        "")
                        +
                        "                                </ul>\n" +
                        "                            </div>";



        // send back data to load on the page filter action done

        result += "<input hidden type=\"text\" id=\"search-key-sendback\" value=\" "+search+"\">";
        return result;
    }
    @GetMapping("/tim-viec")
    public String showJos(@RequestParam(defaultValue = "0", required = false) int pageNo,
                                      @RequestParam(defaultValue = "10", required = false) int pageSize,
                                      @RequestParam(required = false) String search,
                                      @RequestParam(required = false) String sortBy,
                                      @RequestParam(defaultValue = "-1", required = false) int industryId,
                                      @RequestParam(defaultValue = "-1", required = false) int locationId,
                                      @RequestParam(required = false) List<String> jobTypes,
                                      @RequestParam(required = false) String experienceLevel,
                                      @RequestParam(required = false) List<String> careerLevels,
                                      @RequestParam(required = false) double salary,
                                      Model model) {
        showJobListWithSort(1,10,search,sortBy,industryId,locationId,jobTypes,experienceLevel, careerLevels,salary,model);
        return "web/index";
    }
}



