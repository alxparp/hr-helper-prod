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

    @GetMapping("/main")
    public String main(){
        return "main";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}