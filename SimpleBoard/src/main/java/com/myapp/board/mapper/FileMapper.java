package com.myapp.board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.myapp.board.domain.FileDTO;

@Mapper
public interface FileMapper {
	
	public int insertFile(List<FileDTO> attachList);

	public FileDTO selectFileDetail(Long fileNum);

	public int deleteFile(Long boardNum);

	public int selectFileTotalCount(Long boardNum);

	public List<FileDTO> selectFileList(Long boardNum);
	
	public int undeleteFile(List<Long> nums);

}
