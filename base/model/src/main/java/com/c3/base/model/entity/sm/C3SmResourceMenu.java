package com.c3.base.model.entity.sm;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.domain.Persistable;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The persistent class for the sm_bt_system_menu database table.
 */
@Entity
@Table(name = "c3_sm_resource_menu")
//@NamedQuery(name = "SmBtSystemMenu.findAll", query = "SELECT s FROM SmBtSystemMenu s")
public class C3SmResourceMenu implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static final Integer MENU_ROOT = -9999;//根菜单

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "resource_id")
	private Integer resourceId;

	@NotBlank
	@Column(name = "action_url")
	private String actionUrl;

	@Column(name = "icon_id")
	private Long iconId;

	@NotNull
	@Column(name = "is_deleted")
	private Boolean isDeleted;

	@NotBlank
	@Column(name = "menu_name")
	private String menuName;

	@Column(name = "menu_remarks")
	private String menuRemarks;

	@Column(name = "menu_type")
	private String menuType;

	private Integer rank;

	@NotNull
	@Column(name = "parent_resource_id")
	private Integer parentResourceId;

	// bi-directional many-to-one association to SmBtSystemMenu
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "parent_resource_id", insertable = false, updatable = false)
	private C3SmResourceMenu parentMenu;

	// bi-directional many-to-one association to SmBtSystemMenu
	@JsonIgnore
	@OneToMany(mappedBy = "parentMenu")
	private Set<C3SmResourceMenu> children;

	// bi-directional many-to-one association to SmBtSystemMenuButton
	@JsonIgnore
	@OneToMany(mappedBy = "smBtSystemMenu")
	private Set<C3SmResourceButton> buttons;

	public C3SmResourceMenu() {
	}
	
	//for tree
	public String getName() {
		return this.menuName;
	}
	
	//for tree
	public String getType(){
		if("1".equals(this.menuType)){
			return "folder";
		}
		return "item";
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

	public Long getIconId() {
		return this.iconId;
	}

	public void setIconId(Long iconId) {
		this.iconId = iconId;
	}

	public Boolean getIsDeleted() {
		return this.isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getMenuName() {
		return this.menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getMenuRemarks() {
		return this.menuRemarks;
	}

	public void setMenuRemarks(String menuRemarks) {
		this.menuRemarks = menuRemarks;
	}

	public String getMenuType() {
		return this.menuType;
	}

	public void setMenuType(String menuType) {
		this.menuType = menuType;
	}

	public Integer getRank() {
		return this.rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	public Integer getParentResourceId() {
		return parentResourceId;
	}

	public void setParentResourceId(Integer parentResourceId) {
		this.parentResourceId = parentResourceId;
	}

	public C3SmResourceMenu getParentMenu() {
		return this.parentMenu;
	}

	public void setParentMenu(C3SmResourceMenu parentMenu) {
		this.parentMenu = parentMenu;
	}

	public Set<C3SmResourceMenu> getChildren() {
		return this.children;
	}

	public void setChildren(Set<C3SmResourceMenu> children) {
		this.children = children;
	}

	public C3SmResourceMenu addSmBtSystemMenus(C3SmResourceMenu smBtSystemMenus) {
		getChildren().add(smBtSystemMenus);
		smBtSystemMenus.setParentMenu(this);

		return smBtSystemMenus;
	}

	public C3SmResourceMenu removeSmBtSystemMenus(C3SmResourceMenu smBtSystemMenus) {
		getChildren().remove(smBtSystemMenus);
		smBtSystemMenus.setParentMenu(null);

		return smBtSystemMenus;
	}

	public Set<C3SmResourceButton> getButtons() {
		return this.buttons;
	}

	public void setButtons(Set<C3SmResourceButton> buttons) {
		this.buttons = buttons;
	}

	public C3SmResourceButton addSmBtSystemMenuButton(C3SmResourceButton button) {
		getButtons().add(button);
		button.setSmBtSystemMenu(this);

		return button;
	}

	public C3SmResourceButton removeSmBtSystemMenuButton(C3SmResourceButton button) {
		getButtons().remove(button);
		button.setSmBtSystemMenu(null);

		return button;
	}

}