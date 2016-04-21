package com.c3.base.dictionary.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.c3.base.core.service.BaseService;
import com.c3.base.dictionary.entity.Dictionary;
import com.c3.base.dictionary.repository.DictionaryRepository;

/**
 * 
 * description:数据字典业务逻辑类
 * 
 * @author: heshan
 * @version 2016年4月21日 上午10:43:27
 * @see modify content------------author------------date
 */
@Service
public class DictionaryService extends BaseService<Dictionary, Long> {

   @Autowired
   private DictionaryRepository dictionaryRepository;

   public Dictionary findByCode(String code) throws Exception {
      return dictionaryRepository.findByCode(code);
   }

   public List<Dictionary> findAll() throws Exception {
      return dictionaryRepository.findAll();
   }

   public void updateValue(String code, String value) throws Exception {
      dictionaryRepository.updateValueByCode(value, code);
   }

}
