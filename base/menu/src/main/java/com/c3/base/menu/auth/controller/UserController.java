package com.c3.base.menu.auth.controller;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.c3.base.menu.auth.service.SmUserService;
import com.c3.base.model.entity.sm.C3SmRole;
import com.c3.base.model.entity.sm.C3SmUser;
//import com.ccscq.emis.dcs.auth.User;
//import com.crunii.brdc.core.security.UserDetails;
//import com.ccscq.emis.base.model.sm.domain.C3SmUser;
//import com.ccscq.emis.ecs.service.sm.SysUserMngService;
//import com.crunii.brdc.core.web.mvc.support.AbstractController;
import com.google.common.collect.Maps;

/**
 * description: 
 *
 * @version 2016年4月7日 下午3:09:31
 * @see
 * modify content------------author------------date
 */
@Controller
@RequestMapping(value = "sm/users")
public class UserController  {
	
	@Autowired
	private SmUserService smUserService;
	
	/**
	 * 用户信息
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String index() {
		return "sm/users/index";
	}
	
	/**
	 * 用户列表
	 * @param pageNo
	 * @return
	 */
//	@RequestMapping(value = "list", method = RequestMethod.POST)
//	public ModelAndView list(@PageableDefault (size=15)Pageable pageable,@RequestParam Map<String, String> filter) {
//		Page<C3SmUser> userPage = smUserService.pageUser(pageable,filter);
//		return new ModelAndView("sm/users/list", "userPage", userPage);
//	}
	
	@RequestMapping(value = "list", method = RequestMethod.POST)
	public ModelAndView list(@PageableDefault (size=15)Pageable pageable,@RequestParam Map<String, String> filter,/*UserDetails<Integer, User> user,*/Model model) {
//		String loginCode = user.getAccount();
		Page<Map<String, Object>> userPage = smUserService.pageUser(pageable,filter);
//		model.addAttribute("loginCode", loginCode);
		return new ModelAndView("sm/users/list", "userPage", userPage);
	}
	
	/**
	 * sm/users/new
	 * 新建
	 */
	@RequestMapping(value="new", method = RequestMethod.GET)
	public String _new(Model model) {
//		List<GpBtEnterpriseBaseInfo> enterprises = sysUserMngService.getEnterpriseInfo();
//		List<GpCtDistrict> districts = sysUserMngService.getDistrictClass();
		List<Map<String,Object>> deptList = smUserService.getDepartInfo();
//		model.addAttribute("enterprises", enterprises);
//		model.addAttribute("districts", districts);
		model.addAttribute("deptList",deptList);
		return "sm/users/new";
	}
	
	/**
	 * sm/users
	 * 创建
	 * @throws ParseException 
	 */
//	@ResponseBody
//	@RequestMapping(value="create",method = RequestMethod.POST)
//	public Map<String, Object> create(@RequestParam Map<String, String> params) throws ParseException {
//		C3SmUser user=smUserService.getBtUserByEmail(params.get("email"));
//		Map<String, Object> result = Maps.newHashMap();
//		if(user == null){
//			result.put("result", true);
//			smUserService.saveUser(params);
//		}else {
//			result.put("result", false);
//		}
//		return result;
//	}
	
	
	
	@ResponseBody
	@RequestMapping(value="addNew", method = RequestMethod.POST)
	public Object create(@RequestParam Map<String, String> filter) {
		//user.setPwd("$shiro1$MD5$1$$ZwsUcorZkCrsujLiL6T2vQ==");
		return smUserService.saveUser(filter);
	}
	
	/**
	 * sm/users/{{userId}/delete
	 * 删除
	 * @param userId
	 */
	@ResponseBody
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public void delete(@RequestParam Map<String, String> params) {                 
		smUserService.deleteUser(Integer.valueOf(params.get("userId")));
	}
	
//	/**
//	 * sm/users/{id}/edit
//	 * 编辑
//	 */
//	@RequestMapping(value = "{id}/edit", method = RequestMethod.GET)
//	public String edit(@PathVariable Integer id, Model model) {
//		C3SmUser user = smUserService.getUser(id);
//		model.addAttribute("user", user);
//		return "sm/users/edit";
//	}
	
	@RequestMapping(value="{id}/edit", method = RequestMethod.GET)
	public String edit(@PathVariable Integer id, Model model) {
		Map<String,Object> user = smUserService.getPersonUser(id);
//		C3SmUser user = smUserService.getUser(id);
		List<C3SmRole> listRole = smUserService.getRolesList();
		model.addAttribute("user", user);
		model.addAttribute("userId", id);
		model.addAttribute("listRole",listRole);
		return "sm/users/edit";
	}
	
	/**
	 * sm/users?_method=PUT
	 * 更新
	 * @param user
	 */
	@ResponseBody
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public Map<String, Object> update(@RequestParam Map<String, String> params) {
		Map<String, Object> result = Maps.newHashMap();
		if (!params.get("email1").equals(params.get("email"))) {
			C3SmUser user = smUserService.getBtUserByEmail(params.get("email"));
			if(user == null){
				result.put("result", true);
				smUserService.updateUser(params);
			}else {
				result.put("result", false);
			}
		}else {
			result.put("result", true);
			smUserService.updateUser(params);
		}
		return result;
	}
	
	/**
	 * 更新用户状态
	 * @param filter
	 */
	@ResponseBody
	@RequestMapping(value = "updateState", method = RequestMethod.POST)
	public void updateTSysUserState(@RequestParam Map<String, String> filter) {
		smUserService.updateUser(filter);
	}
	
	/**
	 * 用户密码重置
	 * @param filter
	 */
	@ResponseBody
	@RequestMapping(value = "resetPwd", method = RequestMethod.POST)
	public void updateTSysUserPwd(@RequestParam Map<String, String> filter) {
		smUserService.updateTSysUserPwd(filter);
	}
	
	@ResponseBody
	@RequestMapping(value="updatePersonInfo",method = RequestMethod.POST)
	public Object updatePersonInfo(@RequestParam Map<String, String> filter) {
		int i =  smUserService.updatePersonUser(filter);
		return i;
	}
	
}
