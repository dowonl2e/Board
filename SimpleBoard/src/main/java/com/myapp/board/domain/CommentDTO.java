package com.myapp.board.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDTO extends CommonDTO {
	
	private Long commentNum;
	
	private Long boardNum;
	
	private String content;
	
	private String writer;
	
	
}
