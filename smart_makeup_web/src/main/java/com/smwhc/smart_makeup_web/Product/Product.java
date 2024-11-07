package com.smwhc.smart_makeup_web.Product;

import java.util.Set;

import com.smwhc.smart_makeup_web.Image.Image;
import com.smwhc.smart_makeup_web.Product_Category.ProductCategory;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
    private Long id;  // 제품의 PK
    
    // 제품의 이름 not null 길이 20
    @Column(name = "product_name", length = 50, nullable = false)
    private String product_name;

    @Column(name = "product_link", length = 50)
    private String product_link;

    @ManyToOne // 일대다 관계 표현 productcategory 한개가 makeup 한개를 가질 수 있다.
    @JoinColumn(name = "category", nullable = false) // 외래키 제약조건
    private ProductCategory category;

    // 가격
    @Column(name = "price", nullable = false)
    private Integer price;
    
    // 이미지와의 관계를 나타냄
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private Set<Image> images;

    // 생성자
    public Product() {}
    public Product(String product_name, String product_link, Integer price) {
        this.product_name = product_name;
        this.product_link = product_link;
        this.price = price;
    }
}
