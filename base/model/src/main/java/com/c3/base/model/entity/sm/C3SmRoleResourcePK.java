package com.c3.base.model.entity.sm;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the sm_bt_role_resource database table.
 * 
 */
@Embeddable
public class C3SmRoleResourcePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="resource_id")
	private Integer resourceId;

	@Column(name="role_id")
	private Integer roleId;

	public C3SmRoleResourcePK() {
	}
	public Integer getResourceId() {
		return this.resourceId;
	}
	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
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
		if (!(other instanceof C3SmRoleResourcePK)) {
			return false;
		}
		C3SmRoleResourcePK castOther = (C3SmRoleResourcePK)other;
		return 
			this.resourceId.equals(castOther.resourceId)
			&& this.roleId.equals(castOther.roleId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.resourceId.hashCode();
		hash = hash * prime + this.roleId.hashCode();
		
		return hash;
	}
}