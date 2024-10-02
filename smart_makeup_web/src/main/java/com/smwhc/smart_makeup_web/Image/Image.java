package com.smwhc.smart_makeup_web.Image;

import com.smwhc.smart_makeup_web.Board.Board;
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
@Table(name = "image")
@Getter
@Setter
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long image_code;    // 이미지 PK

    @Column(name = "image_link", nullable = false, length = 20)
    private String image_link;  // 이미지가 저장된 링크 주소

    @ManyToOne // 일대다 관계 표현 member 한명이 makeup 여러개를 가질 수 있다.
    @JoinColumn(name = "product_code") // 외래키 제약조건
    private Product product;

    @ManyToOne // 일대다 관계 표현 member 한명이 makeup 여러개를 가질 수 있다.
    @JoinColumn(name = "board_id") // 외래키 제약조건
    private Board board;


    public Image() {}
    public Image(Product product, Board board, String image_link) {
        this.product = product;
        this.board = board;
        this.image_link = image_link;
    }
    public void setImage(ImageDTO imageLinkDTO) {
        this.image_code = imageLinkDTO.getImage_code();
        this.image_link = imageLinkDTO.getImage_link();
    }
}
