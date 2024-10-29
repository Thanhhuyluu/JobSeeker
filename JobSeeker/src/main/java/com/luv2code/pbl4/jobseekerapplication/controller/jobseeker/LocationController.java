package com.luv2code.pbl4.jobseekerapplication.controller.jobseeker;

import com.luv2code.pbl4.jobseekerapplication.entity.Location;
import com.luv2code.pbl4.jobseekerapplication.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/location")
public class LocationController {
    @Autowired
    private LocationService locationService;

    @GetMapping
    public List<Location> getAllLocations() {
        return locationService.findAll();
    }

    @GetMapping
    public Location getLocationById(@PathVariable int id) {
        return locationService.findById(id);
    }

    @PostMapping
    public Location createLocation(@RequestBody Location location) {
        return locationService.save(location);
    }

    @PutMapping("/{id}")
    public Location updateLocation(@PathVariable int id, @RequestBody Location locationDetails) {
        Location location = locationService.findById(id);
        if (location != null) {
            location.setName(locationDetails.getName());
            return locationService.save(location);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void deleteLocation(@PathVariable int id) {
        locationService.deleteById(id);
    }

}
