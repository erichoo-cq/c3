package com.c3.base.menu.auth.user;


public enum UserType {
	DC(1), // 中心用户
	RE(2), // 区县用户
	CO(3);// 企业用户

	private int type;

	private UserType(int type) {
		this.type = type;
	}

	public int getType() {
		return this.type;
	}

	public static UserType formName(String name) {
		return UserType.valueOf(name);
	}

	public static UserType formValue(int type) {
		for (UserType userType : UserType.values()) {
			if (userType.getType() == type) {
				return userType;
			}
		}
		return null;
	}

	@Override
	public String toString() {
		return this.name() + "_" + this.type;
	}
}
