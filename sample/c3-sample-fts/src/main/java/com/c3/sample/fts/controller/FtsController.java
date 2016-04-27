package com.c3.sample.fts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.c3.sample.fts.entity.Fts;
import com.c3.sample.fts.service.FtsService;

@RestController
public class FtsController {
   
   @Autowired
   private FtsService ftsService;
   
   
   @RequestMapping(value = "/fts")
   public String fts() throws Exception {
      
      Fts fts = new Fts();
      fts.setContent("你好，重庆市通信建设有限公司。");
      fts.setUserName("何山");
      ftsService.add(fts);
      
      return "SUCCESS";
   }
   
}