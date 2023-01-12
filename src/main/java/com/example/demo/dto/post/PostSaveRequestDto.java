package com.example.demo.dto.post;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PostSaveRequestDto {
    private Long memberId;
    private String title;
    private String info;
    private String tag;
}
