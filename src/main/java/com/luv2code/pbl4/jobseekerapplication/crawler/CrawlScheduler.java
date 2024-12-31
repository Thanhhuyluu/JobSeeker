package com.luv2code.pbl4.jobseekerapplication.crawler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CrawlScheduler {

    @Autowired
    private JobCrawler jobCrawler;

    // Crawl mỗi 1 giờ
    @Scheduled(fixedRate = 3600000)
    public void scheduledCrawl() {
        if (!jobCrawler.isStopped()) {
                jobCrawler.crawlAllJobWithWebSource();

        }
    }
}

