package com.example.demo.dto.post;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostSaveRequestDto {
    private Long memberId;
    private String title;
    private String info;
    private String tag;

}
