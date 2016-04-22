$(function(){
	var $fileTable = $('#fileTable');
	
	//初始化
	$fileTable.load(CTX + "/file/fileList");
	
	//查询
	$("#ESSFile_search").on('click',function(){
		var doc_name = $("#search_file_name").val();
		var state = $("#file_state").val();
		var postData = {};
		postData.doc_name = doc_name;
		postData.state = state;
		$fileTable.load(CTX + "/file/fileList",postData,function(){});
	});
	
	//paginate
	$("#fileTable").on('click', '#ESSFileList_paginate li a', function(e){
		var $this = $(this),
			$href = $this.attr('href'),
			page = $this.data('page');
		var doc_name = $("#search_file_name").val();
		var state = $("#file_state").val();
		var postData = {};
		postData.doc_name = doc_name;
		postData.state = state;
		$fileTable.load(CTX + "/file/fileList?page=" + page, postData, function(){});
		return false;
	});
	
	//列表按钮绑定事件
	$fileTable.on('click', 'a.toggleState', function(){
		//取a标签下面的data数据
		var fileId = $(this).data().fileId;
		var a = confirm("确定删除？");
		if(a){
			$.post(CTX + "/file/changeFileState",{"fileId":fileId},function(data){
				if(data == 1){
					 $.notify({
				          "type": 'success',
				          "placement": 'top',
				          "title": "系统消息",
				          "text": "修改成功" ,
				          "delay": 1000
				       });
					//刷新页面
					 $fileTable.load(CTX + "/file/fileList");
				}else{
					$.notify({
				          "type": 'success',
				          "placement": 'top',
				          "title": "系统消息",
				          "text": "修改失败" ,
				          "delay": 1000
				       });
				}
			});
		}
	});
	
	$(document).on('click','#ESSFileSubmit',function(){
		var ESSFileUpDis = $("#ESSFileUpDis").text();
		var ESSFileIds = "";
		$("input[name^=attachment]").each(function(){
			ESSFileIds = ESSFileIds + $(this).val() + ",";
		});
		if(ESSFileIds != null || ESSFileIds != ""){
			if(ESSFileIds == null || ESSFileIds == ""){
				 $.notify({
			          "type": 'warning',
			          "placement": 'top',
			          "title": "系统消息",
			          "text": "请上传文件" ,
			          "delay": 1000
			       });
				 return false;
			}
		}
		var postData = {};
		postData.ESSFileUpDis = ESSFileUpDis;
		postData.ESSFileIds = ESSFileIds;
		postData.docType = 2;
		$.post(CTX + "/file/saveFileInfo",postData,function(data){
			if(data != 0){
				 $.notify({
			          "type": 'success',
			          "placement": 'top',
			          "title": "系统消息",
			          "text": "提交成功" ,
			          "delay": 1000
			       });
				 $("#dialog-lg").modal("hide");
				 $fileTable.load(CTX + "/file/fileList");
			}else{
				$.notify({
			          "type": 'warning',
			          "placement": 'top',
			          "title": "系统消息",
			          "text": "提交失败" ,
			          "delay": 1000
			    });
			}
		});
		
	});
	
	$(document).on('click','#closeUpload',function(){
		$fileTable.load(CTX + "/file/fileList");
	});
});

