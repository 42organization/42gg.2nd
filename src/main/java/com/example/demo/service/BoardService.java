package com.example.demo.service;

import com.example.demo.entity.Board;
import com.example.demo.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class BoardService {
    @Autowired
    private BoardRepository boardRepository;

    @Transactional
    public Integer write(Board board, MultipartFile file) throws  Exception {
        if (file != null) {
            String projectPath = System.getProperty("user.dir") + "\\src\\man\\resources\\static.files";
            UUID uuid = UUID.randomUUID();
            String filename = uuid + "_" + file.getOriginalFilename();
            File savefile = new File(projectPath, filename);
            file.transferTo(savefile);
            board.setFilename(filename);
            board.setFilepath("/files/" + filename);
        }
        boardRepository.save(board);
        return board.getId();
    }

    @Transactional(readOnly = true)
    public List<Board> boardList() {
        return boardRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Board> boardSearchList(String searchKeyword){
        return boardRepository.findByTitleContaining(searchKeyword);
    }

    @Transactional(readOnly = true)
    public Board boardView(Integer id) {
        return  boardRepository.findById(id).get();
    }

    @Transactional
    public void boardDelete(Integer id) {
        boardRepository.deleteById(id);
    }

    @Transactional
    public void boardAddLike(Integer id) {
        Board board = boardRepository.findById((id)).orElseThrow(RuntimeException::new);
        board.addLikeCnt();
        boardRepository.save(board);
    }

    @Transactional
    public void boardAddView(Integer id) {
        Board board = boardRepository.findById(id).orElseThrow(RuntimeException::new);
        board.addViewCnt();
        boardRepository.save(board);
    }
}
