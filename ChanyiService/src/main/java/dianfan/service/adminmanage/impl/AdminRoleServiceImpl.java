package dianfan.service.adminmanage.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dianfan.annotations.SystemServiceLog;
import dianfan.constant.ResultBgMsg;
import dianfan.dao.mapper.adminmanage.AdminRoleMapper;
import dianfan.entities.role.AdminPopedom;
import dianfan.entities.role.RolePopedom;
import dianfan.entities.role.TRole;
import dianfan.models.ResultBean;
import dianfan.service.adminmanage.AdminRoleService;
import dianfan.util.UUIDUtil;


/**
 * @ClassName AdminRoleServiceImpl
 * @Description 
 * @author sz
 * @date 2018年7月17日 下午6:20:56
 */
@Service
public class AdminRoleServiceImpl implements AdminRoleService {

	/**
	 * 注入： #AdminRoleMapper
	 */
	@Autowired
	private AdminRoleMapper adminRoleMapper;

	
	/*
	 * (non-Javadoc)
	 * <p>Title: findadmiRolenList</p>
	 * <p>Description: 获取管理员角色列表 </p>
	 * @return ResultBean
	 * link: @see dianfan.service.adminmanage.AdminRoleService#findadmiRolenList()
	 */
	@Override
	@SystemServiceLog(method = "findadmiRolenList",description = "获取管理员角色列表 ")
	public ResultBean findadmiRolenList() {
		// 创建返回数据模型
		Map<String, Object> data = new HashMap<>();
		// 查看角色的总数
		int count = adminRoleMapper.findadmiRolenCount();
		// 设置总条数
		data.put("total", count);
		if (count == 0) {
			data.put("roleList", new ArrayList<>());
			return new ResultBean(data);
		}
		List<TRole> roleList = adminRoleMapper.findadmiRolenList();
		// 获取角色下的权限
		List<String> releid = new ArrayList<>();
		for (TRole t:roleList) {
			releid.add(t.getId());
		}
		// 获取角色下面的权限
		List<RolePopedom> rolePopedom = adminRoleMapper.findRolePopedom(releid);
		
		for (TRole role :  roleList) {
			String id = "";
			for (RolePopedom popedom : rolePopedom) {
				if (role.getId().equals(popedom.getRoleid())) {
					id += popedom.getPopedomid() + ",";
				}
			}
			// 将权限加入到返回参数中
			role.setPopedom(id);
		}
		
		// 添加角色列表
		data.put("roleList", roleList);
		// 成功
		return new ResultBean(data);
	}


	/*
	 * (non-Javadoc)
	 * <p>Title: addAdmiRolen</p>
	 * <p>Description: 添加管理员角色</p>
	 * @param distinguish 角色区分 
	 * @param name 角色描述
	 * @param description 角色描述
	 * @return ResultBean
	 * link: @see dianfan.service.adminmanage.AdminRoleService#addAdmiRolen(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Transactional
	@Override
	@SystemServiceLog(method = "findadmiRolenList",description = "添加管理员角色 ")
	public ResultBean addAdmiRolen(String distinguish, String name, String description,String roleids) {
		// 构建入参模型
		Map<String,Object> param = new HashMap<>();
		param.put("distinguish", distinguish);
		param.put("name", name);
		param.put("description", description);
		
		// 添加前先确认是否存在改角色
		int count = adminRoleMapper.getAdmiRolen(param);
		if (count != 0) {
			// 已存在该角色
			return new ResultBean("4014",ResultBgMsg.C_4014);
		}
		// 创建一个新的角色ID uuid
		param.put("id", UUIDUtil.getUUID());
		adminRoleMapper.addAdmiRolen(param);
		// 创建角色下的权限
		List<String> idList = Arrays.asList(roleids.split(","));
		param.put("idList", idList);
		adminRoleMapper.addAdmiRolenPopedom(param);
		
		return new ResultBean();
	}

	/*
	 * (non-Javadoc)
	 * <p>Title: updataAdmiRolen</p>
	 * <p>Description: 修改管理员角色</p>
	 * @param distinguish 角色区分 
	 * @param name 角色描述
	 * @param description 角色描述
	 * @param roleids 权限ids
	 * @param userid 登陆者的id
	 * @return ResultBean
	 * link: @see dianfan.service.adminmanage.AdminRoleService#updataAdmiRolen(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Transactional
	@Override
	@SystemServiceLog(method = "findadmiRolenList",description = " 修改管理员角色 ")
	public ResultBean updataAdmiRolen(String distinguish, String name, String description, String roleids,
			String userid, String id) {
		// 构建入参模型
		Map<String,Object> param = new HashMap<>();
		param.put("distinguish", distinguish);
		param.put("name", name);
		param.put("description", description);
		// 角色ID
		param.put("id", id);
		/* 登陆者不可以修改自己的角色 */
		// 获取登陆者的角色
		String userRole = adminRoleMapper.getUserRole(userid);
		if (id.equals(userRole)) {
			// 自身不可以修改自身角色
			return new ResultBean("4016", ResultBgMsg.C_4016);
		}
		List<String> idList = Arrays.asList(roleids.split(","));
		param.put("idList", idList);
		// 修改角色信息
		adminRoleMapper.updataAdmiRolen(param);
		// 删除角色下原来的权限
		adminRoleMapper.delAdmiRolenPopedom(id);
		// 新建角色下的权限信息
		adminRoleMapper.addAdmiRolenPopedom(param);
		
		return new ResultBean();
	}

	/*
	 * (non-Javadoc)
	 * <p>Title: delAdmiRolen</p>
	 * <p>Description: 删除角色</p>
	 * @param userid 用户的ID
	 * @param ids 删除的角色ID
	 * @return
	 * link: @see dianfan.service.adminmanage.AdminRoleService#delAdmiRolen(java.lang.String, java.lang.String)
	 */
	@Transactional
	@Override
	@SystemServiceLog(method = "delAdmiRolen",description = " 删除管理员角色 ")
	public ResultBean delAdmiRolen(String userid, String ids) {
		/* 登陆者不可以删除自己的角色 */
		// 获取登陆者的角色
		String userRole = adminRoleMapper.getUserRole(userid);
		// 拆解权限ID
		List<String> idList = Arrays.asList(ids.split(","));
		if (idList.contains(userRole)) {
			// 自身不可以删除自身角色
			return new ResultBean("4017", ResultBgMsg.C_4017);
		}
		// 获取该权限下的管理员
		List<String> user = adminRoleMapper.findUseridByrole(idList);
		if (user != null && user.size() > 0) {
			// 删除角色前先冻结此角色下的管理员
			adminRoleMapper.updataFreezeAdmin(user);
		}
		// 删除该角色与权限的关系表
		adminRoleMapper.delAdminRole(idList);
		// 删除对应角色
		adminRoleMapper.delRolen(idList);
		
		return new ResultBean();
	}


	/*
	 * (non-Javadoc)
	 * <p>Title: findAdminPopedom</p>
	 * <p>Description: 获取管理员权限列表 </p>
	 * @return ResultBean
	 * link: @see dianfan.service.adminmanage.AdminRoleService#findAdminPopedom()
	 */
	@Override
	@SystemServiceLog(method = "findAdminPopedom",description = "获取管理员权限列表 ")
	public ResultBean findAdminPopedom() {
		// 构建返回数据模型
		Map<String, Object> data = new HashMap<>();
		// 获取权限列表数量
		int count = adminRoleMapper.findAdminPopedomCount();
		data.put("total", count);
		if (count == 0) {
			data.put("popedomList", new ArrayList<>());
			return new ResultBean(data);
		}
		// 
		List<AdminPopedom> popedomList = adminRoleMapper.findAdminPopedomList();
		data.put("popedomList",popedomList);
		return new ResultBean(data);
	}

	
	
	
	
	
	
	
	
}
