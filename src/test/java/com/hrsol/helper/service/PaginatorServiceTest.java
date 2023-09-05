package com.hrsol.helper.service;

import com.google.gson.Gson;
import com.hrsol.helper.DummyObjects;
import com.hrsol.helper.converter.LetterConverter;
import com.hrsol.helper.entity.Letter;
import com.hrsol.helper.entity.LetterType;
import com.hrsol.helper.entity.User;
import com.hrsol.helper.model.LetterTypeCriteria;
import com.hrsol.helper.model.dto.LetterDTO;
import com.hrsol.helper.service.impl.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PaginatorServiceTest {

    @Mock
    private LetterService letterService;
    @Mock
    private LetterTypeService letterTypeService;
    @Mock
    private ConfigurationService configurationService;
    private PaginatorService paginatorService;
    private LetterType letterType;
    private Integer size;
    private Letter letter;
    private LetterTypeCriteria letterTypeCriteria;
    private User user;

    @BeforeEach
    void setUp() {
        letterType = DummyObjects.getLetterType();
        user = DummyObjects.getUser();
        size = 8;
        letter = DummyObjects.getLetter();
        letterTypeCriteria = DummyObjects.getClickCriteria();
        paginatorService = new PaginatorService(letterService, letterTypeService, configurationService);
    }

    @Test
    void configurePaginator() {
        // given
        int currentPage = letterTypeCriteria.getPage().orElse(1);
        int pageSize = letterTypeCriteria.getSize().orElse(2);
        List<LetterDTO> letterDTOS = new ArrayList<>();
        Gson gson = new Gson();
        for (int i = 0; i < pageSize; i++) {
            letterDTOS.add(LetterConverter.LetterToDTO(gson.fromJson(gson.toJson(letter), Letter.class)));
        }
        PageRequest request = PageRequest.of(currentPage - 1, pageSize);
        when(letterTypeService.findById(letterTypeCriteria.getId())).thenReturn(letterType);
        when(letterService.getSizeByType(letterType)).thenReturn(size);
        when(letterService.findByLetterType(letterType, request)).thenReturn(letterDTOS);
        Page<LetterDTO> letterPageExpected = new PageImpl<>(letterDTOS, request, size);

        // when
        Page<LetterDTO> letterPageActual = paginatorService.configurePaginator(letterTypeCriteria, user.getUsername());

        // then
        Assertions.assertEquals(letterPageExpected, letterPageActual);

    }

    @Test
    void generatePageNumbers() {
        List<Integer> pageNumbersExpected = List.of(1,2,3,4,5,6);
        List<Integer> pageNumbersActual = paginatorService.generatePageNumbers(pageNumbersExpected.size());
        Assertions.assertEquals(pageNumbersExpected, pageNumbersActual);

    }
}