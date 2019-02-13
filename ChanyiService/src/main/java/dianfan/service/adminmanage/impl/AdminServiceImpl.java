package dianfan.service.adminmanage.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import dianfan.annotations.SystemServiceLog;
import dianfan.constant.ResultBgMsg;
import dianfan.dao.mapper.adminmanage.AdminManageMapper;
import dianfan.entities.loginmanage.AdminInfo;
import dianfan.models.ResultBean;
import dianfan.service.adminmanage.AdminService;
import dianfan.util.StringUtility;
import dianfan.util.UUIDUtil;

/**
 * @ClassName AdminServiceImpl
 * @Description 管理员相关service 实现
 * @author sz
 * @date 2018年7月16日 下午4:56:38
 */
@Service
public class AdminServiceImpl implements AdminService {

	/**
	 * 注入： #AdminManageMapper
	 */
	@Autowired
	private AdminManageMapper adminManageMapper;

	/*
	 * (non-Javadoc)
	 * <p>Title: findAdminList</p>
	 * <p>Description: 获取管理员列表</p>
	 * @param length 分页偏移量
	 * @param page 每次请求的页数
	 * @return
	 * link: @see dianfan.service.adminmanage.AdminService#findAdminList(int, int, int)
	 */
	@Override
	@SystemServiceLog(method = "findAdminList",description = "获取管理员列表 ")
	public ResultBean findAdminList(int length, int page) {
		// 构建响应容器
		Map<String, Object> data = new HashMap<>();
		// 构建入参容器
		Map<String, Object> param = new HashMap<>();
		// 查询列表总数
		int count = adminManageMapper.findfindAdminCount();
		// 设置总条数
		data.put("totalcount", count);
		// 判断是否有数据
		if (count < (page - 1) * length || count == 0) {
			// 空的返回实体
			data.put("adminList", new ArrayList<>());
			// 未删选到数据
			return new ResultBean(data);
		}
		// 设置起始条数
		param.put("start", (page - 1)*length);
		// 添加分页偏移量
		param.put("length", length);
		// 查询管理员列表
		List<AdminInfo> adminList = adminManageMapper.findfindAdminList(param);
		// 获取用户的登陆日志
		// 获取用户的ID
		//List<String> userid = new ArrayList<>();
		for(AdminInfo admin : adminList) {
			String id = admin.getId();
			// 获取用户上次登录的信息
			AdminInfo loginLog = adminManageMapper.findfindAdminLogBy(id);
			if (!StringUtils.isEmpty(loginLog)) {
				if (!StringUtils.isEmpty(loginLog.getLoginDatetime())) {
					admin.setLoginDatetime(loginLog.getLoginDatetime());
				}
				if (!StringUtils.isEmpty(loginLog.getBrowser())) {	
					admin.setBrowser(loginLog.getBrowser());
				}
				if (!StringUtils.isEmpty(loginLog.getLoginIp())) {
					admin.setLoginIp(loginLog.getLoginIp());
				}
			}
			
		}
		// 添加管理员列表
		data.put("adminList", adminList);
		return new ResultBean(data);
	}

	/*
	 * (non-Javadoc)
	 * <p>Title: addAdmin</p>
	 * <p>Description: 添加管理员 </p>
	 * @param account 账号
	 * @param password 密码
	 * @param role 角色
	 * @return
	 * link: @see dianfan.service.adminmanage.AdminService#addAdmin(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional
	@SystemServiceLog(method = "findAdminList",description = "添加管理员 ")
	public ResultBean addAdmin(String account, String password, String role,String userid) {
		// 判断账号是否重复
		int count = adminManageMapper.getCheckAccount(account);
		if (count != 0 ) {
			// 当前账号已存在
			return new ResultBean("4008", ResultBgMsg.C_4008);
		}
		// 创建入参容器
		Map<String, Object> param = new HashMap<>();
		param.put("id", UUIDUtil.getUUID());
		param.put("account", account);
		param.put("password", password);
		param.put("role", role);
		param.put("userid", userid);
		// 添加新管理员
		adminManageMapper.addAdmin(param);
		// 添加管理员的角色
		adminManageMapper.addAdminRole(param);
		// 添加登陆信息
		adminManageMapper.addNewLogin(param);
		
		// 成功
		return new ResultBean();
	}

	/*
	 * (non-Javadoc)
	 * <p>Title: updataAdmin</p>
	 * <p>Description: 修改管理员</p>
	 * @param account 账号
	 * @param password 密码
	 * @param role 角色
	 * @return
	 * link: @see dianfan.service.adminmanage.AdminService#updataAdmin(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional
	@SystemServiceLog(method = "updataAdmin",description = "修改管理员 ")
	public ResultBean updataAdmin(String account, String password, String role,String adminid,String loginid) {
		// 判断账号是否存在
		AdminInfo info = adminManageMapper.getAdminInfo(adminid);
		if (info == null ) {
			// 当前账号不存在
			return new ResultBean("4010", ResultBgMsg.C_4010);
		}
		// 如果是管理员在修改自己的账号
		if (loginid.equals(adminid)) {
			// 查除管理员原来的角色
			String oldRole = adminManageMapper.getCheckoOldRole(loginid);
			// 如果该管理员原来没有角色，现在给自己加角色，直接报错
			if (StringUtility.isNull(oldRole) && !StringUtility.isNull(role)) {
				// 自己不可以为自己添加角色
				return new ResultBean("4021", ResultBgMsg.C_4021);
			} else if (!role.equals(oldRole)) {
				// 自己不可以修改自己的角色
				return new ResultBean("4021", ResultBgMsg.C_4022);
			}
		}
		
		// 创建入参容器
		Map<String, Object> param = new HashMap<>();
		param.put("account", account);
		param.put("password", password);
		param.put("role", role);
		param.put("adminid", adminid);
		param.put("loginid", loginid);
		// 添加用户的ID
		param.put("userid", info.getId());
		// 更新管理员
		// 如果有密码 ，就去更新密码，如果没有，就不更新
		if (!StringUtility.isNull(password)) {
			adminManageMapper.updataAdmin(param);
		}
		
		/* -因为存在删除角色后，用户是冻结状态，此时的用户是没有角色的，
		 * 所以在更新角色之前，先判断一下用户是否有角色，如果有角色就更
		 * 新角色，如果没有角色就添加角色- */
		
		// 1.判断当前用户是否存在角色
		int roles = adminManageMapper.findAdminRoles(adminid);
		if (roles == 0) {
			// 1.1 当前管理员没有角色，直接添加角色
			adminManageMapper.addCreationAdminRole(param);
		} else {
			// 1.2 更新用户的角色
			adminManageMapper.updataAdminRole(param);
		}
		
		// 成功
		return new ResultBean();
	}

	/*
	 * (non-Javadoc)
	 * <p>Title: delAdmin</p>
	 * <p>Description:  修改管理员状态 </p>
	 * @param adminid 管理员ID
	 * @param flag 操作状态
	 * link: @see dianfan.service.adminmanage.AdminService#delAdmin(java.util.List)
	 */
	@Override
	@Transactional
	@SystemServiceLog(method = "updataAdmin",description = "修改管理员状态  ")
	public void delAdmin(List<String> adminid, String flag,  String userid) {
		// 调库操作
		Map<String, Object> param = new HashMap<>();
		param.put("adminid", adminid);
		param.put("flag", flag);
		// 调库操作
		adminManageMapper.delAdmin(param);
	}
	
	
	
	
	
	
	
	
	
	
}
