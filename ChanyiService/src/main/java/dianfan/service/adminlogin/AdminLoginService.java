package dianfan.service.adminlogin;

import java.util.List;

import dianfan.entities.loginmanage.AdminInfo;
import dianfan.entities.loginmanage.AdminLoginLog;
import dianfan.entities.role.Role;
import dianfan.models.ResultBean;

/**
 * @ClassName AdminLoginService
 * @Description 管理员登陆相关 接口
 * @author sz
 * @date 2018年7月16日 下午1:40:49
 */
public interface AdminLoginService {

	/**
	 * @Title: getAdminInfo
	 * @Description: 验证用户登陆信息
	 * @param account
	 *            账号
	 * @param password
	 *            密码
	 * @return
	 * @throws:
	 * @time: 2018年7月16日 下午1:44:18
	 */
	AdminInfo getAdminInfo(String account, String password);

	/**
	 * @Title: findAdminRole
	 * @Description: 通过用户的ID 获取用户的权限
	 * @param id
	 *            用户的ID
	 * @return String
	 * @throws:
	 * @time: 2018年7月16日 下午2:27:11
	 */
	Role findAdminRole(String id);

	/**
	 * @Title: findAdminPopedom
	 * @Description: 获取角色下的权限
	 * @param role
	 *            角色ID
	 * @return String
	 * @throws:
	 * @time: 2018年7月16日 下午3:23:00
	 */
	List<String> findAdminPopedom(String role);

	/**
	 * @Title: getAdminLoginLog
	 * @Description: 获取用户的登陆日志
	 * @param id
	 *            用户的ID
	 * @return
	 * @throws:
	 * @time: 2018年7月17日 上午11:21:48
	 */
	AdminLoginLog getAdminLoginLog(String id);

	/**
	 * @Title: addUserLoginLog
	 * @Description: 添加用户的登陆日志
	 * @param ip
	 * @param browserName
	 * @param id
	 * @throws:
	 * @time: 2018年7月17日 下午12:17:11
	 */
	void addUserLoginLog(String ip, String browserName, String userid);

	/**
	 * @Title: selectPopedomsPermission
	 * @Description: 获取菜单权限
	 * @param userId
	 *            用户id
	 * @param uri
	 *            请求接口路径
	 * @throws:
	 * @time: 2018年7月19日 下午6:09:16
	 */
	int selectPopedomsPermission(String userId, String uri);

	/**
	 * @Title: addOperateLog
	 * @Description:  添加后台管理员操作日志
	 * @param userid 用户id,记录操作用户
	 * @param popedomid 模块 (模块名称 例如：用户管理，添加用户)
	 * @param roleid 角色
	 * @param operate 操作  (用户名+角色+操作内容)
	 * @param ip ip地址
	 * @throws:
	 * @time: 2018年7月26日 下午5:23:45
	 */
	void addOperateLog(String userid,String popedomid,String roleid,String operate,String ip);

	/**
	 * @Title: getPopedomidByUri
	 * @Description: 获取模块id
	 * @param requestUri
	 * @return
	 * @throws:
	 * @time: 2018年7月27日 上午11:07:26
	 */
	String getPopedomidByUri(String requestUri);

	/**
	 * @Title: getRoleidByUserid
	 * @Description: 获取用户的角色ID
	 * @param userid userid
	 * @return
	 * @throws:
	 * @time: 2018年7月27日 上午11:14:39
	 */
	String getRoleidByUserid(String userid);

	/**
	 * @Title: findOperateLogList
	 * @Description: 获取业务日志列表
	 * @param userid userid
	 * @param page 第几页
	 * @param pageSize 每页第几条
	 * @param name 名字搜索
	 * @param role 角色搜索
	 * @param starttime 开始时间
	 * @param endtime 结束时间
	 * @return ResultBean
	 * @throws:
	 * @time: 2018年7月27日 下午1:32:43
	 */
	ResultBean findOperateLogList(String userid, int page, int pageSize, String name, String role, String starttime,
			String endtime);

}
