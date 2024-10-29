package com.luv2code.pbl4.jobseekerapplication.controller.jobseeker;

import com.luv2code.pbl4.jobseekerapplication.entity.Company;
import com.luv2code.pbl4.jobseekerapplication.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/company")
public class CompanyController {
    @Autowired
    private CompanyService companyService;

    @GetMapping
    public List<Company> getAllCompanies() {
        return companyService.findAll();
    }

    @GetMapping
    public Company getCompanyById(@PathVariable int id) {
        return companyService.findById(id);
    }

    @GetMapping
    public Company getCompanyByUrl(@RequestParam String url) {
        return companyService.findByUrl(url);
    }

    @PostMapping
    public Company createCompany(@RequestBody Company company) {
        return companyService.save(company);
    }

    @PutMapping("/{id}")
    public Company updateCompany(@PathVariable int id, @RequestBody Company companyDetails) {
        Company company = companyService.findById(id);
        if(company != null) {
            company.setCompanyName(companyDetails.getCompanyName());
            company.setCompanyDescription(companyDetails.getCompanyDescription());
            company.setCompanyUrl(companyDetails.getCompanyUrl());
            company.setCompanyWebsite(companyDetails.getCompanyWebsite());
            company.setCompanyLocation(companyDetails.getCompanyLocation());
            return companyService.save(company);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void deleteCompany(@PathVariable int id) {
        companyService.deleteById(id);
    }
}
