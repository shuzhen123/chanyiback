/**  
* @Title: WithdrawCashServiceImpl.java
* @Package dianfan.service.our.impl
* @Description: TODO
* @author yl
* @date 2018年7月2日 上午10:29:09
* @version V1.0  
*/ 
package dianfan.service.our.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dianfan.dao.mapper.our.WithdrawCashMapper;
import dianfan.entities.our.WithdrawCashModel;
import dianfan.service.our.WithdrawCashService;

/** @ClassName WithdrawCashServiceImpl
 * @Description 提现service 实现
 * @author yl
 * @date 2018年7月2日 上午10:29:09
 */
@Service
public class WithdrawCashServiceImpl implements WithdrawCashService{
	
	@Autowired
	private WithdrawCashMapper withdrawCashMapper;

	/* (non-Javadoc)
	 * <p>Title: addWithdrawCash</p>
	 * <p>Description: </p>
	 * @param userid 用户id
	 * @param money 提现金额
	 * @param applystatus 提现状态
	 * @param bankstatus 提现方式（银行卡）
	 * link: @see dianfan.service.our.WithdrawCashService#addWithdrawCash(java.lang.String, java.lang.Double, java.lang.String, java.lang.String)
	 */ 
	@Override
	@Transactional
	public void addWithdrawCash(String userid, BigDecimal money, String applystatus,String bankstatus,String bankno,String withdrawer) {
		// TODO Auto-generated method stub
		WithdrawCashModel wcm = new WithdrawCashModel();
		wcm.setMoney(money);
		wcm.setUserId(userid);
		wcm.setApplyStatus(applystatus);
		wcm.setBankStatus(bankstatus);
		wcm.setCardNo(bankno);
		wcm.setCreateBy(userid);
		wcm.setRealName(withdrawer);
		withdrawCashMapper.addWithdrawCash(wcm);
		
	}

}
