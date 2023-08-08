package com.hrsol.helper.service;

import com.hrsol.helper.DummyObjects;
import com.hrsol.helper.converter.LetterConverter;
import com.hrsol.helper.entity.Letter;
import com.hrsol.helper.entity.LetterType;
import com.hrsol.helper.model.dto.LetterDTO;
import com.hrsol.helper.repository.LetterRepository;
import com.hrsol.helper.service.impl.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LetterServiceTest {

    @Mock
    private LetterRepository letterRepository;
    @Mock
    private LetterTypeService letterTypeService;
    @Mock
    private LetterStatusService letterStatusService;
    @Mock
    private UserService userService;
    @Mock
    private TemplateTypeService templateTypeService;
    private LetterService letterService;
    private LetterType letterType;
    private Letter letter;
    List<LetterDTO> letterDTOSExpected;

    @BeforeEach
    void setUp() {
        letterService = new LetterService(
                letterRepository, letterTypeService, letterStatusService, userService, templateTypeService);
        letterType = DummyObjects.getLetterType();
        letter = DummyObjects.getLetter();
        letterDTOSExpected = List.of(LetterConverter.LetterToDTO(letter));
    }

    @Test
    void findByType() {
        // given
        when(letterRepository.findByLetterType(letterType)).thenReturn(List.of(letter));

        // when
        List<LetterDTO> letterDTOSActual = letterService.findByType(letterType);

        // then
        Assertions.assertEquals(letterDTOSExpected, letterDTOSActual);


    }

    @Test
    void getSizeByType() {
        // given
        when(letterRepository.countByLetterType(letterType)).thenReturn(5);

        // when
        Integer size = letterService.getSizeByType(letterType);

        // then
        Assertions.assertEquals(5, size);
    }

    @Test
    void findByLetterType() {
        // given
        Pageable pageable = PageRequest.of(0, 2);
        when(letterRepository.findByLetterType(letterType, pageable)).thenReturn(List.of(letter));

        // when
        List<LetterDTO> letterDTOSActual = letterService.findByLetterType(letterType, pageable);

        // then
        Assertions.assertEquals(letterDTOSExpected, letterDTOSActual);
    }

    @Test
    void approveGeneratedLetter() {
        // given
        int resultExpected = 1;
        when(letterTypeService.findById(anyLong())).thenReturn(letterType);
        when(letterRepository.approveGeneratedLetter(letterType, letter.getId())).thenReturn(resultExpected);

        // when
        int resultActual = letterService.approveGeneratedLetter(letter.getId());

        // then
        Assertions.assertEquals(resultExpected, resultActual);
    }

    @Test
    void containsId() {
        when(letterRepository.existsById(letter.getId())).thenReturn(true);

        boolean isContainsActual = letterService.containsId(letter.getId());

        Assertions.assertTrue(isContainsActual);
    }
}