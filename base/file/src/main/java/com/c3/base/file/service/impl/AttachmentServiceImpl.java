package com.c3.base.file.service.impl;


import java.util.Collections;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.c3.base.file.domain.DownloadDocument;
import com.c3.base.file.service.IAttachmentService;
import com.c3.base.file.config.MimeTypeEnum;
import com.google.common.collect.Maps;

@Service
@Transactional
public class AttachmentServiceImpl implements IAttachmentService<Map<String, Object>> {
	//private static final Logger logger = LoggerFactory.getLogger(AttachmentServiceImpl.class);
	
	@Autowired
	private NamedParameterJdbcTemplate jdbc;
	
	private static final String SEQ_SQL = "SELECT nextval('c3_fm_document_content_file_id_seq') AS file_id";
	private static final String INSERT_SQL = "INSERT INTO c3_fm_document_content(FILE_ID, FILE_EXT, FILE_SIZE, FILE_HANDLE, DOC_NAME, DOC_TYPE, DOC_STATE, UPLOAD_DATE) VALUES(:FILE_ID, :FILE_EXT, :FILE_SIZE, :FILE_HANDLE, :DOC_NAME, :DOC_TYPE, :DOC_STATE, :UPLOAD_DATE)";
	private static final String SELECT_SQL = "SELECT * FROM c3_fm_document_content T1 WHERE T1.FILE_ID = :FILE_ID";
	private static final String INCREMENT_DOWNLOAD_TIMES_SQL = "UPDATE c3_fm_document_content SET DOWNLOAD_TIMES = DOWNLOAD_TIMES + 1 WHERE FILE_ID = ?";
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> create(Map<String, Object> params) {
		Long fileId = jdbc.queryForObject(SEQ_SQL, Collections.EMPTY_MAP, Long.class);
		params.put("FILE_ID", fileId);
		jdbc.update(INSERT_SQL, params);
		Map<String, Object> result = Maps.newHashMap();
		result.put("id", fileId);
		return result;
	}

	@Override
	public DownloadDocument getDocument(Integer fileId, Map<String, String> params) {
		Map<String, Object> filter = Maps.newHashMap();
		filter.put("FILE_ID", fileId);
		Map<String, Object> result = jdbc.queryForMap(SELECT_SQL, filter);
//		Assert.notNull(result.get("mime_type"), "file format not exsists.");
//		String mimeType = result.get("mime_type").toString();
		String mimeType = MimeTypeEnum.getTypeByExt(result.get("file_ext").toString());
		DownloadDocument document = new DownloadDocument();
		document.setExtension(result.get("file_ext").toString());
		document.setName(result.get("doc_name").toString());
		document.setRelativePath(result.get("file_handle").toString());
		document.setMediaType(mimeType);
		jdbc.getJdbcOperations().update(INCREMENT_DOWNLOAD_TIMES_SQL, fileId);
		return document;
	}

}
