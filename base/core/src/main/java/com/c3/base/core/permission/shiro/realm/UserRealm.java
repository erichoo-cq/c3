package com.c3.base.core.permission.shiro.realm;


import java.util.Collection;
import java.util.LinkedHashSet;

import javax.annotation.PostConstruct;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.c3.base.core.permission.shiro.UserDetails;
import com.c3.base.core.permission.shiro.service.IUserDetailsService;


/**
 * description: 自定义的用户realm，继承了授权realm，
 * 主要是扩展了doGetAuthorizationInfo（授权控制，）
 * 一个是doGetAuthenticationInfo（登录控制）
 * 
 *
 * @version 2016年3月28日 下午3:19:32
 * @see
 * modify content------------author------------date
 */
@Component
@ConditionalOnProperty(prefix = "shiro", name = "enabled", matchIfMissing = true)
@ConfigurationProperties(prefix = "shiro.realm")
public class UserRealm extends AuthorizingRealm {
	
	private static final Logger log = LoggerFactory.getLogger(UserRealm.class);
	
	//配置默认的securityManager,管理subject与realm关系
	@Autowired
	private DefaultWebSecurityManager securityManager;
	
	@Autowired
	private IUserDetailsService<?, ?> userDetailsService;

	@Autowired
	public UserRealm(CacheManager shiroCacheManager, CredentialsMatcher matcher) {
		super(shiroCacheManager, matcher);
	}
	
	@PostConstruct
	public void setToSecurityManager() {
		Collection<Realm> rs = securityManager.getRealms();
		if (null == rs) {
			securityManager.setRealm(this);
		} else {
			rs.add(this);
			securityManager.setRealms(rs);
		}
	}

	/**
	 * 将授权信息委托给shiro框架
	 * 
	 * @param principals 携带的subject认证信息，主要包含了role以及permission集合
	 * @return 返回shiro提供的SimpleAuthorizationInfo对象，主要包含了角色以及权限列表
	 * @see SimpleAuthorizationInfo()
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		final UserDetails<?, ?> user = (UserDetails<?, ?>) principals.getPrimaryPrincipal();
		if (user == null) {
			throw new UnknownAccountException("Account does not exist");
		}

		final SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		if (user.getRoles() != null) {
			info.setRoles(new LinkedHashSet<String>(user.getRoles()));
		}

		if (user.getPermissions() != null) {
			info.setStringPermissions(new LinkedHashSet<String>(user.getPermissions()));
		}

		return info;
	}
	
	/**
	 * 进行简单的登录认证
	 * 
	 * @param token 需要认证的信息，例如用户名与密码
	 * @return 返回 SimpleAuthenticationInfo对象
	 * @see SimpleAuthenticationInfo()
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		UsernamePasswordToken credentials = (UsernamePasswordToken) token;

		final String username = credentials.getUsername();
		if (username == null) {
			throw new UnknownAccountException("Username not provided");
		}
		
		final UserDetails<?, ?> user;
		try {
			user = userDetailsService.findByAccountAndHost(username, credentials.getHost());
		} catch (RuntimeException e) {
			log.error("Message: {}, Trace: {}", e.getLocalizedMessage(), e);
			throw e;
		}
		
		if (user == null) {
			throw new UnknownAccountException("Account does not exist");
		}
		return new SimpleAuthenticationInfo(user, user.getPassword(), ByteSource.Util.bytes(user.getAccount()), user.getAccount());
	}

}

