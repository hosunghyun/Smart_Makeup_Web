package com.smwhc.smart_makeup_web.Makeup;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MakeUpDTO {
    private Long makeup_id;
    private String user_id;
    private String color_code;
    private Integer opacity;

    // 생성자
    public MakeUpDTO() {}
    public MakeUpDTO(String user_id, String color_code, Integer opacity) {
        this.user_id = user_id;
        this.color_code = color_code;
        this.opacity = opacity;
    }
    public MakeUpDTO(Long makeup_id, String user_id, String color_code, Integer opacity) {
        this.makeup_id = makeup_id;
        this.user_id = user_id;
        this.color_code = color_code;
        this.opacity = opacity;
    }
}