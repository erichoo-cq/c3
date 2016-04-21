package com.c3.base.menu.auth.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.credential.DefaultPasswordService;
import org.apache.shiro.authc.credential.PasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.c3.base.core.page.Condition;
import com.c3.base.core.page.Condition.Operator;
import com.c3.base.core.repository.jdbc.BaseJdbcTemplate;
import com.c3.base.model.entity.sm.C3SmRole;
import com.c3.base.model.entity.sm.C3SmUser;
import com.c3.base.model.repository.sm.SmPersonRepository;
import com.c3.base.model.repository.sm.SmRoleRepository;
import com.c3.base.model.repository.sm.SmUserRepository;
import com.google.common.collect.Maps;


@Service
@Transactional
public class SmUserService {
	
	@Autowired
	private SmUserRepository smUserRepository;
	
//	@Autowired
	private PasswordService passwordService = new DefaultPasswordService();
	
	@Autowired
	private SmPersonRepository smPersonRepository;
	
	@Autowired
	private SmRoleRepository  smRoleRepository;
	
	@Autowired
	private BaseJdbcTemplate jdbc;
	
	/**
	 * 查找用户
	 * @param id
	 * @return
	 */
	public C3SmUser getUser(int id) {
		return smUserRepository.findOne(id);
	}
	
	public C3SmUser getBtUserByEmail(String email){
		Condition<C3SmUser> condition=Condition.<C3SmUser>build();
		condition.put("email", email);
	    return smUserRepository.findOne(condition);
	}
	
	public List<C3SmRole> getRolesList(){
		return smRoleRepository.findAll();
//		String sql = " select * from sm_bt_role ";
//		List<Map<String,Object>> list = jdbc.queryForList(sql);
//		return list;
	}
	
//	public Page<C3SmUser> pageUser(Pageable pageable, Map<String, String> filter){
//		Condition<C3SmUser> condition=Condition.<C3SmUser>build();
//		if (StringUtils.isNotBlank(filter.get("email"))) {
//			condition.put("email",Operator.LIKE,filter.get("email"));
//		}
//		if (StringUtils.isNotBlank(filter.get("userName"))) {
//			condition.put("userName",Operator.LIKE,filter.get("userName"));
//		}
//		condition.put("person.deptId", 10000);
//		Page<C3SmUser> page = smUserRepository.findAll(condition,pageable);
//		return page;
//	}
	
	public Page<Map<String, Object>> pageUser(Pageable pageable,Map<String,String> filter){
		String email = filter.get("email");
		String userName = filter.get("user_name");
		String sql = " select a.*,c.name from "
				   + " (select a.*,b.role_id  from "
				   + " ( select a.*,d.org_name,d.org_type,"
				   + " b.created_date,cast(a.is_valid as INT ) as state,"
				   + " case when d.org_type = '1' then '中心用户' when d.org_type = '2' then '区县用户' when d.org_type = '3' then '企业用户'"
				   + " else '未知类型用户' end  as org_type_name, "
				   + " b.full_name,b.gender,b.phone "
				   + " from c3_sm_user a,c3_sm_person b ,c3_sm_department c,c3_sm_org d "
				   + " where a.person_id = b.person_id and b.dept_id = c.dept_id  ";
		if(StringUtils.isNotBlank(email)){
			sql = sql + "and a.email = '"+email+"'";
		}
		if(StringUtils.isNotBlank(userName)){
			sql = sql + " and a.user_name like '%"+userName+"%'";
		}
		sql = sql + " and c.org_code = d.org_code ) a left join c3_sm_role_user b on a.user_id = b.user_id ) a "
				  + " left join c3_sm_role c ON a.role_id = c.role_id order by a.user_id ";
		Page<Map<String, Object>> page =  jdbc.pagedQuery(sql, pageable.getPageNumber(), pageable.getPageSize());
		return page;
	}
	
//	public void saveUser(Map<String, String> params) throws ParseException{
//		C3SmUser user=new C3SmUser();
//		C3SmPerson person=new C3SmPerson();
//		person.setState(1);
//		person.setFullName("");
//		person.setDeptId(10000);
//		person.setCreatedDate(new Date());
//		user.setPerson(smPersonRepository.save(person));
//		if (params.get("state").equals("1")) {
//			user.setIsValid(true);
//		}else {
//			user.setIsValid(false);
//		}
//		user.setLoginTimes(0);
//		user.setIsLogin(false);
//		user.setEmail(params.get("email"));
//		user.setUserName(params.get("userName"));
//		user.setPassword(passwordService.encryptPassword(params.get("loginPassword")));
//		smUserRepository.save(user);
//	}
	
	public int saveUser(Map<String, String> filter){
//		smBtUserRepository.save(user);
		
		
		String sqlCheakUser = " select * from c3_sm_user where email = ? ";
		String sqlInsertPerson = " insert into c3_sm_person "
						       + " (state,created_date,full_name,dept_id) values ("
						       + " ?,?,?,? )";
		String sqlGetPerson = " select * from c3_sm_person "
							+ " where created_date = ? and full_name = ?"
							+ " and dept_id = ? ";
		String sqlInsertUser = " insert into c3_sm_user ("
							 + " user_name,is_valid,is_login,password,"
							 + " login_times,email,person_id) values("
							 + " ?,?,?,?,"
							 + " ?,?,? )";
		
		Date now = new Date();
		String full_name = filter.get("full_name");
		int dept_id = Integer.parseInt(filter.get("dept_id"));
		String user_name = filter.get("user_name");
		String pwd = filter.get("pwd");
		pwd = passwordService.encryptPassword(pwd);
		String email = filter.get("email");
		int person_id = 0;
		
		List<Map<String,Object>> listCheak = jdbc.queryForList(sqlCheakUser,email);
		if(listCheak.size()!=0){
			return -1;
		}
		jdbc.update(sqlInsertPerson,1,now,full_name,dept_id);
		List<Map<String,Object>> list = jdbc.queryForList(sqlGetPerson,now,full_name,dept_id);
		person_id = Integer.parseInt(list.get(0).get("person_id").toString());
		jdbc.update(sqlInsertUser,user_name,true,false,pwd,
					0,email,person_id);
		return 1;
	}
	
	public void updateUser(Map<String, String> params){
		Integer id = Integer.valueOf(params.get("id"));
		C3SmUser user = smUserRepository.findOne(id);
		if (StringUtils.isNotBlank(params.get("email"))) {
			user.setEmail(params.get("email"));
		}
		if (StringUtils.isNotBlank(params.get("userName"))) {
			user.setUserName(params.get("userName"));
		}
		if (params.get("state").equals("1")) {
			user.setIsValid(true);
		}else {
			user.setIsValid(false);
		}
		smUserRepository.save(user);
	}
	
	public void updateTSysUserPwd(Map<String, String> filter){
		Integer id = Integer.valueOf(filter.get("id"));
		C3SmUser user = smUserRepository.findOne(id);
		user.setPassword(passwordService.encryptPassword("000000"));
		smUserRepository.save(user);
	}
	
	public void deleteUser(Integer userId){
		String sql = "delete from c3_sm_user where user_id = '"+userId+"'";
		jdbc.update(sql);
	}
	
	public List<Map<String,Object>> getDepartInfo(){
		String sql = " select * from c3_sm_department ";
		List<Map<String,Object>> list = jdbc.queryForList(sql);
		return list;
	}
	
	public Map<String,Object> getPersonUser(int id) {
		
		Map<String,Object> map = Maps.newHashMap();
		String sql = " select b.*,a.user_id,c.role_id "
				   + " from c3_sm_user a left join c3_sm_role_user c on a.user_id = c.user_id,"
				   + " c3_sm_person b "
				   + " where a.person_id = b.person_id  and a.user_id = ? ";
		List<Map<String,Object>> list = jdbc.queryForList(sql,id);
		map = list.get(0);
		return map;
	}
	
	/**
	 * 更新用户基础信息
	 * @param filter
	 * @return
	 */
	public int updatePersonUser(Map<String,String> filter){
//		smBtUserRepository.save(user);
		int i = 0;
		int user_id = Integer.parseInt(filter.get("user_id"));
		int role_id = Integer.parseInt(filter.get("roleId"));
		String full_name = filter.get("full_name");
		String phone = filter.get("phone");
		
		String sql = " select * from c3_sm_user where user_id = ? ";
		List<Map<String,Object>> list = jdbc.queryForList(sql,user_id);
		int personId = Integer.parseInt(list.get(0).get("person_id").toString());
		
		String sqlUpdate = "update c3_sm_person set full_name = ?,phone = ? where person_id = ? ";
		String sqlCheakRole = " select * from c3_sm_role_user where user_id = ? ";
		List<Map<String,Object>> cheakList = jdbc.queryForList(sqlCheakRole,user_id);
		if(cheakList.size() == 0){
			String sqlInsert = " insert into c3_sm_role_user (user_id,role_id) values (?,?) ";
			i = jdbc.update(sqlInsert,user_id,role_id);
		}else{
			String sqlUpdateRole = " update c3_sm_role_user set role_id = ? where user_id = ? ";
			i = jdbc.update(sqlUpdateRole,role_id,user_id);
		}
		i = jdbc.update(sqlUpdate,full_name,phone,personId);
		return i;
	}
	
}
