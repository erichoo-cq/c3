package com.c3.base.menu.auth.service;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.c3.base.model.repository.sm.SmResourceMenuRepository;
import com.c3.base.core.page.Condition;
import com.c3.base.model.entity.sm.C3SmResourceMenu;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@Service
@Transactional
public class ResourceService {
	@Autowired
	private SmResourceMenuRepository  smBtSystemMenuRepository;
	
	/**
	 * 获取菜单并定位
	 * @param parentId
	 * @param currentUrl
	 * @return
	 */
	public List<Map<String, Object>> getNavigationMenu(Integer parentId, String currentUrl) {
		Assert.hasText(currentUrl, "current url must not empty.");
		List<Integer> activeIds = this.positionMenu(currentUrl);
		return this.getNavigationMenu(parentId, activeIds);
	}

	/**
	 * 递归查找菜单
	 * 
	 * @param parentId
	 * @param currentUrl
	 * @return
	 */
	public List<Map<String, Object>> getNavigationMenu(Integer parentId, List<Integer> activeIds) {
		Assert.notNull(parentId, "Parent Id must not null.");
		Condition<C3SmResourceMenu> condition = Condition.<C3SmResourceMenu> build();
//		Condition con = new Condition();
//		QueryConditions con  = new QueryConditions();
//		con.setConditionEqual("parentResourceId", parentId);
		condition.put("parentResourceId", parentId);
		condition.put("isDeleted", false);
		List<C3SmResourceMenu> menus = smBtSystemMenuRepository.findAll(condition, new Sort("rank"));
		List<Map<String, Object>> results = Lists.newArrayList();
		for (C3SmResourceMenu menu : menus) {
//			Integer menuId = menu.getId();
			Integer menuId = menu.getResourceId();
			boolean active = activeIds.contains(menuId);
			Map<String, Object> menuMap = this.parseMenuToMap(menu);
			menuMap.put("active", active);
			menuMap.put("children", this.getNavigationMenu(menuId, activeIds));
			results.add(menuMap);
		}
		return results;
	}

	private Map<String, Object> parseMenuToMap(C3SmResourceMenu menu) {
		Map<String, Object> result = Maps.newHashMap();
		String url = menu.getActionUrl();
		result.put("name", menu.getMenuName());
		result.put("url", url);
		return result;
	}
	
	private List<Integer> positionMenu(String currentUrl){
		Condition<C3SmResourceMenu> condition = Condition.<C3SmResourceMenu> build();
		condition.put("actionUrl", currentUrl);
		List<C3SmResourceMenu> menus = smBtSystemMenuRepository.findAll(condition);
		List<Integer> ids = Lists.newArrayList();
		for(C3SmResourceMenu menu : menus){
			ids.add(menu.getResourceId());
			ids.add(menu.getParentResourceId());
		}
		return ids;
	}

	/**
	 * 获取菜单
	 * 
	 * @param id
	 * @return
	 */
	public C3SmResourceMenu getMenu(Integer id) {
		return smBtSystemMenuRepository.findOne(id);
	}

	/**
	 * 创建菜单
	 * 
	 * @param menu
	 * @return
	 */
	public C3SmResourceMenu createMenu(C3SmResourceMenu menu) {
		return smBtSystemMenuRepository.save(menu);
	}

	/**
	 * 更新菜单
	 * 
	 * @param menu
	 * @return
	 */
	public C3SmResourceMenu updateMenu(C3SmResourceMenu menu) {
		C3SmResourceMenu daoMenu = smBtSystemMenuRepository.findOne(menu.getResourceId());
		if (null != daoMenu) {
			daoMenu.setActionUrl(menu.getActionUrl());
			daoMenu.setMenuName(menu.getMenuName());
			daoMenu.setMenuRemarks(menu.getMenuRemarks());
			daoMenu.setMenuType(menu.getMenuType());
			daoMenu.setIsDeleted(menu.getIsDeleted());
			daoMenu.setRank(menu.getRank());
		}
		
		return daoMenu;
		
	}

	/**
	 * 删除菜单
	 * 
	 * @param menuId
	 */
	public void deleteMenu(Integer menuId) {
		smBtSystemMenuRepository.delete(menuId);
	}

	/**
	 * 根据父级菜单查找
	 * 
	 * @param parentId
	 * @return
	 */
	public List<C3SmResourceMenu> findMenus(Integer parentId) {
		if (parentId == null) {
			parentId = C3SmResourceMenu.MENU_ROOT;
		}
		Condition<C3SmResourceMenu> condition = Condition.<C3SmResourceMenu> build();
		condition.put("parentResourceId", parentId);
		return smBtSystemMenuRepository.findAll(condition, new Sort("rank"));
	}

	/**
	 * 根据父级分页查找
	 * 
	 * @param parentId
	 * @param pageable
	 * @return
	 */
	public Page<C3SmResourceMenu> pageMenus(Integer parentId, Pageable pageable) {
		if (parentId == null) {
			parentId = C3SmResourceMenu.MENU_ROOT;
		}
		Condition<C3SmResourceMenu> condition = Condition.<C3SmResourceMenu> build();
		condition.put("parentResourceId", parentId);
		return smBtSystemMenuRepository.findAll(condition, pageable);
	}

}
