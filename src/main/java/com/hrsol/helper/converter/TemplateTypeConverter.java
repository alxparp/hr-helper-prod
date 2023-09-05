package com.hrsol.helper.converter;

import com.hrsol.helper.entity.TemplateType;
import com.hrsol.helper.model.dto.TemplateTypeDTO;

public class TemplateTypeConverter {

    public static TemplateTypeDTO templateTypeToDTO(TemplateType templateType) {
        return TemplateTypeDTO.builder()
                .id(templateType.getId())
                .type(templateType.getType())
                .build();
    }

    public static TemplateType DTOToTemplateType(TemplateTypeDTO templateTypeDTO) {
        return TemplateType.builder()
                .id(templateTypeDTO.getId())
                .type(templateTypeDTO.getType())
                .build();
    }

}
