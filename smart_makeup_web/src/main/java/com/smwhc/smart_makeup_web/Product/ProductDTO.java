package com.smwhc.smart_makeup_web.Product;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDTO {
    private Long product_code;
    private String product_name;
    private Integer price;
    private String category;

    // 생성자
    public ProductDTO() {}
    public ProductDTO(String product_name, Integer price, String category) {
        this.product_name = product_name;
        this.price = price;
        this.category = category;
    }
    public ProductDTO(Long product_code, String product_name, Integer price, String category) {
        this.product_code = product_code;
        this.product_name = product_name;
        this.price = price;
        this.category = category;
    }
}
