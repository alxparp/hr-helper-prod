package com.hrsol.helper.service.impl;

import com.hrsol.helper.DummyObjects;
import com.hrsol.helper.converter.TemplateTypeConverter;
import com.hrsol.helper.entity.LetterType;
import com.hrsol.helper.entity.TemplateType;
import com.hrsol.helper.model.dto.TemplateTypeDTO;
import com.hrsol.helper.repository.TemplateTypeRepository;
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
class TemplateTypeServiceTest {

    @Mock
    private TemplateTypeRepository templateTypeRepository;
    private TemplateTypeService templateTypeService;
    private TemplateType templateType;

    @BeforeEach
    void setUp() {
        templateTypeService = new TemplateTypeService(templateTypeRepository);
        templateType = DummyObjects.getTemplateType();
    }

    @Test
    void findAll() {
        List<TemplateTypeDTO> templateTypeDTOS = List.of(TemplateTypeConverter.templateTypeToDTO(templateType));
        when(templateTypeRepository.findAll()).thenReturn(List.of(templateType));

        List<TemplateTypeDTO> templateTypeDTOSActual = templateTypeService.findAll();

        Assertions.assertEquals(templateTypeDTOS, templateTypeDTOSActual);
    }

    @Test
    void findByType() {
        when(templateTypeRepository.findByType(templateType.getType())).thenReturn(templateType);

        TemplateType templateTypeActual = templateTypeService.findByType(templateType.getType());

        Assertions.assertEquals(templateType, templateTypeActual);
    }
}