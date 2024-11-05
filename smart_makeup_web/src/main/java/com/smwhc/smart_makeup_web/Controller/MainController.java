package com.smwhc.smart_makeup_web.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.smwhc.smart_makeup_web.Makeup.MakeUp;
import com.smwhc.smart_makeup_web.Makeup.MakeUpService;
import com.smwhc.smart_makeup_web.Member.Member;
import com.smwhc.smart_makeup_web.Member.MemberService;

@Controller
public class MainController {
    @Autowired
    private final MemberService memberService;
    private final MakeUpService makeUpService;

    public MainController(MemberService memberService, MakeUpService makeUpService) {
        this.memberService = memberService;
        this.makeUpService = makeUpService;
    }
    // 메뉴바 컨트롤러 부분

    // 메인 페이지
    @GetMapping({"/", "/index", "/home"})
    public String index() {
        
        return "index";
    }

    @GetMapping("/makeup")
    public String makeup(Model model) {
        // 현재 로그인된 사용자 정보를 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName(); // 사용자 아이디

        Member member = memberService.findById(currentUsername);
        List<MakeUp> makeUps = makeUpService.findByMember(member);
        
        model.addAttribute("makeups", makeUps);
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