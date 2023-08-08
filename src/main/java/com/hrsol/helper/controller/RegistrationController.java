package com.hrsol.helper.controller;

import com.hrsol.helper.entity.User;
import com.hrsol.helper.model.dto.LocationDTO;
import com.hrsol.helper.model.dto.UserDTO;
import com.hrsol.helper.service.impl.LocationService;
import com.hrsol.helper.service.RegistrationService;
import com.hrsol.helper.service.impl.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class RegistrationController {

    private final UserService userService;
    private final RegistrationService registrationService;
    private final LocationService locationService;
    private List<LocationDTO> locationDTOS;

    public RegistrationController(UserService userService,
                                  RegistrationService registrationService,
                                  LocationService locationService) {
        this.userService = userService;
        this.registrationService = registrationService;
        this.locationService = locationService;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model){
        UserDTO user = new UserDTO();
        List<LocationDTO> locations = getAll();
        model.addAttribute("user", user);
        model.addAttribute("locations", locations);
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
            model.addAttribute("locations", getAll());
            return "/register";
        }

        registrationService.register(userDTO);
        return "redirect:/register?success";
    }

    @GetMapping("/confirm")
    public String confirm(@RequestParam("token") String token) {
        return registrationService.confirmToken(token);
    }

    private List<LocationDTO> getAll() {
        if (locationDTOS == null) {
            locationDTOS = locationService.getAll();
            return locationDTOS;
        }
        return locationDTOS;
    }

}
