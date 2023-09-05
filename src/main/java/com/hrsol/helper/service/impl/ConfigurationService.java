package com.hrsol.helper.service.impl;

import com.hrsol.helper.converter.ConfigurationConverter;
import com.hrsol.helper.entity.Configuration;
import com.hrsol.helper.entity.User;
import com.hrsol.helper.model.Places;
import com.hrsol.helper.model.dto.ConfigurationDTO;
import com.hrsol.helper.repository.ConfigurationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ConfigurationService {

    private final ConfigurationRepository configurationRepository;
    private final LocationService locationService;
    private final UserService userService;

    public ConfigurationService(ConfigurationRepository configurationRepository,
                                LocationService locationService,
                                UserService userService) {
        this.configurationRepository = configurationRepository;
        this.locationService = locationService;
        this.userService = userService;
    }

    public List<ConfigurationDTO> getDTOSByUser(String username) {
        User user = userService.findByUsername(username);
        return configurationRepository.findByUser(user)
                .stream()
                .map(ConfigurationConverter::configurationToDTO)
                .toList();
    }

    public List<Configuration> getEntitiesByUser(String username) {
        return configurationRepository.findByUser(userService.findByUsername(username));
    }

    @Transactional
    public void update(Places places, String username) {
        User user = userService.findByUsername(username);
        configurationRepository.deleteByUser(user);
        List<Configuration> configurations = places.getCities()
                .stream()
                .map(city -> Configuration.builder()
                        .location(locationService.getLocationByCity(city))
                        .user(user)
                        .build())
                .toList();
        configurationRepository.saveAll(configurations);
    }
}
