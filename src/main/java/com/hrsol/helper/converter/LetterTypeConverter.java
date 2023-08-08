package com.hrsol.helper.converter;

import com.hrsol.helper.entity.LetterType;
import com.hrsol.helper.model.dto.LetterTypeDTO;

public class LetterTypeConverter {

    public static LetterTypeDTO LetterTypeToDTO(LetterType letterType) {
        return LetterTypeDTO.builder()
                .id(letterType.getId())
                .type(letterType.getType())
                .build();
    }


}
