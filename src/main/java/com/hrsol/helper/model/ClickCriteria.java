package com.hrsol.helper.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClickCriteria {

    @NotNull(message = "Id can't be null!")
    private Long id;
    private Optional<Integer> page;
    private Optional<Integer> size;

}
