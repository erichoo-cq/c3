package com.c3.base.model.entity.sm;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the sm_bt_organization database table.
 * 
 */
@Entity
@Table(name="c3_sm_organization")
//@NamedQuery(name="SmBtOrganization.findAll", query="SELECT s FROM SmBtOrganization s")
public class C3SmOrg implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="org_code")
	private String orgCode;

	@Column(name="create_time")
	private Timestamp createTime;

	@Column(name="org_name")
	private String orgName;

	@Column(name="org_type")
	private String orgType;

	private String remarks;

	public C3SmOrg() {
	}

	public String getOrgCode() {
		return this.orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getOrgName() {
		return this.orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOrgType() {
		return this.orgType;
	}

	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

}