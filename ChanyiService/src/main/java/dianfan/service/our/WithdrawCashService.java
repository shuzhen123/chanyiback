/**  
* @Title: WithdrawCashService.java
* @Package dianfan.service.our
* @Description: TODO
* @author yl
* @date 2018年7月2日 上午10:11:12
* @version V1.0  
*/ 
package dianfan.service.our;

import java.math.BigDecimal;

/** @ClassName WithdrawCashService
 * @Description 提现service(银行卡)
 * @author yl
 * @date 2018年7月2日 上午10:11:12
 */
public interface WithdrawCashService {
	
	/**
	 * @Title: addWithdrawCash
	 * @Description: 添加提现
	 * @param userid 用户id
	 * @param money 提现金额
	 * @param applystatus 提现状态
	 * @param bankstatus 提现方式
	 * @throws:
	 * @time: 2018年7月2日 上午10:20:20
	 */
	void addWithdrawCash(String userid,BigDecimal money,String applystatus,String bankstatus,String bankno,String withdrawer);

}
