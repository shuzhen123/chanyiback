package dianfan.service.order;

import java.math.BigDecimal;

/**
 * @ClassName PayNotifyService
 * @Description 支付异步通知服务
 * @author cjy
 * @date 2018年7月7日 下午3:45:16
 */
public interface PayNotifyService {

	/**
	 * @Title: orderNotify
	 * @Description: 支付处理
	 * @param out_trade_no 商户支付流水号
	 * @param total_amount 实付金额
	 * @param trade_no 第三方平台交易流水号
	 * @return
	 * @throws:
	 * @time: 2018年7月9日 下午3:12:06
	 */
	boolean orderNotify(String out_trade_no, BigDecimal total_amount, String trade_no);

	/**
	 * @Title: orderRefundNotify
	 * @Description: 退款处理
	 * @param out_refund_no 商户退款单号
	 * @param settlement_refund_fee 退款金额
	 * @param return_msg 返回信息
	 * @return
	 * @throws:
	 * @time: 2018年7月11日 下午3:16:30
	 */
	boolean orderRefundNotify(String out_refund_no, BigDecimal settlement_refund_fee, String return_msg);
	/**
	 * @Title: returnGoodsOrderRefundNotify
	 * @Description: 退货退款处理
	 * @param out_refund_no
	 * @param settlement_refund_fee
	 * @param return_msg
	 * @return
	 * @throws:
	 * @time: 2018年8月10日 下午2:48:59
	 */
	boolean returnGoodsOrderRefundNotify(String out_refund_no, BigDecimal settlement_refund_fee, String return_msg);
}
