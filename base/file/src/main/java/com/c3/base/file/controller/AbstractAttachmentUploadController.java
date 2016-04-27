package com.c3.base.file.controller;


import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import com.c3.base.file.domain.Attachment;
import com.c3.base.file.service.IAttachmentService;
import com.google.common.collect.Maps;

public abstract class AbstractAttachmentUploadController extends AbstractFileUploadController {
	
	@Autowired
	private IAttachmentService<Map<String, Object>> attachmentService;

	/**
	 * 上传检查（存储检查，允许上传的文件类型检查等）
	 */
	@Override
	public boolean preHandle(MultipartFile file, Map<String, String> filter, Model model) throws Exception {
		Assert.hasText(filter.get("doc_type"), "Params 'doc_type' required.");
		return true;
	}

	public Map<String, Object> afterHandle(Attachment attachment, Map<String, String> filter, Model model)
			throws Exception {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.crunii.uie.core.web.mvc.support.AbstractFileUploadController#
	 * handleFileUpload(com.crunii.uie.core.web.dto.FileUploadDto,
	 * com.crunii.uie.core.security.UserDetails, java.util.Map,
	 * org.springframework.ui.Model)
	 */
	@Override
	public Map<String, Object> handleFileUpload(Attachment attachment, Map<String, String> filter, Model model)
			throws Exception {
		Map<String, Object> params = Maps.newHashMap();
		Date now = new Date();
		params.put("FILE_EXT", attachment.getExtension());
		params.put("FILE_SIZE", attachment.getSize());
		params.put("FILE_HANDLE", attachment.getRelativePath());
		params.put("DOC_NAME", attachment.getName());
		params.put("DOC_TYPE", filter.get("doc_type"));
		params.put("DOC_STATE", "1");
		params.put("UPLOAD_DATE", now);
		Map<String, Object> result = Maps.newHashMap();

		Map<String, Object> created = attachmentService.create(params);
		if (created != null) {
			result.putAll(created);
		}

		Map<String, Object> proccessed = afterHandle(attachment, filter, model);
		if (proccessed != null) {
			result.putAll(proccessed);
		}
		return result;
	}

}
