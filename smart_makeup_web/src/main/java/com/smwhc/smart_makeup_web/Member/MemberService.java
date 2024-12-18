package com.smwhc.smart_makeup_web.Member;

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
    public void deleteMember(Member member) {
        memberRepository.delete(member);
    }

    // 3. 회원 변경 기능
    public Member modifyMember(MemberDTO memberDTO) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();    // 패스워드 암호화
        Member member = new Member();

        member.setMember_id(memberDTO.getMember_id());
        member.setMember_password(passwordEncoder.encode(memberDTO.getMember_password()));
        member.setEmail(memberDTO.getEmail());
        member.setPhone(memberDTO.getPhone());

        // 저장 시 성공 여부를 확인하고 반환
        Member members = memberRepository.save(member);
        return members;
    }

    // 4. 회원 찾기
    public Member findById(String member_id) {
        if(memberRepository.findById(member_id).get() == null) {
            return null;
        }
        Member member = memberRepository.findById(member_id).get();
        return member;
    }

    // 5. 아이디 찾기
    public Member findByMemberId(MemberDTO memberDTO) {
        Member member = memberRepository.findByEmail(memberDTO.getEmail());
        return member;
    }

    // 6. 비밀번호 찾기
    public Member findByMemberpwd(MemberDTO memberDTO) {
        Member member = memberRepository.findById(memberDTO.getMember_id()).get();
        return member;
    }

    // 7. 비밀번호를 잊어버려 새로 변경하기
    public Member changePWD(MemberDTO memberDTO) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();    // 패스워드 암호화
        Member member = memberRepository.findById(memberDTO.getMember_id()).get();

        member.setMember_password(passwordEncoder.encode(memberDTO.getMember_password()));

        memberRepository.save(member);
        return member;
    }
}
