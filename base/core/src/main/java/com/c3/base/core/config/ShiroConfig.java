package com.c3.base.core.config;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.DefaultPasswordService;
import org.apache.shiro.authc.credential.PasswordMatcher;
import org.apache.shiro.authc.credential.PasswordService;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.crypto.hash.DefaultHashService;
import org.apache.shiro.crypto.hash.HashService;
import org.apache.shiro.realm.text.TextConfigurationRealm;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.ShiroFilter;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import com.c3.base.core.permission.shiro.security.AuthenticationFilter;


@Configuration
@ConditionalOnClass(ShiroFilter.class)
@ConditionalOnProperty(prefix = "shiro", name = "enabled", matchIfMissing = true)
public class ShiroConfig {
	@Autowired
	private net.sf.ehcache.CacheManager ehcacheManager;
	
	@Bean(name = "shiroFilter")
	@ConditionalOnWebApplication
	@ConfigurationProperties(prefix = "shiro.filter")
	public ShiroFilterFactoryBean shiroFilterFactoryBean() {
		ShiroFilterFactoryBean factory = new ShiroFilterFactoryBean();
		factory.setSecurityManager(securityManager());

		Map<String, Filter> filters = new HashMap<String, Filter>(1);
		filters.put("authenticationFilter", new AuthenticationFilter());
		
		factory.setFilters(filters);
		
		return factory;
	}
	
	@Bean
	public CacheManager shiroCacheManager() {
		EhCacheManager cm = new EhCacheManager();
		cm.setCacheManager(ehcacheManager);
		cm.setCacheManagerConfigFile("classpath:config/ehcache.xml");
		return cm;
	}
	
	@Bean
	public SessionDAO sessionDao() {
		return new EnterpriseCacheSessionDAO();
	}

	@Bean
	@ConfigurationProperties(prefix = "shiro.sessionManager")
	public SessionManager sessionManager() {
		final DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
		sessionManager.setSessionDAO(sessionDao());
		return sessionManager;
	}
	
	@Bean
	@ConfigurationProperties(prefix = "shiro.realm")
	public TextConfigurationRealm textRealm() {
		return new TextConfigurationRealm();
	}
	
	@Bean
	public DefaultWebSecurityManager securityManager() {
		final DefaultWebSecurityManager sc = new DefaultWebSecurityManager();
		/**
		 * Be sure cache manager set before session manager and realm.
		 */
		sc.setCacheManager(shiroCacheManager());
		sc.setSessionManager(sessionManager());

		TextConfigurationRealm realm = textRealm();
		if (realm.getUserDefinitions() != null) {
			sc.setRealm(realm);
		}

		return sc;
	}
	
	@Bean
	@ConfigurationProperties(prefix = "shiro.encrypt")
	public HashService hashService() {
		return new DefaultHashService();
	}
	
	@Bean
	public PasswordService passwordService() {
		DefaultPasswordService ps = new DefaultPasswordService();
		ps.setHashService(hashService());
		return ps;
	}

	@Bean
	public CredentialsMatcher credentialsMatcher() {
		final PasswordMatcher credentialsMatcher = new PasswordMatcher();
		credentialsMatcher.setPasswordService(passwordService());
		return credentialsMatcher;
	}
	
	@Bean
	public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}

	@Bean
	@DependsOn("lifecycleBeanPostProcessor")
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
		final AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
		advisor.setSecurityManager(securityManager());
		return advisor;
	}

	@Bean
	@DependsOn("lifecycleBeanPostProcessor")
	public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
		final DefaultAdvisorAutoProxyCreator daap = new DefaultAdvisorAutoProxyCreator();
		daap.setProxyTargetClass(true);
		return daap;
	}
	
}
