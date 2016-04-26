package com.c3.base.log.component;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.c3.base.log.entity.Log;
import com.c3.base.log.repository.LogRepository;

@Component
public class LogDbHelper {
   private static LogRepository logRepository;

   public static void log(HttpServletRequest request, String userName, String content) {
      Log log = new Log();
      log.setContent(content);
      log.setUserName(userName);
      String ip = request.getHeader("x-forwarded-for");
      if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
         ip = request.getHeader("Proxy-Client-IP");
      }
      if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
         ip = request.getHeader("WL-Proxy-Client-IP");
      }
      if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
         ip = request.getRemoteAddr();
      }
      log.setIp(ip.equals("0:0:0:0:0:0:0:1") ? "127.0.0.1" : ip);
      logRepository.save(log);
   }

   @Autowired
   protected void setLogRepository(LogRepository logRepository) {
      LogDbHelper.logRepository = logRepository;
   }
}
