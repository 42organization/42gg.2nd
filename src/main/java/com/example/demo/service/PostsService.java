package com.example.demo.service;

import com.example.demo.dto.PostsResponseDto;
import com.example.demo.dto.PostsSaveRequestDto;
import com.example.demo.entity.Posts;
import com.example.demo.repository.PostsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostsService {
    @Autowired
    private PostsRepository postsRepository;

    //글 등록
    @Transactional  //DB건들이는 함수는 transactionl 어노테이션 붙여줌
    public Long AddOrderPosts(PostsSaveRequestDto requestDto){
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    //글 수정
    @Transactional
    public Long ModifyOrderPosts(Long id, PostsSaveRequestDto requestDto){
        Posts posts = postsRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("해당 게시글이 없습니다. id = " + id));
        posts.update(requestDto.getTitle(), requestDto.getContent());
        return id;
    }

    //글 리스트 처리
    public Page<Posts> ListPosts(Pageable pageable){
        return postsRepository.findAll(pageable);
    }
    
    //특정 게시글 불러오기
    public PostsResponseDto FindOrderPosts(Long id){  //클라이언트가 요구한 특정 게시물을 보내서 응답하기.
        Posts entity = postsRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("해당 게시글이 없습니다. id = " + id));  //id로 찾아서 담기.
        return new PostsResponseDto(entity);   //찾은 객체를 응답dto로 생성해서 보내기
    }

    //특정 게시글 삭제
    @Transactional
    public void RemoveOrderPosts(Long id){
        postsRepository.deleteById(id);
    }
}
