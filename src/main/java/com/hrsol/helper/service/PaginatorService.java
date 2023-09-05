package com.hrsol.helper.service;

import com.hrsol.helper.entity.Configuration;
import com.hrsol.helper.entity.LetterType;
import com.hrsol.helper.entity.Location;
import com.hrsol.helper.model.FilterCriteria;
import com.hrsol.helper.model.dto.ConfigurationDTO;
import com.hrsol.helper.model.dto.LetterDTO;
import com.hrsol.helper.service.impl.*;
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
    private final ConfigurationService configurationService;

    public PaginatorService(LetterService letterService,
                            LetterTypeService letterTypeService,
                            ConfigurationService configurationService) {
        this.letterService = letterService;
        this.letterTypeService = letterTypeService;
        this.configurationService = configurationService;
    }

    public Page<LetterDTO> configurePaginator(FilterCriteria filterCriteria, String username) {
        int currentPage = filterCriteria.getPage().orElse(CURRENT_PAGE_DEFAULT);
        int pageSize = filterCriteria.getSize().orElse(PAGE_SIZE_DEFAULT);
        LetterType letterType = letterTypeService.findById(filterCriteria.getId());

        List<Location> locations = configurationService.getEntitiesByUser(username)
                .stream()
                .map(Configuration::getLocation)
                .toList();

        return configurePaginator(letterType, locations, currentPage, pageSize);
    }

    private Page<LetterDTO> configurePaginator(LetterType letterType, List<Location> locations, int currentPage, int pageSize) {
        int size = letterService.getSizeByLetterTypeAndUsername_LocationIn(letterType, locations);
        PageRequest request = PageRequest.of(currentPage - 1, pageSize);
        List<LetterDTO> letters = letterService.findByLetterTypeAndUsername_LocationIn(letterType, locations, request);
        return new PageImpl<>(letters, request, size);
    }

    public List<Integer> generatePageNumbers(int totalPages) {
        return IntStream.rangeClosed(1, totalPages)
                .boxed()
                .collect(Collectors.toList());
    }

}
