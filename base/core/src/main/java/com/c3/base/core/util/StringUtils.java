package com.c3.base.core.util;

import java.util.Map;

/**
 * description: 字符串相关操作工具类
 *
 * @version 2016年4月20日 下午4:31:18
 * @see
 * modify content------------author------------date
 */
public class StringUtils {
	
	private static final String DELIM_START = "${";
	private static final String DELIM_STOP = "}";

	public static String handelUrl(String url) {
		if (url == null) {
			return null;
		}
		url = url.trim();
		if ((url.equals("")) || (url.startsWith("http://"))
				|| (url.startsWith("https://"))) {
			return url;
		}
		return "http://" + url.trim();
	}
	
	/**
	 * 解析含${xx}变量并替换
	 * @param val
	 * @param values
	 * @return
	 */
	public static String replaceParams(String val, Map<String, String> values) {
		int stopDelim = val.indexOf(DELIM_STOP);
		int startDelim = val.indexOf(DELIM_START);
		while (stopDelim >= 0) {
			int idx = val.indexOf(DELIM_START, startDelim + DELIM_START.length());
			if ((idx < 0) || (idx > stopDelim)) {
				break;
			} else if (idx < stopDelim) {
				startDelim = idx;
			}
		}

		if ((startDelim < 0) && (stopDelim < 0)) {
			return val;
		} else if (((startDelim < 0) || (startDelim > stopDelim)) && (stopDelim >= 0)) {
			//throw new IllegalArgumentException("stop delimiter with no start delimiter: " + val);
			return val;
		}

		String variable = val.substring(startDelim + DELIM_START.length(), stopDelim);
		String substValue = values.get(variable);
		if(substValue == null){
			return val;
		}
		
		val = val.substring(0, startDelim) + substValue + val.substring(stopDelim + DELIM_STOP.length(), val.length());
		val = replaceParams(val, values);
		return val;
	}

}
