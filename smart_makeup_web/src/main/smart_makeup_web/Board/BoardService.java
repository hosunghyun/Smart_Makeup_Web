package com.smwhc.smart_makeup_web.Board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardService {
    @Autowired
    private final BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }
    
    // 1. 게시판 작성

    // 2. 게시판 수정

    // 3. 게시판 삭제

    // 4. 게시판 보기
}
