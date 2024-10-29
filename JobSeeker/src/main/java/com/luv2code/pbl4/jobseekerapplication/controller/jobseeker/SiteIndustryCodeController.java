package com.luv2code.pbl4.jobseekerapplication.controller.jobseeker;

import com.luv2code.pbl4.jobseekerapplication.entity.SiteIndustryCode;
import com.luv2code.pbl4.jobseekerapplication.service.SiteIndustryCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/siteindustry")
public class SiteIndustryCodeController {
    @Autowired
    private SiteIndustryCodeService siteIndustryCodeService;

    @GetMapping
    public List<SiteIndustryCode> getAllSiteIndustryCodes() {
        return siteIndustryCodeService.findAll();
    }

    @GetMapping
    public SiteIndustryCode getSiteIndustryCodeById(@PathVariable int id) {
        return siteIndustryCodeService.findById(id);
    }

    @PostMapping
    public SiteIndustryCode createSiteIndustryCode(@RequestBody SiteIndustryCode siteIndustryCode) {
        return siteIndustryCodeService.save(siteIndustryCode);
    }

    @PutMapping("/{id}")
    public SiteIndustryCode updateSiteIndustryCode(@PathVariable int id, @RequestBody SiteIndustryCode siteIndustryCodeDetails) {
        SiteIndustryCode siteIndustryCode = siteIndustryCodeService.findById(id);
        if (siteIndustryCode != null) {
            siteIndustryCode.setSiteIndustryCode(siteIndustryCodeDetails.getSiteIndustryCode());
            siteIndustryCode.setIndustryId(siteIndustryCodeDetails.getIndustryId());
            siteIndustryCode.setSourceId(siteIndustryCodeDetails.getSourceId());
            return siteIndustryCodeService.save(siteIndustryCode);
        }
        return null;
    }
    @DeleteMapping("/{id}")
    public void deleteSiteIndustryCode(@PathVariable int id) {
        siteIndustryCodeService.deleteById(id);
    }
}
