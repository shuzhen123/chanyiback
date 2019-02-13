package dianfan.service.order;

import java.util.List;

import dianfan.annotations.SystemServiceLog;
import dianfan.entities.order.TradeSer;
import dianfan.models.ResultBean;

/**
 * @ClassName OrderClassService
 * @Description 订单相关 （接口）
 * @author sz
 * @date 2018年7月5日 下午3:30:01
 */
public interface OrderClassService {

	/**
	 * @Title: fildOrderList
	 * @Description: 获取用户订单列表
	 * @param userid
	 *            userid
	 * @param status
	 *            订单状态 01-待付款 02-待审核 03-正在生产 04 待发货 05 待收货 06 已收货
	 * @param page
	 *            请求页
	 * @param pagecounts
	 * @return ResultBean
	 * @throws:
	 * @time: 2018年7月5日 下午4:07:28
	 */
	@SystemServiceLog(method = "fildOrderList", description = "获取用户订单列表 ")
	ResultBean fildOrderList(String userid, String status, Integer page, Integer pagecounts);

	/**
	 * @Title: closeOrder
	 * @Description: 关闭订单
	 * @param userid
	 *            用户id
	 * @param orderid
	 *            订单id
	 * @return ResultBean
	 * @throws:
	 * @time: 2018年7月5日 下午6:39:31
	 */
	@SystemServiceLog(method = "closeOrder", description = "关闭订单 ")
	ResultBean delOrder(String userid, String orderid);

	/**
	 * @Title: fildOrderInfo
	 * @Description: 获取用户订单详情
	 * @param userid
	 *            用户id
	 * @param orderid
	 *            订单id
	 * @return ResultBean
	 * @throws:
	 * @time: 2018年7月6日 上午10:42:55
	 */
	@SystemServiceLog(method = "fildOrderInfo", description = "获取订单详情")
	ResultBean fildOrderInfo(String userid, String orderid);

	/**
	 * @Title: confirmOrder
	 * @Description:
	 * @param userid
	 *            用户id
	 * @param addressids
	 *            地址id
	 * @param goodscartids
	 *            购物车id
	 * @param couponrelateid
	 *            用户优惠券相关id
	 * @throws:
	 * @time: 2018年7月7日 下午4:37:05
	 */
	@SystemServiceLog(method = "addConfirmOrder", description = "确认订单 ")
	ResultBean addConfirmOrder(String userid, String addressids, String goodscartids, String couponrelateid,
			String couponid, String payprice, String source);

	/**
	 * @Title: addOrdinaryBuyNow
	 * @Description:
	 * @param userid
	 *            用户id
	 * @param addressids
	 *            地址id
	 * @param goodsids
	 *            商品id
	 * @param goodspriceids
	 *            商品价格id
	 * @param couponrelateid
	 *            用户优惠券相关id
	 * @param couponid
	 *            优惠券id
	 * @throws:
	 * @time: 2018年7月9日 上午11:42:34
	 */
	@SystemServiceLog(method = "addOrdinaryBuyNow", description = "确认订单(普通商品) ")
	public ResultBean addOrdinaryBuyNow(String userid, String addressids, String goodsids, String goodspriceids,
			Integer num, String couponrelateid, String couponid, String isesflag, String esid, String payprice,
			String source);

	/**
	 * @Title: goodsOrderPayment
	 * @Description:
	 * @param userid
	 *            用户id
	 * @param orderid
	 *            订单id
	 * @param payWays
	 *            支付方式
	 * @param paySource
	 * @param payType
	 * @param ip
	 * @return
	 * @throws:
	 * @time: 2018年7月7日 下午7:13:07
	 */
	public ResultBean goodsOrderPayment(String userid, String orderid, String payWays, String paySource, String payType,
			String ip, String accesstoken) throws Exception;

	/**
	 * @Title: espOrderCloseOutPay
	 * @Description:关闭待付款订单
	 * @throws:
	 * @time: 2018年7月9日 下午5:22:17
	 */
	void espOrderCloseOutPay();

	/**
	 * @Title: espOrderCloseOutEspEndTime
	 * @Description:关闭支付单未成团订单
	 * @throws:
	 * @time: 2018年7月9日 下午5:29:48
	 */
	void espOrderCloseOutEspEndTime();

	/**
	 * @Title: updateTradeSerPayStatus
	 * @Description:退钱
	 * @throws:
	 * @time: 2018年7月9日 下午6:01:55
	 */
	void updateTradeSerPayStatus();

	/**
	 * @Title: acceptOrder
	 * @Description: 订单确认收货完成
	 * @param userid
	 *            用户id
	 * @param orderid
	 *            订单id
	 * @return
	 * @throws:
	 * @time: 2018年7月9日 下午5:56:40
	 */
	void acceptOrder(String userid, String orderid);

	/**
	 * @Title: delOrderForever
	 * @Description: 删除订单
	 * @param userid
	 * @param orderid
	 * @return
	 * @throws:
	 * @time: 2018年7月9日 下午5:00:46
	 */
	ResultBean delOrderForever(String userid, String orderid);

	/**
	 * @Title: orderRefund
	 * @Description: 订单退款
	 * @param orderid
	 *            订单id列表
	 * @throws:
	 * @time: 2018年7月10日 上午11:12:18
	 */
	void orderRefund(List<String> orderids);

	/**
	 * @Title: selectTradeSerPayStatus
	 * @Description: 订单查询状态
	 * @param payStatus
	 *            状态
	 * @return
	 * @throws:
	 * @time: 2018年7月11日 下午5:41:59
	 */
	List<TradeSer> selectTradeSerPayStatus(String payStatus);

	/**
	 * @Title: findOrderInfoList
	 * @Description:
	 * @param goodstype
	 *            订单类型(01:正常下单02：易拼 03：易团)
	 * @param starmoney
	 *            起始价格(价格区间)
	 * @param endmoney
	 *            终了价格(价格区间)
	 * @param factoryid
	 *            工厂id
	 * @param orderstatus
	 *            订单的状态(01-待付款 02-待审核 03-正在生产 04 待发货 05 待收货 06 已收货)
	 * @param goodsstatus
	 *            商品的状态(10-自动分配 11-异常订单(生产不了)12-手动分配订单到工厂(内部专用)13-待生产 14-生产完成 )
	 * @param payways
	 *            支付方式 01-ALI(支付宝) 02-WX(微信) 03-BC(银行卡
	 * @param page
	 *            第几页
	 * @param pagecounts
	 *            每页的条数
	 * @return
	 * @throws:
	 * @time: 2018年7月16日 下午2:24:54
	 */
	ResultBean findOrderInfoList(String userid, String orderid, String goodstype, String starmoney, String endmoney,
			String startdate, String enddate, String factoryid, String orderstatus, String goodsstatus, String payways,
			String deliverystatus, String consignee, String ordersource, String paystatus, Integer page,
			Integer pagecounts);

	/**
	 * @Title: updateBgOrderPrice
	 * @Description: 修改订单价格
	 * @param orderid
	 *            订单id
	 * @param money
	 *            要修改的金额
	 * @param custmessage
	 *            客户备注信息
	 * @param orderstatus
	 *            订单状态
	 * @param goodsstatus
	 *            商品状态
	 * @throws:
	 * @time: 2018年7月17日 上午9:13:35
	 */
	void updateBgOrderPriceOrMessage(String userid, String orderid, String money, String custmessage,
			String orderstatus, String goodsstatus, String paystatus, String factoryid);

	/**
	 * @Title: updateConfirmDelivery
	 * @Description:
	 * @param userid
	 * @param orderid
	 * @param orderstatus
	 * @throws:
	 * @time: 2018年7月31日 下午1:24:44
	 */
	ResultBean updateConfirmDelivery(String userid, String orderid, String orderstatus, String pickupDate,
			String receiptFlag, String receiverPhone, String rtnReceiverName, String rtnReceiverMob,
			String rtnReceiverAddr, String rtnReceiverPhone, String predictDate, String senderName, String senderMob,
			String senderPhone, String senderAddr, String sendTo, String paperFrom);

	/**
	 * @Title: updateReturnGoodsApprove
	 * @Description:
	 * @param userid
	 *            处理人id
	 * @param orderid
	 *            订单id
	 * @param result
	 *            审批结果
	 * @param pickupDate
	 *            上门揽货时间
	 * @param resultfreason
	 *            拒绝原因
	 * @param predictDate
	 *            预计到仓时间
	 * @param sendTo
	 *            始发中心
	 * @return
	 * @throws:
	 * @time: 2018年8月3日 下午4:22:54
	 */
	ResultBean updateReturnGoodsApprove(String userid, String orderid, String result, String pickupDate,
			String resultfreason, String predictDate, String senderName, String senderMob, String senderPhone,
			String senderAddr, String receiptFlag, String rtnReceiverName, String rtnReceiverMob,
			String rtnReceiverAddr, String rtnReceiverPhone, String paperFrom, String afterSaleId, String sendTo);

	/**
	 * @Title: updateBarterGoodsApprove
	 * @Description:
	 * @param userid
	 * @param orderid
	 * @param result
	 * @param pickupDate
	 * @param resultfreason
	 * @param predictDate
	 * @param senderName
	 * @param senderMob
	 * @param senderPhone
	 * @param senderAddr
	 * @param receiptFlag
	 * @param rtnReceiverName
	 * @param rtnReceiverMob
	 * @param rtnReceiverAddr
	 * @param rtnReceiverPhone
	 * @param paperFrom
	 * @param sendTo
	 * @return
	 * @throws:
	 * @time: 2018年8月7日 下午8:36:09
	 */
	ResultBean updateBarterGoodsApprove(String userid, String orderid, String result, String pickupDate,
			String resultfreason, String predictDate, String senderName, String senderMob, String senderPhone,
			String senderAddr, String receiptFlag, String rtnReceiverName, String rtnReceiverMob,
			String rtnReceiverAddr, String rtnReceiverPhone, String paperFrom, String afterSaleId, String sendTo);

	/**
	 * @Title: updateReturnGoodsApproveResult
	 * @Description: 审批结果
	 * @param userid
	 *            处理人id
	 * @param orderid
	 *            订单id
	 * @param result
	 *            审批结果
	 * @param pickupDate
	 *            上门揽货时间
	 * @param resultfreason
	 *            拒绝原因
	 * @param predictDate
	 *            预计到仓时间
	 * @param sendTo
	 *            始发中心
	 * @return
	 * @throws:
	 * @time: 2018年8月3日 下午4:22:54
	 */
	ResultBean updateReturnGoodsApproveResult(String userid, String orderid, String result, String pickupDate,
			String resultfreason, String predictDate, String receiverName, String receiverMob, String receiverPhone,
			String receiverAddr, String receiptFlag, String rtnReceiverName, String rtnReceiverMob,
			String rtnReceiverAddr, String rtnReceiverPhone, String paperFrom, String afterSaleId, String sendTo);

	/**
	 * @Title: updateReturnGoodsApproveResult
	 * @Description: 审批结果
	 * @param userid
	 *            处理人id
	 * @param orderid
	 *            订单id
	 * @param result
	 *            审批结果
	 * @param pickupDate
	 *            上门揽货时间
	 * @param resultfreason
	 *            拒绝原因
	 * @param predictDate
	 *            预计到仓时间
	 * @param sendTo
	 *            始发中心
	 * @return
	 * @throws:
	 * @time: 2018年8月3日 下午4:22:54
	 */
	ResultBean updateBarterGoodsApproveResult(String userid, String orderid, String result, String pickupDate,
			String resultfreason, String predictDate, String receiverName, String receiverMob, String receiverPhone,
			String receiverAddr, String receiptFlag, String rtnReceiverName, String rtnReceiverMob,
			String rtnReceiverAddr, String rtnReceiverPhone, String paperFrom, String afterSaleId, String sendTo);

	/**
	 * @Title: updateBgOrderConsigneeInfo
	 * @Description:
	 * @param orderid
	 *            订单id
	 * @param areacode
	 *            区域code
	 * @param name
	 *            姓名
	 * @param telno
	 *            手机号
	 * @param detailaddr
	 *            详细地址
	 * @throws:
	 * @time: 2018年7月17日 上午9:43:37
	 */
	void updateBgOrderConsigneeInfo(String userid, String orderid, String areacode, String name, String telno,
			String detailaddr);

	/**
	 * @Title: getOrderInfo
	 * @Description:
	 * @param userid
	 *            用户id
	 * @param orderid
	 *            订单id
	 * @return
	 * @throws:
	 * @time: 2018年7月18日 下午2:13:57
	 */
	ResultBean getOrderInfo(String userid, String orderid);

	/**
	 * @Title: updataRecoverOrder
	 * @Description: 恢复订单
	 * @param userid
	 *            用户ID
	 * @param orderid
	 *            订单的ID
	 * @return
	 * @throws:
	 * @time: 2018年8月2日 上午9:47:01
	 */
	ResultBean updataRecoverOrder(String userid, String orderid);

	/**
	 * @Title: getDeliveryId
	 * @Description:
	 * @param orderid
	 * @return
	 * @throws:
	 * @time: 2018年8月1日 下午3:48:46
	 */
	List<String> getDeliveryId(String orderid, String deliverytype);

	/**
	 * @Title: delLwbMain
	 * @Description: 取消发货
	 * @param orderid
	 *            订单id
	 * @return
	 * @throws:
	 * @time: 2018年8月1日 下午3:09:21
	 */
	ResultBean delLwbMain(String orderid);

	/**
	 * @Title: addReturnGoods
	 * @Description: 退货
	 * @param userid
	 *            用户id
	 * @param orderid
	 *            订单id
	 * @param reason
	 *            原因
	 * @param price
	 *            价格
	 * @param instructions
	 *            说明
	 * @param credentials
	 *            上传凭证
	 * @throws:
	 * @time: 2018年8月3日 下午1:56:17
	 */
	void addReturnGoods(String userid, String orderid, String reason, String price, String instructions,
			String credentials);

	/**
	 * @Title: addBarterGoods
	 * @Description:
	 * @Description: 换货
	 * @param userid
	 *            用户id
	 * @param orderid
	 *            订单id
	 * @param reason
	 *            原因
	 * @param instructions
	 *            说明
	 * @param credentials
	 *            上传凭证
	 * @throws:
	 * @time: 2018年8月7日 下午4:27:01
	 */
	void addBarterGoods(String userid, String orderid, String reason, String instructions, String credentials);

	/**
	 * @Title: acceptOrderFooXt
	 * @Description: 系统自动确认收货
	 * @param orderid
	 * @throws:
	 * @time: 2018年8月3日 下午1:54:19
	 */
	void acceptOrderForXt(String orderid);

	/**
	 * @Title: closeOrder
	 * @Description: 订单关闭
	 * @param operater
	 * @param orderid
	 * @return
	 * @throws:
	 * @time: 2018年8月24日 上午9:50:41
	 * @author cjy
	 */
	ResultBean closeOrder(String operater, String orderid);

	/**
	 * @Title: returnCouponCloseOrders
	 * @Description:
	 * @throws:
	 * @time: 2018年8月29日 上午11:27:25
	 */
	void returnCouponCloseOrders();
}
