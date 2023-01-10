package com.example.demo.service;

import com.example.demo.domain.Post;
import com.example.demo.dto.PostResponseDto;
import com.example.demo.repository.PostRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {
    private final PostRepository postRepository;

    public PostResponseDto getPostById(Long postId) {
        Optional<Post> opPost = postRepository.findById(postId);
        Post findedPost = opPost.orElseThrow(RuntimeException::new);
        findedPost.addViewCount();  //get 메서드에서 조회수 증가시키는 로직 안좋은 것 같음..,,
        return new PostResponseDto(findedPost);
    }

    public Long savePost(String writer, String info, String tag) {
        Post post;
        if (tag == null)
            post = Post.createPostWithoutTag(writer, info);
        else
            post = Post.createPost(writer, info, tag);
        postRepository.save(post);
        return post.getId();
    }

    public Long modifyPost(Long postId, String info, String tag) {
        Optional<Post> opPost = postRepository.findById(postId);
        Post findedPost = opPost.orElseThrow(RuntimeException::new);
        findedPost.modifyPost(info, tag);
        return findedPost.getId();
    }

    public Long deletePost(Long postId) {
        postRepository.deleteById(postId);
        return postId;
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Long likePost(Long postId) {
        Post post = postRepository.findById(postId)
                    .orElseThrow(RuntimeException::new);
        post.addLike();
        return post.getId();
    }

    public Long unlikePost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(RuntimeException::new);
        post.cancelLike();
        return post.getId();
    }
}
