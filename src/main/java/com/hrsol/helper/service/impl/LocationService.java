package com.hrsol.helper.service.impl;

import com.hrsol.helper.converter.LocationConverter;
import com.hrsol.helper.entity.Location;
import com.hrsol.helper.model.dto.LocationDTO;
import com.hrsol.helper.repository.LocationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationService {

    private final LocationRepository locationRepository;

    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public List<LocationDTO> getAll() {
        return locationRepository.findAll()
                .stream()
                .map(LocationConverter::locationToLocationDTO)
                .toList();
    }

    public Location getLocationByCity(String city) {
        return locationRepository.findByCity(city);
    }
}
