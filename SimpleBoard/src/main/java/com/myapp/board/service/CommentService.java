package com.myapp.board.service;

import java.util.List;

import com.myapp.board.domain.CommentDTO;

public interface CommentService {

	public boolean registerComment(CommentDTO params);

	public int modifyComment(CommentDTO params);

	public boolean deleteComment(Long commentNum);

	public List<CommentDTO> getCommentList(CommentDTO params);
	
}
