package dianfan.controller.order;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import dianfan.annotations.UnCheckedFilter;
import dianfan.constant.ConstantIF;
import dianfan.constant.ResultApiMsg;
import dianfan.models.ResultBean;
import dianfan.models.TokenModel;
import dianfan.service.impl.RedisTokenService;
import dianfan.service.jd.transport.JdTransportService;
import dianfan.service.order.OrderClassService;

/**
 * @ClassName OrderController
 * @Description 订单相关Controller
 * @author sz
 * @date 2018年7月5日 下午3:27:36
 */
@Scope(WebApplicationContext.SCOPE_REQUEST)
@Controller
@RequestMapping(value = "/order")
public class OrderController {

	/**
	 * 注入： #OrderClassService
	 */
	@Autowired
	private OrderClassService orderClassService;
	@Autowired
	private JdTransportService jdTransportService;

	/**
	 * 注入: RedisTokenService
	 */
	@Autowired
	private RedisTokenService redisTokenService;

	/**
	 * @Title: fildOrderList
	 * @Description: 获取订单列表
	 * @param accesstoken
	 *            accesstoken
	 * @param status
	 *            订单状态 01-待付款 02-待审核 03-正在生产 04 待发货 05 待收货 06 已收货
	 * @return ResultBean
	 * @throws:
	 * @time: 2018年7月5日 下午3:44:20
	 */
	@SystemControllerLog(method = "fildOrderList", logtype = ConstantIF.LOG_TYPE_1, description = "获取支付订单列表")
	@ApiOperation(value = "fildOrderList", httpMethod = "POST", notes = "获取支付订单列表", response = ResultBean.class)
	@RequestMapping(value = "/fildOrderList", method = RequestMethod.POST)
	public @ResponseBody ResultBean fildOrderList(
			@ApiParam(value = "accesstoken") @RequestParam(value = ConstantIF.ACCESSTOKEN) String accesstoken,
			@ApiParam(value = "订单的状态（以','分隔）") @RequestParam(value = "status", defaultValue = "00", required = false) String status,
			@ApiParam(value = "第几页") @RequestParam(value = "page", defaultValue = "1", required = false) Integer page,
			@ApiParam(value = "每页的条数") @RequestParam(value = "pagecounts") Integer pagecounts) {
		// 创建返回数据
		ResultBean result = new ResultBean();

		// 2.获取token
		TokenModel token = redisTokenService.getToken(accesstoken);

		// 3.调库
		result = orderClassService.fildOrderList(token.getUserid(), status, page, pagecounts);
		// 4.成功
		return result;
	}

	/**
	 * @Title: closeOrder
	 * @Description: 关闭订单
	 * @param accesstoken
	 *            accesstoken
	 * @param orderid
	 *            订单的id
	 * @return ResultBean
	 * @throws:
	 * @time: 2018年7月5日 下午6:34:15
	 */
	@SystemControllerLog(method = "closeOrder", logtype = ConstantIF.LOG_TYPE_1, description = "关闭订单")
	@ApiOperation(value = "closeOrder", httpMethod = "POST", notes = "关闭订单", response = ResultBean.class)
	@RequestMapping(value = "/closeOrder", method = RequestMethod.POST)
	public @ResponseBody ResultBean closeOrder(
			@ApiParam(value = "accesstoken") @RequestParam(value = ConstantIF.ACCESSTOKEN) String accesstoken,
			@ApiParam(value = "订单的id") @RequestParam(value = "orderid") String orderid) {
		// 创建返回模型
		ResultBean result = new ResultBean();
		// 获取token
		TokenModel token = redisTokenService.getToken(accesstoken);
		// 空值验证
		if (StringUtils.isEmpty(orderid)) {
			// 参数错误
			return new ResultBean("501", ResultApiMsg.C_501);
		}
		// 调库操作
		result = orderClassService.delOrder(token.getUserid(), orderid);
		// 成功
		return result;
	}

	/**
	 * @Title: delOrder
	 * @Description: 删除订单
	 * @param accesstoken
	 *            accesstoken
	 * @param orderid
	 *            订单的id
	 * @return ResultBean
	 * @throws:
	 * @time: 2018年7月5日 下午6:34:15
	 */
	@SystemControllerLog(method = "delOrder", logtype = ConstantIF.LOG_TYPE_1, description = "删除订单")
	@ApiOperation(value = "delOrder", httpMethod = "POST", notes = "删除订单", response = ResultBean.class)
	@RequestMapping(value = "/delOrder", method = RequestMethod.POST)
	public @ResponseBody ResultBean delOrder(
			@ApiParam(value = "accesstoken") @RequestParam(value = ConstantIF.ACCESSTOKEN) String accesstoken,
			@ApiParam(value = "订单的id") @RequestParam(value = "orderid") String orderid) {
		// 创建返回模型
		ResultBean result = new ResultBean();
		// 获取token
		TokenModel token = redisTokenService.getToken(accesstoken);
		// 空值验证
		if (StringUtils.isEmpty(orderid)) {
			// 参数错误
			return new ResultBean("501", ResultApiMsg.C_501);
		}
		// 调库操作
		result = orderClassService.delOrderForever(token.getUserid(), orderid);
		// 成功
		return result;
	}

	/**
	 * @Title: recoverOrder
	 * @Description: 恢复订单
	 * @param accesstoken
	 *            accesstoken
	 * @param orderid
	 *            订单的id
	 * @return ResultBean
	 * @throws:
	 * @time: 2018年7月5日 下午6:34:15
	 */
	@SystemControllerLog(method = "recoverOrder", logtype = ConstantIF.LOG_TYPE_1, description = "恢复订单")
	@ApiOperation(value = "recoverOrder", httpMethod = "POST", notes = "恢复订单", response = ResultBean.class)
	@RequestMapping(value = "/recoverOrder", method = RequestMethod.POST)
	public @ResponseBody ResultBean recoverOrder(
			@ApiParam(value = "accesstoken") @RequestParam(value = ConstantIF.ACCESSTOKEN) String accesstoken,
			@ApiParam(value = "订单的id") @RequestParam(value = "orderid") String orderid) {
		// 创建返回模型
		ResultBean result = new ResultBean();
		// 获取token
		TokenModel token = redisTokenService.getToken(accesstoken);
		// 空值验证
		if (StringUtils.isEmpty(orderid)) {
			// 参数错误
			return new ResultBean("501", ResultApiMsg.C_501);
		}
		// 调库操作
		result = orderClassService.updataRecoverOrder(token.getUserid(), orderid);
		// 成功
		return result;
	}

	/**
	 * @Title: fildOrderInfo
	 * @Description: 获取订单详情
	 * @param accesstoken
	 *            accesstoken
	 * @param orderid
	 *            订单的id
	 * @return ResultBean
	 * @throws:
	 * @time: 2018年7月6日 上午10:41:26
	 */
	@SystemControllerLog(method = "fildOrderInfo", logtype = ConstantIF.LOG_TYPE_1, description = "获取订单详情")
	@ApiOperation(value = "fildOrderInfo", httpMethod = "POST", notes = "获取订单详情", response = ResultBean.class)
	@RequestMapping(value = "/fildOrderInfo", method = RequestMethod.POST)
	public @ResponseBody ResultBean fildOrderInfo(
			@ApiParam(value = "accesstoken") @RequestParam(value = ConstantIF.ACCESSTOKEN) String accesstoken,
			@ApiParam(value = "订单的id") @RequestParam(value = "orderid") String orderid) {
		// 1.创建返回模型
		ResultBean result = new ResultBean();
		// 2.获取token
		TokenModel token = redisTokenService.getToken(accesstoken);
		// 3.空值验证
		if (StringUtils.isEmpty(orderid)) {
			// !参数错误
			return new ResultBean("501", ResultApiMsg.C_501);
		}
		// 4.调库操作
		result = orderClassService.fildOrderInfo(token.getUserid(), orderid);
		// 5.成功
		return result;
	}

	/**
	 * @Title: goodsOrderPayment
	 * @Description: 调起支付
	 * @param accesstoken
	 *            accesstoken
	 * @param orderid
	 *            订单id
	 * @param payWays
	 * @param payType
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @throws:
	 * @time: 2018年7月7日 上午11:07:27
	 */
	@SystemControllerLog(method = "goodsOrderPayment", logtype = ConstantIF.LOG_TYPE_1, description = "获取订单详情")
	@ApiOperation(value = "goodsOrderPayment", httpMethod = "POST", notes = "获取订单详情", response = ResultBean.class)
	@RequestMapping(value = "/goodsOrderPayment", method = RequestMethod.POST)
	public @ResponseBody ResultBean goodsOrderPayment(
			@ApiParam(value = "accesstoken") @RequestParam(value = ConstantIF.ACCESSTOKEN) String accesstoken,
			@ApiParam(value = "订单的id") @RequestParam(value = "orderid") String orderid,
			@RequestParam(value = "payWays") String payWays, // 支付方式(01-ALI(支付宝) 02-WX（微信） 03-BC（银行卡）)
			@RequestParam(value = "paySource") String paySource, // 支付渠道（01：小程序02：app 03 手机网站 04其他）
			@RequestParam(value = "payType") String payType, // 支付类型，固定值(支付宝（"app", "web"）微信（"app", "mweb", "wxpub",
																// "applet"）)
			HttpServletRequest request) throws Exception {
		// 获取token
		TokenModel token = redisTokenService.getToken(accesstoken);
		String ip = request.getHeader("X-Real-IP");
		return orderClassService.goodsOrderPayment(token.getUserid(), orderid, payWays, paySource, payType.toUpperCase(), ip, accesstoken);
}

	/**
	 * @Title: acceptOrder
	 * @Description: 订单确认收货完成
	 * @param accesstoken
	 * @param orderid
	 * @return
	 * @throws:
	 * @time: 2018年7月9日 下午5:55:48
	 */
	@SystemControllerLog(method = "acceptOrder", logtype = ConstantIF.LOG_TYPE_1, description = "确认收货")
	@ApiOperation(value = "acceptOrder", httpMethod = "POST", notes = "确认收货", response = ResultBean.class)
	@RequestMapping(value = "/acceptOrder", method = RequestMethod.POST)
	public @ResponseBody ResultBean acceptOrder(
			@ApiParam(value = "accesstoken") @RequestParam(value = ConstantIF.ACCESSTOKEN) String accesstoken,
			@ApiParam(value = "订单的id") @RequestParam(value = "orderid") String orderid) {
		// 获取token
		TokenModel token = redisTokenService.getToken(accesstoken);
		orderClassService.acceptOrder(token.getUserid(), orderid);
		return new ResultBean();
	}

	/**
	 * @Title: returnGoods
	 * @Description:
	 * @param accesstoken
	 * @param orderid
	 * @return
	 * @throws:
	 * @time: 2018年8月3日 下午1:49:16
	 */
	@SystemControllerLog(method = "returnGoods", logtype = ConstantIF.LOG_TYPE_1, description = "退货")
	@ApiOperation(value = "returnGoods", httpMethod = "POST", notes = "退货", response = ResultBean.class)
	@RequestMapping(value = "/returnGoods", method = RequestMethod.POST)
	public @ResponseBody ResultBean returnGoods(
			@ApiParam(value = "accesstoken") @RequestParam(value = ConstantIF.ACCESSTOKEN) String accesstoken,
			@ApiParam(value = "订单的id") @RequestParam(value = "orderid") String orderid,
			@ApiParam(value = "退款原因") @RequestParam(value = "reason") String reason,
			@ApiParam(value = "退款金额") @RequestParam(value = "price") String price,
			@ApiParam(value = "退款说明") @RequestParam(value = "instructions") String instructions,
			@ApiParam(value = "上传凭证") @RequestParam(value = "credentials") String credentials) {
		// 获取token
		TokenModel token = redisTokenService.getToken(accesstoken);
		orderClassService.addReturnGoods(token.getUserid(), orderid, reason, price, instructions, credentials);
		return new ResultBean();
	}

	/**
	 * @Title: barterOrder
	 * @Description:
	 * @param accesstoken
	 * @param orderid
	 * @return
	 * @throws:
	 * @time: 2018年8月3日 下午1:49:21
	 */
	@SystemControllerLog(method = "barterOrder", logtype = ConstantIF.LOG_TYPE_1, description = "换货")
	@ApiOperation(value = "barterOrder", httpMethod = "POST", notes = "换货", response = ResultBean.class)
	@RequestMapping(value = "/barterOrder", method = RequestMethod.POST)
	public @ResponseBody ResultBean barterOrder(
			@ApiParam(value = "accesstoken") @RequestParam(value = ConstantIF.ACCESSTOKEN) String accesstoken,
			@ApiParam(value = "订单的id") @RequestParam(value = "orderid") String orderid,
			@ApiParam(value = "换货原因") @RequestParam(value = "reason") String reason,
			@ApiParam(value = "换货说明") @RequestParam(value = "instructions") String instructions,
			@ApiParam(value = "上传换货凭证") @RequestParam(value = "credentials") String credentials) {
		// 获取token
		TokenModel token = redisTokenService.getToken(accesstoken);
		orderClassService.addBarterGoods(token.getUserid(), orderid, reason, instructions, credentials);
		return new ResultBean();
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
			@ApiParam(value = "是:最大长度：200，商家订单编号") @RequestParam(value = "orderNo") String orderNo,
			@ApiParam(value = "物流类型    01：订单发货   02：退/换货寄回仓库    03：退货验收失败退回    04：换货重新发货    05：换货验收失败退回") @RequestParam(value = "deliverytype") String deliverytype) {
		ResultBean rb = null;
		List<String> deliveryNos = orderClassService.getDeliveryId(orderNo,deliverytype);
		if (deliveryNos !=null && deliveryNos.size()>0) {
			for (int i = 0; i < deliveryNos.size(); i++) {
				rb = jdTransportService.queryLwbByCondition(deliveryNos.get(i));
			}
		}
		return rb;
	}

}
