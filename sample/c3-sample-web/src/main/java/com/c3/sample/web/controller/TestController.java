package com.c3.sample.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
	
	@RequestMapping(method = RequestMethod.GET, value="test")
	public String index() {
		return "hello";
	}
}
