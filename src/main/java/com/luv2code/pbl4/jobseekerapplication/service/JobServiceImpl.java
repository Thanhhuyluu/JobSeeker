package com.luv2code.pbl4.jobseekerapplication.service;

import com.luv2code.pbl4.jobseekerapplication.dao.JobRepository;
import com.luv2code.pbl4.jobseekerapplication.entity.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JobServiceImpl implements JobService {

    private JobRepository jobRepository;

    @Autowired
    public JobServiceImpl(JobRepository theJobRepository) {
            this.jobRepository = theJobRepository;
    }

    @Override
    public List<Job> findAll() {
        return jobRepository.findAll();
    }

    @Override
    public Job findById(int id) {
        Optional<Job> result = jobRepository.findById(id);

        Job theJob = null;

        if(result.isPresent()) {
            theJob = result.get();
        }else{
            throw new RuntimeException("Job not found");
        }
        return theJob;
    }

    @Override
    public Job save(Job theJob) {
        return jobRepository.save(theJob);
    }

    @Override
    public void deleteById(int theId) {
        jobRepository.deleteById(theId);
    }
}
