package com.smwhc.smart_makeup_web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.smwhc.smart_makeup_web.Service.UserService;

@Controller
public class MainController {
    @Autowired
    private final UserService userService;
    public MainController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping({"/", "/index", "/home"})
    public String index() {
        userService.test();
        return "index";
    }

    @GetMapping("/makeup")
    public String makeup() {
        return "makeup";
    }

    @GetMapping("/board")
    public String board() {
        return "board";
    }
    
    @GetMapping("/signin")
    public String signin() {
        return "signin";
    }

    @PostMapping("/login")
    public String login(@RequestParam String userid) {
        System.out.println(userid);
        return "redirect:/";
    }
}
