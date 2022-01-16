package com.myapp.board.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.myapp.board.domain.BoardDTO;
import com.myapp.board.domain.FileDTO;

public interface BoardService {
	
	public Integer getBoardListCount(BoardDTO params);
	
	public List<BoardDTO> getBoardList(BoardDTO params);

	public boolean setBoard(BoardDTO to);

	public boolean setBoard(BoardDTO to, MultipartFile[] files);

	public BoardDTO getBoard(Long num);
	
	public int modifyBoard(BoardDTO params);
	
	public int modifyBoard(BoardDTO params, MultipartFile[] files);

	public int removeBoard(Long num);
	
	public List<FileDTO> getAttachFileList(Long boardNum);

	public FileDTO getAttachFileDetail(Long fileNum);

}
