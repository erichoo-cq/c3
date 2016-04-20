package com.c3.base.cache.annotation;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.util.ClassUtils;
import org.springframework.util.SystemPropertyUtils;

/**
 * 
 * description: 类扫描器的基类
 * 
 * @author: heshan
 * @version 2016年4月20日 下午3:54:43
 * @see modify content------------author------------date
 */
@SuppressWarnings({ "rawtypes" })
public class ClassScanner implements ResourceLoaderAware {

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

   /**
    * 寻找basePackage下面注解了annotations的所有类
    * 
    * @param basePackage
    * @param annotations
    * @return
    */
   @SuppressWarnings("unchecked")
   public Set<Class> scan(String basePackage, Class<? extends Annotation>... annotations) {
      return scan(new String[] { basePackage }, annotations);
   }

   /**
    * 寻找basePackages下面注解了annotations的所有类
    * 
    * @param basePackage
    * @param annotations
    * @return
    */
   @SuppressWarnings("unchecked")
   public Set<Class> scan(String[] basePackages, Class<? extends Annotation>... annotations) {
      for (Class anno : annotations) {
         this.addIncludeFilter(new AnnotationTypeFilter(anno));
      }
      Set<Class> classes = new HashSet<Class>();
      for (String basePackage : basePackages) {
         classes.addAll(this.doScan(basePackage));
      }
      return classes;
   }

   /**
    * 寻找特定包下面的所有的类
    * 
    * @param basePackage
    * @return
    */
   public Set<Class> doScan(String basePackage) {
      Set<Class> classes = new HashSet<Class>();
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
                     classes.add(Class.forName(metadataReader.getClassMetadata().getClassName()));
                  } catch (ClassNotFoundException e) {
                     e.printStackTrace();
                  }
               }
            }
         }
      } catch (IOException ex) {
         throw new BeanDefinitionStoreException("I/O failure during classpath scanning", ex);
      }
      return classes;
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

   // public static void main(String[] args) {
   // Set<Class> set = ClassScanner.scan("com.esan.**.urlrewrite",
   // EsanHttpUrl.class);
   // for (Class class1 : set) {
   // System.out.println(class1.getName());
   // }
   // }

}
