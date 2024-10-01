package com.smwhc.smart_makeup_web.Board;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    public List<Board> getBoardByPage(Integer pageNum,Integer pageSize) {
        // 가져올 페이지 정보를 담을 객체 생성
        Page<Board> board = boardRepository.findAll(PageRequest.of(pageNum, pageSize));

        if (board.isEmpty()) {
            return null;
        }
        return board.getContent();
    }
}
