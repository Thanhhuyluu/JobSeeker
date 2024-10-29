package com.luv2code.pbl4.jobseekerapplication.service;

import com.luv2code.pbl4.jobseekerapplication.dao.IndustryRepository;
import com.luv2code.pbl4.jobseekerapplication.entity.Industry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IndustryServiceImpl implements IndustryService {
    private IndustryRepository industryRepository;
    @Autowired
    public IndustryServiceImpl(IndustryRepository industryRepository) { this.industryRepository = industryRepository; }

    @Override
    public List<Industry> findAll() {
        return industryRepository.findAll();
    }

    @Override
    public Industry findById(int id) {
        Optional<Industry> result = industryRepository.findById(id);
        Industry theIndustry = null;
        if (result.isPresent()) {
            theIndustry = result.get();
        }else {
            throw new RuntimeException("Industry not found");
        }
        return theIndustry;
    }

    @Override
    public Industry save(Industry theIndustry) { return industryRepository.save(theIndustry); }

    @Override
    public void deleteById(int id) {
        industryRepository.deleteById(id);
    }
}
