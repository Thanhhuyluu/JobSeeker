package com.luv2code.pbl4.jobseekerapplication.service;

import com.luv2code.pbl4.jobseekerapplication.dao.SiteIndustryCodeRepositoty;
import com.luv2code.pbl4.jobseekerapplication.entity.SiteIndustryCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SiteIndustryCodeServiceImpl implements SiteIndustryCodeService {
    private SiteIndustryCodeRepositoty siteIndustryCodeRepositoty;
    @Autowired
    public SiteIndustryCodeServiceImpl(SiteIndustryCodeRepositoty TheSiteIndustryCodeRepositoty) {this.siteIndustryCodeRepositoty = TheSiteIndustryCodeRepositoty;}
    @Override
    public List<SiteIndustryCode> findAll() {
        return siteIndustryCodeRepositoty.findAll();
    }
    @Override
    public SiteIndustryCode findById(int id) {
        Optional<SiteIndustryCode> result = siteIndustryCodeRepositoty.findById(id);

        SiteIndustryCode theSiteIndustryCode = null;
        if (result.isPresent()) {
            theSiteIndustryCode = result.get();
        }else {
            throw new RuntimeException("SiteIndustryCode not found");
        }
        return theSiteIndustryCode;
    }
    @Override
    public SiteIndustryCode save(SiteIndustryCode TheSiteIndustryCode) {
        return siteIndustryCodeRepositoty.save(TheSiteIndustryCode);
    }
    @Override
    public void deleteById(int id) { this.siteIndustryCodeRepositoty.deleteById(id); }
}
