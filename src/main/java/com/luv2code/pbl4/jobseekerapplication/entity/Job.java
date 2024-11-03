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

    @ManyToOne
    @JoinColumn(name="company_id")
    private Company company;

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

    @Column(name = "min_salary")
    private int minSalary;

    @Column(name = "max_salary")
    private int maxSalary;

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

    public Job(String jobTitle, String jobDescription,
               String salaryRange, Company company,
               String jobType, String experienceLevel,
               String careerLevel, LocalDate postedDate,
               LocalDate expirationDate, String jobUrl,
               int minSalary, int maxSalary) {
        this.jobTitle = jobTitle;
        this.jobDescription = jobDescription;
        this.salaryRange = salaryRange;
        this.company = company;
        this.jobType = jobType;
        this.experienceLevel = experienceLevel;
        this.careerLevel = careerLevel;
        this.postedDate = postedDate;
        this.expirationDate = expirationDate;
        this.jobUrl = jobUrl;
        this.minSalary = minSalary;
        this.maxSalary = maxSalary;
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

    public int getMinSalary() {
        return minSalary;
    }

    public void setMinSalary(int minSalary) {
        this.minSalary = minSalary;
    }

    public int getMaxSalary() {
        return maxSalary;
    }

    public void setMaxSalary(int maxSalary) {
        this.maxSalary = maxSalary;
    }

    @Override
    public String toString() {
        return "Job{" +
                "jobId=" + jobId +
                ", jobTitle='" + jobTitle + '\'' +
                ", jobDescription='" + jobDescription + '\'' +
                ", salaryRange='" + salaryRange + '\'' +
                ", company=" + company +
                ", jobType='" + jobType + '\'' +
                ", experienceLevel='" + experienceLevel + '\'' +
                ", careerLevel='" + careerLevel + '\'' +
                ", postDate=" + postedDate +
                ", expirationDate=" + expirationDate +
                ", jobUrl='" + jobUrl + '\'' +
                '}';
    }


    public void addIndustry(Industry industry){
        if(industries==null){
            industries=new ArrayList<>();
        }
        industries.add(industry);
    }

    public void addLocation(Location location){
        if(locations==null){
            locations=new ArrayList<>();
        }
        locations.add(location);
    }
}
