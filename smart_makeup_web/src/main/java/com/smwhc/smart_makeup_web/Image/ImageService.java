package com.smwhc.smart_makeup_web.Image;

import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.smwhc.smart_makeup_web.Board.Board;

@Service
public class ImageService {
    @Autowired
    private final ImageRepository imageRepository;

    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    // 1. 게시판 이미지 링크 저장
    public void saveBoardImageLink(Board board, String image) {
        // 이미지를 저장하기 위한 객체
        Image img = new Image();
        
        img.setBoard(board);        // 객체에 게시판 아이디 저장
        img.setImage_link(image);   // 객체에 이미지 링크 저장

        imageRepository.save(img);  // 객체를 레포지토리에 저장
    }
    
    // 2. 이미지 불러오기
    public List<Image> getImageUrlByBoardId(Long board_id) {
        List<Image> images = imageRepository.findByBoardId(board_id);
    
        if (images.isEmpty()) {     // 이미지가 비었었는지 확인
            return Collections.emptyList(); // 비어있는 리스트 반환
        }
        
        return images; // 이미지 리스트 반환
    }
    // 3. 이미지 삭제하기
    public void deleteImage(Image image) {
        imageRepository.delete(image);
    }

    // 4. 이미지 찾기
    public Image findByImage(ImageDTO imageDTO) {
        Image image = imageRepository.findById(imageDTO.getImage_code()).get();
        return image;
    }
}
