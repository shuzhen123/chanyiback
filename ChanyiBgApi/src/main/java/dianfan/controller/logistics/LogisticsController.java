/**  
* @Title: LogisticsController.java
* @Package dianfan.controller.logistics
* @Description: TODO
* @author yl
* @date 2018年7月20日 上午10:52:08
* @version V1.0  
*/
package dianfan.controller.logistics;

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
import dianfan.service.logistics.LogisticsService;

/**
 * @ClassName LogisticsController
 * @Description
 * @author yl
 * @date 2018年7月20日 上午10:52:08
 */
@Scope("request")
@Controller
@RequestMapping(value = "/logistics")
public class LogisticsController {

	@Autowired
	private LogisticsService logisticsService;
	/**
	 * 注入： #RedisTokenService
	 */
	@Autowired
	private RedisTokenService redisTokenService;

	@SystemControllerLog(method = "findLogisticsList", logtype = ConstantIF.LOG_TYPE_1, description = "获取订单物流列表")
	@ApiOperation(value = "findLogisticsList", httpMethod = "GET", notes = "获取订单物流列表", response = ResultBean.class)
	@RequestMapping(value = "/findLogisticsList", method = RequestMethod.GET)
	public @ResponseBody ResultBean findLogisticsList(
			@ApiParam(value = "token值") @RequestParam(value = ConstantIF.ACCESSTOKEN) String accesstoken,
			@ApiParam(value = "商户单号(不重复)") @RequestParam(value = "mId", required = false) String mId,
			@ApiParam(value = "物流单号") @RequestParam(value = "deliveryNo", required = false) String deliveryNo,
			@ApiParam(value = "京东单号") @RequestParam(value = "jdNo", required = false) String jdNo,
			@ApiParam(value = "事业部编号") @RequestParam(value = "deptNo", required = false) String deptNo,
			@ApiParam(value = "快递单号") @RequestParam(value = "expressNo", required = false) String expressNo,
			@ApiParam(value = "寄件人姓名") @RequestParam(value = "senderName", required = false) String senderName,
			@ApiParam(value = "寄件人手机") @RequestParam(value = "senderMob", required = false) String senderMob,
			@ApiParam(value = "寄件人电话") @RequestParam(value = "senderPhone", required = false) String senderPhone,
			@ApiParam(value = "收件人姓名") @RequestParam(value = "receiverName", required = false) String receiverName,
			@ApiParam(value = "收件人手机") @RequestParam(value = "receiverMob", required = false) String receiverMob,
			@ApiParam(value = "收件人电话") @RequestParam(value = "receiverPhone", required = false) String receiverPhone,
			@ApiParam(value = "返单收件人姓名") @RequestParam(value = "rtnReceiverName", required = false) String rtnReceiverName,
			@ApiParam(value = "返单收件人手机号") @RequestParam(value = "rtnReceiverMob", required = false) String rtnReceiverMob,
			@ApiParam(value = "返单收件人电话") @RequestParam(value = "rtnReceiverPhone", required = false) String rtnReceiverPhone,
			@ApiParam(value = "Start(创建时间)") @RequestParam(value = "createtimestart", required = false) String createtimestart,
			@ApiParam(value = "End(创建时间)") @RequestParam(value = "createtimeend", required = false) String createtimeend,
			@ApiParam(value = "请求页") @RequestParam(value = ConstantIF.PAGE, defaultValue = ConstantIF.PAGE_START_STR) int page,
			@ApiParam(value = "每页条数") @RequestParam(value = ConstantIF.PAGE_SIZE, defaultValue = ConstantIF.PAGE_OFFSET_STR) int pageSize) {
		ResultBean result = new ResultBean();
		TokenModel tokens = redisTokenService.getToken(accesstoken);
		if (tokens != null) {
			// 创建返回数据
			result = logisticsService.findLogisticsInfo(mId,jdNo, deliveryNo, deptNo, expressNo, senderName, senderMob,
					senderPhone, receiverName, receiverMob, receiverPhone, rtnReceiverName, rtnReceiverMob,
					rtnReceiverPhone, createtimestart, createtimeend, page, pageSize);
		} else {
			return new ResultBean("001", ResultBgMsg.C_001);
		}
		return result;
	}

	@SystemControllerLog(method = "addLogistics", logtype = ConstantIF.LOG_TYPE_1, description = "添加物流")
	@ApiOperation(value = "addLogistics", httpMethod = "GET", notes = "添加物流", response = ResultBean.class)
	@RequestMapping(value = "/addLogistics", method = RequestMethod.GET)
	public @ResponseBody ResultBean addLogistics(
			@ApiParam(value = "token值") @RequestParam(value = ConstantIF.ACCESSTOKEN) String accesstoken,
			@ApiParam(value = "商户单号(不重复)") @RequestParam(value = "mId", required = true) String mId,
			@ApiParam(value = "物流单号") @RequestParam(value = "deliveryNo", required = true) String deliveryNo,
			@ApiParam(value = "事业部编号") @RequestParam(value = "deptNo", required = false) String deptNo,
			@ApiParam(value = "快递单号") @RequestParam(value = "expressNo", required = true) String expressNo,
			@ApiParam(value = "寄件人姓名") @RequestParam(value = "senderName", required = true) String senderName,
			@ApiParam(value = "寄件人手机") @RequestParam(value = "senderMob", required = true) String senderMob,
			@ApiParam(value = "寄件人电话") @RequestParam(value = "senderPhone", required = false) String senderPhone,
			@ApiParam(value = "寄件人地址") @RequestParam(value = "senderAddr", required = true) String senderAddr,
			@ApiParam(value = "收件人姓名") @RequestParam(value = "receiverName", required = true) String receiverName,
			@ApiParam(value = "收件人手机") @RequestParam(value = "receiverMob", required = true) String receiverMob,
			@ApiParam(value = "收件人电话") @RequestParam(value = "receiverPhone", required = false) String receiverPhone,
			@ApiParam(value = "收货人地址") @RequestParam(value = "receiverAddr", required = true) String receiverAddr,
			@ApiParam(value = "订单备注") @RequestParam(value = "remark", required = false) String remark,
			@ApiParam(value = "是否易碎（1：是2：否）") @RequestParam(value = "isFragile", required = true, defaultValue = "2") String isFragile,
			@ApiParam(value = "始发转运中心名称") @RequestParam(value = "sendTo", required = false) String sendTo,
			@ApiParam(value = "预计到仓时间") @RequestParam(value = "predictDate", required = false) String predictDate,
			@ApiParam(value = "是否货到付款（1：是 0：否）") @RequestParam(value = "isCod", required = true, defaultValue = "0") String isCod,
			@ApiParam(value = "代收金额") @RequestParam(value = "receiveable", required = false) String receiveable,
			@ApiParam(value = "上门揽件标记（1：是2：否）") @RequestParam(value = "onDoorPickUp", required = true, defaultValue = "1") String onDoorPickUp,
			@ApiParam(value = "送货时间") @RequestParam(value = "expressTimeReq", required = false) String expressTimeReq,
			@ApiParam(value = "是否保价（1：是2：否）") @RequestParam(value = "isGuarantee", required = true, defaultValue = "1") String isGuarantee,
			@ApiParam(value = "京东单号") @RequestParam(value = "jdNo", required = true) String jdNo,
			@ApiParam(value = "保价金额(如果选择保价此项必填)") @RequestParam(value = "guaranteeMoney", required = false) String guaranteeMoney,
			@ApiParam(value = "签单返还（0无签单，1纸质签单，2电子签单，3纸质签单和电子签单）") @RequestParam(value = "receiptFlag", required = true, defaultValue = "2") String receiptFlag,
			@ApiParam(value = "上门揽件时间") @RequestParam(value = "pickupDate", required = false) String pickupDate,
			@ApiParam(value = "纸质来源（1带单2取单3带单和取单）") @RequestParam(value = "paperFrom", required = false) String paperFrom,
			@ApiParam(value = "返单收件人姓名") @RequestParam(value = "rtnReceiverName", required = false) String rtnReceiverName,
			@ApiParam(value = "返单收件人手机号") @RequestParam(value = "rtnReceiverMob", required = false) String rtnReceiverMob,
			@ApiParam(value = "返单收件人电话") @RequestParam(value = "rtnReceiverPhone", required = false) String rtnReceiverPhone,
			@ApiParam(value = "返单收件人电话") @RequestParam(value = "rtnReceiverAddr", required = false) String rtnReceiverAddr,
			@ApiParam(value = "重量（kg）") @RequestParam(value = "weight", required = true) String weight,
			@ApiParam(value = "长度(mm)") @RequestParam(value = "length", required = true) String length,
			@ApiParam(value = "宽度(mm)") @RequestParam(value = "width", required = true) String width,
			@ApiParam(value = "高度(mm)") @RequestParam(value = "height", required = true) String height,
			@ApiParam(value = "是否安维") @RequestParam(value = "installFlag", required = false) String installFlag,
			@ApiParam(value = "三级分类编码（安维必填）") @RequestParam(value = "thridCategoryNo", required = false) String thridCategoryNo,
			@ApiParam(value = "品牌id") @RequestParam(value = "brandNo", required = false) String brandNo,
			@ApiParam(value = "商品sku") @RequestParam(value = "productSku", required = false) String productSku,
			@ApiParam(value = "商品sku") @RequestParam(value = "packageName", required = false) String packageName) {
		ResultBean result = new ResultBean();
		TokenModel tokens = redisTokenService.getToken(accesstoken);
		if (tokens != null) {
			String userid = tokens.getUserid();
			// 创建返回数据
			result = logisticsService.addLogistics(userid,mId, deliveryNo, deptNo, expressNo, senderName, senderMob,
					senderPhone, senderAddr, receiverName, receiverMob, receiverPhone, receiverAddr, remark, isFragile,
					sendTo, predictDate, isCod, receiveable, onDoorPickUp, expressTimeReq, isGuarantee, jdNo,
					guaranteeMoney, receiptFlag, pickupDate, paperFrom, rtnReceiverName, rtnReceiverMob,
					rtnReceiverAddr, rtnReceiverPhone, weight, length, width, height, installFlag, thridCategoryNo,
					brandNo, productSku, packageName);
		} else {
			return new ResultBean("001", ResultBgMsg.C_001);
		}
		return result;
	}

}
