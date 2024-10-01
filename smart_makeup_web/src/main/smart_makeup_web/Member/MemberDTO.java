package com.smwhc.smart_makeup_web.Member;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberDTO {
    private String member_id;
    private String member_password;
    private String email;
    private String phone;

    // 생성자
    public MemberDTO() {}
    public MemberDTO(String member_id, String member_password, String email, String phone) {
        this.member_id = member_id;
        this.member_password = member_password;
        this.email = email;
        this.phone = phone;
    }
    public MemberDTO(String member_id, String member_password, String email) {
        this.member_id = member_id;
        this.member_password = member_password;
        this.email = email;
        this.phone = null;
    }
}
