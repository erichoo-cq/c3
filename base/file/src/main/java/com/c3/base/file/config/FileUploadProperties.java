package com.c3.base.file.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.google.common.base.MoreObjects;

/**
 * description: 配置文件上传的路径，前缀为fileUpload
 * 可以配置的属性为rootPath，以及相对路径relativePath
 *
 * @version 2016年4月6日 下午4:58:21
 * @see
 * modify content------------author------------date
 */
@Configuration
@ConfigurationProperties(prefix = FileUploadProperties.PREFIX)
public class FileUploadProperties {

	public static final String PREFIX = "fileUpload";
	
	private String rootPath = "/tmp";
	
	private String relativePath;

	public String getRootPath() {
		return rootPath;
	}

	public void setRootPath(String rootPath) {
		this.rootPath = rootPath;
	}

	public String getRelativePath() {
		return relativePath;
	}

	public void setRelativePath(String relativePath) {
		this.relativePath = relativePath;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).add("rootPath", rootPath).add("relativePath", relativePath).toString();
	}

}
