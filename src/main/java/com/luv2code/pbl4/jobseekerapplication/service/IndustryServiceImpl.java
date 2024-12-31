package com.luv2code.pbl4.jobseekerapplication.service;

import com.luv2code.pbl4.jobseekerapplication.dao.IndustryRepository;
import com.luv2code.pbl4.jobseekerapplication.dao.SiteIndustryCodeRepository;
import com.luv2code.pbl4.jobseekerapplication.dto.JobsOfIndustry;
import com.luv2code.pbl4.jobseekerapplication.entity.Industry;
import com.luv2code.pbl4.jobseekerapplication.entity.SiteIndustryCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class IndustryServiceImpl implements IndustryService {

    private IndustryRepository industryRepository;
    private SiteIndustryCodeRepository industryCodeRepository;


    @Autowired
    public IndustryServiceImpl(IndustryRepository industryRepository, SiteIndustryCodeRepository industryCodeRepository) {
        this.industryRepository = industryRepository;
        this.industryCodeRepository = industryCodeRepository;
    }

    @Override
    public List<Industry> findAll() {
        return industryRepository.findAll();
    }

    @Override
    public Industry findById(int id) {
        Optional<Industry> result = industryRepository.findById(id);

        Industry theIndustry = null;

        if(result.isPresent()) {
            theIndustry = result.get();
        }else{
            throw new RuntimeException("Industry not found");
        }
        return theIndustry;
    }


    @Override
    public Industry save(Industry theIndustry) {
        return industryRepository.save(theIndustry);
    }

    @Override
    public void deleteById(int id) {
        industryRepository.deleteById(id);
    }

    @Override
    public SiteIndustryCode getIndustryCodeByIndustryIdAndSourceId(int industryId, int sourceId) {
        return industryCodeRepository.findByIndustryIdAndSourceId(industryId,sourceId);
    }

    @Override
    public SiteIndustryCode findBySiteIndustryCodeAndSourceId(String industryCode, int sourceId) {
        return industryCodeRepository.findBySiteIndustryCodeAndSourceId(industryCode,sourceId);
    }

    @Override
    public List<JobsOfIndustry> countJobsOfIndustry(LocalDate dateLimit) {
        return industryRepository.countJobsOfIndustry(dateLimit);
    }
}
