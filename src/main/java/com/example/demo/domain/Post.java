package com.example.demo.domain;

import com.example.demo.domain.util.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;
    private String info;
    private Long viewCnt;
    private String tag;
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    List<Like> likeList = new ArrayList<>();

    private Post(Member m, String info, String title) {
        this.info = info;
        this.title = title;
        this.member = m;
        this.tag = "";
        this.viewCnt = Long.valueOf(0);
    }

    private Post(Member m, String info, String title, String tag) {
        this.info = info;
        this.title = title;
        this.tag = tag;
        this.member = m;
        this.viewCnt = Long.valueOf(0);
    }

    public static Post createPostWithoutTag(Member m, String info, String title) {
        return new Post(m, info, title);
    }

    public static Post createPost(Member m, String info, String title, String tag) {
        return new Post(m, info, title, tag);
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
