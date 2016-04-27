package com.c3.base.file.service;

import java.util.Map;

import com.c3.base.file.domain.DownloadDocument;


public interface IAttachmentService<T> {
	
	public T create(Map<String, Object> params);
	
	public DownloadDocument getDocument(Integer fileId, Map<String, String> params);

}