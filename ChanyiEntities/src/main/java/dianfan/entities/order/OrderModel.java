package dianfan.entities.order;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import dianfan.entities.Coupon;
import dianfan.entities.logistics.DeliveryModels;
import dianfan.entities.our.UserInfoModel;

/**
 * @ClassName OrderModel
 * @Description 订单表
 * @author sz
 * @date 2018年7月5日 下午4:12:56
 */
public class OrderModel {

	private String orderId; //varchar(50) NOT NULL COMMENT '订单号(生成)',
	private String easySpellingId; //varchar(50) DEFAULT NULL COMMENT '易拼id',
	private String userId; //varchar(50) NOT NULL COMMENT '用户id',
	private String addressId; //varchar(50) DEFAULT NULL COMMENT '地址id',
	private String goodsType; //varchar(2) DEFAULT NULL COMMENT '订单类型(01:正常下单02：易拼 03：易团)',
	private String custMessage; //varchar(300) DEFAULT NULL COMMENT '客户备注信息',
	private BigDecimal totalFee; //decimal(10,2) DEFAULT NULL COMMENT '总金额',
	private BigDecimal discountFee; //decimal(10,2) DEFAULT NULL COMMENT '优惠金额',
	private BigDecimal payFee; //decimal(10,2) DEFAULT NULL COMMENT '实付金额',
	private String couponRelateId; //varchar(50) DEFAULT NULL COMMENT '用户相关优惠券表id',
	private String factoryListId; //varchar(50) DEFAULT NULL COMMENT '工厂id',
	private String orderStatus; //varchar(2) DEFAULT NULL COMMENT '订单状态 01-待付款 02-待审核 03-正在生产 04 待发货 05 待收货 06 已收货
	private String goodsStatus; //varchar(2) DEFAULT NULL COMMENT '商品状态 （10-自动分配 11-异常订单（生产不了） 12-待分配订单到工厂（内部专用）13-待生产 14-生产完成  ）',
	private String couponId; //varchar(50) DEFAULT NULL COMMENT '使用的优惠券id（个人或者商家）',
	private Timestamp createTime; //datetime DEFAULT NULL COMMENT '创建时间',
	private String createBy; //varchar(50) DEFAULT NULL COMMENT '创建者',
	private Timestamp updateTime; //timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
	private String updateBy; //varchar(50) DEFAULT NULL COMMENT '更新者',
	
	private Timestamp orderStartTime; //datetime DEFAULT NULL COMMENT '订单开始时间',
	private Timestamp orderEndTime; //datetime DEFAULT NULL COMMENT '订单结束时间',
	
	private List<OrderGoods> orderGoods; //订单下的商品     一对多
	
	// 订单详情中会用到的用户的信息   （用户收货地址中的3个字段）
	private String gainName; //varchar(32) DEFAULT NULL COMMENT '收件人姓名',
	private String gainTelno; //varchar(32) DEFAULT NULL COMMENT '收件人联系方式',
	
	private String areaCode; //varchar(8) DEFAULT NULL COMMENT '地区code',
	private transient String areaName; // '地区名称',
	
	private String addressCode; //根据上面查出来的地区code，将省市区都查出来返回给前段
	
	private String detailAddr; //varchar(250) DEFAULT NULL COMMENT '详细地址',
	 
	// 订单详情中会用到的支付方式 （交易流水表 中的一个字段） 
	private String payWays; //varchar(2) DEFAULT NULL COMMENT '支付方式 01-ALI(支付宝) 02-WX（微信）03-BC（银行卡）',
	
	private String payStatus;//支付状态
	
	private List<OrderSuperior> orderSuperior;//订单上级信息
	
	private UserInfoModel userInfoModel;//用户信息
	
	private String factoryName;//工厂名称
	
	private Coupon coupon;
	
	private String pickupDate;//上门揽件时间
	
	private List<DeliveryModels> dmlist;
	
	private String deliveryStatus;
	
	private BigDecimal roleReduceFee;//角色减免金额总【2018/08/15 ADD】
	
	private BigDecimal couponReduceFee;//优惠券减免额总【2018/08/15 ADD】
	
	private BigDecimal spgReduceFee;//导购二维码减免金额总【2018/08/15 ADD】
	

	public String getAddressCode() {
		return addressCode;
	}

	public void setAddressCode(String addressCode) {
		this.addressCode = addressCode;
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

	public List<OrderGoods> getOrderGoods() {
		return orderGoods;
	}

	public void setOrderGoods(List<OrderGoods> orderGoods) {
		this.orderGoods = orderGoods;
	}

	public String getGainName() {
		return gainName;
	}

	public void setGainName(String gainName) {
		this.gainName = gainName;
	}

	public String getGainTelno() {
		return gainTelno;
	}

	public void setGainTelno(String gainTelno) {
		this.gainTelno = gainTelno;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getDetailAddr() {
		return detailAddr;
	}

	public void setDetailAddr(String detailAddr) {
		this.detailAddr = detailAddr;
	}

	public String getPayWays() {
		return payWays;
	}

	public void setPayWays(String payWays) {
		this.payWays = payWays;
	}

	
	
	public Timestamp getOrderStartTime() {
		return orderStartTime;
	}

	public void setOrderStartTime(Timestamp orderStartTime) {
		this.orderStartTime = orderStartTime;
	}

	public Timestamp getOrderEndTime() {
		return orderEndTime;
	}

	public void setOrderEndTime(Timestamp orderEndTime) {
		this.orderEndTime = orderEndTime;
	}

	public List<OrderSuperior> getOrderSuperior() {
		return orderSuperior;
	}

	public void setOrderSuperior(List<OrderSuperior> orderSuperior) {
		this.orderSuperior = orderSuperior;
	}

	public UserInfoModel getUserInfoModel() {
		return userInfoModel;
	}

	public void setUserInfoModel(UserInfoModel userInfoModel) {
		this.userInfoModel = userInfoModel;
	}

	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getFactoryName() {
		return factoryName;
	}

	public void setFactoryName(String factoryName) {
		this.factoryName = factoryName;
	}

	public Coupon getCoupon() {
		return coupon;
	}

	public void setCoupon(Coupon coupon) {
		this.coupon = coupon;
	}

	public String getPickupDate() {
		return pickupDate;
	}

	public void setPickupDate(String pickupDate) {
		this.pickupDate = pickupDate;
	}

	public List<DeliveryModels> getDmlist() {
		return dmlist;
	}

	public void setDmlist(List<DeliveryModels> dmlist) {
		this.dmlist = dmlist;
	}

	public String getDeliveryStatus() {
		return deliveryStatus;
	}

	public void setDeliveryStatus(String deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}

	public BigDecimal getRoleReduceFee() {
		return roleReduceFee;
	}

	public void setRoleReduceFee(BigDecimal roleReduceFee) {
		this.roleReduceFee = roleReduceFee;
	}

	public BigDecimal getCouponReduceFee() {
		return couponReduceFee;
	}

	public void setCouponReduceFee(BigDecimal couponReduceFee) {
		this.couponReduceFee = couponReduceFee;
	}

	public BigDecimal getSpgReduceFee() {
		return spgReduceFee;
	}

	public void setSpgReduceFee(BigDecimal spgReduceFee) {
		this.spgReduceFee = spgReduceFee;
	}

	@Override
	public String toString() {
		return "OrderModel [orderId=" + orderId + ", easySpellingId=" + easySpellingId + ", userId=" + userId
				+ ", addressId=" + addressId + ", goodsType=" + goodsType + ", custMessage=" + custMessage
				+ ", totalFee=" + totalFee + ", discountFee=" + discountFee + ", payFee=" + payFee + ", couponRelateId="
				+ couponRelateId + ", factoryListId=" + factoryListId + ", orderStatus=" + orderStatus
				+ ", goodsStatus=" + goodsStatus + ", couponId=" + couponId + ", createTime=" + createTime
				+ ", createBy=" + createBy + ", updateTime=" + updateTime + ", updateBy=" + updateBy
				+ ", orderStartTime=" + orderStartTime + ", orderEndTime=" + orderEndTime + ", orderGoods=" + orderGoods
				+ ", gainName=" + gainName + ", gainTelno=" + gainTelno + ", areaCode=" + areaCode + ", addressCode="
				+ addressCode + ", detailAddr=" + detailAddr + ", payWays=" + payWays + ", payStatus=" + payStatus
				+ ", orderSuperior=" + orderSuperior + ", userInfoModel=" + userInfoModel + ", factoryName="
				+ factoryName + ", coupon=" + coupon + ", pickupDate=" + pickupDate + ", dmlist=" + dmlist
				+ ", deliveryStatus=" + deliveryStatus + ", roleReduceFee=" + roleReduceFee + ", couponReduceFee="
				+ couponReduceFee + ", spgReduceFee=" + spgReduceFee + "]";
	}

}
