package com.c3.base.core.permission.shiro;


import java.io.Serializable;
import java.util.Collection;

public interface UserDetails<ID extends Serializable, E> extends Serializable {
	
	/**
	 * 获取用户实体
	 * @return
	 */
	E getUser();

	/**
	 * 获取账号id
	 * 
	 * @return accountId
	 */
	ID getAccountId();

	/**
	 * 获取登录账号
	 * 
	 * @return account
	 */
	String getAccount();

	/**
	 * 获取用户名
	 * 
	 * @return username
	 */
	String getUsername();

	/**
	 * 获取用户密码
	 * 
	 * @return 用户密码
	 */
	String getPassword();

	/**
	 * 获取用户角色名列表
	 * 
	 * @return 角色名列表
	 */
	Collection<String> getRoles();

	/**
	 * 获取用户权限列表
	 * 
	 * @return 权限列表
	 */
	Collection<String> getPermissions();

}
