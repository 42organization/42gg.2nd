package com.example.demo.service;

import com.example.demo.domain.Board;
import com.example.demo.repository.BoardRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    public List<Board> findBoards() {
        return boardRepository.findAll();
    }

    public Board findOne(Long boardId) {
        return boardRepository.findById(boardId).orElseThrow(NullPointerException::new);
    }
    @Transactional
    public void create(Board board) {
        boardRepository.save(board);
    }
    @Transactional
    public void update(Long id, String title, String content) {
        Board findBoard =
                boardRepository.findById(id).orElseThrow(NullPointerException::new);
        findBoard.setTitle(title);
        findBoard.setContent(content);
    }
    @Transactional
    public void delete(Board board) {
        boardRepository.delete(board);
    }

}
