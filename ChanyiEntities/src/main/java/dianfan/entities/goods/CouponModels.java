package dianfan.entities.goods;

import java.sql.Timestamp;

/**
 * @ClassName Coupon
 * @Description 优惠券表
 * @author sz
 * @date 2018年7月4日 下午1:48:02
 */
public class CouponModels {
	
	private String id; //varchar(50) NOT NULL COMMENT '主键id',
	private String couponName; //varchar(50) DEFAULT NULL COMMENT '优惠券名称',
	private String couponNum; //int(8) DEFAULT NULL COMMENT '优惠券数量',
	private Timestamp couponEndtime; //datetime DEFAULT NULL COMMENT '优惠券截止日期',
	private String couponApply; //varchar(2) DEFAULT NULL COMMENT '使用(01:应用02：停止应用)',
	private String couponType; //varchar(2) DEFAULT NULL COMMENT '优惠券类型(01:商家优惠券02：注册优惠券03：红包)',
	private String couponCondtion; //varchar(50) DEFAULT NULL COMMENT '优惠券满足条件',
	private String couponReduceMoney; //varchar(50) DEFAULT NULL COMMENT '优惠券优惠金额',
	private String couponClassifyId; //text COMMENT '优惠券id（可多选，如果为空，全场通用）',
	private String couponDes; //varchar(250) DEFAULT NULL COMMENT '优惠券描述',
	private Timestamp couponStarttime; //datetime DEFAULT NULL COMMENT '优惠券投放时间',
	private Timestamp createTime; //datetime DEFAULT NULL COMMENT '创建时间',
	private String createBy; //varchar(50) DEFAULT NULL COMMENT '创建者',
	private Timestamp updateTime; //timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
	private String updateBy; //varchar(50) DEFAULT NULL COMMENT '更新者',
	private String version; //int(11) NOT NULL DEFAULT '0' COMMENT '版本号',
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCouponName() {
		return couponName;
	}
	public void setCouponName(String couponName) {
		this.couponName = couponName;
	}
	public String getCouponNum() {
		return couponNum;
	}
	public void setCouponNum(String couponNum) {
		this.couponNum = couponNum;
	}
	public Timestamp getCouponEndtime() {
		return couponEndtime;
	}
	public void setCouponEndtime(Timestamp couponEndtime) {
		this.couponEndtime = couponEndtime;
	}
	public String getCouponApply() {
		return couponApply;
	}
	public void setCouponApply(String couponApply) {
		this.couponApply = couponApply;
	}
	public String getCouponType() {
		return couponType;
	}
	public void setCouponType(String couponType) {
		this.couponType = couponType;
	}
	public String getCouponCondtion() {
		return couponCondtion;
	}
	public void setCouponCondtion(String couponCondtion) {
		this.couponCondtion = couponCondtion;
	}
	public String getCouponReduceMoney() {
		return couponReduceMoney;
	}
	public void setCouponReduceMoney(String couponReduceMoney) {
		this.couponReduceMoney = couponReduceMoney;
	}
	public String getCouponClassifyId() {
		return couponClassifyId;
	}
	public void setCouponClassifyId(String couponClassifyId) {
		this.couponClassifyId = couponClassifyId;
	}
	public String getCouponDes() {
		return couponDes;
	}
	public void setCouponDes(String couponDes) {
		this.couponDes = couponDes;
	}
	public Timestamp getCouponStarttime() {
		return couponStarttime;
	}
	public void setCouponStarttime(Timestamp couponStarttime) {
		this.couponStarttime = couponStarttime;
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
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	@Override
	public String toString() {
		return "CouponModels [id=" + id + ", couponName=" + couponName + ", couponNum=" + couponNum + ", couponEndtime="
				+ couponEndtime + ", couponApply=" + couponApply + ", couponType=" + couponType + ", couponCondtion="
				+ couponCondtion + ", couponReduceMoney=" + couponReduceMoney + ", couponClassifyId=" + couponClassifyId
				+ ", couponDes=" + couponDes + ", couponStarttime=" + couponStarttime + ", createTime=" + createTime
				+ ", createBy=" + createBy + ", updateTime=" + updateTime + ", updateBy=" + updateBy + ", version="
				+ version + "]";
	}
	
	
	
	
	

}
