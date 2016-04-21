<<<<<<< HEAD:base/core/src/main/java/com/c3/base/core/cache/annotation/EnableQueryCache.java
package com.c3.base.core.cache.annotation;

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
=======
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
>>>>>>> a21b9a6620b9017dad056e2ec2f7871c456b759a:base/cache/src/main/java/com/c3/base/cache/annotation/EnableQueryCache.java
}