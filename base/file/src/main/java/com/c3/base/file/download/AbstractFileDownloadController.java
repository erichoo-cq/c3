package com.c3.base.file.download;


import java.io.File;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.c3.base.file.config.FileUploadProperties;
import com.c3.base.file.domain.DownloadDocument;



public abstract class AbstractFileDownloadController  {
	@Autowired
	private FileUploadProperties fileUploadProperties;

	public boolean preHandle(Integer fileId, Map<String, String> filter, Model model) throws Exception {
		return true;
	}

	public void afterHandle(Integer fileId, Map<String, String> filter, Model model) throws Exception {

	}

	public abstract DownloadDocument getDocument(Integer fileId, Map<String, String> filter, Model model)
			throws Exception;

	@RequestMapping(value = "attachment/download", method = RequestMethod.GET)
	public ResponseEntity<byte[]> _download(@RequestParam("id") Integer fileId, Map<String, String> filter, Model model)
			throws Exception {
		Assert.isTrue(preHandle(fileId, filter, model), "Pre Valid Fail.");

		DownloadDocument docment = getDocument(fileId, filter, model);
		
		String fileName = new String(docment.getFileName().getBytes("gbk"), "iso-8859-1");

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(docment.getMediaType());
		headers.setContentDispositionFormData(docment.getContentDisposition(), fileName);
		File file = new File(fileUploadProperties.getRootPath(), docment.getRelativePath());
		Assert.isTrue(file.exists(), "File not exists.");
		afterHandle(fileId, filter, model);
		return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
	}

}
