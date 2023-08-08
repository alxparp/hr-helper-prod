package com.hrsol.helper.controller;

import com.hrsol.helper.model.AjaxResponseBody;
import com.hrsol.helper.model.ClickCriteria;
import com.hrsol.helper.model.dto.LetterDTO;
import com.hrsol.helper.model.dto.LetterTypeDTO;
import com.hrsol.helper.service.impl.LetterService;
import com.hrsol.helper.service.impl.LetterTypeService;
import com.hrsol.helper.service.PaginatorService;
import jakarta.validation.Valid;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/main")
public class MainController {

    private final LetterTypeService letterTypeService;
    private final LetterService letterService;
    private final PaginatorService paginatorService;

    public MainController(LetterTypeService letterTypeService,
                          LetterService letterService,
                          PaginatorService paginatorService) {
        this.letterTypeService = letterTypeService;
        this.letterService = letterService;
        this.paginatorService = paginatorService;
    }

    @GetMapping
    public String main(Model model) {
        List<LetterTypeDTO> letterTypes = letterTypeService.findAll();
        model.addAttribute("letterTypes", letterTypes);
        return "main";
    }

    @PostMapping(value = "/letterType", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> getResultViaAjax(
            @Valid @RequestBody ClickCriteria clickCriteria, Errors errors) {

        AjaxResponseBody result = new AjaxResponseBody();
        ResponseEntity<?> response = checkErrors(clickCriteria.getId(), errors, result);
        if (response != null) return response;

        if (!letterTypeService.containsId(clickCriteria.getId())) {
            result.setMsg("Such letter type doesn't exist");
            return ResponseEntity.badRequest().body(result);
        }

        formPageResult(clickCriteria, result);

        return ResponseEntity.ok(result);
    }

    public ResponseEntity<?> checkErrors(Long id,
                                         Errors errors,
                                         AjaxResponseBody result) {
        if (errors.hasErrors()) {
            result.setMsg(errors.getAllErrors()
                    .stream().map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining(",")));

            return ResponseEntity.badRequest().body(result);
        }

        return null;
    }

    private void formPageResult(ClickCriteria clickCriteria, AjaxResponseBody result) {
        Page<LetterDTO> letterPage = paginatorService.configurePaginator(clickCriteria);
        int totalPages = letterPage.getTotalPages();
        if (totalPages > 0) result.setPageNumbers(paginatorService.generatePageNumbers(totalPages));

        if (letterPage.getContent().isEmpty()) {
            result.setMsg("No letter found!");
        } else {
            result.setMsg("Success");
        }
        result.setResult(letterPage);
    }

    @PostMapping(value = "/approveLetter", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> approveLetter(
            @Valid @RequestBody LetterDTO letterDTO, Errors errors) {

        AjaxResponseBody result = new AjaxResponseBody();
        ResponseEntity<?> response = checkErrors(letterDTO.getId(), errors, result);
        if (response != null) return response;

        if (!letterService.containsId(letterDTO.getId())) {
            result.setMsg("Such letter doesn't exist");
            return ResponseEntity.badRequest().body(result);
        }

        int flag = letterService.approveGeneratedLetter(letterDTO.getId());
        if (flag == 0) result.setMsg("Can't approve the letter");
        else result.setMsg("Success");

        return ResponseEntity.ok(result);
    }


}
