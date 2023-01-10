package com.example.demo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
public class AllPostResponseDto {
    private List<PostResponseDto> dataList;
    private Long postCnt;

    public AllPostResponseDto (List<PostResponseDto> dataList) {
        this.dataList = dataList;
        this.postCnt = Long.valueOf(dataList.size());
    }
}
