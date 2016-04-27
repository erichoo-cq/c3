package com.c3.base.cache.annotation;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

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

import com.c3.base.cache.web.PageCacheFilter;

/**
 * description: 扫描EnablePageCache注解，根据注解配置对页面缓存过滤器进行配置
 * 
 * @author: heshan
 * @version 2016年4月20日 下午3:58:46
 * @see
 */
public class EnablePageCacheScanner implements ResourceLoaderAware {
//   private final Log log = LogFactory.getLog(this.getClass());

   protected ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();

   protected final List<TypeFilter> includeFilters = new LinkedList<TypeFilter>();

   protected MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(
         this.resourcePatternResolver);

   public void setResourceLoader(ResourceLoader resourceLoader) {
      this.resourcePatternResolver = ResourcePatternUtils.getResourcePatternResolver(resourceLoader);
      this.metadataReaderFactory = new CachingMetadataReaderFactory(resourceLoader);
   }

   public final ResourceLoader getResourceLoader() {
      return this.resourcePatternResolver;
   }

   protected boolean matches(MetadataReader metadataReader) throws IOException {
      for (TypeFilter tf : this.includeFilters) {
         if (tf.match(metadataReader, this.metadataReaderFactory)) {
            return true;
         }
      }
      return false;
   }

   @SuppressWarnings("rawtypes")
   public void scan(PageCacheFilter filter, String... basePackages) {
      this.includeFilters.add(new AnnotationTypeFilter(EnablePageCache.class));
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
                  if ((includeFilters.size() == 0) || matches(metadataReader)) {
                     try {
                        ClassMetadata cm = metadataReader.getClassMetadata();
                        Class clazz = Class.forName(cm.getClassName());
                        EnablePageCache anno = AnnotationUtils.findAnnotation(clazz, EnablePageCache.class);
                        filter.addIncludePages(anno.includePages());
                        filter.addIncludePatterns(anno.includePatterns());
                        filter.addExcludePages(anno.excludePages());
                        filter.addExcludePatterns(anno.excludePatterns());
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
}
