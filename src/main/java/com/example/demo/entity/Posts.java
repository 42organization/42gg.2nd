package com.example.demo.entity;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class Posts extends BaseTimeEntity{

    @Id   //테이블의 pk역할
    @GeneratedValue(strategy = GenerationType.IDENTITY)    //기본키 생성을 데이터베이스에 위임(데이터베이스에 의존적)ㄴ
    private Long id;

    @Column
    private String title;

    @Column
    private String content;

    @Column
    private String author;

    @Builder
    public Posts(String title, String content, String author){
        this.title = title;
        this.content = content;
        this.author = author;
    }

    //Posts 게시글 수정
    public void update(String title, String content){
        this.title = title;
        this.content = content;
    }
}
