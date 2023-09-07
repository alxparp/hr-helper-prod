package com.hrsol.helper.service.impl;

import com.hrsol.helper.DummyObjects;
import com.hrsol.helper.converter.LetterConverter;
import com.hrsol.helper.entity.*;
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
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

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
    private List<LetterDTO> letterDTOSExpected;
    private LetterStatus letterStatus;
    private User user;
    private TemplateType templateType;

    @BeforeEach
    void setUp() {
        letterService = new LetterService(
                letterRepository, letterTypeService, letterStatusService, userService, templateTypeService);
        letterType = DummyObjects.getLetterType();
        letter = DummyObjects.getLetter();
        letterDTOSExpected = List.of(LetterConverter.LetterToDTO(letter));
        letterStatus = DummyObjects.getLetterStatus();
        user = DummyObjects.getUser();
        templateType = DummyObjects.getTemplateType();
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
    void findByLetterTypeAndUsername_LocationIn() {

    }

    @Test
    void getSizeByLetterTypeAndUsername_LocationIn() {

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

    @Test
    void save() {
        LetterDTO letterDTO = LetterConverter.LetterToDTO(letter);
        when(letterStatusService.findByType(letterDTO.getLetterStatus())).thenReturn(letterStatus);
        when(userService.findByUsername(letterDTO.getUsername())).thenReturn(letter.getUsername());
        when(letterTypeService.findByType(letterDTO.getLetterType())).thenReturn(letterType);
        when(templateTypeService.findByType(letterDTO.getTemplateType())).thenReturn(templateType);
        when(letterRepository.save(letter)).thenReturn(letter);

        LetterDTO letterDTOActual = letterService.save(letterDTO);

        Assertions.assertEquals(letterDTO, letterDTOActual);
    }

    @Test
    void findById() {
        // given
        LetterDTO letterDTOExpected = LetterConverter.LetterToDTO(letter);
        when(letterRepository.findById(letter.getId())).thenReturn(Optional.of(letter));

        // when
        LetterDTO letterDTOActual = letterService.findById(letter.getId());

        // then
        Assertions.assertEquals(letterDTOExpected, letterDTOActual);
        assertThrows(NoSuchElementException.class, () -> {
            letterService.findById(2L);
        });
    }
}