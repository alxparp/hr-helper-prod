package com.hrsol.helper.service;

import com.hrsol.helper.DummyObjects;
import com.hrsol.helper.converter.LocationConverter;
import com.hrsol.helper.entity.Location;
import com.hrsol.helper.model.dto.LocationDTO;
import com.hrsol.helper.repository.LocationRepository;
import com.hrsol.helper.service.impl.LocationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LocationServiceTest {

    @Mock
    private LocationRepository locationRepository;
    private LocationService locationService;
    private Location location;

    @BeforeEach
    void setUp() {
        locationService = new LocationService(locationRepository);
        location = DummyObjects.getLocation();
    }

    @Test
    void getAll() {
        // given
        when(locationRepository.findAll()).thenReturn(List.of(location));

        // when
        List<LocationDTO> locationDTOSActual = locationService.getAll();

        // then
        Assertions.assertEquals(List.of(LocationConverter.locationToLocationDTO(location)), locationDTOSActual);

    }

    @Test
    void getLocationByCity() {
        // given
        when(locationRepository.findByCity(location.getCity())).thenReturn(location);

        // when
        Location locationActual = locationService.getLocationByCity(location.getCity());

        // then
        Assertions.assertEquals(location, locationActual);

    }
}