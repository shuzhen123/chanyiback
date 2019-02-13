package dianfan.entities.order;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @ClassName Order
 * @Description 订单
 * @author cjy
 * @date 2018年7月7日 上午11:36:04
 */
public class Order {
	private String orderid; //varchar(50) NOT NULL COMMENT '订单号(生成)',
	private String easySpellingId; //varchar(50) DEFAULT NULL COMMENT '易拼id',
	private String userid; //varchar(50) NOT NULL COMMENT '用户id',
	private String addressId; //varchar(50) DEFAULT NULL COMMENT '地址id',
	private String goodsType; //varchar(2) DEFAULT NULL COMMENT '订单类型(01:正常下单02：易拼 03：易团)',
	private String custMessage; //varchar(300) DEFAULT NULL COMMENT '客户备注信息',
	private BigDecimal totalFee; //decimal(10,2) DEFAULT NULL COMMENT '总金额',
	private BigDecimal discountFee; //decimal(10,2) DEFAULT NULL COMMENT '优惠金额',
	private BigDecimal payFee; //decimal(10,2) DEFAULT NULL COMMENT '实付金额',
	private String couponRelateId; //varchar(50) DEFAULT NULL COMMENT '用户相关优惠券表id',
	private String factoryListId; //varchar(50) DEFAULT NULL COMMENT '工厂id',
	private String orderStatus; //varchar(2) DEFAULT NULL COMMENT '订单状态',
	private String goodsStatus; //varchar(2) DEFAULT NULL COMMENT '商品状态',
	private String couponId; //varchar(50) DEFAULT NULL COMMENT '使用的优惠券id（个人或者商家）',
	private Timestamp createTime; //datetime DEFAULT NULL COMMENT '创建时间',
	private String createBy; //varchar(50) DEFAULT NULL COMMENT '创建者',
	private String updateBy; //varchar(50) DEFAULT NULL COMMENT '更新者',
	private Integer version; //int(11) NOT NULL DEFAULT '0' COMMENT '版本号',
	private Integer entkbn; //int(1) DEFAULT '0' COMMENT '数据有效区分(0:数据有效1:数据无效9:逻辑删除)',
	
	private String tradeNo; //varchar(40) DEFAULT NULL COMMENT '交易号',
	private Timestamp orderStartTime;//开始时间
	private Timestamp orderEndTime;//结束时间
	private String name;//收货人姓名
	private String telno;//收货人手机号
	private String areaCode;//地区code
	private String detailAddr;//详细地址
	private String source;//订单来源
	private BigDecimal couponReduceFee;//优惠券减免额总【2018/08/15 ADD】
	private BigDecimal roleReduceFee;//角色减免金额总【2018/08/15 ADD】
	private BigDecimal spgReduceFee;//导购二维码减免金额总【2018/08/15 ADD】

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public String getEasySpellingId() {
		return easySpellingId;
	}

	public void setEasySpellingId(String easySpellingId) {
		this.easySpellingId = easySpellingId;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
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

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Integer getEntkbn() {
		return entkbn;
	}

	public void setEntkbn(Integer entkbn) {
		this.entkbn = entkbn;
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

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
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

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public BigDecimal getCouponReduceFee() {
		return couponReduceFee;
	}

	public void setCouponReduceFee(BigDecimal couponReduceFee) {
		this.couponReduceFee = couponReduceFee;
	}

	public BigDecimal getRoleReduceFee() {
		return roleReduceFee;
	}

	public void setRoleReduceFee(BigDecimal roleReduceFee) {
		this.roleReduceFee = roleReduceFee;
	}

	public BigDecimal getSpgReduceFee() {
		return spgReduceFee;
	}

	public void setSpgReduceFee(BigDecimal spgReduceFee) {
		this.spgReduceFee = spgReduceFee;
	}

	@Override
	public String toString() {
		return "Order [orderid=" + orderid + ", easySpellingId=" + easySpellingId + ", userid=" + userid
				+ ", addressId=" + addressId + ", goodsType=" + goodsType + ", custMessage=" + custMessage
				+ ", totalFee=" + totalFee + ", discountFee=" + discountFee + ", payFee=" + payFee + ", couponRelateId="
				+ couponRelateId + ", factoryListId=" + factoryListId + ", orderStatus=" + orderStatus
				+ ", goodsStatus=" + goodsStatus + ", couponId=" + couponId + ", createTime=" + createTime
				+ ", createBy=" + createBy + ", updateBy=" + updateBy + ", version=" + version + ", entkbn=" + entkbn
				+ ", tradeNo=" + tradeNo + ", orderStartTime=" + orderStartTime + ", orderEndTime=" + orderEndTime
				+ ", name=" + name + ", telno=" + telno + ", areaCode=" + areaCode + ", detailAddr=" + detailAddr
				+ ", source=" + source + ", couponReduceFee=" + couponReduceFee + ", roleReduceFee=" + roleReduceFee
				+ ", spgReduceFee=" + spgReduceFee + "]";
	}
}
