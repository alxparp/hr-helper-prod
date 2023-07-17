package com.hrsol.helper.controller;

import com.hrsol.helper.entity.User;
import com.hrsol.helper.model.UserDTO;
import com.hrsol.helper.service.RegistrationService;
import com.hrsol.helper.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RegistrationController {

    private final UserService userService;
    private final RegistrationService registrationService;

    public RegistrationController(UserService userService,
                                  RegistrationService registrationService) {
        this.userService = userService;
        this.registrationService = registrationService;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model){
        UserDTO user = new UserDTO();
        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping("/register/save")
    public String save(@ModelAttribute("user") @Valid UserDTO userDTO,
                       BindingResult result,
                       Model model ) {

        User existingUser = userService.findByUsername(userDTO.getUsername());

        if(existingUser != null){
            result.rejectValue("username", null,
                    "There is already an account registered with the same username");
        }

        if(result.hasErrors()){
            model.addAttribute("user", userDTO);
            return "/register";
        }

        registrationService.register(userDTO);
        return "redirect:/register?success";

    }

}
