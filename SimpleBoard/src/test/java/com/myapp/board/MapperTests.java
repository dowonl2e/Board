package com.myapp.board;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myapp.board.domain.BoardDTO;
import com.myapp.board.mapper.BoardMapper;

@SpringBootTest
class MapperTests {

	@Autowired
	private BoardMapper boardMapper;
	
	@Test
	public void insertBoardTest() {
		try {
			
			BoardDTO to = new BoardDTO();
			to.setTitle("2번 게시글 제목");
			to.setContent("2번 게시글 내용");
			to.setWriter("테스터2");
	
			int result = boardMapper.insertBoard(to);
			System.out.println("결과는 " + result);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void selectBoardListTest() {
		try {
			int boardcount = boardMapper.selectBoardListCount(null);
			if(boardcount > 0) {
				List<BoardDTO> boardlist = boardMapper.selectBoardList(null);
				if(!CollectionUtils.isEmpty(boardlist)) {
					System.out.println("게시물 목록");
					for(BoardDTO boardvo : boardlist) {
						System.out.println("---------------------");
						System.out.println("번호 : " + boardvo.getNum());
						System.out.println("타이틀 : " + boardvo.getTitle());
						System.out.println("내용 : " + boardvo.getContent());
						System.out.println("등록일 : " + boardvo.getInsertTime());
						System.out.println("---------------------");
					}
				}
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void updateBoardTest() {
		try {
			BoardDTO boarddto = new BoardDTO();
			boarddto.setNum((long)1);
			boarddto.setTitle("1번 게시물 제목 수정");
			boarddto.setContent("1번 게시물 내용 수정");
			boarddto.setWriter("수정자");
			
			int result = boardMapper.updateBoard(boarddto);
			if(result == 1) {
				BoardDTO boardvo = boardMapper.selectBoard((long)1);
				
				String boardJson = new ObjectMapper().writeValueAsString(boardvo);
				
				System.out.println("수정 후 게시물 조회");
				System.out.println("---------------");
				System.out.println(boardJson);
				System.out.println("---------------");
				
			}
			else {
				System.out.println("업데이트 실패");
			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void selectBoardTest() {
		try {
			BoardDTO boardvo = boardMapper.selectBoard((long)1);
			
			String boardJson = new ObjectMapper().writeValueAsString(boardvo);
			
			System.out.println("게시물 조회");
			System.out.println("---------------");
			System.out.println(boardJson);
			System.out.println("---------------");
			
			if(!ObjectUtils.isEmpty(boardvo)) {
				int result = boardMapper.updateBoardVisitCnt((long)1);
				if(result == 1) {
					System.out.println("조회 수 업데이트 성공");
				}
				else {
					System.out.println("조회 수 업데이트 실패");
				}
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}
