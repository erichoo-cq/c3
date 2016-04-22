package com.c3.base.cache.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.c3.base.cache.annotation.EnablePageCacheScanner;
import com.c3.base.cache.web.PageCacheFilter;

/**
 * 
 * description:注册页面缓存过滤器
 * 
 * @author: heshan
 * @version 2016年4月21日 上午10:31:30
 * @see
 */
@Configuration
public class PageCacheFilterConfig {

   @Autowired
   private AutowireCapableBeanFactory beanFactory;

   @Bean
   public FilterRegistrationBean registFilter() {
      FilterRegistrationBean registration = new FilterRegistrationBean();
      PageCacheFilter pageCacheFilter = new PageCacheFilter();
      EnablePageCacheScanner scanner = new EnablePageCacheScanner();
      scanner.scan(pageCacheFilter, "com.c3.**.pagecache");
      beanFactory.autowireBean(pageCacheFilter);
      registration.setFilter(pageCacheFilter);
      registration.addUrlPatterns(pageCacheFilter.getIncludePages());
      registration.addUrlPatterns(pageCacheFilter.getIncludePatterns());
      return registration;
   }
}
