package com.c3.test.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.c3.base.cache.annotation.L2CacheEhcache;

@Entity
@Table(name = "CacheTester")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@L2CacheEhcache
public class CacheTester implements Serializable {
   private static final long serialVersionUID = 1L;

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "id_", length = 32)
   protected Long id;

   @Column(name = "name_")
   private String name;

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

}
