package com.c3.base.core.config;

import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.c3.base.core.repository.jpa.support.BaseRepositoryFactoryBean;

/**
 * 配置类
 * 
 * @author heshan
 */
@Configuration
@EntityScan("com.c3.**.entity")
@EnableJpaRepositories(basePackages = { "com.c3" }, repositoryFactoryBeanClass = BaseRepositoryFactoryBean.class)
@EnableTransactionManagement
public class JpaConfig {
}