/**  
* @Title: Coupon.java
* @Package dianfan.entities
* @Description: TODO
* @author yl
* @date 2018年6月28日 下午2:07:02
* @version V1.0  
*/
package dianfan.entities;

import java.sql.Timestamp;

/**
 * @ClassName Coupon
 * @Description
 * @author yl
 * @date 2018年6月28日 下午2:07:02
 */
public class Coupon {

	private String id; // varchar(50) NOT NULL COMMENT '主键id'
	private String couponName; // varchar(50) DEFAULT NULL COMMENT '优惠券名称'
	private Integer couponNum; // int(8) DEFAULT NULL COMMENT '优惠券数量'
	private Timestamp couponEndtime; // datetime DEFAULT NULL COMMENT '优惠券截止日期'
	private String couponApply; // varchar(2) DEFAULT NULL COMMENT '使用(01:应用02：停止应用)'
	private String couponType; // varchar(2) DEFAULT NULL COMMENT '优惠券类型(01:商家优惠券02：注册优惠券)'
	private String couponClassifyId; // text COMMENT '优惠券id（可多选，如果为空，全场通用）'
	private String couponDes; // varchar(250) DEFAULT NULL COMMENT '优惠券描述'
	private Timestamp couponStarttime; // datetime DEFAULT NULL COMMENT '优惠券投放时间'
	private Timestamp createTime; // datetime DEFAULT NULL COMMENT '创建时间'
	private String createBy; // varchar(50) DEFAULT NULL COMMENT '创建者'
	private Timestamp updateTime; // timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP; COMMENT '更新时间'
	private String updateBy; // varchar(50) DEFAULT NULL COMMENT '更新者'
	private Integer entkbn; // int(1) DEFAULT '0' COMMENT '数据有效区分(0:数据有效1:数据无效9:逻辑删除)'
	private Integer version; // int(11) NOT NULL DEFAULT '0' COMMENT '版本号'
	private String couponCondtion;//优惠券满足条件
	private String couponReduceMoney;//优惠券优惠金额
	//后台列表显示
	private String couponClassifyName; 
	
	private String goodsid; 
	private String storeid;
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
	public Integer getCouponNum() {
		return couponNum;
	}
	public void setCouponNum(Integer couponNum) {
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
	public String getCouponClassifyName() {
		return couponClassifyName;
	}
	public void setCouponClassifyName(String couponClassifyName) {
		this.couponClassifyName = couponClassifyName;
	}
	public String getGoodsid() {
		return goodsid;
	}
	public void setGoodsid(String goodsid) {
		this.goodsid = goodsid;
	}
	public String getStoreid() {
		return storeid;
	}
	public void setStoreid(String storeid) {
		this.storeid = storeid;
	}
	@Override
	public String toString() {
		return "Coupon [id=" + id + ", couponName=" + couponName + ", couponNum=" + couponNum + ", couponEndtime="
				+ couponEndtime + ", couponApply=" + couponApply + ", couponType=" + couponType + ", couponClassifyId="
				+ couponClassifyId + ", couponDes=" + couponDes + ", couponStarttime=" + couponStarttime
				+ ", createTime=" + createTime + ", createBy=" + createBy + ", updateTime=" + updateTime + ", updateBy="
				+ updateBy + ", entkbn=" + entkbn + ", version=" + version + ", couponCondtion=" + couponCondtion
				+ ", couponReduceMoney=" + couponReduceMoney + ", couponClassifyName=" + couponClassifyName
				+ ", goodsid=" + goodsid + ", storeid=" + storeid + "]";
	}
}
