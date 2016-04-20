package com.c3.base.core.util;

import java.util.List;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.Assert;

public class SqlUtil {
	
	private static final Pattern pattern = Pattern.compile("order\\s*by[\\w|\\W|\\s|\\S]*", 2);

	
	public static String parseLikeString(String str) {
	    if (str == null) {
	      return null;
	    }
	    return str.replaceAll("([\\\\])", "$1$1").replaceAll("([_%＿％])", "\\\\$1");
	}
	
	public static <T> Page<T> build(List<T> content, Pageable pageable){
		Assert.notNull(content, "List content does not null.");
		int count = content.size();
		List<T> results = content.subList(pageable.getOffset(), Math.min(pageable.getOffset() + pageable.getPageSize(), count));
		return new PageImpl<>(results, pageable, count);
	}
	
	
	public static String removeSelect(String sql) {
		Assert.hasText(sql);
		int beginPos = sql.toLowerCase().indexOf(" from ");
		Assert.isTrue(beginPos != -1, "sql:" + sql + " must has a keyword 'from'");
		return sql.substring(beginPos);
	}

	public static String removeOrders(String sql) {
		Assert.hasText(sql);
		Matcher m = pattern.matcher(sql);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			m.appendReplacement(sb, "");
		}
		m.appendTail(sb);
		return sb.toString();
	}


}
