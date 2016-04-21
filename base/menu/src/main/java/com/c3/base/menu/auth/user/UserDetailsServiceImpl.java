package com.c3.base.menu.auth.user;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.apache.commons.lang.ArrayUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.c3.base.core.permission.shiro.UserDetails;
import com.c3.base.core.permission.shiro.service.IUserDetailsService;
import com.c3.base.core.repository.jdbc.BaseJdbcTemplate;
import com.c3.base.model.entity.sm.C3SmPerson;
import com.c3.base.model.entity.sm.C3SmUser;
import com.c3.base.model.repository.sm.SmUserRepository;
import com.google.common.base.Optional;
import com.google.common.collect.Sets;

@Service
@Transactional
public class UserDetailsServiceImpl implements IUserDetailsService<Integer, User> {
	private static final String[] SUPER_USER = { "admin", "super" };// 超级用户

	private static Set<String> ALL_ROLES = Sets.newHashSet();

	@Autowired
	private SmUserRepository smUserRepository;
	@Autowired
	private BaseJdbcTemplate jdbc;

	@PostConstruct
	public void initRoles() {
		for (UserType t : UserType.values()) {
			ALL_ROLES.add(t.name());
		}
	}

	@Override
	public UserDetails<Integer, User> findByAccountAndHost(String username, String host) {
		Optional<String> validateUserName = Optional.of(username);
		C3SmUser smUser = smUserRepository.findFirstByUserNameOrEmail(validateUserName.get());
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

	private Collection<String> getRoles(User user) {
		if (ArrayUtils.contains(SUPER_USER, user.getUserName())) {
			return Collections.unmodifiableSet(ALL_ROLES);
		}
		return Collections.singleton(user.getType().name());
	}

	/**
	 * 获取用户角色
	 * 
	 * @param user
	 * @return
	 */
	private Collection<String> getPermissions(User user) {
		return null;
	}

	private Map<String, Object> findOrgInfo(Integer deptId) {
		String sql = "select t1.org_code, t2.org_type, t2.org_name from sm_bt_department t1, sm_bt_organization t2 where t1.dept_id = ? and t1.org_code = t2.org_code";
		return jdbc.queryForMap(sql, deptId);
	}

}
