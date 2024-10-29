package com.luv2code.pbl4.jobseekerapplication.service;

import com.luv2code.pbl4.jobseekerapplication.entity.SiteIndustryCode;

import java.util.List;

public interface SiteIndustryCodeService {
    List<SiteIndustryCode> findAll();
    SiteIndustryCode findById(int id);
    SiteIndustryCode save(SiteIndustryCode TheSiteIndustryCode);
    void deleteById(int id);
}
