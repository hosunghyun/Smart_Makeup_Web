package com.smwhc.smart_makeup_web.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private String user_id;
    private String user_password;
    private String email;
    private String phone;

    // 생성자
    public UserDTO() {}
    public UserDTO(String user_id, String user_password, String email, String phone) {
        this.user_id = user_id;
        this.user_password = user_password;
        this.email = email;
        this.phone = phone;
    }
    public UserDTO(String user_id, String user_password, String email) {
        this.user_id = user_id;
        this.user_password = user_password;
        this.email = email;
        this.phone = null;
    }
}
