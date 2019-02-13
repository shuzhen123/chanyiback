/**  
* @Title: UserRolePopedomService.java
* @Package dianfan.service.urpopedom
* @Description: TODO
* @author yl
* @date 2018年7月25日 上午10:17:56
* @version V1.0  
*/ 
package dianfan.service.urpopedom;

import dianfan.models.ResultBean;

/** @ClassName UserRolePopedomService
 * @Description 
 * @author yl
 * @date 2018年7月25日 上午10:17:56
 */
public interface UserRolePopedomService {
	
	/**
	 * @Title: findUrpopedomList
	 * @Description: 
	 * @return
	 * @throws:
	 * @time: 2018年7月25日 上午10:42:05
	 */
	ResultBean findUrpopedomList();
	/**
	 * @Title: updateBgUserRolePopedom
	 * @Description: 修改员工角色权限
	 * @param mrpid 角色与权限表id
	 * @param popedomid 权限表id
	 * @throws:
	 * @time: 2018年7月25日 下午1:49:12
	 */
	void updateBgUserRolePopedom(String mrpid,String description,String popedom,String popedomid);

}
