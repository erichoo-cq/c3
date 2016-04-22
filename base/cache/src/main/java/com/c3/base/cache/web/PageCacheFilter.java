package com.c3.base.cache.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import com.opensymphony.oscache.web.filter.CacheFilter;

/**
 * 
 * description:页面缓存过滤器
 * 
 * @author: heshan
 * @version 2016年4月21日 上午10:31:10
 * @see
 */
public class PageCacheFilter extends CacheFilter {
   private final static PathMatcher pathMatcher = new AntPathMatcher();
   private List<String> includePages = new ArrayList<String>();
   private List<String> includePatterns = new ArrayList<String>();
   private List<String> excludePages = new ArrayList<String>();
   private List<String> excludePatterns = new ArrayList<String>();

   public void addIncludePages(String[] includePages) {
      for (String page : includePages) {
         this.includePages.add(page);
      }
   }

   public String[] getIncludePages() {
      return this.includePages.toArray(new String[] {});
   }

   public void addIncludePatterns(String[] includePatterns) {
      for (String pattern : includePatterns) {
         this.includePatterns.add(pattern);
      }
   }

   public String[] getIncludePatterns() {
      return this.includePatterns.toArray(new String[] {});
   }

   public void addExcludePages(String[] excludePages) {
      for (String page : excludePages) {
         this.excludePages.add(page);
      }
   }

   public void addExcludePatterns(String[] excludePatterns) {
      for (String pattern : excludePatterns) {
         this.excludePatterns.add(pattern);
      }
   }

   public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
         throws IOException, ServletException {
      HttpServletRequest req = (HttpServletRequest) request;
      if (isExcludedUrl(excludePages, excludePatterns, req.getServletPath())) {// 不包含
         chain.doFilter(request, response);
      } else {
         super.doFilter(request, response, chain);
      }
   }

   private boolean isExcludedUrl(List<String> pages, List<String> patterns, String url) {
      if (pages != null && !pages.isEmpty()) {
         for (int i = 0; i < pages.size(); i++) {
            if (url.equals(pages.get(i))) {
               return true;
            }
         }
      }
      if (patterns != null && !patterns.isEmpty()) {
         for (int i = 0; i < patterns.size(); i++) {
            if (pathMatcher.match(pages.get(i), url)) {
               return true;
            }
         }
      }
      return false;
   }

}