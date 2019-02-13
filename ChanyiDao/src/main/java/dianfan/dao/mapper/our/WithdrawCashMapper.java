/**  
* @Title: WithdrawCashMapper.java
* @Package dianfan.dao.mapper.our
* @Description: TODO
* @author yl
* @date 2018年7月2日 上午10:39:13
* @version V1.0  
*/ 
package dianfan.dao.mapper.our;

import org.springframework.stereotype.Repository;

import dianfan.entities.our.WithdrawCashModel;

/** @ClassName WithdrawCashMapper
 * @Description 
 * @author yl
 * @date 2018年7月2日 上午10:39:13
 */
@Repository
public interface WithdrawCashMapper {
	
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
	void addWithdrawCash(WithdrawCashModel wcm);

}
