package com.smwhc.smart_makeup_web.Makeup;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MakeUpDTO {
    private Long makeup_id;
    private String member_id;
    private String color_code;
    private Integer opacity;
    private Integer button_number;

    // 생성자
    public MakeUpDTO() {}
    public MakeUpDTO(String member_id, String color_code, Integer opacity, Integer button_number) {
        this.member_id = member_id;
        this.color_code = color_code;
        this.opacity = opacity;
        this.button_number = button_number;
    }
    public MakeUpDTO(Long makeup_id, String member_id, String color_code, Integer opacity, Integer button_number) {
        this.makeup_id = makeup_id;
        this.member_id = member_id;
        this.color_code = color_code;
        this.opacity = opacity;
        this.button_number = button_number;
    }
}