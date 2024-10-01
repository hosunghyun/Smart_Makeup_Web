package com.smwhc.smart_makeup_web.Image;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

    public Image() {}
    public Image(String image_link) {
        this.image_link = image_link;
    }
    public void setImage(ImageDTO imageLinkDTO) {
        this.image_code = imageLinkDTO.getImage_code();
        this.image_link = imageLinkDTO.getImage_link();
    }
}
