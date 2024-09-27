package com.smwhc.smart_makeup_web.Comment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDTO {
    private Long comment_id;
    private String user_id;
    private String board_id;
    private String comment_content;

    // 생성자
    public CommentDTO() {}
    public CommentDTO(String user_id, String board_id, String comment_content) {
        this.user_id = user_id;
        this.board_id = board_id;
        this.comment_content = comment_content;
    }
    public CommentDTO(Long comment_id, String user_id, String board_id, String comment_content) {
        this.comment_id = comment_id;
        this.user_id = user_id;
        this.board_id = board_id;
        this.comment_content = comment_content;
    }
}
