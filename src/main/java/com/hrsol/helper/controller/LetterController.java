package com.hrsol.helper.controller;

import com.hrsol.helper.model.AjaxResponseBody;
import com.hrsol.helper.model.ClickCriteria;
import com.hrsol.helper.model.LetterDTO;
import com.hrsol.helper.model.LetterTypeDTO;
import com.hrsol.helper.service.LetterTypeService;
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
public class LetterController {

    private final LetterTypeService letterTypeService;
    private final PaginatorService paginatorService;

    public LetterController(LetterTypeService letterTypeService,
                            PaginatorService paginatorService) {
        this.letterTypeService = letterTypeService;
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

        if (errors.hasErrors()) {
            result.setMsg(errors.getAllErrors()
                    .stream().map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining(",")));

            return ResponseEntity.badRequest().body(result);
        }

        if (!letterTypeService.containsId(clickCriteria.getId())) {
            result.setMsg("Such letter type doesn't exist");
            return ResponseEntity.badRequest().body(result);
        }

        formPageResult(clickCriteria, result);

        return ResponseEntity.ok(result);
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



}
