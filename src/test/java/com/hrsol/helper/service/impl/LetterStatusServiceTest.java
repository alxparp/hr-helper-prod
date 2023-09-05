package com.hrsol.helper.service.impl;

import com.hrsol.helper.DummyObjects;
import com.hrsol.helper.converter.LetterStatusConverter;
import com.hrsol.helper.entity.LetterStatus;
import com.hrsol.helper.model.dto.LetterStatusDTO;
import com.hrsol.helper.repository.LetterStatusRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LetterStatusServiceTest {

    @Mock
    private LetterStatusRepository letterStatusRepository;
    private LetterStatusService letterStatusService;
    private LetterStatus letterStatus;

    @BeforeEach
    void setUp() {
        letterStatusService = new LetterStatusService(letterStatusRepository);
        letterStatus = DummyObjects.getLetterStatus();
    }

    @Test
    void getAll() {
        List<LetterStatusDTO> letterStatusDTOSExpected = List.of(LetterStatusConverter.letterStatusToDTO(letterStatus));
        when(letterStatusRepository.findAll()).thenReturn(List.of(letterStatus));

        List<LetterStatusDTO> letterStatusDTOSActual = letterStatusService.getAll();

        Assertions.assertEquals(letterStatusDTOSExpected, letterStatusDTOSActual);
    }

    @Test
    void findByType() {
        when(letterStatusRepository.findByType(letterStatus.getType())).thenReturn(letterStatus);

        LetterStatus letterStatusActual = letterStatusService.findByType(letterStatus.getType());

        Assertions.assertEquals(letterStatus, letterStatusActual);
    }
}