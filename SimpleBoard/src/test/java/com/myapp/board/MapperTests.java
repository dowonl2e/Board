package com.myapp.board;

import java.time.format.DateTimeFormatter;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.CollectionUtils;

import com.myapp.board.domain.BoardDTO;
import com.myapp.board.mapper.BoardMapper;
import com.mysql.cj.x.protobuf.MysqlxCrud.Collection;

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
			System.out.println("결과는 " + result + "입니다.");
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
						System.out.println("등록일 : " + boardvo.getInsert_time());
						System.out.println("---------------------");
					}
				}
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}
