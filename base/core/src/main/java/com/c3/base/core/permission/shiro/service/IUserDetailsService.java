package com.c3.base.core.permission.shiro.service;

import java.io.Serializable;

import com.c3.base.core.permission.shiro.UserDetails;


public interface IUserDetailsService<ID extends Serializable, E> {

	public UserDetails<ID, E> findByAccountAndHost(String username, String host);

}