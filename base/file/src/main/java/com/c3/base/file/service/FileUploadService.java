package com.c3.base.file.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.c3.base.core.repository.jdbc.BaseJdbcTemplate;



@Service
public class FileUploadService {
	
	@Autowired
	private BaseJdbcTemplate jdbc;
	
	public List<Map<String,Object>> queryFileList(Map<String,String> filter){
		
		String doc_name = filter.get("doc_name");
		
		//有效文档
//		List<Map<String,Object>> list = new ArrayList<>();
		String sql = " select * "
				+ " from c3_fm_document_content  "
				+ " where doc_state = '1' ";
		if(StringUtils.isNotBlank(doc_name)){
			sql = sql + " and doc_name like '%"+doc_name+"%'";
		}
		
		sql = sql + " order by file_id ";
		return jdbc.queryForList(sql);
//		list = jdbc.queryForList(sql);
		
//		for(int i=0;i<list.size();i++){
//			String doc_state = "0";
//			String doc_state_name = "";
//			if(list.get(i).get("doc_state")!=null){
//				doc_state = list.get(i).get("doc_state").toString();
//			}
//			if(doc_state.equals("0")){
//				doc_state_name = "无效";
//			}else{
//				doc_state_name = "有效";
//			}
//			list.get(i).put("doc_state_name", doc_state_name);
//		}
//		return list;
	}
	
	public int saveFileInfo(Map<String,Object> param){
		int i = 0;
		String fileDis = "";
		if(param.get("ESSFileUpDis")!=null){
			fileDis = param.get("ESSFileUpDis").toString();
		}
		String ESSFileIds = "";
		if(param.get("ESSFileIds")!=null){
			ESSFileIds= param.get("ESSFileIds").toString();
		}
		String FileIds[] = ESSFileIds.split(",");
		String docType = "1";
		docType = param.get("docType").toString();
		
		String sqlUpdateFile = " update c3_fm_document_content set doc_description = ?,doc_type = ? where file_id = ? ";
		for(int k=0;k<FileIds.length;k++){
			if(StringUtils.isNotBlank(FileIds[k])){
				int fileId = 0;
				fileId = Integer.parseInt(FileIds[k]);
				i = jdbc.update(sqlUpdateFile,fileDis,docType,fileId);
			}
		}
		
		
		return i;
	}
	
	public int updateFileState(String fileId) {
		int fileId2 = Integer.parseInt(fileId);
		String sql = " select * from c3_fm_document_content where file_id = ? ";
		List<Map<String,Object>> list = jdbc.queryForList(sql, fileId2);
		int state = -1;
		if(list.size()!=0){
			if(list.get(0).get("doc_state")!=null){
				state = Integer.parseInt(list.get(0).get("doc_state").toString());
			}else{
				state = 0;
			}
		}
		String sqlUpdate = "update c3_fm_document_content set doc_state = ? where file_id = ? ";
//		int goalState = 0;
//		if(state == 0){
//			goalState = 1;
//		}else{
//			goalState = 0;
//		}
		state = (state ==  0) ? 1 : 0;
		return jdbc.update(sqlUpdate, state, fileId2);
	}
}
