package com.smwhc.smart_makeup_web.Product_Makeup;

import com.smwhc.smart_makeup_web.Makeup.MakeUp;
import com.smwhc.smart_makeup_web.Product.Product;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "product_makeup")
@Getter
@Setter
public class ProductMakeup {
    // 기본키
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_makeup_id")
    private Long id;

    // makeup 테이블의 PK인 makeup_id 를 외래키로 사용
    @ManyToOne // 일대다 관계 표현 makeup 한개가 ProductType을 여러개를 가질 수 있다.
    @JoinColumn(name = "makeup_id", nullable = false) // 외래키 제약조건
    private MakeUp makeup;

    // product 테이블의 PK인 product_code 를 외래키로 사용
    @ManyToOne  // 일대다 관계 표현 product 한개가 ProductType을 여러개를 가질 수 있다.
    @JoinColumn(name = "product_code", nullable = false) // 외래키 제약조건
    private Product product;

    // 생성자
    public ProductMakeup() {}
    public ProductMakeup(MakeUp makeup, Product product) {
        this.makeup = makeup;
        this.product = product;
    }
}