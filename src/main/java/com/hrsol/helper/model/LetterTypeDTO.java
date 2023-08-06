package com.hrsol.helper.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class LetterTypeDTO {

    private Long id;
    private String type;

}
