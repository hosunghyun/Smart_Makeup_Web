package com.smwhc.smart_makeup_web.Comment;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    public List<Comment> findByBoardId(Long boarid);
}