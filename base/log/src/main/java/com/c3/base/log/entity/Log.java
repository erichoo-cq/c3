package com.c3.base.log.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.c3.base.core.repository.jpa.entity.BaseEntity;

/**
 * 
 * description: 日志实体类
 * 
 * @author: heshan
 * @version 2016年4月21日 上午9:52:56
 * @see modify content------------author------------date
 */
@Entity
@Table(name = "LOG_")
public class Log extends BaseEntity<Long> {
   private static final long serialVersionUID = 1L;

   @Column(name = "content_")
   private String content;

   @Column(name = "user_name_")
   private String userName;

   @Column(name = "ip_", length = 20)
   private String ip;

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

   public String getIp() {
      return ip;
   }

   public void setIp(String ip) {
      this.ip = ip;
   }

   public Date getCreateTime() {
      return createTime;
   }

   public void setCreateTime(Date createTime) {
      this.createTime = createTime;
   }

}
