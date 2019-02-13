/**  
* @Title: CommissionService.java
* @Package dianfan.service.our
* @Description: TODO
* @author yl
* @date 2018年6月30日 下午12:41:59
* @version V1.0  
*/ 
package dianfan.service.our;

import java.util.List;

import dianfan.entities.our.MoneyEntryExit;
import dianfan.models.ResultBean;

/** @ClassName CommissionService
 * @Description 佣金逻辑层
 * @author yl
 * @date 2018年6月30日 下午12:41:59
 */
public interface CommissionService {
	
	/**
	 * @Title: findCommissionList
	 * @Description: 用户佣金列表
	 * @param userid 用户id
	 * @return 用户佣金列表
	 * @throws:
	 * @time: 2018年6月30日 下午12:43:05
	 */
	List<MoneyEntryExit> findUserMoneyEntryExit(String userid);
	/**
	 * @Title: findCommissionList
	 * @Description: 
	 * @return
	 * @throws:
	 * @time: 2018年7月25日 下午3:38:19
	 */
	ResultBean findCommissionList();
	/**
	 * @Title: updateCommission
	 * @Description: 
	 * @param id
	 * @param proportion
	 * @throws:
	 * @time: 2018年7月25日 下午5:13:25
	 */
	void updateCommission(String userid,String id,String proportion);

}
