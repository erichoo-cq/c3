package com.c3.sample.fts.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.c3.base.core.repository.jpa.entity.BaseEntity;

/**
 * 
 * description: 全文检索实体类
 * 
 * @author: heshan
 * @version 2016年4月21日 上午9:52:56
 * @see
 */
@Entity
@Table(name = "FTS_")
//@Indexed
//@Analyzer(impl = SimpleAnalyzer.class)
public class Fts extends BaseEntity<Long> {
   private static final long serialVersionUID = 1L;

//   @Field()
   @Column(name = "content_")
   private String content;

   @Column(name = "user_name_")
   private String userName;

   @Temporal(TemporalType.TIMESTAMP)
   @Column(name = "create_time_")
   protected Date createTime = new Date();

   public String getContent() {
      return content;
   }

   public void setContent(String content) {
      this.content = content;
   }

   public String getUserName() {
      return userName;
   }

   public void setUserName(String userName) {
      this.userName = userName;
   }

   public Date getCreateTime() {
      return createTime;
   }

   public void setCreateTime(Date createTime) {
      this.createTime = createTime;
   }

}
