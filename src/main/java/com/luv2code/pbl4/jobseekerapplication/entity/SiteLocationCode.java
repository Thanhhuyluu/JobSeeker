package com.luv2code.pbl4.jobseekerapplication.entity;

import jakarta.persistence.*;

@Entity
@Table(name="site_location_codes")
public class SiteLocationCode {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="site_code_id")
    private int siteCodeId;

    @Column(name="location_id")
    private int locationId;

    @Column(name="source_id")
    private int sourceId;

    @Column(name="site_location_code")
    private String siteLocationCode;

    public SiteLocationCode() {
    }

    public SiteLocationCode(int locationId, int sourceId, String siteLocationCode) {
        this.locationId = locationId;
        this.sourceId = sourceId;
        this.siteLocationCode = siteLocationCode;
    }

    public int getSiteCodeId() {
        return siteCodeId;
    }

    public void setSiteCodeId(int siteCodeId) {
        this.siteCodeId = siteCodeId;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public int getSourceId() {
        return sourceId;
    }

    public void setSourceId(int sourceId) {
        this.sourceId = sourceId;
    }

    public String getSiteLocationCode() {
        return siteLocationCode;
    }

    public void setSiteLocationCode(String siteLocationCode) {
        this.siteLocationCode = siteLocationCode;
    }
}
