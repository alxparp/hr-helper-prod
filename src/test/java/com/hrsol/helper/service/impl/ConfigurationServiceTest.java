package com.hrsol.helper.service.impl;

import com.hrsol.helper.DummyObjects;
import com.hrsol.helper.converter.ConfigurationConverter;
import com.hrsol.helper.entity.Configuration;
import com.hrsol.helper.entity.User;
import com.hrsol.helper.model.Places;
import com.hrsol.helper.model.dto.ConfigurationDTO;
import com.hrsol.helper.repository.ConfigurationRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConfigurationServiceTest {

    @Mock
    private ConfigurationRepository configurationRepository;
    @Mock
    private LocationService locationService;
    @Mock
    private UserService userService;
    private ConfigurationService configurationService;
    private Configuration configuration;
    private User user;

    @BeforeEach
    void setUp() {
        configurationService = new ConfigurationService(configurationRepository, locationService, userService);
        configuration = DummyObjects.getConfiguration();
        user = configuration.getUser();
    }

    @Test
    void getDTOSByUser() {
        List<ConfigurationDTO> configurationDTOSExpected = List.of(ConfigurationConverter.configurationToDTO(configuration));
        when(userService.findByUsername(user.getUsername())).thenReturn(user);
        when(configurationRepository.findByUser(user)).thenReturn(List.of(configuration));

        List<ConfigurationDTO> configurationDTOSActual = configurationService.getDTOSByUser(user.getUsername());

        Assertions.assertEquals(configurationDTOSExpected, configurationDTOSActual);

    }

    @Test
    void getEntitiesByUser() {
        List<Configuration> configurationsExpected = List.of(configuration);
        when(userService.findByUsername(user.getUsername())).thenReturn(user);
        when(configurationRepository.findByUser(user)).thenReturn(List.of(configuration));

        List<Configuration> configurationsActual = configurationService.getEntitiesByUser(user.getUsername());

        Assertions.assertEquals(configurationsExpected, configurationsActual);
    }

    @Test
    void update() {
        var places = new Places(null, null, null, List.of(user.getLocation().getCity()));
        var valueCapture = ArgumentCaptor.forClass(Iterable.class);
        configuration.setId(null);
        when(userService.findByUsername(user.getUsername())).thenReturn(user);
        doNothing().when(configurationRepository).deleteByUser(user);
        when(locationService.getLocationByCity(user.getLocation().getCity())).thenReturn(user.getLocation());

        configurationService.update(places, user.getUsername());

        verify(configurationRepository).saveAll(valueCapture.capture());
        Assertions.assertEquals(List.of(configuration), valueCapture.getValue());

    }
}