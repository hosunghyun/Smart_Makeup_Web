package com.smwhc.smart_makeup_web.Product;

import java.util.Set;

import com.smwhc.smart_makeup_web.Image.Image;
import com.smwhc.smart_makeup_web.Product_Type.ProductType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
    @Column(name = "product_code")
    private Long product_code;  // 제품의 PK
    
    // 제품의 이름 not null 길이 20
    @Column(name = "product_name", length = 50, nullable = false)
    private String product_name;

    // 가격
    @Column(name = "price", nullable = false)
    private Integer price;

    // 제품종류와의 관계를 나타냄
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private Set<ProductType> product_types;
    
    // 이미지와의 관계를 나타냄
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private Set<Image> images;

    // 생성자
    public Product() {}
    public Product(String product_name, Integer price) {
        this.product_name = product_name;
        this.price = price;
    }
}
