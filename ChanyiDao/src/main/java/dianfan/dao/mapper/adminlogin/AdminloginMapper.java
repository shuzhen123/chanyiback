package dianfan.dao.mapper.adminlogin;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import dianfan.entities.loginmanage.AdminInfo;
import dianfan.entities.loginmanage.AdminLoginLog;
import dianfan.entities.loginmanage.OperateLogs;
import dianfan.entities.role.Role;

/**
 * @ClassName AdminloginMapper
 * @Description 管理员登陆dao
 * @author sz
 * @date 2018年7月16日 下午1:51:46
 */
@Repository
public interface AdminloginMapper {

	/**
	 * @Title: getAdminInfo
	 * @Description: 验证用户的登陆信息
	 * @param param
	 * @return
	 * @throws:
	 * @time: 2018年7月16日 下午1:54:33
	 */
	@Select("SELECT id, account, password, entkbn FROM t_admin WHERE account = #{account} AND password = #{password} AND entkbn != 9")
	AdminInfo getAdminInfo(Map<String, Object> param);

	/**
	 * @Title: findAdminRole
	 * @Description: 获取用户的角色
	 * @param id
	 *            用户的ID
	 * @return String
	 * @throws:
	 * @time: 2018年7月16日 下午2:31:45
	 */
	@Select("SELECT r.id  id, r.role_distinguish roleDistinguish, r.role_name roleName FROM t_admin_role ar , t_role r WHERE ar.adminid = #{id} AND ar.roleid = r.id AND r.entkbn = 0 ")
	Role findAdminRole(String id);

	/**
	 * @Title: findAdminPopedom
	 * @Description: 获取角色下的权限ID
	 * @param role
	 *            角色ID
	 * @return String
	 * @throws:
	 * @time: 2018年7月16日 下午3:25:08
	 */
	@Select("SELECT tp.popedomid FROM t_role_popedom rp , t_popedom tp WHERE rp.popedomid = tp.popedomid AND rp.roleid = #{role} AND tp.kind = 01 AND rp.popedom = 1")
	List<String> findAdminPopedom(String role);

	/**
	 * @Title: getAdminLoginLog
	 * @Description: 获取用户的登陆日志
	 * @param id
	 *            用户的ID
	 * @return
	 * @throws:
	 * @time: 2018年7月17日 上午11:23:39
	 */
	@Select("SELECT login_datetime loginDatetime, login_ip loginIp, browser  FROM t_user_login_log WHERE user_id = #{id} ORDER BY login_datetime DESC LIMIT 1,1")
	AdminLoginLog getAdminLoginLog(String id);

	/**
	 * @Title: addUserLoginLog
	 * @Description: 添加用户的登陆日志
	 * @param param
	 * @throws:
	 * @time: 2018年7月17日 下午12:23:37
	 */
	@Insert("INSERT INTO t_user_login_log (log_id, login_datetime, login_ip, user_id, browser) VALUES (#{id}, NOW(), #{ip}, #{userid}, #{browserName})")
	void addUserLoginLog(Map<String, Object> param);

	/**
	 * @Title: selectPopedomsPermission
	 * @Description: 获取菜单权限
	 * @param param
	 *            参数
	 * @return
	 * @throws:
	 * @time: 2018年7月19日 下午6:21:13
	 */
	@Select("SELECT count(*) FROM t_admin_role ar INNER JOIN t_role_popedom rp ON ar.roleid = rp.roleid INNER JOIN t_popedom p ON rp.popedomid = p.popedomid AND p.kind = '01' AND p.popedomfatherid is not null WHERE p.popedomurl = #{uri} AND ar.adminid = #{userId}")
	int selectPopedomsPermission(Map<String, Object> param);

	/**
	 * @Title: getRoleidByUri
	 * @Description: 获取模块ID
	 * @param requestUri
	 * @return
	 * @throws:
	 * @time: 2018年7月27日 上午11:09:30
	 */
	@Select(" SELECT popedomid FROM t_popedom WHERE popedomurl = #{requestUri} AND kind = 01 ")
	String getRoleidByUri(String requestUri);

	/**
	 * @Title: getRoleidByUserid
	 * @Description: 获取用户的角色ID
	 * @param userid userid
	 * @return
	 * @throws:
	 * @time: 2018年7月27日 上午11:15:51
	 */
	@Select(" SELECT roleid FROM t_admin_role WHERE adminid = #{userid} ")
	String getRoleidByUserid(String userid);

	/*=======================================系统日志相关=========================================*/
	
	/**
	 * @Title: findOperateLogCount
	 * @Description: 获取业务日志列表数量
	 * @param param
	 * @return
	 * @throws:
	 * @time: 2018年7月27日 下午2:00:32
	 */
	int findOperateLogCount(Map<String, Object> param);

	/**
	 * @Title: findOperateLogList
	 * @Description: 获取业务日志列表
	 * @param param
	 * @return
	 * @throws:
	 * @time: 2018年7月27日 下午2:00:39
	 */
	List<OperateLogs> findOperateLogList(Map<String, Object> param);

}
