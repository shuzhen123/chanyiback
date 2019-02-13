package dianfan.controller.order;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;

import com.alipay.api.AlipayApiException;
import com.wordnik.swagger.annotations.ApiOperation;

import dianfan.annotations.SystemControllerLog;
import dianfan.annotations.UnCheckedFilter;
import dianfan.base64.Base64Util;
import dianfan.constant.ConstantIF;
import dianfan.entities.alipay.PublicPayParam;
import dianfan.logger.Logger;
import dianfan.pay.alipay.AlipayNotify;
import dianfan.pay.wx.WxNotify;
import dianfan.service.order.PayNotifyService;
import dianfan.util.CipherTextUtil;
import dianfan.util.PropertyUtil;

/**
 * @ClassName PayNotifyController
 * @Description 支付通知
 * @author cjy
 * @date 2018年7月7日 下午3:34:00
 */
@Scope(WebApplicationContext.SCOPE_REQUEST)
@Controller
@RequestMapping(value = "/notify")
public class PayNotifyController {

	@Autowired
	private PayNotifyService payNotifyService;

	/**
	 * @Title: alipayNotify
	 * @Description: 支付宝支付异步通知
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws AlipayApiException
	 * @throws:
	 * @time: 2018年7月7日 下午3:39:24
	 */
	@SystemControllerLog(method = "alipayNotify", logtype = ConstantIF.LOG_TYPE_1, description = "支付宝支付异步通知")
	@ApiOperation(value = "alipayNotify", httpMethod = "POST", notes = "支付宝支付异步通知", response = Void.class)
	@RequestMapping(value = "/alipayNotify")
	@UnCheckedFilter
	public void alipayNotify(HttpServletRequest request, HttpServletResponse response)
			throws IOException, AlipayApiException {
		Logger.error("支付宝回调111");
		Map<String, String> params = new HashMap<String, String>();
		Map<String, String[]> requestParams = request.getParameterMap();
		for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = iter.next();
			String[] values = requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
			}
			params.put(name, valueStr);
			Logger.error("支付宝支付异步通知参数name:" + name + ":" + valueStr);
		}

		PublicPayParam base = new PublicPayParam();
		base.setSign_type("RSA2");
		base.setAlipay_public_key(PropertyUtil.getProperty("alipay.public_key"));

		boolean check_sign = new AlipayNotify(base).signature(params);

		if (!check_sign) {
			// 验签失败
			response.getWriter().write("failed");
			response.flushBuffer();
		}

		// 是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email），
		if (!StringUtils.equals(PropertyUtil.getProperty("alipay.h5.pid"), params.get("seller_id"))) {
			response.getWriter().write("failed");
			response.flushBuffer();
		}

		// 验证app_id是否为该商户本身
		if (!StringUtils.equals(PropertyUtil.getProperty("alipay.h5.appid"), params.get("app_id"))) {
			response.getWriter().write("failed");
			response.flushBuffer();
		}

		BigDecimal total_fee = new BigDecimal(params.get("total_amount"));
		boolean ret = payNotifyService.orderNotify(params.get("out_trade_no"),
				total_fee.setScale(2, BigDecimal.ROUND_HALF_UP), params.get("trade_no"));

		if (ret) {
			response.getWriter().write("success");
		} else {
			response.getWriter().write("failed");
		}
		response.flushBuffer();
	}

	/**
	 * @Title: wxpayNotify
	 * @Description: 微信支付异步通知
	 * @param request
	 * @param response
	 * @throws Exception
	 * @throws:
	 * @time: 2018年7月7日 下午3:39:53
	 */
	@SystemControllerLog(method = "wxpayNotify", logtype = ConstantIF.LOG_TYPE_1, description = "微信支付异步通知")
	@ApiOperation(value = "wxpayNotify", httpMethod = "POST", notes = "微信支付异步通知", response = Void.class)
	@RequestMapping(value = "/wxpayNotify")
	@UnCheckedFilter
	public void wxpayNotify(HttpServletRequest request, HttpServletResponse response) throws Exception {
		BufferedReader br;
		String buffer = null;
		StringBuffer resp_data = null;

		String SUCCESS = "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
		String FAILED = "<xml><return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[FAIL]]></return_msg></xml>";

		try {
			br = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
			// 存放响应数据
			resp_data = new StringBuffer();
			while ((buffer = br.readLine()) != null) {
				resp_data.append(buffer);
			}
			Logger.error("微信支付异步通知" + resp_data);
			// 微信通知参数
			WxNotify notify = new WxNotify();
			Map<String, String> ret_data = notify.xmlToMap(resp_data.toString());

			if (!"SUCCESS".equals(ret_data.get("return_code")) || !"SUCCESS".equals(ret_data.get("result_code"))) {
				Logger.error("notify" + "通知数据异常");
				// 通知数据异常
				response.getWriter().write(FAILED);
			} else {
				// 验签
				String attach = ret_data.get("attach");
				boolean checkSign;
				if ("APP".equals(attach)) {
					checkSign = notify.checkSign(resp_data.toString(), PropertyUtil.getProperty("wx.app.key"));
				} else {
					checkSign = notify.checkSign(resp_data.toString(), PropertyUtil.getProperty("wx.mp.key"));
				}
				Logger.error("notify 通知数据验证" + checkSign);
				if (checkSign) {
					// 验签成功,处理订单
					BigDecimal total_fee = new BigDecimal(ret_data.get("total_fee"));
					total_fee = total_fee.divide(new BigDecimal(100));
					boolean ret = payNotifyService.orderNotify(ret_data.get("out_trade_no"),
							total_fee.setScale(2, BigDecimal.ROUND_HALF_UP), ret_data.get("transaction_id"));
					if (ret) {
						// 处理完成
						Logger.error("notify 通知数据验证处理完成");
						response.getWriter().write(SUCCESS);
					} else {
						// 处理失败
						Logger.error("notify 处理失败");
						response.getWriter().write(FAILED);
					}
				} else {
					// 验签失败
					Logger.error("notify 验签失败");
					response.getWriter().write(FAILED);
				}
			}
		} catch (IOException e1) {
			Logger.error("notify 验签失败" + e1);
			response.getWriter().write(FAILED);
		}
		response.flushBuffer();
	}

	/**
	 * @Title: wxrefundNotify
	 * @Description: 微信退款异步通知
	 * @param request
	 * @param response
	 * @throws Exception
	 * @throws:
	 * @time: 2018年7月11日 下午2:42:25
	 */
	@SystemControllerLog(method = "wxrefundNotify", logtype = ConstantIF.LOG_TYPE_1, description = "微信退款异步通知")
	@ApiOperation(value = "wxrefundNotify", httpMethod = "POST", notes = "微信退款异步通知", response = Void.class)
	@RequestMapping(value = "/refund/wxpayNotify")
	@UnCheckedFilter
	public void wxrefundNotify(HttpServletRequest request, HttpServletResponse response) throws Exception {
		BufferedReader br;
		String buffer = null;
		StringBuffer resp_data = null;

		String SUCCESS = "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
		String FAILED = "<xml><return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[FAIL]]></return_msg></xml>";

		try {
			br = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
			// 存放响应数据
			resp_data = new StringBuffer();
			while ((buffer = br.readLine()) != null) {
				resp_data.append(buffer);
			}
			Logger.error("微信退款异步通知" + resp_data);
			// 微信通知参数

			WxNotify notify = new WxNotify();
			Map<String, String> ret_data = notify.xmlToMap(resp_data.toString());

			if (!StringUtils.equals(ret_data.get("return_code"), "SUCCESS")) {
				Logger.error("refund/notify通知数据异常");
				// 通知数据异常
				response.getWriter().write(FAILED);
			} else {
				// 解密
				String xml_str = CipherTextUtil.decryptData(Base64Util.decode(ret_data.get("req_info")),
						PropertyUtil.getProperty("wx.mp.key"));
				// xml转Map
				Map<String, String> data = notify.xmlToMap(xml_str);

				boolean status = true;

				if (StringUtils.equals(data.get("refund_status"), "SUCCESS")) {
					Logger.error("refund/notify退款成功:" + data.get("refund_status"));
					// 退款成功
					BigDecimal refund_fee = new BigDecimal(data.get("settlement_refund_fee"));
					refund_fee = refund_fee.divide(new BigDecimal(100));
					status = payNotifyService.orderRefundNotify(data.get("out_refund_no"),
							refund_fee.setScale(2, BigDecimal.ROUND_HALF_UP), null);
				} else if (StringUtils.equals(data.get("refund_status"), "CHANGE")) {
					Logger.error("refund/notify退款异常:" + data.get("refund_status"));
					// 退款异常
					status = payNotifyService.orderRefundNotify(data.get("out_refund_no"), null,
							ret_data.get("return_msg"));
				} else if (StringUtils.equals(data.get("refund_status"), "REFUNDCLOSE")) {
					Logger.error("refund/notify退款关闭:" + data.get("refund_status"));
					// 退款关闭
					status = true;
				} else {
					Logger.error("refund/notify未知退款状态:" + data.get("refund_status"));
					// 未知退款状态
					status = false;
				}

				if (status) {
					Logger.error("refund/notify回调处理返回微信结果:完成");
					// 处理完成
					response.getWriter().write(SUCCESS);
				} else {
					Logger.error("refund/notify回调处理返回微信结果:失败");
					// 处理失败
					response.getWriter().write(FAILED);
				}
			}
		} catch (IOException e1) {
			response.getWriter().write(FAILED);
		}
		response.flushBuffer();
	}

}
