package com.luv2code.pbl4.jobseekerapplication.dao;

import com.luv2code.pbl4.jobseekerapplication.entity.SiteIndustryCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SiteIndustryCodeRepository extends JpaRepository<SiteIndustryCode,Integer> {
    public SiteIndustryCode findByIndustryIdAndSourceId(int industryid, int sourceid);
    public SiteIndustryCode findBySiteIndustryCodeAndSourceId(String industryCode, int sourceId);
}
