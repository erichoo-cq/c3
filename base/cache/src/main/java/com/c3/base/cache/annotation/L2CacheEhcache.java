<<<<<<< HEAD:base/core/src/main/java/com/c3/base/core/cache/annotation/L2CacheEhcache.java
package com.c3.base.core.cache.annotation;

import java.lang.annotation.*;

/**
 * 
 * description:二级缓存的ehcache配置
 * 
 * @author: heshan
 * @version 2016年4月20日 下午3:54:06
 * @see modify content------------author------------date
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface L2CacheEhcache {

   String name() default "";

   /**
    * 设定内存中创建对象的最大值
    * 
    * @return
    */
   int maxEntriesLocalHeap() default 10000;

   /**
    * 设置元素是否永久驻留
    * 
    * @return
    */
   boolean eternal() default false;

   /**
    * 设置某个元素消亡前的停顿时间, 0意味着元素可以停顿无穷长的时间
    * 
    * @return
    */
   long timeToIdleSeconds() default 120;

   /**
    * 为元素设置消亡前的生存时间
    * 
    * @return
    */
   long timeToLiveSeconds() default 120;

   /**
    * 设置当内存中缓存达到maxInMemory 限制时元素是否可写到磁盘上
    * 
    * @return
    */
   boolean overflowToDisk() default true;

=======
package com.c3.base.cache.annotation;

import java.lang.annotation.*;

/**
 * description:二级缓存的ehcache配置
 * 
 * @author: heshan
 * @version 2016年4月20日 下午3:54:06
 * @see modify content------------author------------date
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface L2CacheEhcache {

   String name() default "";

   /**
    * 设定内存中创建对象的最大值
    * 
    * @return
    */
   int maxEntriesLocalHeap() default 10000;

   /**
    * 设置元素是否永久驻留
    * 
    * @return
    */
   boolean eternal() default false;

   /**
    * 设置某个元素消亡前的停顿时间, 0意味着元素可以停顿无穷长的时间
    * 
    * @return
    */
   long timeToIdleSeconds() default 120;

   /**
    * 为元素设置消亡前的生存时间
    * 
    * @return
    */
   long timeToLiveSeconds() default 120;

   /**
    * 设置当内存中缓存达到maxInMemory 限制时元素是否可写到磁盘上
    * 
    * @return
    */
   boolean overflowToDisk() default true;

>>>>>>> a21b9a6620b9017dad056e2ec2f7871c456b759a:base/cache/src/main/java/com/c3/base/cache/annotation/L2CacheEhcache.java
}