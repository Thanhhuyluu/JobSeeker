package com.luv2code.pbl4.jobseekerapplication.controller.jobseeker;

import com.luv2code.pbl4.jobseekerapplication.crawler.JobCrawler;
import com.luv2code.pbl4.jobseekerapplication.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/viec-lam")
public class JobController {
    private JobService jobService;
    private JobCrawler jobCrawler;

    @Autowired
    public JobController(JobService jobService, JobCrawler jobCrawler) {
        this.jobService = jobService;
        this.jobCrawler = jobCrawler;
    }

    @GetMapping("/crawl")
    public String showJobDetails(Model model) {
        jobCrawler.crawl("https://www.topcv.vn/viec-lam/nhan-vien-kinh-doanh-tai-ha-noi-luong-10-trieu-hoa-hong-thu-nhap-15-25-trieu/1476874.html?ta_source=BoxFeatureJob_LinkDetail");
        return "test";


    }
}
