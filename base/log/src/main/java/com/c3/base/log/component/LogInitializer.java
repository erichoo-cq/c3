package com.c3.base.log.component;

import java.io.File;
import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.c3.base.core.util.CommonFileReader;
import com.c3.base.dictionary.component.DC;
import com.c3.base.log.constant.Constant;

/**
 * 
 * description:日志初始化
 * 
 * @author: heshan
 * @version 2016年4月21日 上午10:45:11
 * @see modify content------------author------------date
 */
@Component
public class LogInitializer implements ApplicationListener<ContextRefreshedEvent> {

   private static final String RootLogger = "log4j.rootLogger";
   private static final String DebugFile = "log4j.appender.debug.File";
   private static final String InfoFile = "log4j.appender.info.File";
   private static final String WarnFile = "log4j.appender.warn.File";
   private static final String ErrorFile = "log4j.appender.error.File";

   @Override
   public void onApplicationEvent(ContextRefreshedEvent event) {
      try {
         String logDir = DC.getValue(Constant.LOG_DIR);
         String logRoot = DC.getValue(Constant.LOG_ROOT);
         Properties props = CommonFileReader.readProperties("classpath:log4j.properties");
         if (logRoot != null) {
            props.setProperty(RootLogger, logRoot);
         }
         if (logDir != null) {
            props.setProperty(DebugFile, logDir + File.separator + props.getProperty(DebugFile));
            props.setProperty(InfoFile, logDir + File.separator + props.getProperty(InfoFile));
            props.setProperty(WarnFile, logDir + File.separator + props.getProperty(WarnFile));
            props.setProperty(ErrorFile, logDir + File.separator + props.getProperty(ErrorFile));
         }
         PropertyConfigurator.configure(props);
         System.out.println("************日志系统初始化成功************");
      } catch (Exception e) {
         System.err.println("************日志系统初始化失败************");
         System.err.println(e.getMessage());
      }
   }

}
