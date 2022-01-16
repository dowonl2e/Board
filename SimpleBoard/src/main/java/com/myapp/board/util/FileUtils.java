package com.myapp.board.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.myapp.board.domain.FileDTO;
import com.myapp.board.exception.AttachFileException;

@Component
public class FileUtils {
	
	/** 오늘날짜 */ 
	private final String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyMMdd"));
	
	/** 
	 * 업로드 경로
	 * Paths.get() -> : OS에 해당하는 패턴으로 경로를 만든다. 
	 */
	private final String uploadPath = Paths.get(System.getProperty("user.home"), "spring-files", "upload", "SimpleBoard", today).toString();
	
	/**
	 * 서버에 생성할 파일명을 처리할 랜덤 문자열 반환
	 * @return 랜덤 문자열
	 */
	private final String getRandomString() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
	public List<FileDTO> uploadFiles(MultipartFile[] files, Long boardNum){
		

		
		List<FileDTO> fileList = new ArrayList<FileDTO>();
		
		//uploadPath에 해당하는 디렉토리가 존재하지 않으면, 부모 디렉토리를 포함한 모든 디렉토리 생성
		File dir = new File(uploadPath);
		if(dir.exists() == false) {
			dir.mkdirs();
		}
		System.out.println("업로드 경로 : " + uploadPath);
		for(MultipartFile file : files) {
			
			if(file.getSize() < 1) {
				continue;
			}
			
			try {
				/** 파일 확장자 */
				final String extension = FilenameUtils.getExtension(file.getOriginalFilename());
				
				/** 서버에 저장할 파일명(랜덤 문자열 + 확장자) */
				final String saveNames = getRandomString() + "." + extension;
				
				File target = new File(uploadPath, saveNames);
				file.transferTo(target);
				
				FileDTO fileto = new FileDTO();
				fileto.setBoardNum(boardNum);
				fileto.setOriginalName(file.getOriginalFilename());
				fileto.setSaveName(saveNames);
				fileto.setSize(file.getSize());
				
				fileList.add(fileto);
			}
			catch (IOException e) {
				throw new AttachFileException("[" + file.getOriginalFilename() + "] failed to save file...");
			}
			catch (Exception e) {
				throw new AttachFileException("[" + file.getOriginalFilename() + "] failed to save file...");
			}
		}
		
		return fileList;
	}
}
