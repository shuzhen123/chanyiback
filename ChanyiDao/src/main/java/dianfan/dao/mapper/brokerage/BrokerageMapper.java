package dianfan.dao.mapper.brokerage;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import dianfan.entities.commission.UserBounsDetail;
import dianfan.entities.order.UserWithdrawDepositStream;

/**
 * @ClassName BrokerageMapper
 * @Description  易盟佣金流水相关 dao
 * @author sz
 * @date 2018年7月25日 下午1:50:00
 */
@Repository
public interface BrokerageMapper {

	
	/**
	 * @Title: findBrokerageCount
	 * @Description: 获取佣金流水列表总数
	 * @param param
	 * @return
	 * @throws:
	 * @time: 2018年7月25日 下午2:39:19
	 */
	int findBrokerageCount(Map<String, Object> param);

	/**
	 * @Title: findBrokerageList
	 * @Description: 获取佣金流水列表
	 * @param param
	 * @return
	 * @throws:
	 * @time: 2018年7月25日 下午2:47:44
	 */
	List<UserBounsDetail> findBrokerageList(Map<String, Object> param);

	/**
	 * @Title: findWithdrawDepositCount
	 * @Description: 获取用户提现流水表数量
	 * @param param
	 * @return
	 * @throws:
	 * @time: 2018年7月25日 下午4:21:04
	 */
	int findWithdrawDepositCount(Map<String, Object> param);

	/**
	 * @Title: findWithdrawDepositList
	 * @Description: 获取用户提现流水表
	 * @param param
	 * @return
	 * @throws:
	 * @time: 2018年7月25日 下午4:21:13
	 */
	List<UserWithdrawDepositStream> findWithdrawDepositList(Map<String, Object> param);

	/**
	 * @Title: updatawithdrawalApproval
	 * @Description: 审批操作
	 * @param param
	 * @throws:
	 * @time: 2018年7月25日 下午5:36:34
	 */
	void updatawithdrawalApproval(Map<String, Object> param);

}
