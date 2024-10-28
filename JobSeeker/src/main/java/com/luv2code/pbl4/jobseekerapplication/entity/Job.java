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

    @Column(name="salary_range")
    private String salaryRange;

    @Column(name="company_id")
    private int companyId;

    @Column(name="job_type")
    private String jobType;

    @Column(name="experience_level")
    private String experienceLevel;

    @Column(name="career_level")
    private String careerLevel;

    @Column(name="posted_date")
    private LocalDate postedDate;

    @Column(name="expiration_date")
    private LocalDate expirationDate;

    @Column(name="job_url")
    private String jobUrl;

    @Column(name="location_id")
    private int locationId;

    @ManyToMany(fetch = FetchType.LAZY,
                cascade = {CascadeType.DETACH,CascadeType.MERGE,
                        CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinTable(
            name="job_industries",
            joinColumns = @JoinColumn(name="job_id"),
            inverseJoinColumns = @JoinColumn(name="industry_id")
    )
    private List<Industry> industries;

    public Job() {
    }

    public Job(String jobTitle, String jobDescription,
               String salaryRange, int companyId,
               String jobType, String experienceLevel,
               String careerLevel, LocalDate postedDate,
               LocalDate expirationDate, String jobUrl,
               int locationId) {
        this.jobTitle = jobTitle;
        this.jobDescription = jobDescription;
        this.salaryRange = salaryRange;
        this.companyId = companyId;
        this.jobType = jobType;
        this.experienceLevel = experienceLevel;
        this.careerLevel = careerLevel;
        this.postedDate = postedDate;
        this.expirationDate = expirationDate;
        this.jobUrl = jobUrl;
        this.locationId = locationId;
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

    public String getSalaryRange() {
        return salaryRange;
    }

    public void setSalaryRange(String salaryRange) {
        this.salaryRange = salaryRange;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public String getExperienceLevel() {
        return experienceLevel;
    }

    public void setExperienceLevel(String experienceLevel) {
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

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public List<Industry> getIndustries() {
        return industries;
    }

    public void setIndustries(List<Industry> industries) {
        this.industries = industries;
    }

    @Override
    public String toString() {
        return "Job{" +
                "jobId=" + jobId +
                ", jobTitle='" + jobTitle + '\'' +
                ", jobDescription='" + jobDescription + '\'' +
                ", salaryRange='" + salaryRange + '\'' +
                ", companyId=" + companyId +
                ", jobType='" + jobType + '\'' +
                ", experienceLevel='" + experienceLevel + '\'' +
                ", careerLevel='" + careerLevel + '\'' +
                ", postDate=" + postedDate +
                ", expirationDate=" + expirationDate +
                ", jobUrl='" + jobUrl + '\'' +
                ", locationId=" + locationId +
                '}';
    }


    public void addIndustry(Industry industry){
        if(industries==null){
            industries=new ArrayList<>();
        }
        industries.add(industry);
    }
}
