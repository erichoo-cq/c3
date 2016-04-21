package com.c3.base.core.repository.jpa;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.c3.base.core.repository.jpa.entity.BaseEntity;
import com.c3.base.core.repository.jpa.entity.C3PageRequest;
import com.c3.base.core.repository.jpa.qlhelper.OrderBy;
import com.c3.base.core.repository.jpa.qlhelper.Set;
import com.c3.base.core.repository.jpa.qlhelper.Where;

/**
 * 
 * description:扩展JpaRepository,增加了通用的方法
 * 
 * @author: heshan
 * @version 2016年4月21日 上午10:33:40
 * @see modify content------------author------------date
 */
@NoRepositoryBean
public interface BaseRepository<E extends BaseEntity<ID>, ID extends Serializable> extends JpaRepository<E, ID> {

   public Query getQuery(String ql);

   public Query getSqlQuery(String sql);

   public void clear();

   @SuppressWarnings("unchecked")
   public void delete(ID... ids) throws Exception;

   public void delete(Where where) throws Exception;

   public <C> void delete(Where where, Class<C> clazz) throws Exception;

   public <C> void deleteAll(Class<C> clazz) throws Exception;

   public void update(Set set, Where where) throws Exception;

   public E findOne(Where where) throws Exception;

   public <C> C findOne(ID id, Class<C> clazz) throws Exception;

   public <C> C findOne(Where where, Class<C> clazz) throws Exception;

   public E findOneWithQl(String ql) throws Exception;

   public <C> C findOneWithQl(String ql, Class<C> clazz) throws Exception;

   public E findOneWithSql(String sql) throws Exception;

   public <C> C findOneWithSql(String sql, Class<C> clazz) throws Exception;

   public <C> C findOneWithSql(String sql, Map<String, Object> params, Class<C> clazz);

   public List<E> findAll(Where where) throws Exception;

   public List<E> findAll(OrderBy orderBy) throws Exception;

   public List<E> findAll(Where where, OrderBy orderBy) throws Exception;

   public List<E> findAll(C3PageRequest page) throws Exception;

   public List<E> findAll(C3PageRequest page, Where where) throws Exception;

   public List<E> findAll(C3PageRequest page, OrderBy orderBy) throws Exception;

   public List<E> findAll(C3PageRequest page, Where where, OrderBy orderBy) throws Exception;

   public List<E> findAllWithQl(String ql) throws Exception;

   public <C> List<C> findAllWithQl(String ql, Class<C> clazz) throws Exception;

   public List<E> findAllWithSql(String sql) throws Exception;

   public <C> List<C> findAllWithSql(String sql, Class<C> clazz) throws Exception;

   public <C> List<C> findAllWithSql(String sql, Map<String, Object> params, Class<C> clazz) throws Exception;

}