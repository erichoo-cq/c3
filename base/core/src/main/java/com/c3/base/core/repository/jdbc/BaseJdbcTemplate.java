package com.c3.base.core.repository.jdbc;

import java.util.List;
import java.util.Map;

import com.c3.base.core.util.SqlUtil;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.Assert;

import com.google.common.collect.Lists;

public abstract class BaseJdbcTemplate extends JdbcTemplate {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public BaseJdbcTemplate(DataSource dataSource){
		super(dataSource);
	}
	
	public BaseJdbcTemplate(DataSource dataSource, boolean lazyInit){
		super(dataSource, lazyInit);
	}

	public Page<Map<String, Object>> pagedQuery(String sql, int page, int size) {
		return this.pagedQuery(sql, page, size, new Object[0]);
	}

	public Page<Map<String, Object>> pagedQuery(String sql, int page, int size, Object... args) {

		Assert.notNull(sql, "SQL must not be null");

		int total = this.getCount("select * from (" + sql + ") t",args);

		Pageable pageable = new PageRequest(page, size);
		if (total < 1) {
			List<Map<String, Object>> emptyList = Lists.newArrayList();
			return new PageImpl<Map<String, Object>>(emptyList);
		}

		String limitSql = pageable.getPageSize()>0?sql + " limit " + pageable.getPageSize() + " offset " + pageable.getOffset():sql;
		List<Map<String, Object>> results = queryForList(limitSql, args);
		if (logger.isDebugEnabled()) {
			logger.debug("limit sql : {}", limitSql);
		}
		return new PageImpl<Map<String, Object>>(results, pageable, total);

	}

	public <T> Page<T> pagedQuery(String sql, int page, int size, Class<T> requiredType) {
		return this.pagedQuery(sql, page, size, requiredType, new Object[0]);
	}

	public <T> Page<T> pagedQuery(String sql, int page, int size, Class<T> requiredType, Object... args) {
		Assert.notNull(sql, "SQL must not be null");

		int total = this.getCount(sql);

		Pageable pageable = new PageRequest(page, size);
		if (total < 1) {
			List<T> emptyList = Lists.newArrayList();
			return new PageImpl<T>(emptyList);
		}

		String limitSql = pageable.getPageSize()>0?sql + " limit " + pageable.getPageSize() + " offset " + pageable.getOffset():sql;
		List<T> results = queryForList(limitSql, requiredType, args);
		if (logger.isDebugEnabled()) {
			logger.debug("limit sql : {}", limitSql);
		}
		return new PageImpl<T>(results, pageable, total);
	}

	private int getCount(String sql,Object... args) {
		String countSqlString = "select count(*) " + SqlUtil.removeSelect(sql);
		if (logger.isDebugEnabled()) {
			logger.debug("count sql : {}", countSqlString);
		}
		return queryForObject(countSqlString, args, Integer.class);
	}

	protected abstract String limitSelect(String sql, int size, int offset);

}
