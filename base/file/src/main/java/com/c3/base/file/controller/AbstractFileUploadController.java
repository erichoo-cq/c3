package com.c3.base.file.controller;


import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.c3.base.core.util.CommUtils;
import com.c3.base.core.util.StringUtils;
import com.c3.base.file.config.FileUploadProperties;
import com.c3.base.file.domain.Attachment;
import com.google.common.collect.Maps;



public abstract class AbstractFileUploadController {

	@Autowired
	private FileUploadProperties fileUploadProperties;

	/**
	 * 上传前置处理
	 * 
	 * @param file
	 * @param user
	 * @param filter
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public boolean preHandle(MultipartFile file, Map<String, String> filter, Model model) throws Exception {
		return true;
	}

	/**
	 * 文件上传后续处理
	 * 
	 * @param file
	 * @param filter
	 * @param response
	 * @param request
	 * @param model
	 * @return
	 */
	public abstract Map<String, Object> handleFileUpload(Attachment attachment, Map<String, String> filter, Model model) throws Exception;

	@ResponseBody
	@RequestMapping(value = "attachment/upload", method = RequestMethod.POST)
	public final Map<String, Object> _fileupload(@RequestParam("file") MultipartFile file, @RequestParam Map<String, String> filter,
			Model model) throws Exception {
		Map<String, Object> result = Maps.newHashMap();
		result.put("code", "-1");
		result.put("desc", "上传失败");
		if (!this.preHandle(file, filter, model)) {
			return result;
		}

		if (!file.isEmpty()) {
			//FileItem fileItem = file.getFileItem();
			String originalFilename = file.getOriginalFilename();
			// 原始文件名
			String fileRealName = CommUtils.getFileRealName(originalFilename);
			// 文件扩展名
			String fileExt = CommUtils.getExtension(originalFilename);
			// 新文件名
			String fileName = CommUtils.genFileName();
			String rootPath = this.fileUploadProperties.getRootPath();
			String uploadDirectory = formatPath(this.fileUploadProperties.getRelativePath());

			File dir = new File(rootPath, uploadDirectory);
			if (!dir.isDirectory()) {
				dir.mkdirs();
			}

			String fileRelativePath = new File(uploadDirectory, CommUtils.genFileName(fileName, fileExt)).getPath();
			fileRelativePath = fileRelativePath.replaceAll("\\\\+", "/");// 把反斜杠\替换成正斜杠/

			File fileRealPath = new File(rootPath, fileRelativePath); // 创建文件
			try {
				file.transferTo(fileRealPath);
				Attachment attachment = new Attachment();
				attachment.setName(fileRealName);
				attachment.setFile(fileRealPath);
				attachment.setRelativePath(fileRelativePath);
				attachment.setExtension(fileExt);
				attachment.setContentType(file.getContentType());
				attachment.setSize(file.getSize());
				Map<String, Object> handleRs = handleFileUpload(attachment, filter, model);
				if (handleRs != null) {
					result.putAll(handleRs);
				}
				result.put("code", "0");
				result.put("desc", "上传成功");
				result.put("fileName", fileRealName);
				result.put("relativePath", fileRelativePath);
			} catch (Exception e) {
				result.put("code", "-1");
				result.put("desc", "文件上传失败。[" + e.getMessage() + ']');
			}
		}
		return result;
	}

	/**
	 * 格式化上传路径
	 * 
	 * @param path
	 * @return
	 */
	protected static String formatPath(String path) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String date = format.format(new Date());
		String[] dates = date.split("-");
		Map<String, String> params = Maps.newHashMap();
		params.put("yyyy", dates[0]);
		params.put("MM", dates[1]);
		params.put("dd", dates[2]);
		return StringUtils.replaceParams(path, params);
	}

}
