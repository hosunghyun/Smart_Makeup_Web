package com.smwhc.smart_makeup_web.Member;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
        Member member = new Member();
        member.setMember(memberDTO);

        memberRepository.save(member);
    }
    // 2. 회원 삭제 기능

    // 3. 회원 변경 기능

    // 4. 회원 찾기
    public Member findById(String member_id) {
        Optional<Member> member = memberRepository.findById(member_id);
        return member.orElse(null);
    }
}
