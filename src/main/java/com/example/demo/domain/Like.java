package com.example.demo.domain;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "t_like")
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Builder
    public Like(Member m, Post p) {
        this.post = p;
        this.member = m;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Like like = (Like) o;
        return id.equals(like.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void setLikeInMemberAndPost(Member m, Post p) {
        m.getLikeList().add(this);
        p.getLikeList().add(this);
    }

    public void deleteLikeInMemberAndPost(Member findMember, Post findPost) {
        findMember.getLikeList().remove(this);
        findPost.getLikeList().remove(this);
    }
}
