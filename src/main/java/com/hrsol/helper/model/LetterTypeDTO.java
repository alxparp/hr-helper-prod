package com.hrsol.helper.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class LetterTypeDTO {

    private Long id;
    private String type;
    private List<LetterDTO> letterDTOS;

}
