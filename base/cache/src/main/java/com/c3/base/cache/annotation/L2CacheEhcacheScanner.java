package com.c3.base.cache.annotation;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.cache.ehcache.EhCacheMessageLogger;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.util.ClassUtils;
import org.springframework.util.SystemPropertyUtils;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

/**
 * description: 扫描L2CacheEhCache注解，根据注解配置生成Cache并加入到CacheManager
 * 
 * @author: heshan
 * @version 2016年4月20日 下午3:58:46
 * @see modify content------------author------------date
 */
public class L2CacheEhcacheScanner implements ResourceLoaderAware {
   private static final EhCacheMessageLogger LOG = Logger.getMessageLogger(EhCacheMessageLogger.class,
         L2CacheEhcacheScanner.class.getName());

   protected ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();

   protected final List<TypeFilter> includeFilters = new LinkedList<TypeFilter>();

   protected final List<TypeFilter> excludeFilters = new LinkedList<TypeFilter>();

   protected MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(
         this.resourcePatternResolver);

   public void setResourceLoader(ResourceLoader resourceLoader) {
      this.resourcePatternResolver = ResourcePatternUtils.getResourcePatternResolver(resourceLoader);
      this.metadataReaderFactory = new CachingMetadataReaderFactory(resourceLoader);
   }

   public final ResourceLoader getResourceLoader() {
      return this.resourcePatternResolver;
   }

   public void addIncludeFilter(TypeFilter includeFilter) {
      this.includeFilters.add(includeFilter);
   }

   public void addExcludeFilter(TypeFilter excludeFilter) {
      this.excludeFilters.add(0, excludeFilter);
   }

   public void resetFilters(boolean useDefaultFilters) {
      this.includeFilters.clear();
      this.excludeFilters.clear();
   }

   protected boolean matches(MetadataReader metadataReader) throws IOException {
      for (TypeFilter tf : this.excludeFilters) {
         if (tf.match(metadataReader, this.metadataReaderFactory)) {
            return false;
         }
      }
      for (TypeFilter tf : this.includeFilters) {
         if (tf.match(metadataReader, this.metadataReaderFactory)) {
            return true;
         }
      }
      return false;
   }

   @SuppressWarnings("rawtypes")
   public void scan(CacheManager manager, String... basePackages) {
      this.addIncludeFilter(new AnnotationTypeFilter(L2CacheEhcache.class));
      for (String basePackage : basePackages) {
         try {
            String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX
                  + ClassUtils.convertClassNameToResourcePath(SystemPropertyUtils.resolvePlaceholders(basePackage))
                  + "/**/*.class";
            Resource[] resources = this.resourcePatternResolver.getResources(packageSearchPath);
            for (int i = 0; i < resources.length; i++) {
               Resource resource = resources[i];
               if (resource.isReadable()) {
                  MetadataReader metadataReader = this.metadataReaderFactory.getMetadataReader(resource);
                  if ((includeFilters.size() == 0 && excludeFilters.size() == 0) || matches(metadataReader)) {
                     try {
                        ClassMetadata cm = metadataReader.getClassMetadata();
                        Class clazz = Class.forName(cm.getClassName());
                        L2CacheEhcache anno = AnnotationUtils.findAnnotation(clazz, L2CacheEhcache.class);
                        String cacheName = anno.name();
                        if (StringUtils.isBlank(cacheName)) {// 未定义cache的名字,则使用类得名字
                           cacheName = clazz.getName();
                           LOG.debug("未定义缓存名称,取类名" + cacheName);
                        }
                        if (manager.cacheExists(cacheName)) {// 缓存已经使用xml定义,则不处理
                           LOG.debug("缓存" + cacheName + "已经存在,不再根据注解进行配置");
                        } else {
                           Cache cache = new Cache(cacheName, anno.maxEntriesLocalHeap(), anno.overflowToDisk(),
                                 anno.eternal(), anno.timeToLiveSeconds(), anno.timeToIdleSeconds());
                           manager.addCache(cache);
                        }
                     } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                     }

                  }
               }
            }
         } catch (IOException ex) {
            throw new BeanDefinitionStoreException("I/O failure during classpath scanning", ex);
         }
      }
   }

   // public static void main(String[] args) {
   // Set<Class> set = ClassScanner.scan("com.esan.test.**.entity",
   // L2CacheEhcache.class);
   // for (Class class1 : set) {
   // System.out.println(class1.getName());
   // }
   // }
}
