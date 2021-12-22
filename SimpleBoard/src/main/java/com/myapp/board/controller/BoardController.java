package com.myapp.board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.myapp.board.constraint.Method;
import com.myapp.board.domain.BoardDTO;
import com.myapp.board.service.BoardService;
import com.myapp.board.util.UiUtils;

@Controller
public class BoardController extends UiUtils {

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
	
	@GetMapping(value = "/board/write")
	public String write(@RequestParam(value = "num", required=false) Long num, Model model) {
		
		if(num == null) {
			model.addAttribute("board", new BoardDTO());
		}
		else {
			BoardDTO board = boardService.getBoard(num);
			if(ObjectUtils.isEmpty(board)) {
				return "redirect:/board/list"; 
			}
			model.addAttribute("board", board);
		}
		
		return "board/write";
	}
	
	@PostMapping(value = "/board/register")
	public String registerBoard(final BoardDTO params, Model model) {
		try {
			boolean result = boardService.setBoard(params);
			if(!result) {
				return showMessageWithRedirect("게시물 등록에 실패했습니다.", "/board/list", Method.GET, null, model);
			}
		}
		catch (DataAccessException e){
			return showMessageWithRedirect("데이터 처리 과정에 오류가 발생하였습니다.", "/board/list", Method.GET, null, model);
		}
		catch(Exception e) {
			return showMessageWithRedirect("시스템에 문제가 발생하였습니다.", "/board/list", Method.GET, null, model);
		}
		return showMessageWithRedirect("게시물 등록이 완료되었습니다.", "/board/list", Method.GET, null, model);
	}
	
	@GetMapping(value = "/board/view")
	public String view(@RequestParam(value = "num", required=false) Long num, Model model) {
		
		if(num == null) {
			return showMessageWithRedirect("옳바르지 않은 접근입니다.", "/board/list", Method.GET, null, model);
		}
		
		BoardDTO board = boardService.getBoard(num);
		if(ObjectUtils.isEmpty(board)) {
			return showMessageWithRedirect("조회 가능한 게시물이 없습니다.", "/board/list", Method.GET, null, model);
		}
		
		model.addAttribute("board", board);
		
		return "board/view";
	}
	
	@PostMapping(value = "/board/delete")
	public String deleteBoard(final BoardDTO params, Model model) {
		try {
			if(params.getNum() == null) {
				return showMessageWithRedirect("옳바르지 않은 접근입니다.", "/board/list", Method.GET, null, model);
			}
			
			int result = boardService.removeBoard(params);
			if(result <= 0) {
				return showMessageWithRedirect("삭제할 게시물이 없습니다.", "/board/list", Method.GET, null, model);
			}
		}
		catch (DataAccessException e){
			return showMessageWithRedirect("데이터 처리 과정에 오류가 발생하였습니다.", "/board/list", Method.GET, null, model);
		}
		catch(Exception e) {
			return showMessageWithRedirect("시스템에 문제가 발생하였습니다.", "/board/list", Method.GET, null, model);
		}
		return showMessageWithRedirect("게시물이 삭제가 완료되었습니다.", "/board/list", Method.GET, null, model);
	}
}
