$(function(){
	var $rolePage = $('#rolePage');
	
	//查询
	$("#searchBtn").on('click', function(){
		var $btn = $(this).button('loading');
		var params = $("#roleForm").serializeArray();
		$rolePage.load(CTX + "/sm/role/list",params,function(){
			$btn.button('reset');
		})
		$('#menuList').load(CTX + "/sm/role/getMenu/0",function(){})
		
	});
	$("#searchBtn").click();
	
	//翻页
	$rolePage.on('click', '#role-paginate li a', function(e){
		var params = $("#roleForm").serializeArray();
		var $this = $(this),
			$href = $this.attr('href'),
			page = $this.data('page');
		$rolePage.load(CTX + "/sm/role/list?page=" + page,params);
		return false;
	});
	
	//选择角色加载资源列表
	$(document).on('click',"#roleTbody tr",function(){
		if($(this).hasClass('warning')){
			return false;
		}
		$(this).addClass('warning');
		$(this).siblings().removeClass('warning');
		$('#menuList').html('<p class="text-center m-t-sm"><i class="fa fa-spinner fa fa-spin fa fa-2x"></i></p>');
		$('#menuList').load(CTX + "/sm/role/getMenu/" +  $(this).data('role'),function(){})
	})
	
	//input 改变事件
	$(document).on('change',"#nestable input",function(){
		$(this).attr('change',1)
	})
	
	//提交关联
	$("#sm-role-menu").click(function(){
		var roleId = $("#selectRoleId").val();
		if(roleId == 0){
			$.notify({"type": 'danger',"placement": 'top-right',"title": "提示消息","text": "请选择角色。"});
			return false;
		}
		var addMenu = "",delMenu = "";
		$("#nestable input").each(function(){
			var $t = $(this);
			if($t.attr('change') == 1){
				if($t.is(':checked')){
					addMenu += $t.val() + "#"
				} else {
					delMenu += $t.val() + "#"
				}
				$t.attr('change','0');
			}
		})
		if(addMenu == '' && delMenu == ''){
			$.notify({"type": 'success',"placement": 'top-right',"title": "提示消息","text": "提交成功。"});
			return false;
		}
		//开始提交数据
		var $btn = $(this).button('loading');
		$.post(CTX + '/sm/role/saveResource', {"roleId":roleId,"addMenu":addMenu,"delMenu":delMenu}, function(o){
			$.notify({"type": 'success',"placement": 'top-right',"title": "提示消息","text": "提交成功。"});
			$btn.button('reset');
		})
	})
	
//	//加载表格
//	var loadPage = function(param){
//		
//		$.get(CTX + "/sm/role/list", param, function(data){
//			$rolePage.html(data);
//		});
//	};
//	
//	loadPage();
//	
//	//表单提交
//	$('#roleForm').on('submit', function(){
//		loadPage({roleName: $("#roleName").val()});
//		return false;
//	});
//	
//	//查询
//	$("#searchBtn").on('click', function(){
//		var $btn = $(this).button('loading');
//		var params = $("#searchForm").serializeArray();
//		$('#roleForm').submit();
//	});
	
	
	var $dialog = $('#dialog');
	//添加
	$('#sm-role-add').on('click', function(){
		var $this = $(this),
		    href = $this.attr('href');
		$.get(href, function(data){
			$dialog.find('.modal-content').html(data);
			$dialog.modal('show');
			$dialog.find('form').validate({
				submitHandler: function(form){
					var params = $(form).serializeArray();
					$.post(CTX + '/sm/role', params, function(){
						$dialog.modal('hide');
						$.notify({
		 		          "type": 'success',
		 		          "title": "提示消息",
		 		          "text": "角色信息保存成功。"
		 		        });
						$("#searchBtn").click();
					});
					return false;
				}
			});
		});
		return false;
	});
	
	//新增保存
	$(document).on('click', '#sm-role-new-submit', function(){
		$(this).closest('#dialog').find('form').submit();
	});
	
	//编辑
	$rolePage.on('click', 'a.edit', function(){
		var $this = $(this),
		    id = $this.data('id');
		$.get(CTX + '/sm/role/edit/' + id, function(data){
			$dialog.find('.modal-content').html(data);
			$dialog.modal('show');
			$dialog.find('form').validate({
				submitHandler: function(form){
					var params = $(form).serializeArray();
					$.post(CTX + '/sm/role/edit', params, function(){
						$dialog.modal('hide');
						$.notify({
		 		          "type": 'success',
		 		          "title": "提示消息",
		 		          "text": "角色信息保存成功。"
		 		        });
						$("#searchBtn").click();
					});
					return false;
				}
			});
		});
		return false;
	});
	
	//修改保存
	$(document).on('click', '#sm-role-edit-submit', function(){
		$(this).closest('#dialog').find('form').submit();
	});
	
	//关闭展开资源菜单列表
	$(document).on('click', 'button', function(){
		if($(this).attr('data-action') == 'expand'){
			$(this).parent().find('ol').show();
			$(this).attr('data-action','collapse')
		} else{
			$(this).parent().find('ol').hide();
			$(this).attr('data-action','expand')
		}
		
	});
	
	
//	//添加权限
//	$rolePage.on('click','#auth',function(e){
//		var postData1 = {};
//		var $this = $(this);
//		var $td = $this.closest('tr').find('td.editableTD');
//		var id = $td.find('div[name=editrole_id]').find('input[name=role_id]').val();;
//		postData1.id = id;
//		alert(id);
//		$rolePage.load(CTX + "/sm/role/authoritylist", postData1,function(){
//		});
//	});
//	
//	//查看权限
//	$rolePage.on('click','#lookauth',function(e){
//		var postData1 = {};
//		var $this = $(this);
//		var $td = $this.closest('tr').find('td.editableTD');
//		var id = $td.find('div[name=editrole_id]').find('input[name=role_id]').val();;
//		postData1.id = id;
//		$rolePage.load(CTX + "/sm/role/look", postData1,function(){
//		});
//		
//	});
	

});