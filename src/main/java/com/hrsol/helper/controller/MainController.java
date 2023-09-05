package com.hrsol.helper.controller;

import com.hrsol.helper.entity.User;
import com.hrsol.helper.model.*;
import com.hrsol.helper.model.dto.ConfigurationDTO;
import com.hrsol.helper.model.dto.LetterDTO;
import com.hrsol.helper.service.PaginatorService;
import com.hrsol.helper.service.impl.*;
import jakarta.validation.Valid;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/main")
public class MainController {

    private final LetterTypeService letterTypeService;
    private final LetterService letterService;
    private final PaginatorService paginatorService;
    private final LocationService locationService;
    private final ConfigurationService configurationService;

    public MainController(LetterTypeService letterTypeService,
                          LetterService letterService,
                          PaginatorService paginatorService,
                          LocationService locationService,
                          ConfigurationService configurationService) {
        this.letterTypeService = letterTypeService;
        this.letterService = letterService;
        this.paginatorService = paginatorService;
        this.locationService = locationService;
        this.configurationService = configurationService;
    }

    @GetMapping
    public String main(Model model, Principal principal) {
        model.addAttribute("letterTypes", letterTypeService.findAll());
        model.addAttribute("locations", locationService.getAll());
        model.addAttribute("locationsDefault", configurationService.getDTOSByUser(principal.getName()));
        return "main";
    }

    @GetMapping(value = "/locationsDefault", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> getLocationsDefault(Principal principal) {
        CitiesResponseBody result = new CitiesResponseBody();
        result.setCities(configurationService.getDTOSByUser(principal.getName())
                .stream()
                .map(ConfigurationDTO::getLocation)
                .toList());
        result.setMsg("Success");
        return ResponseEntity.ok(result);
    }

    @PostMapping(value = "/letterType", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> getLettersByType(
            @Valid @RequestBody LetterTypeCriteria letterTypeCriteria, Errors errors, Principal principal) {

        LetterResponseBody result = new LetterResponseBody();
        ResponseEntity<?> response = handleErrorsAndValidate(errors, result, letterTypeService.containsId(letterTypeCriteria.getId()));
        if (response != null) return response;

        Page<LetterDTO> letterPage = paginatorService.configurePaginator(letterTypeCriteria, principal.getName());
        formPageResult(result, letterPage);
        return ResponseEntity.ok(result);
    }

    @PostMapping(value = "/approveLetter", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> approveLetter(
            @Valid @RequestBody ApproveLetter approveLetter, Errors errors) {

        LetterResponseBody result = new LetterResponseBody();
        Long approveLetterId = approveLetter.getId();
        ResponseEntity<?> response = handleErrorsAndValidate(errors, result, letterService.containsId(approveLetterId));
        if (response != null) return response;

        int flag = letterService.approveGeneratedLetter(approveLetterId);
        result.setMsg(flag == 0 ? "Can't approve the letter" : "Success");
        return ResponseEntity.ok(result);
    }

    private ResponseEntity<?> handleErrorsAndValidate(
            Errors errors, LetterResponseBody result, boolean isValid) {

        ResponseEntity<?> response = handleErrors(errors, result);
        if (response != null) return response;

        if (!isValid) {
            result.setMsg("Such letter type/letter doesn't exist");
            return ResponseEntity.badRequest().body(result);
        }
        return null;
    }

    public ResponseEntity<?> handleErrors(Errors errors,
                                          LetterResponseBody result) {
        if (errors.hasErrors()) {
            String errorMsg = errors.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining(","));
            result.setMsg(errorMsg);
            return ResponseEntity.badRequest().body(result);
        }
        return null;
    }

    private void formPageResult(LetterResponseBody result, Page<LetterDTO> letterPage) {
        int totalPages = letterPage.getTotalPages();
        if (totalPages > 0) result.setPageNumbers(paginatorService.generatePageNumbers(totalPages));

        if (letterPage.getContent().isEmpty()) {
            result.setMsg("No letter found!");
        } else {
            result.setMsg("Success");
        }
        result.setResult(letterPage);
    }

    @PostMapping(value = "/lettersByCities", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> getLettersByCities(@RequestBody Places places, Principal principal) {
        configurationService.update(places, principal.getName());
        LetterResponseBody result = new LetterResponseBody();
        Page<LetterDTO> letterPage = paginatorService.configurePaginator(places, principal.getName());
        formPageResult(result, letterPage);
        return ResponseEntity.ok(result);
    }


}
