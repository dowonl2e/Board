package com.myapp.board.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.myapp.board.domain.BoardDTO;
import com.myapp.board.mapper.BoardMapper;

@Service
public class BoardServiceImpl implements BoardService {

	@Autowired
	private BoardMapper boardMapper;
	
	@Override
	public List<BoardDTO> getBoardList(BoardDTO to) {
		List<BoardDTO> boardList = Collections.emptyList();
		
		int boardlistcount = boardMapper.selectBoardListCount(to);
		if(boardlistcount >= 0) {
			boardList = boardMapper.selectBoardList(to);
		}
		
		return boardList;
	}

	@Override
	public boolean setBoard(BoardDTO to) {
		return (boardMapper.insertBoard(to) < 1 ? false : true);
	}

	@Override
	public BoardDTO getBoard(Long num) {
		return boardMapper.selectBoard(num);
	}
	
	@Override
	public int modifyBoard(BoardDTO to) {
		int result = 0;
		
		BoardDTO vo = boardMapper.selectBoard(to.getNum());
		if(ObjectUtils.isEmpty(vo)) {
			result = -1;
		}
		else {
			result = boardMapper.updateBoard(to);
		}
		return result;
	}
	
	@Override
	public int removeBoard(BoardDTO to) {
		int result = 0;
		
		BoardDTO vo = boardMapper.selectBoard(to.getNum());
		if(ObjectUtils.isEmpty(vo)) {
			result = -1;
		}
		else {
			result = boardMapper.deleteBoard(to);
		}
		
		return result;
	}

}
