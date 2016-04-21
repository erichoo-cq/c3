package com.c3.base.cache.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import com.c3.base.cache.web.PageCacheFilter;

/**
 * 
 * description:注册页面缓存过滤器
 * 
 * @author: heshan
 * @version 2016年4月21日 上午10:31:30
 * @see modify content------------author------------date
 */
public class PageCacheFilterConfig {

   private @Autowired AutowireCapableBeanFactory beanFactory;

   @Bean
   public FilterRegistrationBean myFilter() {
      FilterRegistrationBean registration = new FilterRegistrationBean();
      PageCacheFilter pageCacheFilter = new PageCacheFilter();
      beanFactory.autowireBean(pageCacheFilter);
      registration.setFilter(pageCacheFilter);
      registration.addUrlPatterns("/myfitlerpath/*");
      return registration;
   }
}
