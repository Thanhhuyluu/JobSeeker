package com.luv2code.pbl4.jobseekerapplication.controller.jobseeker;

import com.luv2code.pbl4.jobseekerapplication.crawler.JobCrawler;
import com.luv2code.pbl4.jobseekerapplication.entity.Industry;
import com.luv2code.pbl4.jobseekerapplication.entity.Job;
import com.luv2code.pbl4.jobseekerapplication.entity.Location;
import com.luv2code.pbl4.jobseekerapplication.service.IndustryService;
import com.luv2code.pbl4.jobseekerapplication.service.JobService;
import com.luv2code.pbl4.jobseekerapplication.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


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

    @GetMapping("/")
    public String showJobList(Model model) {
        findPaginated(1,model,-1,-1,"",null,null,null,null);

        return "web/index";
    }

    @GetMapping("/search/page={pageNo}")
    public String showJobSearch(@PathVariable("pageNo") int pageNo,
                                Model model,
                                @RequestParam(value = "industryId", defaultValue = "-1", required = false) int industryId,
                                @RequestParam(value = "locationId", defaultValue = "-1", required = false) int locationId ,
                                @RequestParam(value = "searchKey", defaultValue = "", required = false) String searchKey,
                                @RequestParam(value = "jobTypes", required = false) List<String> jobTypes,
                                @RequestParam(value = "careerLevels", required = false) List<String> careerLevels,
                                @RequestParam(value = "salary", required = false) Integer minSalary,
                                @RequestParam(value = "experienceLevel", required = false) Integer experienceLevel
    )  {
        findPaginated(1,model,industryId,locationId,searchKey, jobTypes,careerLevels,minSalary,experienceLevel);

        return "web/index";

    }

    @GetMapping("/page={pageNo}")
    @ResponseBody
    public String findPaginated(@PathVariable("pageNo") int pageNo,
                                Model model,
                                @RequestParam(value = "industryId", defaultValue = "-1", required = false) int industryId,
                                @RequestParam(value = "locationId", defaultValue = "-1", required = false) int locationId ,
                                @RequestParam(value = "searchKey", defaultValue = "", required = false) String searchKey,
                                @RequestParam(value = "jobTypes", required = false) List<String> jobTypes,
                                @RequestParam(value = "careerLevels", required = false) List<String> careerLevels,
                                @RequestParam(value = "salary", required = false) Integer minSalary,
                                @RequestParam(value = "experienceLevel", required = false) Integer experienceLevel) {



        System.out.println(jobTypes);
        List<Job> rawList = new ArrayList<>();
        String industryName = "", locationName ="";
        if(industryId != -1 && locationId != -1){
            industryName = industryService.findById(industryId).getIndustryName();
            locationName = locationService.findById(locationId).getName();
            System.out.println("oke");
            rawList=jobService.findByJobIndustryIdAndLocationId(industryId,locationId);
        }else if(industryId != -1 && locationId == -1) {
            industryName = industryService.findById(industryId).getIndustryName();
            rawList = jobService.findByIndustryId(industryId);
            System.out.println("locationEmpty");
        }else if(industryId == -1 && locationId != -1) {
            locationName = locationService.findById(locationId).getName();
            rawList = jobService.findByLocationId(locationId);
            System.out.println("industryEmpty");
        }else {

            System.out.println("industryLocationEmpty");
            rawList = jobService.findAll();
        }

        System.out.println("search Key: " + searchKey);
        if(!searchKey.trim().isEmpty()){

            List<Job> listJob = new ArrayList<>();
            for(Job job : rawList){
                if(job.getJobTitle().toLowerCase().contains(searchKey.toLowerCase())){
                    listJob.add(job);
                }
            }

            rawList = listJob;

        }
        rawList = jobService.findByFilters(rawList, careerLevels,experienceLevel,minSalary,jobTypes);

        List<Industry> listIndustry = industryService.findAll();
        List<Location> listLocations = locationService.findAll();

        int numPerPage = 10;
        int size = rawList.size();
        int pageNum = size%numPerPage == 0 ? (size /numPerPage) : (size /numPerPage + 1);


        int start, end;
        start = (pageNo-1) * numPerPage;
        end = Math.min(pageNo * numPerPage, size);
        List<Job> jobsList = jobService.getListByPage(rawList, start, end);



        model.addAttribute("industries", listIndustry);
        model.addAttribute("locations", listLocations);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("pageNum", pageNum);
        model.addAttribute("totalItems", size);
        model.addAttribute("jobs", jobsList);
        model.addAttribute("industryId", industryId);
        model.addAttribute("locationId", locationId);
        model.addAttribute("industryName", industryName);
        model.addAttribute("locationName", locationName);
        model.addAttribute("searchKey", searchKey);
        String result = "";


        result +="<div class=\"result-overview\">\n" +
                "                                <h1 class=\"result-overview-records-count\" > "+ size+" Jobs</h1>\n" +
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


        result +="<div class=\"app-main-job-list-container\">\n" +
                        "<ul id=\"app-main-job-list\" class=\"app-main-job-list\">";
        for (Job job : jobsList) {
            String locations = "";
            for(Location location : job.getLocations()) {
                locations += location.getName() +", ";
            }
            if (!locations.isEmpty()) {
                locations = locations.substring(0, locations.length() - 2);
            }

            result +=
                                "<li class=\"app-main-job-item\">\n" +
                                "  <a href=\"#\" th:title=\""+ job.getJobTitle() +"\" class=\"app-main-job-item-link\">" +
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
                                "                <span class=\"app-main-job-item-main-infor-tag-text\">" + job.getSalaryRange() + "</span>\n" +
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
                        "                                        <button  class=\"app-main-navigate-item-btn\" value=\"/viec-lam/page="+(pageNo-1)+("?industryId="+industryId + "&") +("locationId="+locationId)+"\">\n" +
                        "                                            <i class=\"fa-solid fa-chevron-left\"></i>\n" +
                        "                                        </button>\n" +
                        "                                    </li>\n")

                :

                "");

        for (int i = 1; i <= pageNum; i++) {
            result +=
                    "                                    <li" +
                            "                                        class=\"app-main-navigate-item "+ ((pageNo == i) ? "active\" " : "\" ")+">\n" +
                            "                                        <button class=\"app-main-navigate-item-btn\" value=\"/viec-lam/page="+i+("?industryId="+industryId + "&") +("locationId="+locationId)+"\">"+i+"</button>\n" +
                            "                            </li>\n";

        }



        result+=
                ((pageNo < pageNum) ?
                        "<li class=\"app-main-navigate-item\">\n" +
                                "                                        <button class=\"app-main-navigate-item-btn\" value=\"/viec-lam/page="+(pageNo +1)+("?industryId="+industryId + "&")  +("locationId="+locationId)+"\">\n" +
                                "                                            <i class=\"fa-solid fa-chevron-right\"></i>\n" +
                                "                                        </button>\n" +
                        "</li>\n"

                        :
                        "")
                                +
                "                                </ul>\n" +
                "                            </div>";



        // send back data to load on the page filter action done

        result += "<input hidden type=\"text\" id=\"search-key-sendback\" value=\" "+searchKey+"\">";
        return result;

    }

    @GetMapping("/delete")
    public String deleteEmployee(@RequestParam("jobId") int theJobId) {
        //delete the employee
        jobService.deleteById(theJobId);

        //redirect to the /employees/list
        return "redirect:/viec-lam/list";
    }

    @GetMapping("/showFormForUpdate")
    public String showFormForUpdate(@RequestParam("jobId") int theJobId, Model theModel) {

        //get the employee from the service
        Job theEmployee = jobService.findById(theJobId);
        // set employee in the model to prepopulate the form
        theModel.addAttribute("job", theEmployee);
        // send over to our form
        return "jobs/job-form";
    }
}



