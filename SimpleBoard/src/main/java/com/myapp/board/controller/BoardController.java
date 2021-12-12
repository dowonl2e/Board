package com.myapp.board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.myapp.board.domain.BoardDTO;
import com.myapp.board.service.BoardService;

@Controller
public class BoardController {

	@Autowired
	private BoardService boardService;
	
	/*
	 * Spring 4.3 이상 기존 RequestMapping의 RequestMethod.GET, RequestMethod.POST를 GetMapping, PostMapping으로 나누어 사용 가능
	 */
	@GetMapping(value = "/board/list")
	public String list(final BoardDTO to, Model model) {
		
		model.addAttribute("boardlist", boardService.getBoardList(to));
		
		return "board/list";
	}
	
}
