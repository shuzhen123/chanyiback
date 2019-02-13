package dianfan.entities.order;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @ClassName TradeSer
 * @Description 交易流水
 * @author cjy
 * @date 2018年7月7日 上午11:41:00
 */
public class TradeSer {
	private String payid; //varchar(50) NOT NULL COMMENT '交易id（UUID）',
	private String orderid; //varchar(50) NOT NULL COMMENT '订单ID（时间戳为单号）关联订单表',
	private String serialNumber; //varchar(40) DEFAULT NULL COMMENT '流水号',
	private String tradeNo; //varchar(40) DEFAULT NULL COMMENT '交易号',
	private String payWays; //varchar(2) DEFAULT NULL COMMENT '支付方式 01-ALI(支付宝) 02-WX（微信） 03-BC（银行卡）',
	private String paySource; //varchar(2) DEFAULT NULL COMMENT '支付渠道（01：小程序02：app 03 手机网站 04其他）',
	private String orderStatus; //varchar(2) DEFAULT NULL COMMENT '支付状态 01-支付成功 02-支付失败 03-支付中 04- 发起退款 05-退款中 06-退款成功 07-退款失败',
	private BigDecimal depositFee; //double(10,2) DEFAULT NULL COMMENT '交易总额金额',
	private Timestamp payStartTime; //datetime DEFAULT NULL COMMENT '支付开始时间',
	private Timestamp payEndTime; //datetime DEFAULT NULL COMMENT '支付完成时间',
	private String refundReason; //varchar(50) DEFAULT NULL COMMENT '退款原因',
	private String refundFailReason; //varchar(50) DEFAULT NULL COMMENT '退款失败原因',
	private BigDecimal refundMoney; //double(10,2) DEFAULT NULL COMMENT '退款金额',
	private Timestamp refundStartTime; //datetime DEFAULT NULL COMMENT '退款开始时间',
	private Timestamp refundEndTime; //datetime DEFAULT NULL COMMENT '退款成功时间',
	private Timestamp createTime; //datetime DEFAULT NULL COMMENT '创建时间',
	private Integer entkbn; //int(1) DEFAULT '0' COMMENT '数据有效区分(0:数据有效1:数据无效9:逻辑删除)',
	private Integer version; //int(11) NOT NULL DEFAULT '0' COMMENT '版本号',
	public String getPayid() {
		return payid;
	}
	public void setPayid(String payid) {
		this.payid = payid;
	}
	public String getOrderid() {
		return orderid;
	}
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public String getTradeNo() {
		return tradeNo;
	}
	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}
	public String getPayWays() {
		return payWays;
	}
	public void setPayWays(String payWays) {
		this.payWays = payWays;
	}
	public String getPaySource() {
		return paySource;
	}
	public void setPaySource(String paySource) {
		this.paySource = paySource;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public BigDecimal getDepositFee() {
		return depositFee;
	}
	public void setDepositFee(BigDecimal depositFee) {
		this.depositFee = depositFee;
	}
	public Timestamp getPayStartTime() {
		return payStartTime;
	}
	public void setPayStartTime(Timestamp payStartTime) {
		this.payStartTime = payStartTime;
	}
	public Timestamp getPayEndTime() {
		return payEndTime;
	}
	public void setPayEndTime(Timestamp payEndTime) {
		this.payEndTime = payEndTime;
	}
	public String getRefundReason() {
		return refundReason;
	}
	public void setRefundReason(String refundReason) {
		this.refundReason = refundReason;
	}
	public String getRefundFailReason() {
		return refundFailReason;
	}
	public void setRefundFailReason(String refundFailReason) {
		this.refundFailReason = refundFailReason;
	}
	public BigDecimal getRefundMoney() {
		return refundMoney;
	}
	public void setRefundMoney(BigDecimal refundMoney) {
		this.refundMoney = refundMoney;
	}
	public Timestamp getRefundStartTime() {
		return refundStartTime;
	}
	public void setRefundStartTime(Timestamp refundStartTime) {
		this.refundStartTime = refundStartTime;
	}
	public Timestamp getRefundEndTime() {
		return refundEndTime;
	}
	public void setRefundEndTime(Timestamp refundEndTime) {
		this.refundEndTime = refundEndTime;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public Integer getEntkbn() {
		return entkbn;
	}
	public void setEntkbn(Integer entkbn) {
		this.entkbn = entkbn;
	}
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	@Override
	public String toString() {
		return "TradeSer [payid=" + payid + ", orderid=" + orderid + ", serialNumber=" + serialNumber + ", tradeNo="
				+ tradeNo + ", payWays=" + payWays + ", paySource=" + paySource + ", orderStatus=" + orderStatus
				+ ", depositFee=" + depositFee + ", payStartTime=" + payStartTime + ", payEndTime=" + payEndTime
				+ ", refundReason=" + refundReason + ", refundFailReason=" + refundFailReason + ", refundMoney="
				+ refundMoney + ", refundStartTime=" + refundStartTime + ", refundEndTime=" + refundEndTime
				+ ", createTime=" + createTime + ", entkbn=" + entkbn + ", version=" + version + "]";
	}
}
