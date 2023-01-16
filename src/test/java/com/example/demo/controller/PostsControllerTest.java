package com.example.demo.controller;

import com.example.demo.dto.PostsSaveRequestDto;
import com.example.demo.dto.PostsUpdateRequestDto;
import com.example.demo.entity.Posts;
import com.example.demo.repository.PostsRepository;
import org.assertj.core.api.Assert;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) //스프링부트의 내장 서버를 랜덤 포트로 띄우기
class PostsControllerTest {

    @LocalServerPort    //테스트에 포트 번호 삽입
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;   //http요청에 대한 응답을 저장할 객체

    @Autowired
    private PostsRepository postsRepository;

    @AfterEach
    public void reset_repo(){
        postsRepository.deleteAll();

    }
    
    @Test
    @DisplayName("CreatePosts")
    void posts_create() {
        //given
        String title = "title";
        String content = "content";
        PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
                .title(title)
                .content(content)
                .author("author1")
                .build();

        String url = "http://localhost:" + port + "/posts/";

        //when
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class);

        //then
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Posts> postslist = postsRepository.findAll();
        Assertions.assertThat(postslist.get(0).getTitle()).isEqualTo(title);
        Assertions.assertThat(postslist.get(0).getContent()).isEqualTo(content);
        Assertions.assertThat(postslist.get(0).getAuthor()).isEqualTo("author1");
        Assertions.assertThat(postslist.get(0).getLikecount()).isEqualTo(0);
        Assertions.assertThat(postslist.get(0).getViewcount()).isEqualTo(0);
    }

    @Test
    @DisplayName("UpdatePosts")
    void posts_Modify() {
        //given
        Posts savedPosts = postsRepository.save(Posts.builder()
                .title("title1")
                .content("content1")
                .author("author1")
                .build());
        Long updateId = savedPosts.getId();
        String updatedTitle = "title2";
        String updatedContent = "content2";

        PostsUpdateRequestDto requestDto = PostsUpdateRequestDto.builder()
                .title(updatedTitle)
                .content(updatedContent)
                .build();
        String url = "http://localhost:" + port + "/posts/" + updateId;
        HttpEntity<PostsUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);

        //when
        ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Long.class);

        //then
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(responseEntity.getBody()).isGreaterThan(0L);
        List<Posts> postslist = postsRepository.findAll();

        Assertions.assertThat(postslist.get(0).getTitle()).isEqualTo(updatedTitle);
        Assertions.assertThat(postslist.get(0).getContent()).isEqualTo(updatedContent);
    }

    @Test
    @DisplayName("RemovePosts")
    void posts_Remove(){
        //given
        /*
        String title = "title2";
        String content = "content2";
        String author = "author2";

         */
        Posts post1 = postsRepository.save(Posts.builder()
                .title("title1")
                .content("content1")
                .author("author1")
                .build());
        Long deleteId = post1.getId();
/*
        Posts post2 = postsRepository.save(Posts.builder()
                .title(title)
                .content(content)
                .author(author)
                .build());
*/
        String url = "http://localhost:" + port + "/posts/" + deleteId;

        //HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity<Posts> httpEntity = new HttpEntity<>(post1);

        ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.DELETE, httpEntity, Long.class);
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        //Assertions.assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Posts> postlist = postsRepository.findAll();
        Assertions.assertThat(postlist).isEmpty();

    }

    @Test
    @DisplayName("ReadPost")
    void posts_Read(){
        String title = "title";
        String content = "content";
        String author = "author";

        Posts post1 = postsRepository.save(Posts.builder()
                .title(title)
                .content(content)
                .author(author)
                .build());

        String url = "http://localhost:" + port + "/posts/" + post1.getId();

        //when
        ResponseEntity<Posts> responseEntity = restTemplate.getForEntity(url, Posts.class);

        //then
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        Posts posts = responseEntity.getBody();
        Assertions.assertThat(posts.getTitle()).isEqualTo(title);
        Assertions.assertThat(posts.getContent()).isEqualTo(content);
        Assertions.assertThat(posts.getAuthor()).isEqualTo(author);
    }

    @Test
    @DisplayName("AllListPosts")
    void posts_List() {
        Posts post1 = postsRepository.save(Posts.builder()
                .title("title1")
                .content("content1")
                .author("author1")
                .build());

        Posts post2 = postsRepository.save(Posts.builder()
                .title("title2")
                .content("content2")
                .author("author2")
                .build());

        String url = "http://localhost:" + port + "/posts/";
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        List<Posts> postslist = postsRepository.findAll();
        Assertions.assertThat(postslist.size()).isEqualTo(2);
    }

    @Test
    void postsAddLike() {
    }

    @Test
    void postsRemoveLike() {
    }
}