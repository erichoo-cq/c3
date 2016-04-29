package com.c3.sample.web.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.c3.base.core.permission.shiro.UserDetails;
import com.c3.base.menu.auth.service.SmUserService;
import com.c3.base.menu.auth.user.User;


/**
 * description: 登录控制器
 *
 * @version 2016年4月28日 上午10:26:30
 * @see
 * modify content------------author------------date
 */
@Controller
public class LoginController {
	
	@Autowired
	private SmUserService smUserService;
	
	private static int count;
	
	@RequestMapping(value = "/login", method = {RequestMethod.GET, RequestMethod.POST})
	public String login(HttpServletRequest request) {
		System.out.println(count++);
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		if( StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
			return "login";
		}
		UsernamePasswordToken token  = new UsernamePasswordToken(username, password);
		Subject subject = SecurityUtils.getSubject();
		//token.clear();
		subject.login(token);
		if (subject.isAuthenticated()){
			return "redirect:/";
		}
		return "login";
	}
	
	@RequestMapping(value = "/logout", method = {RequestMethod.GET})
	public String logout( ) {
		return "login";
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String forwardIndex() {
		return "/main";
	}
	
	/**
	 * description: 跳转到修改密码的页面
	 * @return 返回页面路径
	 * @time: 2016年4月29日 上午11:01:19
	 */
	@RequestMapping(value = "/pwd", method = RequestMethod.GET)
	public String forwardPassword() {
		return "/sm/pwd/index";
	}
	
	/**
	 * description: 更新用户密码
	 * @param filter 前端请求参数map
	 * @return 操作是否成功
	 * @time: 2016年4月29日 上午10:55:10
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value="/pwd/updateUserInfo", method = RequestMethod.POST)
	public Object update(@RequestParam Map<String, String> filter) {
		UserDetails<Integer, User> user = (UserDetails<Integer, User>)SecurityUtils.getSubject().getPrincipal();
		Integer userId = user.getUser().getUserId();
		return smUserService.updateUserPassword(filter,userId);
	}
}
