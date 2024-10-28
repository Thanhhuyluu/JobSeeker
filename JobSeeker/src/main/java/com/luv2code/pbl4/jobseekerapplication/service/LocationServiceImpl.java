package com.luv2code.pbl4.jobseekerapplication.service;

import com.luv2code.pbl4.jobseekerapplication.dao.LocationRepository;
import com.luv2code.pbl4.jobseekerapplication.entity.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LocationServiceImpl implements LocationService {

    private LocationRepository locationRepository;

    @Autowired
    public LocationServiceImpl(LocationRepository theLocationRepository) { this.locationRepository = theLocationRepository; }


    @Override
    public List<Location> findAll() {
        return locationRepository.findAll();
    }

    @Override
    public Location findById(int id) {
        Optional<Location> result = locationRepository.findById(id);

        Location theLocation = null;
        if (result.isPresent()) {
            theLocation = result.get();
        }else {
            throw new RuntimeException("Location not found");
        }
        return theLocation;
    }

    @Override
    public Location save(Location TheLocation) {
        return locationRepository.save(TheLocation);
    }

    @Override
    public void deleteById(int id) { locationRepository.deleteById(id); }

}

