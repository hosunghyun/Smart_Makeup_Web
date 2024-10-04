package com.smwhc.smart_makeup_web.Member;

import java.util.Set;

import com.smwhc.smart_makeup_web.Makeup.MakeUp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

// 회원 테이블
@Entity
@Table(name = "member")
@Getter
@Setter
public class Member {
    @Id
    @Column(name = "member_id", updatable = false, length = 50)
    private String member_id;     // 사용자 아이디

    @Column(name = "Member_password", nullable = false, length = 100)   // not null 추가
    private String Member_password;   // 사용자 비밀번호

    @Column(name = "email", nullable = false, length = 50)
    private String email;

    @Column(name = "phone", length = 20)
    private String phone;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private Set<MakeUp> makeups;
    
    // 생성자
    public Member() {}
    public Member(String member_id, String Member_password, String email, String phone) {
        this.member_id = member_id;
        this.Member_password = Member_password;
        this.email = email;
        this.phone = phone;
    }

    public void setMember(MemberDTO userDTO) {
        this.member_id = userDTO.getMember_id();
        this.Member_password = userDTO.getMember_password();
        this.email = userDTO.getEmail();
        this.phone = userDTO.getPhone();
    }
}
