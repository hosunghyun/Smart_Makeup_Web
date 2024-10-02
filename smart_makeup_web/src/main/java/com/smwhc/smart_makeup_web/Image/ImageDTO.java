package com.smwhc.smart_makeup_web.Image;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImageDTO {
    private Long image_code;
    private String image_link;
    private Long product_code;
    private Long board_id;

    // 생성자
    public ImageDTO() {}
    public ImageDTO(Long product_code, Long board_id, String image_Link) {
        this.product_code = product_code;
        this.board_id = board_id;
        this.image_link = image_Link;
    }
    public ImageDTO(Long image_code, Long product_code, Long board_id, String image_link) {
        this.image_code = image_code;
        this.image_link = image_link;
        this.product_code = product_code;
        this.board_id = board_id;
    }
}
