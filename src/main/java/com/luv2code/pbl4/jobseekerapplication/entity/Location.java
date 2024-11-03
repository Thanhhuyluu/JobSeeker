package com.luv2code.pbl4.jobseekerapplication.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="locations")
public class Location {

    @Id
    @Column(name="location_id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int locationId;

    @Column(name="location_name")
    private String name;


    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.REFRESH, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.PERSIST})
    @JoinTable(
            name="job_locations",
            joinColumns = @JoinColumn(name="location_id"),
            inverseJoinColumns = @JoinColumn(name="job_id")
    )
    private List<Job> jobs;

    public Location() {
    }

    public Location(String name) {
        this.name = name;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int id) {
        this.locationId = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Job> getJobs() {
        return jobs;
    }

    public void setJobs(List<Job> jobs) {
        this.jobs = jobs;
    }

    @Override
    public String toString() {
        return name;
    }

    public void addJob(Job job) {
        if(jobs == null) {
            jobs = new ArrayList<Job>();
        }
        jobs.add(job);
    }
}
