package com.hrsol.helper.model;

import com.hrsol.helper.entity.Country;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class LocationDTO {

    private Long id;
    private String city;
    private Country country;

}
