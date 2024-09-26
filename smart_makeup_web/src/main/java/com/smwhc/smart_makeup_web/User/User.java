package com.smwhc.smart_makeup_web.Table;

import com.smwhc.smart_makeup_web.DTO.UserDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;

// 회원 테이블
@Entity
@Getter
public class User {
    @Id
    @Column(name = "user_id", updatable = false)
    private String user_id;     // 사용자 아이디

    @Column(name = "user_password", nullable = false)   // not null 추가
    private String user_password;   // 사용자 비밀번호

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "phone")
    private String phone;

    // 생성자
    public User() {}
    public User(String user_id, String user_password, String email, String phone) {
        this.user_id = user_id;
        this.user_password = user_password;
        this.email = email;
        this.phone = phone;
    }
    public User(UserDTO userDTO) {
        this.user_id = userDTO.getUser_id();
        this.user_password = userDTO.getUser_password();
        this.email = userDTO.getEmail();
        this.phone = userDTO.getPhone();
    }
}
