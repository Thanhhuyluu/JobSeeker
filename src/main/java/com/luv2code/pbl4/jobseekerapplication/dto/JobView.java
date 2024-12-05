package com.luv2code.pbl4.jobseekerapplication.dto;

import com.luv2code.pbl4.jobseekerapplication.entity.Company;
import com.luv2code.pbl4.jobseekerapplication.entity.Job;
import jakarta.persistence.*;

import java.time.LocalDate;

public class JobView {

    private int jobId;
    private String jobTitle;
    private String jobDescription;
    private Company company;
    private String jobType;
    private int experienceLevel;
    private String careerLevel;
    private LocalDate postedDate;
    private LocalDate expirationDate;
    private String jobUrl;
    private double salary;
    private String salaryCurrency;

    private boolean isLiked;

    public JobView() {
    }
    public JobView(Job job) {
        this.jobId = job.getJobId();
        this.jobTitle = job.getJobTitle();
        this.jobDescription = job.getJobDescription();
        this.company = job.getCompany();
        this.jobType = job.getJobType();
        this.experienceLevel = job.getExperienceLevel();
        this.careerLevel = job.getCareerLevel();
        this.postedDate = job.getPostedDate();
        this.expirationDate = job.getExpirationDate();
        this.jobUrl = job.getJobUrl();
        this.salary = job.getSalary();
        this.salaryCurrency = job.getSalaryCurrency();
    }

    public JobView(int jobId, String jobTitle, String jobDescription, Company company, String jobType, int experienceLevel, String careerLevel, LocalDate postedDate, LocalDate expirationDate, String jobUrl, double salary, String salaryCurrency, boolean isLiked) {
        this.jobId = jobId;
        this.jobTitle = jobTitle;
        this.jobDescription = jobDescription;
        this.company = company;
        this.jobType = jobType;
        this.experienceLevel = experienceLevel;
        this.careerLevel = careerLevel;
        this.postedDate = postedDate;
        this.expirationDate = expirationDate;
        this.jobUrl = jobUrl;
        this.salary = salary;
        this.salaryCurrency = salaryCurrency;
        this.isLiked = isLiked;
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
        this.salaryCurrency = salaryCurrency;
    }

    public boolean isLiked() {
        return isLiked;
    }

    public void setLiked(boolean liked) {
        isLiked = liked;
    }
}
