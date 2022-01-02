package com.myapp.board.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.myapp.board.domain.CommentDTO;
import com.myapp.board.mapper.CommentMapper;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentMapper commentMapper;
	
	@Override
	public boolean registerComment(CommentDTO params) {
		return (commentMapper.insertComment(params) > 0 ? true : false);
	}
	
	@Override
	public int modifyComment(CommentDTO params) {
		int result = 0;
		
		CommentDTO comment = commentMapper.selectCommentDetail(params.getCommentNum());
		if(ObjectUtils.isEmpty(comment)) {
			result = -1;
		}
		else {
			result = commentMapper.updateComment(params);
		}
		
		return result;
	}

	@Override
	public boolean deleteComment(Long commentNum) {
		return (commentMapper.deleteComment(commentNum) > 0 ? true : false);
	}

	@Override
	public List<CommentDTO> getCommentList(CommentDTO params) {
		List<CommentDTO> commentList = Collections.emptyList();
		
		int commentTotalCount = commentMapper.selectCommentTotalCount(params);
		if(commentTotalCount > 0) {
			commentList = commentMapper.selectCommentList(params);
		}
		
		return commentList;
	}
	
	

}
