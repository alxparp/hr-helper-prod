package com.hrsol.helper.converter;

import com.hrsol.helper.entity.Letter;
import com.hrsol.helper.model.LetterDTO;

public class LetterConverter {

    public static LetterDTO LetterToDTO(Letter letter) {
        return LetterDTO.builder()
                .id(letter.getId())
                .name(letter.getName())
                .dueDate(letter.getDueDate())
                .city(letter.getUsername().getLocation().getCity())
                .letterStatus(letter.getLetterStatus().getType())
                .build();
    }

}
