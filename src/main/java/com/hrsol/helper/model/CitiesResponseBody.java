package com.hrsol.helper.model;

import lombok.Data;

import java.util.List;

@Data
public class CitiesResponseBody {

    private String msg;
    private List<String> cities;

}
