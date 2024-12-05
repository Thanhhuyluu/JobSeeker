package com.luv2code.pbl4.jobseekerapplication.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="jobs")
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="job_id")
    private int jobId;

    @Column(name="job_title")
    private String jobTitle;


    @Column(name="job_description")
    private String jobDescription;

    @ManyToOne
    @JoinColumn(name="company_id")
    private Company company;

    @Column(name="job_type")
    private String jobType;

    @Column(name="experience_level")
    private int experienceLevel;

    @Column(name="career_level")
    private String careerLevel;

    @Column(name="posted_date")
    private LocalDate postedDate;

    @Column(name="expiration_date")
    private LocalDate expirationDate;

    @Column(name="job_url")
    private String jobUrl;

    @Column(name = "salary")
    private double salary;

    @Column(name = "salary_currency")
    private String salaryCurrency;

    @ManyToMany(fetch = FetchType.LAZY,
                cascade = {CascadeType.DETACH,CascadeType.MERGE,
                        CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinTable(
            name="job_industries",
            joinColumns = @JoinColumn(name="job_id"),
            inverseJoinColumns = @JoinColumn(name="industry_id")
    )
    private List<Industry> industries;



    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH,CascadeType.MERGE,
                    CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinTable(
            name="job_locations",
            joinColumns = @JoinColumn(name="job_id"),
            inverseJoinColumns = @JoinColumn(name="location_id")
    )
    private List<Location> locations;


    public Job() {
    }

    public Job(String salaryCurrency,
               double salary,
               String jobUrl,
               LocalDate expirationDate,
               LocalDate postedDate,
               String careerLevel,
               int experienceLevel,
               String jobType,
               Company company,
               String jobDescription,
               String jobTitle) {
        this.salaryCurrency = salaryCurrency;
        this.salary = salary;
        this.jobUrl = jobUrl;
        this.expirationDate = expirationDate;
        this.postedDate = postedDate;
        this.careerLevel = careerLevel;
        this.experienceLevel = experienceLevel;
        this.jobType = jobType;
        this.company = company;
        this.jobDescription = jobDescription;
        this.jobTitle = jobTitle;
    }

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public int getExperienceLevel() {
        return experienceLevel;
    }

    public void setExperienceLevel(int experienceLevel) {
        this.experienceLevel = experienceLevel;
    }

    public String getCareerLevel() {
        return careerLevel;
    }

    public void setCareerLevel(String careerLevel) {
        this.careerLevel = careerLevel;
    }

    public LocalDate getPostedDate() {
        return postedDate;
    }

    public void setPostedDate(LocalDate postedDate) {
        this.postedDate = postedDate;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getJobUrl() {
        return jobUrl;
    }

    public void setJobUrl(String jobUrl) {
        this.jobUrl = jobUrl;
    }

    public List<Industry> getIndustries() {
        return industries;
    }

    public void setIndustries(List<Industry> industries) {
        this.industries = industries;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getSalaryCurrency() {
        return salaryCurrency;
    }

    public void setSalaryCurrency(String salaryCurrency) {
        this.salaryCurrency =  salaryCurrency;
    }

    @Override
    public String toString() {
        return "Job{" +
                "jobId=" + jobId +
                ", jobTitle='" + jobTitle + '\'' +
                ", company=" + company +
                ", jobType='" + jobType + '\'' +
                ", experienceLevel='" + experienceLevel + '\'' +
                ", careerLevel='" + careerLevel + '\'' +
                ", postDate=" + postedDate +
                ", expirationDate=" + expirationDate +
                ", jobUrl='" + jobUrl + '\'' +
                ", jobSalary='" + salary + '\'' +
                ", salaryCurrency='" + salaryCurrency + '\'' +
                '}' + "\n";
    }


    public void addIndustry(Industry industry) {
        if (industries == null) {
            industries = new ArrayList<>();
        }
        if (!industries.contains(industry)) {
            industries.add(industry);
        }
    }
    public void addLocation(Location location){
        if(locations==null){
            locations=new ArrayList<>();
        }
        locations.add(location);
    }
}
