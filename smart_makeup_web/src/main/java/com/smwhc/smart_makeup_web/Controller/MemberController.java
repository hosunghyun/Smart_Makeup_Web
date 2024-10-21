package com.smwhc.smart_makeup_web.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.smwhc.smart_makeup_web.Member.Member;
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

    // 사용자가 입력한 비밀번호가 맞는지 확인
    @PostMapping("/checkpassword")
    public ResponseEntity<String> checkpassword(@RequestBody MemberDTO memberDTO) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();    // 패스워드 암호화
        // 현재 로그인된 사용자 정보를 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName(); // 사용자 아이디
        Member member = memberService.findById(currentUsername);

        String result;

        if (passwordEncoder.matches(memberDTO.getMember_password(), member.getMember_password())) {
            result = "success";
        } else {
            result = "fails";
        }

        return ResponseEntity.status(200).body(result);     // 결과를 반한
    }

    // 프로필 페이지로 이동
    @GetMapping("/profile")
    public String profile(Model model) {
        // 현재 로그인된 사용자 정보를 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName(); // 사용자 아이디

        Member member = memberService.findById(currentUsername);

        model.addAttribute("member", member);

        return "profile";
    }

    // 회원 탈퇴 기능
    @PostMapping("/withdraw")
    public ResponseEntity<String> withdraw(@RequestBody MemberDTO memberDTO) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();    // 패스워드 암호화
        // 현재 로그인된 사용자 정보를 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName(); // 사용자 아이디
        Member member = memberService.findById(currentUsername);

        String result;

        if (passwordEncoder.matches(memberDTO.getMember_password(), member.getMember_password())) {
            memberService.deleteMember(member);
            result = "success";
        } else {
            result = "fails";
        }

        return ResponseEntity.status(200).body(result);     // 결과를 반한
    }

    // 회원 수정 기능
    @PostMapping("/change/member-info")
    public ResponseEntity<String> changemember(@RequestBody MemberDTO memberDTO) {
        String result;
        Member member;

        if (memberDTO.getMember_password() == null || memberDTO.getMember_password().isEmpty() ||
            memberDTO.getEmail() == null || memberDTO.getEmail().isEmpty()) {
            result = "fails";
        }
        else {
            member  = memberService.modifyMember(memberDTO);
            if (member != null) {
                result = "success";
            }
            else {
                result = "fails";
            }
        }
        
        return ResponseEntity.status(200).body(result);     // 결과를 반한
    }

    // 아이디를 찾는 기능으로 이메일과 전화번호를 받아서 찾는다.
    @PostMapping("/searchId")
    public ResponseEntity<String> searchid(@RequestBody MemberDTO memberDTO) {
        String result;
        
        if(memberDTO.getEmail() != null || !memberDTO.getEmail().isEmpty() ||
            memberDTO.getPhone() != null || !memberDTO.getPhone().isEmpty()) {
                Member member = memberService.findByMemberId(memberDTO);
                result = member.getMember_id();
            }
        else {
            result = "fails";
        }
        
        return ResponseEntity.status(200).body(result);
    }

    // 비밀번호 찾을 때 기능으로 아이디와 이메일을 받아서 처리
    @PostMapping("/searchpwd")
    public ResponseEntity<String> searchpwd(@RequestBody MemberDTO memberDTO) {
        String result;

        if (memberDTO.getMember_id() != null || !memberDTO.getMember_id().isEmpty() ||
            memberDTO.getEmail() != null || !memberDTO.getEmail().isEmpty()) {
            Member member = memberService.findByMemberpwd(memberDTO);
            result = member.getMember_password();
        }
        else {
            result = "fails";
        }
        
        return ResponseEntity.status(200).body(result);
    }

    // 아이디와 이메일이 동일하므로 새 비밀번호로 변경하기
    @PostMapping("/newpwd")
    public ResponseEntity<String> newpwd(@RequestBody MemberDTO memberDTO) {
        String result;

        if(memberDTO.getMember_id() != null || !memberDTO.getMember_id().isEmpty() ||
            memberDTO.getMember_password() != null || !memberDTO.getMember_password().isEmpty()) {
            //Member member = 
            memberService.changePWD(memberDTO);
            result = "success";
        }
        else {
            result = "falis";
        }

        return ResponseEntity.status(200).body(result);
    }
}
