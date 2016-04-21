package com.c3.base.core.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;

/**
 * 
 * description:spring 国际化工具类
 * 
 * @author: heshan
 * @version 2016年4月21日 上午10:50:47
 * @see modify content------------author------------date
 */
public class MessageUtil {

   private static MessageSource messageSource;

   /**
    * 根据消息键和参数 获取消息 委托给spring messageSource
    * 
    * @param code
    *           消息键
    * @param args
    *           参数
    * @return
    */
   public static String message(String code, Object... args) {
      if (messageSource == null) {
         messageSource = SpringUtil.getBean(MessageSource.class);
      }
      if (StringUtils.isNotBlank(code)) {
         return messageSource.getMessage(code, args, null);
      } else {
         return "";
      }

   }

}
