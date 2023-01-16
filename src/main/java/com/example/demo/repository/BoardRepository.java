package com.example.demo.repository;

import com.example.demo.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository <Board, Integer>{
    List<Board> findByTitleContaining(String searchKeyword);
}
//.