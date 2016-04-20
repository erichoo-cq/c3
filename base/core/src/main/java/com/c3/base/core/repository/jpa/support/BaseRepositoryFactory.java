package com.c3.base.core.repository.jpa.support;

import java.io.Serializable;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.core.RepositoryInformation;
import org.springframework.data.repository.core.RepositoryMetadata;

import com.c3.base.core.repository.jpa.BaseRepositoryImpl;

public class BaseRepositoryFactory extends JpaRepositoryFactory {

   public BaseRepositoryFactory(EntityManager entityManager) {
      super(entityManager);
   }

   @Override
   @SuppressWarnings({ "unchecked", "rawtypes" })
   protected <E, ID extends Serializable> SimpleJpaRepository<E, ID> getTargetRepository(
         RepositoryInformation information, EntityManager entityManager) {
      JpaEntityInformation<?, Serializable> entityInformation = getEntityInformation(information.getDomainType());
      return new BaseRepositoryImpl(entityInformation, entityManager);
   }

   @Override
   protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
      return BaseRepositoryImpl.class;
   }

}
