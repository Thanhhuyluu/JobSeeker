package com.luv2code.pbl4.jobseekerapplication.service;

import com.luv2code.pbl4.jobseekerapplication.dao.JobRepository;
import com.luv2code.pbl4.jobseekerapplication.entity.Job;
import com.luv2code.pbl4.jobseekerapplication.utils.CareerLevelEnum;
import com.luv2code.pbl4.jobseekerapplication.utils.JobTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    @Override
    public Job findByJobUrl(String jobUrl) {
        return jobRepository.findByJobUrl(jobUrl);
    }

    @Override
    public Page<Job> findPaginated(Pageable pageable) {
        return jobRepository.findAll(pageable);
    }

    @Override
    public List<Job> findByJobIndustryIdAndLocationId(int industryId, int locationId) {
        return jobRepository.findByLocationAndIndustry(industryId, locationId);

    }

    @Override
    public List<Job> findByLocationId(int locationId) {
        return jobRepository.findByLocationId(locationId);
    }

    @Override
    public List<Job> findByIndustryId(int industryId) {
        return jobRepository.findByIndustryId(industryId);
    }
    public List<Job> getListByPage(List<Job> list, int start, int end){
        ArrayList<Job> arr = new ArrayList<>();
        for(int i = start; i< end; i++) {
            arr.add(list.get(i));
        }
        return arr;
    }

    @Override
//    public List<Job> findByFilters(List<Job> rawList,List<String> careerLevel, List<String> experienceLevel, Integer minSalary, List<String> jobTypes) {
//        List<Job> result = new ArrayList<>();
//
//        for (Job theJob : rawList) {
//            if(jobTypes.contains(JobTypeEnum.getDisplayNameByDatabaseName(theJob.getJobType()))
//            && ( careerLevel.contains(CareerLevelEnum.getDisplayNameByDatabaseName(theJob.getCareerLevel())) || careerLevel.isEmpty())) {
//
//                result.add(theJob);
//
//            }
//        }
//        return result;
//    }

    public List<Job> findByFilters(List<Job> rawList, List<String> careerLevel, Integer experienceLevel, Integer minSalary, List<String> jobTypes) {
        List<Job> filteredJobs = new ArrayList<>();

        for (Job job : rawList) {
            boolean careerMatch = (careerLevel == null || careerLevel.isEmpty() || careerLevel.contains(CareerLevelEnum.getDisplayNameByDatabaseName(job.getCareerLevel())) || careerLevel.isEmpty());


            boolean experienceMatch = (experienceLevel == null || experienceLevel.equals(normalizeExperienceLevel(job.getExperienceLevel())));


            boolean jobTypeMatch = (jobTypes == null || jobTypes.isEmpty() || jobTypes.contains(JobTypeEnum.getDisplayNameByDatabaseName(job.getJobType())));


            boolean salaryMatch = true;
            if (minSalary != null) {
                String salaryRange = job.getSalaryRange();
                int salary = job.getMinSalary();
                salaryMatch = salary >= minSalary;
            }


            if (careerMatch && experienceMatch && jobTypeMatch && salaryMatch) {
                filteredJobs.add(job);
            }
        }

        return filteredJobs;
    }


    public Integer normalizeExperienceLevel(String experienceLevel) {
        if (experienceLevel.equalsIgnoreCase("Không yêu cầu kinh nghiệm")) {
            return 0;
        } else if (experienceLevel.equalsIgnoreCase("Dưới 1 năm")) {
            return 0;
        } else if (experienceLevel.endsWith("năm")) {
            String number = experienceLevel.replace(" năm", "").trim();

            if (experienceLevel.startsWith("Trên")) {
                try {
                    return Integer.parseInt(number) + 1;
                } catch (NumberFormatException e) {
                    return null;
                }
            } else {
                try {
                    return Integer.parseInt(number);
                } catch (NumberFormatException e) {
                    return null;
                }
            }
        }
        return null;
    }



}
