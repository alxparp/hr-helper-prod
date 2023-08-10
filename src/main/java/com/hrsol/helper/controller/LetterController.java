package com.hrsol.helper.controller;

import com.hrsol.helper.entity.enums.Mode;
import com.hrsol.helper.model.dto.*;
import com.hrsol.helper.service.impl.*;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/main/letter")
public class LetterController {

    private LetterService letterService;
    private LetterStatusService letterStatusService;
    private UserService userService;
    private LetterTypeService letterTypeService;
    private TemplateTypeService templateTypeService;
    private List<LetterStatusDTO> letterStatuses;
    private List<UserDTO> users;
    private List<LetterTypeDTO> letterTypes;
    private List<TemplateTypeDTO> templateTypes;

    public LetterController(LetterService letterService,
                            LetterStatusService letterStatusService,
                            UserService userService,
                            LetterTypeService letterTypeService,
                            TemplateTypeService templateTypeService) {
        this.letterService = letterService;
        this.letterStatusService = letterStatusService;
        this.userService = userService;
        this.letterTypeService = letterTypeService;
        this.templateTypeService = templateTypeService;
    }

    @GetMapping("/create")
    public String create(Model model) {
        LetterDTO letter = new LetterDTO();
        model.addAttribute("letter", letter);
        model.addAttribute("mode", Mode.ADD);
        model.addAllAttributes(getSelectObjects());
        return "letter";
    }

    private Map<String, List<?>> getSelectObjects() {
        Map<String, List<?>> selectObjects = new HashMap<>();
        selectObjects.put("letterStatuses", letterStatuses == null ? letterStatusService.getAll() : letterStatuses);
        selectObjects.put("users", users == null ? userService.findAllUsers() : users);
        selectObjects.put("letterTypes", letterTypes == null ? letterTypeService.findAll() : letterTypes);
        selectObjects.put("templateTypes", templateTypes == null ? templateTypeService.findAll() : templateTypes);
        return selectObjects;
    }

    @PostMapping("/create/save")
    public String save(@ModelAttribute("letter") @Valid LetterDTO letterDTO,
                       BindingResult result,
                       Model model) {

        if(result.hasErrors()){
            model.addAttribute("letter", letterDTO);
            model.addAttribute("mode", Mode.ADD);
            model.addAllAttributes(getSelectObjects());
            return "letter";
        }

        letterService.save(letterDTO);
        return "redirect:/main/letter/create?success";
    }

    @GetMapping("/edit")
    public String edit(@RequestParam(value = "id", required = false) Long id,
                       Model model) {
        LetterDTO letter = letterService.findById(id);
        model.addAttribute("letter", letter);
        model.addAttribute("mode", Mode.EDIT);
        model.addAllAttributes(getSelectObjects());
        return "letter";
    }

    @PostMapping("/edit/update")
    public String update(@ModelAttribute("letter") @Valid LetterDTO letterDTO,
                         BindingResult result, Model model) {

        if(letterDTO.getId() == null || !letterService.containsId(letterDTO.getId())) {
            result.rejectValue("id", null,
                    "There is no letter found with the same id");
        }

        if(result.hasErrors()){
            model.addAttribute("letter", letterDTO);
            model.addAllAttributes(getSelectObjects());
            model.addAttribute("mode", Mode.EDIT);
            return "letter";
        }

        letterService.update(letterDTO);

        return "redirect:/main/letter/edit?success&id=" +letterDTO.getId();
    }
}
