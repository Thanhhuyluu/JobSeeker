package com.luv2code.pbl4.jobseekerapplication.service;

import com.luv2code.pbl4.jobseekerapplication.entity.Location;
import com.luv2code.pbl4.jobseekerapplication.entity.SiteLocationCode;

import java.util.List;

public interface LocationService {
    List<Location> findAll();

    Location findById(int id);


    Location save(Location theLocation);

    void deleteById(int id);

    SiteLocationCode getLocationCodeByLocationIdAndSourceId(int locationId, int sourceId);

    SiteLocationCode findBySiteLocationCodeAndSourceId(String location, int sourceId);

}
