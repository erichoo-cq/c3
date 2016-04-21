package com.c3.base.core.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.c3.base.core.repository.jpa.BaseRepository;
import com.c3.base.core.repository.jpa.entity.BaseEntity;

/**
 * 
 * description: 基础业务逻辑，可加入一些通用逻辑，例如事物注解等
 * 
 * @author: heshan
 * @version 2016年4月21日 上午10:27:22
 * @see modify content------------author------------date
 */
@Transactional
public abstract class BaseService<E extends BaseEntity<ID>, ID extends Serializable> {

   @Autowired
   protected BaseRepository<E, ID> baseRepository;

   public void add(E entity) throws Exception {
      baseRepository.save(entity);
   }

   public void delete(E entity) {
      baseRepository.delete(entity);
   }

   public void delete(ID id) throws Exception {
      baseRepository.delete(id);
   }

   public void clearTable() throws Exception {
      baseRepository.deleteAll();
   }

   public void update(E entity) throws Exception {
      baseRepository.save(entity);
   }

   public E getOne(ID id) throws Exception {
      return baseRepository.findOne(id);
   }

   public List<E> getAll() throws Exception {
      return baseRepository.findAll();
   }

}
