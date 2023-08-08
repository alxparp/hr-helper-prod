package com.hrsol.helper.service;

import com.hrsol.helper.entity.LetterType;
import com.hrsol.helper.model.ClickCriteria;
import com.hrsol.helper.model.dto.LetterDTO;
import com.hrsol.helper.service.impl.LetterService;
import com.hrsol.helper.service.impl.LetterTypeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class PaginatorService {

    private final static int CURRENT_PAGE_DEFAULT = 1;
    private final static int PAGE_SIZE_DEFAULT = 2;
    private final LetterService letterService;
    private final LetterTypeService letterTypeService;

    public PaginatorService(LetterService letterService,
                            LetterTypeService letterTypeService) {
        this.letterService = letterService;
        this.letterTypeService = letterTypeService;
    }

    public Page<LetterDTO> configurePaginator(ClickCriteria clickCriteria) {
        int currentPage = clickCriteria.getPage().orElse(CURRENT_PAGE_DEFAULT);
        int pageSize = clickCriteria.getSize().orElse(PAGE_SIZE_DEFAULT);

        LetterType letterType = letterTypeService.findById(clickCriteria.getId());
        int size = letterService.getSizeByType(letterType);
        PageRequest request = PageRequest.of(currentPage - 1, pageSize);

        List<LetterDTO> letters = letterService.findByLetterType(letterType, request);

        return new PageImpl<>(letters, request, size);
    }

    public List<Integer> generatePageNumbers(int totalPages) {
        return IntStream.rangeClosed(1, totalPages)
                .boxed()
                .collect(Collectors.toList());
    }

}
