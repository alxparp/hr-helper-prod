package com.hrsol.helper.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class City {

    private Long id;
    private List<String> city;
    private Optional<Integer> page;
    private Optional<Integer> size;

}
