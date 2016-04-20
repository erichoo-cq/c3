package com.c3.base.model.entity.sm;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the sm_bt_role_user database table.
 * 
 */
@Entity
@Table(name="c3_sm_role_user")
//@NamedQuery(name="SmBtRoleUser.findAll", query="SELECT s FROM SmBtRoleUser s")
public class C3SmRoleUser implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private C3SmRoleUserPK id;

	//bi-directional many-to-one association to SmBtUser
	@ManyToOne
	@JoinColumn(name="user_id", insertable=false, updatable=false)
	private C3SmUser smBtUser;
	
	@ManyToOne
	@JoinColumn(name = "role_id", insertable = false, updatable = false)
	private C3SmRole c3SmRole;

	public C3SmRoleUser() {
	}

	public C3SmRoleUserPK getId() {
		return this.id;
	}

	public void setId(C3SmRoleUserPK id) {
		this.id = id;
	}

	public C3SmUser getSmBtUser() {
		return smBtUser;
	}

	public void setSmBtUser(C3SmUser smBtUser) {
		this.smBtUser = smBtUser;
	}

	public C3SmRole getC3SmRole() {
		return c3SmRole;
	}

	public void setC3SmRole(C3SmRole c3SmRole) {
		this.c3SmRole = c3SmRole;
	}

}