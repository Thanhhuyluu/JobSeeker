package com.luv2code.pbl4.jobseekerapplication.service;

import com.luv2code.pbl4.jobseekerapplication.entity.Company;

import java.util.List;

public interface CompanyService {
    List<Company> findAll();

    Company findById(int id);

    Company findByUrl(String name);

    Company save(Company theCompany);

    void deleteById(int id);


}
