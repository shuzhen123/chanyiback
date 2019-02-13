package dianfan.service.usermanage;

import dianfan.models.ResultBean;

/**
 * @ClassName BasicsService
 * @Description 系统数据
 * @author sz
 * @date 2018年7月23日 下午3:11:14
 */
public interface BasicsService {

	/**
	 * @Title: findBasicsData
	 * @Description: 获取系统数据
	 * @return
	 * @throws:
	 * @time: 2018年7月23日 下午3:37:20
	 */
	ResultBean findBasicsData(String userid);

	/**
	 * @Title: findSettingSwitchs
	 * @Description: 获取设置开关
	 * @param userid
	 * @return
	 * @throws:
	 * @time: 2018年7月24日 上午9:29:08
	 */
	ResultBean findSettingSwitchs(String userid);

	/**
	 * @Title: findPopedomList
	 * @Description: 获取权限列表 自用
	 * @param userid
	 * @return
	 * @throws:
	 * @time: 2018年7月24日 上午10:02:50
	 */
	ResultBean findPopedomList(String userid);

	/**
	 * @Title: addAdminPopedoms
	 * @Description: 添加管理员权限自用
	 * @param userid 用户ID
	 * @param popedomname 权限名称
	 * @param popedomfatherid 权限父ID
	 * @param popedomurl 树的连接路径
	 * @param sort 排序
	 * @param kind kind
	 * @return
	 * @throws:
	 * @time: 2018年7月24日 下午3:09:40
	 */
	ResultBean addAdminPopedoms(String userid, String popedomname, String popedomfatherid, String popedomurl,
			String sort, String kind);

	/**
	 * @Title: updataAdminPopedoms
	 * @Description: 修改管理员权限自用
	 * @param userid 用户ID
	 * @param popedomname 权限名称
	 * @param popedomfatherid 权限父ID
	 * @param popedomurl 树的连接路径
	 * @param sort 排序
	 * @param kind kind
	 * @param id id
	 * @return
	 * @throws:
	 * @time: 2018年7月24日 下午3:43:59
	 */
	ResultBean updataAdminPopedoms(String userid, String popedomname, String popedomfatherid, String popedomurl,
			String sort, String kind, String id);

	/**
	 * @Title: delAdminPopedoms
	 * @Description: 删除权限 自用
	 * @param userid 管理员Id
	 * @param ids 权限ID
	 * @return
	 * @throws:
	 * @time: 2018年7月24日 下午4:00:41
	 */
	ResultBean delAdminPopedoms(String userid, String ids);

	/**
	 * @Title: editSettingSwitchs
	 * @Description: 修改系统设置 
	 * @param userid 用户ID
	 * @param ids 设置开关Id
	 * @return
	 * @throws:
	 * @time: 2018年7月24日 下午4:48:07
	 */
	ResultBean updataeditSettingSwitchs(String userid, String ids ,int flag, String des);
	
	
	

}
