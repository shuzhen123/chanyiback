/**  
* @Title: UserRoleService.java
* @Package dianfan.service.userrole
* @Description: TODO
* @author yl
* @date 2018年7月24日 上午10:57:23
* @version V1.0  
*/ 
package dianfan.service.userrole;

import dianfan.models.ResultBean;

/** @ClassName UserRoleService
 * @Description 
 * @author yl
 * @date 2018年7月24日 上午10:57:23
 */
public interface UserRoleService {
	/**
	 * @Title: findUserRoleList
	 * @Description: 
	 * @return
	 * @throws:
	 * @time: 2018年7月24日 下午1:04:15
	 */
	ResultBean findUserRoleList();
	/**
	 * @Title: findUserDiscountList
	 * @Description: 
	 * @return
	 * @throws:
	 * @time: 2018年7月25日 下午5:47:19
	 */
	ResultBean findUserDiscountList();
	/**
	 * @Title: updateUserDiscount
	 * @Description: 
	 * @param id
	 * @param salediscount
	 * @throws:
	 * @time: 2018年7月25日 下午5:55:27
	 */
	void updateUserDiscount(String id,String salediscount);

}
