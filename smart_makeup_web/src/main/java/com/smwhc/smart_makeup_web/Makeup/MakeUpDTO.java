package com.smwhc.smart_makeup_web.Makeup;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MakeUpDTO {
    private Long makeup_id;
    private String member_id;
    private String category;
    private String color_code;
    private Integer opacity;
    private Integer number;

    // 생성자
    public MakeUpDTO() {}
    public MakeUpDTO(String member_id, String color_code, Integer opacity, Integer number, String category) {
        this.member_id = member_id;
        this.color_code = color_code;
        this.opacity = opacity;
        this.number = number;
        this.category = category;
    }
    public MakeUpDTO(Long makeup_id, String member_id, String color_code, Integer opacity, Integer number, String category) {
        this.makeup_id = makeup_id;
        this.member_id = member_id;
        this.color_code = color_code;
        this.opacity = opacity;
        this.number = number;
        this.category = category;
    }
}