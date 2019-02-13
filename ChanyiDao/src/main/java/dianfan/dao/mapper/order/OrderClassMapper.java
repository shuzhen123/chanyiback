package dianfan.dao.mapper.order;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import dianfan.entities.base.AreaModel;
import dianfan.entities.commission.UserBounsDetail;
import dianfan.entities.goods.GoodsSpec;
import dianfan.entities.logistics.DeliveryModels;
import dianfan.entities.logistics.OrderDeliveryRelate;
import dianfan.entities.order.AfterSale;
import dianfan.entities.order.ConsigneeModel;
import dianfan.entities.order.Order;
import dianfan.entities.order.OrderCloseModel;
import dianfan.entities.order.OrderCloseOutEspEndTimeModel;
import dianfan.entities.order.OrderGoods;
import dianfan.entities.order.OrderModel;
import dianfan.entities.order.OrderSuperior;
import dianfan.entities.order.TradeSer;
import dianfan.entities.our.Goods;
import dianfan.entities.role.Role;

/**
 * @ClassName OrderClassMapper
 * @Description 订单类的dao
 * @author sz
 * @date 2018年7月5日 下午3:32:57
 */
@Repository
public interface OrderClassMapper {

	/**
	 * @Title: fildOrderList
	 * @Description: 获取用户订单列表,
	 * @param prama
	 * @return OrderModel
	 * @throws:
	 * @time: 2018年7月5日 下午4:33:04
	 */
	List<OrderModel> fildOrderList(Map<String, Object> prama);

	/**
	 * @Title: findOrderInfoList
	 * @Description: 获取用户订单列表(后台)
	 * @param prama
	 * @return
	 * @throws:
	 * @time: 2018年7月16日 下午12:18:51
	 */
	List<OrderModel> findOrderInfoList(Map<String, Object> prama);

	/**
	 * @Title: fildOrderCount
	 * @Description: 获取用户订单总条数
	 * @param prama
	 * @return
	 * @throws:
	 * @time: 2018年7月5日 下午5:53:00
	 */
	int fildOrderCount(Map<String, Object> prama);

	/**
	 * @Title: findOrderListCount
	 * @Description: 获取用户订单总条数(后台)
	 * @param params
	 * @return
	 * @throws:
	 * @time: 2018年7月16日 下午12:12:09
	 */
	int findOrderListCount(Map<String, Object> params);

	/**
	 * @Title: getOrderState
	 * @Description: 查看订单的状态
	 * @param param
	 * @return String
	 * @throws:
	 * @time: 2018年7月5日 下午6:49:53
	 */
	@Select("SELECT order_status FROM t_order WHERE entkbn = 0 AND user_id = #{userid} AND order_id = #{orderid} ")
	String getOrderState(Map<String, Object> param);

	/**
	 * @Title: delOrder
	 * @Description: 关闭对应订单
	 * @param param
	 * @throws:
	 * @time: 2018年7月5日 下午7:05:09
	 */
	@Update("UPDATE t_order SET order_status = '22', update_time = now(), update_by =  #{userid} WHERE user_id = #{userid} AND order_id = #{orderid} ")
	void delOrder(Map<String, Object> param);

	/**
	 * @Title: delOrderLowGoods
	 * @Description: 删除对应订单下的商品
	 * @param param
	 * @throws:
	 * @time: 2018年7月5日 下午7:08:50
	 */
	@Update("UPDATE t_order_goods SET entkbn = 9, update_time = now(), update_by =  #{userid}  WHERE order_id = #{orderid} ")
	void delOrderLowGoods(Map<String, Object> param);

	/**
	 * @Title: fildOrderInfo
	 * @Description: 获取订单的详情
	 * @param param
	 * @return OrderModel
	 * @throws:
	 * @time: 2018年7月6日 上午11:55:07
	 */
	OrderModel fildOrderInfo(Map<String, Object> param);

	/**
	 * @Title: fildOrderInfo
	 * @Description: 获取订单的详情
	 * @param param
	 * @return OrderModel
	 * @throws:
	 * @time: 2018年7月6日 上午11:55:07
	 */
	OrderModel queryOrderInfo(String orderid);

	/**
	 * @Title: fildOrderInfo
	 * @Description: 获取订单的详情(后台)
	 * @param param
	 * @return OrderModel
	 * @throws:
	 * @time: 2018年7月6日 上午11:55:07
	 */
	OrderModel getBgOrderInfo(String orderid);

	/**
	 * @Title: findDeliveryList
	 * @Description: 获取收货人寄件人信息
	 * @return
	 * @throws:
	 * @time: 2018年8月8日 下午5:29:05
	 */
	@Select("select tdy.sender_name senderName,tdy.sender_mob senderMob,tdy.sender_addr senderAddr,tdy.receiver_name receiverName,tdy.receiver_mob receiverMob,tdy.receiver_addr receiverAddr from t_order_delivery_relate todr inner join t_delivery tdy on todr.delivery_id=tdy.id where todr.order_id=#{orderid} and delivery_type ='01'")
	List<DeliveryModels> findDeliveryList(String orderid);

	/**
	 * @Title: addConfirmOrder
	 * @Description: 确认订单
	 * @param order
	 * @throws:
	 * @time: 2018年7月9日 上午11:02:19
	 */
	void addConfirmOrder(Order order);

	/**
	 * @Title: fildSpecList
	 * @Description: 获取所有的规格对应的id和值
	 * @return Map<id , string值>
	 * @throws:
	 * @time: 2018年7月8日 下午3:21:42
	 */
	List<GoodsSpec> fildSpecList();

	/**
	 * @Title: getOrderDataByOrderid
	 * @Description: 根据用户id和订单id获取订单信息
	 * @param userid
	 *            用户id
	 * @param orderid
	 *            订单id
	 * @return
	 * @throws:
	 * @time: 2018年7月7日 上午11:45:56
	 */
	@Select("select pay_fee from t_order where order_id=#{orderid} and user_id=#{userid} and order_status='01' and entkbn=0")
	BigDecimal getOrderPayfeeByOrderid(@Param(value = "userid") String userid,
			@Param(value = "orderid") String orderid);

	/**
	 * @Title: updateTradeStrData
	 * @Description: 更新交易流水表（存在则更新不存在则插入）
	 * @param ser
	 *            交易流水数据
	 * @throws:
	 * @time: 2018年7月7日 下午12:00:01
	 */
	void updateTradeStrData(TradeSer ser);

	/**
	 * @Title: getUserOpenidByUserid
	 * @Description: 根据用户id获取用户小程序openid
	 * @param userid
	 *            用户id
	 * @return
	 * @throws:
	 * @time: 2018年7月7日 下午2:03:47
	 */
	@Select("select wxsmall_openid from t_user_userinfo where id=#{userid}")
	String getUserOpenidByUserid(String userid);

	/**
	 * @Title: findTradeSerByserialNumber
	 * @Description: 根据流水号（serial_number）获取订单信息
	 * @param string
	 *            流水号
	 * @throws:
	 * @time: 2018年7月7日 下午3:55:47
	 */
	Order findTradeSerByserialNumber(String serial_number);

	/**
	 * @Title: findFactoryIdFromAddrid
	 * @Description: 根据地址id获取对应工厂id
	 * @param address_id
	 *            地址id
	 * @return
	 * @throws:
	 * @time: 2018年7月7日 下午5:13:02
	 */
	@Select("select flist.id from t_factory_list flist, t_factory_list_area_list farea "
			+ "where flist.id=farea.factory_id and farea.area_code=(select ac.sup_area_code from t_user_address ua,m_area_code ac where ua.area_code=ac.area_code and ua.id=#{address_id}) and farea.entkbn=0 ")
	String findFactoryIdFromAddrid(String address_id);

	/**
	 * @Title: updateOrderStatus
	 * @Description: 更新交易流水表(支付完成)
	 * @param orderid
	 *            订单id
	 * @throws:
	 * @time: 2018年7月7日 下午4:39:59
	 */
	@Update("update t_trade_ser set trade_no=#{tradeNo}, order_status='01', pay_end_time=now() where order_id=#{orderid}")
	void updateOrderTradeSer(Order order);

	/**
	 * @Title: updateOrderStatus
	 * @Description: 更改订单状态(支付完成)
	 * @param orderid
	 *            订单id
	 * @throws:
	 * @time: 2018年7月7日 下午4:39:59
	 */
	@Update("update t_order set factory_list_id=#{factoryListId}, order_status=#{orderStatus}, goods_status=#{goodsStatus} where order_id=#{orderid}")
	void updateOrderStatus(Order order);

	/**
	 * @Title: updateOrders
	 * @Description: 更新订单(定时任务使用)
	 * @param orderid
	 *            订单编号
	 * @param goodstype
	 *            订单类型
	 * @param orderstatus
	 *            订单状态
	 * @param orderstatus
	 *            要更新的状态
	 * @return 更新订单结果
	 * @throws:
	 * @time: 2018年7月7日 下午6:44:32
	 */
	@Update("UPDATE t_order SET order_status = #{toUpdateStatus},update_by = 'taskbatch',version=#{version} WHERE order_id = #{orderId} and goods_type = #{goodsType} and order_status = #{orderStatus}")
	int updateOrders(Map<String, Object> param);

	/**
	 * @Title: findAllOrders
	 * @Description: 获取相关订单类型下的相关订单状态及超出订单结束时间的订单列表信息(定时任务使用)
	 * @param goodstype
	 *            订单类型
	 * @param orderstatus
	 *            订单状态
	 * @param nowdatetime
	 *            订单结束时间
	 * @return 关闭订单的数据
	 * @throws:
	 * @time: 2018年7月7日 下午6:44:43
	 */
	List<OrderCloseModel> findAllOrders(Map<String, Object> param);

	/**
	 * @Title: updateBindRelation
	 * @Description: 清空绑定关系
	 * @param userid
	 *            用户id
	 * @throws:
	 * @time: 2018年7月9日 上午9:17:00
	 */
	@Update("update t_user_userinfo set saler_qr_num=null, consumer_qr_num=null, experiencestore_qr_num=null where id=#{userid}")
	void updateBindRelation(String userid);

	/**
	 * @Title: getEasySpellingOrdersStatus
	 * @Description: 查看付款订单是否已满足拼团人数
	 * @param easySpellingId
	 *            拼团id
	 * @Title: getEasySpellingOrdersStatus
	 * @Description: 查看付款订单是否已满足拼团人数
	 * @param easySpellingId
	 *            拼团id
	 * @return
	 * @throws:
	 * @time: 2018年7月9日 下午12:27:48
	 */
	@Select("select order_id orderid, user_id userid, address_id addressId from t_order where easy_spelling_id=#{easySpellingId} and goods_type='02' and order_status='61' and entkbn=0")
	List<Order> getEasySpellingOrdersCount(String easySpellingId);

	/**
	 * @Title: getEasySpellingPersonCount
	 * @Description: 根据拼团id获取拼团参数的人数
	 * @param easySpellingId
	 * @return
	 * @throws:
	 * @time: 2018年7月9日 下午12:46:56
	 */
	@Select("select limit_num from t_easy_spelling_parameter where id=( "
			+ "select easy_spelling_parameter_id from t_easy_spelling where id=#{easySpellingId}) ")
	int getEasySpellingPersonCount(String easySpellingId);

	/**
	 * @Title: findCloseOutEspEndTimeOrders
	 * @Description:
	 * @param goodsType
	 *            订单类型
	 * @param orderStatus
	 *            订单状态
	 * @param currentDate
	 *            现在时间
	 * @return
	 * @throws:
	 * @time: 2018年7月9日 上午11:16:24
	 */
	List<OrderCloseOutEspEndTimeModel> findCloseOutEspEndTimeOrders(Map<String, Object> param);

	/**
	 * 
	 * @Title: updateTradeStrPayStatus
	 * @Description: 更新交易流水表
	 * @param param
	 *            参数
	 * @return
	 * @throws:
	 * @time: 2018年7月9日 下午4:23:06
	 */
	@Update("update t_trade_ser set order_status = #{newOrderStatus},version = version + 1,update_by='taskbatch',refund_reason =#{refundReason},refund_money=#{refundMoney},refund_start_time = now() where order_status=#{oldOrderStatus} and order_id=#{orderId} and version=#{version} and entkbn=0")
	int updateTradeSerPayStatus(Map<String, Object> param);

	/**
	 * @Title: selectTradeSerPayStatus
	 * @Description:
	 * @param param
	 *            参数
	 * @return
	 * @throws:
	 * @time: 2018年7月9日 下午5:07:58
	 */
	@Select("select pay_id payid,order_id orderid,serial_number serialNumber,trade_no tradeNo,pay_ways payWays,pay_source paySource,order_status orderStatus,deposit_fee depositFee,pay_start_time payStartTime,pay_end_time payEndTime,create_time createTime,create_by createBy, update_by updateBy,entkbn entkbn,version version,refund_reason refundReason,refund_fail_reason refundFailReason,refund_money refundMoney,refund_start_time refundStartTime,refund_end_time refundEndTime from t_trade_ser tts where tts.entkbn=0 and order_status=#{newOrderStatus} ")
	List<TradeSer> selectTradeSerPayStatus(Map<String, Object> param);

	/**
	 * @Title: selectTradeSerPayStatusByOrderId
	 * @Description:
	 * @param param3
	 * @return
	 * @throws:
	 * @time: 2018年7月9日 下午6:04:39
	 */
	@Select("select pay_id payid,order_id orderid,serial_number serialNumber,trade_no tradeNo,pay_ways payWays,pay_source paySource,order_status orderStatus,deposit_fee depositFee,pay_start_time payStartTime,pay_end_time payEndTime,create_time createTime,create_by createBy, update_by updateBy,entkbn entkbn,version version from t_trade_ser tts where tts.entkbn=0 and order_status=#{newOrderStatus} and order_id=#{orderId} ")
	TradeSer selectTradeSerPayStatusByOrderId(Map<String, Object> param);

	/**
	 * @Title: getOrderByid
	 * @Description: 确认需要关闭的订单是否存在
	 * @param orderid
	 *            订单的id
	 * @return int
	 * @throws:
	 * @time: 2018年7月9日 下午5:06:29
	 */
	@Select(" SELECT COUNT(*) FROM t_order WHERE order_id = #{orderid} AND entkbn = 0 AND (order_status = '06' or order_status = '21' or order_status = '22') ")
	int getOrderByid(String orderid);

	/**
	 * @Title: delOrderForever
	 * @Description: 删除订单（逻辑删除）
	 * @param param
	 * @throws:
	 * @time: 2018年7月9日 下午5:10:34
	 */
	@Update("UPDATE t_order SET entkbn = 9,update_by = #{userid}, update_time = now() WHERE user_id = #{userid} AND order_id = #{orderid} ")
	void delOrderForever(Map<String, Object> param);

	/**
	 * @Title: updateOrderAccept
	 * @Description: 订单确认收货完成
	 * @param userid
	 * @param orderid
	 * @throws:
	 * @time: 2018年7月9日 下午6:18:31
	 */
	@Update("update t_order set order_status=#{status} where order_id=#{orderid} and user_id=#{userid}")
	void updateAcceptOrderStatus(@Param(value = "userid") String userid, @Param(value = "orderid") String orderid,
			@Param(value = "status") String status);

	/**
	 * @Title: updateOrderAccept
	 * @Description: 订单确认收货完成 系统
	 * @param userid
	 * @param orderid
	 * @throws:
	 * @time: 2018年7月9日 下午6:18:31
	 */
	@Update("update t_order set order_status=#{status}, update_by = 'taskbatch', update_time = now() where order_id=#{orderid}")
	void updateAcceptOrderStatusxt(@Param(value = "orderid") String orderid, @Param(value = "status") String status);

	/**
	 * @Title: findCommissionInfoByOrderid
	 * @Description: 根据订单id获取佣金分成
	 * @param orderid
	 *            订单id
	 * @return
	 * @throws:
	 * @time: 2018年7月9日 下午6:23:36
	 */
	@Select("select user_id userId, bouns_fee bounsFee from t_user_bouns_detail where order_no=#{orderid}")
	List<UserBounsDetail> findCommissionInfoByOrderid(String orderid);

	/**
	 * @Title: updateBounsStatus
	 * @Description: 更新佣金流水表状态
	 * @param orderid
	 *            订单id
	 * @throws:
	 * @time: 2018年7月9日 下午6:46:43
	 */
	@Update("update t_user_bouns_detail set user_bouns_status='01' where order_no=#{orderid}")
	void updateBounsStatusByOrderid(String orderid);

	/**
	 * @Title: findGoodss
	 * @Description: 通过订单id查找下面的
	 * @param oid
	 *            订单的id
	 * @return GoodsSpec
	 * @throws:
	 * @time: 2018年7月10日 下午4:37:32
	 */
	List<OrderGoods> findGoodss(List<String> oid);

	/**
	 * @Title: findRefundOrders
	 * @Description: 获取待退款订单
	 * @param orderids
	 *            订单ids
	 * @return
	 * @throws:
	 * @time: 2018年7月10日 下午2:51:28
	 */
	List<TradeSer> findRefundOrders(List<String> orderids);

	/**
	 * @Title: getRefundOrdersStatus
	 * @Description: 获取退款状态
	 * @param orderid
	 *            订单id
	 * @return
	 * @throws:
	 * @time: 2018年8月6日 下午5:56:34
	 */
	@Select("select order_status from t_trade_ser where order_id=#{orderid}")
	String getRefundOrdersStatus(String orderid);

	/**
	 * @Title: updateTradeSerOrderStatus
	 * @Description: 修改支付状态
	 * @param orderid
	 * @throws:
	 * @time: 2018年8月8日 下午7:02:09
	 */
	@Update("update  t_trade_ser set refund_money=#{refundmoney} where order_id=#{orderid}")
	void updateTradeSerOrderStatus(@Param("refundmoney") BigDecimal refundmoney, @Param("orderid") String orderid);

	/**
	 * @Title: updateOrderRefundTradeSer
	 * @Description: 退款流水更新
	 * @param ts
	 * @throws:
	 * @time: 2018年7月11日 上午10:32:49
	 */
	@Update("update t_trade_ser set order_status=#{orderStatus}, refund_fail_reason=#{refundFailReason}, refund_start_time=now(), version=version+1 where pay_id=#{payid}")
	void updateOrderRefundTradeSer(TradeSer ts);

	/**
	 * @Title: updateAfterSaleradeSer
	 * @Description: 售后退款流水更新
	 * @param ts
	 * @throws:
	 * @time: 2018年8月9日 上午11:37:24
	 */
	@Update("update t_trade_ser set order_status=#{orderStatus},refund_reason=#{refundReason},refund_money=#{refundMoney},refund_fail_reason=#{refundFailReason}, refund_start_time=now(), version=version+1 where pay_id=#{payid}")
	void updateAfterSaleradeSer(TradeSer ts);

	/**
	 * @Title: addOrderGoods
	 * @Description:
	 * @param og
	 * @throws:
	 * @time: 2018年8月9日 上午11:35:50
	 */
	void addOrderGoods(@Param("og") List<OrderGoods> og);

	/**
	 * @Title: getRoleInfos
	 * @Description: 获取角色信息
	 * @param userid
	 *            用户id
	 * @return
	 * @throws:
	 * @time: 2018年8月21日 下午2:16:11
	 */
	@Select("select DISTINCT mr.id,mr.role_distinguish roleDistinguish,mr.role_name roleName from t_user_role tur inner join m_role mr on tur.roleid=mr.id where tur.userid=#{userid}")
	Role getRoleInfos(String userid);

	/**
	 * @Title: getTradeSerDataByPayid
	 * @Description: 根据交易id获取交易数据
	 * @param out_refund_no
	 * @return
	 * @throws:
	 * @time: 2018年7月11日 下午3:24:46
	 */
	@Select("select pay_id payid, order_id orderid, refund_money refundMoney from t_trade_ser where pay_id=#{out_refund_no} and order_status='05' and entkbn=0")
	TradeSer getTradeSerDataByPayid(String out_refund_no);

	/**
	 * @Title: updateTradeSerRefundStatus
	 * @Description: 更新流水表
	 * @param tradeSer
	 * @throws:
	 * @time: 2018年7月11日 下午3:49:57
	 */
	@Update("update t_trade_ser set order_status=#{orderStatus}, refund_fail_reason=#{refundFailReason}, refund_end_time=now() where pay_id=#{payid}")
	void updateTradeSerRefundStatus(TradeSer tradeSer);

	/**
	 * @Title: updateTradeSerPayStatus
	 * @Description: 更新流水表支付状态
	 * @param tradeSer
	 * @throws:
	 * @time: 2018年7月11日 下午3:49:57
	 */
	@Update("update t_trade_ser set order_status=#{orderStatus},version=version+1 where pay_id=#{payid} and version=#{version}")
	void updateBgTradeSerPayStatus(TradeSer tser);

	/**
	 * @Title: updateOrderRefundStatus
	 * @Description: 更新订单表
	 * @param o
	 * @throws:
	 * @time: 2018年7月11日 下午3:57:12
	 */
	@Update("update t_order set order_status=#{orderStatus} where order_id=#{orderid}")
	void updateOrderRefundStatus(Order o);

	/**
	 * @Title: getConsigneeInfo
	 * @Description: 获取收货人信息
	 * @param addressid
	 * @return
	 * @throws:
	 * @time: 2018年7月12日 上午10:37:06
	 */
	ConsigneeModel getConsigneeInfo(String addressid);

	/**
	 * @Title: findAreaCode
	 * @Description: 通过区域code，将省市区返回
	 * @param code
	 *            区域code
	 * @return String
	 * @throws:
	 * @time: 2018年7月12日 上午10:36:15
	 */
	@Select("SELECT CONCAT(shen.area_name,\" \",shi.area_name,\" \",qu.area_name) addressCode FROM m_area_code shen, m_area_code shi, m_area_code qu "
			+ "WHERE qu.sup_area_code = shi.area_code AND shi.sup_area_code = shen.area_code AND qu.area_code = #{code} ")
	String findAreaCode(String code);

	/**
	 * @Title: findPCityName
	 * @Description:
	 * @param code
	 * @return
	 * @throws:
	 * @time: 2018年8月7日 下午2:29:43
	 */
	@Select("SELECT CONCAT(shen.area_name,\" \",shi.area_name) areaName,CONCAT(shen.area_name,\" \",shi.area_name) areaCode FROM m_area_code shen, m_area_code shi "
			+ "WHERE  shi.sup_area_code = shen.area_code AND shi.area_code = #{code} ")
	AreaModel getPCityName(String code);

	/**
	 * @Title: getPCityAreaName
	 * @Description:
	 * @param code
	 * @return
	 * @throws:
	 * @time: 2018年8月24日 下午3:43:37
	 */
	@Select("SELECT CONCAT(shen.area_name,shi.area_name,qu.area_name) addressCode FROM m_area_code shen, m_area_code shi, m_area_code qu "
			+ "WHERE qu.sup_area_code = shi.area_code AND shi.sup_area_code = shen.area_code AND qu.area_code = #{code} ")
	String getPCityAreaName(String code);

	/**
	 * @Title: findCityAreaName
	 * @Description: 通过区域code获取城市区域name
	 * @param code
	 *            区域code
	 * @return
	 * @throws:
	 * @time: 2018年8月6日 下午1:09:17
	 */
	@Select("SELECT CONCAT(shi.area_name,qu.area_name) addressCode FROM m_area_code shen, m_area_code shi, m_area_code qu WHERE qu.sup_area_code = shi.area_code AND shi.sup_area_code = shen.area_code AND qu.area_code =#{code} ")
	String getCityAreaName(String code);

	/**
	 * @Title: updateBgOrderPrice
	 * @Description: 修改订单价格
	 * @param params
	 *            入参
	 * @throws:
	 * @time: 2018年7月17日 上午9:17:58
	 */
	void updateBgOrderPriceOrMessage(Map<String, Object> params);

	/**
	 * @Title: updateBgOrderConsigneeInfo
	 * @Description: 修改收件人信息
	 * @param params
	 *            参数
	 * @throws:
	 * @time: 2018年7月17日 上午9:49:30
	 */
	void updateBgOrderConsigneeInfo(Map<String, Object> params);

	/**
	 * @Title: getVersionInfo
	 * @Description: 获取订单版本信息
	 * @param orderid
	 *            订单号
	 * @return
	 * @throws:
	 * @time: 2018年7月17日 上午9:23:36
	 */
	@Select("select version from t_order where order_id=#{orderid}")
	String getVersionInfo(String orderid);

	/**
	 * @Title: getSuperiorInfo
	 * @Description: 获取订单上级信息
	 * @param orderid
	 *            订单id
	 * @return
	 * @throws:
	 * @time: 2018年7月17日 上午11:36:04
	 */
	@Select("select uu.id userId,uu.nick_name nickName,uu.avatar_url avatarUrl,uu.sex,uu.telno,ubd.bouns_fee bounsFee,ubd.bouns_from bounsFrom,ubd.bouns_percent bounsPercent,ubd.c_last_money cLastMoney,tumr.role_name roleName,ubd.user_bouns_status userBounsStatus from t_user_bouns_detail ubd inner join t_user_userinfo uu on ubd.user_id=uu.id inner join (select tur.userid,mr.role_name from t_user_role tur inner join m_role mr on tur.roleid=mr.id) tumr where tumr.userid=ubd.user_id and ubd.order_no=#{orderid} and uu.entkbn=0 and ubd.entkbn=0")
	List<OrderSuperior> getSuperiorInfo(String orderid);

	/**
	 * @Title: getAreaName 获取地区名称
	 * @Description:
	 * @param areacode
	 *            地区code
	 * @return
	 * @throws:
	 * @time: 2018年7月18日 上午11:28:40
	 */
	@Select("select area_code areaCode, area_name areaName,sup_area_code supAreaCode from m_area_code where area_code=#{areacode}")
	AreaModel getAreaName(String areacode);

	/**
	 * @Title: getFactoryAdmin
	 * @Description: 获取工厂账号
	 * @param factoryid
	 * @return
	 * @throws:
	 * @time: 2018年7月19日 上午9:39:48
	 */
	@Select("select admin_id from t_factory_admin_relate where factory_id=#{factoryid}")
	List<String> getFactoryAdmin(String factoryid);

	/**
	 * @Title: getTradeSerInfo
	 * @Description: 根据订单号查询流水信息
	 * @param orderid
	 *            订单号
	 * @return
	 * @throws:
	 * @time: 2018年7月20日 上午10:21:00
	 */
	@Select("select pay_id payid,order_id orderid,version from t_trade_ser where order_id=#{orderid}")
	TradeSer getTradeSerInfo(String orderid);

	/**
	 * @Title: getOrderId
	 * @Description: 获取订单id
	 * @param deliveryid
	 * @return
	 * @throws:
	 * @time: 2018年7月24日 下午5:16:20
	 */
	@Select("select  delivery_id deliveryId,delivery_type deliveryType,delivery_status deliveryStatus  from t_order_delivery_relate where order_id = #{orderid}  and entkbn=0")
	List<OrderDeliveryRelate> getDeliveryId(String orderid);

	/**
	 * @Title: getDeliveryIds
	 * @Description:
	 * @param orderid
	 * @param deliverytype
	 * @return
	 * @throws:
	 * @time: 2018年8月9日 上午9:57:41
	 */
	@Select("select  delivery_id  from t_order_delivery_relate where order_id = #{orderid} and delivery_type=#{deliverytype} and entkbn=0")
	List<String> getDeliveryIds(@Param("orderid") String orderid, @Param("deliverytype") String deliverytype);

	/**
	 * @Title: getConfirmDeliveryId
	 * @Description: 确认发货物流id
	 * @param orderid
	 *            订单id
	 * @return
	 * @throws:
	 * @time: 2018年8月6日 下午5:27:12
	 */
	@Select("select  delivery_id from t_order_delivery_relate where order_id = #{orderid} and delivery_type ='01'  and entkbn=0")
	List<String> getConfirmDeliveryId(String orderid);

	/**
	 * @Title: getReturnGoodsDeliveryId
	 * @Description: 返回退/换货寄回仓库的物流id
	 * @param orderid
	 *            订单id
	 * @return
	 * @throws:
	 * @time: 2018年8月6日 下午5:27:12
	 */
	@Select("select  delivery_id from t_order_delivery_relate where order_id = #{orderid} and delivery_type ='02'  and entkbn=0")
	List<String> getReturnGoodsDeliveryId(String orderid);

	/**
	 * @Title: getReturnGoodsFailDeliveryId
	 * @Description: 退货验收失败退回
	 * @param orderid
	 *            订单id
	 * @return
	 * @throws:
	 * @time: 2018年8月6日 下午5:36:02
	 */
	@Select("select delivery_id from t_order_delivery_relate where order_id = #{orderid} and delivery_type ='03'  and entkbn=0")
	List<String> getReturnGoodsFailDeliveryId(String orderid);

	/**
	 * @Title: getBarterGoodsFailDeliveryId
	 * @Description:
	 * @param orderid
	 * @return
	 * @throws:
	 * @time: 2018年8月9日 上午10:05:26
	 */
	@Select("select delivery_id from t_order_delivery_relate where order_id = #{orderid} and delivery_type ='05'  and entkbn=0")
	List<String> getBarterGoodsFailDeliveryId(String orderid);

	/**
	 * @Title: getBarterGoodsSuccessDeliveryId
	 * @Description:
	 * @param orderid
	 * @return
	 * @throws:
	 * @time: 2018年8月9日 上午10:06:23
	 */
	@Select("select delivery_id from t_order_delivery_relate where order_id = #{orderid} and delivery_type ='04'  and entkbn=0")
	List<String> getBarterGoodsSuccessDeliveryId(String orderid);

	/**
	 * @Title: getSaleDiscount
	 * @Description: 获取员工折扣
	 * @return
	 * @throws:
	 * @time: 2018年7月27日 上午10:35:01
	 */
	@Select("select mr.sale_discount from t_user_role tur inner join m_role mr on tur.roleid=mr.id where tur.userid=#{userid}")
	BigDecimal getSaleDiscount(String userid);

	/**
	 * @Title: findGoodsInfoByOrderId
	 * @Description: 查询物流商品信息
	 * @param orderid
	 *            订单id
	 * @return
	 * @throws:
	 * @time: 2018年7月31日 下午2:50:51
	 */
	List<Goods> findGoodsInfoByOrderId(String orderid);

	/**
	 * @Title: delDelivery
	 * @Description: 删除物流信息
	 * @param orderid
	 *            订单id
	 * @throws:
	 * @time: 2018年8月1日 下午3:26:38
	 */
	@Update("update t_delivery td  set td.entkbn=9 where td.id in (select todr.delivery_id from t_order_delivery_relate todr where todr.order_id=#{orderid})")
	void delDelivery(String orderid);

	/**
	 * @Title: getDeliveryNo
	 * @Description:
	 * @param orderid
	 * @return
	 * @throws:
	 * @time: 2018年8月1日 下午3:51:28
	 */
	@Select("select delivery_id from t_order_delivery_relate where order_id=#{orderid}")
	String getDeliveryNo(String orderid);

	/**
	 * @Title: delOrderDeliveryRelate
	 * @Description: 删除订单物流关系信息
	 * @param orderid
	 *            订单id
	 * @throws:
	 * @time: 2018年8月1日 下午3:29:12
	 */
	@Update("update t_order_delivery_relate set entkbn=9 where order_id=#{orderid}")
	void delOrderDeliveryRelate(String orderid);

	/**
	 * @Title: addOrderDeliveryRelate
	 * @Description: 添加订单物流相关
	 * @param odr
	 * @throws:
	 * @time: 2018年8月1日 下午6:59:19
	 */
	@Insert("insert into t_order_delivery_relate (id,order_id,delivery_id,delivery_type,delivery_status,express_type,entkbn) values (#{id},#{orderId},#{deliveryId},#{deliveryType},#{deliveryStatus},#{expressType},0)")
	void addOrderDeliveryRelate(OrderDeliveryRelate odr);

	/**
	 * @Title: updataRecoverOrder
	 * @Description: 恢复订单操作
	 * @param param
	 * @throws:
	 * @time: 2018年8月2日 上午9:50:51
	 */
	@Update("UPDATE t_order SET entkbn = 0,update_by = #{userid}, update_time = now() WHERE user_id = #{userid} AND order_id = #{orderid} ")
	void updataRecoverOrder(Map<String, Object> param);

	/**
	 * @Title: fildOrderCountOmit
	 * @param prama
	 * @Description:
	 * @return
	 * @throws:
	 * @time: 2018年8月2日 上午10:00:38
	 */
	@Select(" SELECT count(*) FROM t_order WHERE entkbn = 9 AND user_id = #{userid} ")
	int fildOrderCountOmit(Map<String, Object> prama);

	/**
	 * @Title: fildOrderListOmit
	 * @Description: 获取用户删除的订单列表
	 * @param prama
	 * @return
	 * @throws:
	 * @time: 2018年8月2日 上午10:10:44
	 */
	List<OrderModel> fildOrderListOmit(Map<String, Object> prama);

	/**
	 * @Title: findorderGoods
	 * @Description: 查询订单下的商品的ID
	 * @param orderid
	 * @return
	 * @throws:
	 * @time: 2018年8月2日 上午10:53:37
	 */
	@Select(" SELECT DISTINCT order_id orderId, goods_id goodsId FROM t_order_goods WHERE order_id = #{orderid} AND entkbn = 0 ")
	List<OrderGoods> findorderGoods(String orderid);

	/**
	 * @Title: getshopId
	 * @Description: 获取体验店的ID
	 * @param orderid
	 *            订单的ID
	 * @return
	 * @throws:
	 * @time: 2018年8月2日 上午11:42:14
	 */
	@Select(" SELECT id FROM t_experiencestore_apply WHERE apply_user_id = (SELECT user_id FROM t_order WHERE order_id = #{orderid} AND entkbn = 0) and entkbn = 0 ")
	String getshopId(String orderid);

	/**
	 * @Title: addExperiencestoreOrder
	 * @Description: 添加体验店中的商品
	 * @param param
	 * @throws:
	 * @time: 2018年8月2日 上午11:48:19
	 */
	void addExperiencestoreOrder(Map<String, Object> param);

	/**
	 * @Title: addAfterSale
	 * @Description: 添加售后信息
	 * @param as
	 * @throws:
	 * @time: 2018年8月3日 下午2:36:44
	 */
	@Insert("insert into t_after_sale (id,order_id,reason,pic_urls,remark,handle_status,result,delivery_id,create_time,entkbn) values (#{id},#{orderId},#{reason},#{picUrls},#{remark},#{handleStatus},#{result},#{deliveryId},now(),0)")
	void addAfterSale(AfterSale as);

	/**
	 * @Title: updateAfterSale
	 * @Description: 修改售后信息
	 * @param as
	 * @throws:
	 * @time: 2018年8月6日 下午3:35:03
	 */
	void updateAfterSale(AfterSale as);

	/**
	 * @Title: updateAfterSales
	 * @Description:
	 * @param as
	 * @throws:
	 * @time: 2018年8月10日 下午5:39:44
	 */
	@Update("update t_after_sale set result=#{result},version=version+1 where order_id=#{orderId}")
	void updateAfterSales(AfterSale as);

	/**
	 * @Title: getFindUserType
	 * @Description: 确认下单的角色
	 * @param userid
	 * @return
	 * @throws:
	 * @time: 2018年8月6日 下午3:19:10
	 */
	@Select(" SELECT COUNT(*) FROM m_role WHERE id in (SELECT roleid FROM t_user_role WHERE userid = #{userid}) AND role_distinguish = '05' ")
	int getFindUserType(String userid);

	/**
	 * @Title: getOrderStateByOrderid
	 * @Description: 检测订单状态
	 * @param orderid
	 * @return
	 * @throws:
	 * @time: 2018年8月24日 上午9:59:39
	 * @author cjy
	 */
	@Select("select count(*) from t_order where order_id=#{orderid} and order_status='01'")
	boolean getOrderStateByOrderid(String orderid);

	@Update("update t_order set order_status = '21', update_time = now(), update_by = #{operater} where order_id = #{orderid} ")
	void delOrderByOrderid(@Param("orderid") String orderid, @Param("operater") String operater);

	/**
	 * @Title: findOrderGoodsIds
	 * @Description: 获取订单中商品id
	 * @param orderid
	 *            订单id
	 * @return
	 * @throws:
	 * @time: 2018年8月27日 上午9:31:47
	 * @author cjy
	 */
	@Select("select goods_id from t_order_goods where order_id=#{orderid} and entkbn=0")
	List<String> findOrderGoodsIds(String orderid);

	/**
	 * @Title: goodsSalesCountInc
	 * @Description: 商品销量+1
	 * @param ids
	 * @throws:
	 * @time: 2018年8月27日 上午9:34:36
	 * @author cjy
	 */
	void goodsSalesCountInc(List<String> ids);

	/**
	 * @Title: findAllStatusOrders
	 * @Description:
	 * @param param
	 * @return
	 * @throws:
	 * @time: 2018年8月29日 上午11:35:52
	 */
	@Select("select order_id orderid,user_id  userid,coupon_relate_id  couponRelateId from t_order t where (order_status = '21' or order_status = '22' or order_status = '61') and entkbn=0 and t.coupon_relate_id <> '' and t.coupon_relate_id  is not null")
	List<Order> returnCouponCloseOrders();

}
