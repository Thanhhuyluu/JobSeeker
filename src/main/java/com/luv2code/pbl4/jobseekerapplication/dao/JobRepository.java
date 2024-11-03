package com.luv2code.pbl4.jobseekerapplication.dao;

import com.luv2code.pbl4.jobseekerapplication.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JobRepository extends JpaRepository<Job, Integer> {
    public Job findByJobUrl(String jobUrl);

//    @Query("SELECT j FROM Job j " +
//            "JOIN j.locations l " +
//            "JOIN j.industries i " +
//            "WHERE (l.locationId = :locationId) " +
//            "AND (i.industryId = :industryId)")
//    List<Job> findByLocationAndIndustry(@Param("locationId") Integer locationId,
//                                        @Param("industryId") Integer industryId);
//
    @Query("SELECT j FROM Job j " +
            "JOIN j.locations l " +
            "JOIN j.industries i " +
            "WHERE l.locationId = :locationId " +
            "AND i.industryId = :industryId")
    List<Job> findByLocationAndIndustry(@Param("industryId") Integer industryId,@Param("locationId") Integer locationId);



    @Query("SELECT j FROM Job j " +
            "JOIN j.locations l " +
            "WHERE (l.locationId = :locationId) ")
    public List<Job> findByLocationId(Integer locationId);

    @Query("SELECT j FROM Job j " +
            "JOIN j.industries i " +
            "WHERE (i.industryId = :industryId) ")
    public List<Job> findByIndustryId(Integer industryId);

    @Query("select j from Job j " +
            "where j.minSalary <= :salary and j.maxSalary >= :salary")
    public List<Job> findBySalary(Double salary);

    // Search








}
