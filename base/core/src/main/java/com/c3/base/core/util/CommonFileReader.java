package com.c3.base.core.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Properties;

import org.springframework.util.StreamUtils;

/**
 * 文件操作工具
 * 
 * @author heshan
 */
public class CommonFileReader {

   /**
    * 读取properties文件 以classpath:开头表示从类路径读取;以file:开头表示从文件系统中读取
    * 
    * @return
    * @throws IOException
    */
   public static Properties readProperties(String filePath) throws IOException {
      Properties prop = new Properties();
      InputStream in = loadFileAsStream(filePath);
      prop.load(new InputStreamReader(in, "UTF-8"));
      return prop;
   }

   /**
    * 读取文件为String
    * 
    * @param filePath
    * @param encoding
    * @return
    * @throws IOException
    */
   public static String loadFileAsString(String filePath, String code) throws IOException {
      InputStream in = loadFileAsStream(filePath);
      return StreamUtils.copyToString(in, Charset.forName(code));
   }

   /**
    * 读取文件为InputStream,以classpath:开头表示从类路径读取;以file:开头表示从文件系统中读取
    * 
    * @param filePath
    * @return
    * @throws FileNotFoundException
    */
   public static InputStream loadFileAsStream(String filePath) throws FileNotFoundException {
      return new FileInputStream(getFile(filePath));
   }

   /**
    * 读取文件
    * 
    * @param filePath
    * @return File
    */
   public static File getFile(String filePath) {
      File file = new File(convertPath(filePath));
      if (!file.exists()) {
         throw new RuntimeException("文件" + file.getAbsolutePath() + "不存在.");
      }
      return file;
   }

   /**
    * 转换路径
    * 
    * @param filePath
    * @return File
    */
   public static String convertPath(String filePath) {
      if (filePath.startsWith("classpath:")) {
         filePath = filePath.substring(10);
         filePath = Thread.currentThread().getContextClassLoader().getResource(filePath).toString();
         // if (filePath.startsWith("jar:")) {// 文件在jar包里面
         // filePath = filePath.substring(9, filePath.lastIndexOf("!"));
         // filePath = filePath.replace('/', '\\').replace("jar:file:", "");
         // } else {// 文件不在jar包里面
         // }
         filePath = filePath.replace('/', '\\').replace("file:", "");
      } else if (filePath.startsWith("file:")) {
         filePath = filePath.substring(5);
      } else if (filePath.startsWith("webinf:")) {
         filePath = filePath.substring(7);
         String path = Thread.currentThread().getContextClassLoader().getResource("").toString();
         path = path.replace('/', '\\').replace("file:", "").replace("classes\\", "").substring(1);
         filePath = path + filePath;
      } else {
         throw new RuntimeException("指定文件路径不合规范,");
      }
      return filePath;
   }

   private CommonFileReader() {
   }

}
