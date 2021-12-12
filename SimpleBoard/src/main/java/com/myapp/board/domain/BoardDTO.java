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
	
	private int visit_cnt; //조회수 
	
	private String notice_yn; //공지여부 
	
	private String delete_yn; //삭제여부 
	
	private String secret_yn; //비밀글 여부
	
	private LocalDateTime insert_time; //등록일
	
	private LocalDateTime update_time; //수정일
	
	private LocalDateTime delete_time; //삭제일
	
}
