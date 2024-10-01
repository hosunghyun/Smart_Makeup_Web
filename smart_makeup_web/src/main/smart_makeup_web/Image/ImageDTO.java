package com.smwhc.smart_makeup_web.Image;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImageDTO {
    private Long image_code;
    private String image_link;

    // 생성자
    public ImageDTO() {}
    public ImageDTO(String image_Link) {
        this.image_link = image_Link;
    }
    public ImageDTO(Long image_code, String image_link) {
        this.image_code = image_code;
        this.image_link = image_link;
    }
}
