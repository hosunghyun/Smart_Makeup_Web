package com.smwhc.smart_makeup_web.Image;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.smwhc.smart_makeup_web.Board.Board;
import com.smwhc.smart_makeup_web.Board.BoardRepository;

@Service
public class ImageService {
    @Autowired
    private final ImageRepository imageRepository;
    private final BoardRepository boardRepository;

    public ImageService(ImageRepository imageRepository, BoardRepository boardRepository) {
        this.imageRepository = imageRepository;
        this.boardRepository = boardRepository;
    }

    // 1. 게시판 이미지 링크 저장
    public void saveBoardImageLink(Long board_id, String image) {
        Image img = new Image();
        img.setBoard(boardRepository.findById(board_id).get());
        img.setImage_link(image);
        imageRepository.save(img);
    }
    // 2. 이미지 불러오기
    public String getImageUrlByBoardId(Long board_id) {
        return imageRepository.findById(board_id)
                .map(image -> {
                    String imageLink = image.getImage_link();
                    return imageLink != null ? imageLink : "image_not_found"; // null일 경우 기본 URL 반환
                }).orElse("image_not_found"); // Optional이 비어있을 경우 처리
    }
    // 3. 이미지 삭제하기

    // 4. 이미지 수정하기
}
