package com.luv2code.pbl4.jobseekerapplication.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="industries")
public class Industry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="industry_id")
    private int industryId;

    @Column(name="industry_name")
    private String industryName;


    @ManyToMany(fetch = FetchType.LAZY,
                cascade = {CascadeType.REFRESH, CascadeType.MERGE,
                        CascadeType.DETACH, CascadeType.PERSIST})
    @JoinTable(
            name="job_industries",
            joinColumns = @JoinColumn(name="industry_id"),
            inverseJoinColumns = @JoinColumn(name="job_id")
    )
    private List<Job> jobs;

    public Industry() {
    }

    public Industry(String industryName) {
        this.industryName = industryName;
    }

    public int getIndustryId() {
        return industryId;
    }

    public void setIndustryId(int industryId) {
        this.industryId = industryId;
    }

    public String getIndustryName() {
        return industryName;
    }

    public void setIndustryName(String industryName) {
        this.industryName = industryName;
    }

    public List<Job> getJobs() {
        return jobs;
    }

    public void setJobs(List<Job> jobs) {
        this.jobs = jobs;
    }

    @Override
    public String toString() {
        return "Industry{" +
                "industryId=" + industryId +
                ", industryName='" + industryName + '\'' +
                '}';
    }

    public void addJob(Job job) {
        if(jobs == null) {
            jobs = new ArrayList<Job>();
        }
        jobs.add(job);
    }

}
