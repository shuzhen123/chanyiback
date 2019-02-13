package dianfan.controller.jd;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jd.open.api.sdk.request.ECLP.EclpCoTransportLasWayBillRequest;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

import dianfan.annotations.SystemControllerLog;
import dianfan.annotations.UnCheckedFilter;
import dianfan.constant.ConstantIF;
import dianfan.models.ResultBean;
import dianfan.service.jd.transport.JdTransportService;

/**
 * @ClassName JdController
 * @Description 京东通知
 * @author cjy
 * @date 2018年7月12日 下午2:38:42
 */
@Scope(WebApplicationContext.SCOPE_REQUEST)
@Controller
@RequestMapping(value = "/jd")
public class JdController {
	@Autowired
	private JdTransportService jdTransportService;

	/**
	 * @Title: jdNotify
	 * @Description: 京东异步通知
	 * @param request
	 * @param response
	 * @throws JsonProcessingException
	 * @throws:
	 * @time: 2018年7月12日 下午4:23:42
	 */
	@SystemControllerLog(method = "jdNotify", logtype = ConstantIF.LOG_TYPE_1, description = "京东异步通知")
	@ApiOperation(value = "jdNotify", httpMethod = "POST", notes = "京东异步通知", response = Void.class)
	@RequestMapping(value = "/authNotify")
	@UnCheckedFilter
	public void jdNotify(HttpServletRequest request, HttpServletResponse response) throws JsonProcessingException {
		System.err.println("---------------------------------------");
		System.err.println(request.getParameter("code"));

		ObjectMapper om = new ObjectMapper();
		String string = om.writeValueAsString(request.getParameterMap());
		System.err.println(string);
	}

	/**
	 * @Title: transportLasWayBill
	 * @Description: 大件纯配运单导入
	 * @throws JsonProcessingException
	 * @throws:
	 * @time: 2018年7月12日 下午4:25:39
	 */
	@SystemControllerLog(method = "transportLasWayBill", logtype = ConstantIF.LOG_TYPE_1, description = "大件纯配运单导入")
	@ApiOperation(value = "transportLasWayBill", httpMethod = "POST", notes = "大件纯配运单导入", response = ResultBean.class)
	@RequestMapping(value = "/transportLasWayBill", method = RequestMethod.POST)
	@UnCheckedFilter
	public @ResponseBody ResultBean transportLasWayBill(
			@ApiParam(value = "是:最大长度：200，商家订单编号") @RequestParam(value = "orderNo") String orderNo,
			@ApiParam(value = "是:最大长度：50，寄件人姓名") @RequestParam(value = "senderName") String senderName,
			@ApiParam(value = "否:寄件人手机(手机和座机必须填一个)") @RequestParam(value = "senderMobile", required = false) String senderMobile,
			@ApiParam(value = "否:寄件人座机(手机和座机必须填一个)") @RequestParam(value = "senderPhone", required = false) String senderPhone,
			@ApiParam(value = "是:最大长度：200，寄件人地址") @RequestParam(value = "senderAddress") String senderAddress,
			@ApiParam(value = "是:最大长度：50，收件人姓名") @RequestParam(value = "receiverName") String receiverName,
			@ApiParam(value = "否:收货人手机(手机和座机必须填一个)") @RequestParam(value = "receiverMobile", required = false) String receiverMobile,
			@ApiParam(value = "否:收货人座机(手机和座机必须填一个)") @RequestParam(value = "receiverPhone", required = false) String receiverPhone,
			@ApiParam(value = "是:最大长度：200，收货人地址") @RequestParam(value = "receiverAddress") String receiverAddress,
			@ApiParam(value = "否:最大长度：500，订单备注") @RequestParam(value = "remark", required = false) String remark,
			@ApiParam(value = "否:是否易碎(1是，2否)") @RequestParam(value = "isFragile", required = false) String isFragile,
			@ApiParam(value = "是:始发转运中心名称") @RequestParam(value = "senderTc") String senderTc,
			@ApiParam(value = "否:预计到仓时间(yyyy/MM/dd)") @RequestParam(value = "predictDate", required = false) String predictDate,
			@ApiParam(value = "是:是否京东平台订单(1是，2否)") @RequestParam(value = "isJDOrder") String isJDOrder,
			@ApiParam(value = "是:是否货到付款(1是，0否)") @RequestParam(value = "isCod") String isCod,
			@ApiParam(value = "否:代收金额（货到付款时必输）") @RequestParam(value = "receiveable", required = false) String receiveable,
			@ApiParam(value = "是:上门揽件标记(1是，2否)") @RequestParam(value = "onDoorPickUp") String onDoorPickUp,
			@ApiParam(value = "否:上门揽件时间(yyyy/MM/dd)") @RequestParam(value = "pickUpDate", required = false) String pickUpDate,
			@ApiParam(value = "是:是否保价(1是，2否)") @RequestParam(value = "isGuarantee") String isGuarantee,
			@ApiParam(value = "否:保价金额(元)") @RequestParam(value = "guaranteeValue", required = false) String guaranteeValue,
			@ApiParam(value = "否:签单返还（0无签单，1纸质签单，2电子签单，3纸质签单和电子签单）") @RequestParam(value = "receiptFlag", required = false) String receiptFlag,
			@ApiParam(value = "否:纸质单来源（1带单，2取单，3带单和取单）") @RequestParam(value = "paperFrom", required = false) String paperFrom,
			@ApiParam(value = "否:返单收件人姓名") @RequestParam(value = "rtnReceiverName", required = false) String rtnReceiverName,
			@ApiParam(value = "否:返单收件人手机号") @RequestParam(value = "rtnReceiverMobile", required = false) String rtnReceiverMobile,
			@ApiParam(value = "否:返单收件人地址") @RequestParam(value = "rtnReceiverAddress", required = false) String rtnReceiverAddress,
			@ApiParam(value = "否:返单收件人电话") @RequestParam(value = "rtnReceiverPhone", required = false) String rtnReceiverPhone,
			@ApiParam(value = "是:重量(Kg)") @RequestParam(value = "weight") String weight,
			@ApiParam(value = "是:长度(mm)") @RequestParam(value = "length") String length,
			@ApiParam(value = "是:宽度(mm)") @RequestParam(value = "width") String width,
			@ApiParam(value = "是:高度(mm)") @RequestParam(value = "height") String height,
			@ApiParam(value = "是:是否安维(1是，2否)") @RequestParam(value = "installFlag") String installFlag,
			@ApiParam(value = "否:最大长度：20，三级分类编码(安维必填)") @RequestParam(value = "thirdCategoryNo", required = false) String thirdCategoryNo,
			@ApiParam(value = "否:最大长度：50，品牌ID(安维必填)") @RequestParam(value = "brandNo", required = false) String brandNo,
			@ApiParam(value = "否:最大长度：50，商品sku") @RequestParam(value = "productSku", required = false) String productSku,
			@ApiParam(value = "否:最大长度：200，物品内容") @RequestParam(value = "packageName", required = false) String packageName) {
		// if(StringUtils.isEmpty(senderMobile) && StringUtils.isEmpty(senderPhone)) {
		// //寄件人手机和座机必须填一个
		// return new ResultBean("7000", ResultApiMsg.C_7000);
		// }
		// if(StringUtils.isEmpty(receiverMobile) && StringUtils.isEmpty(receiverPhone))
		// {
		// //收货人手机和座机必须填一个
		// return new ResultBean("7001", ResultApiMsg.C_7001);
		// }
		//
		// if(!StringUtils.isEmpty(isFragile) && (!StringUtils.equals(isFragile, "1") ||
		// !StringUtils.equals(isFragile, "2"))) {
		// //收货人手机和座机必须填一个
		// return new ResultBean("7002", ResultApiMsg.C_7002);
		// }

		EclpCoTransportLasWayBillRequest data = new EclpCoTransportLasWayBillRequest();
		data.setOrderNo(orderNo);
		data.setSenderName(senderName);
		data.setSenderMobile(senderMobile);
		data.setSenderPhone(senderPhone);
		data.setSenderAddress(senderAddress);
		data.setReceiverName(receiverName);
		data.setReceiverMobile(receiverMobile);
		data.setReceiverPhone(receiverPhone);
		data.setReceiverAddress(receiverAddress);
		data.setRemark(remark);
		data.setIsFragile(isFragile);
		data.setSenderTc(senderTc);
		data.setPredictDate(predictDate);
		data.setIsJDOrder(isJDOrder);
		data.setIsCod(isCod);
		data.setReceiveable(receiveable);
		data.setOnDoorPickUp(onDoorPickUp);
		data.setPickUpDate(pickUpDate);
		data.setIsGuarantee(isGuarantee);
		data.setGuaranteeValue(guaranteeValue);
		data.setReceiptFlag(receiptFlag);
		data.setPaperFrom(paperFrom);
		data.setRtnReceiverName(rtnReceiverName);
		data.setRtnReceiverMobile(rtnReceiverMobile);
		data.setRtnReceiverAddress(rtnReceiverAddress);
		data.setRtnReceiverPhone(rtnReceiverPhone);
		data.setWeight(weight);
		data.setLength(length);
		data.setWidth(width);
		data.setHeight(height);
		data.setInstallFlag(installFlag);
		data.setThirdCategoryNo(thirdCategoryNo);
		data.setBrandNo(brandNo);
		data.setProductSku(productSku);
		data.setPackageName(packageName);

		return jdTransportService.transportLasWayBill(data);
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
			@ApiParam(value = "是:最大长度：200，商家订单编号") @RequestParam(value = "orderNo") String orderNo) {
		return jdTransportService.cancelLwbMain(orderNo);
	}

	/**
	 * @Title: queryLwbByCondition
	 * @Description: 大件纯配运单状态查询
	 * @param orderNo
	 *            商家订单编号
	 * @return
	 * @throws:
	 * @time: 2018年7月12日 下午5:04:03
	 */
	@SystemControllerLog(method = "queryLwbByCondition", logtype = ConstantIF.LOG_TYPE_1, description = "大件纯配运单状态查询")
	@ApiOperation(value = "queryLwbByCondition", httpMethod = "POST", notes = "大件纯配运单状态查询", response = ResultBean.class)
	@RequestMapping(value = "/queryLwbByCondition", method = RequestMethod.POST)
	@UnCheckedFilter
	public @ResponseBody ResultBean queryLwbByCondition(
			@ApiParam(value = "是:最大长度：200，商家订单编号") @RequestParam(value = "orderNo") String orderNo) {
		return jdTransportService.queryLwbByCondition(orderNo);
	}
}
