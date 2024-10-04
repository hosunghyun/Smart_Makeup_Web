package com.smwhc.smart_makeup_web.Comment;

import com.smwhc.smart_makeup_web.Board.Board;
import com.smwhc.smart_makeup_web.Member.Member;

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
@Table(name = "comment")
@Getter
@Setter
public class Comment {
    // 기본키
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    // User 테이블의 PK인 user_id 를 외래키로 사용
    @ManyToOne // 일대다 관계 표현 User 한 명이 Comment 여러 개를 가질 수 있다.
    @JoinColumn(name = "member_id", nullable = false) // 외래키 제약조건
    private Member member;

    // Board 테이블의 PK인 product_id 를 외래키로 사용
    @ManyToOne  // 일대다 관계 표현 Board 한 개가 Comment 여러개를 가질 수 있다.
    @JoinColumn(name = "board_id", nullable = false) // 외래키 제약조건
    private Board board;

    @Column(name = "comment_content", nullable = false)
    private String comment_content;

    // 생성자
    public Comment() {}
    public Comment(Member member, Board board, String comment_content) {
        this.member = member;
        this.board = board;
        this.comment_content = comment_content;
    }
}
