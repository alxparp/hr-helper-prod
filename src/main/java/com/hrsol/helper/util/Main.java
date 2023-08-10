package com.hrsol.helper.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hrsol.helper.model.City;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws JsonProcessingException {
        List<String> cities = new ArrayList<>();
        cities.add("Odessa");
        cities.add("Kiev");

        City city = new City();
        city.setCity(cities);

        ObjectMapper mapper = new ObjectMapper();
        String result = mapper.writeValueAsString(city);
        System.out.println(result);
    }

}
