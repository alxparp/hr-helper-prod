package com.hrsol.helper.converter;

import com.hrsol.helper.entity.LetterStatus;
import com.hrsol.helper.model.dto.LetterStatusDTO;

public class LetterStatusConverter {

    public static LetterStatusDTO letterStatusToDTO(LetterStatus letterStatus) {
        return LetterStatusDTO.builder()
                .id(letterStatus.getId())
                .type(letterStatus.getType())
                .build();
    }

    public static LetterStatus DTOToLetterStatus(LetterStatusDTO letterStatusDTO) {
        return LetterStatus.builder()
                .id(letterStatusDTO.getId())
                .type(letterStatusDTO.getType())
                .build();
    }

}
