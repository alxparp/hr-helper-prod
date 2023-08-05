package com.hrsol.helper.controller;

import com.hrsol.helper.model.AjaxResponseBody;
import com.hrsol.helper.model.ClickCriteria;
import com.hrsol.helper.model.LetterDTO;
import com.hrsol.helper.model.LetterTypeDTO;
import com.hrsol.helper.service.LetterService;
import com.hrsol.helper.service.LetterTypeService;
import jakarta.validation.Valid;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/main")
public class LetterController {

    private final LetterTypeService letterTypeService;
    private final LetterService letterService;

    public LetterController(LetterTypeService letterTypeService,
                            LetterService letterService) {
        this.letterTypeService = letterTypeService;
        this.letterService = letterService;
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

        int currentPage = clickCriteria.getPage().orElse(1);
        int pageSize = clickCriteria.getSize().orElse(2);

        Page<LetterDTO> letterPage = letterService.findPaginated(
                PageRequest.of(currentPage - 1, pageSize),
                letterTypeService.findById(clickCriteria.getId()));

        int totalPages = letterPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            result.setPageNumbers(pageNumbers);
        }

        if (letterPage.getContent().isEmpty()) {
            result.setMsg("No letter found!");
        } else {
            result.setMsg("Success");
        }
        result.setResult(letterPage);

        return ResponseEntity.ok(result);
    }

//    @GetMapping("/listLetters")
//    public String listLetters(
//            Model model,
//            @RequestParam("page") Optional<Integer> page,
//            @RequestParam("size") Optional<Integer> size) {
//        int currentPage = page.orElse(1);
//        int pageSize = size.orElse(5);
//
//        Page<LetterDTO> letterPage = letterService.findPaginated(
//                PageRequest.of(currentPage - 1, pageSize));
//
//        model.addAttribute("letterPage", letterPage);
//
//        int totalPages = letterPage.getTotalPages();
//        if (totalPages > 0) {
//            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
//                    .boxed()
//                    .collect(Collectors.toList());
//            model.addAttribute("pageNumbers", pageNumbers);
//        }
//        return "main";
//    }

}
