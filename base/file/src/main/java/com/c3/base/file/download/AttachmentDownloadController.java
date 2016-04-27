package com.c3.base.file.download;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;


@Controller
public class AttachmentDownloadController extends AbstractAttachmentDownloadController {

	/**
	 * 下载后续处理
	 */
	public void afterHandle(Integer fileId, Map<String, String> filter, Model model) throws Exception {
		
	}
	
}