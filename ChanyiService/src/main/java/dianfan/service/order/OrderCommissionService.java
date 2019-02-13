package dianfan.service.order;

/**
 * @ClassName OrderCommissionService
 * @Description 佣金计算服务
 * @author cjy
 * @date 2018年7月4日 下午5:42:16
 */
public interface OrderCommissionService {

	/**
	 * @Title: calculateCommission
	 * @Description: 订单佣金计算
	 * @param orderid 订单id
	 * @throws:
	 * @time: 2018年7月4日 下午5:44:46
	 */
	void calculateCommission(String orderid);
	
	/**
	 * @Title: commissionReturn
	 * @Description: 退货返佣金
	 * @param orderid 订单id
	 * @throws:
	 * @time: 2018年7月6日 上午11:17:49
	 */
	void commissionReturn(String orderid);
}
