package com.hrsol.helper.service;

import com.hrsol.helper.converter.LetterTypeConverter;
import com.hrsol.helper.entity.LetterType;
import com.hrsol.helper.model.LetterDTO;
import com.hrsol.helper.model.LetterTypeDTO;
import com.hrsol.helper.repository.LetterTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LetterTypeService {

    private final LetterTypeRepository letterTypeRepository;
    private final LetterService letterService;

    public LetterTypeService(LetterTypeRepository letterTypeRepository,
                             LetterService letterService) {
        this.letterTypeRepository = letterTypeRepository;
        this.letterService = letterService;
    }

    public List<LetterTypeDTO> findAll() {
        return letterTypeRepository.findAll()
                .stream()
                .map(letterType -> {
                    LetterTypeDTO letterTypeDTO = LetterTypeConverter.LetterTypeToDTO(letterType);
                    List<LetterDTO> letterList = letterService.findByType(letterType);
                    letterTypeDTO.setLetterDTOS(letterList);
                    return letterTypeDTO;
                })
                .toList();
    }

    public LetterType findById(Long id) {
        return letterTypeRepository.findById(id).orElseThrow();
    }
}
