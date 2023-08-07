package com.hrsol.helper.controller;

import com.hrsol.helper.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin-main";
    }

    @GetMapping("main/letter")
    public String letter() {
        return "letter";
    }

    @GetMapping("main/reminder")
    public String reminder() {
        return "reminder";
    }

    @GetMapping("main/templates")
    public String templates() {
        return "templates";
    }
}