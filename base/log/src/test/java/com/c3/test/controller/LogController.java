package com.c3.test.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.c3.base.log.util.LogUtil;

@RestController
public class LogController {

   @RequestMapping(value = "/log")
   public String log() throws Exception {
      LogUtil.logError("log.success", null);
      return "SUCCESS";
   }
}