package com.c3.base.file.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.c3.base.core.util.SqlUtil;
import com.c3.base.file.service.FileUploadService;


@Controller
@RequestMapping("/file")
public class C3FileController {
	
	@Autowired
	private FileUploadService fileUploadService;
	
	@RequestMapping(value="uploadMain" )
	public String fileUpload( ) {
		return "file/fileUploadMain";
	}
	
	@RequestMapping(value = "fileUpload")
	public String energyAuditUpload(Model model) {
		return "file/fileUpload";
	}
	
	@RequestMapping(value = "fileList")
	public String queryFileList(@PageableDefault (size=8)Pageable pageable, 
			@RequestParam Map<String,String> filter,Model model) {
		
		filter.put("doc_type", "2");
		List<Map<String,Object>> list = fileUploadService.queryFileList(filter);
		Page<Map<String, Object>> pageResult = SqlUtil.<Map<String, Object>>build(list, pageable);
		model.addAttribute("fileList", pageResult);
		return "file/fileList";
	}
	
	/**
	 * 保存上传文件信息
	 * @param parms
	 * @return
	 */
	@RequestMapping(value="saveFileInfo")
	@ResponseBody
	public Object saveFileInfo(@RequestParam Map<String,Object> parms){
		
		return fileUploadService.saveFileInfo(parms);
	}
	
	@RequestMapping(value="changeFileState")
	@ResponseBody
	public Object changeFileState(@RequestParam(name="fileId") String id){
		
//		String fileId = parms.get("fileId");
		return fileUploadService.updateFileState(id);
	}
}
