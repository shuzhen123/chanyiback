package dianfan.dao.mapper.adminmanage;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import dianfan.entities.role.AdminPopedom;
import dianfan.entities.role.RolePopedom;
import dianfan.entities.role.TRole;

@Repository
public interface AdminRoleMapper {


	/**
	 * @Title: findadmiRolenCount
	 * @Description: 获取角色数量
	 * @return 
	 * @throws:
	 * @time: 2018年7月17日 下午6:35:55
	 */
	@Select("SELECT COUNT(*) FROM t_role WHERE entkbn = 0")
	int findadmiRolenCount();

	/**
	 * @Title: findadmiRolenList
	 * @Description: 获取角色列表
	 * @return Role
	 * @throws:
	 * @time: 2018年7月17日 下午6:48:27
	 */
	@Select("SELECT id, role_distinguish roleDistinguish, role_name roleName,role_description roleDescription, create_time createTime,entkbn entkbn FROM t_role WHERE entkbn = 0")
	List<TRole> findadmiRolenList();

	/**
	 * @Title: getAdmiRolen
	 * @Description: 判断是否存在该角色
	 * @param param
	 * @return
	 * @throws:
	 * @time: 2018年7月17日 下午7:06:15
	 */
	@Select("SELECT COUNT(*) FROM t_role WHERE entkbn = 0 AND role_distinguish = #{distinguish} AND role_name = #{name} ")
	int getAdmiRolen(Map<String, Object> param);

	/**
	 * @Title: addAdmiRolen
	 * @Description: 添加角色
	 * @param param
	 * @throws:
	 * @time: 2018年7月17日 下午7:26:58
	 */
	@Insert("INSERT INTO t_role (id,role_distinguish,role_name,create_time,role_description) VALUES (#{id},#{distinguish},#{name},NOW(),#{description}) ")
	void addAdmiRolen(Map<String, Object> param);

	/**
	 * @param param 
	 * @Title: addAdmiRolenPopedom
	 * @Description: 添加角色下的权限
	 * @throws:
	 * @time: 2018年7月18日 上午9:35:17
	 */
	void addAdmiRolenPopedom(Map<String, Object> param);

	/**
	 * @Title: getUserRole
	 * @Description: 获取登陆者的角色
	 * @param id 登陆者的ID
	 * @return
	 * @throws:
	 * @time: 2018年7月18日 上午9:59:42
	 */
	@Select("SELECT roleid FROM t_admin_role WHERE adminid = #{userid} ")
	String getUserRole(String userid);

	/**
	 * @Title: updataAdmiRolen
	 * @Description: 修改角色的基本信息
	 * @param param
	 * @throws:
	 * @time: 2018年7月18日 上午10:03:47
	 */
	@Update("UPDATE t_role  SET role_distinguish = #{distinguish}, role_name = #{name}, role_description = #{description} WHERE id = #{id} ")
	void updataAdmiRolen(Map<String, Object> param);

	/**
	 * @Title: updataAdmiRolenPopedom
	 * @Description: 修改角色下的权限
	 * @param param
	 * @throws:
	 * @time: 2018年7月18日 上午10:27:55
	 */
	//void updataAdmiRolenPopedom(Map<String, Object> param);

	/**
	 * @Title: delAdmiRolenPopedom
	 * @Description: 删除角色下权限
	 * @param id 角色ID
	 * @throws:
	 * @time: 2018年7月18日 上午10:45:33
	 */
	@Delete("DELETE FROM t_role_popedom WHERE roleid = #{id} ")
	void delAdmiRolenPopedom(String id);

	/**
	 * @Title: updataFreezeAdmin
	 * @Description: 冻结角色下的管理员
	 * @param ids 角色ID
	 * @throws:
	 * @time: 2018年7月18日 上午11:17:01
	 */
	void updataFreezeAdmin(List<String> user);

	/**
	 * @Title: findUseridByrole
	 * @Description: 获取角色下下的管理员
	 * @param idList
	 * @return
	 * @throws:
	 * @time: 2018年7月18日 上午11:57:02
	 */
	List<String> findUseridByrole(List<String> idList);

	/**
	 * @Title: delAdminRole
	 * @Description: 接触角色与管理员的关系
	 * @param idList
	 * @throws:
	 * @time: 2018年7月18日 下午12:11:42
	 */
	void delAdminRole(List<String> idList);

	/**
	 * @Title: delRolen
	 * @Description: 删除角色
	 * @param idList 角色id
	 * @throws:
	 * @time: 2018年7月18日 下午12:17:05
	 */
	void delRolen(List<String> idList);

	/**
	 * @Title: findRolePopedom
	 * @Description: 获取角色下的权限ID
	 * @param releid
	 * @return
	 * @throws:
	 * @time: 2018年7月18日 下午1:46:59
	 */
	List<RolePopedom> findRolePopedom(List<String> releid);

	/**
	 * @Title: findAdminPopedomCount
	 * @Description: 获取权限列表总数量
	 * @return int
	 * @throws:
	 * @time: 2018年7月18日 下午2:29:10
	 */
	@Select("SELECT COUNT(*) FROM t_popedom WHERE ISNULL(popedomfatherid) AND kind = 01 ORDER BY sort")
	int findAdminPopedomCount();

	/**
	 * @Title: findAdminPopedomList
	 * @Description: 获取权限列表
	 * @return
	 * @throws:
	 * @time: 2018年7月18日 下午2:37:33
	 */
	@Select("SELECT popedomid, popedomname, sort FROM t_popedom WHERE ISNULL(popedomfatherid) AND kind = 01 ORDER BY sort")
	List<AdminPopedom> findAdminPopedomList();

	


	

}
