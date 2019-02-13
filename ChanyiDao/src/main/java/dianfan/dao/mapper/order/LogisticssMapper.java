package dianfan.dao.mapper.order;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import dianfan.entities.order.OrderDeliveryRelates;


@Repository
public interface LogisticssMapper {

	/**
	 * @Title: findLogisticsInfo
	 * @Description: 获取订单下的物流信息（状态是：订单正常发货）
	 * @return OrderDeliveryRelate
	 * @throws:
	 * @time: 2018年7月24日 下午6:08:58
	 */
	List<OrderDeliveryRelates> findLogisticsInfo();
	/**
	 * @Title: findLogisticsInfo
	 * @Description: 获取订单下的物流信息（状态是：退/换货寄回仓库）
	 * @return OrderDeliveryRelate
	 * @throws:
	 * @time: 2018年7月24日 下午6:08:58
	 */
	List<OrderDeliveryRelates> findLogisticsInfoToSales();
	/**
	 * @Title: findLogisticsInfo
	 * @Description: 获取订单下的物流信息（状态是：退货验收失败退回）
	 * @return OrderDeliveryRelate
	 * @throws:
	 * @time: 2018年7月24日 下午6:08:58
	 */
	List<OrderDeliveryRelates> findLogisticsInfoToRetreatCheck();
	/**
	 * @Title: findLogisticsInfo
	 * @Description: 获取订单下的物流信息（状态是：换货重新发货）
	 * @return OrderDeliveryRelate
	 * @throws:
	 * @time: 2018年7月24日 下午6:08:58
	 */
	List<OrderDeliveryRelates> findLogisticsInfoToRetreatAgain();
	/**
	 * @Title: findLogisticsInfo
	 * @Description: 获取订单下的物流信息（状态是：换货验收失败退回）
	 * @return OrderDeliveryRelate
	 * @throws:
	 * @time: 2018年7月24日 下午6:08:58
	 */
	List<OrderDeliveryRelates> findLogisticsInfoToTrade();

	/**
	 * @Title: updataOrderDeliveryFlag
	 * @Description: 更新物流状态为 02 到达签收
	 * @param id 物流ID
	 * @throws:
	 * @time: 2018年7月25日 上午9:55:26
	 */
	@Update(" UPDATE t_order_delivery_relate SET delivery_status = '02' , delivery_time = now() WHERE id = #{id} ")
	void updataOrderDeliveryFlag(String id);

	/**
	 * @Title: findLogisticsInfodelivery
	 * @Description: 获取订单下的物流信息 已经签收的获取
	 * @return
	 * @throws:
	 * @time: 2018年8月3日 下午3:18:38
	 */
	@Select(" SELECT order_id orderId, delivery_time deliveryTime FROM t_order_delivery_relate WHERE delivery_type = '01' AND delivery_status = '02' AND entkbn = 0 ")
	List<OrderDeliveryRelates> findLogisticsInfodelivery();

	/**
	 * @Title: getOrderFlagByOrderId
	 * @Description: 确认用户是否已经点了确认收货
	 * @param orderId
	 * @return
	 * @throws:
	 * @time: 2018年8月3日 下午3:31:35
	 */
	@Select(" SELECT COUNT(*) FROM t_order WHERE order_id = #{orderId} AND order_status = '05' ")
	int getOrderFlagByOrderId(String orderId);

	/**
	 * @Title: getDeliveryTime
	 * @Description: 获取订单的到货时间
	 * @param orderId
	 * @return
	 * @throws:
	 * @time: 2018年8月3日 下午3:39:34
	 */
	@Select(" SELECT delivery_time deliveryTime FROM t_order_delivery_relate WHERE delivery_type = '01' AND delivery_status = '02' AND entkbn = 0 and order_id = #{orderId} ")
	Date getDeliveryTime(String orderId);

	/**
	 * @Title: updataOrderFlag
	 * @Description: 将订单状态该为43
	 * @param id
	 * @throws:
	 * @time: 2018年8月3日 下午3:48:19
	 */
	@Update(" UPDATE t_order SET order_status = '43' , update_time = NOW(), update_by = 'taskbatch' WHERE order_id = #{id} ")
	void updataOrderFlag(String id);
	
	/**
	 * @Title: updataAfterSale
	 * @Description: 修改售后表中的订单的订单状态
	 * @param id 订单的ID
	 * @param flag 订单需要该成的状态
	 * @throws:
	 * @time: 2018年8月6日 下午4:33:02
	 */
	@Update(" UPDATE t_after_sale SET result = #{flag}, update_time = NOW(), update_by = 'taskbatch' WHERE order_id = #{id} ")
	void updataAfterSale(String id, String flag);

}
