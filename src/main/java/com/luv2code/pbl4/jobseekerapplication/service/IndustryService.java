package com.luv2code.pbl4.jobseekerapplication.service;

import com.luv2code.pbl4.jobseekerapplication.entity.Industry;
import com.luv2code.pbl4.jobseekerapplication.entity.SiteIndustryCode;

import java.util.List;

public interface IndustryService {
    List<Industry> findAll();

    Industry findById(int id);


    Industry save(Industry theIndustry);

    void deleteById(int id);

    SiteIndustryCode getIndustryCodeByIndustryIdAndSourceId(int industryId, int sourceId);

    SiteIndustryCode findBySiteIndustryCodeAndSourceId(String industryCode, int sourceId);
}
