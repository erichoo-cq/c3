package com.c3.base.file.config;

public enum MimeTypeEnum {
	
	PNG("png", "image/png"),
	DOC("doc", "application/msword"),
	JPG("jpg", "image/jpeg");
	

	private String ext;
	
	private String type;
	
	private MimeTypeEnum(String ext, String type) {
		this.ext = ext;
		this.type = type;
	}
	
	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	
	public  static String getTypeByExt(String ext) {
		String type  = "";
		
		for(MimeTypeEnum e : MimeTypeEnum.values()) {
			if (e.getExt().equalsIgnoreCase(ext)) {
				type = e.getType();
				break;
			}
		}
		
		return type;
	}
	
	
}
