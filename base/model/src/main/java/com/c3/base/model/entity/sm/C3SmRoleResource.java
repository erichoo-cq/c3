package com.c3.base.model.entity.sm;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the sm_bt_role_resource database table.
 * 
 */
@Entity
@Table(name="c3_sm_role_resource")
//@NamedQuery(name="SmBtRoleResource.findAll", query="SELECT s FROM SmBtRoleResource s")
public class C3SmRoleResource implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private C3SmRoleResourcePK id;

	@Column(name="resource_type")
	private Integer resourceType;

	public C3SmRoleResource() {
	}

	public C3SmRoleResourcePK getId() {
		return this.id;
	}

	public void setId(C3SmRoleResourcePK id) {
		this.id = id;
	}

	public Integer getResourceType() {
		return this.resourceType;
	}

	public void setResourceType(Integer resourceType) {
		this.resourceType = resourceType;
	}

}