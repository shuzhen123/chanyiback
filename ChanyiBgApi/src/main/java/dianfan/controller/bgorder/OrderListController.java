/**  
* @Title: OrderListController.java
* @Package dianfan.controller
* @Description: TODO
* @author yl
* @date 2018年7月16日 上午11:51:37
* @version V1.0  
*/
package dianfan.controller.bgorder;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

import dianfan.annotations.SystemControllerLog;
import dianfan.annotations.UnCheckedFilter;
import dianfan.constant.ConstantIF;
import dianfan.constant.ResultBgMsg;
import dianfan.models.ResultBean;
import dianfan.models.TokenModel;
import dianfan.service.impl.RedisTokenService;
import dianfan.service.jd.transport.JdTransportService;
import dianfan.service.logistics.LogisticsService;
import dianfan.service.order.OrderClassService;
import dianfan.util.RegexUtils;

/**
 * @ClassName OrderListController
 * @Description
 * @author yl
 * @date 2018年7月16日 上午11:51:37
 */
@Scope("request")
@Controller
@RequestMapping(value = "/bgorderlist")
public class OrderListController {

	/**
	 * 注入： #OrderClassService
	 */
	@Autowired
	private OrderClassService orderClassService;
	/**
	 * 注入： #RedisTokenService
	 */
	@Autowired
	private RedisTokenService redisTokenService;
	@Autowired
	private LogisticsService logisticsService;
	@Autowired
	private JdTransportService jdTransportService;

	/**
	 * @Title: findOrderInfoList
	 * @Description: 获取订单列表
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
	 * @time: 2018年7月17日 上午9:37:28
	 */
	@SystemControllerLog(method = "findOrderInfoList", logtype = ConstantIF.LOG_TYPE_1, description = "获取订单列表")
	@ApiOperation(value = "findOrderInfoList", httpMethod = "GET", notes = "获取订单列表", response = ResultBean.class)
	@RequestMapping(value = "/findOrderInfoList", method = RequestMethod.GET)
	public @ResponseBody ResultBean findOrderInfoList(
			@ApiParam(value = "token值") @RequestParam(value = ConstantIF.ACCESSTOKEN) String accesstoken,
			@ApiParam(value = "订单编号") @RequestParam(value = "orderid", required = false) String orderid,
			@ApiParam(value = "订单类型(01:正常下单02：易拼 03：易团)") @RequestParam(value = "goodstype", defaultValue = "00", required = false) String goodstype,
			@ApiParam(value = "起始价格(价格区间)") @RequestParam(value = "starmoney", required = false) String starmoney,
			@ApiParam(value = "终了价格(价格区间)") @RequestParam(value = "endmoney", required = false) String endmoney,
			@ApiParam(value = "起始时间(时间区间)") @RequestParam(value = "startdate", required = false) String startdate,
			@ApiParam(value = "终了时间(时间区间)") @RequestParam(value = "enddate", required = false) String enddate,
			@ApiParam(value = "工厂id") @RequestParam(value = "factoryid", required = false) String factoryid,
			@ApiParam(value = "订单的状态(01-待付款 02-待审核 03-正在生产 04 待发货 05 待收货 06 已收货)") @RequestParam(value = "orderstatus", defaultValue = "00", required = false) String orderstatus,
			@ApiParam(value = "商品的状态(10-自动分配 11-异常订单(生产不了)12-手动分配订单到工厂(内部专用)13-待生产 14-生产完成 )") @RequestParam(value = "goodsstatus", defaultValue = "00", required = false) String goodsstatus,
			@ApiParam(value = "支付方式 01-ALI(支付宝) 02-WX(微信) 03-BC(银行卡)") @RequestParam(value = "payways", defaultValue = "00", required = false) String payways,
			@ApiParam(value = "状态(01：在途02：到达签收03:延时再送)") @RequestParam(value = "deliverystatus", defaultValue = "00", required = false) String deliverystatus,
			@ApiParam(value = "收货人姓名") @RequestParam(value = "consignee", required = false) String consignee,
			@ApiParam(value = "订单来源(01 小程序  02 APP  03 H5)") @RequestParam(value = "orderSource", defaultValue = "00", required = false) String orderSource,
			@ApiParam(value = "支付状态(支付状态 01-支付成功 02-支付失败 03-支付中 04- 发起退款 05-退款中 06-退款成功 07-退款失败)") @RequestParam(value = "payStatus", defaultValue = "00", required = false) String payStatus,
			@ApiParam(value = "请求页") @RequestParam(value = ConstantIF.PAGE, defaultValue = ConstantIF.PAGE_START_STR) int page,
			@ApiParam(value = "每页条数") @RequestParam(value = ConstantIF.PAGE_SIZE, defaultValue = ConstantIF.PAGE_OFFSET_STR) int pageSize) {
		// 创建返回数据
		ResultBean result = new ResultBean();
		TokenModel tokens = redisTokenService.getToken(accesstoken);
		if (tokens != null) {
			String userid = tokens.getUserid();
			// 3.调库
			result = orderClassService.findOrderInfoList(userid, orderid, goodstype, starmoney, endmoney, startdate,
					enddate, factoryid, orderstatus, goodsstatus, payways, deliverystatus, consignee, orderSource,
					payStatus, page, pageSize);
		} else {
			return new ResultBean("001", ResultBgMsg.C_001);
		}
		// 4.成功
		return result;
	}

	@SystemControllerLog(method = "getOrderInfoDetail", logtype = ConstantIF.LOG_TYPE_1, description = "获取订单详情")
	@ApiOperation(value = "getOrderInfoDetail", httpMethod = "GET", notes = "获取订单详情", response = ResultBean.class)
	@RequestMapping(value = "/getOrderInfoDetail", method = RequestMethod.GET)
	public @ResponseBody ResultBean getOrderInfoDetail(
			@ApiParam(value = "token值") @RequestParam(value = ConstantIF.ACCESSTOKEN) String accesstoken,
			@ApiParam(value = "订单id") @RequestParam(value = "orderid", required = false) String orderid) {
		// 创建返回数据
		ResultBean result = new ResultBean();
		TokenModel tokens = redisTokenService.getToken(accesstoken);
		if (tokens != null) {
			String userid = tokens.getUserid();
			// 3.调库
			result = orderClassService.getOrderInfo(userid, orderid);
		} else {
			return new ResultBean("001", ResultBgMsg.C_001);
		}
		// 4.成功
		return result;
	}

	/**
	 * @Title: updateBgOrderPrice
	 * @Description: 修改订单金额以及订单备注
	 * @param orderid
	 *            订单id
	 * @param money
	 *            需要修改的金额
	 * @return
	 * @throws:
	 * @time: 2018年7月17日 上午9:38:16
	 */
	@SystemControllerLog(method = "updateBgOrderPriceOrMessage", logtype = ConstantIF.LOG_TYPE_1, description = "修改订单价格或客户备注")
	@ApiOperation(value = "updateBgOrderPriceOrMessage", httpMethod = "POST", notes = "修改订单价格或客户备注", response = ResultBean.class)
	@RequestMapping(value = "/updateBgOrderPriceOrMessage", method = RequestMethod.POST)
	public @ResponseBody ResultBean updateBgOrderPriceOrMessage(
			@ApiParam(value = "token值") @RequestParam(value = ConstantIF.ACCESSTOKEN) String accesstoken,
			@ApiParam(value = "订单id") @RequestParam(value = "orderid", required = true) String orderid,
			@ApiParam(value = "修改后的金额") @RequestParam(value = "money", required = false) String money,
			@ApiParam(value = "客户备注信息") @RequestParam(value = "custmessage", required = false) String custmessage,
			@ApiParam(value = "订单状态 01-待付款 02-待审核 03-正在生产 04 待发货 05 待收货 06 已收货21被动关闭  22主动关闭41发起售后  42待退货 43退货中 44售后完成61付款未拼满") @RequestParam(value = "orderstatus", required = false) String orderstatus,
			@ApiParam(value = "商品状态(10-自动分配 11-异常订单（生产不了） 12-手动分配订单到工厂（内部专用）13-待生产 14-生产完成 )") @RequestParam(value = "goodsstatus", required = false) String goodsstatus,
			@ApiParam(value = "支付状态 01-支付成功 02-支付失败 03-支付中 04- 发起退款 05-退款中 06-退款成功 07-退款失败") @RequestParam(value = "paystatus", required = false) String paystatus,
			@ApiParam(value = "工厂id") @RequestParam(value = "factoryid", required = false) String factoryid) {
		// 客户备注信息限制位数
		if (StringUtils.isNotEmpty(custmessage)) {
			int dalen = custmessage.length();
			if (dalen > 150) {
				return new ResultBean("2004", ResultBgMsg.C_2004);
			}
		}
		// 创建返回数据
		ResultBean result = new ResultBean();
		TokenModel tokens = redisTokenService.getToken(accesstoken);
		if (tokens != null) {
			String userid = tokens.getUserid();
			// 3.调库
			orderClassService.updateBgOrderPriceOrMessage(userid, orderid, money, custmessage, orderstatus, goodsstatus,
					paystatus, factoryid);
		} else {
			return new ResultBean("001", ResultBgMsg.C_001);
		}
		// 4.成功
		return result;
	}

	/**
	 * @Title: updateBgOrderConsigneeInfo
	 * @Description: 修改收件人信息
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
	 * @return
	 * @throws:
	 * @time: 2018年7月17日 上午10:20:00
	 */
	@SystemControllerLog(method = "updateBgOrderConsigneeInfo", logtype = ConstantIF.LOG_TYPE_1, description = "修改订单收件人信息")
	@ApiOperation(value = "updateBgOrderConsigneeInfo", httpMethod = "POST", notes = "修改订单收件人信息", response = ResultBean.class)
	@RequestMapping(value = "/updateBgOrderConsigneeInfo", method = RequestMethod.POST)
	public @ResponseBody ResultBean updateBgOrderConsigneeInfo(
			@ApiParam(value = "token值") @RequestParam(value = ConstantIF.ACCESSTOKEN) String accesstoken,
			@ApiParam(value = "订单id") @RequestParam(value = "orderid", required = true) String orderid,
			@ApiParam(value = "地区code") @RequestParam(value = "areacode", required = false) String areacode,
			@ApiParam(value = "姓名") @RequestParam(value = "name", required = false) String name,
			@ApiParam(value = "电话号码") @RequestParam(value = "telno", required = false) String telno,
			@ApiParam(value = "详细地址") @RequestParam(value = "detailaddr", required = false) String detailaddr) {
		/**
		 * 验证手机格式
		 */
		if (StringUtils.isNotEmpty(telno) && !RegexUtils.phoneRegex(telno)) {
			return new ResultBean("002", ResultBgMsg.C_002);
		}
		// 详细地址限制位数
		if (StringUtils.isNotEmpty(detailaddr)) {
			int dalen = detailaddr.length();
			if (dalen > 120) {
				return new ResultBean("2003", ResultBgMsg.C_2003);
			}
		}
		// 创建返回数据
		ResultBean result = new ResultBean();
		TokenModel tokens = redisTokenService.getToken(accesstoken);
		if (tokens != null) {
			String userid = tokens.getUserid();
			// 3.调库
			orderClassService.updateBgOrderConsigneeInfo(userid, orderid, areacode, name, telno, detailaddr);
		} else {
			return new ResultBean("001", ResultBgMsg.C_001);
		}
		// 4.成功
		return result;
	}

	/**
	 * @Title: queryLogisticsList
	 * @Description: 获取订单物流列表
	 * @param orderNo
	 *            商家订单编号
	 * @return
	 * @throws:
	 * @time: 2018年7月12日 下午5:04:03
	 */
	@SystemControllerLog(method = "queryLogisticsList", logtype = ConstantIF.LOG_TYPE_1, description = "获取订单物流列表")
	@ApiOperation(value = "queryLogisticsList", httpMethod = "POST", notes = "获取订单物流列表", response = ResultBean.class)
	@RequestMapping(value = "/queryLogisticsList", method = RequestMethod.POST)
	@UnCheckedFilter
	public @ResponseBody ResultBean queryLogisticsList(
			@ApiParam(value = "token值") @RequestParam(value = ConstantIF.ACCESSTOKEN) String accesstoken,
			@ApiParam(value = "商户订单号") @RequestParam(value = "orderNo") String orderNo) {
		return logisticsService.queryBgLwbByCondition(orderNo);
	}

	/**
	 * @Title: confirmDelivery
	 * @Description: 确认发货
	 * @param accesstoken
	 *            token值
	 * @param orderid
	 *            订单id
	 * @param orderstatus
	 *            订单状态 01-待付款 02-待审核 03-正在生产 04 待发货 05 待收货 06 已收货21被动关闭 22主动关闭41发起售后
	 *            42待退货 43退货中 44售后完成61付款未拼满
	 * @param pickupDate
	 *            上门揽货时间(yyyy/MM/dd)
	 * @param receiptFlag
	 *            签单返还（0无签单，1纸质签单，2电子签单，3纸质签单和电子签单）
	 * @param receiverPhone
	 *            收货人座机(手机和座机必须填一个)
	 * @param rtnReceiverName
	 *            返单收件人姓名
	 * @param rtnReceiverMob
	 *            返单收件人手机号
	 * @param rtnReceiverAddr
	 *            返单收件人地址
	 * @param rtnReceiverPhone
	 *            返单收件人电话
	 * @param predictDate
	 *            预计到仓时间
	 * @param senderName
	 *            寄件人姓名
	 * @param senderMob
	 *            寄件人手机（寄件人手机(手机和座机必须填一个)）
	 * @param senderPhone
	 *            寄件人电话（寄件人座机(手机和座机必须填一个)）
	 * @param senderAddr
	 *            寄件人地址
	 * @param sendTo
	 *            始发转运中心名称（上海:上海大件分拣中心、佛山:佛山大件分拣中心、成都:成都大件分拣中心）
	 * @param paperFrom
	 *            纸质单来源（1带单，2取单，3带单和取单）
	 * @return
	 * @throws:
	 * @time: 2018年8月3日 下午4:09:18
	 */
	@SystemControllerLog(method = "confirmDelivery", logtype = ConstantIF.LOG_TYPE_1, description = "确认发货")
	@ApiOperation(value = "confirmDelivery", httpMethod = "POST", notes = "确认发货", response = ResultBean.class)
	@RequestMapping(value = "/confirmDelivery", method = RequestMethod.POST)
	public @ResponseBody ResultBean confirmDelivery(
			@ApiParam(value = "token值") @RequestParam(value = ConstantIF.ACCESSTOKEN) String accesstoken,
			@ApiParam(value = "订单id") @RequestParam(value = "orderid", required = true) String orderid,
			@ApiParam(value = "订单状态 01-待付款 02-待审核 03-正在生产 04 待发货 05 待收货 06 已收货21被动关闭  22主动关闭41发起售后  42待退货 43退货中 44售后完成61付款未拼满") @RequestParam(value = "orderstatus", required = false) String orderstatus,
			@ApiParam(value = "上门揽货时间(yyyy/MM/dd)") @RequestParam(value = "pickupDate") String pickupDate,
			@ApiParam(value = "签单返还（0无签单，1纸质签单，2电子签单，3纸质签单和电子签单）") @RequestParam(value = "receiptFlag", required = false) String receiptFlag,
			@ApiParam(value = "收货人座机(手机和座机必须填一个)") @RequestParam(value = "receiverPhone", required = false) String receiverPhone,
			@ApiParam(value = "返单收件人姓名") @RequestParam(value = "rtnReceiverName", required = false) String rtnReceiverName,
			@ApiParam(value = "返单收件人手机号") @RequestParam(value = "rtnReceiverMob", required = false) String rtnReceiverMob,
			@ApiParam(value = "返单收件人地址") @RequestParam(value = "rtnReceiverAddr", required = false) String rtnReceiverAddr,
			@ApiParam(value = "返单收件人电话") @RequestParam(value = "rtnReceiverPhone", required = false) String rtnReceiverPhone,
			@ApiParam(value = "预计到仓时间") @RequestParam(value = "predictDate", required = false) String predictDate,
			@ApiParam(value = "寄件人姓名") @RequestParam(value = "senderName") String senderName,
			@ApiParam(value = "寄件人手机（寄件人手机(手机和座机必须填一个)）") @RequestParam(value = "senderMob", required = false) String senderMob,
			@ApiParam(value = "寄件人电话（寄件人座机(手机和座机必须填一个)）") @RequestParam(value = "senderPhone", required = false) String senderPhone,
			@ApiParam(value = "寄件人地址") @RequestParam(value = "senderAddr") String senderAddr,
			@ApiParam(value = "始发转运中心名称（上海:上海大件分拣中心、佛山:佛山大件分拣中心、成都:成都大件分拣中心）") @RequestParam(value = "sendTo") String sendTo,
			@ApiParam(value = "纸质单来源（1带单，2取单，3带单和取单）") @RequestParam(value = "paperFrom", required = false) String paperFrom) {

		// 创建返回数据
		ResultBean result = new ResultBean();
		TokenModel tokens = redisTokenService.getToken(accesstoken);
		if (tokens != null) {
			String userid = tokens.getUserid();
			// 3.调库
			result = orderClassService.updateConfirmDelivery(userid, orderid, orderstatus, pickupDate, receiptFlag,
					receiverPhone, rtnReceiverName, rtnReceiverMob, rtnReceiverAddr, rtnReceiverPhone, predictDate,
					senderName, senderMob, senderPhone, senderAddr, sendTo, paperFrom);
		} else {
			return new ResultBean("001", ResultBgMsg.C_001);
		}
		// 4.成功
		return result;
	}

	/**
	 * @Title: cancelLwbMain
	 * @Description: 大件取消接口
	 * @param orderNo
	 *            商家订单编号
	 * @return
	 * @throws:
	 * @time: 2018年7月12日 下午5:02:24
	 */
	@SystemControllerLog(method = "cancelLwbMain", logtype = ConstantIF.LOG_TYPE_1, description = "大件取消接口")
	@ApiOperation(value = "cancelLwbMain", httpMethod = "POST", notes = "大件取消接口", response = ResultBean.class)
	@RequestMapping(value = "/cancelLwbMain", method = RequestMethod.POST)
	@UnCheckedFilter
	public @ResponseBody ResultBean cancelLwbMain(
			@ApiParam(value = "是:最大长度：200，商家订单编号") @RequestParam(value = "orderNo") String orderNo,
			@ApiParam(value = "物流类型    01：订单发货   02：退/换货寄回仓库    03：退货验收失败退回    04：换货重新发货    05：换货验收失败退回") @RequestParam(value = "deliverytype") String deliverytype) {
		ResultBean rb = null;
		List<String> deliveryNos = orderClassService.getDeliveryId(orderNo,deliverytype);
		if (deliveryNos !=null && deliveryNos.size()>0) {
			for (int i = 0; i < deliveryNos.size(); i++) {
				rb = jdTransportService.cancelLwbMain(deliveryNos.get(i));
				if ("200".equals(rb.getCode())) {
					orderClassService.delLwbMain(orderNo);
				}
			}
		}else {
			return new ResultBean("2013",ResultBgMsg.C_2013);
		}
		return rb;
	}
	
	/**
	 * @Title: closeOrder
	 * @Description: 订单关闭
	 * @param orderNo
	 * @param deliverytype
	 * @return
	 * @throws:
	 * @time: 2018年8月24日 上午9:47:35
	 * @author cjy
	 */
	@SystemControllerLog(method = "closeOrder", logtype = ConstantIF.LOG_TYPE_1, description = "订单关闭")
	@ApiOperation(value = "closeOrder", httpMethod = "POST", notes = "订单关闭", response = ResultBean.class)
	@RequestMapping(value = "/closeOrder", method = RequestMethod.POST)
	public @ResponseBody ResultBean closeOrder(
			@ApiParam(value = "token值") @RequestParam(value = ConstantIF.ACCESSTOKEN) String accesstoken,
			@ApiParam(value = "订单编号") @RequestParam(value = "orderNo") String orderNo) {
		TokenModel tokens = redisTokenService.getToken(accesstoken);
		return orderClassService.closeOrder(tokens.getUserid(), orderNo);
	}

}
