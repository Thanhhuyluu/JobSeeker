package com.luv2code.pbl4.jobseekerapplication.service;

import com.luv2code.pbl4.jobseekerapplication.entity.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JobService {

    List<Job> findAll();

    Job findById(int id);

    Job save(Job theJob);

    void deleteById(int id);

    Job findByJobUrl(String jobUrl);

    Page<Job> findPaginated(Pageable pageable);

    List<Job> findByJobIndustryIdAndLocationId(int industryId ,int locationId);

    List<Job> findByLocationId(int locationId);

    List<Job> findByIndustryId(int industryId);

    List<Job> getListByPage(List<Job> list, int start, int end);

    //search

    List<Job> findByFilters(
            List<Job> rawList,
            List<String> careerLevel,
            Integer experienceLevel,
            Integer minSalary,
            List<String> jobTypes);


}
