package com.luv2code.pbl4.jobseekerapplication.dao;

import com.luv2code.pbl4.jobseekerapplication.dto.JobsOfIndustry;
import com.luv2code.pbl4.jobseekerapplication.entity.Industry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface IndustryRepository extends JpaRepository<Industry, Integer> {






    @Query("SELECT  new com.luv2code.pbl4.jobseekerapplication.dto.JobsOfIndustry(j.postedDate, i.industryName, COUNT(j.jobId)) " +
            "FROM Job j " +
            "JOIN j.industries i " +
            "WHERE j.postedDate >= :dateLimit " +
            "GROUP BY j.postedDate, i.industryName " +
            "ORDER BY j.postedDate DESC, i.industryName")
    List<JobsOfIndustry> countJobsOfIndustry(@Param("dateLimit") LocalDate dateLimit);


}
