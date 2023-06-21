package com.example.project.controllers;


import com.example.project.service.UsersDetailsService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final UsersDetailsService usersDetailsService;

    @Autowired
    public AuthController(UsersDetailsService usersDetailsService) {
        this.usersDetailsService = usersDetailsService;
    }

    @GetMapping("/login")
    public String login(){
        return "auth/login";
    }


}
