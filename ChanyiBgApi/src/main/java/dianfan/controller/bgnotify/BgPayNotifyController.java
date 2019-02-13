/**  
* @Title: BgPayNotifyController.java
* @Package dianfan.controller.bgnotify
* @Description: TODO
* @author yl
* @date 2018年8月10日 下午2:59:24
* @version V1.0  
*/ 
package dianfan.controller.bgnotify;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;

import com.wordnik.swagger.annotations.ApiOperation;

import dianfan.annotations.SystemControllerLog;
import dianfan.annotations.UnCheckedFilter;
import dianfan.base64.Base64Util;
import dianfan.constant.ConstantIF;
import dianfan.logger.Logger;
import dianfan.pay.wx.WxNotify;
import dianfan.service.order.PayNotifyService;
import dianfan.util.CipherTextUtil;
import dianfan.util.PropertyUtil;

/** @ClassName BgPayNotifyController
 * @Description 
 * @author yl
 * @date 2018年8月10日 下午2:59:24
 */
@Scope(WebApplicationContext.SCOPE_REQUEST)
@Controller
@RequestMapping(value = "/bgnotify")
public class BgPayNotifyController {
	
	@Autowired
	private PayNotifyService payNotifyService;
	
	/**
	 * @Title: wxrefundNotify
	 * @Description: 微信退款异步通知
	 * @param request
	 * @param response
	 * @throws Exception
	 * @throws:
	 * @time: 2018年7月11日 下午2:42:25
	 */
	@SystemControllerLog(method = "bgwxrefundNotify", logtype = ConstantIF.LOG_TYPE_5, description = "后台微信退款异步通知")
	@ApiOperation(value = "bgwxrefundNotify", httpMethod = "POST", notes = "后台微信退款异步通知", response = Void.class)
	@RequestMapping(value = "/bgrefund/bgwxrefundNotify")
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
					Logger.error("refund/notify退款成功(后台):" + data.get("refund_status"));
					// 退款成功
					BigDecimal refund_fee = new BigDecimal(data.get("settlement_refund_fee"));
					refund_fee = refund_fee.divide(new BigDecimal(100));
					status = payNotifyService.returnGoodsOrderRefundNotify(data.get("out_refund_no"),
							refund_fee.setScale(2, BigDecimal.ROUND_HALF_UP), null);
				} else if (StringUtils.equals(data.get("refund_status"), "CHANGE")) {
					Logger.error("refund/notify退款异常(后台):" + data.get("refund_status"));
					// 退款异常
					status = payNotifyService.returnGoodsOrderRefundNotify(data.get("out_refund_no"), null,
							ret_data.get("return_msg"));
				} else if (StringUtils.equals(data.get("refund_status"), "REFUNDCLOSE")) {
					Logger.error("refund/notify退款关闭(后台):" + data.get("refund_status"));
					// 退款关闭
					status = true;
				} else {
					Logger.error("refund/notify未知退款状态(后台):" + data.get("refund_status"));
					// 未知退款状态
					status = false;
				}

				if (status) {
					Logger.error("refund/notify回调处理返回微信结果:完成(后台)");
					// 处理完成
					response.getWriter().write(SUCCESS);
				} else {
					Logger.error("refund/notify回调处理返回微信结果:失败(后台)");
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
