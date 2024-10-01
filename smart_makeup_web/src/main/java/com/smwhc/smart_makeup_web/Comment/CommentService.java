package com.smwhc.smart_makeup_web.Comment;

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

    // 2. 댓글 수정

    // 3. 댓글 삭제

    // 4. 댓글 보기
}
