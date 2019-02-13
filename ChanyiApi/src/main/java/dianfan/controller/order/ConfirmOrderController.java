/**  
* @Title: ConfirmOrderController.java
* @Package dianfan.controller.order
* @Description: TODO
* @author yl
* @date 2018年7月7日 下午4:23:29
* @version V1.0  
*/ 
package dianfan.controller.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;

import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

import dianfan.annotations.SystemControllerLog;
import dianfan.constant.ConstantIF;
import dianfan.models.ResultBean;
import dianfan.models.TokenModel;
import dianfan.service.impl.RedisTokenService;
import dianfan.service.order.OrderClassService;

/** @ClassName ConfirmOrderController
 * @Description 购物车确认订单
 * @author yl
 * @date 2018年7月7日 下午4:23:29
 */
@Scope(WebApplicationContext.SCOPE_REQUEST)
@Controller
@RequestMapping(value = "/order")
public class ConfirmOrderController {
	
	/**
	 * 注入： #OrderClassService
	 */
	@Autowired
	private OrderClassService orderClassService;
	
	/**
	 * 注入: #RedisTokenService
	 */
	@Autowired
	private RedisTokenService redisTokenService;
	/**
	 * @Title: confirmOrder
	 * @Description: 
	 * @param accesstoken token
	 * @param addressid 地址id
	 * @param goodscartids 购物车id
	 * @param couponrelateid 用户相关优惠券表id
	 * @return
	 * @throws:
	 * @time: 2018年7月7日 下午4:29:49
	 */
	@SystemControllerLog(method = "confirmOrder", logtype = ConstantIF.LOG_TYPE_1, description = "确认订单")
	@ApiOperation(value = "confirmOrder", httpMethod = "POST", notes = "确认订单", response = ResultBean.class)
	@RequestMapping(value = "/confirmOrder", method = RequestMethod.POST)
	public @ResponseBody ResultBean confirmOrder(
			@ApiParam(value="accesstoken") @RequestParam(value=ConstantIF.ACCESSTOKEN) String accesstoken,
			@ApiParam(value="地址id") @RequestParam(value="addressid") String addressid,
			@ApiParam(value="购物车id") @RequestParam(value="goodscartids") String goodscartids,
			@ApiParam(value="用户相关优惠券表id") @RequestParam(value="couponrelateid",required=false) String couponrelateid,
			@ApiParam(value="优惠券表id") @RequestParam(value="couponid",required=false) String couponid,
			@ApiParam(value="支付价格") @RequestParam(value="payprice",required=false) String payprice,
			@ApiParam(value="订单来源(01 小程序   02 APP    03 H5)") @RequestParam(value="source",required=true,defaultValue="01") String source
			) {
		// 获取token
		TokenModel token = redisTokenService.getToken(accesstoken);
		// 调库操作
		ResultBean rb = orderClassService.addConfirmOrder(token.getUserid(), addressid,goodscartids,couponrelateid,couponid,payprice,source);
		// 成功
		return rb;
	}
	
	/**
	 * @Title: ordinaryBuyNowOrder
	 * @Description: 
	 * @param accesstoken
	 * @param addressid 地址id
	 * @param goodsids 商品id
	 * @param goodspriceids 商品价格id
	 * @param num 数量
	 * @param couponrelateid 用户优惠券相关联id
	 * @param couponid 优惠券id
	 * @return
	 * @throws:
	 * @time: 2018年7月9日 下午2:07:00
	 */
	@SystemControllerLog(method = "ordinaryBuyNowOrder", logtype = ConstantIF.LOG_TYPE_1, description = "确认订单(普通商品立即购买)")
	@ApiOperation(value = "ordinaryBuyNowOrder", httpMethod = "POST", notes = "确认订单(普通商品立即购买)", response = ResultBean.class)
	@RequestMapping(value = "/ordinaryBuyNowOrder", method = RequestMethod.POST)
	public @ResponseBody ResultBean ordinaryBuyNowOrder(
			@ApiParam(value="accesstoken") @RequestParam(value=ConstantIF.ACCESSTOKEN) String accesstoken,
			@ApiParam(value="地址id") @RequestParam(value="addressid") String addressid,
			@ApiParam(value="商品id") @RequestParam(value="goodsids") String goodsids,
			@ApiParam(value="商品价格id") @RequestParam(value="goodspriceids",required=false) String goodspriceids,
			@ApiParam(value="商品数量") @RequestParam(value="num") Integer num,
			@ApiParam(value="用户相关优惠券表id") @RequestParam(value="couponrelateid",required=false) String couponrelateid,
			@ApiParam(value="优惠券表id") @RequestParam(value="couponid",required=false) String couponid,
			@ApiParam(value="是否发起易拼") @RequestParam(value="isesflag",required = false)  String isesflag,
			@ApiParam(value="易拼表(发起拼团)id") @RequestParam(value="esid",required=false) String esid,
			@ApiParam(value="支付价格") @RequestParam(value="payprice",required=false) String payprice,
			@ApiParam(value="订单来源(01 小程序   02 APP    03 H5)") @RequestParam(value="source",required=true,defaultValue="01") String source
			) {
		// 获取token
		TokenModel token = redisTokenService.getToken(accesstoken);
		// 调库操作
		ResultBean rb =  orderClassService.addOrdinaryBuyNow(token.getUserid(), addressid,goodsids,goodspriceids,num,couponrelateid,couponid,isesflag,esid,payprice,source);
		// 成功
		return rb;
	}
	

}
