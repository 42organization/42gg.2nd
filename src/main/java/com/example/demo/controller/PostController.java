package com.example.demo.controller;

import com.example.demo.domain.Post;
import com.example.demo.dto.like.LikeRequestDto;
import com.example.demo.dto.post.AllPostResponseDto;
import com.example.demo.dto.post.PostModifyRequestDto;
import com.example.demo.dto.post.PostSaveRequestDto;
import com.example.demo.dto.post.PostResponseDto;
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

    @PatchMapping("/view")
    public ResponseEntity<Long> incViewCnt(@RequestParam Long postId) {
        Long id = postService.incView(postId);
        return new ResponseEntity<Long>(id, HttpStatus.OK);
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
        Long id = postService.savePost(postSaveRequestDto.getMemberId(), postSaveRequestDto.getTitle(),
                postSaveRequestDto.getInfo(), postSaveRequestDto.getTag());
        return new ResponseEntity<Long>(id, HttpStatus.OK);
    }

    @PatchMapping
    ResponseEntity<Long> modifyPost(@RequestBody PostModifyRequestDto postRequestDto) {
        Long id = postService.modifyPost(postRequestDto.getPostId(), postRequestDto.getInfo()
            ,postRequestDto.getTag());
        return new ResponseEntity<Long>(id, HttpStatus.OK);
    }

    @PostMapping("/like")
    ResponseEntity<Long> likePost(@RequestBody LikeRequestDto data) {
        Long id = postService.likePost(data.getPostId(), data.getMemberId());
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @PostMapping("/unlike")
    ResponseEntity<Long> unlikePost(@RequestBody LikeRequestDto data) {
        Long id = postService.unlikePost(data.getPostId(), data.getMemberId());
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @DeleteMapping
    ResponseEntity<Long> deletePost(@RequestParam Long postId) {
        Long id = postService.deletePost(postId);
        return new ResponseEntity<Long>(id, HttpStatus.OK);
    }

}
