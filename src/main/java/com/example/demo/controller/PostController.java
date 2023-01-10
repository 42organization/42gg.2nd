package com.example.demo.controller;

import com.example.demo.domain.Post;
import com.example.demo.dto.AllPostResponseDto;
import com.example.demo.dto.PostModifyRequestDto;
import com.example.demo.dto.PostSaveRequestDto;
import com.example.demo.dto.PostResponseDto;
import com.example.demo.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping
    public PostResponseDto getPostById(@RequestParam Long postId) {
        return postService.getPostById(postId);
    }

    @GetMapping("/all")
    public AllPostResponseDto getAllPosts() {
        List<Post> postList =  postService.getAllPosts();
        List<PostResponseDto> responseDtos = postList.stream().map(PostResponseDto::new)
                .collect(Collectors.toList());
        return new AllPostResponseDto(responseDtos);
    }

    @PostMapping
    ResponseEntity<Long> savePost(@RequestBody PostSaveRequestDto postSaveRequestDto) {
        Long id = postService.savePost(postSaveRequestDto.getWriter(), postSaveRequestDto.getInfo()
            ,postSaveRequestDto.getTag());
        return new ResponseEntity<Long>(id, HttpStatus.OK);
    }

    @PatchMapping
    ResponseEntity<Long> modifyPost(@RequestBody PostModifyRequestDto postRequestDto) {
        Long id = postService.modifyPost(postRequestDto.getPostId(), postRequestDto.getInfo()
            ,postRequestDto.getTag());
        return new ResponseEntity<Long>(id, HttpStatus.OK);
    }

    @PatchMapping("/like")
    ResponseEntity<Long> likePost(@RequestParam Long postId) {
        Long id = postService.likePost(postId);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @PatchMapping("/unlike")
    ResponseEntity<Long> unlikePost(@RequestParam Long postId) {
        Long id = postService.unlikePost(postId);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @DeleteMapping
    ResponseEntity<Long> deletePost(@RequestParam Long postId) {
        Long id = postService.deletePost(postId);
        return new ResponseEntity<Long>(id, HttpStatus.OK);
    }

}
