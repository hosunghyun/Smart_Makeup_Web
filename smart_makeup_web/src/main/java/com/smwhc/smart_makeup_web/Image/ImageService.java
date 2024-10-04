package com.smwhc.smart_makeup_web.Image;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
    public List<Image> getImageUrlByBoardId(Long board_id) {
        return imageRepository.findByBoard_board_id(board_id);
    }
    // 3. 이미지 삭제하기

    // 4. 이미지 수정하기
}
