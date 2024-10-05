package com.smwhc.smart_makeup_web.Board;


import com.smwhc.smart_makeup_web.Image.Image;
import com.smwhc.smart_makeup_web.Makeup.MakeUp;
import com.smwhc.smart_makeup_web.Member.Member;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
    @Column(name = "board_id")
    private Long id;    // 게시판 아이디

    // member 테이블의 PK인 member_id를 외래키로 사용
    @ManyToOne // 일대다 관계 표현 Member 한명이 board 여러개를 가질 수 있다.
    @JoinColumn(name = "member_id", nullable = false) // 외래키 제약조건
    private Member member;

    // 게시판 글의 제목
    @Column(name = "title", nullable = false, length = 50)
    private String title;

    // 게시판 글의 내용
    @Column(name = "content_text")
    private String content_text;

    // 이미지와의 관계를 나타냄
    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    private Set<Image> images;

    // 생성자
    public Board() {}
    public Board(Member member, String title, String content_text) {
        this.member = member;
        this.title = title;
        this.content_text = content_text;
    }
    
}
