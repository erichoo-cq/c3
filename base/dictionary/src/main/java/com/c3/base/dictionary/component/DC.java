package com.c3.base.dictionary.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.c3.base.dictionary.entity.Dictionary;
import com.c3.base.dictionary.service.DictionaryService;

/**
 * 
 * description:数据字典工具类，以静态方式获取字典
 * 
 * @author: heshan
 * @version 2016年4月21日 上午10:28:35
 * @see modify content------------author------------date
 */
@Component
public class DC {

   private static DictionaryService dictionaryService;

   public static Dictionary getDict(String code) {
      try {
         return dictionaryService.findByCode(code);
      } catch (Exception e) {
         e.printStackTrace();
         return null;
      }
   }

   public static String getValue(String code) {
      try {
         return dictionaryService.findByCode(code).getValue();
      } catch (Exception e) {
         return null;
      }
   }

   public static <T> T getValue(String code, Class<T> clazz) {
      try {
         return dictionaryService.findByCode(code).getValue(clazz);
      } catch (Exception e) {
         return null;
      }
   }

   @Autowired
   public void setDictionaryService(DictionaryService dictionaryService) {
      DC.dictionaryService = dictionaryService;
   }

}