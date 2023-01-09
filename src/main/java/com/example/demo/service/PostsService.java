package com.example.demo.service;

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

    //Posts 글 등록
    @Transactional  //DB건들이는 함수는 transactionl 어노테이션 붙여줌
    public void AddOrderPosts(Posts posts){
        postsRepository.save(posts);
    }

    //Posts 글 리스트 처리
    public Page<Posts> ListPosts(Pageable pageable){
        return postsRepository.findAll(pageable);
    }
    
    //Posts 특정 게시글 불러오기
    public Posts FindOrderPosts(Long id){
        return postsRepository.findById(id).get();
    }

    //Posts 특정 게시글 삭제
    @Transactional
    public void RemoveOrderPosts(Long id){
        postsRepository.deleteById(id);
    }
}
