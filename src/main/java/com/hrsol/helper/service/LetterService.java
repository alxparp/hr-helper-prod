package com.hrsol.helper.service;

import com.hrsol.helper.converter.LetterConverter;
import com.hrsol.helper.entity.LetterType;
import com.hrsol.helper.model.LetterDTO;
import com.hrsol.helper.repository.LetterRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class LetterService {

    private final LetterRepository letterRepository;

    public LetterService(LetterRepository letterRepository) {
        this.letterRepository = letterRepository;
    }

    public List<LetterDTO> findByType(LetterType letterType) {
        return letterRepository.findByLetterType(letterType)
                .stream()
                .map(LetterConverter::LetterToDTO)
                .toList();
    }

    public Page<LetterDTO> findPaginated(Pageable pageable, LetterType letterType) {
        List<LetterDTO> letters = findByType(letterType);

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<LetterDTO> list;

        if (letters.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, letters.size());
            list = letters.subList(startItem, toIndex);
        }

        Page<LetterDTO> letterPage = new PageImpl<>(
                list,
                PageRequest.of(currentPage, pageSize),
                letters.size()
        );

        return letterPage;
    }
}
