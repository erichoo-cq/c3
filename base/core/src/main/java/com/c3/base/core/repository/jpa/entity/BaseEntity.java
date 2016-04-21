package com.c3.base.core.repository.jpa.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.data.domain.Persistable;

/**
 * @author heshan
 */
@MappedSuperclass
public class BaseEntity<ID extends Serializable> implements Persistable<ID> {
   private static final long serialVersionUID = 1L;

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "id_", length = 32)
   protected ID id;

   public ID getId() {
      return id;
   }

   public void setId(ID id) {
      this.id = id;
   }

   @Override
   public boolean isNew() {
      return null == getId();
   }

   @Override
   public boolean equals(Object obj) {
      if (null == obj) {
         return false;
      }
      if (this == obj) {
         return true;
      }
      if (!getClass().equals(obj.getClass())) {
         return false;
      }
      BaseEntity<?> that = (BaseEntity<?>) obj;
      return null == this.getId() ? false : this.getId().equals(that.getId());
   }

   @Override
   public int hashCode() {
      int hashCode = 17;
      hashCode += null == getId() ? 0 : getId().hashCode() * 31;
      return hashCode;
   }

   @Override
   public String toString() {
      return ReflectionToStringBuilder.toString(this);
   }

}
