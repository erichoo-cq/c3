package com.c3.base.model.entity.sm;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.data.domain.Persistable;

/**
 * The persistent class for the sm_bt_system_menu_button database table.
 */
@Entity
@Table(name = "c3_sm_resource_button")
//@NamedQuery(name = "SmBtSystemMenuButton.findAll", query = "SELECT s FROM SmBtSystemMenuButton s")
public class C3SmResourceButton implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
//	@SequenceGenerator(name = "SM_BT_SYSTEM_MENU_BUTTON_RESOURCEID_GENERATOR", sequenceName = "SM_SEQ_RESOURCE_ID")
//	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SM_BT_SYSTEM_MENU_BUTTON_RESOURCEID_GENERATOR")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "resource_id")
	private Integer resourceId;

	@Column(name = "action_url")
	private String actionUrl;

	@Column(name = "button_name")
	private String buttonName;

	@Column(name = "button_remarks")
	private String buttonRemarks;

	@Column(name = "button_type")
	private String buttonType;

	@Column(name = "is_deleted")
	private Boolean isDeleted;

	private Integer rank;

	// bi-directional many-to-one association to SmBtSystemMenu
	@ManyToOne
	@JoinColumn(name = "menu_id")
	private C3SmResourceMenu smBtSystemMenu;

	public C3SmResourceButton() {
	}

	public Integer getResourceId() {
		return this.resourceId;
	}

	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}

	public String getActionUrl() {
		return this.actionUrl;
	}

	public void setActionUrl(String actionUrl) {
		this.actionUrl = actionUrl;
	}

	public String getButtonName() {
		return this.buttonName;
	}

	public void setButtonName(String buttonName) {
		this.buttonName = buttonName;
	}

	public String getButtonRemarks() {
		return this.buttonRemarks;
	}

	public void setButtonRemarks(String buttonRemarks) {
		this.buttonRemarks = buttonRemarks;
	}

	public String getButtonType() {
		return this.buttonType;
	}

	public void setButtonType(String buttonType) {
		this.buttonType = buttonType;
	}

	public Boolean getIsDeleted() {
		return this.isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Integer getRank() {
		return this.rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	public C3SmResourceMenu getSmBtSystemMenu() {
		return this.smBtSystemMenu;
	}

	public void setSmBtSystemMenu(C3SmResourceMenu smBtSystemMenu) {
		this.smBtSystemMenu = smBtSystemMenu;
	}

}