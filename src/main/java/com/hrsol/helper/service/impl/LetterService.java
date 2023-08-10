package com.hrsol.helper.service.impl;

import com.hrsol.helper.converter.LetterConverter;
import com.hrsol.helper.entity.Letter;
import com.hrsol.helper.entity.LetterType;
import com.hrsol.helper.model.dto.LetterDTO;
import com.hrsol.helper.repository.LetterRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LetterService {

    private final LetterRepository letterRepository;
    private final LetterTypeService letterTypeService;
    private final LetterStatusService letterStatusService;
    private final UserService userService;
    private final TemplateTypeService templateTypeService;

    public LetterService(LetterRepository letterRepository,
                         LetterTypeService letterTypeService,
                         LetterStatusService letterStatusService,
                         UserService userService,
                         TemplateTypeService templateTypeService) {
        this.letterRepository = letterRepository;
        this.letterTypeService = letterTypeService;
        this.letterStatusService = letterStatusService;
        this.userService = userService;
        this.templateTypeService = templateTypeService;
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

    public List<LetterDTO> findByLetterTypeAndCities(LetterType letterType, List<String> cities, Pageable pageable) {
        return letterRepository.findByLetterTypeAndUsername_Location_CityIn(letterType, cities, pageable)
                .stream()
                .map(LetterConverter::LetterToDTO)
                .toList();
    }

    public Integer getSizeByLetterTypeAndCities(LetterType letterType, List<String> cities) {
        return letterRepository.countByLetterTypeAndUsername_Location_CityIn(letterType, cities);
    }

    @Transactional
    public int approveGeneratedLetter(Long id) {
        LetterType letterType = letterTypeService.findById(2L);
        return letterRepository.approveGeneratedLetter(letterType, id);
    }

    public boolean containsId(Long id) {
        return letterRepository.existsById(id);
    }

    public Letter save(LetterDTO letterDTO) {
        Letter letter = LetterConverter.DTOToLetter(letterDTO);

        letter.setLetterStatus(letterStatusService.findByType(letterDTO.getLetterStatus()));
        letter.setUsername(userService.findByUsername(letterDTO.getUsername()));
        letter.setLetterType(letterTypeService.findByType(letterDTO.getLetterType()));
        letter.setTemplateType(templateTypeService.findByType(letterDTO.getTemplateType()));

        return letterRepository.save(letter);
    }

    public Letter update(LetterDTO letterDTO) {
        return save(letterDTO);
    }

    public LetterDTO findById(Long id) {
        return LetterConverter.LetterToDTO(letterRepository.findById(id).orElseThrow());
    }
}
