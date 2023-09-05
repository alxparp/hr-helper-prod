package com.hrsol.helper.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Optional;

@Getter
@Setter
public class Places extends FilterCriteria {

    private List<String> cities;

    public Places(Long id, Optional<Integer> page, Optional<Integer> size, List<String> cities) {
        super(id, page, size);
        this.cities = cities;
    }

}
