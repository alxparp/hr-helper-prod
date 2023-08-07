package com.hrsol.helper.service;

import com.hrsol.helper.converter.LetterConverter;
import com.hrsol.helper.entity.LetterType;
import com.hrsol.helper.model.LetterDTO;
import com.hrsol.helper.repository.LetterRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LetterService {

    private final LetterRepository letterRepository;
    private final LetterTypeService letterTypeService;

    public LetterService(LetterRepository letterRepository,
                         LetterTypeService letterTypeService) {
        this.letterRepository = letterRepository;
        this.letterTypeService = letterTypeService;
    }

    public List<LetterDTO> findByType(LetterType letterType) {
        return letterRepository.findByLetterType(letterType)
                .stream()
                .map(LetterConverter::LetterToDTO)
                .toList();
    }

    public Integer getSizeByType(LetterType letterType) {
        return letterRepository.countByLetterType(letterType);
    }

    public List<LetterDTO> findByLetterType(LetterType letterType, Pageable pageable) {
        return letterRepository.findByLetterType(letterType, pageable)
                .stream()
                .map(LetterConverter::LetterToDTO)
                .toList();
    }

    @Transactional
    public int approveGeneratedLetter(Long id) {
        LetterType letterType = letterTypeService.findById(2L);
        return letterRepository.approveGeneratedLetter(letterType, id);
    }
}
