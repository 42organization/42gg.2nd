package com.example.demo.service;

import com.example.demo.entity.Board;
import com.example.demo.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.UUID;

@Service
public class BoardService {
    @Autowired
    private BoardRepository boardRepository;

    public void write(Board board, MultipartFile file) throws  Exception {

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
    }

    public List<Board> boardList() {
        return boardRepository.findAll();
    }

    public List<Board> boardSearchList(String searchKeyword){
        return boardRepository.findByTitleContaining(searchKeyword);
    }

    public Board boardView(Integer id) {
        return  boardRepository.findById(id).get();
    }

    public void boardDelete(Integer id) {
        boardRepository.deleteById(id);
    }
}
