package dianfan.service.adminlogin.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dianfan.annotations.SystemServiceLog;
import dianfan.dao.mapper.adminlogin.AdminloginMapper;
import dianfan.dao.mapper.operatelog.OperateLogMapper;
import dianfan.entities.loginmanage.AdminInfo;
import dianfan.entities.loginmanage.AdminLoginLog;
import dianfan.entities.loginmanage.OperateLogs;
import dianfan.entities.role.Role;
import dianfan.models.ResultBean;
import dianfan.service.adminlogin.AdminLoginService;
import dianfan.service.impl.RedisTokenService;
import dianfan.util.UUIDUtil;

/**
 * @ClassName AdminLoginServiceImlp
 * @Description 管理员登陆相关Service
 * @author sz
 * @date 2018年7月16日 下午1:41:40
 */
@Service
public class AdminLoginServiceImpl implements AdminLoginService {
//	@Autowired  
//	private HttpServletRequest request; 
	
	/**
	 * 注入： #AdminloginMapper
	 */
	@Autowired
	private AdminloginMapper adminloginMapper;

	/**
	 * 注入: #RedisTokenService
	 */
	@Autowired
	private RedisTokenService redisTokenService;
	/**
	 * 注入: #OperateLogMapper
	 */
	@Autowired
	private OperateLogMapper operateLogMapper;

	/*
	 * (non-Javadoc) <p>Title: getAdminInfo</p> <p>Description: 验证用户的登陆信息</p>
	 * 
	 * @param account 账号
	 * 
	 * @param password 密码
	 * 
	 * @return link: @see
	 * dianfan.service.adminlogin.AdminLoginService#getAdminInfo(java.lang.String,
	 * java.lang.String)
	 */
	@SystemServiceLog(method = "getAdminInfo", description = "验证用户登陆，获取用户数据 ")
	@Override
	public AdminInfo getAdminInfo(String account, String password) {
		
		// 入参空值验证
		if (account == null || "".equals(account) || password == null || "".equals(password)) {
			return null;
		}
		// 创建入参容器
		Map<String, Object> param = new HashMap<>();
		param.put("account", account);
		param.put("password", password);
		// 调库验证
		AdminInfo adminInfo = adminloginMapper.getAdminInfo(param);
		
		// 返回
		return adminInfo;
	}

	/*
	 * (non-Javadoc) <p>Title: findAdminRole</p> <p>Description: 获取用户的角色</p>
	 * 
	 * @param id 用户的ID
	 * 
	 * @return link: @see
	 * dianfan.service.adminlogin.AdminLoginService#findAdminRole(java.lang.String)
	 */
	@Override
	@SystemServiceLog(method = "findAdminRole", description = "获取用户的角色 ")
	public Role findAdminRole(String id) {
		// 获取用户的j角色
		Role role = adminloginMapper.findAdminRole(id);
		return role;
	}

	/*
	 * (non-Javadoc) <p>Title: findAdminPopedom</p> <p>Description: 获取角色下的权限</p>
	 * 
	 * @param role 角色ID
	 * 
	 * @return link: @see
	 * dianfan.service.adminlogin.AdminLoginService#findAdminPopedom(java.lang.
	 * String)
	 */
	@Override
	@SystemServiceLog(method = "findAdminPopedom", description = "获取角色下的权限 ")
	public List<String> findAdminPopedom(String role) {
		// 获取角色下的权限
		List<String> popedom = adminloginMapper.findAdminPopedom(role);
		return popedom;
	}

	/*
	 * (non-Javadoc) <p>Title: getAdminLoginLog</p> <p>Description: 获取用户的登陆日志</p>
	 * 
	 * @param id 用户的ID
	 * 
	 * @return link: @see
	 * dianfan.service.adminlogin.AdminLoginService#getAdminLoginLog(java.lang.
	 * String)
	 */
	@Override
	@SystemServiceLog(method = "getAdminLoginLog", description = "获取用户的登陆日志 ")
	public AdminLoginLog getAdminLoginLog(String id) {
		AdminLoginLog adminLoginLog = adminloginMapper.getAdminLoginLog(id);
		return adminLoginLog;
	}

	/*
	 * (non-Javadoc) <p>Title: addUserLoginLog</p> <p>Description: 插入用户的登陆日志</p>
	 * 
	 * @param ip
	 * 
	 * @param browserName
	 * 
	 * @param userid link: @see
	 * dianfan.service.adminlogin.AdminLoginService#addUserLoginLog(java.lang.
	 * String, java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional
	@SystemServiceLog(method = "addUserLoginLog", description = "插入用户的登陆日志 ")
	public void addUserLoginLog(String ip, String browserName, String userid) {
		// 创建入参容器
		Map<String, Object> param = new HashMap<>();
		// 登录IP
		param.put("ip", ip);
		// 浏览器
		param.put("browserName", browserName);
		// 用户ID
		param.put("userid", userid);
		// 登录日志ID
		param.put("id", UUIDUtil.getUUID());
		// 插入到数据库
		adminloginMapper.addUserLoginLog(param);
		
		
	}

	
	/*
	 * (non-Javadoc) <p>Title: selectPopedomsPermission</p> <p>Description:获取菜单权限
	 * </p>
	 * 
	 * @param userId
	 * 
	 * @param uri link: @see
	 * dianfan.service.adminlogin.AdminLoginService#selectPopedomsPermission(java.
	 * lang.String, java.lang.String)
	 */
	@Override
	@SystemServiceLog(method = "selectPopedomsPermission", description = "获取菜单权限 ")
	public int selectPopedomsPermission(String userId, String uri) {
		// 创建入参容器
		Map<String, Object> param = new HashMap<>();
		// 用户id
		param.put("userId", userId);
		// 树的连接路径
		param.put("uri", uri);
		
		return adminloginMapper.selectPopedomsPermission(param);
	}

	
	/*
	 * (non-Javadoc)
	 * <p>Title: addOperateLog</p>
	 * <p>Description: 添加后台管理员操作日志</p>
	 * @param userid
	 * @param popedomid
	 * @param roleid
	 * @param operate
	 * @param ip
	 * link: @see dianfan.service.adminlogin.AdminLoginService#addOperateLog(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional
	@SystemServiceLog(method = "addOperateLog", description = "添加后台管理员操作日志 ")
	public void addOperateLog(String userid, String popedomid, String roleid, String operate, String ip) {
		// 添加后台管理员操作日志
		operateLogMapper.InsertOperateLog(userid, popedomid, roleid, operate, ip);
	}

	/*
	 * (non-Javadoc)
	 * <p>Title: getRoleidByUri</p>
	 * <p>Description: 获取模块ID</p>
	 * @param requestUri url
	 * @return
	 * link: @see dianfan.service.adminlogin.AdminLoginService#getRoleidByUri(java.lang.String)
	 */
	@Override
	@SystemServiceLog(method = "getRoleidByUri", description = "获取模块ID ")
	public String getPopedomidByUri(String requestUri) {
		// 获取模块ID
		String popedomid = adminloginMapper.getRoleidByUri(requestUri);
		return popedomid;
	}

	/*
	 * (non-Javadoc)
	 * <p>Title: getRoleidByUserid</p>
	 * <p>Description: 获取用户的角色ID</p>
	 * @param userid userid
	 * @return
	 * link: @see dianfan.service.adminlogin.AdminLoginService#getRoleidByUserid(java.lang.String)
	 */
	@Override
	@SystemServiceLog(method = "getRoleidByUri", description = "获取模块ID ")
	public String getRoleidByUserid(String userid) {
		// 获取用户的角色ID
		String roleid = adminloginMapper.getRoleidByUserid(userid);
 		return roleid;
	}

	/*
	 * (non-Javadoc)
	 * <p>Title: findOperateLogList</p>
	 * <p>Description: 获取业务日志列表</p>
	 * @param userid userid
	 * @param page 第几页
	 * @param pageSize 每页第几条
	 * @param name 名字搜索
	 * @param role 角色搜索
	 * @param starttime 开始时间
	 * @param endtime 结束时间
	 * @return ResultBean
	 * link: @see dianfan.service.adminlogin.AdminLoginService#findOperateLogList(java.lang.String, int, int, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	@SystemServiceLog(method = "findOperateLogList", description = "获取业务日志列表")
	public ResultBean findOperateLogList(String userid, int page, int pageSize, String name, String role,
			String starttime, String endtime) {
		// 创建返回容器
		Map<String, Object> data = new HashMap<>();
		
		// 创建入参容器
		Map<String, Object> param = new HashMap<>();
		//param.put("userid", userid);
		param.put("name", name);
		param.put("role", role);
		param.put("starttime", starttime);
		param.put("endtime", endtime);
		// 分页偏移量
		param.put("start", (page - 1) * pageSize);
		param.put("length", pageSize);
		// 获取日志列表数量
		int count = adminloginMapper.findOperateLogCount(param);
		data.put("count", count);
		if (count == 0 || count < (page - 1) * pageSize) {
			data.put("operateLogList", new ArrayList<>());
			return new ResultBean(data);
		}
		// 获取日志列表
		List<OperateLogs> operateLogList = adminloginMapper.findOperateLogList(param);
		data.put("operateLogList", operateLogList);
		return new ResultBean(data);
	}





}
