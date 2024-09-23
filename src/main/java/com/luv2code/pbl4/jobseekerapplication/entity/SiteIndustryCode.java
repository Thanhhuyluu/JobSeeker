package com.luv2code.pbl4.jobseekerapplication.entity;


import jakarta.persistence.*;

@Entity
@Table(name="site_industry_codes")
public class SiteIndustryCode {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="site_code_id")
    private int siteCodeId;

    @Column(name="industry_id")
    private int industryId;

    @Column(name="source_id")
    private int sourceId;

    @Column(name="site_industry_code")
    private String siteIndustryCode;

    public SiteIndustryCode() {
    }

    public SiteIndustryCode(int industryId, int sourceId,
                            String siteIndustryCode) {
        this.industryId = industryId;
        this.sourceId = sourceId;
        this.siteIndustryCode = siteIndustryCode;
    }

    public int getSiteCodeId() {
        return siteCodeId;
    }

    public void setSiteCodeId(int siteCodeId) {
        this.siteCodeId = siteCodeId;
    }

    public int getIndustryId() {
        return industryId;
    }

    public void setIndustryId(int industryId) {
        this.industryId = industryId;
    }

    public int getSourceId() {
        return sourceId;
    }

    public void setSourceId(int sourceId) {
        this.sourceId = sourceId;
    }

    public String getSiteIndustryCode() {
        return siteIndustryCode;
    }

    public void setSiteIndustryCode(String siteIndustryCode) {
        this.siteIndustryCode = siteIndustryCode;
    }

    @Override
    public String toString() {
        return "SiteIndustryCode{" +
                "siteCodeId=" + siteCodeId +
                ", industryId=" + industryId +
                ", sourceId=" + sourceId +
                ", siteIndustryCode='" + siteIndustryCode + '\'' +
                '}';
    }
}
