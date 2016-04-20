package com.c3.base.cache.annotation;

import java.lang.annotation.*;

/**
 * description: 开启查询缓存
 * 
 * @author: heshan
 * @version 2016年4月20日 下午3:53:38
 * @see modify content------------author------------date
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EnableQueryCache {
   boolean value() default true;
}