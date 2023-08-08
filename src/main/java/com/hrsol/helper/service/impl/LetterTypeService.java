package com.hrsol.helper.service.impl;

import com.hrsol.helper.converter.LetterTypeConverter;
import com.hrsol.helper.entity.LetterType;
import com.hrsol.helper.model.dto.LetterTypeDTO;
import com.hrsol.helper.repository.LetterTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LetterTypeService {

    private final LetterTypeRepository letterTypeRepository;

    public LetterTypeService(LetterTypeRepository letterTypeRepository) {
        this.letterTypeRepository = letterTypeRepository;
    }

    public List<LetterTypeDTO> findAll() {
        return letterTypeRepository.findAll()
                .stream()
                .map(LetterTypeConverter::LetterTypeToDTO)
                .toList();
    }

    public LetterType findById(Long id) {
        return letterTypeRepository.findById(id).orElseThrow();
    }

    public boolean containsId(Long id) {
        return letterTypeRepository.existsById(id);
    }

    public LetterType findByType(String type) {
        return letterTypeRepository.findByType(type);
    }
}
