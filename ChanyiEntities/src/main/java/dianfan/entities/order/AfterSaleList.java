/**  
* @Title: AfterSale.java
* @Package dianfan.entities.order
* @Description: TODO
* @author yl
* @date 2018年8月3日 下午2:00:13
* @version V1.0  
*/ 
package dianfan.entities.order;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @ClassName AfterSaleList
 * @Description 售后列表
 * @author cjy
 * @date 2018年8月7日 下午12:12:43
 */
public class AfterSaleList {
	private String id; //varchar(50) NOT NULL COMMENT '主键id',
	private String orderId; //varchar(50) DEFAULT NULL COMMENT '订单号',
	private String reason; //varchar(250) DEFAULT NULL COMMENT '换货、退货退款原因',
	private String picUrls; //text COMMENT '换货、退货退款凭证',
	private String handleStatus; //varchar(2) DEFAULT '01' COMMENT '
	private String result; //varchar(2) DEFAULT '01' COMMENT '售后状态 
	private String goodsType; //varchar(50) DEFAULT NULL COMMENT '订单类型(01:正常下单02：易拼 03：易团)',
	private String orderStatus; //varchar(2) DEFAULT NULL COMMENT '订单状态
	private Timestamp createTime; //datetime DEFAULT NULL COMMENT '创建时间',
	private String name; //varchar(50) DEFAULT NULL COMMENT '姓名',
	private String telno; //varchar(50) DEFAULT NULL COMMENT '电话号码',
	private String payWays; //varchar(2) DEFAULT NULL COMMENT '支付方式 01-ALI(支付宝) 02-WX（微信） 03-BC（银行卡）',
	private BigDecimal depositFee; //double(10,2) DEFAULT NULL COMMENT '交易总额金额',
	private String deliveryType; //double(10,2) DEFAULT NULL COMMENT '物流类型    \r\n01：订单发货   \r\n02：退/换货寄回仓库    \r\n03：退货验收失败退回    \r\n04：换货重新发货    \r\n05：换货验收失败退回',
	private String result_f_reason; //
	
	public String getHandleStatus() {
		return handleStatus;
	}
	public void setHandleStatus(String handleStatus) {
		this.handleStatus = handleStatus;
	}
	public String getResult_f_reason() {
		return result_f_reason;
	}
	public void setResult_f_reason(String result_f_reason) {
		this.result_f_reason = result_f_reason;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getPicUrls() {
		return picUrls;
	}
	public void setPicUrls(String picUrls) {
		this.picUrls = picUrls;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getGoodsType() {
		return goodsType;
	}
	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTelno() {
		return telno;
	}
	public void setTelno(String telno) {
		this.telno = telno;
	}
	public String getPayWays() {
		return payWays;
	}
	public void setPayWays(String payWays) {
		this.payWays = payWays;
	}
	public BigDecimal getDepositFee() {
		return depositFee;
	}
	public void setDepositFee(BigDecimal depositFee) {
		this.depositFee = depositFee;
	}
	public String getDeliveryType() {
		return deliveryType;
	}
	public void setDeliveryType(String deliveryType) {
		this.deliveryType = deliveryType;
	}
	@Override
	public String toString() {
		return "AfterSaleList [id=" + id + ", orderId=" + orderId + ", reason=" + reason + ", picUrls=" + picUrls
				+ ", result=" + result + ", goodsType=" + goodsType + ", orderStatus=" + orderStatus + ", createTime="
				+ createTime + ", name=" + name + ", telno=" + telno + ", payWays=" + payWays + ", depositFee="
				+ depositFee + ", deliveryType=" + deliveryType + "]";
	}
}
