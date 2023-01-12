package com.example.demo.dto.post;

import com.example.demo.domain.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Setter
@Getter
public class PostResponseDto {

    private String username;
    private String title;
    private String info;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private Long likeCnt;
    private Long viewCnt;
    private String tag;

    public PostResponseDto(Post post) {
        this.username = post.getMember().getUsername();
        this.title = post.getTitle();
        this.info = post.getInfo();
        this.createdDate = post.getCreatedDate();
        this.modifiedDate = post.getModifiedDate();
        this.likeCnt = Long.valueOf(post.getLikeList().size());
        this.viewCnt = post.getViewCnt();
        this.tag = post.getTag();
    }


}
