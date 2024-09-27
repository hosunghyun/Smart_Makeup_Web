package com.smwhc.smart_makeup_web.User;

import java.util.Set;

import com.smwhc.smart_makeup_web.Makeup.MakeUp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;

// 회원 테이블
@Entity
@Table(name = "user")
@Getter
public class User {
    @Id
    @Column(name = "user_id", updatable = false, length = 20)
    private String user_id;     // 사용자 아이디

    @Column(name = "user_password", nullable = false, length = 20)   // not null 추가
    private String user_password;   // 사용자 비밀번호

    @Column(name = "email", nullable = false, length = 30)
    private String email;

    @Column(name = "phone", length = 13)
    private String phone;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<MakeUp> makeups;
    
    // 생성자
    public User() {}
    public User(String user_id, String user_password, String email, String phone) {
        this.user_id = user_id;
        this.user_password = user_password;
        this.email = email;
        this.phone = phone;
    }

    public void setUser(UserDTO userDTO) {
        this.user_id = userDTO.getUser_id();
        this.user_password = userDTO.getUser_password();
        this.email = userDTO.getEmail();
        this.phone = userDTO.getPhone();
    }
}
