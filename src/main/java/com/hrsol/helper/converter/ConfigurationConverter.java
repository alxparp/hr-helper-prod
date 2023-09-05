package com.hrsol.helper.converter;

import com.hrsol.helper.entity.Configuration;
import com.hrsol.helper.model.dto.ConfigurationDTO;

public class ConfigurationConverter {

    public static ConfigurationDTO configurationToDTO(Configuration configuration) {
        return ConfigurationDTO.builder()
                .id(configuration.getId())
                .location(configuration.getLocation().getCity())
                .build();
    }

    public static Configuration DTOToConfiguration(ConfigurationDTO configurationDTO) {
        return Configuration.builder()
                .id(configurationDTO.getId())
                .build();
    }

}
