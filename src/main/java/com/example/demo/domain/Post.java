package com.example.demo.domain;

import com.example.demo.util.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class Post extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String info;
    private String writer;

    public Post(String info, String writer) {
        this.info = info;
        this.writer = writer;
    }

    public void modifyPost(String info) {
        this.info = info;
    }
}
