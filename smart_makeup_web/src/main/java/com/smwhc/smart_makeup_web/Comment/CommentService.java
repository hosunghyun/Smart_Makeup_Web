package com.smwhc.smart_makeup_web.Comment;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    @Autowired
    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    // 1. 댓글 작성
    public void saveComment(Comment comment) {  // 댓글을 받아서 저장하기
        commentRepository.save(comment);
    }
    // 2. 댓글 수정

    // 3. 댓글 삭제
    public void deletecomment(Long comment_id) {
        Comment comment = commentRepository.findById(comment_id).get();
        commentRepository.delete(comment);
    }

    // 4. 게시판에 있는 댓글 list 형식으로 반환하는 부분
    public List<Comment> findByComments(String board_id) {
        List<Comment> contents;     // 배열 선언
        Long boardid = Long.parseLong(board_id);    // 게시판 아이디를 레포지토리에서 찾을 수 있게 Long 형으로 형변환
        contents = commentRepository.findByBoardId(boardid);    // 레포지토리에서 Board의 Id로 댓글들 찾기
        return contents;    // 게시판 아이디에 맞는 댓글을 전부 반환
    }

    public Comment findById(Long id) {
        return commentRepository.findById(id).get();
    }
}
