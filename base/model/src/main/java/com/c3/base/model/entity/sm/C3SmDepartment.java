package com.c3.base.model.entity.sm;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the sm_bt_department database table.
 * 
 */
@Entity
@Table(name="c3_sm_department")
//@NamedQuery(name="SmBtDepartment.findAll", query="SELECT s FROM SmBtDepartment s")
public class C3SmDepartment implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="dept_id")
	private Integer deptId;

	@Column(name="dept_level")
	private String deptLevel;

	@Column(name="dept_name")
	private String deptName;

	private String leader;

	@Column(name="org_code")
	private String orgCode;

	private String remarks;

	//bi-directional many-to-one association to SmBtDepartment
	@ManyToOne
	@JoinColumn(name="superior_dept_id")
	private C3SmDepartment smBtDepartment;

	//bi-directional many-to-one association to SmBtDepartment
	@OneToMany(mappedBy="smBtDepartment")
	private List<C3SmDepartment> smBtDepartments;

	public C3SmDepartment() {
	}

	public Integer getDeptId() {
		return this.deptId;
	}

	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}

	public String getDeptLevel() {
		return this.deptLevel;
	}

	public void setDeptLevel(String deptLevel) {
		this.deptLevel = deptLevel;
	}

	public String getDeptName() {
		return this.deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getLeader() {
		return this.leader;
	}

	public void setLeader(String leader) {
		this.leader = leader;
	}

	public String getOrgCode() {
		return this.orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public C3SmDepartment getSmBtDepartment() {
		return this.smBtDepartment;
	}

	public void setSmBtDepartment(C3SmDepartment smBtDepartment) {
		this.smBtDepartment = smBtDepartment;
	}

	public List<C3SmDepartment> getSmBtDepartments() {
		return this.smBtDepartments;
	}

	public void setSmBtDepartments(List<C3SmDepartment> smBtDepartments) {
		this.smBtDepartments = smBtDepartments;
	}

	public C3SmDepartment addSmBtDepartment(C3SmDepartment smBtDepartment) {
		getSmBtDepartments().add(smBtDepartment);
		smBtDepartment.setSmBtDepartment(this);

		return smBtDepartment;
	}

	public C3SmDepartment removeSmBtDepartment(C3SmDepartment smBtDepartment) {
		getSmBtDepartments().remove(smBtDepartment);
		smBtDepartment.setSmBtDepartment(null);

		return smBtDepartment;
	}

}