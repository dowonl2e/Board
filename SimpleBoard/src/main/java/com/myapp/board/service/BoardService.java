package com.myapp.board.service;

import java.util.List;

import com.myapp.board.domain.BoardDTO;

public interface BoardService {
	
	public Integer getBoardListCount(BoardDTO params);
	
	public List<BoardDTO> getBoardList(BoardDTO params);

	public boolean setBoard(BoardDTO to);
	
	public BoardDTO getBoard(Long num);
	
	public int modifyBoard(BoardDTO params);

	public int removeBoard(Long num);
	

}
