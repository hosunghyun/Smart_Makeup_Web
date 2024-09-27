package com.smwhc.smart_makeup_web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.smwhc.smart_makeup_web.Makeup.MakeUpService;
import com.smwhc.smart_makeup_web.User.UserService;

@Controller
public class MainController {
    // 회원 프레젠테이션 계층과 연결
    @Autowired
    private final UserService userService;

    // 화장 프레젠테이션 계층과 연결
    @Autowired
    private final MakeUpService makeUpService;

    // 생성자
    public MainController(UserService userService, MakeUpService makeUpService) {
        this.userService = userService;
        this.makeUpService = makeUpService;
    }

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
