package com.myapp.board.domain;


import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BoardDTO {

	private Long num; //번호 
	
	private String title; //제목 
	
	private String content; //내용 
	
	private String writer; //작성
	
	private int visitCnt; //조회수 
	
	private String noticeYn; //공지여부 
	
	private String deleteYn; //삭제여부 
	
	private String secretYn; //비밀글 여부
	
	private LocalDateTime insertTime; //등록일
	
	private LocalDateTime updateTime; //수정일
	
	private LocalDateTime deleteTime; //삭제일
	
}
