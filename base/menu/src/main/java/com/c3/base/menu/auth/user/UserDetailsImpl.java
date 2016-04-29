package com.c3.base.menu.auth.user;

import java.util.Collection;
import java.util.Collections;

import com.c3.base.core.permission.shiro.UserDetails;
import com.google.common.base.MoreObjects;

public class UserDetailsImpl implements UserDetails<Integer, User> {

	private static final long serialVersionUID = 1L;

	//用户
	private User user;
	
	//角色列表
	private Collection<String> roles = Collections.emptyList();
	
	//权限列表
	private Collection<String> permissions = Collections.emptyList();

	public UserDetailsImpl(User user, Collection<String> roles, Collection<String> permissions) {
		this.user = user;
		this.roles = roles;
		this.permissions = permissions;
	}

	@Override
	public String getAccount() {
		return this.user.getUserName();
	}

	@Override
	public Integer getAccountId() {
		return this.user.getUserId();
	}

	@Override
	public String getPassword() {
		return this.user.getPassword();
	}

	@Override
	public Collection<String> getPermissions() {
		return this.permissions;
	}

	@Override
	public Collection<String> getRoles() {
		return this.roles;
	}

	@Override
	public User getUser() {
		return this.user;
	}

	@Override
	public String getUsername() {
		return this.user.getUserName();
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).add("user", user).add("roles", roles).add("permissions", permissions).toString();
	}

}
