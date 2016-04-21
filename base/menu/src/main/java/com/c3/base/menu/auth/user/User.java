/**
 * Created on 2015年12月16日
 *
 * Copyright(c) Chongqing Communication Industry Services Co.LTD, 2015.  All rights reserved. 
 */
package com.c3.base.menu.auth.user;

import java.io.Serializable;

import com.google.common.base.MoreObjects;

public class User implements Serializable {

	private static final long serialVersionUID = -3142453117416681694L;

	private Integer userId;// 用户id
	private String userName;// 账号
	private String nickName;// 昵称
	private String password;// 密码密文
	private Integer userType;// 用户类型 1 中心用户 2区县用户 3 企业用户
	private String orgCode;// 机构编码
	private String orgName;// 机构名

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public UserType getType(){
		return UserType.formValue(this.userType);
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).add("userId", userId).add("userName", userName).add("nickName", nickName).add("password", password)
				.add("userType", userType).add("orgCode", orgCode).add("orgName", orgName).toString();
	}

}
