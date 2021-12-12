package com.myapp.board;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myapp.board.domain.BoardDTO;
import com.myapp.board.service.BoardService;

@SpringBootTest
public class ServiceTests {
	
	@Autowired
	private BoardService boardService;
	
	@Test
	public void getBoardListTest() {
		try {
			BoardDTO to = new BoardDTO();
			List<BoardDTO> boardlist = boardService.getBoardList(to);
			if(!CollectionUtils.isEmpty(boardlist)) {
				System.out.println("게시물 목록 조회");
				for(BoardDTO boardvo : boardlist) {
					System.out.println("-----------------------");
					System.out.println("게시물 번호 : " + boardvo.getNum());
					System.out.println("게시물 제목 : " + boardvo.getTitle());
					System.out.println("게시물 내용 : " + boardvo.getContent());
					System.out.println("-----------------------\n");
					
				}
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void setBoardTest() {
		try {
			BoardDTO to = new BoardDTO();
			to.setTitle("서비스에서 추가된 게시물 제목");
			to.setContent("서비스에서 추가된 게시물 내용");
			to.setWriter("서비스");
			
			boolean result = boardService.setBoard(to);
			if(!result) {
				System.out.println("게시물 등록 실패");
			}
			else {
				System.out.println("게시물 등록 성공");
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void modifyBoardTest() {
		try {
			BoardDTO to = new BoardDTO();
			to.setNum((long)3);
			to.setTitle("서비스에서 수정된 게시물 제목");
			to.setContent("서비스에서 수정된 게시물 내용");
			to.setWriter("서비스");
			
			int result = boardService.modifyBoard(to);
			if(result < 0) {
				System.out.println("수정할 게시물이 없음");
			}
			else if(result == 0){
				System.out.println("게시물 수정 실패");
			}
			else {
				System.out.println("게시물 수정 성공");
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void getBoardTest() {
		try {
			BoardDTO boardvo = boardService.getBoard((long)5);
			if(ObjectUtils.isEmpty(boardvo)) {
				System.out.println("게시물이 없음");
			}
			else {
				String boardJson = new ObjectMapper().writeValueAsString(boardvo);
				System.out.println("----------게시물 조회 결과----------");
				System.out.println(boardJson);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}

