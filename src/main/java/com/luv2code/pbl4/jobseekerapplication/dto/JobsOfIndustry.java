package com.luv2code.pbl4.jobseekerapplication.dto;

import java.time.LocalDate;

public class JobsOfIndustry {
    private LocalDate date;
    private String industryName;
    private long jobCount;

    // Constructor, getter, setter
    public JobsOfIndustry(LocalDate date, String industryName, long jobCount) {
        this.date = date;
        this.industryName = industryName;
        this.jobCount = jobCount;
    }

    // Getters and Setters
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getIndustryName() {
        return industryName;
    }

    public void setIndustryName(String industryName) {
        this.industryName = industryName;
    }

    public long getJobCount() {
        return jobCount;
    }

    public void setJobCount(long jobCount) {
        this.jobCount = jobCount;
    }
}
