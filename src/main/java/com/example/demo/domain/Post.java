package com.example.demo.domain;

import com.example.demo.util.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String info;
    private String writer;
    private Long likeCnt;
    private Long viewCnt;
    private String tag;

    private Post(String info, String writer) {
        this.info = info;
        this.writer = writer;
        this.tag = "";
        this.likeCnt = Long.valueOf(0);
        this.viewCnt = Long.valueOf(0);
    }

    private Post(String info, String writer, String tag) {
        this.info = info;
        this.writer = writer;
        this.tag = tag;
        this.likeCnt = Long.valueOf(0);
        this.viewCnt = Long.valueOf(0);
    }

    public static Post createPostWithoutTag(String info, String writer) {
        return new Post(info, writer);
    }

    public static Post createPost(String info, String writer, String tag) {
        return new Post(info, writer, tag);
    }

    public void addLike() {
        this.likeCnt++;
    }

    public void cancelLike() {
        if (this.likeCnt > 0)
            this.likeCnt--;
    }

    public void addViewCount() {
        this.viewCnt++;
    }

    public void modifyPost(String info, String tag) {
        this.info = info;
        if (tag != null)
            this.tag = tag;
    }
}
