package com.smwhc.smart_makeup_web.Product_Makeup;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductMakeupDTP {
    private Long product_type_id;
    private String makeup_id;
    private String product_id;

    // 생성자
    public ProductMakeupDTP() {}
    public ProductMakeupDTP(String makeup_id, String product_id) {
        this.makeup_id = makeup_id;
        this.product_id = product_id;
    }
    public ProductMakeupDTP(Long product_type_id, String makeup_id, String product_id) {
        this.product_type_id = product_type_id;
        this.makeup_id = makeup_id;
        this.product_id = product_id;
    }
}
