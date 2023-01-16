package com.example.demo.controller;

import com.example.demo.domain.Like;
import com.example.demo.domain.Member;
import com.example.demo.domain.Post;
import com.example.demo.dto.like.LikeRequestDto;
import com.example.demo.dto.post.AllPostResponseDto;
import com.example.demo.dto.post.PostModifyRequestDto;
import com.example.demo.dto.post.PostResponseDto;
import com.example.demo.dto.post.PostSaveRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import javax.persistence.EntityManager;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class PostControllerTest {

    private static final String BASE_URL = "/post";

    @Autowired
    private EntityManager em;
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("post id로 찾기")
    void getPostById() throws Exception {
        Member member = saveAndGetMember("aaaaa", "bbbbb");
        Post post = saveAndGetPost(member, "info", "title", "tag");

        MvcResult mvcResult = mvc.perform(get(BASE_URL + "?postId=" + post.getId()))
                .andExpect(status().isOk())
                .andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();

        PostResponseDto postResponseDto = objectMapper.readValue(contentAsString, PostResponseDto.class);
        assertThat(postResponseDto.getUsername()).isEqualTo(member.getUsername());
        assertThat(postResponseDto.getTitle()).isEqualTo(post.getTitle());

    }


    @Test
    @DisplayName("조회수 테스트")
    void incViewCnt() throws Exception {
        Member member = saveAndGetMember("aaaaa", "bbbbb");
        Post post = saveAndGetPost(member, "info", "title", "tag");

        System.out.println(post.getClass());
        System.out.println(post.getViewCnt());
        String url = BASE_URL + "/view?postId=" + post.getId();
        mvc.perform(patch(url))
                .andExpect(status().isOk())
                .andReturn();
        assertThat(post.getViewCnt()).isEqualTo(1);
    }

    @Test
    @DisplayName("모든 post 조회")
    void getAllPosts() throws Exception {
        Member member = saveAndGetMember("aaaaa", "bbbbb");
        Post post = saveAndGetPost(member, "info", "title", "tag");
        Post post2 = saveAndGetPost(member, "info", "title", "tag");

        String url = BASE_URL + "/all";
        MvcResult mvcResult = mvc.perform(get(url))
                .andExpect(status().isOk())
                .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        AllPostResponseDto allPostResponseDto = objectMapper.readValue(contentAsString, AllPostResponseDto.class);
        assertThat(allPostResponseDto.getPostCnt()).isEqualTo(2);

    }

    @Test
    @DisplayName("post 저장 테스트")
    void savePost() throws Exception {
        Member member = saveAndGetMember("aaaaa", "bbbbb");

        String title = "title";
        String info = "content";
        String tag = "tag";
        PostSaveRequestDto postSaveRequestDto = new PostSaveRequestDto(member.getId(),
                title, info, tag);

        String url = BASE_URL;
        String data = objectMapper.writeValueAsString(postSaveRequestDto);
        MvcResult mvcResult = mvc.perform(post(url).content(data)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        String id = mvcResult.getResponse().getContentAsString();
        Post savedPost = em.createQuery("select p from Post p where p.id=:postId", Post.class)
                .setParameter("postId", Long.valueOf(id)).getSingleResult();
        assertThat(savedPost.getTag()).isEqualTo(tag);
        assertThat(savedPost.getInfo()).isEqualTo(info);
        assertThat(savedPost.getTitle()).isEqualTo(title);

    }

    @Test
    @DisplayName("post 수정 테스트")
    void modifyPost() throws Exception{
        Member member = saveAndGetMember("aaaaa", "bbbbb");
        Post post = saveAndGetPost(member, "info", "title", "tag");

        String changedInfo = "asdasd";
        String changedTag = "tttt";

        PostModifyRequestDto postModifyRequestDto = new PostModifyRequestDto(post.getId(), changedInfo, changedTag);
        String jsonData = objectMapper.writeValueAsString(postModifyRequestDto);

        String url = BASE_URL;
        MvcResult mvcResult = mvc.perform(patch(url).content(jsonData)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        String resp = mvcResult.getResponse().getContentAsString();
        assertThat(Long.valueOf(resp)).isEqualTo(post.getId());

        Post modifiedPost = em.createQuery("select p from Post p where p.id=:postId", Post.class)
                .setParameter("postId", Long.valueOf(resp)).getSingleResult();
        assertThat(modifiedPost.getInfo()).isEqualTo(changedInfo);
        assertThat(modifiedPost.getTag()).isEqualTo(changedTag);
    }

    @Test
    @DisplayName("좋아요 테스트")
    void likePost() throws Exception{
        Member member = saveAndGetMember("aaaaa", "bbbbb");
        Post post = saveAndGetPost(member, "info", "title", "tag");

        LikeRequestDto likeRequestDto = new LikeRequestDto(member.getId(), post.getId());
        String jsonData = objectMapper.writeValueAsString(likeRequestDto);

        String url = BASE_URL + "/like";
        mvc.perform(post(url).content(jsonData)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(member.getLikeList().size()).isEqualTo(1);
        assertThat(member.getLikeList().get(0).getPost().getId()).isEqualTo(post.getId());
    }

    @Test
    @DisplayName("좋아요 취소")
    void unlikePost() throws Exception{
        Member member = saveAndGetMember("aaaaa", "bbbbb");
        Post post = saveAndGetPost(member, "info", "title", "tag");
        Like like = saveAndGetLike(member, post);

        LikeRequestDto likeRequestDto = new LikeRequestDto(member.getId(), post.getId());
        String jsonData = objectMapper.writeValueAsString(likeRequestDto);

        String url = BASE_URL + "/unlike";
        MvcResult mvcResult = mvc.perform(post(url).content(jsonData)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        String s = mvcResult.getResponse().getContentAsString();
        assertThat(Long.valueOf(s)).isEqualTo(like.getId());
        assertThat(member.getLikeList().size()).isEqualTo(0);
    }

    @Test
    @DisplayName("post 삭제 테스트")
    void deletePost() throws Exception {
        Member member = saveAndGetMember("aaaaa", "bbbbb");
        Post post = saveAndGetPost(member, "info", "title", "tag");

        String url = BASE_URL + "?postId=" + post.getId();
        MvcResult mvcResult = mvc.perform(delete(url))
                .andExpect(status().isOk())
                .andReturn();
        String s = mvcResult.getResponse().getContentAsString();
        assertThat(member.getPostList().size()).isEqualTo(0);
        assertThat(Long.valueOf(s)).isEqualTo(post.getId());
    }

    private Member saveAndGetMember(String username, String password) {
        Member member = new Member(username, password);
        em.persist(member);
        return member;
    }

    private Post saveAndGetPost(Member member, String info, String title, String tag) {
        Post post = Post.createPost(member, info, title, tag);
        em.persist(post);
        return post;
    }
    private Like saveAndGetLike(Member member, Post post) {
        Like like =  Like.builder().m(member).p(post).build();
        like.setLikeInMemberAndPost(member, post);
        em.persist(like);
        return like;
    }
}