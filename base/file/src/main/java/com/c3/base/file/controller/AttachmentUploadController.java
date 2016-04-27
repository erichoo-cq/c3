package com.c3.base.file.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.c3.base.file.domain.Attachment;



@Controller
public class AttachmentUploadController extends AbstractAttachmentUploadController {

	/**
	 * 上传后续处理
	 */
	public Map<String, Object> afterHandle(Attachment attachment, Map<String, String> filter, Model model)
			throws Exception {
		return null;
	}

}