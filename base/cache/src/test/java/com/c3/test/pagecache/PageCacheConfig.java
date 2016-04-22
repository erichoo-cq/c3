package com.c3.test.pagecache;

import com.c3.base.cache.annotation.EnablePageCache;

@EnablePageCache(
      includePages = {"/nocache/cache1"},//缓存了指定页面
      includePatterns = {"/cache/*"},//缓存了匹配该模型的所有页面
      excludePages = {"/cache/cache2"}//排除该页面的缓存
      )
public class PageCacheConfig {

}
