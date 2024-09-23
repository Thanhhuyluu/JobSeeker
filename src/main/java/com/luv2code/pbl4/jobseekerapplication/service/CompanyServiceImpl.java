package com.luv2code.pbl4.jobseekerapplication.service;

import com.luv2code.pbl4.jobseekerapplication.dao.CompanyRepository;
import com.luv2code.pbl4.jobseekerapplication.entity.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyServiceImpl implements CompanyService {

    private CompanyRepository companyRepository;

    @Autowired
    public CompanyServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public List<Company> findAll() {
        return companyRepository.findAll();
    }

    @Override
    public Company findById(int id) {
        Optional<Company> result = companyRepository.findById(id);

        Company theCompany = null;

        if(result.isPresent()) {
            theCompany = result.get();
        }else{
            throw new RuntimeException("Company not found");
        }
        return theCompany;
    }

    @Override
    public Company findByUrl(String companyUrl) {
        Optional<Company> result = companyRepository.findByCompanyUrl(companyUrl);

        Company theCompany = null;

        if(result.isPresent()) {
            theCompany = result.get();
        }
        return theCompany;
    }

    @Override
    public Company save(Company theCompany) {
        return companyRepository.save(theCompany);
    }

    @Override
    public void deleteById(int id) {
        companyRepository.deleteById(id);
    }
}
