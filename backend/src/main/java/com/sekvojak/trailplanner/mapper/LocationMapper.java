package com.sekvojak.trailplanner.mapper;

import com.sekvojak.trailplanner.dto.LocationDto;
import com.sekvojak.trailplanner.entity.Location;

public class LocationMapper {

    public static LocationDto toDto(Location location) {
        return new LocationDto(
                location.getId(),
                location.getName(),
                location.getRegion(),
                location.getCountry(),
                location.getLatitude(),
                location.getLongitude()
        );
    }

    public static Location toEntity(LocationDto dto) {
        return new Location(
                dto.getId(),
                dto.getName(),
                dto.getRegion(),
                dto.getCountry(),
                dto.getLatitude(),
                dto.getLongitude()
        );
    }
}
