package com.smwhc.smart_makeup_web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {
    @GetMapping({"/", "/index", "/home"})
    public String index() {
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
