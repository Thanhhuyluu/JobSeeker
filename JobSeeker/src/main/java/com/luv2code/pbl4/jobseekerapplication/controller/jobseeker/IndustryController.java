package com.luv2code.pbl4.jobseekerapplication.controller.jobseeker;

import com.luv2code.pbl4.jobseekerapplication.entity.Industry;
import com.luv2code.pbl4.jobseekerapplication.service.IndustryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/industry")
public class IndustryController {
    @Autowired
    private IndustryService industryService;

    @GetMapping
    public List<Industry> getAllIndustry() {
        return industryService.findAll();
    }

    @GetMapping
    public Industry getIndustryById(@PathVariable int id) {
        return industryService.findById(id);
    }

    @PostMapping
    public Industry createIndustry(@RequestBody Industry industry) {
        return industryService.save(industry);
    }

    @PutMapping("/{id}")
    public Industry updateIndustry(@PathVariable int id,@RequestBody Industry industryDetails) {
        Industry industry = industryService.findById(id);
        if(industry != null) {
            industry.setIndustryName(industryDetails.getIndustryName());
            return industryService.save(industry);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void deleteIndustry(@PathVariable int id) {
        industryService.deleteById(id);
    }
}
