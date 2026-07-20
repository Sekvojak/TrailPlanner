package com.sekvojak.trailplanner.controller;


import com.sekvojak.trailplanner.dto.LocationDto;
import com.sekvojak.trailplanner.mapper.LocationMapper;
import com.sekvojak.trailplanner.service.LocationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/locations")
public class LocationController {

    private final LocationService locationService;


    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping
    public List<LocationDto> getAllLocations() {
        return locationService.getAllLocations()
                .stream()
                .map(LocationMapper::toDto)
                .toList();
    }
}
