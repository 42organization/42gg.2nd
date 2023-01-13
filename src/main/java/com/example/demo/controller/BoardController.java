package com.example.demo.controller;

import com.example.demo.entity.Board;
import com.example.demo.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
	public List<Board> BoardView(String searchKeyword) {
		System.out.println("/board/list GET 요청");

		List<Board> list = null;
		if (searchKeyword != null)
			list = boardservice.boardSearchList(searchKeyword);
		else
			list = boardservice.boardList();
		return list;
	}


	@GetMapping("/board/{id}/view")
	public Board boardView(@PathVariable Integer id) {
		return boardservice.boardView(id);
	}
	@PostMapping("/board/write")
	public String boardWrite(Board board, MultipartFile file) throws Exception{
		System.out.println("boardWrite요청");
		boardservice.write(board, file);
		return "";
	}

	@DeleteMapping("board/{id}/delete")
	public String boardDelete(Integer id) {
		boardservice.boardDelete(id);

		return "";
	}
	
	@PutMapping("board/{id}/update")
	public String boardUpdate(@PathVariable Integer id,
							  	Board board,
							  	MultipartFile file) throws Exception {
		Board boardTemp = boardservice.boardView(id);
		boardTemp.setTitle(board.getTitle());
		boardTemp.setContent(board.getContent());

		boardservice.write(boardTemp, file );
		return "";
		//.
	}
}//@Valid
