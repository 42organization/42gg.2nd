package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Entity
public class Posts {

    @Id   //테이블의 pk역할
    @GeneratedValue(strategy = GenerationType.IDENTITY)    //기본키 생성을 데이터베이스에 위임(데이터베이스에 의존적)ㄴ
    private Long id;

    @Column
    private String title;

    @Column
    private String content;

    @Column
    private String author;
}
