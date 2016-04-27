/**
 * Created on 2016年1月26日
 *
 * Copyright(c) Chongqing Communication Industry Services Co.LTD, 2016.  All rights reserved. 
 */
package com.c3.base.menu.auth.directive;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.support.RequestContext;

import com.c3.base.core.freemaker.AbstractDirective;
import com.c3.base.core.freemaker.DirectiveUtils;
import com.c3.base.menu.auth.service.ResourceService;
import com.google.common.collect.Maps;

import freemarker.core.Environment;
import freemarker.template.ObjectWrapper;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateModel;

@com.c3.base.core.annotation.Directive(name="navmenu")
public class NavMenuDirective extends AbstractDirective {

	@Autowired
	private ResourceService resourceService;

	@SuppressWarnings({ "rawtypes", "unchecked", "deprecation" })
	@Override
	public void exec(Environment env, Map params, TemplateModel[] arg2, TemplateDirectiveBody body) throws Exception {
		RequestContext context = DirectiveUtils.getContext(env);
		Map paramWrap = Maps.newHashMap(params);
		
		String currentUrl = context.getRequestUri();
		List<Map<String, Object>> menus = resourceService.getNavigationMenu(1, currentUrl);
		paramWrap.put(OUT_LIST, ObjectWrapper.DEFAULT_WRAPPER.wrap(menus));
		Map origMap = DirectiveUtils.addParamsToVariable(env, paramWrap);
		body.render(env.getOut());
		DirectiveUtils.removeParamsFromVariable(env, paramWrap, origMap);
	}
	
}
