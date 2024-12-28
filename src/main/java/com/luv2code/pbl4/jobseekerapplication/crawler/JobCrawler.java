package com.luv2code.pbl4.jobseekerapplication.crawler;

import com.luv2code.pbl4.jobseekerapplication.entity.*;
import com.luv2code.pbl4.jobseekerapplication.service.CompanyService;
import com.luv2code.pbl4.jobseekerapplication.service.IndustryService;
import com.luv2code.pbl4.jobseekerapplication.service.JobService;
import com.luv2code.pbl4.jobseekerapplication.service.LocationService;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.couchbase.CouchbaseProperties;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class JobCrawler {
    JobService jobService;
    CompanyService companyService;
    IndustryService industryService;
    LocationService locationService;
    @Autowired
    public JobCrawler(JobService jobService, CompanyService companyService, IndustryService industryService, LocationService locationService) {
        this.jobService = jobService;
        this.companyService = companyService;
        this.industryService = industryService;
        this.locationService = locationService;
    }

    private volatile boolean isPaused = false;

    public synchronized void pauseCrawl() {
        isPaused = true;
    }

    public synchronized void resumeCrawl() {
        isPaused = false;
        notify();
    }

    public synchronized void checkIfPaused() throws InterruptedException {
        while (isPaused) {
            wait();
        }
    }

    public void crawl(String url) {
        Job theJob = jobService.findByJobUrl(url);
        if(theJob != null) {
            return;
        }

        System.out.println("Crawling job: " + url);
        try {

            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                    .referrer("http://www.google.com")
                    .get();

            doc.select(".job-detail__info--title");
            String jobTitle = doc.select(".job-detail__info--title").text();


            String jobDescription = doc.select(".job-description").html();

            String jobSalaryRange = null;
            int minSalary = 0, maxSalary = 0;

            String currency = "";
            Double salary = null;

            if(doc.selectFirst(".job-detail__info--section-content-value")!= null){
                jobSalaryRange = doc.selectFirst(".job-detail__info--section-content-value").text();




                Pattern rangePattern = Pattern.compile("(\\d+(\\.\\d+)?)\\s*-\\s*(\\d+(\\.\\d+)?)\\s*([\\p{L}]+)?");
                Pattern abovePattern = Pattern.compile("Trên\\s*(\\d+(\\.\\d+)?)\\s*([\\p{L}]+)?");
                Pattern belowPattern = Pattern.compile("Dưới\\s*(\\d+(\\.\\d+)?)\\s*([\\p{L}]+)?");

                Matcher rangeMatcher = rangePattern.matcher(jobSalaryRange);
                Matcher aboveMatcher = abovePattern.matcher(jobSalaryRange);
                Matcher belowMatcher = belowPattern.matcher(jobSalaryRange);

                if (rangeMatcher.find()) {
                    salary = Double.parseDouble(rangeMatcher.group(1).replace(",", ""));
                    currency = (rangeMatcher.group(5) != null && !rangeMatcher.group(5).equalsIgnoreCase("triệu")) ? rangeMatcher.group(5) : "VND";
                } else if (aboveMatcher.find()) {
                    salary = Double.parseDouble(aboveMatcher.group(1).replace(",", ""));
                    currency = (aboveMatcher.group(3) != null && !aboveMatcher.group(3).equalsIgnoreCase("triệu")) ? aboveMatcher.group(3) : "VND";
                } else if (belowMatcher.find()) {
                    salary = Double.parseDouble(belowMatcher.group(1).replace(",", ""));
                    currency =  (belowMatcher.group(3) != null && !belowMatcher.group(3).equalsIgnoreCase("triệu"))? belowMatcher.group(3) : "VND";
                }else {
                    salary = 0.0;
                    currency = "VND";
                }
            }




            Elements jobDetailSession = doc.select(".job-detail__info--section-content-value");

            int experienceLevel = 0;

            try {
                Pattern pattern = Pattern.compile("\\d+");
                Matcher matcher = pattern.matcher(jobDetailSession.get(3).text());

                if (matcher.find()) {
                    experienceLevel =  Integer.parseInt(matcher.group());
                }else {
                    experienceLevel = 0;
                }



            } catch (IndexOutOfBoundsException e) {
                System.out.println("No experience level found" + " url" + url);
                return;
            }
            Elements boxGeneralGroupInfo = doc.select(".box-general-group-info-value");

            String jobType = boxGeneralGroupInfo.get(2).text();

            String careerLevel = boxGeneralGroupInfo.get(0).text();

            LocalDate postedDate = LocalDate.now();

            LocalDate expirationDate = null;
            String dateString = null;
            if(doc.selectFirst(".job-detail__information-detail--actions-label")!= null){
                dateString = doc.selectFirst(".job-detail__information-detail--actions-label").text().trim().split(": ")[1];

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

                expirationDate = LocalDate.parse(dateString, formatter);

            }
            String companyUrl = null;
            if(doc.selectFirst(".company-logo") != null){
                companyUrl = doc.selectFirst(".company-logo").attr("href");
            }


            Company company = companyService.findByUrl(companyUrl);

            if (company == null) {
                String companyName = null;
                if( doc.selectFirst(".company-name-label") != null){
                    companyName = doc.selectFirst(".company-name-label").text();
                }


                Document companyDoc = Jsoup.connect(companyUrl)
                        .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                        .referrer("http://www.google.com")
                        .get();
                String companyDescription = null;
                if(companyDoc.selectFirst(".content") != null){
                    companyDescription = companyDoc.selectFirst(".content").html();
                }
                String companyLocation =null;
                if(companyDoc.selectFirst(".desc")!= null){
                    companyLocation = companyDoc.selectFirst(".desc").text();
                }
                String companyWebsite =null;
                if( companyDoc.selectFirst(".company-subdetail-info-text")!= null){
                    companyWebsite = companyDoc.selectFirst(".company-subdetail-info-text").attr("href");
                }



                company = new Company(companyName, companyUrl, companyLocation, companyDescription, companyWebsite, null);
                company = companyService.save(company);
                //Lưu logo công ty
                String companyLogo = null;
                if(companyDoc.selectFirst(".company-image-logo img") != null){
                    companyLogo = companyDoc.selectFirst(".company-image-logo img").absUrl("src");

                    String projectDir = System.getProperty("user.dir");
                    String imgDir = Paths.get(projectDir, "src", "main", "resources", "static", "img").toString();

                    String fileExtension = getFileExtension(companyLogo);

                    downloadImage(companyLogo, imgDir + "/company_logo" + company.getCompanyId() + fileExtension);
                    company.setCompanyLogo("/company_logo" + company.getCompanyId() + fileExtension);
                    companyService.save(company);
                }

            }
            Job job = new Job(currency,salary,url,expirationDate,postedDate,careerLevel,experienceLevel,jobType,company,jobDescription,jobTitle);
            System.out.println(job);


            // Lấy phần mã ngành
            Elements boxCategoryTags = doc.select(".box-category-tags");

            Element industryTagWrapper = boxCategoryTags.first();

            Elements industryTags = industryTagWrapper.select(".box-category-tag");
            int ANOTHER_INDUSTRY_ID = 899;
            List<String> industryList = new ArrayList<>();
            for (Element industryTag : industryTags) {


                Pattern pattern = Pattern.compile("cr(\\d+)$");
                Matcher matcher = pattern.matcher(industryTag.attr("href"));

                if (matcher.find()) {
                    industryList.add(matcher.group(1));
                    String tempIndustryCode = matcher.group(1);
                    SiteIndustryCode siteIndustryCode = null;
                    siteIndustryCode = industryService.findBySiteIndustryCodeAndSourceId(tempIndustryCode,1);
                    if(siteIndustryCode != null) {
                        Industry industry = industryService.findById(siteIndustryCode.getIndustryId());
                        job.addIndustry(industry);
                    }else {
                        Industry industry = industryService.findById(ANOTHER_INDUSTRY_ID);
                        job.addIndustry(industry);
                    }
                }
            }


                // Lấy phần mã địa điểm
            Element locationTagWrapper = boxCategoryTags.get(1);

            Elements locationTags = locationTagWrapper.select(".box-category-tag");


            for (Element locationTag : locationTags) {


                Pattern pattern = Pattern.compile("l(\\d+)$");
                Matcher matcher = pattern.matcher(locationTag.attr("href"));

                if (matcher.find()) {

                    String tempLocationCode = matcher.group(1);
                    SiteLocationCode siteLocationCode = null;
                    siteLocationCode = locationService.findBySiteLocationCodeAndSourceId(tempLocationCode,1);
                    if(siteLocationCode != null) {
                        Location location = locationService.findById(siteLocationCode.getLocationId());
                        System.out.println(location);
                        job.addLocation(location);
                    }
                }
            }





             jobService.save(job);




        } catch (IOException ex1){
            ex1.printStackTrace();
        }



    }

    public void crawlAllJobWithWebSource(){

        List<Industry> industryList = industryService.findAll();
        List<Location> locationList = locationService.findAll();

        for(Industry industry : industryList){
            for(Location location : locationList){
                String industryCode = industryService.getIndustryCodeByIndustryIdAndSourceId(industry.getIndustryId(),1).getSiteIndustryCode();
                String locationCode = locationService.getLocationCodeByLocationIdAndSourceId(location.getLocationId(),1).getSiteLocationCode();
                String url = "https://www.topcv.vn/tim-viec-lam-"+industry.getIndustryName().replace("/"," ").replaceAll("\\s+", "-")
                        + "-tai-" + location.getName().replaceAll("\\s+", "-") + "-l" + locationCode +"c"+ industryCode;


                try {
                    Document doc = Jsoup.connect(url)
                            .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                            .referrer("http://www.google.com")
                            .get();

                    Elements jobs = doc.select(".job-list-search-result .job-item-search-result");
                    for(Element job : jobs){
                        String jobUrl = null;
                        if(job.selectFirst(".title").selectFirst("a") != null){
                            jobUrl = job.selectFirst(".title").selectFirst("a").attr("href");
                            jobUrl = getUrlUpToHtml(jobUrl);
                            crawl(jobUrl);
                            TimeUnit.SECONDS.sleep(20);

                        }


                    }


                }catch (IOException ex1){
                    ex1.printStackTrace();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    e.printStackTrace();
                }


            }


        }


    }
    public static void downloadImage(String imageUrl, String destinationFile) throws IOException {
        URL url = new URL(imageUrl);
        try (InputStream in = url.openStream()) {
            Files.copy(in, Paths.get(destinationFile), StandardCopyOption.REPLACE_EXISTING);
        }
    }
    public static String getFileExtension(String url) {
        return url.substring(url.lastIndexOf("."));
    }
    public String getUrlUpToHtml(String url) {
        int index = url.indexOf(".html");
        if (index != -1) {
            return url.substring(0, index + 5); // Lấy từ đầu đến sau ".html"
        }
        return url; // Trả về nguyên URL nếu không tìm thấy ".html"
    }
}
