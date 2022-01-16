package com.myapp.board.controller;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.myapp.board.constraint.Method;
import com.myapp.board.domain.BoardDTO;
import com.myapp.board.domain.FileDTO;
import com.myapp.board.paging.Criteria;
import com.myapp.board.service.BoardService;
import com.myapp.board.util.UiUtils;

@Controller
public class BoardController extends UiUtils {

	@Autowired
	private BoardService boardService;
	
	/*
	 * Spring 4.3 이상 기존 RequestMapping의 RequestMethod.GET, RequestMethod.POST를 GetMapping, PostMapping으로 나누어 사용 가능
	 */ 
	@GetMapping(value = "/board/list")
	public String list(@ModelAttribute("params") BoardDTO params, Model model) {
		
		model.addAttribute("boardlist", boardService.getBoardList(params));

		return "board/list";
	}
	
	@PostMapping(value = "/board/list")
	public String listPost(@ModelAttribute("params") BoardDTO params, Model model) {
		
		model.addAttribute("boardlist", boardService.getBoardList(params));

		return "board/list";
	}
	
	@GetMapping(value = "/board/write")
	public String write(@ModelAttribute("params") BoardDTO params, @RequestParam(value = "num", required=false) Long num, Model model) {
		
//		if(num == null) {
//		}
//		else {
//			BoardDTO board = boardService.getBoard(num);
//			if(ObjectUtils.isEmpty(board)) {
//				return "redirect:/board/list"; 
//			}
//			model.addAttribute("board", board);
//		}
		
		model.addAttribute("board", new BoardDTO());
		
		return "board/write";
	}
	
	@PostMapping(value = "/board/register")
	public String registerBoard(@ModelAttribute("params") BoardDTO params, final MultipartFile[] files, Model model) {
		Map<String, Object> pagingParams = getPagingParams(params);
		try {
			boolean result = boardService.setBoard(params, files);
			if(!result) {
				return showMessageWithRedirect("게시물 등록에 실패했습니다.", "/board/list", Method.GET, pagingParams, model);
			}
		}
		catch (DataAccessException e){
			return showMessageWithRedirect("데이터 처리 과정에 오류가 발생하였습니다.", "/board/list", Method.GET, pagingParams, model);
		}
		catch(Exception e) {
			return showMessageWithRedirect("시스템에 문제가 발생하였습니다.", "/board/list", Method.GET, pagingParams, model);
		}
		return showMessageWithRedirect("게시물 등록이 완료되었습니다.", "/board/list", Method.GET, pagingParams, model);
	}
	
	@GetMapping(value = "/board/modify")
	public String modify(@ModelAttribute("params") BoardDTO params, @RequestParam(value = "num", required=false) Long num, Model model) {
		Map<String, Object> pagingParams = getPagingParams(params);
		if(num == null) {
			model.addAttribute("board", new BoardDTO());
		}
		else {
			BoardDTO board = boardService.getBoard(num);
			if(ObjectUtils.isEmpty(board)) {
				return showMessageWithRedirect("게시물이 없습니다.", "/board/list", Method.GET, pagingParams, model);
			}
			model.addAttribute("board", board);
			model.addAttribute("fileList", boardService.getAttachFileList(num));
		}
		
		return "board/modify";
	}
	
	@PostMapping(value = "/board/modify")
	public String modifyBoard(@ModelAttribute("params") BoardDTO params, final MultipartFile[] files, Model model) {
		Map<String, Object> pagingParams = getPagingParams(params);
		try {
			int result = boardService.modifyBoard(params, files);
			if(result < 0) {
				return showMessageWithRedirect("수정 가능한 게시물이 없습니다.", "/board/list", Method.GET, pagingParams, model);
			}
			else if(result < 0) {
				return showMessageWithRedirect("게시물 수정에 실패했습니다.", "/board/list", Method.GET, pagingParams, model);
			}
		}
		catch (DataAccessException e){
			e.printStackTrace();
			return showMessageWithRedirect("데이터 처리 과정에 오류가 발생하였습니다.", "/board/list", Method.GET, pagingParams, model);
		}
		catch(Exception e) {
			e.printStackTrace();
			return showMessageWithRedirect("시스템에 문제가 발생하였습니다.", "/board/list", Method.GET, pagingParams, model);
		}
		return showMessageWithRedirect("게시물 수정이 완료되었습니다.", "/board/list", Method.GET, pagingParams, model);
	}
	
	
	@GetMapping(value = "/board/view")
	public String view(@ModelAttribute("params") BoardDTO params, @RequestParam(value = "num", required=false) Long num, Model model) {
		
		if(num == null) {
			return showMessageWithRedirect("옳바르지 않은 접근입니다.", "/board/list", Method.GET, null, model);
		}
		
		BoardDTO board = boardService.getBoard(num);
		if(ObjectUtils.isEmpty(board)) {
			return showMessageWithRedirect("조회 가능한 게시물이 없습니다.", "/board/list", Method.GET, null, model);
		}
		model.addAttribute("board", board);
		model.addAttribute("fileList", boardService.getAttachFileList(num));
		
		return "board/view";
	}
	
	@PostMapping(value = "/board/delete")
	public String deleteBoard(@ModelAttribute("params") BoardDTO params, @RequestParam(value = "num", required=false) Long num, Model model) {
		Map<String, Object> pagingParams = getPagingParams(params);
		try {
			if(params.getNum() == null) {
				return showMessageWithRedirect("옳바르지 않은 접근입니다.", "/board/list", Method.GET, pagingParams, model);
			}
			
			int result = boardService.removeBoard(num);
			if(result <= 0) {
				return showMessageWithRedirect("삭제할 게시물이 없습니다.", "/board/list", Method.GET, pagingParams, model);
			}
		}
		catch (DataAccessException e){
			return showMessageWithRedirect("데이터 처리 과정에 오류가 발생하였습니다.", "/board/list", Method.GET, pagingParams, model);
		}
		catch(Exception e) {
			return showMessageWithRedirect("시스템에 문제가 발생하였습니다.", "/board/list", Method.GET, pagingParams, model);
		}
		return showMessageWithRedirect("게시물이 삭제가 완료되었습니다.", "/board/list", Method.GET, pagingParams, model);
	}
	
	public Map<String, Object> getPagingParams(Criteria criteria) {

		Map<String, Object> params = new LinkedHashMap<>();
		params.put("currentPageNo", criteria.getCurrentPageNo());
		params.put("recordsPerPage", criteria.getRecordsPerPage());
		params.put("pageSize", criteria.getPageSize());
		params.put("searchType", criteria.getSearchType());
		params.put("searchKeyword", criteria.getSearchKeyword());

		return params;
	}
	
	@GetMapping("/board/download")
	public void downloadFile(@RequestParam(value = "fileNum", required = true) final Long fileNum, Model model, HttpServletResponse response) {
		if (fileNum == null) throw new RuntimeException("올바르지 않은 접근입니다.");
		
		FileDTO fileInfo = boardService.getAttachFileDetail(fileNum);
		if (fileInfo == null || "Y".equals(fileInfo.getDeleteYn())) {
			throw new RuntimeException("파일 정보를 찾을 수 없습니다.");
		}
		
		String uploadDate = fileInfo.getInsertTime().format(DateTimeFormatter.ofPattern("yyMMdd"));
		String uploadPath = Paths.get(System.getProperty("user.home"), "spring-files", "upload", "SimpleBoard", uploadDate).toString();
		
		String filename = fileInfo.getOriginalName();
		File file = new File(uploadPath, fileInfo.getSaveName());
		try {
			byte[] data = FileUtils.readFileToByteArray(file);
			response.setContentType("application/octet-stream");
			response.setContentLength(data.length);
			response.setHeader("Content-Transfer-Encoding", "binary");
			response.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(filename, "UTF-8") + "\";");

			response.getOutputStream().write(data);
			response.getOutputStream().flush();
			response.getOutputStream().close();

		} catch (IOException e) {
			throw new RuntimeException("파일 다운로드에 실패하였습니다.");

		} catch (Exception e) {
			throw new RuntimeException("시스템에 문제가 발생하였습니다.");
		}
	}
}
