package com.example.demo.controller;

import com.example.demo.repository.PostsRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PostsControllerTest {

    @Autowired
    private PostsRepository postsRepository;

    @AfterEach
    public void reset(){
        postsRepository.deleteAll();

    }
    @Test
    void postsOrderAdd() {
    }

    @Test
    void postsOrderDetails() {
    }

    @Test
    void postsOrderModify() {
    }

    @Test
    void postsOrderRemove() {
    }

    @Test
    void postsOrderList() {
    }

    @Test
    void postsAddLike() {
    }

    @Test
    void postsRemoveLike() {
    }
}