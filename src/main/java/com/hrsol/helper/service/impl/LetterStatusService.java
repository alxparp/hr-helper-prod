package com.hrsol.helper.service.impl;

import com.hrsol.helper.converter.LetterStatusConverter;
import com.hrsol.helper.entity.LetterStatus;
import com.hrsol.helper.model.dto.LetterStatusDTO;
import com.hrsol.helper.repository.LetterStatusRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LetterStatusService {

    private LetterStatusRepository letterStatusRepository;

    public LetterStatusService(LetterStatusRepository letterStatusRepository) {
        this.letterStatusRepository = letterStatusRepository;
    }

    public List<LetterStatusDTO> getAll() {
        return letterStatusRepository.findAll()
                .stream()
                .map(LetterStatusConverter::letterStatusToDTO)
                .toList();
    }

    public LetterStatus findByType(String type) {
        return letterStatusRepository.findByType(type);
    }
}
