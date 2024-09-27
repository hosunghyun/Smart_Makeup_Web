package com.smwhc.smart_makeup_web.Board;

import com.smwhc.smart_makeup_web.User.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "board")
@Getter
@Setter
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long board_id;

    // user 테이블의 PK인 user_id를 외래키로 사용
    @ManyToOne // 일대다 관계 표현 User 한명이 makeup 여러개를 가질 수 있다.
    @JoinColumn(name = "user_id", nullable = false) // 외래키 제약조건
    private User user;

    // 게시판 글의 제목
    @Column(name = "title", nullable = false, length = 20)
    private String title;

    // 게시판 글의 내용
    @Column(name = "content_text", nullable = false)
    private String content_text;

    // 생성자
    public Board() {}
    public Board(User user, String title, String content_text) {
        this.user = user;
        this.title = title;
        this.content_text = content_text;
    }
}
