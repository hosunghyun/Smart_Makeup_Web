package com.smwhc.smart_makeup_web.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// 회원정보를 위한 레포지토리
@Repository
public interface MemberRepository extends JpaRepository<Member, String> {
    public Member findByEmail(String email);
} 
