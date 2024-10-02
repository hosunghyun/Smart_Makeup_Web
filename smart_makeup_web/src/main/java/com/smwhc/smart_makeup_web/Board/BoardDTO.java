package com.smwhc.smart_makeup_web.Board;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardDTO {
    private Long board_id;
    private String member_id;
    private String image_link;
    private String title;
    private String content_text;

    // 생성자
    public BoardDTO() {}
    public BoardDTO(String member_id, String title, String content_text) {
        this.member_id = member_id;
        this.title = title;
        this.content_text = content_text;
    }
    public BoardDTO(Long board_id, String member_id, String title, String content_text) {
        this.board_id = board_id;
        this.member_id = member_id;
        this.title = title;
        this.content_text = content_text;
    }
}
