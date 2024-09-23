package com.luv2code.pbl4.jobseekerapplication.dao;

import com.luv2code.pbl4.jobseekerapplication.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Integer> {
    Optional<Company> findByCompanyUrl(String companyUrl);
}
