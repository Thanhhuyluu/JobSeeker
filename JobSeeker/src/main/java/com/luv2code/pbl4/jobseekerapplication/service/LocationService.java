package com.luv2code.pbl4.jobseekerapplication.service;


import com.luv2code.pbl4.jobseekerapplication.entity.Location;

import java.util.List;

public interface LocationService {
    List<Location> findAll();
    Location findById(int id);
    Location save(Location TheLocation);
    void deleteById(int id);
}
