package com.c3.base.core.cache.ehcache;

import java.net.URL;
import java.util.Properties;

import org.hibernate.cache.CacheException;
import org.hibernate.cache.ehcache.EhCacheMessageLogger;
import org.hibernate.cache.ehcache.EhCacheRegionFactory;
import org.hibernate.cache.ehcache.internal.util.HibernateEhcacheUtils;
import org.hibernate.cfg.Settings;
import org.jboss.logging.Logger;

import com.c3.base.core.cache.annotation.L2CacheEhcacheScanner;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.config.Configuration;
import net.sf.ehcache.config.ConfigurationFactory;

public class EhCacheAnnotationRegionFactory extends EhCacheRegionFactory {
   private static final long serialVersionUID = 1L;
   private static final EhCacheMessageLogger LOG = Logger.getMessageLogger(EhCacheMessageLogger.class,
         EhCacheAnnotationRegionFactory.class.getName());

   @Override
   public void start(Settings settings, Properties properties) throws CacheException {
      this.settings = settings;
      if (manager != null) {
         LOG.attemptToRestartAlreadyStartedEhCacheProvider();
         return;
      }
      try {
         String configurationResourceName = null;
         if (properties != null) {
            configurationResourceName = (String) properties.get(NET_SF_EHCACHE_CONFIGURATION_RESOURCE_NAME);
         }
         if (configurationResourceName == null || configurationResourceName.length() == 0) {
            final Configuration configuration = ConfigurationFactory.parseConfiguration();
            manager = new CacheManager(configuration);
         } else {
            final URL url = loadResource(configurationResourceName);
            final Configuration configuration = HibernateEhcacheUtils.loadAndCorrectConfiguration(url);
            manager = new CacheManager(configuration);
         }
         
         //加入注解的扫描,以xml为主
         L2CacheEhcacheScanner scanner = new L2CacheEhcacheScanner();
         scanner.scan(manager, "com.esan.**.entity");
         mbeanRegistrationHelper.registerMBean(manager, properties);
      } catch (net.sf.ehcache.CacheException e) {
         if (e.getMessage().startsWith("Cannot parseConfiguration CacheManager. Attempt to create a new instance of "
               + "CacheManager using the diskStorePath")) {
            throw new CacheException("Attempt to restart an already started EhCacheRegionFactory. "
                  + "Use sessionFactory.close() between repeated calls to buildSessionFactory. "
                  + "Consider using SingletonEhCacheRegionFactory. Error from ehcache was: " + e.getMessage());
         } else {
            throw new CacheException(e);
         }
      }
   }

}
