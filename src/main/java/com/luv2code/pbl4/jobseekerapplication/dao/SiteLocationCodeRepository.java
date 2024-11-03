package com.luv2code.pbl4.jobseekerapplication.dao;


import com.luv2code.pbl4.jobseekerapplication.entity.SiteLocationCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SiteLocationCodeRepository extends JpaRepository<SiteLocationCode, Integer> {
    public SiteLocationCode findByLocationIdAndSourceId(int locationId, int sourceId);
    public SiteLocationCode findBySiteLocationCodeAndSourceId(String location, int sourceId);
}
