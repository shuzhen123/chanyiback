/**  
* @Title: UserRolePopedomMapper.java
* @Package dianfan.dao.mapper.urpopedom
* @Description: TODO
* @author yl
* @date 2018年7月25日 上午10:19:46
* @version V1.0  
*/ 
package dianfan.dao.mapper.urpopedom;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import dianfan.entities.role.AdminPopedom;

/** @ClassName UserRolePopedomMapper
 * @Description 
 * @author yl
 * @date 2018年7月25日 上午10:19:46
 */
@Repository
public interface UserRolePopedomMapper {
	
	/**
	 * @Title: getUrpopedomNum
	 * @Description: 获取员工权限数量
	 * @param urpm
	 * @return
	 * @throws:
	 * @time: 2018年7月25日 上午10:39:01
	 */
	int getUrpopedomNum();
	
	/**
	 * @Title: findUrpopedomList
	 * @Description: 获取员工权限列表
	 * @param urpm
	 * @return
	 * @throws:
	 * @time: 2018年7月25日 上午10:40:13
	 */
	List<AdminPopedom> findUrpopedomList();
	
	/**
	 * @Title: deleteUserRolePopedom
	 * @Description:  删除角色和权限的关系
	 * @param roleid
	 * @throws:
	 * @time: 2018年7月26日 下午1:30:02
	 */
	@Delete("delete from t_m_role_popedom where roleid = #{roleid}")
	void deleteUserRolePopedom(String roleid);
	/**
	 * @Title: updateBgUserRolePopedom
	 * @Description: 修改员工角色权限
	 * @param mrpid 角色与权限表id
	 * @param popedomid 权限表id
	 * @throws:
	 * @time: 2018年7月25日 下午1:49:12
	 */
	void updateBgUserRolePopedom(Map<String, Object> param);
	/**
	 * @Title: updateRoleDescription
	 * @Description: 修改角色描述
	 * @param param
	 * @throws:
	 * @time: 2018年7月26日 下午1:56:54
	 */
	@Update("update m_role set role_description = #{description} where id=#{id}")
	void updateRoleDescription(@Param("id") String id,@Param("description") String description);

}
