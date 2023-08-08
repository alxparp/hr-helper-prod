package com.hrsol.helper.service.impl;

import com.hrsol.helper.converter.TemplateTypeConverter;
import com.hrsol.helper.entity.TemplateType;
import com.hrsol.helper.model.dto.TemplateTypeDTO;
import com.hrsol.helper.repository.TemplateTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TemplateTypeService {

    private TemplateTypeRepository templateTypeRepository;

    public TemplateTypeService(TemplateTypeRepository templateTypeRepository) {
        this.templateTypeRepository = templateTypeRepository;
    }

    public List<TemplateTypeDTO> findAll() {
        return templateTypeRepository.findAll()
                .stream()
                .map(TemplateTypeConverter::templateTypeToDTO)
                .toList();
    }

    public TemplateType findByType(String type) {
        return templateTypeRepository.findByType(type);
    }
}
