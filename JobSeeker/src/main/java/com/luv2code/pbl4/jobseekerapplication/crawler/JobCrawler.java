package com.luv2code.pbl4.jobseekerapplication.crawler;

import com.luv2code.pbl4.jobseekerapplication.entity.Company;
import com.luv2code.pbl4.jobseekerapplication.entity.Job;
import com.luv2code.pbl4.jobseekerapplication.service.CompanyService;
import com.luv2code.pbl4.jobseekerapplication.service.JobService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class JobCrawler {
    JobService jobService;
    CompanyService companyService;

    @Autowired
    public JobCrawler(JobService jobService, CompanyService companyService) {
        this.jobService = jobService;
        this.companyService = companyService;
    }

    public void crawl(String url) {
        try {
            Document doc = Jsoup.connect(url).get();
            List<Job> jobs = new ArrayList<>();

            String jobTitle = doc.select(".job-detail__info--title").text();

            String jobDescription = doc.select(".job-description").text();

            String jobSalaryRange = doc.selectFirst(".job-detail__info--section-content-value").text();



            Elements jobDetailSession = doc.select(".job-detail__info--section-content-value");

            String experienceLevel = jobDetailSession.get(2).text();

            Elements boxGeneralGroupInfo = doc.select(".box-general-group-info-value");

            String jobType = boxGeneralGroupInfo.get(3).text();

            String careerLevel = boxGeneralGroupInfo.get(0).text();

            LocalDate postedDate = LocalDate.now();


            String dateString = doc.selectFirst(".job-detail__information-detail--actions-label").text().trim().split(": ")[1];

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            LocalDate expirationDate = LocalDate.parse(dateString, formatter);

            String companyUrl = doc.selectFirst(".company-logo").attr("href");

            Company company = companyService.findByUrl(companyUrl);
            if (company == null) {
                String companyName = doc.selectFirst(".company-name-label").text();

                Document companyDoc = Jsoup.connect(companyUrl).get();
                String companyDescription = companyDoc.selectFirst(".content").html();
                String companyLocation = companyDoc.selectFirst(".desc").text();
                String companyWebsite = companyDoc.selectFirst(".company-subdetail-info-text").attr("href");
                company = new Company(companyName,companyUrl,companyLocation,companyDescription,companyWebsite);
                company = companyService.save(company);
            }


            Job job = new Job(jobTitle,jobDescription,jobSalaryRange,company.getCompanyId(),jobType,experienceLevel,careerLevel,postedDate,expirationDate,url,1);

            System.out.println(job);
            jobService.save(job);




        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
