package com.c3.base.model.entity.sm;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the sm_bt_person database table.
 * 
 */
@Entity
@Table(name="c3_sm_person")
//@NamedQuery(name="SmBtPerson.findAll", query="SELECT s FROM SmBtPerson s")
public class C3SmPerson implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
//	@SequenceGenerator(name = "SM_BT_PERSON_PERSONID_GENERATOR", sequenceName = "SM_SEQ_PERSON_ID")
//	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SM_BT_PERSON_PERSONID_GENERATOR")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="person_id")
	private Integer personId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_date")
	private Date createdDate;

	@Column(name="dept_id")
	private Integer deptId;

	@Column(name="dept_name")
	private String deptName;

	@Column(name="full_name")
	private String fullName;

	@Column(name = "gender")
	private String gender;

	@Column(name="image_id")
	private Long imageId;

	@Column(name = "phone")
	private String phone;

	@Column(name = "remarks")
	private String remarks;

	@Column(name = "state")
	private Integer state;
	
	//一个部门有多个person
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "dept_id", insertable = false, updatable = false)
	private C3SmDepartment department;

	//bi-directional many-to-one association to SmBtUser
	//一个person有几个系统用户
	@OneToMany(mappedBy="person")
	private Set<C3SmUser> users;

	public C3SmPerson() {
	}

	public Integer getPersonId() {
		return this.personId;
	}

	public void setPersonId(Integer personId) {
		this.personId = personId;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Integer getDeptId() {
		return this.deptId;
	}

	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}

	public String getDeptName() {
		return this.deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getFullName() {
		return this.fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Long getImageId() {
		return this.imageId;
	}

	public void setImageId(Long imageId) {
		this.imageId = imageId;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Set<C3SmUser> getUsers() {
		return this.users;
	}

	public void setSmBtUsers(Set<C3SmUser> users) {
		this.users = users;
	}

	public C3SmUser addSmBtUser(C3SmUser user) {
		getUsers().add(user);
		user.setPerson(this);

		return user;
	}

	public C3SmUser removeSmBtUser(C3SmUser user) {
		getUsers().remove(user);
		user.setPerson(null);

		return user;
	}
	
	public C3SmDepartment getDepartment() {
		return this.department;
	}

	public void setDepartment(C3SmDepartment department) {
		this.department = department;
	}


}