package com.c3.base.model.entity.sm;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

//import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/**
 * The persistent class for the sm_bt_role database table.
 */
@Entity
@Table(name = "c3_sm_role")
//@NamedQuery(name = "SmBtRole.findAll", query = "SELECT s FROM SmBtRole s")
public class C3SmRole implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "role_id")
	private Integer roleId;

	@Column(name = "is_system")
	private Boolean isSystem;

	@Column(name = "description")
	private String description;

	@Column(name = "name")
	private String name;

	public C3SmRole() {
		
	}


	public Integer getRoleId() {
		return this.roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public Boolean getIsSystem() {
		return this.isSystem;
	}

	public void setIsSystem(Boolean isSystem) {
		this.isSystem = isSystem;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(roleId, isSystem, description, name);
	}

	@Override
	public boolean equals(Object object) {
		if (object instanceof C3SmRole) {
			C3SmRole that = (C3SmRole) object;
			return Objects.equal(this.roleId, that.roleId) && Objects.equal(this.isSystem, that.isSystem)
					&& Objects.equal(this.description, that.description) && Objects.equal(this.name, that.name);
		}
		return false;
	}

//	@Override
//	public String toString() {
//		return MoreObjects.toStringHelper(this).add("roleId", roleId).add("isSystem", isSystem).add("roleDescription", roleDescription)
//				.add("roleName", roleName).toString();
//	}

	@Override
	public String toString() {
		return "C3SmRole{" +
				"roleId=" + roleId +
				", isSystem=" + isSystem +
				", roleDescription='" + description + '\'' +
				", roleName='" + name + '\'' +
				'}';
	}
}