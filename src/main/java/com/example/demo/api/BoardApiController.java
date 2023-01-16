package com.example.demo.api;

import com.example.demo.domain.Board;
import com.example.demo.domain.BoardDeleteDto;
import com.example.demo.dto.BoardDto;
import com.example.demo.service.BoardService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BoardApiController {
    private final BoardService boardService;

    @GetMapping("/api/board-list")
    public Wrapper board_list() {
        List<Board> boardList = boardService.findBoards();
        List<BoardDto> boardDtoList = boardList.stream().map(x -> new BoardDto(x)).collect(Collectors.toList());
        return new Wrapper(boardDtoList);
    }
    @GetMapping("/api/board-detail/{boardId}")
    public Wrapper board_detail(@PathVariable("boardId") Long boardId){
        Board board = boardService.findOne(boardId);
        BoardDto boardDto = new BoardDto(board);
        return new Wrapper(boardDto);
    }
    @PostMapping("/api/create-board")
    public ResponseEntity create_board(@RequestBody BoardDto boardDto) {
        System.out.println("create_board/board = " + boardDto);
        HttpHeaders headers = new HttpHeaders();
        Map<String, String> body = new HashMap<>();
        HttpStatus status = HttpStatus.CREATED;
        try {
            Board board = new Board(
                    boardDto.getId(),
                    boardDto.getTitle(),
                    boardDto.getContent()
            );
            boardService.create(board);
        } catch (Exception exception) {
            status = HttpStatus.BAD_REQUEST;
            System.out.println("create_board/exception" + exception);
        }
        return new ResponseEntity(body, headers, status);
    }
    @PutMapping("/api/update-board")
    public ResponseEntity update_board(@RequestBody BoardDto boardDto) {
        System.out.println("update_board/boardDto = " + boardDto);
        HttpHeaders headers = new HttpHeaders();
        Map<String, String> body = new HashMap<>();
        HttpStatus status = HttpStatus.NO_CONTENT;
        try {
            boardService.update(boardDto.getId(), boardDto.getTitle(), boardDto.getContent());
        } catch (Exception exception) {
            status = HttpStatus.BAD_REQUEST;
            System.out.println("update_board/exception = " + exception);
        }
        return new ResponseEntity(body, headers, status);
    }
    @DeleteMapping("/api/delete-board")
    public ResponseEntity delete_board(@RequestBody BoardDeleteDto boardDeleteDto) {
        System.out.println("delete/board/boardDeleteDto = " + boardDeleteDto);
        HttpHeaders headers = new HttpHeaders();
        Map <String, String> body = new HashMap<>();
        HttpStatus status = HttpStatus.NO_CONTENT;
        try {
            Board board = boardService.findOne(boardDeleteDto.getId());
            boardService.delete(board);
        } catch (Exception exception) {
            status = HttpStatus.BAD_REQUEST;
            System.out.println("delete_board/exception = " + exception);
        }
        return new ResponseEntity(body, headers, status);
    }
}
