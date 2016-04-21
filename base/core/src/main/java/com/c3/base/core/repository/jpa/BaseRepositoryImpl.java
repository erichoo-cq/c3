package com.c3.base.core.repository.jpa;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.c3.base.cache.annotation.EnableQueryCache;
import com.c3.base.core.repository.jpa.entity.BaseEntity;
import com.c3.base.core.repository.jpa.entity.C3PageRequest;
import com.c3.base.core.repository.jpa.qlhelper.OrderBy;
import com.c3.base.core.repository.jpa.qlhelper.ParamHelper;
import com.c3.base.core.repository.jpa.qlhelper.QlHelper;
import com.c3.base.core.repository.jpa.qlhelper.Set;
import com.c3.base.core.repository.jpa.qlhelper.Where;

/**
 * 
 * description:BaseRepository实现
 * 
 * @author: heshan
 * @version 2016年4月21日 上午10:34:39
 * @see BaseRepository
 */
@NoRepositoryBean
public class BaseRepositoryImpl<E extends BaseEntity<ID>, ID extends Serializable> extends SimpleJpaRepository<E, ID>
      implements BaseRepository<E, ID> {

   private final EntityManager entityManager;

   private Class<E> entityClass;
   private Boolean enableQueryCache = false;

   public BaseRepositoryImpl(JpaEntityInformation<E, ID> entityInformation, EntityManager entityManager) {
      super(entityInformation, entityManager);
      this.entityManager = entityManager;
      this.entityClass = entityInformation.getJavaType();
      EnableQueryCache enableQueryCacheAnnotation = AnnotationUtils.findAnnotation(entityClass, EnableQueryCache.class);
      if (enableQueryCacheAnnotation != null) {
         this.enableQueryCache = enableQueryCacheAnnotation.value();
      }
   }

   public BaseRepositoryImpl(Class<E> entityClass, EntityManager entityManager) {
      super(entityClass, entityManager);
      this.entityManager = entityManager;
      this.entityClass = entityClass;
      EnableQueryCache enableQueryCacheAnnotation = AnnotationUtils.findAnnotation(entityClass, EnableQueryCache.class);
      if (enableQueryCacheAnnotation != null) {
         this.enableQueryCache = enableQueryCacheAnnotation.value();
      }
   }

   public Query getQuery(String ql) {
      Query query = this.entityManager.createQuery(ql);
      query.setHint("org.hibernate.cacheable", this.enableQueryCache);
      return query;
   }

   @Override
   public Query getSqlQuery(String sql) {
      Query query = this.entityManager.createNativeQuery(sql);
      return query;
   }

   @Override
   public void clear() {
      flush();
      this.entityManager.clear();
   }

   @SuppressWarnings("unchecked")
   @Override
   public void delete(ID... ids) throws Exception {
      for (ID id : ids) {
         this.delete(id);
      }
   }

   @Override
   public void delete(Where where) throws Exception {
      Query query = getQuery("delete from " + entityClass.getName() + " o " + toSTRING(where));
      setQlParams(query, where);
      query.executeUpdate();
   }

   @Override
   public <C> void delete(Where where, Class<C> clazz) throws Exception {
      Query query = getQuery("delete from " + clazz.getName() + " o " + toSTRING(where));
      setQlParams(query, where);
      query.executeUpdate();
   }

   @Override
   public <C> void deleteAll(Class<C> clazz) throws Exception {
      Query query = getQuery("delete from " + clazz.getName());
      query.executeUpdate();
   }

   @Override
   public void update(Set set, Where where) throws Exception {
      Query query = getQuery("update " + entityClass.getName() + " o " + toSTRING(set) + toSTRING(where));
      setQlParams(query, set);
      setQlParams(query, where);
      query.executeUpdate();
   }

   @Override
   public <C> C findOne(ID id, Class<C> clazz) throws Exception {
      return entityManager.find(clazz, id);
   }

   @Override
   public E findOne(Where where) throws Exception {
      return (E) findOne(where, entityClass);
   }

   @Override
   @SuppressWarnings("unchecked")
   public <C> C findOne(Where where, Class<C> clazz) throws Exception {
      Query query = getQuery("select o from " + clazz.getName() + " o " + toSTRING(where));
      setQlParams(query, where);
      return (C) query.getSingleResult();
   }

   @Override
   public E findOneWithQl(String ql) throws Exception {
      return findOneWithQl(ql, entityClass);
   }

   @Override
   @SuppressWarnings("unchecked")
   public <C> C findOneWithQl(String ql, Class<C> clazz) throws Exception {
      return (C) getQuery(ql).getSingleResult();
   }

   @Override
   public E findOneWithSql(String sql) throws Exception {
      return findOneWithSql(sql, entityClass);
   }

   @Override
   public <C> C findOneWithSql(String sql, Class<C> clazz) throws Exception {
      return findOneWithSql(sql, null, clazz);
   }

   @Override
   @SuppressWarnings("unchecked")
   public <C> C findOneWithSql(String sql, Map<String, Object> params, Class<C> clazz) {
      Query query = getSqlQuery(sql);
      if (params != null && !params.isEmpty()) {
         for (String key : params.keySet()) {
            query.setParameter(key, params.get(key));
         }
      }
      return (C) query.getSingleResult();
   }

   @Override
   public List<E> findAll(Where where) throws Exception {
      return findAll(where, null);
   }

   @Override
   public List<E> findAll(OrderBy orderBy) throws Exception {
      return findAll(null, null, orderBy);
   }

   @Override
   public List<E> findAll(Where where, OrderBy orderBy) throws Exception {
      return findAll(null, where, orderBy);
   }

   @Override
   public List<E> findAll(C3PageRequest page) throws Exception {
      return findAll(page, null, null);
   }

   @Override
   public List<E> findAll(C3PageRequest page, Where where) throws Exception {
      return findAll(page, where, null);
   }

   @Override
   public List<E> findAll(C3PageRequest page, OrderBy orderBy) throws Exception {
      return findAll(page, null, orderBy);
   }

   @Override
   @SuppressWarnings("unchecked")
   public List<E> findAll(C3PageRequest page, Where where, OrderBy orderBy) throws Exception {
      if (page != null) {
         page.setTotalRecords(getRecordsCount(where).intValue());
      }
      Query query = getQuery("select o from " + entityClass.getName() + " o " + toSTRING(where) + toSTRING(orderBy));
      setQlParams(query, where);
      setPageParams(query, page);
      return query.getResultList();
   }

   @Override
   public List<E> findAllWithQl(String ql) throws Exception {
      return findAllWithQl(ql, entityClass);
   }

   @Override
   @SuppressWarnings("unchecked")
   public <C> List<C> findAllWithQl(String ql, Class<C> clazz) throws Exception {
      return getQuery(ql).getResultList();
   }

   @Override
   public List<E> findAllWithSql(String sql) throws Exception {
      return findAllWithSql(sql, entityClass);
   }

   @Override
   public <C> List<C> findAllWithSql(String sql, Class<C> clazz) throws Exception {
      return findAllWithSql(sql, null, clazz);
   }

   @Override
   @SuppressWarnings("unchecked")
   public <C> List<C> findAllWithSql(String sql, Map<String, Object> params, Class<C> clazz) throws Exception {
      Query query = getSqlQuery(sql);
      if (params != null && !params.isEmpty()) {
         for (String key : params.keySet()) {
            query.setParameter(key, params.get(key));
         }
      }
      return (List<C>) query.getResultList();
   }

   /**
    * 查询符合条件的记录的总条数
    */
   private Long getRecordsCount(Where where) {
      Query query = getQuery("select count(o) from " + entityClass.getName() + " o " + toSTRING(where));
      setQlParams(query, where);
      return (Long) query.getSingleResult();
   }

   private void setPageParams(Query query, C3PageRequest page) {
      if (page != null) {
         int firstIndex = page.getPageNumber() * page.getPageSize();
         int maxResult = page.getPageSize();
         query.setFirstResult(firstIndex).setMaxResults(maxResult);
      }
   }

   /**
    * 参数设置
    */
   private void setQlParams(Query query, ParamHelper condition) {
      if (condition != null) {
         for (String key : condition.getParams().keySet()) {
            query.setParameter(key, condition.getParams().get(key));
         }
      }
   }

   private static String toSTRING(QlHelper sqlHelper) {
      return sqlHelper == null ? "" : sqlHelper.toString();
   }

}