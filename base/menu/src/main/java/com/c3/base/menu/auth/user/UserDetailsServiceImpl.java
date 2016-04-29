package com.c3.base.menu.auth.user;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.DisabledAccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.c3.base.core.permission.shiro.UserDetails;
import com.c3.base.core.permission.shiro.service.IUserDetailsService;
import com.c3.base.core.repository.jdbc.BaseJdbcTemplate;
import com.c3.base.model.entity.sm.C3SmPerson;
import com.c3.base.model.entity.sm.C3SmUser;
import com.c3.base.model.repository.sm.SmUserRepository;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.opensymphony.oscache.util.StringUtil;

/**
 * description: 从数据库中获取到角色以及权限信息，供自定义的userRealm使用
 *
 * @version 2016年4月27日 下午9:08:59
 * @see UserRealm()
 * modify content------------author------------date
 */
@Service
@Transactional
public class UserDetailsServiceImpl implements IUserDetailsService<Integer, User> {
	//超级用户
	private static final String[] SUPER_USER = { "admin", "super" };

	//所有的角色集合
	private static Set<String> ALL_ROLES = Sets.newHashSet();

	@Autowired
	private SmUserRepository smUserRepository;
	
	@Autowired
	private BaseJdbcTemplate jdbc;

	/**
	 * description: 将UserType中定义的角色类型初始化
	 * @author:dwx
	 * @time: 2016年4月27日 下午9:03:24
	 */
	@PostConstruct
	public void initRoles() {
		for (UserType t : UserType.values()) {
			ALL_ROLES.add(t.name());
		}
	}

	@Override
	public UserDetails<Integer, User> findByAccountAndHost(String username, String host) {
		if (StringUtils.isEmpty(username)) {
			throw new AccountException("Accout is empty");
		}
		
		C3SmUser smUser = smUserRepository.findFirstByUserNameOrEmail(username);
		if (!smUser.getIsValid()) {
			throw new DisabledAccountException("Account is disabled.");
		}

		C3SmPerson person = smUser.getPerson();
		User user = new User();
		user.setUserId(smUser.getId());
		user.setUserName(smUser.getUserName());
		user.setPassword(smUser.getPassword());
		user.setNickName(person.getRemarks());
		Map<String, Object> orgInfo = this.findOrgInfo(person.getDeptId());
		Integer userType = Integer.valueOf(orgInfo.get("org_type").toString());
		user.setUserType(userType);
		user.setOrgCode(orgInfo.get("org_code").toString());
		user.setOrgName(orgInfo.get("org_name").toString());
		return new UserDetailsImpl(user, this.getRoles(user), this.getPermissions(user));
	}

	/**
	 * description: 获取当前用户所属的角色
	 * @param user 当前用户对象
	 * @return 角色集合
	 * @author:dwx
	 * @time: 2016年4月27日 下午8:58:55
	 */
	private Collection<String> getRoles(User user) {
		if (ArrayUtils.contains(SUPER_USER, user.getUserName())) {
			return Collections.unmodifiableSet(ALL_ROLES);
		}
		return Collections.singleton(user.getType().name());
	}

	/**
	 * description: 根据当前用户返回权限列表
	 * @param user：当前用户
	 * @return：权限列表
	 * @author:dwx
	 * @time: 2016年4月27日 下午9:04:42
	 */
	private Collection<String> getPermissions(User user) {
		String sql = "select t4.menu_name as name, t4.action_url as url, t4.menu_type, t4.perm_key "
				+ " from c3_sm_user t1, c3_sm_role_user t2, c3_sm_role_resource t3, c3_sm_resource_menu t4 "
				+ " where t1.user_id=? and t1.user_id = t2.user_id and t2.role_id = t3.role_id and t3.resource_type = 1 "
				+ " and t3.resource_id = t4.resource_id and t4.is_deleted = false order by t4.rank";
		List<Map<String, Object>> results = jdbc.queryForList(sql, user.getUserId());
		Collection<String> perms = Lists.newLinkedList();
		for (Map<String, Object> result : results) {
			String perm = ObjectUtils.toString(result.get("perm_key"));
			if (!StringUtil.isEmpty(perm)) {
				perms.add(perm);
			}
		}
		return perms;
	}

	/**
	 * description: 获取组织信息
	 * @param deptId 部门id
	 * @return 获取到的组织信息
	 * @author:dwx
	 * @time: 2016年4月28日 上午10:13:47
	 */
	private Map<String, Object> findOrgInfo(Integer deptId) {
		String sql = "select t1.org_code, t2.org_type, t2.org_name from c3_sm_department t1, c3_sm_org t2 where t1.dept_id = ? and t1.org_code = t2.org_code";
		return jdbc.queryForMap(sql, deptId);
	}

}
