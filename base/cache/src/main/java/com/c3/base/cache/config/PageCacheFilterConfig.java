package com.c3.base.cache.config;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

   @Value("${c3_cache.pagecache.enable:false}")
   private boolean enable;

   @Value("${c3_cache.pagecache.packagescan:}")
   private String packagescan;

   @Autowired
   private AutowireCapableBeanFactory beanFactory;

   @Bean
   public FilterRegistrationBean registFilter() {
      FilterRegistrationBean registration = new FilterRegistrationBean();
      if (enable) {
         PageCacheFilter pageCacheFilter = new PageCacheFilter();
         EnablePageCacheScanner scanner = new EnablePageCacheScanner();
         scanner.scan(pageCacheFilter, packagescan.split(","));
         beanFactory.autowireBean(pageCacheFilter);
         registration.setFilter(pageCacheFilter);
         registration.addUrlPatterns(pageCacheFilter.getIncludePages());
         registration.addUrlPatterns(pageCacheFilter.getIncludePatterns());
      } else {
         registration.setEnabled(false);
         registration.setFilter(new Filter() {
            @Override
            public void init(FilterConfig filterConfig) throws ServletException {
            }

            @Override
            public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
                  throws IOException, ServletException {
               chain.doFilter(request, response);
            }

            @Override
            public void destroy() {
            }
         });
      }
      return registration;
   }
}
