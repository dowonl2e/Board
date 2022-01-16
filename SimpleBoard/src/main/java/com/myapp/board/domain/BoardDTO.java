package com.myapp.board.domain;


import java.util.List;

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
	
	private String changeYn; //파일 변경 여부
	
	private List<Long> fileNums; //파일 인덱스 리스트
}
