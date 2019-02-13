/**  
* @Title: GuidePriceController.java
* @Package dianfan.controller.order
* @Description: TODO
* @author yl
* @date 2018年8月20日 上午10:36:12
* @version V1.0  
*/ 
package dianfan.controller.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;

import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

import dianfan.annotations.SystemControllerLog;
import dianfan.constant.ConstantIF;
import dianfan.constant.ResultApiMsg;
import dianfan.models.ResultBean;
import dianfan.models.TokenModel;
import dianfan.service.impl.RedisTokenService;
import dianfan.service.order.GuidePriceService;

/** @ClassName GuidePriceController
 * @Description 
 * @author yl
 * @date 2018年8月20日 上午10:36:12
 */
@Scope(WebApplicationContext.SCOPE_REQUEST)
@Controller
@RequestMapping(value = "/gprice")
public class GuidePriceController {
	
	/**
	 * 注入： #OrderClassService
	 */
	@Autowired
	private GuidePriceService guidePriceService;
	
	/**
	 * 注入: #RedisTokenService
	 */
	@Autowired
	private RedisTokenService redisTokenService;
	/**
	 * @Title: guideEditOrderPrice
	 * @Description: 确认订单后，订单详情显示订单二维码，导购扫描后可以设置折扣金额，不高于最低折扣价，修改商品下单和支付流程
	 * @param accesstoken token
	 * @param orderid 订单id
	 * @param discount 折扣价
	 * @param couponrelateid 用户相关优惠券表id
	 * @return
	 * @throws:
	 * @time: 2018年7月7日 下午4:29:49
	 */
	@SystemControllerLog(method = "guideEditOrderPrice", logtype = ConstantIF.LOG_TYPE_1, description = "导购设置折扣金额")
	@ApiOperation(value = "guideEditOrderPrice", httpMethod = "POST", notes = "导购设置折扣金额", response = ResultBean.class)
	@RequestMapping(value = "/guideEditOrderPrice", method = RequestMethod.POST)
	public @ResponseBody ResultBean guideEditOrderPrice(
			@ApiParam(value="accesstoken") @RequestParam(value=ConstantIF.ACCESSTOKEN) String accesstoken,
			@ApiParam(value="订单id") @RequestParam(value="orderid") String orderid,
			@ApiParam(value="折扣价") @RequestParam(value="discount") String discount
			) {
		// 获取token
		TokenModel token = redisTokenService.getToken(accesstoken);
		// 调库操作
		ResultBean rb = guidePriceService.updateOrderDiscount(token.getUserid(), orderid, discount);
		// 成功
		return rb;
	}
	
	/**
	 * @Title: guideOrderPrice
	 * @Description: 获取最大优惠金额
	 * @param accesstoken
	 * @param orderid
	 * @return
	 * @throws:
	 * @time: 2018年8月20日 下午3:29:59
	 */
	@SystemControllerLog(method = "guideOrderPrice", logtype = ConstantIF.LOG_TYPE_1, description = "最大折扣金额")
	@ApiOperation(value = "guideOrderPrice", httpMethod = "POST", notes = "最大折扣金额", response = ResultBean.class)
	@RequestMapping(value = "/guideOrderPrice", method = RequestMethod.POST)
	public @ResponseBody ResultBean guideOrderPrice(
			@ApiParam(value="accesstoken") @RequestParam(value=ConstantIF.ACCESSTOKEN) String accesstoken,
			@ApiParam(value="订单id") @RequestParam(value="goodscartids") String orderid
			) {
		// 获取token
		TokenModel token = redisTokenService.getToken(accesstoken);
		// 调库操作
		ResultBean rb = guidePriceService.getMaxDiscount(token.getUserid(), orderid);
		// 成功
		return rb;
	}
	/**
	 * @Title: fildOrderInfo
	 * @Description: 获取订单详情
	 * @param accesstoken
	 * @param orderid 订单id
	 * @return
	 * @throws:
	 * @time: 2018年8月22日 下午3:01:01
	 */
	@SystemControllerLog(method = "queryOrderInfo", logtype = ConstantIF.LOG_TYPE_1, description = "获取订单详情")
	@ApiOperation(value = "queryOrderInfo", httpMethod = "POST", notes = "获取订单详情", response = ResultBean.class)
	@RequestMapping(value = "/queryOrderInfo", method = RequestMethod.POST)
	public @ResponseBody ResultBean queryOrderInfo(
			@ApiParam(value = "accesstoken") @RequestParam(value = ConstantIF.ACCESSTOKEN) String accesstoken,
			@ApiParam(value = "订单的id") @RequestParam(value = "orderid") String orderid) {
		// 1.创建返回模型
		ResultBean result = new ResultBean();
		// 2.获取token
		//TokenModel token = redisTokenService.getToken(accesstoken);
		// 3.空值验证
		if (StringUtils.isEmpty(orderid)) {
			// !参数错误
			return new ResultBean("501", ResultApiMsg.C_501);
		}
		// 4.调库操作
		result = guidePriceService.queryOrderInfo(orderid);
		// 5.成功
		return result;
	}
	

}
