package dianfan.dao.mapper.userManage;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import dianfan.entities.UserInfo;

/**
 * @ClassName UserManageMapper
 * @Description 后台用户管理 dao
 * @author sz
 * @date 2018年7月23日 上午11:19:57
 */
@Repository
public interface UserManageMapper {

	/**
	 * @param param 
	 * @Title: findUserCount
	 * @Description: 获取用户列表 数量
	 * @return
	 * @throws:
	 * @time: 2018年7月23日 上午11:45:40
	 */
	int findUserCount(Map<String, Object> param);

	/**
	 * @Title: findUserList
	 * @Description: 获取用户列表 
	 * @param param
	 * @return
	 * @throws:
	 * @time: 2018年7月23日 下午12:03:07
	 */
	List<UserInfo> findUserList(Map<String, Object> param);

	/**
	 * @Title: updataUserPwd
	 * @Description: 重置用户密码
	 * @param param
	 * @throws:
	 * @time: 2018年7月23日 下午2:11:06
	 */
	@Update(" UPDATE t_user_userinfo SET pwd = #{newPwd} ,update_time = NOW() ,update_by = #{userid} WHERE id = #{id} ")
	void updataUserPwd(Map<String, Object> param);

	/**
	 * @Title: updataUserTelno
	 * @Description: 重置用户手机号
	 * @param param
	 * @throws:
	 * @time: 2018年7月23日 下午2:41:17
	 */
	@Update(" UPDATE t_user_userinfo SET telno = #{telno} ,update_time = NOW() ,update_by = #{userid} WHERE id = #{id} ")
	void updataUserTelno(Map<String, Object> param);

	/**
	 * @Title: updataUserType
	 * @Description: 更新用户状态
	 * @param param
	 * @throws:
	 * @time: 2018年7月23日 下午2:55:43
	 */
	void updataUserType(Map<String, Object> param);

	
	
}
