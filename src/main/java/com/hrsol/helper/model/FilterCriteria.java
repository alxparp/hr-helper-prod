package com.hrsol.helper.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Optional;

@Data
@AllArgsConstructor
public class FilterCriteria {

    @NotNull(message = "Id can't be null!")
    @Min(value = 0, message = "Value can't be less than 0")
    private Long id;
    private Optional<Integer> page;
    private Optional<Integer> size;

}
