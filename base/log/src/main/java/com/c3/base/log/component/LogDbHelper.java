package com.c3.base.log.component;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.c3.base.log.entity.Log;
import com.c3.base.log.repository.LogRepository;

@Component
public class LogDbHelper {
   private static LogRepository logRepository;

   protected static void log(HttpServletRequest request, String content) {
      Log log = new Log();
      log.setContent(content);
      Subject currentUser = SecurityUtils.getSubject();// 获取当前用户
      log.setUserName(currentUser.getPrincipal().toString());
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
