package com.c3.base.dictionary.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.c3.base.core.repository.jpa.BaseRepository;
import com.c3.base.dictionary.entity.Dictionary;

/**
 * 
 * description: 数据字典仓库类
 * 
 * @author: heshan
 * @version 2016年4月21日 上午10:43:01
 * @see modify content------------author------------date
 */
public interface DictionaryRepository extends BaseRepository<Dictionary, Long> {

   Dictionary findByCode(String code) throws Exception;

   @Modifying
   @Query("update Dictionary set value=:value where code=:code")
   int updateValueByCode(@Param("value") String value, @Param("code") String code) throws Exception;
}
