package com.example.demo.controller;

import com.example.demo.entity.Board;
import com.example.demo.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BoardController {

	@Autowired
	private BoardService boardservice;
	
	@GetMapping("/board/writeform")
	public String boardWriteForm() {
		return "boardwriteform";
	}

	@GetMapping("/board/list")
	public List<Board> BoardView() {
		System.out.println("/board/list GET 요청");
		return boardservice.boardList();
	}


	@GetMapping("/board/{id}/view")
	public Board boardView(@PathVariable Integer id) {
		return boardservice.boardView(id);
	}
	@PostMapping("/board/write")
	public String boardWrite(Board board){
		System.out.println("boardWrite요청");
		boardservice.write(board);
		return "";
	}

	@DeleteMapping("board/2/delete")
	public String boardDelete(Integer id) {
		boardservice.boardDelete(id);

		return "";
	}
	
	@PutMapping("board/{id}/update")
	public String boardUpdate(@PathVariable Integer id,
							  	Board board) {
		Board boardTemp = boardservice.boardView(id);
		boardTemp.setTitle(board.getTitle());
		boardTemp.setContent(board.getContent());

		boardservice.write(boardTemp);
		return "";
		//...
	}
}
