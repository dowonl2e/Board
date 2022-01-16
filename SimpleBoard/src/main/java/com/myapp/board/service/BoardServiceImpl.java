package com.myapp.board.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import com.myapp.board.domain.BoardDTO;
import com.myapp.board.domain.FileDTO;
import com.myapp.board.mapper.BoardMapper;
import com.myapp.board.mapper.FileMapper;
import com.myapp.board.paging.PaginationInfo;
import com.myapp.board.util.FileUtils;

@Service
public class BoardServiceImpl implements BoardService {

	@Autowired
	private BoardMapper boardMapper;
	
	@Autowired
	private FileMapper fileMapper;
	
	@Autowired
	private FileUtils fileUtils;
	
	@Override
	public Integer getBoardListCount(BoardDTO params) {
		return boardMapper.selectBoardListCount(params);
	}
	
	@Override
	public List<BoardDTO> getBoardList(BoardDTO params) {
		List<BoardDTO> boardList = Collections.emptyList();
		
		int boardTotalCount = boardMapper.selectBoardListCount(params);
		
		/**
		 * 1. BoardDTO의 Criteria에 페이지에 대한 기본 데이터 저장
		 * 2. PaginationInfo에서 기본셋에서 페이징에 필요한 값 재정의
		 * 3. 재정의 된 PaginationInfo의 객체를 다시 BoardDTO 객체에 추가
		 */
		PaginationInfo paginationInfo = new PaginationInfo(params);
		paginationInfo.setTotalRecordCount(boardTotalCount);
		params.setPaginationInfo(paginationInfo);
		
		if(boardTotalCount > 0) {
			boardList = boardMapper.selectBoardList(params);
		}
		
		return boardList;
	}

	@Override
	public boolean setBoard(BoardDTO to) {
		
		/**
		 * 트랜잭션 테스트용 코드
		 */ 
		
		/*
		boolean result = false;
		
		if(to.getNum() == null) {
			result = (boardMapper.insertBoard(to) < 1 ? false : true);
		}
		
		BoardDTO dto = null;
		System.out.println(dto.getTitle());
		
		return result;
		*/
		
		return (boardMapper.insertBoard(to) < 1 ? false : true);
	}

	@Override
	public boolean setBoard(BoardDTO to, MultipartFile[] files) {
		int result = 1;
		
		if(setBoard(to) == false) {
			return false;
		}
		
		List<FileDTO> fileList = fileUtils.uploadFiles(files, to.getNum());
		if(CollectionUtils.isEmpty(fileList) == false) {
			result = fileMapper.insertFile(fileList);
			if(result < 1) {
				result = 0;
			}
		}
		
		return (result > 0);
	}
	
	@Override
	public BoardDTO getBoard(Long num) {
		return boardMapper.selectBoard(num);
	}
	
	@Override
	public int modifyBoard(BoardDTO params) {
		int result = 0;
		
		BoardDTO vo = boardMapper.selectBoard(params.getNum());
		if(ObjectUtils.isEmpty(vo)) {
			result = -1;
		}
		else {
			result = boardMapper.updateBoard(params);
		}
		return result;
	}
	
	@Override
	public int modifyBoard(BoardDTO params, MultipartFile[] files) {
		int result = modifyBoard(params);
		
		if(result > 0) {
			// 파일이 추가, 삭제, 변경된 경우
			if ("Y".equals(params.getChangeYn())) {
				fileMapper.deleteFile(params.getNum());

				// fileIdxs에 포함된 idx를 가지는 파일의 삭제여부를 'N'으로 업데이트
				if (CollectionUtils.isEmpty(params.getFileNums()) == false) {
					fileMapper.undeleteFile(params.getFileNums());
				}
				
				List<FileDTO> fileList = fileUtils.uploadFiles(files, params.getNum());
				if(CollectionUtils.isEmpty(fileList) == false) {
					result = fileMapper.insertFile(fileList);
					if(result < 1) {
						result = 0;
					}
				}
			}
		}
		return result;
	}
	
	@Override
	public int removeBoard(Long num) {
//		int result = 0;
//		
//		BoardDTO vo = boardMapper.selectBoard(to.getNum());
//		if(ObjectUtils.isEmpty(vo)) {
//			result = -1;
//		}
//		else {
//			result = boardMapper.deleteBoard(to);
//		}
		
		return boardMapper.deleteBoard(num);
	}

	@Override
	public List<FileDTO> getAttachFileList(Long boardNum) {
		int fileTotalCount = fileMapper.selectFileTotalCount(boardNum);
		if (fileTotalCount < 1) {
			return Collections.emptyList();
		}
		return fileMapper.selectFileList(boardNum);
	}

	@Override
	public FileDTO getAttachFileDetail(Long fileNum) {
		return fileMapper.selectFileDetail(fileNum);
	}
	
}
