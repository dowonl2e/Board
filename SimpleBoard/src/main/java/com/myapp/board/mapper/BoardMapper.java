package com.myapp.board.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.myapp.board.domain.BoardDTO;

/*
 * 기존 Spring은 DAO 클래스에 @Repository 애너테이션을 선언해서 해당 클래스가 DB와 통신하는 클래스임을 나타냈음
 * MyBatis 인터페이스에 @Mapper를 선언함으로서 이 클래스는 데이트베이스와 통신하는 클래스로 다른 로직은 필요 없음 
 */
@Mapper
public interface BoardMapper {
	
	public List<BoardDTO> selectBoardList(BoardDTO to);
	
	public Integer selectBoardListCount(BoardDTO to);
	
	public int insertBoard(BoardDTO to);
	
	public BoardDTO selectBoard(Long num);
	
	public int updateBoard(BoardDTO to);

	public int updateBoardVisitCnt(Long num);
	
	public int deleteBoard(BoardDTO to);
}