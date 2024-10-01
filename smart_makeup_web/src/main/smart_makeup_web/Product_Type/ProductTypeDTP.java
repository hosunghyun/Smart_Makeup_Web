package com.smwhc.smart_makeup_web.Product_Type;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductTypeDTP {
    private Long product_type_id;
    private String makeup_id;
    private String product_id;
    private String product_type_name;

    // 생성자
    public ProductTypeDTP() {}
    public ProductTypeDTP(String makeup_id, String product_id, String product_type_name) {
        this.makeup_id = makeup_id;
        this.product_id = product_id;
        this.product_type_name = product_type_name;
    }
    public ProductTypeDTP(Long product_type_id, String makeup_id, String product_id, String product_type_name) {
        this.product_type_id = product_type_id;
        this.makeup_id = makeup_id;
        this.product_id = product_id;
        this.product_type_name = product_type_name;
    }
}
