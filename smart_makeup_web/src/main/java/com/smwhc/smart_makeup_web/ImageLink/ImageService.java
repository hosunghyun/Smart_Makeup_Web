package com.smwhc.smart_makeup_web.ImageLink;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageService {
    @Autowired
    private final ImageRepository imageRepository;

    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    // 1. 이미지 링크 저장
    public void save(ImageDTO imageDTO) {
        Image image = new Image();
        image.setImage(imageDTO);
        imageRepository.save(image);
    }
    // 2. 이미지 불러오기
    
    // 3. 이미지 삭제하기

    // 4. 이미지 수정하기
}
