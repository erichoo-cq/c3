package com;

import junit.framework.TestCase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AppTest extends TestCase {
   
   public static void main(String[] args) {
      SpringApplication.run(AppTest.class, args);
   }
   
}