package com.c3.sample.web.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String forwardLogin() {
		return "/login";
	}
	
	@RequestMapping(value = "/login", method = {RequestMethod.GET, RequestMethod.POST})
	public String login() {
		Subject subject = SecurityUtils.getSubject();
		if (subject.isAuthenticated()){
			return "redirect:/";
		}
		return "login";
	}
	
	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public String forwardIndex() {
		return "/main";
	}
}
