package dianfan.service.adminmanage;

import dianfan.models.ResultBean;

/**
 * @ClassName AdminRoleService
 * @Description 用户角色相关 接口
 * @author sz
 * @date 2018年7月17日 下午6:20:06
 */
public interface AdminRoleService {

	/**
	 * @Title: findadmiRolenList
	 * @Description: 获取管理员角色列表
	 * @return  ResultBean
	 * @throws:
	 * @time: 2018年7月17日 下午6:28:06
	 */
	ResultBean findadmiRolenList();

	/**
	 * @Title: addAdmiRolen
	 * @Description: 添加管理员角色
	 * @param distinguish 角色区分
	 * @param name 角色名称
	 * @param description 创建时间
	 * @param roleids 权限ID
	 * @return
	 * @throws:
	 * @time: 2018年7月17日 下午7:02:41
	 */
	ResultBean addAdmiRolen(String distinguish, String name, String description,String roleids);

	/**
	 * @Title: updataAdmiRolen
	 * @Description: 修改管理员角色
	 * @param distinguish 角色区分
	 * @param name 角色名称
	 * @param description 创建时间
	 * @param roleids 权限ID
	 * @param userid 登陆者id
	 * @return
	 * @throws:
	 * @time: 2018年7月18日 上午9:47:45
	 */
	ResultBean updataAdmiRolen(String distinguish, String name, String description, String roleids, String userid,String id);

	/**
	 * @Title: delAdmiRolen
	 * @Description: 删除角色
	 * @param userid 用户的id
	 * @param ids 需要删除的角色ID
	 * @return
	 * @throws:
	 * @time: 2018年7月18日 上午11:08:07
	 */
	ResultBean delAdmiRolen(String userid, String ids);

	/**
	 * @Title: findAdminPopedom
	 * @Description: 获取管理员权限列表 
	 * @return
	 * @throws:
	 * @time: 2018年7月18日 下午2:26:50
	 */
	ResultBean findAdminPopedom();

}
