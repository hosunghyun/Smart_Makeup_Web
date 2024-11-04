package com.smwhc.smart_makeup_web.Makeup;


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
@Table(name = "makeup")
@Getter
@Setter
public class MakeUp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "makeup_id")
    private Long id;     // 화장의 PK 

    @ManyToOne // 일대다 관계 표현 User 한명이 makeup 여러개를 가질 수 있다.
    @JoinColumn(name = "member_id", nullable = false) // 외래키 제약조건
    private Member member;

    @Column(name = "button_number")
    private Integer button_number;

    // 화장할 때 화장품의 색상코드
    @Column(name = "color_code", length = 20)
    private String color_code;  

    // 화장할 때 화장품의 투명도
    @Column(name = "opacity")
    private Integer opacity;

    // 생성자
    public MakeUp() {}
    public MakeUp(Member member, String color_code, Integer opacity) {
        this.member = member;
        this.color_code = color_code;
        this.opacity = opacity;
    }
}