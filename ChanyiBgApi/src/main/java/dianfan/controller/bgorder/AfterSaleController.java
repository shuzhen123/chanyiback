/**  
* @Title: OrderListController.java
* @Package dianfan.controller
* @Description: TODO
* @author yl
* @date 2018年7月16日 上午11:51:37
* @version V1.0  
*/
package dianfan.controller.bgorder;

import java.util.HashMap;
import java.util.Map;

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
import dianfan.constant.ConstantIF;
import dianfan.constant.ResultBgMsg;
import dianfan.models.ResultBean;
import dianfan.models.TokenModel;
import dianfan.service.impl.RedisTokenService;
import dianfan.service.order.AfterSaleService;
import dianfan.service.order.OrderClassService;

/**
 * @ClassName AfterSaleController
 * @Description 售后管理
 * @author cjy
 * @date 2018年8月7日 上午10:17:14
 */
@Scope("request")
@Controller
@RequestMapping(value = "/after")
public class AfterSaleController {

	@Autowired
	private AfterSaleService afterSaleService;
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

	/**
	 * @Title: findAfterSaleList
	 * @Description: 获取售后数据列表
	 * @return
	 * @throws:
	 * @time: 2018年8月7日 下午1:16:07
	 * @author cjy
	 */
	@SystemControllerLog(method = "findAfterSaleList", logtype = ConstantIF.LOG_TYPE_1, description = "获取售后数据列表")
	@ApiOperation(value = "findAfterSaleList", httpMethod = "GET", notes = "获取售后数据列表", response = ResultBean.class)
	@RequestMapping(value = "/findAfterSaleList", method = RequestMethod.GET)
	public @ResponseBody ResultBean findAfterSaleList(
			@ApiParam(value = "请求页") @RequestParam(value = ConstantIF.PAGE, defaultValue = ConstantIF.PAGE_START_STR) int page,
			@ApiParam(value = "每页条数") @RequestParam(value = ConstantIF.PAGE_SIZE, defaultValue = ConstantIF.PAGE_OFFSET_STR) int pageSize,
			
			@ApiParam(value = "订单id") @RequestParam(value = "orderid", required=false) String orderid,
			@ApiParam(value = "收件人姓名") @RequestParam(value = "name", required=false) String name,
			@ApiParam(value = "订单类型(01:正常下单02：易拼 03：易团)") @RequestParam(value = "orderType", required=false) String orderType,
			@ApiParam(value = "支付方式(01-ALI(支付宝) 02-WX（微信） 03-BC（银行卡）)") @RequestParam(value = "payStyle", required=false) String payStyle,
			@ApiParam(value = "价格区间-高") @RequestParam(value = "price_h", required=false) String price_h,
			@ApiParam(value = "价格区间-低") @RequestParam(value = "price_l", required=false) String price_l,
			@ApiParam(value = "开始时间(格式 2018-08-07 12:03:04)") @RequestParam(value = "startTime", required=false) String startTime,
			@ApiParam(value = "结束时间(格式 2018-08-07 12:03:04)") @RequestParam(value = "endTime", required=false) String endTime
			) {
		Map<String, Object> param = new HashMap<>();
		param.put("orderid", orderid);
		param.put("name", name);
		param.put("orderType", orderType);
		param.put("payStyle", payStyle);
		param.put("price_h", price_h);
		param.put("price_l", price_l);
		param.put("startTime", startTime);
		param.put("endTime", endTime);
		return afterSaleService.findAfterSaleList(page, pageSize, param);
	}
	
	/**
	 * @Title: returnGoodsApprove
	 * @Description: 退货审批
	 * @param accesstoken
	 *            token值
	 * @param orderid
	 *            订单id
	 * @param result
	 *            结果（01:待审核02：拒绝03：通过04：通知京东上门取货（填收货地址）待取货 07：待收货 08：待签收 09：验收不成功
	 *            10： 退货验收成功并退款11 ：退货验收不成功 重新发货 12： 换货验收成功重新发货 13：换货验收不成功 重新发货 ）
	 * @param pickupDate
	 *            上门揽货时间(yyyy/MM/dd)
	 * @param sendTo
	 *            始发转运中心名称（上海:上海大件分拣中心、佛山:佛山大件分拣中心、成都:成都大件分拣中心）
	 * @return
	 * @throws:
	 * @time: 2018年8月3日 下午4:09:18
	 */
	@SystemControllerLog(method = "returnGoodsApprove", logtype = ConstantIF.LOG_TYPE_1, description = "退货审批")
	@ApiOperation(value = "returnGoodsApprove", httpMethod = "POST", notes = "退货审批", response = ResultBean.class)
	@RequestMapping(value = "/returnGoodsApprove", method = RequestMethod.POST)
	public @ResponseBody ResultBean returnGoodsApprove(
			@ApiParam(value = "token值") @RequestParam(value = ConstantIF.ACCESSTOKEN) String accesstoken,
			@ApiParam(value = "订单id") @RequestParam(value = "orderid", required = true) String orderid,
			@ApiParam(value = "售后状态    01：待审核    02：拒绝    03：待取货    04：待收货    05：待验收    以下对应售后类型和验收结果选择不同路线21：退货验收不成功 - 待取货    22：退货验收不成功 - 待收货    41：退货验收成功 - 已退款        61：换货验收不成功 - 待取货 62：换货验收不成功 - 待收货    81：换货验收成功 - 待取货    82：换货验收成功 - 待收货99：售后完成") @RequestParam(value = "result", required = true) String result,
			@ApiParam(value = "上门揽货时间(yyyy/MM/dd)") @RequestParam(value = "pickupDate",required=false) String pickupDate,
			@ApiParam(value = "拒绝理由") @RequestParam(value = "resultfreason", required = false) String resultfreason,
			@ApiParam(value = "预计到仓时间") @RequestParam(value = "predictDate", required = false) String predictDate,
			@ApiParam(value = "寄件人姓名") @RequestParam(value = "senderName",required=false) String senderName,
			@ApiParam(value = "寄件人手机（寄件人手机(手机和座机必须填一个)）") @RequestParam(value = "senderMob", required = false) String senderMob,
			@ApiParam(value = "寄件人电话（寄件人座机(手机和座机必须填一个)）") @RequestParam(value = "senderPhone", required = false) String senderPhone,
			@ApiParam(value = "寄件人地址") @RequestParam(value = "senderAddr",required=false) String senderAddr,
			@ApiParam(value = "签单返还（0无签单，1纸质签单，2电子签单，3纸质签单和电子签单）") @RequestParam(value = "receiptFlag", required = false) String receiptFlag,
			@ApiParam(value = "返单收件人姓名") @RequestParam(value = "rtnReceiverName", required = false) String rtnReceiverName,
			@ApiParam(value = "返单收件人手机号") @RequestParam(value = "rtnReceiverMob", required = false) String rtnReceiverMob,
			@ApiParam(value = "返单收件人地址") @RequestParam(value = "rtnReceiverAddr", required = false) String rtnReceiverAddr,
			@ApiParam(value = "返单收件人电话") @RequestParam(value = "rtnReceiverPhone", required = false) String rtnReceiverPhone,
			@ApiParam(value = "纸质单来源（1带单，2取单，3带单和取单）") @RequestParam(value = "paperFrom", required = false) String paperFrom,
			@ApiParam(value = "售后id") @RequestParam(value = "afterSaleId", required = true) String afterSaleId,
			@ApiParam(value = "始发转运中心名称（上海:上海大件分拣中心、佛山:佛山大件分拣中心、成都:成都大件分拣中心）") @RequestParam(value = "sendTo",required=false) String sendTo) {

		// 创建返回数据
		ResultBean results = new ResultBean();
		TokenModel tokens = redisTokenService.getToken(accesstoken);
		if (tokens != null) {
			String userid = tokens.getUserid();
			// 3.调库
			results = orderClassService.updateReturnGoodsApprove(userid, orderid, result, pickupDate, resultfreason,
					predictDate,senderName, senderMob, senderPhone, senderAddr,receiptFlag,rtnReceiverName, rtnReceiverMob, rtnReceiverAddr, rtnReceiverPhone,paperFrom,afterSaleId, sendTo);
		} else {
			return new ResultBean("001", ResultBgMsg.C_001);
		}
		// 4.成功
		return results;
	}
	@SystemControllerLog(method = "barterGoodsApprove", logtype = ConstantIF.LOG_TYPE_1, description = "换货审批")
	@ApiOperation(value = "barterGoodsApprove", httpMethod = "POST", notes = "退货审批", response = ResultBean.class)
	@RequestMapping(value = "/barterGoodsApprove", method = RequestMethod.POST)
	public @ResponseBody ResultBean barterGoodsApprove(
			@ApiParam(value = "token值") @RequestParam(value = ConstantIF.ACCESSTOKEN) String accesstoken,
			@ApiParam(value = "订单id") @RequestParam(value = "orderid", required = true) String orderid,
			@ApiParam(value = "售后状态    01：待审核    02：拒绝    03：待取货    04：待收货    05：待验收    以下对应售后类型和验收结果选择不同路线21：退货验收不成功 - 待取货    22：退货验收不成功 - 待收货    41：退货验收成功 - 已退款        61：换货验收不成功 - 待取货 62：换货验收不成功 - 待收货    81：换货验收成功 - 待取货    82：换货验收成功 - 待收货99：售后完成") @RequestParam(value = "result", required = true) String result,
			@ApiParam(value = "上门揽货时间(yyyy/MM/dd)") @RequestParam(value = "pickupDate",required=false) String pickupDate,
			@ApiParam(value = "拒绝理由") @RequestParam(value = "resultfreason", required = false) String resultfreason,
			@ApiParam(value = "预计到仓时间") @RequestParam(value = "predictDate", required = false) String predictDate,
			@ApiParam(value = "寄件人姓名") @RequestParam(value = "senderName",required=false) String senderName,
			@ApiParam(value = "寄件人手机（寄件人手机(手机和座机必须填一个)）") @RequestParam(value = "senderMob", required = false) String senderMob,
			@ApiParam(value = "寄件人电话（寄件人座机(手机和座机必须填一个)）") @RequestParam(value = "senderPhone", required = false) String senderPhone,
			@ApiParam(value = "寄件人地址") @RequestParam(value = "senderAddr",required=false) String senderAddr,
			@ApiParam(value = "签单返还（0无签单，1纸质签单，2电子签单，3纸质签单和电子签单）") @RequestParam(value = "receiptFlag", required = false) String receiptFlag,
			@ApiParam(value = "返单收件人姓名") @RequestParam(value = "rtnReceiverName", required = false) String rtnReceiverName,
			@ApiParam(value = "返单收件人手机号") @RequestParam(value = "rtnReceiverMob", required = false) String rtnReceiverMob,
			@ApiParam(value = "返单收件人地址") @RequestParam(value = "rtnReceiverAddr", required = false) String rtnReceiverAddr,
			@ApiParam(value = "返单收件人电话") @RequestParam(value = "rtnReceiverPhone", required = false) String rtnReceiverPhone,
			@ApiParam(value = "纸质单来源（1带单，2取单，3带单和取单）") @RequestParam(value = "paperFrom", required = false) String paperFrom,
			@ApiParam(value = "售后id") @RequestParam(value = "afterSaleId", required = true) String afterSaleId,
			@ApiParam(value = "始发转运中心名称（上海:上海大件分拣中心、佛山:佛山大件分拣中心、成都:成都大件分拣中心）") @RequestParam(value = "sendTo",required=false) String sendTo) {
		
		// 创建返回数据
		ResultBean results = new ResultBean();
		TokenModel tokens = redisTokenService.getToken(accesstoken);
		if (tokens != null) {
			String userid = tokens.getUserid();
			// 3.调库
			results = orderClassService.updateBarterGoodsApprove(userid, orderid, result, pickupDate, resultfreason,
					predictDate,senderName, senderMob, senderPhone, senderAddr,receiptFlag,rtnReceiverName, rtnReceiverMob, rtnReceiverAddr, rtnReceiverPhone,paperFrom,afterSaleId, sendTo);
		} else {
			return new ResultBean("001", ResultBgMsg.C_001);
		}
		// 4.成功
		return results;
	}
	/**
	 * @Title: returnGoodsApproveResult
	 * @Description: 退货结果审批返回
	 * @param accesstoken
	 *            token值
	 * @param orderid
	 *            订单id
	 * @param result
	 *            结果（01:待审核02：拒绝03：通过04：通知京东上门取货（填收货地址）待取货 07：待收货 08：待签收 09：验收不成功
	 *            10： 退货验收成功并退款11 ：退货验收不成功 重新发货 12： 换货验收成功重新发货 13：换货验收不成功 重新发货 ）
	 * @param pickupDate
	 *            上门揽货时间(yyyy/MM/dd)
	 * @param sendTo
	 *            始发转运中心名称（上海:上海大件分拣中心、佛山:佛山大件分拣中心、成都:成都大件分拣中心）
	 * @return
	 * @throws:
	 * @time: 2018年8月3日 下午4:09:18
	 */
	@SystemControllerLog(method = "returnGoodsApproveResult", logtype = ConstantIF.LOG_TYPE_1, description = "退货结果审批")
	@ApiOperation(value = "returnGoodsApproveResult", httpMethod = "POST", notes = "退货结果审批", response = ResultBean.class)
	@RequestMapping(value = "/returnGoodsApproveResult", method = RequestMethod.POST)
	public @ResponseBody ResultBean returnGoodsApproveResult(
			@ApiParam(value = "token值") @RequestParam(value = ConstantIF.ACCESSTOKEN) String accesstoken,
			@ApiParam(value = "订单id") @RequestParam(value = "orderid", required = true) String orderid,
			@ApiParam(value = "售后状态    01：待审核    02：拒绝    03：待取货    04：待收货    05：待验收    以下对应售后类型和验收结果选择不同路线21：退货验收不成功 - 待取货    22：退货验收不成功 - 待收货    41：退货验收成功 - 已退款        61：换货验收不成功 - 待取货 62：换货验收不成功 - 待收货    81：换货验收成功 - 待取货    82：换货验收成功 - 待收货99：售后完成") @RequestParam(value = "result", required = true) String result,
			@ApiParam(value = "上门揽货时间(yyyy/MM/dd)") @RequestParam(value = "pickupDate",required=false) String pickupDate,
			@ApiParam(value = "拒绝理由") @RequestParam(value = "resultfreason", required = false) String resultfreason,
			@ApiParam(value = "预计到仓时间") @RequestParam(value = "predictDate", required = false) String predictDate,
			@ApiParam(value = "收件人姓名") @RequestParam(value = "receiverName",required=false) String receiverName,
			@ApiParam(value = "收件人手机号") @RequestParam(value = "receiverMob",required=false) String receiverMob,
			@ApiParam(value = "收件人地址") @RequestParam(value = "receiverAddr",required=false) String receiverAddr,
			@ApiParam(value = "收件人电话") @RequestParam(value = "receiverPhone", required = false) String receiverPhone,
			@ApiParam(value = "签单返还（0无签单，1纸质签单，2电子签单，3纸质签单和电子签单）") @RequestParam(value = "receiptFlag", required = false) String receiptFlag,
			@ApiParam(value = "返单收件人姓名") @RequestParam(value = "rtnReceiverName", required = false) String rtnReceiverName,
			@ApiParam(value = "返单收件人手机号") @RequestParam(value = "rtnReceiverMob", required = false) String rtnReceiverMob,
			@ApiParam(value = "返单收件人地址") @RequestParam(value = "rtnReceiverAddr", required = false) String rtnReceiverAddr,
			@ApiParam(value = "返单收件人电话") @RequestParam(value = "rtnReceiverPhone", required = false) String rtnReceiverPhone,
			@ApiParam(value = "纸质单来源（1带单，2取单，3带单和取单）") @RequestParam(value = "paperFrom", required = false) String paperFrom,
			@ApiParam(value = "售后id") @RequestParam(value = "afterSaleId", required = true) String afterSaleId,
			@ApiParam(value = "始发转运中心名称（上海:上海大件分拣中心、佛山:佛山大件分拣中心、成都:成都大件分拣中心）") @RequestParam(value = "sendTo",required=false) String sendTo) {
		
		// 创建返回数据
		ResultBean results = new ResultBean();
		TokenModel tokens = redisTokenService.getToken(accesstoken);
		if (tokens != null) {
			String userid = tokens.getUserid();
			// 3.调库
			results = orderClassService.updateReturnGoodsApproveResult(userid, orderid, result, pickupDate, resultfreason,
					predictDate,receiverName, receiverMob, receiverPhone, receiverAddr,receiptFlag,rtnReceiverName, rtnReceiverMob, rtnReceiverAddr, rtnReceiverPhone,paperFrom,afterSaleId, sendTo);
		} else {
			return new ResultBean("001", ResultBgMsg.C_001);
		}
		// 4.成功
		return results;
	}
	/**
	 * @Title: barterGoodsApproveResult
	 * @Description: 换货结果审批返回
	 * @param accesstoken
	 *            token值
	 * @param orderid
	 *            订单id
	 * @param result
	 *            结果（01:待审核02：拒绝03：通过04：通知京东上门取货（填收货地址）待取货 07：待收货 08：待签收 09：验收不成功
	 *            10： 退货验收成功并退款11 ：退货验收不成功 重新发货 12： 换货验收成功重新发货 13：换货验收不成功 重新发货 ）
	 * @param pickupDate
	 *            上门揽货时间(yyyy/MM/dd)
	 * @param sendTo
	 *            始发转运中心名称（上海:上海大件分拣中心、佛山:佛山大件分拣中心、成都:成都大件分拣中心）
	 * @return
	 * @throws:
	 * @time: 2018年8月3日 下午4:09:18
	 */
	@SystemControllerLog(method = "barterGoodsApproveResult", logtype = ConstantIF.LOG_TYPE_1, description = "换货结果审批")
	@ApiOperation(value = "barterGoodsApproveResult", httpMethod = "POST", notes = "换货结果审批", response = ResultBean.class)
	@RequestMapping(value = "/barterGoodsApproveResult", method = RequestMethod.POST)
	public @ResponseBody ResultBean barterGoodsApproveResult(
			@ApiParam(value = "token值") @RequestParam(value = ConstantIF.ACCESSTOKEN) String accesstoken,
			@ApiParam(value = "订单id") @RequestParam(value = "orderid", required = true) String orderid,
			@ApiParam(value = "售后状态    01：待审核    02：拒绝    03：待取货    04：待收货    05：待验收    以下对应售后类型和验收结果选择不同路线21：退货验收不成功 - 待取货    22：退货验收不成功 - 待收货    41：退货验收成功 - 已退款        61：换货验收不成功 - 待取货 62：换货验收不成功 - 待收货    81：换货验收成功 - 待取货    82：换货验收成功 - 待收货99：售后完成") @RequestParam(value = "result", required = true) String result,
			@ApiParam(value = "上门揽货时间(yyyy/MM/dd)") @RequestParam(value = "pickupDate",required=false) String pickupDate,
			@ApiParam(value = "拒绝理由") @RequestParam(value = "resultfreason", required = false) String resultfreason,
			@ApiParam(value = "预计到仓时间") @RequestParam(value = "predictDate", required = false) String predictDate,
			@ApiParam(value = "收件人姓名") @RequestParam(value = "receiverName",required=false) String receiverName,
			@ApiParam(value = "收件人手机号") @RequestParam(value = "receiverMob",required=false) String receiverMob,
			@ApiParam(value = "收件人地址") @RequestParam(value = "receiverAddr",required=false) String receiverAddr,
			@ApiParam(value = "收件人电话") @RequestParam(value = "receiverPhone", required = false) String receiverPhone,
			@ApiParam(value = "签单返还（0无签单，1纸质签单，2电子签单，3纸质签单和电子签单）") @RequestParam(value = "receiptFlag", required = false) String receiptFlag,
			@ApiParam(value = "返单收件人姓名") @RequestParam(value = "rtnReceiverName", required = false) String rtnReceiverName,
			@ApiParam(value = "返单收件人手机号") @RequestParam(value = "rtnReceiverMob", required = false) String rtnReceiverMob,
			@ApiParam(value = "返单收件人地址") @RequestParam(value = "rtnReceiverAddr", required = false) String rtnReceiverAddr,
			@ApiParam(value = "返单收件人电话") @RequestParam(value = "rtnReceiverPhone", required = false) String rtnReceiverPhone,
			@ApiParam(value = "纸质单来源（1带单，2取单，3带单和取单）") @RequestParam(value = "paperFrom", required = false) String paperFrom,
			@ApiParam(value = "售后id") @RequestParam(value = "afterSaleId", required = true) String afterSaleId,
			@ApiParam(value = "始发转运中心名称（上海:上海大件分拣中心、佛山:佛山大件分拣中心、成都:成都大件分拣中心）") @RequestParam(value = "sendTo",required=false) String sendTo) {
		
		// 创建返回数据
		ResultBean results = new ResultBean();
		TokenModel tokens = redisTokenService.getToken(accesstoken);
		if (tokens != null) {
			String userid = tokens.getUserid();
			// 3.调库
			results = orderClassService.updateBarterGoodsApproveResult(userid, orderid, result, pickupDate, resultfreason,
					predictDate,receiverName, receiverMob, receiverPhone, receiverAddr,receiptFlag,rtnReceiverName, rtnReceiverMob, rtnReceiverAddr, rtnReceiverPhone,paperFrom,afterSaleId, sendTo);
		} else {
			return new ResultBean("001", ResultBgMsg.C_001);
		}
		// 4.成功
		return results;
	}


}
