package com.c3.base.log.component;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.c3.base.core.util.MessageUtil;

/**
 * 
 * description:日志工具类，最大限度解耦
 * 
 * @author: heshan
 * @version 2016年4月21日 上午10:45:25
 * @see modify content------------author------------date
 */
public class LogUtil {

   private static final Logger DEBUG_LOG = LoggerFactory.getLogger("esan.debug");
   private static final Logger INFO_LOG = LoggerFactory.getLogger("esan.info");
   private static final Logger WARN_LOG = LoggerFactory.getLogger("esan.warn");
   private static final Logger ERROR_LOG = LoggerFactory.getLogger("esan.error");

   private static String getStackTrack() {
      StackTraceElement ste = Thread.currentThread().getStackTrace()[4];
      return ste.getClassName() + "." + ste.getMethodName() + "()-->Line:" + ste.getLineNumber() + "-->";
   }

   public static void logDebug(String code, Object[] args) {
      logDebug(code, args, null);
   }

   public static void logDebug(String code, Object[] args, Throwable e) {
      if (DEBUG_LOG.isDebugEnabled()) {
         String message = MessageUtil.message(code, args == null ? new Object[] {} : args);
         message = getStackTrack() + message;
         if (e != null) {
            DEBUG_LOG.debug(message, e);
         } else {
            DEBUG_LOG.debug(message);
         }
      }
   }

   public static void logInfo(String code, Object[] args) {
      logInfo(code, args, null);
   }

   public static void logInfo(String code, Object[] args, Throwable e) {
      if (INFO_LOG.isInfoEnabled()) {
         String message = MessageUtil.message(code, args == null ? new Object[] {} : args);
         message = getStackTrack() + message;
         if (e != null) {
            INFO_LOG.info(message, e);
         } else {
            INFO_LOG.info(message);
         }
      }
   }

   public static void logWarn(String code, Object[] args) {
      logWarn(code, args, null);
   }

   public static void logWarn(String code, Object[] args, Throwable e) {
      if (WARN_LOG.isErrorEnabled()) {
         String message = MessageUtil.message(code, args == null ? new Object[] {} : args);
         message = getStackTrack() + message;
         if (e != null) {
            WARN_LOG.error(message, e);
         } else {
            WARN_LOG.error(message);
         }
      }
   }

   public static void logError(String code, Object[] args) {
      logError(code, args, null);
   }

   public static void logError(String code, Object[] args, Throwable e) {
      if (ERROR_LOG.isErrorEnabled()) {
         String message = MessageUtil.message(code, args == null ? new Object[] {} : args);
         message = getStackTrack() + message;
         if (e != null) {
            ERROR_LOG.error(message, e);
         } else {
            ERROR_LOG.error(message);
         }
      }
   }

   /**
    * 可做预警处理
    */
   public static void logMail() {
      // MAIL_LOG.
   }

   public static void logDB(HttpServletRequest request, String content) {
      LogDbHelper.log(request, content);
   }

}