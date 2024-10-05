package com.smwhc.smart_makeup_web.Member;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    @Autowired
    private final MemberRepository memberRepository;
    
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    // 1. 회원 등록 기능
    public void save(MemberDTO memberDTO) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();    // 패스워드 암호화
        Member member = new Member();       // 회원 객체 생성

        member.setMember_id(memberDTO.getMember_id());                                      // 객체에 아이디 담기
        member.setMember_password(passwordEncoder.encode(memberDTO.getMember_password()));  // 비밀번호를 암호화하고 객체에 담기
        member.setEmail(memberDTO.getEmail());                                              // 객체에 이메일 담기
        member.setPhone(memberDTO.getPhone());                                              // 객체에 전화번호 담기

        memberRepository.save(member);      // 객체를 레포지토리에 저장하기
    }
    // 2. 회원 삭제 기능

    // 3. 회원 변경 기능

    // 4. 회원 찾기
    public Member findById(String member_id) {
        Member member = memberRepository.findById(member_id).get();
        return member;
    }
}
