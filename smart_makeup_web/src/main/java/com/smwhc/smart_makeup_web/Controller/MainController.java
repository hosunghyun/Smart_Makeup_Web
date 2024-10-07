package com.smwhc.smart_makeup_web.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class MainController {
//------------------------------------------------------------------------------------------------------------------------------------------

    // 메뉴바 컨트롤러 부분

    // 메인 페이지
    @GetMapping({"/", "/index", "/home"})
    public String index() {
        return "index";
    }

    @GetMapping("/makeup")
    public String makeup() {
        return "makeup";
    }
    
    // 로그인 페이지로 이동
    // 로그인 페이지에서 입력된 아이디와 패스워드로 로그인
    @GetMapping("/sign")
    public String signin() {
        return "sign";
    }

    // 프로필 페이지로 이동
    @GetMapping("/profile")
    public String profile() {
        return "profile";
    }
}