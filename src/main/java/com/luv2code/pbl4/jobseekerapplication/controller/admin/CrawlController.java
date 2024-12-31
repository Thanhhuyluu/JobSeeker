package com.luv2code.pbl4.jobseekerapplication.controller.admin;


import com.luv2code.pbl4.jobseekerapplication.crawler.JobCrawler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/admin/crawl")
public class CrawlController {

    @Autowired
    private JobCrawler jobCrawler;

    @PostMapping("/start")
    public String startCrawl() {
        jobCrawler.resumeCrawl();
        return "Crawl started.";
    }

    @PostMapping("/pause")
    public String pauseCrawl() {
        jobCrawler.pauseCrawl();
        return "Crawl paused.";
    }

    @PostMapping("/stop")
    public String stopCrawl() {
        jobCrawler.stopCrawl();
        return "Crawl stopped.";
    }
}
