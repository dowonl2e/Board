package com.myapp.board.service;

import java.util.List;

import com.myapp.board.domain.BoardDTO;

public interface BoardService {
	
	public List<BoardDTO> getBoardList(BoardDTO to);

	public boolean setBoard(BoardDTO to);
	
	public BoardDTO getBoard(Long num);
	
	public int modifyBoard(BoardDTO to);

	public int removeBoard(BoardDTO to);
	

}
