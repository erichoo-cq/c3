package com.c3.base.menu.auth.service;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.c3.base.core.page.Condition.Operator;
import com.c3.base.core.page.Condition;
import com.c3.base.model.entity.sm.C3SmRole;
import com.c3.base.model.entity.sm.C3SmRoleResource;
import com.c3.base.model.entity.sm.C3SmRoleResourcePK;
import com.c3.base.model.repository.sm.SmRoleRepository;
import com.c3.base.model.repository.sm.SmRoleResourceRepository;


@Service
@Transactional
public class RoleService {
	@Autowired
	private JdbcTemplate jdbc;
	@Autowired
	private SmRoleRepository smRoleRepository;
//	@Autowired
//	private SmResourceMenuRepository smBtSystemMenuRepository;
	@Autowired
	private SmRoleResourceRepository smRoleResourceRepository;
	
	
	/**
	 * 获取role
	 * @param id
	 * @return
	 */
	public C3SmRole getRole(Integer id){
		return smRoleRepository.findOne(id);
	}
	
	/**
	 * 角色查询
	 * 
	 * @param params
	 * @param pageable
	 * @return
	 */
	public Page<C3SmRole> pageRoles(Map<String, String> params, Pageable pageable) {
		Condition<C3SmRole> condition = Condition.<C3SmRole> build();
		String roleName = params.get("roleName");
		if (StringUtils.isNotBlank(roleName)) {
			condition.put("roleName", Operator.LIKE, roleName);
		}
		Page<C3SmRole> rolePage = smRoleRepository.findAll(condition, pageable);
		return rolePage;
	}

	/**
	 * 添加
	 * @param smBtRole
	 */
	public void create(C3SmRole smBtRole) {
		
		smRoleRepository.save(smBtRole);
	}

	/**
	 * 修改
	 * 
	 * @param smBtRole
	 */
//	public void update(C3SmRole smBtRole) {
//		
//		smRoleRepository.updateAttributes(smBtRole, new String[] { "roleName", "roleDescription", "isSystem" });
//	}
	
	public void update(C3SmRole c3SmRole) {
		Integer roleId = c3SmRole.getRoleId();
		C3SmRole roleEntity = smRoleRepository.findOne(roleId);
		if (null == roleEntity) {
			System.out.println("角色不存在：" + roleId);
		}
		roleEntity.setName(c3SmRole.getName());
		roleEntity.setDescription(c3SmRole.getDescription());
		roleEntity.setIsSystem(c3SmRole.getIsSystem());
	}
	
	
	/**
	 * 所有菜单
	 * @return
	 */
	public List<Map<String, Object>> getAllMenu(Integer roleId) {
		String sql = " select t1.*,coalesce(t2.resource_id,0) state from c3_sm_resource_menu t1 "
				   + " left join (select a.role_id,b.* from c3_sm_role_resource a,c3_sm_resource_menu b "
				   + " where a.resource_id = b.resource_id and a.role_id = ?) t2 on t1.resource_id = t2.resource_id"
				   + " where t1.parent_resource_id = (select resource_id from c3_sm_resource_menu where parent_resource_id = '-9999')"
				   + " and t1.is_deleted='false' order by rank";
		List<Map<String, Object>> list = jdbc.queryForList(sql,roleId);
		
		String sqlChild = " select t1.*,coalesce(t2.resource_id,0) state from c3_sm_resource_menu t1"
				        + " left join (select a.role_id,b.* from c3_sm_role_resource a,c3_sm_resource_menu b "
				   	    + " where a.resource_id = b.resource_id and a.role_id = ?) t2 on t1.resource_id = t2.resource_id"
				   	    + " where t1.parent_resource_id=? and t1.is_deleted='false' order by rank";
		for(Map<String, Object> map : list){
			Integer resourceId = Integer.valueOf(map.get("resource_id").toString());
			List<Map<String, Object>> childs = jdbc.queryForList(sqlChild,roleId,resourceId);
			if(childs.size() > 0){
				map.put("childs", childs);
			}
		}
		return list;
	}
	
	public void saveResource(Integer roleId,String addMenu,String delMenu){
		if(!addMenu.equals("")){
			String[] menus = addMenu.split("#");
			for(String m : menus){
				if(m.equals("")){
					break;
				}
				C3SmRoleResource sm = new C3SmRoleResource();
				C3SmRoleResourcePK pk = new C3SmRoleResourcePK();
				pk.setResourceId(Integer.valueOf(m));
				pk.setRoleId(roleId);
				sm.setId(pk);
				sm.setResourceType(1);
			    smRoleResourceRepository.save(sm);
			}
		}
		
		if(!delMenu.equals("")){
			String[] menus = delMenu.split("#");
			for(String m : menus){
				if(m.equals("")){
					break;
				}
				C3SmRoleResource sm = new C3SmRoleResource();
				C3SmRoleResourcePK pk = new C3SmRoleResourcePK();
				pk.setResourceId(Integer.valueOf(m));
				pk.setRoleId(roleId);
				sm.setId(pk);
				sm.setResourceType(1);
			    smRoleResourceRepository.delete(sm);
			}
		}
	}
	
//	/**
//	 * 父级菜单资源查询
//	 * 
//	 * @param params
//	 * @param pageable
//	 * @return
//	 */
//	public List<Map<String, Object>> getMenu() {
//		String sql = " select * from c3_sm_resource_menu where parent_resource_id = (select resource_id from c3_sm_resource_menu where parent_resource_id = '-9999')";
//		List<Map<String, Object>> menupage = jdbc.queryForList(sql);
//		return menupage;
//	}
	
//	/**
//	 * 菜单资源查询
//	 * 
//	 * @param params
//	 * @param pageable
//	 * @return
//	 */
//	public List<SmBtSystemMenu> getMenus() {
//		List<SmBtSystemMenu> menupage = smBtSystemMenuRepository.findAll(Condition.<SmBtSystemMenu> build());
//		return menupage;
//	}
//	
//	/**
//	 * 菜单资源插入
//	 */
//	public void insert(Map<String, String> params) {
//		int role_id = Integer.parseInt(params.get("id"));
//		String sql = "INSERT INTO sm_bt_role_resource(resource_id, resource_type, role_id) VALUES(?, 1,? ) ";
//		for(int i=1;i<params.size();i++){
//			int resource_id = Integer.parseInt(params.get(""+i+""));
//			jdbc.update(sql,resource_id,role_id);
//		}
//	}
//	/**
//	 * 查看角色权限
//	 */
//	public List<Map<String,Object>> look(Map<String, String> params){
//		int role_id = Integer.parseInt(params.get("id"));
//		String sql = " select a.role_id,b.* "+
//				" from sm_bt_role_resource a,c3_sm_resource_menu b "+
//				" where a.resource_id = b.resource_id and a.role_id = ? ";
//		List<Map<String,Object>> list = jdbc.queryForList(sql,role_id);
//		return list;
//	}
//	/**
//	 * 清除已有权限
//	 */
//	public void delete(Map<String, String> params) {
//		int role_id = Integer.parseInt(params.get("id"));
//		String sql = "DELETE FROM sm_bt_role_resource WHERE role_id = ? ";
//		jdbc.update(sql, role_id);
//	}
}
