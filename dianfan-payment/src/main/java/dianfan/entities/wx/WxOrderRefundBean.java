package dianfan.entities.wx;

/**
 * @ClassName WxOrderRefundBean
 * @Description 微信退款
 * @author cjy
 * @date 2018年7月10日 下午3:19:39
 */
public class WxOrderRefundBean {
	private String transaction_id; //微信生成的订单号，在支付通知中有返回
	private String out_trade_no; //商户系统内部订单号
	private String out_refund_no; // 商户退款单号,商户系统内部的退款单号，商户系统内部唯一
	private Integer total_fee; // 订单总金额
	private Integer refund_fee; // 退款总金额
	private String refund_desc; // 退款原因
	private String notify_url; // 异步接收微信支付退款结果通知的回调地址
	public String getTransaction_id() {
		return transaction_id;
	}
	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}
	public String getOut_trade_no() {
		return out_trade_no;
	}
	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}
	public String getOut_refund_no() {
		return out_refund_no;
	}
	public void setOut_refund_no(String out_refund_no) {
		this.out_refund_no = out_refund_no;
	}
	public Integer getTotal_fee() {
		return total_fee;
	}
	public void setTotal_fee(Integer total_fee) {
		this.total_fee = total_fee;
	}
	public Integer getRefund_fee() {
		return refund_fee;
	}
	public void setRefund_fee(Integer refund_fee) {
		this.refund_fee = refund_fee;
	}
	public String getRefund_desc() {
		return refund_desc;
	}
	public void setRefund_desc(String refund_desc) {
		this.refund_desc = refund_desc;
	}
	public String getNotify_url() {
		return notify_url;
	}
	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}
	@Override
	public String toString() {
		return "WxOrderRefundBean [transaction_id=" + transaction_id + ", out_trade_no=" + out_trade_no
				+ ", out_refund_no=" + out_refund_no + ", total_fee=" + total_fee + ", refund_fee=" + refund_fee
				+ ", refund_desc=" + refund_desc + ", notify_url=" + notify_url + "]";
	}

}
