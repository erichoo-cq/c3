package com.c3.base.file.domain;


import java.io.Serializable;

import org.springframework.http.MediaType;


public class DownloadDocument implements Serializable {

	private static final long serialVersionUID = 1176924216207715508L;
	private static final String DEFAULT_CONTENT_DISPOSITION = "attachment";
	// 相对路径
	private String relativePath;
	private String name;
	private String extension;
	private MediaType mediaType;
	// attachment inline
	private String contentDisposition = DEFAULT_CONTENT_DISPOSITION;

	public String getFileName() {
		return this.name + "." + this.extension;
	}

	public String getRelativePath() {
		return relativePath;
	}

	public void setRelativePath(String relativePath) {
		this.relativePath = relativePath;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public MediaType getMediaType() {
		return mediaType;
	}
	
	public void setMediaType(String mediaType) {
		this.mediaType = MediaType.valueOf(mediaType);
	}

	public void setMediaType(MediaType mediaType) {
		this.mediaType = mediaType;
	}

	public String getContentDisposition() {
		return contentDisposition;
	}

	public void setContentDisposition(String contentDisposition) {
		this.contentDisposition = contentDisposition;
	}

//	@Override
//	public String toString() {
//		return MoreObjects.toStringHelper(this).add("relativePath", relativePath).add("name", name).add("extension", extension)
//				.add("mediaType", mediaType).add("contentDisposition", contentDisposition).toString();
//	}

	@Override
	public String toString() {
		return "DownloadDocument{" +
				"relativePath='" + relativePath + '\'' +
				", name='" + name + '\'' +
				", extension='" + extension + '\'' +
				", mediaType=" + mediaType +
				", contentDisposition='" + contentDisposition + '\'' +
				'}';
	}
}
