package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import junit.framework.TestCase;

@SpringBootApplication
public class AppTest extends TestCase {

   public static void main(String[] args) {
      SpringApplication.run(AppTest.class, args);
   }
}