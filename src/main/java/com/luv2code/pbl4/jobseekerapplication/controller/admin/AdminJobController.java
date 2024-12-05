package com.luv2code.pbl4.jobseekerapplication.controller.admin;


import com.luv2code.pbl4.jobseekerapplication.crawler.JobCrawler;
import com.luv2code.pbl4.jobseekerapplication.entity.Job;
import com.luv2code.pbl4.jobseekerapplication.service.IndustryService;
import com.luv2code.pbl4.jobseekerapplication.service.JobService;
import com.luv2code.pbl4.jobseekerapplication.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin/jobs")
public class AdminJobController {
    private JobService jobService;
    private LocationService locationService;
    private IndustryService industryService;
    private JobCrawler jobCrawler;

    @Autowired
    public AdminJobController(JobService jobService, LocationService locationService, IndustryService industryService, JobCrawler jobCrawler) {
        this.jobService = jobService;
        this.locationService = locationService;
        this.industryService = industryService;
        this.jobCrawler = jobCrawler;
    }


    @GetMapping("")
    public String showJobs(Model model) {
        List<Job> jobs = jobService.findAll();
        model.addAttribute("jobs", jobs);
        return "admin/job/JobList";
    }
}
