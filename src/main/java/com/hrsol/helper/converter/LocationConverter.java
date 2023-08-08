package com.hrsol.helper.converter;

import com.hrsol.helper.entity.Location;
import com.hrsol.helper.model.dto.LocationDTO;

public class LocationConverter {

    public static LocationDTO locationToLocationDTO(Location location) {
        return LocationDTO.builder()
                .id(location.getId())
                .city(location.getCity())
                .country(location.getCountry())
                .build();
    }

    public static Location locationDTOToLocation(LocationDTO locationDTO) {
        return Location.builder()
                .id(locationDTO.getId())
                .city(locationDTO.getCity())
                .country(locationDTO.getCountry())
                .build();
    }

}
