package com.c3.base.menu.auth.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.c3.base.menu.auth.service.ResourceService;
import com.c3.base.model.entity.sm.C3SmResourceMenu;

/**
 * description: 资源管理控制器
 *
 * @version 2016年4月7日 下午2:55:38
 * @see
 * modify content------------author------------date
 */
@Controller
@RequestMapping("sm/resource")
public class ResourceController {
	
	private  final  Logger  log= LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ResourceService resourceService;

	/**
	 * description: 
	 * @return 
	 * @author:dwx
	 * @time: 2016年4月7日 下午3:25:23
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String index() {
		return "sm/resource/index";
	}

	/**
	 * 菜单树
	 * 
	 * @param parentId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "menus", method = RequestMethod.GET)
	public List<C3SmResourceMenu> menus(@RequestParam(name = "id", required = false) Integer parentId) {
		return resourceService.findMenus(parentId);
	}

	/**
	 * 菜单列表
	 * 
	 * @param parentId
	 * @return
	 */
	@RequestMapping(value = "menu/page", method = RequestMethod.GET)
	public String pageMenu(@RequestParam(name = "id") Integer parentId, @PageableDefault(sort = "rank") Pageable pageable, Model model) {
		Page<C3SmResourceMenu> page = resourceService.pageMenus(parentId, pageable);
		model.addAttribute("page", page);
		return "sm/resource/page";
	}

	/**
	 * 新建菜单
	 * 
	 * @param menu
	 * @param parentId
	 * @return
	 */
	@RequestMapping(value = "menu/new", method = RequestMethod.GET)
	public String _new(@ModelAttribute("menu") C3SmResourceMenu menu, @RequestParam(name = "id") Integer parentId) {
		menu.setParentResourceId(parentId);
		return "sm/resource/new";
	}

	/**
	 * 创建菜单
	 * 
	 * @param menu
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "menu", method = RequestMethod.POST)
	public String create(@ModelAttribute("menu") C3SmResourceMenu menu, @Valid BindingResult result) {
		if (result.hasErrors()) {
			log.error("Valid Has Error, {}", result.getAllErrors());
			return "sm/resource/new";
		}
		resourceService.createMenu(menu);
		return "sm/resource/create";
	}

	/**
	 * 编辑菜单
	 * 
	 * @param menu
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "menu/{id}/edit", method = RequestMethod.GET)
	public String edit(@PathVariable Integer id, Model model) {
		C3SmResourceMenu menu = resourceService.getMenu(id);
		model.addAttribute("menu", menu);
		return "sm/resource/edit";
	}

	/**
	 * 更新菜单
	 * 
	 * @param menu
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "menu", method = RequestMethod.PUT)
	public String update(@ModelAttribute("menu") C3SmResourceMenu menu, @Valid BindingResult result) {
		if (result.hasErrors()) {
			log.error("Valid Has Error, {}", result.getAllErrors());
			return "sm/resource/new";
		}
		resourceService.updateMenu(menu);
		return "sm/resource/create";
		
	}

}
