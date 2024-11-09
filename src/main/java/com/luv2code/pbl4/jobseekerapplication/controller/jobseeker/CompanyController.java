package com.luv2code.pbl4.jobseekerapplication.controller.jobseeker;

import com.luv2code.pbl4.jobseekerapplication.entity.Company;
import com.luv2code.pbl4.jobseekerapplication.service.CompanyService;
import com.luv2code.pbl4.jobseekerapplication.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cong-ty")
public class CompanyController {

    private CompanyService companyService;
    private JobService jobService;


    @Autowired
    public CompanyController(CompanyService companyService, JobService jobService) {
        this.companyService = companyService;
        this.jobService = jobService;
    }

    @GetMapping("/{companyId}")
    public String showCompany(Model model, @PathVariable("companyId") int companyId) {
        Company theCompany = companyService.findById(companyId);
        model.addAttribute("company", theCompany);
        return "web/company_infor_page";
    }

}
