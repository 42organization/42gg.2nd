package com.example.demo.service;

import com.example.demo.domain.Like;
import com.example.demo.domain.Member;
import com.example.demo.domain.Post;
import com.example.demo.dto.post.PostResponseDto;
import com.example.demo.repository.LikeRepository;
import com.example.demo.repository.MemberRepository;
import com.example.demo.repository.PostRepository;
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
    private final MemberRepository memberRepository;
    private final LikeRepository likeRepository;

    public PostResponseDto getPostById(Long postId) {
        Optional<Post> opPost = postRepository.findById(postId);
        Post findedPost = opPost.orElseThrow(()->new RuntimeException("post없음"));
        System.out.println(findedPost.getId());
        return new PostResponseDto(findedPost);
    }

    public Long savePost(Long memberId, String title, String info, String tag) {
        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(()-> new RuntimeException("해당 멤버가 존재하지 않습니다."));
        Post post;
        if (tag == null)
            post = Post.createPostWithoutTag(findMember, info, title);
        else
            post = Post.createPost(findMember, info, title, tag);
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


    public Long likePost(Long postId, Long memberId) {
        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(()->new RuntimeException("해당 멤버가 존재하지 않습니다."));
        Post findPost = postRepository.findById(postId)
                .orElseThrow(()->new RuntimeException("해당 포스트가 존재하지 않습니다."));
        Like like = Like.builder()
                .m(findMember).p(findPost).build();
        like.setLikeInMemberAndPost(findMember, findPost);
        likeRepository.save(like);
        return like.getId();
    }

    public Long unlikePost(Long postId, Long memberId) {
        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(()->new RuntimeException("해당 멤버가 존재하지 않습니다."));
        Post findPost = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("해당 포스트가 존재하지 않습니다"));
        List<Like> likeList = findMember.getLikeList();
        Like targetLike = likeList.stream().filter(l -> l.getId() == postId).findAny()
                .orElseThrow(() -> new RuntimeException("해당 멤버가 좋아요를 누른 포스트가 아닙니다."));
        Long likeId = targetLike.getId();
        targetLike.deleteLikeInMemberAndPost(findMember, findPost);
        likeRepository.delete(targetLike);
        return likeId;
    }

    public Long incView(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(()->new RuntimeException("해당 포스트가 존재하지 않습니다."));
         post.addViewCount();
        return post.getId();
    }
}
