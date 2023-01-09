package com.example.demo.controller;

import com.example.demo.service.PostsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostsController {

    @Autowired
    private PostsService postsService;

    /*글 작성*/
    @GetMapping("/posts/write")
    public String postsorderAdd(){
        return "";   //html 파일 입력
    }


}
