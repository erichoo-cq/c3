package com.c3.base.file.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


/**
 * description: 定义ftp服务器相关信息，需要配置的为
 * host，port，user，password
 *
 * @version 2016年3月17日 上午11:18:54
 * @see
 * modify content------------author------------date
 */
@Configuration
@ConfigurationProperties(prefix = "ftp")
public class FtpProperties {
	
	private String host;
	
	private int port = 21;
	
	private String user;
	
	private String password;

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
