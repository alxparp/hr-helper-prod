package com.hrsol.helper.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LetterDTO {

//    @NotNull(message = "Id can't be null!")
//    @Min(value = 0, message = "Value can't be less than 0")
    private Long id;
    @NotEmpty(message = "Name should not be empty")
    private String name;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Date should not be empty")
    private Date dueDate;
    private String city;
    private String letterStatus;
    private String username;
    private String letterType;
    private String templateType;

}
