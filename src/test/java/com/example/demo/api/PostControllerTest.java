package com.example.demo.api;

import com.example.demo.domain.Post;
import com.example.demo.dto.PostResponseDto;
import com.example.demo.dto.PostSaveRequestDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class PostControllerTest {

    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;
    @PersistenceUnit
    EntityManagerFactory emf;
    EntityManager em;

    @Test
    public void savePostTest() {
        PostSaveRequestDto postRequest = new PostSaveRequestDto("hyunkyu", "info", "tag");

        String url = "http://localhost:" + port + "/post";
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, postRequest, Long.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @Transactional
    public void getPostTest(){
        em = emf.createEntityManager();
        EntityTransaction entityTransaction = em.getTransaction();
        Post post = Post.createPost("content", "hyunkyu", "tag");
        em.persist(post);

        String url = "http://localhost:" + port + "/post?postId=" + post.getId();
        ResponseEntity<PostResponseDto> responseEntity = restTemplate.getForEntity(url, PostResponseDto.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody().getInfo()).isEqualTo("content");
    }
}
