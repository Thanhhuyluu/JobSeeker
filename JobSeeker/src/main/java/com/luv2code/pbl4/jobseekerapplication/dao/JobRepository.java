package com.luv2code.pbl4.jobseekerapplication.dao;

import com.luv2code.pbl4.jobseekerapplication.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job, Integer> {
}
