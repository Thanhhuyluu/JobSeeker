package com.luv2code.pbl4.jobseekerapplication.service;

import com.luv2code.pbl4.jobseekerapplication.entity.Job;

import java.util.List;

public interface JobService {

    List<Job> findAll();

    Job findById(int id);

    Job save(Job theJob);

    void deleteById(int id);

}
