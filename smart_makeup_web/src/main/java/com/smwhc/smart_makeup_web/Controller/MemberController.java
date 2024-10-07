package com.smwhc.smart_makeup_web.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.smwhc.smart_makeup_web.Member.MemberDTO;
import com.smwhc.smart_makeup_web.Member.MemberService;

@Controller
public class MemberController {
    @Autowired
    private final MemberService memberService;  // 회원 프레젠테이션 계층과 연결

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    // 로그인 화면에서 회원가입 버튼을 클릭했을 때 회원 가입 페이지로 이동
    @GetMapping("/join")
    public String join() {
        return "join";
    }
    // 회원 가입 페이지에서 아이디, 비밀번호, 이메일, 전화번호를 입력했고 POST 형식으로 전송한 데이터를 받는 곳
    @PostMapping("/join_in")
    public String join_in(@ModelAttribute MemberDTO memberDTO) {
        memberService.save(memberDTO);
        return "index";
    }

    // 회원 가입시 중복확인을 위한 데이터 확인
    @PostMapping("/join/{id}")
    public ResponseEntity<String> getUserById(@RequestBody MemberDTO memberDTO) {
        String result;
        if(memberService.findById(memberDTO.getMember_id()) == null) {      
            result = "null";    // 유저를 찾는 동작에서 null이 나오면 가입된 유저가 없다.
        }
        else {
            result = "exist";   // 유저가 있으므로 중복이다.
        }
        
        return ResponseEntity.status(200).body(result);     // 결과를 반한
    }
}
