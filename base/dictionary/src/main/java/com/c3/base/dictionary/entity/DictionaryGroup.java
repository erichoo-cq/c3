package com.c3.base.dictionary.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.c3.base.cache.annotation.L2CacheEhcache;
import com.c3.base.core.repository.jpa.entity.BaseEntity;

/**
 * 
 * description:数据字典分组，不常更新，开启二级缓存
 * 
 * @author: heshan
 * @version 2016年4月21日 上午10:42:00
 * @see modify content------------author------------date
 */
@Entity
@Table(name = "DICTIONARY_GROUP_")
@L2CacheEhcache
public class DictionaryGroup extends BaseEntity<Long> {
   private static final long serialVersionUID = 1L;

   @Column(name = "name_")
   private String name;

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

}
