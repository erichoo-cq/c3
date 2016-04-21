package com.c3.base.menu.auth.controller;

//import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.c3.base.menu.auth.service.RoleService;
import com.c3.base.model.entity.sm.C3SmRole;


@Controller
@RequestMapping(value = "sm/role")
public class RoleController {
	
	@Autowired
	private RoleService roleService;

	/**
	 * 角色信息
	 * 
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String index() {
		return "sm/role/index";
	}

	/**
	 * 角色列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "list", method = RequestMethod.POST)
	public ModelAndView list(@RequestParam Map<String, String> params, @PageableDefault(size = 15) Pageable pageable) {
		Page<C3SmRole> rolePage = roleService.pageRoles(params, pageable);
		return new ModelAndView("sm/role/list", "rolePage", rolePage);
	}

	/**
	 * 新建
	 */
	@RequestMapping(value = "new", method = RequestMethod.GET)
	public String _new(@ModelAttribute("role") C3SmRole role) {
		return "sm/role/new";
	}

	/**
	 * 保存
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST)
	public void save(C3SmRole smBtRole) {
		roleService.create(smBtRole);
	}
	
	/**
	 * 编辑角色
	 * @param role
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "edit/{id}", method = RequestMethod.GET)
	public String edit(@PathVariable("id") Integer id, Model model) {
		C3SmRole role = roleService.getRole(id);
		model.addAttribute("role", role);
		return "sm/role/edit";
	}

	/**
	 * 保存修改
	 */
	@RequestMapping(value = "edit", method = RequestMethod.POST)
	@ResponseBody
	public void update(C3SmRole c3SmRole) {
		roleService.update(c3SmRole);
	}

	/**
	 * 角色对应资源关系
	 * @param roleId
	 * @return
	 */
	@RequestMapping(value = "getMenu/{roleId}", method = RequestMethod.GET)
	public String getAllMenu(@PathVariable Integer roleId,Model model){
		model.addAttribute("menus",roleService.getAllMenu(roleId));
		model.addAttribute("roleId", roleId);
		return "sm/role/menu";
	}
	
	/**
	 * 保存角色与资源关联关系
	 */
	@RequestMapping(value = "saveResource", method = RequestMethod.POST)
	@ResponseBody
	public void saveResource(@RequestParam Map<String, String> params){
		Integer roleId = Integer.valueOf(params.get("roleId"));
		String addMenu = params.get("addMenu");
		String delMenu = params.get("delMenu");
		roleService.saveResource(roleId,addMenu,delMenu);
	}
	
//	/**
//	 * 所有资源
//	 */
//	@RequestMapping(value = "authoritylist", method = RequestMethod.POST)
//	@ResponseBody
//	public ModelAndView authoritylist(@RequestParam Map<String, String> params, Model model) {
//		roleService.delete(params);
//		List<Map<String, Object>> authorityPage = roleService.getMenu();
//		model.addAttribute("authorityPage", authorityPage);
//		List<SmBtSystemMenu> authorityPages = roleService.getMenus();
//		model.addAttribute("authorityPages", authorityPages);
//		model.addAttribute("number", params);
//		return new ModelAndView("sm/role/update");
//	}

//	/**
//	 * 连接资源和角色
//	 */
//	@RequestMapping(value = "insert")
//	@ResponseBody
//	public void insert(@RequestParam Map<String, String> params) {
//		roleService.insert(params);
//	}

//	/**
//	 * 查看角色权限
//	 */
//	@RequestMapping(value = "look", method = RequestMethod.POST)
//	@ResponseBody
//	public ModelAndView look(@RequestParam Map<String, String> params, Model model) {
//		List<Map<String, Object>> authorityPage = roleService.getMenu();
//		model.addAttribute("authorityPage", authorityPage);
//		List<Map<String, Object>> lookList = roleService.look(params);
//		model.addAttribute("lookList", lookList);
//		int number = 0;
//		model.addAttribute("number", number);
//		return new ModelAndView("sm/role/look");
//	}
}
