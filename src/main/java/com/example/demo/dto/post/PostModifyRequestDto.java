package com.example.demo.dto.post;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter

public class PostModifyRequestDto {
    private Long postId;
    private String info;
    private String tag;
}
