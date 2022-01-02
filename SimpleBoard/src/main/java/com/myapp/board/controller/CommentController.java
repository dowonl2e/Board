package com.myapp.board.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.myapp.board.adapter.GsonLocalDateTimeAdapter;
import com.myapp.board.domain.CommentDTO;
import com.myapp.board.service.CommentService;

@RestController
public class CommentController {

	@Autowired
	private CommentService commentService;
	
	@GetMapping(value = "/comments/{boardNum}")
	public JsonObject getCommentList(@PathVariable("boardNum") Long boardNum, @ModelAttribute("params") CommentDTO params) {
		JsonObject jsonObject = new JsonObject();
		
		List<CommentDTO> commentList = commentService.getCommentList(params);
		
		if(CollectionUtils.isEmpty(commentList) == false) {
			Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new GsonLocalDateTimeAdapter()).create();
			JsonArray jsonArray = gson.toJsonTree(commentList).getAsJsonArray();
			jsonObject.add("commentList", jsonArray);
		}
		
		
		return jsonObject;
	}
	
	/**
	 * @param params
	 * @return
	 * 
	 * Post 매핑 방식
	 * 1. 클라이언트는 작성한 내용 및 데이터를 JSON 문자열로 전송한다.
	 * 2. 서버(컨트롤러)는 JSON 문자열을 파라미터로 받는다.
	 * 3. @RequestBody은 전달받은 JSON 문자열을 객체로 변환한다.
	 * 4. 객체로 변환된 JSON은 CommentDTO 클래스 객체인 params에 매핑(바인딩)된다. 
	 * 
	 */
	@PostMapping(value = "/comments")
	public JsonObject registerComment(@RequestBody final CommentDTO params) {
		JsonObject jsonObject = new JsonObject();
		
		try {
			boolean result = commentService.registerComment(params);
			jsonObject.addProperty("result", result);
		}
		catch (DataAccessException e) {
			jsonObject.addProperty("message", "데이터베이스 처리 과정에 문제가 발생하였습니다.");
		}
		catch (Exception e) {
			jsonObject.addProperty("message", "시스템에 문제가 발생하였습니다.");
		}
		
		return jsonObject;
	}
	
	@PatchMapping(value = "/comments/{commentNum}")
	public JsonObject modifyComment(@PathVariable(value = "commentNum", required=true) Long commentNum, @RequestBody final CommentDTO params) {
		JsonObject jsonObject = new JsonObject();
		
		try {
			
			if(commentNum != null) {
				params.setCommentNum(commentNum);
			}
			
			int result = commentService.modifyComment(params);
			if(result < 0) {
				jsonObject.addProperty("message", "수정가능한 댓글이 없습니다.");
			}
			else if( result == 0) {
				jsonObject.addProperty("message", "댓글 수정에 실패했습니다.");
			}
			else {
				jsonObject.addProperty("result", result);
			}
		}
		catch (DataAccessException e) {
			jsonObject.addProperty("message", "데이터베이스 처리 과정에 문제가 발생하였습니다.");
		}
		catch (Exception e) {
			jsonObject.addProperty("message", "시스템에 문제가 발생하였습니다.");
		}
		
		return jsonObject;
	}
}
