package com.example.demo.dto;

import com.example.demo.domain.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Setter
@Getter
public class PostResponseDto {
    private String writer;

    private String info;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public PostResponseDto(Post post) {
        this.writer = post.getWriter();
        this.info = post.getInfo();
        this.createdDate = post.getCreatedDate();
        this.modifiedDate = post.getModifiedDate();
    }

    @Override
    public String toString() {
        return "PostResponseDto{" +
                "writer='" + writer + '\'' +
                ", info='" + info + '\'' +
                ", createdDate=" + createdDate +
                ", modifiedDate=" + modifiedDate +
                '}';
    }
}
