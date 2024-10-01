package com.smwhc.smart_makeup_web.Product;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "product")
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long product_code;  // 제품의 PK
    
    // 제품의 이름 not null 길이 20
    @Column(name = "product_name", length = 20, nullable = false)
    private String product_name;

    // 가격
    @Column(name = "price", nullable = false)
    private Integer price;

    // 생성자
    public Product() {}
    public Product(String product_name, Integer price) {
        this.product_name = product_name;
        this.price = price;
    }
}
