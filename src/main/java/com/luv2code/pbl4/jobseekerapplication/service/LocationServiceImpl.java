package com.luv2code.pbl4.jobseekerapplication.service;

import com.luv2code.pbl4.jobseekerapplication.dao.LocationRepository;
import com.luv2code.pbl4.jobseekerapplication.dao.SiteLocationCodeRepository;
import com.luv2code.pbl4.jobseekerapplication.entity.Location;
import com.luv2code.pbl4.jobseekerapplication.entity.SiteLocationCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LocationServiceImpl implements LocationService {

    private final SiteLocationCodeRepository siteLocationCodeRepository;
    private LocationRepository locationRepository;

    @Autowired
    LocationServiceImpl(LocationRepository locationRepository, SiteLocationCodeRepository siteLocationCodeRepository) {
        this.locationRepository = locationRepository;
        this.siteLocationCodeRepository = siteLocationCodeRepository;
    }

    @Override
    public List<Location> findAll() {
        return locationRepository.findAll();
    }

    @Override
    public Location findById(int id) {
        Location location = null;
        Optional<Location> optionalLocation = locationRepository.findById(id);
        if (optionalLocation.isPresent()) {
            location = optionalLocation.get();
        }
        return location;
    }

    @Override
    public Location save(Location theLocation) {
        return locationRepository.save(theLocation);
    }

    @Override
    public void deleteById(int id) {
        locationRepository.deleteById(id);
    }

    @Override
    public SiteLocationCode getLocationCodeByLocationIdAndSourceId(int locationId, int sourceId) {
        return siteLocationCodeRepository.findByLocationIdAndSourceId(locationId, sourceId);
    }

    @Override
    public SiteLocationCode findBySiteLocationCodeAndSourceId(String location, int sourceId) {
        return siteLocationCodeRepository.findBySiteLocationCodeAndSourceId(location, sourceId);
    }
}
