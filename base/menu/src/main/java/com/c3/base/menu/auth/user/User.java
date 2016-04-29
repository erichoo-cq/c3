package com.c3.base.menu.auth.user;

import java.io.Serializable;

import com.google.common.base.MoreObjects;

/**
 * description: 用户vo对象，主要有用户id，用户名，密码，以及角色类型
 *              用于登录与权限控制
 *
 * @version 2016年4月27日 下午10:13:12
 * @see
 * modify content------------author------------date
 */
public class User implements Serializable {

	private static final long serialVersionUID = -3142453117416681694L;

	//用户id
	private Integer userId;
	
	//账号
	private String userName;
	
	//昵称
	private String nickName;
	
	//密码密文
	private String password;
	
	//用户类型
	private Integer userType;
	
	//机构编码，可以省略
	private String orgCode;
	
	//机构名，可以省略
	private String orgName;

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
