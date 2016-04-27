package com.c3.base.core.config;


import java.util.List;
import java.util.Map;

import javax.servlet.Servlet;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerAutoConfiguration.FreeMarkerWebConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.c3.base.core.annotation.Directive;
import com.c3.base.core.freemaker.AbstractDirective;
import com.google.common.base.CaseFormat;
import com.google.common.collect.Maps;

@Configuration
@ConditionalOnClass(Servlet.class)
@ConditionalOnWebApplication
public class FreeMarkerWebConfig extends FreeMarkerWebConfiguration {
	@Autowired
	private List<AbstractDirective> directives;

	@Bean
	@ConditionalOnMissingBean(FreeMarkerConfig.class)
	@Override
	public FreeMarkerConfigurer freeMarkerConfigurer() {
		FreeMarkerConfigurer configurer = new FreeMarkerConfigurer();
		applyProperties(configurer);
		configurer.setFreemarkerVariables(this.parseDirectiveBeanToFtlVariables());
		return configurer;
	}

	private Map<String, Object> parseDirectiveBeanToFtlVariables() {
		Map<String, Object> result = Maps.newHashMap();
		result.put("shiro", new com.c3.base.core.freemaker.tags.ShiroTags());
		for (AbstractDirective directive : this.directives) {
			Directive anno = AnnotationUtils.findAnnotation(directive.getClass(), Directive.class);
			String beanName = anno.value();
			String macroName = anno.name();
			if (StringUtils.isBlank(macroName)) {
				macroName = beanName;
			}
			if (StringUtils.isBlank(macroName)) {
				macroName = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, directive.getClass().getSimpleName());
			}
			result.put(macroName, directive);
		}
		return result;
	}

}
