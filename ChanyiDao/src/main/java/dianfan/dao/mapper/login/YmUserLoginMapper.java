package dianfan.dao.mapper.login;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import dianfan.entities.UserInfo;
import dianfan.entities.role.Role;

/**
 * @ClassName YmUserLoginMapper
 * @Description 易盟用户登录dao
 * @author cjy
 * @date 2018年9月17日 下午2:25:10
 */
@Repository
public interface YmUserLoginMapper {

	/**
	 * @Title: getYmUserInfo
	 * @Description: 根据手机号密码获取用户信息
	 * @param phone
	 * @param password
	 * @return
	 * @throws:
	 * @time: 2018年9月17日 下午2:44:56
	 * @author cjy
	 */
	UserInfo getYmUserInfo(@Param("phone")String phone, @Param("password")String password);

	/**
	 * @Title: findYmUserRole
	 * @Description: 获取用户角色对应的权限
	 * @param id
	 * @return
	 * @throws:
	 * @time: 2018年9月17日 下午2:46:21
	 * @author cjy
	 */
	@Select("select group_concat(rp.popedomid) from t_user_role ur, t_m_role_popedom rp where ur.roleid=rp.roleid and ur.userid=#{id}")
	String getYmUserRolePower(String id);

}
