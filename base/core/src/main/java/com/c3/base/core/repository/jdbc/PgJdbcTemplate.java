package com.c3.base.core.repository.jdbc;


import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class PgJdbcTemplate extends BaseJdbcTemplate {
	
	@Autowired
	public PgJdbcTemplate(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	protected String limitSelect(String sql, int size, int offset) {
		Assert.hasText(sql, "sql must be has text");
		String nsql = StringUtils.trim(sql.toLowerCase());
		if ((size > 0) && (nsql.indexOf(" union ") == -1) && (nsql.indexOf(" limit ") == -1)) {
			if (StringUtils.endsWith(nsql, ";")) {
				nsql = nsql.substring(0, nsql.length() - 1);
			}
			String exeSql = nsql + " limit " + size + " offset " + offset;
			if (logger.isDebugEnabled()) {
				logger.debug("execute sql : {}", exeSql);
			}
			return exeSql;
		}
		return sql;
	}

}
