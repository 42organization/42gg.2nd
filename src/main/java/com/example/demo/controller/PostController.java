package com.example.demo.controller;

import com.example.demo.dto.PostRequestDto;
import com.example.demo.dto.PostResponseDto;
import com.example.demo.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("post")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping()
    public PostResponseDto getPostById(Long postId) {
        return postService.getPostById(postId);
    }

    @PostMapping()
    ResponseEntity<Long> savePost(@RequestBody PostRequestDto postRequestDto) {
        Long id = postService.savePost(postRequestDto.getWriter(), postRequestDto.getInfo());
        return new ResponseEntity<Long>(id, HttpStatus.OK);
    }

    @PatchMapping()
    ResponseEntity<Long> modifyPost(@RequestBody PostRequestDto postRequestDto) {
        Long id = postService.modifyPost(postRequestDto.getPostId(), postRequestDto.getInfo());
        return new ResponseEntity<Long>(id, HttpStatus.OK);
    }

    @DeleteMapping()
    ResponseEntity<Long> deletePost(Long postId) {
        Long id = postService.deletePost(postId);
        return new ResponseEntity<Long>(id, HttpStatus.OK);
    }

}
