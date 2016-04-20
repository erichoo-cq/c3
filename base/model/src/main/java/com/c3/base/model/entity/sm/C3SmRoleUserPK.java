package com.c3.base.model.entity.sm;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the sm_bt_role_user database table.
 */
@Embeddable
public class C3SmRoleUserPK implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name = "user_id", insertable = false, updatable = false)
	private Integer userId;

	@Column(name = "role_id")
	private Integer roleId;

	public C3SmRoleUserPK() {
	}

	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getRoleId() {
		return this.roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof C3SmRoleUserPK)) {
			return false;
		}
		C3SmRoleUserPK castOther = (C3SmRoleUserPK) other;
		return this.userId.equals(castOther.userId) && this.roleId.equals(castOther.roleId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.userId.hashCode();
		hash = hash * prime + this.roleId.hashCode();

		return hash;
	}
}