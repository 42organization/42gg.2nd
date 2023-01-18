package com.example.demo.controller;

import com.example.demo.entity.Board;
import com.example.demo.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/board")
public class BoardController {

	@Autowired
	private BoardService boardservice;
	
	@GetMapping("/writeform")
	public String boardWriteForm() {
		return "boardwrite";
	}

	@GetMapping("/list")
	public List<Board> BoardView(String searchKeyword) {
		System.out.println("/board/list GET 요청");

		List<Board> list = null;
		if (searchKeyword != null)
			list = boardservice.boardSearchList(searchKeyword);
		else
			list = boardservice.boardList();
		return list;
	}


	@GetMapping("/{id}/view")
	public Board boardView(@PathVariable Integer id) {
		return boardservice.boardView(id);
	}

	@PostMapping("/{id}/addLike")
	public ResponseEntity<Integer> addLike(@PathVariable Integer id) {
		boardservice.boardAddLike(id);
		return new ResponseEntity<Integer>(id, HttpStatus.OK);
	}

	@PostMapping("/{id}/addView")
	public ResponseEntity<Integer> addView(@PathVariable Integer id) {
		boardservice.boardAddView(id);
		return new ResponseEntity<Integer>(id, HttpStatus.OK);
	}

	@PostMapping("/write")
	public ResponseEntity<Integer> boardWrite(Board board, MultipartFile file) throws Exception{
		System.out.println("boardWrite요청");
		boardservice.write(board, file);
		return new ResponseEntity<Integer>(board.getId(), HttpStatus.OK);
	}

	@DeleteMapping("/{id}/delete")
	public ResponseEntity<Integer> boardDelete(Integer id) {
		boardservice.boardDelete(id);

		return new ResponseEntity<Integer>(id, HttpStatus.OK);
	}
	
	@PutMapping("/{id}/update")
	public String boardUpdate(@PathVariable Integer id,
							  	Board board,
							  	MultipartFile file) throws Exception {
		Board boardTemp = boardservice.boardView(id);
		boardTemp.setTitle(board.getTitle());
		boardTemp.setContent(board.getContent());
		boardTemp.setFilename(board.getFilename());
		boardTemp.setFilepath(board.getFilepath());

		boardservice.write(boardTemp, file);
		return "";
	}
}//@Valid
