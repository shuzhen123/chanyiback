package dianfan.service.adminmanage;

import java.util.List;

import dianfan.models.ResultBean;

/**
 * @ClassName AdminService
 * @Description 管理员相关service 接口
 * @author sz
 * @date 2018年7月16日 下午4:56:17
 */
public interface AdminService {

	/**
	 * @Title: findAdminList
	 * @Description:  获取管理员列表
	 * @param start 开始条数
	 * @param length 分页偏移量
	 * @param page 第几页
	 * @return
	 * @throws:
	 * @time: 2018年7月17日 上午9:51:53
	 */
	ResultBean findAdminList(int length, int page);

	/**
	 * @Title: addAdmin
	 * @Description: 添加管理员
	 * @param account 账号
	 * @param password 密码
	 * @param role 角色
	 * @return
	 * @throws:
	 * @time: 2018年7月17日 下午3:35:36
	 */
	ResultBean addAdmin(String account, String password, String role,String userid);

	/**
	 * @Title: updataAdmin
	 * @Description: 修改管理员
	 * @param account 账号
	 * @param password 密码
	 * @param role 角色
	 * @return 
	 * @throws: 
	 * @time: 2018年7月17日 下午4:04:33
	 */
	ResultBean updataAdmin(String account, String password, String role,String adminid,String loginid);

	/**
	 * @Title: delAdmin
	 * @Description: 修改管理员状态 
	 * @param adminid
	 * @param flag 
	 * @throws:
	 * @time: 2018年7月17日 下午4:33:22
	 */
	void delAdmin(List<String> adminid, String flag, String userid);


	
	
	
	
	
	
	
}
