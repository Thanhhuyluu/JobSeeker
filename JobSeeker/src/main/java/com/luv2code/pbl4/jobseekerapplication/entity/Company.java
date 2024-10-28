package com.luv2code.pbl4.jobseekerapplication.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "companies")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_id")
    private int companyId;

    @Column(name="company_name")
    private String companyName;

    @Column(name="company_url")
    private String companyUrl;

    @Column(name="company_location")
    private String companyLocation;

    @Column(name="company_description")
    private String companyDescription;

    @Column(name="company_website")
    private String companyWebsite;

    public Company() {
    }

    public Company(String companyName, String companyUrl, String companyLocation, String companyDescription, String companyWebsite) {
        this.companyName = companyName;
        this.companyUrl = companyUrl;
        this.companyLocation = companyLocation;
        this.companyDescription = companyDescription;
        this.companyWebsite = companyWebsite;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyUrl() {
        return companyUrl;
    }

    public void setCompanyUrl(String companyUrl) {
        this.companyUrl = companyUrl;
    }

    public String getCompanyLocation() {
        return companyLocation;
    }

    public void setCompanyLocation(String companyLocation) {
        this.companyLocation = companyLocation;
    }

    public String getCompanyDescription() {
        return companyDescription;
    }

    public void setCompanyDescription(String companyDescription) {
        this.companyDescription = companyDescription;
    }

    public String getCompanyWebsite() {
        return companyWebsite;
    }

    public void setCompanyWebsite(String companyWebsite) {
        this.companyWebsite = companyWebsite;
    }
}
