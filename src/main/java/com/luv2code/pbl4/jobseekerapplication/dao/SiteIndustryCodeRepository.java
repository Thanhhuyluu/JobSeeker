package com.luv2code.pbl4.jobseekerapplication.dao;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SiteIndustryCode extends JpaRepository<SiteIndustryCode,Integer> {
    public SiteIndustryCode findByIndustryidAndSourceid(String code);
}
