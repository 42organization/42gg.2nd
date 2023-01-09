package com.example.demo.controller;

import com.example.demo.dto.PostsSaveRequestDto;
import com.example.demo.service.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/posts")    //공통적인 부분은 RequestMapping으로 처리.
public class PostsController {
    private final PostsService postsService;

    /*글 작성하기*/
    @PostMapping()
    public Long postsOrderAdd(@RequestBody PostsSaveRequestDto requestDto){
        return postsService.AddOrderPosts(requestDto);
    }

    /*글 보여주기*/
    @GetMapping("")
    public void postsOrderDetails(Model model){
    }

    /*글 수정*/
    @PutMapping("/")
    public void postsOrderModify(){

    }

    /*글 삭제*/
    @DeleteMapping("/{id}")
    public void postsOrderRemove(@PathVariable Long id){
        postsService.RemoveOrderPosts(id);
    }

    /*글 목록*/
    public void postsOrderList(){

    }

}
