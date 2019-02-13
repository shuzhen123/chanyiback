package dianfan.service.usermanage;

import dianfan.models.ResultBean;

/**
 * @ClassName UserManage
 * @Description 后台用户管理 接口
 * @author sz
 * @date 2018年7月23日 上午11:16:35
 */
public interface UserServiceManage {

	
	/**
	 * @Title: findUserList
	 * @Description: 获取用户列表
	 * @param areacode 
	 * @param sex 
	 * @param role 
	 * @return
	 * @throws:
	 * @time: 2018年7月23日 上午11:28:05
	 */
	ResultBean findUserList(String userid, int page, int pageSize, String name, String telno, String source,
			String starttime, String endtime, String role, String sex, String areacode,Integer entkbn);

	/**
	 * @Title: updataUserPwd
	 * @Description: 修改用户的密码
	 * @param userid 用户id
	 * @param id 修改的ID
	 * @param newPwd  新密码
	 * @return
	 * @throws:
	 * @time: 2018年7月23日 下午2:09:06
	 */
	ResultBean updataUserPwd(String userid, String id, String newPwd);
	
	/**
	 * @Title: updataUserPwd
	 * @Description: 修改用户的手机号
	 * @param userid 用户id
	 * @param id 修改的ID
	 * @param telno  新手机号
	 * @return
	 * @throws:
	 * @time: 2018年7月23日 下午2:09:06
	 */
	ResultBean updataUserTelno(String userid, String id, String telno);

	/**
	 * @Title: updataUserType
	 * @Description: 修改用户的状态
	 * @param userid 
	 * @param ids
	 * @param type
	 * @return
	 * @throws:
	 * @time: 2018年7月23日 下午2:49:31
	 */
	ResultBean updataUserType(String userid, String ids, int type);
	
	
	

}
