package com.hrsol.helper.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApproveLetter {

    @NotNull(message = "Id can't be null!")
    @Min(value = 0, message = "Value can't be less than 0")
    private Long id;

}
