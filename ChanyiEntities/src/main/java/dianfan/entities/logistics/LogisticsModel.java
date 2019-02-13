/**  
* @Title: LogisticsModel.java
* @Package dianfan.entities.logistics
* @Description: TODO
* @author yl
* @date 2018年7月20日 上午10:58:38
* @version V1.0  
*/ 
package dianfan.entities.logistics;

import java.sql.Date;
import java.sql.Timestamp;

/** @ClassName LogisticsModel
 * @Description 
 * @author yl
 * @date 2018年7月20日 上午10:58:38
 */
public class LogisticsModel {
	
	private String id;// varchar(50) NOT NULL COMMENT '主键id',
	private String mId;// varchar(200) NOT NULL COMMENT '商户单号(不重复)',
	private String deliveryNo;// varchar(50) DEFAULT NULL COMMENT '物流单号',
	private String deptNo;// varchar(50) DEFAULT NULL COMMENT '事业部编号',
	private String expressNo;// varchar(64) DEFAULT NULL COMMENT '快递单号',
	private String senderName;// varchar(50) DEFAULT NULL COMMENT '寄件人姓名',
	private String senderMob;// varchar(50) DEFAULT NULL COMMENT '寄件人手机',
	private String senderPhone;// varchar(20) DEFAULT NULL COMMENT '寄件人电话',
	private String senderAddr;// varchar(200) DEFAULT NULL COMMENT '寄件人地址',
	private String receiverName;// varchar(50) DEFAULT NULL COMMENT '收件人姓名',
	private String receiverMob;// varchar(50) DEFAULT NULL COMMENT '收件人手机',
	private String receiverPhone;// varchar(50) DEFAULT NULL COMMENT '收件人电话',
	private String receiverAddr;// varchar(200) DEFAULT NULL COMMENT '收货人地址',
	private String remark;// varchar(500) DEFAULT NULL COMMENT '订单备注',
	private String isFragile;// varchar(1) DEFAULT NULL COMMENT '是否易碎（1：是2：否）',
	private String sendTo;// varchar(50) DEFAULT NULL COMMENT '始发转运中心名称',
	private Date predictDate;// date DEFAULT NULL COMMENT '预计到仓时间',
	private String isCod;// varchar(1) DEFAULT NULL COMMENT '是否货到付款（1：是 0：否）',
	private String receiveable;// varchar(50) DEFAULT NULL COMMENT '代收金额',
	private String onDoorPickUp;// decimal(10,2) DEFAULT NULL COMMENT '上门揽件标记（1：是2：否）',
	private Timestamp expressTimeReq;// datetime DEFAULT NULL COMMENT '送货时间',
	private String isGuarantee;// varchar(1) DEFAULT NULL COMMENT '是否保价（1：是2：否）',
	private String jdNo;// varchar(50) DEFAULT NULL COMMENT '京东单号',
	private String guaranteeMoney;// varchar(50) DEFAULT NULL COMMENT '保价金额',
	private String receiptFlag;// varchar(1) DEFAULT NULL COMMENT '签单返还（0无签单，1纸质签单，2电子签单，3纸质签单和电子签单）',
	private Date pickupDate;// date DEFAULT NULL COMMENT '上门揽件时间',
	private String paperFrom;// varchar(1) DEFAULT NULL COMMENT '纸质来源（1带单2取单3带单和取单）',
	private String rtnReceiverName;// varchar(50) DEFAULT NULL COMMENT '返单收件人姓名',
	private String rtnReceiverMob;// varchar(50) DEFAULT NULL COMMENT '返单收件人手机号',
	private String rtnReceiverAddr;// varchar(300) DEFAULT NULL COMMENT '返单收件人地址',
	private String rtnReceiverPhone;// varchar(50) DEFAULT NULL COMMENT '返单收件人电话',
	private String weight;// varchar(50) DEFAULT NULL COMMENT '重量（kg）',
	private String length;// varchar(50) DEFAULT NULL COMMENT '长度(mm)',
	private String width;// varchar(50) DEFAULT NULL COMMENT '宽度(mm)',
	private String height;// varchar(50) DEFAULT NULL COMMENT '高度(mm)',
	private String installFlag;// varchar(1) DEFAULT NULL COMMENT '是否安维',
	private String thridCategoryNo;// varchar(20) DEFAULT NULL COMMENT '三级分类编码（安维必填）',
	private String brandNo;// varchar(50) DEFAULT NULL COMMENT '品牌id',
	private String productSku;// varchar(50) DEFAULT NULL COMMENT '商品sku',
	private String packageName;// varchar(200) DEFAULT NULL COMMENT '物品内容',
	private Timestamp createTime;// datetime DEFAULT NULL COMMENT '创建时间',
	private String createBy;// varchar(50) DEFAULT NULL COMMENT '创建者',
	private Timestamp updateTime;// timestamp NOT NULL DEFAULT CURRENTTIMESTAMP ON UPDATE CURRENTTIMESTAMP COMMENT '更新时间',
	private String updateBy;// varchar(50) DEFAULT NULL COMMENT '更新者',
	private Integer entkbn;// int(1) DEFAULT '0' COMMENT '数据有效区分(0:数据有效1:数据无效9:逻辑删除)',
	private Integer version;// int(11) NOT NULL DEFAULT '0' COMMENT '版本号',
	private Timestamp createTimeStart;// datetime DEFAULT NULL COMMENT '创建时间start',
	private Timestamp createTimeEnd;// datetime DEFAULT NULL COMMENT '创建时间end',
	private Integer start;
	private Integer offset;
	private String deliveryType;
	private String deliveryStatus;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getmId() {
		return mId;
	}
	public void setmId(String mId) {
		this.mId = mId;
	}
	public String getDeliveryNo() {
		return deliveryNo;
	}
	public void setDeliveryNo(String deliveryNo) {
		this.deliveryNo = deliveryNo;
	}
	public String getDeptNo() {
		return deptNo;
	}
	public void setDeptNo(String deptNo) {
		this.deptNo = deptNo;
	}
	public String getExpressNo() {
		return expressNo;
	}
	public void setExpressNo(String expressNo) {
		this.expressNo = expressNo;
	}
	public String getSenderName() {
		return senderName;
	}
	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}
	public String getSenderMob() {
		return senderMob;
	}
	public void setSenderMob(String senderMob) {
		this.senderMob = senderMob;
	}
	public String getSenderPhone() {
		return senderPhone;
	}
	public void setSenderPhone(String senderPhone) {
		this.senderPhone = senderPhone;
	}
	public String getSenderAddr() {
		return senderAddr;
	}
	public void setSenderAddr(String senderAddr) {
		this.senderAddr = senderAddr;
	}
	public String getReceiverName() {
		return receiverName;
	}
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}
	public String getReceiverMob() {
		return receiverMob;
	}
	public void setReceiverMob(String receiverMob) {
		this.receiverMob = receiverMob;
	}
	public String getReceiverPhone() {
		return receiverPhone;
	}
	public void setReceiverPhone(String receiverPhone) {
		this.receiverPhone = receiverPhone;
	}
	public String getReceiverAddr() {
		return receiverAddr;
	}
	public void setReceiverAddr(String receiverAddr) {
		this.receiverAddr = receiverAddr;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getIsFragile() {
		return isFragile;
	}
	public void setIsFragile(String isFragile) {
		this.isFragile = isFragile;
	}
	public String getSendTo() {
		return sendTo;
	}
	public void setSendTo(String sendTo) {
		this.sendTo = sendTo;
	}
	public Date getPredictDate() {
		return predictDate;
	}
	public void setPredictDate(Date predictDate) {
		this.predictDate = predictDate;
	}
	public String getIsCod() {
		return isCod;
	}
	public void setIsCod(String isCod) {
		this.isCod = isCod;
	}
	public String getReceiveable() {
		return receiveable;
	}
	public void setReceiveable(String receiveable) {
		this.receiveable = receiveable;
	}
	public String getOnDoorPickUp() {
		return onDoorPickUp;
	}
	public void setOnDoorPickUp(String onDoorPickUp) {
		this.onDoorPickUp = onDoorPickUp;
	}
	public Timestamp getExpressTimeReq() {
		return expressTimeReq;
	}
	public void setExpressTimeReq(Timestamp expressTimeReq) {
		this.expressTimeReq = expressTimeReq;
	}
	public String getIsGuarantee() {
		return isGuarantee;
	}
	public void setIsGuarantee(String isGuarantee) {
		this.isGuarantee = isGuarantee;
	}
	public String getJdNo() {
		return jdNo;
	}
	public void setJdNo(String jdNo) {
		this.jdNo = jdNo;
	}
	public String getGuaranteeMoney() {
		return guaranteeMoney;
	}
	public void setGuaranteeMoney(String guaranteeMoney) {
		this.guaranteeMoney = guaranteeMoney;
	}
	public String getReceiptFlag() {
		return receiptFlag;
	}
	public void setReceiptFlag(String receiptFlag) {
		this.receiptFlag = receiptFlag;
	}
	public Date getPickupDate() {
		return pickupDate;
	}
	public void setPickupDate(Date pickupDate) {
		this.pickupDate = pickupDate;
	}
	public String getPaperFrom() {
		return paperFrom;
	}
	public void setPaperFrom(String paperFrom) {
		this.paperFrom = paperFrom;
	}
	public String getRtnReceiverName() {
		return rtnReceiverName;
	}
	public void setRtnReceiverName(String rtnReceiverName) {
		this.rtnReceiverName = rtnReceiverName;
	}
	public String getRtnReceiverMob() {
		return rtnReceiverMob;
	}
	public void setRtnReceiverMob(String rtnReceiverMob) {
		this.rtnReceiverMob = rtnReceiverMob;
	}
	public String getRtnReceiverAddr() {
		return rtnReceiverAddr;
	}
	public void setRtnReceiverAddr(String rtnReceiverAddr) {
		this.rtnReceiverAddr = rtnReceiverAddr;
	}
	public String getRtnReceiverPhone() {
		return rtnReceiverPhone;
	}
	public void setRtnReceiverPhone(String rtnReceiverPhone) {
		this.rtnReceiverPhone = rtnReceiverPhone;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getLength() {
		return length;
	}
	public void setLength(String length) {
		this.length = length;
	}
	public String getWidth() {
		return width;
	}
	public void setWidth(String width) {
		this.width = width;
	}
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	public String getInstallFlag() {
		return installFlag;
	}
	public void setInstallFlag(String installFlag) {
		this.installFlag = installFlag;
	}
	public String getThridCategoryNo() {
		return thridCategoryNo;
	}
	public void setThridCategoryNo(String thridCategoryNo) {
		this.thridCategoryNo = thridCategoryNo;
	}
	public String getBrandNo() {
		return brandNo;
	}
	public void setBrandNo(String brandNo) {
		this.brandNo = brandNo;
	}
	public String getProductSku() {
		return productSku;
	}
	public void setProductSku(String productSku) {
		this.productSku = productSku;
	}
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
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
	public Timestamp getCreateTimeStart() {
		return createTimeStart;
	}
	public void setCreateTimeStart(Timestamp createTimeStart) {
		this.createTimeStart = createTimeStart;
	}
	public Timestamp getCreateTimeEnd() {
		return createTimeEnd;
	}
	public void setCreateTimeEnd(Timestamp createTimeEnd) {
		this.createTimeEnd = createTimeEnd;
	}
	public Integer getStart() {
		return start;
	}
	public void setStart(Integer start) {
		this.start = start;
	}
	public Integer getOffset() {
		return offset;
	}
	public void setOffset(Integer offset) {
		this.offset = offset;
	}
	public String getDeliveryType() {
		return deliveryType;
	}
	public void setDeliveryType(String deliveryType) {
		this.deliveryType = deliveryType;
	}
	public String getDeliveryStatus() {
		return deliveryStatus;
	}
	public void setDeliveryStatus(String deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}
	@Override
	public String toString() {
		return "LogisticsModel [id=" + id + ", mId=" + mId + ", deliveryNo=" + deliveryNo + ", deptNo=" + deptNo
				+ ", expressNo=" + expressNo + ", senderName=" + senderName + ", senderMob=" + senderMob
				+ ", senderPhone=" + senderPhone + ", senderAddr=" + senderAddr + ", receiverName=" + receiverName
				+ ", receiverMob=" + receiverMob + ", receiverPhone=" + receiverPhone + ", receiverAddr=" + receiverAddr
				+ ", remark=" + remark + ", isFragile=" + isFragile + ", sendTo=" + sendTo + ", predictDate="
				+ predictDate + ", isCod=" + isCod + ", receiveable=" + receiveable + ", onDoorPickUp=" + onDoorPickUp
				+ ", expressTimeReq=" + expressTimeReq + ", isGuarantee=" + isGuarantee + ", jdNo=" + jdNo
				+ ", guaranteeMoney=" + guaranteeMoney + ", receiptFlag=" + receiptFlag + ", pickupDate=" + pickupDate
				+ ", paperFrom=" + paperFrom + ", rtnReceiverName=" + rtnReceiverName + ", rtnReceiverMob="
				+ rtnReceiverMob + ", rtnReceiverAddr=" + rtnReceiverAddr + ", rtnReceiverPhone=" + rtnReceiverPhone
				+ ", weight=" + weight + ", length=" + length + ", width=" + width + ", height=" + height
				+ ", installFlag=" + installFlag + ", thridCategoryNo=" + thridCategoryNo + ", brandNo=" + brandNo
				+ ", productSku=" + productSku + ", packageName=" + packageName + ", createTime=" + createTime
				+ ", createBy=" + createBy + ", updateTime=" + updateTime + ", updateBy=" + updateBy + ", entkbn="
				+ entkbn + ", version=" + version + ", createTimeStart=" + createTimeStart + ", createTimeEnd="
				+ createTimeEnd + ", start=" + start + ", offset=" + offset + ", deliveryType=" + deliveryType
				+ ", deliveryStatus=" + deliveryStatus + "]";
	}

}
