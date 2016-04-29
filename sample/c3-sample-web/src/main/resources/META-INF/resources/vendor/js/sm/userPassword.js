$(function(){
	//修改密码
	$(document).on('click','#passwordSubmit',function(){
		var old_pwd = $("#old_pwd").val();
		var new_pwd = $("#new_pwd").val();
		var con_pwd = $("#con_pwd").val();
		var pwdTest = /^[a-zA-Z0-9]{6,16}$/;
		var isPwdValue = pwdTest.test(new_pwd);
		if(old_pwd == null || old_pwd == ""){
			$.notify({
		          "type": 'warning',
		          "placement": 'top',
		          "title": "系统消息",
		          "text": "请输入原密码" ,
		          "delay": 1000
		       });
			return false;
		}
		if(new_pwd == null || new_pwd == ""){
			$.notify({
		          "type": 'warning',
		          "placement": 'top',
		          "title": "系统消息",
		          "text": "请输入新密码" ,
		          "delay": 1000
		       });
			return false;
		}
		
		if(con_pwd == null || con_pwd == ""){
			$.notify({
		          "type": 'warning',
		          "placement": 'top',
		          "title": "系统消息",
		          "text": "请输入确认密码" ,
		          "delay": 1000
		       });
			return false;
		}
		
		if(new_pwd != con_pwd){
			$.notify({
		          "type": 'warning',
		          "placement": 'top',
		          "title": "系统消息",
		          "text": "确认密码与新密码不相同" ,
		          "delay": 1000
		       });
			return false;
		}
		if(isPwdValue != 1){
			$.notify({
		          "type": 'warning',
		          "placement": 'top',
		          "title": "系统消息",
		          "text": "密码无效,长度为6-16,数字与字母的任意组合" ,
		          "delay": 1000
		       });
			return false;
		}
		
		var postData = {};
		postData.oldPwd = old_pwd;
		postData.newPwd = new_pwd;
		$.post(CTX + "/pwd/updateUserInfo", postData, function(data){
			if(data == -1){
				$.notify({
			          "type": 'danger',
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
				
				   setTimeout(function (){
						window.location.reload();
					   }, 1800); 
				   
			}
		});
	});
	
});