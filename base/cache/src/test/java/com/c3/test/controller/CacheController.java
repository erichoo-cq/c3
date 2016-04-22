package com.c3.test.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CacheController {

   @RequestMapping(value = "/cache/cache1")
   public String cache1() throws Exception {
      System.out.println("------cache1-----");
      return "SUCCESS";
   }

   @RequestMapping(value = "/cache/cache2")
   public String cache2() throws Exception {
      System.out.println("------cache2-----");
      return "SUCCESS";
   }

   @RequestMapping(value = "/cache/cache3")
   public String cache3() throws Exception {
      System.out.println("------cache3-----");
      return "SUCCESS";
   }

   @RequestMapping(value = "/nocache/cache1")
   public String nocache1() throws Exception {
      System.out.println("------nocache1-----");
      return "SUCCESS";
   }

   @RequestMapping(value = "/nocache/cache2")
   public String nocache2() throws Exception {
      System.out.println("------nocache2-----");
      return "SUCCESS";
   }

}
