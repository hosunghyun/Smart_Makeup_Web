package com.smwhc.smart_makeup_web.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
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

    // 사용자가 프로필을 누르면 개인정보이기 때문에 먼저 비밀번호로 사용자 확인 페이지로 이동
    @GetMapping("/checkuser")
    public String checkuser() {
        return "checkuser";
    }
}