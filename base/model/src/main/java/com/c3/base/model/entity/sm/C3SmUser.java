package com.c3.base.model.entity.sm;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.domain.Persistable;

/**
 * description: 系统用户表，与person表是多对一的关系，
 * 一般来说一个用户对应一个实际的person，不排除一个
 * person有多个用户情况
 *
 * @version 2016年3月17日 下午4:28:40
 * @see
 * modify content------------author------------date
 */
@Entity
@Table(name = "c3_sm_user")
//@NamedQuery(name = "SmBtUser.findAll", query = "SELECT s FROM SmBtUser s")
public class C3SmUser implements Persistable<Integer> {
	private static final long serialVersionUID = 1L;
	
	@Id
//	@SequenceGenerator(name = "SM_BT_USER_USERID_GENERATOR", sequenceName = "SM_SEQ_USER_ID")
//	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SM_BT_USER_USERID_GENERATOR")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Integer userId;

	//邮件地址
	@Column(unique = true)
	private String email;

	//是否登录
	@Column(name = "is_login")
	private Boolean isLogin;

	//是否有效
	@Column(name = "is_valid")
	private Boolean isValid;

	//最后登录时间
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_login_time")
	private Date lastLoginTime;

	//密码
	@Column(name = "password", nullable = false)
	private String password;

	//登录次数
	@Column(name = "login_times")
	private Integer loginTimes;

	//用户名
	@Column(name = "user_name")
	private String userName;

	// bi-directional many-to-one association to SmBtPerson
	//一个person可以对应多个用户
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "person_id", insertable = true, updatable = true)
	private C3SmPerson person;

	public C3SmUser() {
	}

	@Override
	public Integer getId() {
		return this.userId;
	}

	@Override
	public boolean isNew() {
		return this.userId == null;
	}

	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getIsLogin() {
		return this.isLogin;
	}

	public void setIsLogin(Boolean isLogin) {
		this.isLogin = isLogin;
	}

	public Boolean getIsValid() {
		return this.isValid;
	}

	public void setIsValid(Boolean isValid) {
		this.isValid = isValid;
	}

	public Date getLastLoginTime() {
		return this.lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String loginPassword) {
		this.password = loginPassword;
	}

	public Integer getLoginTimes() {
		return this.loginTimes;
	}

	public void setLoginTimes(Integer loginTimes) {
		this.loginTimes = loginTimes;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public C3SmPerson getPerson() {
		return this.person;
	}

	public void setPerson(C3SmPerson person) {
		this.person = person;
	}

}