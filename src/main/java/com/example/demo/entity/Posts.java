package com.example.demo.entity;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@DynamicInsert   //insert시 null인 필드 제외
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

    @ColumnDefault("0")
    private Long likecount;

    @Builder
    public Posts(String title, String content, String author, Long likecount){
        this.title = title;
        this.content = content;
        this.author = author;
        this.likecount = likecount;
    }

    //Posts 게시글 수정
    public void update(String title, String content){
        this.title = title;
        this.content = content;
    }

    //Posts 좋아요
    public void dolike(Long likecount){
        this.likecount = likecount;
    }
}
