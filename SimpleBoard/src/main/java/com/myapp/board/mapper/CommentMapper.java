package com.myapp.board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.myapp.board.domain.CommentDTO;

@Mapper
public interface CommentMapper {

	public int insertComment(CommentDTO params);

	public CommentDTO selectCommentDetail(Long commentNum);

	public int updateComment(CommentDTO params);

	public int deleteComment(Long commentNum);

	public List<CommentDTO> selectCommentList(CommentDTO params);

	public int selectCommentTotalCount(CommentDTO params);

}
