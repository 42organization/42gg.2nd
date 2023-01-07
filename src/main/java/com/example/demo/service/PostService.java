package com.example.demo.service;

import com.example.demo.domain.Post;
import com.example.demo.dto.PostResponseDto;
import com.example.demo.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {
    private final PostRepository postRepository;
    @Transactional(readOnly = true)
    public PostResponseDto getPostById(Long postId) {
        Optional<Post> opPost = postRepository.findById(postId);
        Post findedPost = opPost.orElseThrow(RuntimeException::new);
        return new PostResponseDto(findedPost);
    }

    public Long savePost(String writer, String info) {
        Post post = new Post(info, writer);
        postRepository.save(new Post(info, writer));
        return post.getId();
    }

    public Long modifyPost(Long postId, String info) {
        Optional<Post> opPost = postRepository.findById(postId);
        Post findedPost = opPost.orElseThrow(RuntimeException::new);
        findedPost.modifyPost(info);
        return findedPost.getId();
    }

    public Long deletePost(Long postId) {
        postRepository.deleteById(postId);
        return postId;
    }
}
