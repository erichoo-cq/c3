package com.c3.base.log.config;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

/**
 * 
 * description: 日志国际化文件扫描加载
 * 
 * @author: heshan
 * @version 2016年4月22日 下午4:42:29
 * @see modify content------------author------------date
 */
@Configuration
public class LogMessageConfig {

   @Value("${logging.message_dir:config/log_message}")
   private String messageDir;

   @Bean(name = "logMessageSource")
   public ReloadableResourceBundleMessageSource messageSource() {
      ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
      messageSource.setUseCodeAsDefaultMessage(false);
      messageSource.setDefaultEncoding("UTF-8");
      messageSource.setCacheSeconds(60);
      List<String> baseNames = new ArrayList<String>();
      try {
         Enumeration<URL> urlsEnum = Thread.currentThread().getContextClassLoader().getResources(messageDir);
         while (urlsEnum.hasMoreElements()) {
            URL url = (URL) urlsEnum.nextElement();
            File dir = new File(url.toURI());
            for (File f : dir.listFiles()) {
               String fileName = f.getName();
               fileName = fileName.substring(0, fileName.lastIndexOf("."));
               baseNames.add("classpath:" + messageDir + fileName);
            }
         }
      } catch (IOException | URISyntaxException e) {
         e.printStackTrace();
      }
      messageSource.setBasenames(baseNames.toArray(new String[] {}));
      return messageSource;
   }
}