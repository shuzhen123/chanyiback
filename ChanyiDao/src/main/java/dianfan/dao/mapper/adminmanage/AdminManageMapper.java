package dianfan.dao.mapper.adminmanage;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import dianfan.entities.loginmanage.AdminInfo;

/**
 * @ClassName AdminManageMapper
 * @Description 管理员相关dao
 * @author sz
 * @date 2018年7月16日 下午4:59:09
 */
@Repository
public interface AdminManageMapper {

	/**
	 * @Title: findfindAdminCount
	 * @Description: 查询管理员  总数
	 * @return int
	 * @throws:
	 * @time: 2018年7月17日 上午11:07:22
	 */
	@Select("SELECT COUNT(*) FROM t_admin admin LEFT JOIN t_admin_role tar ON admin.id = tar.adminid LEFT JOIN t_role role ON tar.roleid = role.id "
			+ "WHERE admin.entkbn != 9  ")
	int findfindAdminCount();

	/**
	 * @Title: findfindAdminList
	 * @Description: 查询管理员列表 
	 * @param param
	 * @return AdminInfo
	 * @throws:
	 * @time: 2018年7月17日 下午2:02:00
	 */
	@Select("SELECT admin.id, admin.account, admin.entkbn, role.role_name roleName, role.id roleId FROM t_admin admin LEFT JOIN t_admin_role tar ON admin.id = tar.adminid LEFT JOIN t_role role ON tar.roleid = role.id "
			+ "WHERE admin.entkbn != 9 ORDER BY admin.create_time LIMIT #{start},#{length} ")
	List<AdminInfo> findfindAdminList(Map<String, Object> param);

	/**
	 * @Title: findfindAdminLog
	 * @Description: 查询用户的最近一此的登陆信息
	 * @param userid 用户的IDs
	 * @return
	 * @throws:
	 * @time: 2018年7月17日 下午2:34:01
	 */
	List<AdminInfo> findfindAdminLog(List<String> userid);

	/**
	 * @Title: getCheckAccount
	 * @Description: 判断账号是否存在
	 * @param account 账号 
	 * @return int
	 * @throws:
	 * @time: 2018年7月17日 下午3:41:58
	 */
	@Select("SELECT COUNT(*) FROM t_admin WHERE entkbn != 9 AND account = #{account} ")
	int getCheckAccount(String account);

	/**
	 * @Title: addAdmin
	 * @Description: 添加新的管理员
	 * @param param
	 * @throws:
	 * @time: 2018年7月17日 下午3:46:22
	 */
	@Insert("INSERT INTO t_admin (id,account,password,create_time,create_by) VALUES (#{id}, #{account}, #{password}, NOW(), #{userid})")
	void addAdmin(Map<String, Object> param);

	/**
	 * @Title: addAdminRole
	 * @Description: 添加管理员的角色
	 * @param param 
	 * @throws:
	 * @time: 2018年7月17日 下午3:49:37
	 */
	@Insert("INSERT INTO t_admin_role (id,adminid,roleid) VALUES (REPLACE(uuid(),'-',''), #{id}, #{role})")
	void addAdminRole(Map<String, Object> param);
	
	
	
	/**
	 * @Title: updataAdmin
	 * @Description: 修改管理员
	 * @param param
	 * @throws:
	 * @time: 2018年7月17日 下午4:09:11
	 */
	@Update("UPDATE t_admin SET password = #{password}, update_time = NOW(), update_by = #{loginid} WHERE id = #{adminid} ")
	void updataAdmin(Map<String, Object> param);

	/**
	 * @Title: updataAdminRole
	 * @Description: 修改管理员的角色
	 * @param param
	 * @throws:
	 * @time: 2018年7月17日 下午4:11:09
	 */
	@Update("UPDATE t_admin_role SET roleid = #{role} WHERE adminid = #{adminid} ")
	void updataAdminRole(Map<String, Object> param);

	/**
	 * @Title: getAdminInfo
	 * @Description: 获取用户的信息
	 * @param account 账号
	 * @return
	 * @throws:
	 * @time: 2018年7月17日 下午4:12:51
	 */
	@Select("SELECT id FROM t_admin WHERE id = #{id} AND entkbn != 9")
	AdminInfo getAdminInfo(String id);

	/**
	 * @Title: delAdmin
	 * @Description: 删除管理员
	 * @param param 管理员ID
	 * @throws:
	 * @time: 2018年7月17日 下午4:35:38
	 */
	void delAdmin(Map<String, Object> param);

	/**
	 * @Title: findAdminRoles
	 * @Description: 确认该管理员是否存在角色
	 * @param adminid 管理员
	 * @return int 
	 * @throws:
	 * @time: 2018年7月19日 下午1:35:49
	 */
	@Select("SELECT COUNT(*) FROM t_admin_role WHERE adminid = #{adminid} ")
	int findAdminRoles(String adminid);

	/**
	 * @Title: addCreationAdminRole
	 * @Description： 更新用户角色时，发现用户没有角色，直接增加
	 * @param param
	 * @throws:
	 * @time: 2018年7月19日 下午1:51:28
	 */
	@Insert("INSERT INTO t_admin_role (id,adminid,roleid) VALUES (REPLACE(uuid(),'-',''), #{adminid}, #{role})")
	void addCreationAdminRole(Map<String, Object> param);

	/**
	 * @Title: addNewLogin
	 * @Description: 新创建的用户给一个初始登陆信息
	 * @param param
	 * @throws:
	 * @time: 2018年7月19日 下午3:19:55
	 */
	@Insert("INSERT INTO t_user_login_log (log_id,login_datetime,login_ip,user_id) VALUES (REPLACE(uuid(),'-',''),NOW(),'0.0.0.0',#{id})")
	void addNewLogin(Map<String, Object> param);

	/**
	 * @Title: getCheckoOldRole
	 * @Description: 查询登陆者原来的角色
	 * @param loginid 登陆者的ID
	 * @return
	 * @throws:
	 * @time: 2018年7月19日 下午5:45:17
	 */
	@Select("SELECT roleid FROM t_admin_role WHERE adminid = #{loginid}")
	String getCheckoOldRole(String loginid);

	/**
	 * @Title: findfindAdminLogBy
	 * @Description: 获取用户的上次的信息
	 * @param id 用户id
	 * @return
	 * @throws:
	 * @time: 2018年7月24日 上午10:52:21
	 */
	@Select(" SELECT login_datetime loginDatetime, login_ip loginIp,browser FROM t_user_login_log WHERE user_id = #{id} ORDER BY login_datetime DESC LIMIT 1 ")
	AdminInfo findfindAdminLogBy(String id);
	
	
	
	
	
	
	
}
