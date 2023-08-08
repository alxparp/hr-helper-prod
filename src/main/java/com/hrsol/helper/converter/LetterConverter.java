package com.hrsol.helper.converter;

import com.hrsol.helper.entity.Letter;
import com.hrsol.helper.model.dto.LetterDTO;

import java.sql.Date;

public class LetterConverter {

    public static LetterDTO LetterToDTO(Letter letter) {
        return LetterDTO.builder()
                .id(letter.getId())
                .name(letter.getName())
                .dueDate(letter.getDueDate())
                .city(letter.getUsername().getLocation().getCity())
                .letterStatus(letter.getLetterStatus().getType())
                .username(letter.getUsername().getUsername())
                .letterType(letter.getLetterType().getType())
                .templateType(letter.getTemplateType().getType())
                .build();
    }

    public static Letter DTOToLetter(LetterDTO letterDTO) {
        Letter letter = new Letter();
        letter.setId(letterDTO.getId());
        letter.setName(letterDTO.getName());
        if (letterDTO.getDueDate() != null)
            letter.setDueDate(new Date(letterDTO.getDueDate().getTime()));
        return letter;
    }

}
