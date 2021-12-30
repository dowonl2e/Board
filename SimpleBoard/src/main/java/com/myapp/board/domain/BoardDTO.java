package com.myapp.board.domain;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BoardDTO extends CommonDTO {

	private Long num; //번호 
	
	private String title; //제목 
	
	private String content; //내용 
	
	private String writer; //작성
	
	private int visitCnt; //조회수 
	
	private String noticeYn; //공지여부 
	
	private String secretYn; //비밀글 여부
	
}
