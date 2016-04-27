package com.c3.base.file.download;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import com.c3.base.file.domain.DownloadDocument;
import com.c3.base.file.service.IAttachmentService;



public class AbstractAttachmentDownloadController extends AbstractFileDownloadController {
	
	@Autowired
	private IAttachmentService<Map<String, Object>> attachmentService;

	@Override
	public DownloadDocument getDocument(Integer fileId, Map<String, String> filter, Model model) throws Exception {
		return attachmentService.getDocument(fileId, filter);
	}

}
