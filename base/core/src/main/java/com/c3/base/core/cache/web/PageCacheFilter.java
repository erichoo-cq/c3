package com.c3.base.core.cache.web;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import com.opensymphony.oscache.web.filter.CacheFilter;

public class PageCacheFilter extends CacheFilter {
   private final static PathMatcher pathMatcher = new AntPathMatcher();
   private final static String URL_SPLIT_PATTERN = "[, ;\r\n]";
   private final static String EXCLUDED_PAGES = "excludedPages";
   private final static String EXCLUDED_PATTERNS = "excludedPatterns";

   private String[] pages;
   private String[] patterns;

   public void init(FilterConfig filterConfig) {
      pages = getExcludedPages(filterConfig);
      patterns = getExcludedPatterns(filterConfig);
      super.init(filterConfig);
   }

   public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
         throws IOException, ServletException {
      HttpServletRequest req = (HttpServletRequest) request;
      if (isExcludedUrl(pages, patterns, req.getServletPath())) {// 不包含
         chain.doFilter(request, response);
      } else {
         super.doFilter(request, response, chain);
      }
   }

   private boolean isExcludedUrl(String[] pages, String[] patterns, String url) {
      if (pages != null) {
         for (int i = 0; i < pages.length; i++) {
            if (url.equals(pages[i])) {
               return true;
            }
         }
      }
      if (patterns != null) {
         for (int i = 0; i < patterns.length; i++) {
            if (pathMatcher.match(patterns[i], url)) {
               return true;
            }
         }
      }
      return false;
   }

   private String[] getExcludedPages(FilterConfig filterConfig) {
      String excludedPages = filterConfig.getInitParameter(EXCLUDED_PAGES);
      if (excludedPages != null && !"".equals(excludedPages)) {
         return excludedPages.split(URL_SPLIT_PATTERN);
      }
      return null;
   }

   private String[] getExcludedPatterns(FilterConfig filterConfig) {
      String excludedPages = filterConfig.getInitParameter(EXCLUDED_PATTERNS);
      if (excludedPages != null && !"".equals(excludedPages)) {
         return excludedPages.split(URL_SPLIT_PATTERN);
      }
      return null;
   }
}