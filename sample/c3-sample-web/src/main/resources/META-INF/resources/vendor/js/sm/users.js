$(function(){
	//查询条件
	var $userPage = $("#userPage");
	$userPage.load(CTX + "/sm/users/list",{});
	
	//查询
	$("#searchBtn").click(function(){
		var $btn = $(this).button('loading')
		var params = $("#userForm").serializeArray();
		$userPage.load(CTX + "/sm/users/list", params,function(){
			$btn.button('reset');
		});
	});
	
	//paginate
	$userPage.on('click', '#user-paginate li a', function(e){
		var params = $("#userForm").serializeArray();
		var $this = $(this),
			$href = $this.attr('href'),
			page = $this.data('page');
		$userPage.load(CTX + "/sm/users/list?page=" + page,params);
		return false;
	});
	
	//用户操作
	$userPage.on('click', 'td #userOperation', function(e){
		var id = $(this).data('userid');
		var state = $(this).attr('data-state');
		if(state == 0){
			$(this).find('i').removeClass().addClass('fa fa-close text-danger');
			$(this).attr('data-state',1);
			var s = $(this).parent().parent().find('#userState span');
			s.removeClass().addClass('label label-success');
			s.html('可用');
		} else {
			$(this).find('i').removeClass().addClass('fa fa-check');
			$(this).attr('data-state',0);
			var s = $(this).parent().parent().find('#userState span');
			s.removeClass().addClass('label label-default');
			s.html('停用');
		}
		//var $btn = $(this).button('loading')
		$.post(CTX + '/sm/users/updateState', {'id':id,'state':Math.abs(state-1)}, function(data){
			//$btn.button('reset');
			$.notify({
		          "type": 'success',
		          "placement": 'bottom',
		          "title": "提示消息",
		          "text": "用户状态修改成功。",
		          "delay": '3000'
		        });
			$userPage.load(CTX + "/sm/users/list",{});
		});
	});
	
	//重置用户密码
	$userPage.on('click','td #resetPwd',function(e){
		var $btn = $(this).button('loading')
		var id = $(this).data('userid');
		$.post(CTX + '/sm/users/resetPwd', {'id':id}, function(data){
			$btn.button('reset');
			$.notify({
		          "type": 'success',
		          "placement": 'bottom',
		          "title": "提示消息",
		          "text": "用户密码重置成功。",
		          "delay": '3000'
		        });
		})
	});
	
	//新增
	$(document).on('click','#addSubmit',function(){
		var email = $("#add_email").val();
		var user_name = $("#add_user_name").val();
		var full_name = $("#add_full_name").val();
		var dept_id = $("#add_dept_id").val();
		var first_pwd = $("#add_first_pwd").val();
		var confrim_pwd = $("#add_confrim_pwd").val();
		if(email == null || email == ""){
			$.notify({
		          "type": 'warning',
		          "placement": 'top',
		          "title": "系统消息",
		          "text": "请输入登录账号" ,
		          "delay": 1000
		       });
			return false;
		}
		if(user_name == null || user_name == ""){
			$.notify({
		          "type": 'warning',
		          "placement": 'top',
		          "title": "系统消息",
		          "text": "请输入账号名称" ,
		          "delay": 1000
		       });
			return false;
		}
		if(full_name == null || full_name == ""){
			$.notify({
		          "type": 'warning',
		          "placement": 'top',
		          "title": "系统消息",
		          "text": "请输入用户名称" ,
		          "delay": 1000
		       });
			return false;
		}
		if(dept_id == null || dept_id == ""){
			$.notify({
		          "type": 'warning',
		          "placement": 'top',
		          "title": "系统消息",
		          "text": "请选择所属部门" ,
		          "delay": 1000
		       });
			return false;
		}
		if(first_pwd == null || first_pwd == ""){
			$.notify({
		          "type": 'warning',
		          "placement": 'top',
		          "title": "系统消息",
		          "text": "请输入密码" ,
		          "delay": 1000
		       });
			return false;
		}
		if(first_pwd != confrim_pwd){
			$.notify({
		          "type": 'warning',
		          "placement": 'top',
		          "title": "系统消息",
		          "text": "重复密码与初始密码不相同" ,
		          "delay": 1000
		       });
			return false;
		}
		
		var postData = {};
		postData.email = email;
		postData.user_name = user_name;
		postData.full_name = full_name;
		postData.dept_id = dept_id;
		postData.pwd = first_pwd;
		$.post(CTX + "/sm/users/addNew",postData,function(data){
			if(data != -1){
				$.notify({
			          "type": 'success',
			          "placement": 'top',
			          "title": "系统消息",
			          "text": "提交成功" ,
			          "delay": 1000
			       });
				 $("#sideslip-sm").modal("hide");
				 $userPage.load(CTX + "/sm/users/list",{});
			}else{
				$.notify({
			          "type": 'warning',
			          "placement": 'top',
			          "title": "系统消息",
			          "text": "登录账号重复,请重新输入" ,
			          "delay": 1000
			       });
			}
		});
	});
	
	
	
	//修改密码
	$(document).on('click','#editSubmit',function(){
		var user_id = $("#edit_user_id").val();
		var edit_old_pwd = $("#edit_old_pwd").val();
		var edit_new_pwd = $("#edit_new_pwd").val();
		var edit_con_pwd = $("#edit_con_pwd").val();
		if(edit_old_pwd == null || edit_old_pwd == ""){
			$.notify({
		          "type": 'warning',
		          "placement": 'top',
		          "title": "系统消息",
		          "text": "请输入原密码" ,
		          "delay": 1000
		       });
			return false;
		}
		if(edit_new_pwd == null || edit_new_pwd == ""){
			$.notify({
		          "type": 'warning',
		          "placement": 'top',
		          "title": "系统消息",
		          "text": "请输入新密码" ,
		          "delay": 1000
		       });
			return false;
		}
		if(edit_new_pwd != edit_con_pwd){
			$.notify({
		          "type": 'warning',
		          "placement": 'top',
		          "title": "系统消息",
		          "text": "确认密码与新密码不相同" ,
		          "delay": 1000
		       });
			return false;
		}
		
		var postData = {};
		postData.user_id = user_id;
		postData.oldPwd = edit_old_pwd;
		postData.newPwd = edit_new_pwd;
		$.post(CTX + "/sm/users/updateInfo",postData,function(data){
			if(data == -1){
				$.notify({
			          "type": 'success',
			          "placement": 'top',
			          "title": "系统消息",
			          "text": "原密码错误" ,
			          "delay": 1000
			       });
			}else{
				$.notify({
			          "type": 'success',
			          "placement": 'top',
			          "title": "系统消息",
			          "text": "提交成功" ,
			          "delay": 1000
			       });
				 $("#sideslip-sm").modal("hide");
				 $userPage.load(CTX + "/sm/users/list",{});
			}
		});
	});
	
	//修改基本信息
	$(document).on('click','#editPersonSubmit',function(){
		var user_id = $("#edit_user_id").val();
		var full_name = $("#full_name").val();
		var phone = $("#phone").val();
		var role_id = $("#role_select").val();
		var postData = {};
		postData.user_id = user_id;
		postData.full_name = full_name;
		postData.phone = phone;
		postData.roleId = role_id;
		if(role_id == null || role_id == ""){
			$.notify({
		          "type": 'warning',
		          "placement": 'top',
		          "title": "系统消息",
		          "text": "请选择一个角色" ,
		          "delay": 1000
		       });
			return false;
		}
		$.post(CTX + "/sm/users/updatePersonInfo",postData,function(data){
			if(data == -1){
				$.notify({
			          "type": 'warning',
			          "placement": 'top',
			          "title": "系统消息",
			          "text": "更新失败" ,
			          "delay": 1000
			       });
			}else{
				$.notify({
			          "type": 'success',
			          "placement": 'top',
			          "title": "系统消息",
			          "text": "提交成功" ,
			          "delay": 1000
			       });
				 $("#sideslip-sm").modal("hide");
				 $userPage.load(CTX + "/sm/users/list",{});
			}
		});
	});
	
	
});