package com.hrsol.helper.service;

import com.google.gson.Gson;
import com.hrsol.helper.DummyObjects;
import com.hrsol.helper.converter.LetterConverter;
import com.hrsol.helper.entity.Letter;
import com.hrsol.helper.entity.LetterType;
import com.hrsol.helper.model.ClickCriteria;
import com.hrsol.helper.model.dto.LetterDTO;
import com.hrsol.helper.service.impl.LetterService;
import com.hrsol.helper.service.impl.LetterTypeService;
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
    private PaginatorService paginatorService;
    private LetterType letterType;
    private Integer size;
    private Letter letter;
    private ClickCriteria clickCriteria;

    @BeforeEach
    void setUp() {
        letterType = DummyObjects.getLetterType();
        size = 8;
        letter = DummyObjects.getLetter();
        clickCriteria = DummyObjects.getClickCriteria();
        paginatorService = new PaginatorService(letterService, letterTypeService);
    }

    @Test
    void configurePaginator() {
        // given
        int currentPage = clickCriteria.getPage().orElse(1);
        int pageSize = clickCriteria.getSize().orElse(2);
        List<LetterDTO> letterDTOS = new ArrayList<>();
        Gson gson = new Gson();
        for (int i = 0; i < pageSize; i++) {
            letterDTOS.add(LetterConverter.LetterToDTO(gson.fromJson(gson.toJson(letter), Letter.class)));
        }
        PageRequest request = PageRequest.of(currentPage - 1, pageSize);
        when(letterTypeService.findById(clickCriteria.getId())).thenReturn(letterType);
        when(letterService.getSizeByType(letterType)).thenReturn(size);
        when(letterService.findByLetterType(letterType, request)).thenReturn(letterDTOS);
        Page<LetterDTO> letterPageExpected = new PageImpl<>(letterDTOS, request, size);

        // when
        Page<LetterDTO> letterPageActual = paginatorService.configurePaginator(clickCriteria);

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