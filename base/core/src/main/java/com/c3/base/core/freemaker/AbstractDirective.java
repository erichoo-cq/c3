package com.c3.base.core.freemaker;


import java.io.IOException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

public abstract class AbstractDirective implements TemplateDirectiveModel {

	protected final Logger log = LoggerFactory.getLogger(this.getClass());

	/**
	 * 输出参数：对象数据
	 */
	public static final String OUT_BEAN = "tag_bean";
	/**
	 * 输出参数：列表数据
	 */
	public static final String OUT_LIST = "tag_list";
	/**
	 * 输出参数：分页数据
	 */
	public static final String OUT_PAGINATION = "tag_pagination";
	
	@SuppressWarnings("rawtypes")
	public abstract void exec(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws Exception;
	
	@SuppressWarnings("rawtypes")
	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		try {
			this.exec(env, params, loopVars, body);
		} catch (Exception e) {
			log.error("Execute Directive Error.", e);
		}
	}

}
