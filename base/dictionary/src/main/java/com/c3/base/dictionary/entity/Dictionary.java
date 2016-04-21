package com.c3.base.dictionary.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.c3.base.cache.annotation.L2CacheEhcache;

import src.main.java.com.c3.base.core.repository.jpa.entity.BaseEntity;

/**
 * 
 * description:数据字典实体类，不常更新，开启二级缓存。
 * 
 * @author: heshan
 * @version 2016年4月21日 上午10:41:29
 * @see modify content------------author------------date
 */
@Entity
@Table(name = "DICTIONARY_")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@L2CacheEhcache
public class Dictionary extends BaseEntity<Long> {
   private static final long serialVersionUID = 1L;

   @Column(name = "group_id_")
   private Long groupId;

   @Column(name = "name_")
   private String name;

   @Column(name = "code_")
   private String code;

   @Column(name = "value_")
   private String value;

   public String getCode() {
      return code;
   }

   public void setCode(String code) {
      this.code = code;
   }

   public Long getGroupId() {
      return groupId;
   }

   public void setGroupId(Long groupId) {
      this.groupId = groupId;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getValue() {
      return value;
   }

   @SuppressWarnings("unchecked")
   public <T> T getValue(Class<T> clazz) {
      try {
         return (T) clazz.getDeclaredMethod("valueOf", String.class).invoke(clazz, value);
      } catch (Exception e) {
         e.printStackTrace();
         return null;
      }
   }

   public void setValue(String value) {
      this.value = value;
   }

}
