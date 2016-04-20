package com.c3.base.core.repository.jpa.support;

import java.io.Serializable;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

import com.esan.base.coredata.entity.BaseEntity;

public class BaseRepositoryFactoryBean<R extends JpaRepository<E, ID>, E extends BaseEntity<ID>, ID extends Serializable>
      extends JpaRepositoryFactoryBean<R, E, ID> {

   public BaseRepositoryFactoryBean() {
   }

   protected RepositoryFactorySupport createRepositoryFactory(EntityManager entityManager) {
      return new BaseRepositoryFactory(entityManager);
   }
}
