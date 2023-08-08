package com.hrsol.helper.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class TemplateTypeDTO {

    private Long id;
    private String type;

}
