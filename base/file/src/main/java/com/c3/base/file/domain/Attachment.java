package com.c3.base.file.domain;


import java.io.File;
import java.io.Serializable;

import com.google.common.base.MoreObjects;

public class Attachment implements Serializable {

	private static final long serialVersionUID = -9167935827783052785L;
	private File file;// 文件对象
	private String name;// 文件名
	private Long size;// 文件大小
	private String contentType;// 文件类型
	private String extension;// 文件后缀
	private String relativePath;// 相对路径
	private String absolutePath;// 绝对路径

	public Attachment() {

	}

	public Attachment(File file, String name, Long size, String contentType, String extension, String relativePath,
			String absolutePath) {
		this.file = file;
		this.name = name;
		this.size = size;
		this.contentType = contentType;
		this.extension = extension;
		this.relativePath = relativePath;
		this.absolutePath = absolutePath;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public String getRelativePath() {
		return relativePath;
	}

	public void setRelativePath(String relativePath) {
		this.relativePath = relativePath;
	}

	public String getAbsolutePath() {
		return absolutePath;
	}

	public void setAbsolutePath(String absolutePath) {
		this.absolutePath = absolutePath;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).add("file", file).add("name", name).add("size", size)
				.add("contentType", contentType).add("extension", extension).add("relativePath", relativePath)
				.add("absolutePath", absolutePath).toString();
	}

}
