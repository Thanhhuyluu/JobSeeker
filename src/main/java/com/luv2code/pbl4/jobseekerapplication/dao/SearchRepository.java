package com.luv2code.pbl4.jobseekerapplication.dao;


import com.luv2code.pbl4.jobseekerapplication.dto.ListResult;
import com.luv2code.pbl4.jobseekerapplication.entity.Job;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Repository
public class SearchRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public ListResult<Job> getAllJobsWithSortByColumnAndSearch(int pageNo,
                                                               int pageSize,
                                                               String search,
                                                               String sortBy,
                                                               int industryId,
                                                               int locationId, List<String> jobTypes,
                                                               String experienceLevel,
                                                               List<String> careerLevels,
                                                               Double salary) {

        StringBuilder sqlQuery = new StringBuilder("SELECT j FROM Job j  WHERE 1 = 1 ");

        sqlQuery.append(" AND (?1 IN (SELECT i.industryId FROM j.industries i) OR ?1 = -1)");
        sqlQuery.append(" AND (?2 IN (SELECT l.locationId FROM j.locations l) OR ?2 = -1)");



        int index = 3;
        if(StringUtils.hasLength(search)) {

            sqlQuery.append(" AND ( LOWER(j.jobTitle) LIKE LOWER(?3)");
            sqlQuery.append(" OR LOWER(j.jobDescription) LIKE LOWER(?4) )");
            index += 2;

        }


        if(jobTypes == null) jobTypes = new ArrayList<>();

        if ( !jobTypes.isEmpty()) {
            sqlQuery.append(" AND j.jobType IN (");
            for (int i = 0; i < jobTypes.size(); i++) {
                if (i == jobTypes.size() - 1) {
                    sqlQuery.append(" ?").append(index);
                } else {
                    sqlQuery.append(" ?").append(index).append(",");
                }
                index++;
            }
            sqlQuery.append(")");
        }




        if (StringUtils.hasLength(experienceLevel)) {
            if (experienceLevel.matches("\\d+")) {
                sqlQuery.append(" AND (j.experienceLevel = ?").append(index).append(")");

            } else if (experienceLevel.matches("\\d+ \\d+")) {
                sqlQuery.append(" AND (j.experienceLevel >= ?").append(index);
                index++;
                sqlQuery.append(" AND j.experienceLevel <= ?").append(index).append(")");
            } else if (experienceLevel.matches(">\\d+")) {
                sqlQuery.append(" AND (j.experienceLevel >= ?").append(index).append(")");
            }
            index++;
        }

        if (careerLevels != null && !careerLevels.isEmpty()) {
            sqlQuery.append(" AND j.careerLevel IN (");
            for (int i = 0; i < careerLevels.size(); i++) {
                if (i == careerLevels.size() - 1) {
                    sqlQuery.append(" ?").append(index);
                } else {
                    sqlQuery.append(" ?").append(index).append(",");
                }
                index++;
            }
            sqlQuery.append(")");
        }

        if (salary != null) {
            sqlQuery.append(" AND ((j.salaryCurrency = 'VND' AND j.salary >= ?").append(index).append(")");
            sqlQuery.append(" OR (j.salaryCurrency = 'USD' AND j.salary * 23000 >= ?").append(index).append("))");
            index++;
        }

        if (StringUtils.hasLength(sortBy)) {
            Pattern pattern = Pattern.compile("(\\w+?):(asc|desc)", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(sortBy);
            if (matcher.matches()) {
                sqlQuery.append(" ORDER BY j.").append(matcher.group(1)).append(" ").append(matcher.group(2).toUpperCase());
            }
        }

        System.out.println(sqlQuery.toString());

        Query selectQuery = entityManager.createQuery(sqlQuery.toString());

        if(pageNo > 0){
            selectQuery.setFirstResult((pageNo-1) * pageSize);
        }
        selectQuery.setMaxResults(pageSize);


        selectQuery.setParameter(1, industryId);
        selectQuery.setParameter(2, locationId);

        int paramIndex = 3;
        if(StringUtils.hasLength(search)) {

            selectQuery.setParameter(3, "%" + search + "%");
            selectQuery.setParameter(4, "%" + search + "%");
            paramIndex += 5;

        }



        if (!jobTypes.isEmpty()) {
            if(StringUtils.hasLength(search)) {
                for (int i = 0; i < jobTypes.size(); i++) {
                    selectQuery.setParameter(paramIndex, jobTypes.get(i));
                    paramIndex++;
                }
            } else {
                for (int i = 0; i < jobTypes.size(); i++) {
                    selectQuery.setParameter(paramIndex, jobTypes.get(i));
                    paramIndex++;
                }
            }
        }

        if (StringUtils.hasLength(experienceLevel)) {
            if (experienceLevel.matches("\\d+")) {
                selectQuery.setParameter(paramIndex, Integer.parseInt(experienceLevel));
            } else if (experienceLevel.matches("\\d+ \\d+")) {
                String[] parts = experienceLevel.split(" ");
                selectQuery.setParameter(paramIndex, Integer.parseInt(parts[0]));
                paramIndex++;
                selectQuery.setParameter(paramIndex, Integer.parseInt(parts[1]));
            } else if (experienceLevel.matches(">\\d+")) {
                selectQuery.setParameter(paramIndex, Integer.parseInt(experienceLevel.substring(1)));
            }
            paramIndex++;
        }

        if (careerLevels != null && !careerLevels.isEmpty()) {
            if (StringUtils.hasLength(experienceLevel)) {
                for (int i = 0; i < careerLevels.size(); i++) {
                    selectQuery.setParameter(paramIndex, careerLevels.get(i));
                    paramIndex++;
                }
            } else {
                for (int i = 0; i < careerLevels.size(); i++) {
                    selectQuery.setParameter(paramIndex, careerLevels.get(i));
                    paramIndex++;
                }
            }
        }
        if (salary != null) {
            selectQuery.setParameter(paramIndex, salary);
        }


        List<Job> jobs = selectQuery.getResultList();


        // get total items


        StringBuilder countQuery = new StringBuilder("SELECT COUNT(*) FROM Job j WHERE 1 = 1 ");

        countQuery.append(" AND (?1 IN (SELECT i.industryId FROM j.industries i) OR ?1 = -1)");
        countQuery.append(" AND (?2 IN (SELECT l.locationId FROM j.locations l) OR ?2 = -1)");
        int countIndex = 3;
        if(StringUtils.hasLength(search)) {

            countQuery.append(" AND ( LOWER(j.jobTitle) LIKE LOWER(?3)");
            countQuery.append(" OR LOWER(j.jobDescription) LIKE LOWER(?4) )");
            countIndex += 2;
        }


        if (jobTypes != null && !jobTypes.isEmpty()) {
            countQuery.append(" AND j.jobType IN (");
            for (int i = 0; i < jobTypes.size(); i++) {
                if (i == jobTypes.size() - 1) {
                    countQuery.append(" ?").append(countIndex);
                } else {
                    countQuery.append(" ?").append(countIndex).append(",");
                }
                countIndex++;
            }
            countQuery.append(")");
        }


        if (StringUtils.hasLength(experienceLevel)) {
            if (experienceLevel.matches("\\d+")) {
                countQuery.append(" AND (j.experienceLevel = ?").append(countIndex).append(")");
            } else if (experienceLevel.matches("\\d+ \\d+")) {
                countQuery.append(" AND (j.experienceLevel >= ?").append(countIndex);
                countIndex++;
                countQuery.append(" AND j.experienceLevel <= ?").append(countIndex).append(")");
            } else if (experienceLevel.matches(">\\d+")) {
                countQuery.append(" AND (j.experienceLevel >= ?").append(countIndex).append(")");
            }
            countIndex++;
        }
        if (careerLevels != null && !careerLevels.isEmpty()) {
            countQuery.append(" AND j.careerLevel IN (");
            for (int i = 0; i < careerLevels.size(); i++) {
                if (i == careerLevels.size() - 1) {
                    countQuery.append(" ?").append(countIndex);
                } else {
                    countQuery.append(" ?").append(countIndex).append(",");
                }
                countIndex++;
            }
            countQuery.append(")");
        }

        if (salary != null) {
            countQuery.append(" AND ((j.salaryCurrency = 'VND' AND j.salary >= ?").append(countIndex).append(")");
            countQuery.append(" OR (j.salaryCurrency = 'USD' AND j.salary * 23000 >= ?").append(countIndex).append("))");
        }

        Query countSelectQuery = entityManager.createQuery(countQuery.toString());

        countSelectQuery.setParameter(1, industryId);
        countSelectQuery.setParameter(2, locationId);

        int countParamIndex = 3;

        if(StringUtils.hasLength(search)) {

            countSelectQuery.setParameter(3, "%" + search + "%");
            countSelectQuery.setParameter(4, "%" + search + "%");
            countParamIndex += 2;
        }

        if (jobTypes != null && !jobTypes.isEmpty()) {
            if(StringUtils.hasLength(search)) {
                for (int i = 0; i < jobTypes.size(); i++) {
                    countSelectQuery.setParameter(countParamIndex, jobTypes.get(i));
                    countParamIndex++;
                }
            } else {
                for (int i = 0; i < jobTypes.size(); i++) {
                    countSelectQuery.setParameter(countParamIndex, jobTypes.get(i));
                    countParamIndex++;
                }
            }
        }

        if (StringUtils.hasLength(experienceLevel)) {
            if (experienceLevel.matches("\\d+")) {
                countSelectQuery.setParameter(countParamIndex, Integer.parseInt(experienceLevel));
            } else if (experienceLevel.matches("\\d+ \\d+")) {
                String[] parts = experienceLevel.split(" ");
                countSelectQuery.setParameter(countParamIndex, Integer.parseInt(parts[0]));
                countParamIndex++;
                countSelectQuery.setParameter(countParamIndex, Integer.parseInt(parts[1]));
            } else if (experienceLevel.matches(">\\d+")) {
                countSelectQuery.setParameter(countParamIndex, Integer.parseInt(experienceLevel.substring(1)));
            }
            countParamIndex++;
        }

        if (careerLevels != null && !careerLevels.isEmpty()) {
            if (StringUtils.hasLength(experienceLevel)) {
                for (int i = 0; i < careerLevels.size(); i++) {
                    countSelectQuery.setParameter(countParamIndex, careerLevels.get(i));
                    countParamIndex++;
                }
            } else {
                for (int i = 0; i < careerLevels.size(); i++) {
                    countSelectQuery.setParameter(countParamIndex, careerLevels.get(i));
                    countParamIndex++;
                }
            }
        }

        if (salary != null) {
            countSelectQuery.setParameter(countParamIndex, salary);
        }

        Long totalItems = (Long) countSelectQuery.getSingleResult();

        ListResult<Job> listResult = new ListResult<Job>(jobs, totalItems);

        System.out.println(jobs);
        System.out.println(totalItems);

        System.out.println(sqlQuery.toString());
        System.out.println(countQuery.toString());


        return listResult;
    }

}
