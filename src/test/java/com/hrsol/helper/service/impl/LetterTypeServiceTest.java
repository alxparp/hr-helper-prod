package com.hrsol.helper.service.impl;

import com.hrsol.helper.DummyObjects;
import com.hrsol.helper.converter.LetterTypeConverter;
import com.hrsol.helper.entity.LetterType;
import com.hrsol.helper.model.dto.LetterTypeDTO;
import com.hrsol.helper.repository.LetterTypeRepository;
import com.hrsol.helper.service.impl.LetterTypeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LetterTypeServiceTest {

    @Mock
    private LetterTypeRepository letterTypeRepository;
    private LetterTypeService letterTypeService;
    private LetterType letterType;

    @BeforeEach
    void setUp() {
        letterTypeService = new LetterTypeService(letterTypeRepository);
        letterType = DummyObjects.getLetterType();
    }

    @Test
    void findAll() {
        // given
        List<LetterTypeDTO> letterTypeDTOSExpected = List.of(LetterTypeConverter.LetterTypeToDTO(letterType));
        when(letterTypeRepository.findAll()).thenReturn(List.of(letterType));

        // when
        List<LetterTypeDTO> letterTypeDTOSActual = letterTypeService.findAll();

        // then
        Assertions.assertEquals(letterTypeDTOSExpected, letterTypeDTOSActual);
    }

    @Test
    void findById() {
        // given
        when(letterTypeRepository.findById(letterType.getId())).thenReturn(Optional.of(letterType));

        // when
        LetterType letterTypeActual = letterTypeService.findById(letterType.getId());

        // then
        Assertions.assertEquals(letterType, letterTypeActual);
        assertThrows(NoSuchElementException.class, () -> {
            letterTypeService.findById(2L);
        });
    }

    @Test
    void containsId() {
        when(letterTypeRepository.existsById(letterType.getId())).thenReturn(true);

        boolean isContainsActual = letterTypeService.containsId(letterType.getId());

        Assertions.assertTrue(isContainsActual);
    }

    @Test
    void findByType() {
        when(letterTypeRepository.findByType(letterType.getType())).thenReturn(letterType);

        LetterType letterTypeActual = letterTypeService.findByType(letterType.getType());

        Assertions.assertEquals(letterType, letterTypeActual);
    }
}