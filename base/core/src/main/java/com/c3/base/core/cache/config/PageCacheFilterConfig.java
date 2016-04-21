package com.c3.base.core.cache.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import com.c3.base.core.cache.web.PageCacheFilter;

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
