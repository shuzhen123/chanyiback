/**  
* @Title: UserRoleMapper.java
* @Package dianfan.dao.mapper.userrole
* @Description: TODO
* @author yl
* @date 2018年7月24日 上午11:00:38
* @version V1.0  
*/ 
package dianfan.dao.mapper.userrole;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import dianfan.entities.role.BgUserRoleModel;
import dianfan.entities.role.Role;

/** @ClassName UserRoleMapper
 * @Description 
 * @author yl
 * @date 2018年7月24日 上午11:00:38
 */
@Repository
public interface UserRoleMapper {
	
	/**
	 * @Title: getUserRoleNum 
	 * @Description:  统计用户角色个数
	 * @param burm
	 * @return
	 * @throws:
	 * @time: 2018年7月24日 上午11:09:56
	 */
	int getUserRoleNum(BgUserRoleModel burm);
	/**
	 * @Title: findUserRoleList
	 * @Description: 获取用户角色列表
	 * @param burm
	 * @return
	 * @throws:
	 * @time: 2018年7月24日 上午11:11:16
	 */
	List<Role> findUserRoleList();
	/**
	 * @Title: getRoleUserNum
	 * @Description:  获取角色下的用户数量
	 * @param roleid 角色id
	 * @return
	 * @throws:
	 * @time: 2018年7月27日 下午12:51:54
	 */
	@Select("select count(*) from t_user_role where roleid=#{roleid}")
	int getRoleUserNum(String roleid);
	
	/**
	 * @Title: findPopedomids
	 * @Description:  获取权限id列表
	 * @param roleid 角色id
	 * @return
	 * @throws:
	 * @time: 2018年7月27日 下午12:56:06
	 */
	@Select("select tp.popedomid from t_m_role_popedom tmrp inner join t_popedom tp on tmrp.popedomid=tp.popedomid where tmrp.roleid=#{roleid} and tp.kind='02'")
	List<String> findPopedomids(String roleid);
	/**
	 * @Title: findUserDiscountList
	 * @Description: 
	 * @return
	 * @throws:
	 * @time: 2018年7月25日 下午5:50:46
	 */
	@Select("select mr.id,mr.role_distinguish roleDistinguish,mr.role_name roleName,mr.role_description roleDescription,count(mr.id) rolenum,group_concat(tmrp.popedomid) popedomid from m_role mr left join t_m_role_popedom tmrp on mr.id=tmrp.roleid left join t_user_role tur on mr.id=tur.roleid where mr.entkbn=0 group by mr.id")
	List<Role> findUserDiscountList();
	/**
	 * @Title: updateUserDiscount
	 * @Description: 
	 * @param rl
	 * @throws:
	 * @time: 2018年7月25日 下午5:58:35
	 */
	void updateUserDiscount(Role rl);

}
