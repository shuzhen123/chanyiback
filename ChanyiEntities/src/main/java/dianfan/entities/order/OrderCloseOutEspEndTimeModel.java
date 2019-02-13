package dianfan.entities.order;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @ClassName OrderModel
 * @Description 订单表
 * @author sz
 * @date 2018年7月5日 下午4:12:56
 */
public class OrderCloseOutEspEndTimeModel {

	private String orderId; // varchar(50) NOT NULL COMMENT '订单号(生成)',
	private String easySpellingId; // varchar(50) DEFAULT NULL COMMENT '易拼id',
	private String userId; // varchar(50) NOT NULL COMMENT '用户id',
	private String addressId; // varchar(50) DEFAULT NULL COMMENT '地址id',
	private String goodsType; // varchar(2) DEFAULT NULL COMMENT '订单类型(01:正常下单02：易拼 03：易团)',
	private String custMessage; // varchar(300) DEFAULT NULL COMMENT '客户备注信息',
	private BigDecimal totalFee; // decimal(10,2) DEFAULT NULL COMMENT '总金额',
	private BigDecimal discountFee; // decimal(10,2) DEFAULT NULL COMMENT '优惠金额',
	private BigDecimal payFee; // decimal(10,2) DEFAULT NULL COMMENT '实付金额',
	private String couponRelateId; // varchar(50) DEFAULT NULL COMMENT '用户相关优惠券表id',
	private String factoryListId; // varchar(50) DEFAULT NULL COMMENT '工厂id',
	private String orderStatus; // varchar(2) DEFAULT NULL COMMENT '订单状态 01-待付款 02-待审核 03-正在生产 04 待发货 05 待收货 06
								// 已收货
	private String goodsStatus; // varchar(2) DEFAULT NULL COMMENT '商品状态 （10-自动分配 11-异常订单（生产不了）
								// 12-待分配订单到工厂（内部专用）13-待生产 14-生产完成 ）',
	private String couponId; // varchar(50) DEFAULT NULL COMMENT '使用的优惠券id（个人或者商家）',
	private Timestamp createTime; // datetime DEFAULT NULL COMMENT '创建时间',
	private String createBy; // varchar(50) DEFAULT NULL COMMENT '创建者',
	private Timestamp updateTime; // timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
									// COMMENT '更新时间',
	private String updateBy; // varchar(50) DEFAULT NULL COMMENT '更新者',

	private Timestamp orderStartTime;
	private Timestamp orderEndTime;

	private Timestamp endTime;

	private Integer version;

	/**
	 * @return the endTime
	 */
	public Timestamp getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime
	 *            the endTime to set
	 */
	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getEasySpellingId() {
		return easySpellingId;
	}

	public void setEasySpellingId(String easySpellingId) {
		this.easySpellingId = easySpellingId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAddressId() {
		return addressId;
	}

	public void setAddressId(String addressId) {
		this.addressId = addressId;
	}

	public String getGoodsType() {
		return goodsType;
	}

	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}

	public String getCustMessage() {
		return custMessage;
	}

	public void setCustMessage(String custMessage) {
		this.custMessage = custMessage;
	}

	public BigDecimal getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(BigDecimal totalFee) {
		this.totalFee = totalFee;
	}

	public BigDecimal getDiscountFee() {
		return discountFee;
	}

	public void setDiscountFee(BigDecimal discountFee) {
		this.discountFee = discountFee;
	}

	public BigDecimal getPayFee() {
		return payFee;
	}

	public void setPayFee(BigDecimal payFee) {
		this.payFee = payFee;
	}

	public String getCouponRelateId() {
		return couponRelateId;
	}

	public void setCouponRelateId(String couponRelateId) {
		this.couponRelateId = couponRelateId;
	}

	public String getFactoryListId() {
		return factoryListId;
	}

	public void setFactoryListId(String factoryListId) {
		this.factoryListId = factoryListId;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getGoodsStatus() {
		return goodsStatus;
	}

	public void setGoodsStatus(String goodsStatus) {
		this.goodsStatus = goodsStatus;
	}

	public String getCouponId() {
		return couponId;
	}

	public void setCouponId(String couponId) {
		this.couponId = couponId;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	/**
	 * @return the orderStartTime
	 */
	public Timestamp getOrderStartTime() {
		return orderStartTime;
	}

	/**
	 * @return the orderEndTime
	 */
	public Timestamp getOrderEndTime() {
		return orderEndTime;
	}

	/**
	 * @param orderStartTime
	 *            the orderStartTime to set
	 */
	public void setOrderStartTime(Timestamp orderStartTime) {
		this.orderStartTime = orderStartTime;
	}

	/**
	 * @param orderEndTime
	 *            the orderEndTime to set
	 */
	public void setOrderEndTime(Timestamp orderEndTime) {
		this.orderEndTime = orderEndTime;
	}

	/**
	 * @return the version
	 */
	public Integer getVersion() {
		return version;
	}

	/**
	 * @param version
	 *            the version to set
	 */
	public void setVersion(Integer version) {
		this.version = version;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "OrderCloseOutEspEndTimeModel [orderId=" + orderId + ", easySpellingId=" + easySpellingId + ", userId="
				+ userId + ", addressId=" + addressId + ", goodsType=" + goodsType + ", custMessage=" + custMessage
				+ ", totalFee=" + totalFee + ", discountFee=" + discountFee + ", payFee=" + payFee + ", couponRelateId="
				+ couponRelateId + ", factoryListId=" + factoryListId + ", orderStatus=" + orderStatus
				+ ", goodsStatus=" + goodsStatus + ", couponId=" + couponId + ", createTime=" + createTime
				+ ", createBy=" + createBy + ", updateTime=" + updateTime + ", updateBy=" + updateBy
				+ ", orderStartTime=" + orderStartTime + ", orderEndTime=" + orderEndTime + ", endTime=" + endTime
				+ ", version=" + version + "]";
	}

}
