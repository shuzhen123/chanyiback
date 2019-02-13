/**  
* @Title: UserCouponDetail.java
* @Package dianfan.entities
* @Description: TODO
* @author yl
* @date 2018年7月19日 下午5:43:54
* @version V1.0  
*/ 
package dianfan.entities;

import java.sql.Timestamp;

/** @ClassName UserCouponDetail
 * @Description 
 * @author yl
 * @date 2018年7月19日 下午5:43:54
 */
public class UserCouponDetail {
	
	private String id; // varchar(50) NOT NULL COMMENT '主键id'
	private String userId;
	private String couponName; // varchar(50) DEFAULT NULL COMMENT '优惠券名称'
	private Integer couponNum; // int(8) DEFAULT NULL COMMENT '优惠券数量'
	private Timestamp couponEndtime; // datetime DEFAULT NULL COMMENT '优惠券截止日期'
	private String couponApply; // varchar(2) DEFAULT NULL COMMENT '使用(01:应用02：停止应用)'
	private String couponType; // varchar(2) DEFAULT NULL COMMENT '优惠券类型(01:商家优惠券02：注册优惠券)'
	private String couponClassifyId; // text COMMENT '优惠券id（可多选，如果为空，全场通用）'
	private String couponDes; // varchar(250) DEFAULT NULL COMMENT '优惠券描述'
	private Timestamp couponStarttime; // datetime DEFAULT NULL COMMENT '优惠券投放时间'
	private String couponCondtion;//优惠券满足条件
	private String couponReduceMoney;//优惠券优惠金额
	private Integer userUsed; // tinyint(1) DEFAULT '0' COMMENT '用户是否使用(默认未被使用)'
	private Timestamp drawDate; // datetime DEFAULT NULL COMMENT '领取日期'
	private Timestamp usedDate; // datetime DEFAULT NULL COMMENT '使用日期'
	private Timestamp usedEndTime; // datetime DEFAULT NULL COMMENT '截止日期'
	private Timestamp createTime; // datetime DEFAULT NULL COMMENT '创建时间'
	
	private String goodsid;//商品id
	private String storeid;//店铺id
	private String nickName;
	private String telno;
	
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
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
	public Integer getUserUsed() {
		return userUsed;
	}
	public void setUserUsed(Integer userUsed) {
		this.userUsed = userUsed;
	}
	public Timestamp getDrawDate() {
		return drawDate;
	}
	public void setDrawDate(Timestamp drawDate) {
		this.drawDate = drawDate;
	}
	public Timestamp getUsedDate() {
		return usedDate;
	}
	public void setUsedDate(Timestamp usedDate) {
		this.usedDate = usedDate;
	}
	public Timestamp getUsedEndTime() {
		return usedEndTime;
	}
	public void setUsedEndTime(Timestamp usedEndTime) {
		this.usedEndTime = usedEndTime;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getTelno() {
		return telno;
	}
	public void setTelno(String telno) {
		this.telno = telno;
	}
	@Override
	public String toString() {
		return "UserCouponDetail [id=" + id + ", userId=" + userId + ", couponName=" + couponName + ", couponNum="
				+ couponNum + ", couponEndtime=" + couponEndtime + ", couponApply=" + couponApply + ", couponType="
				+ couponType + ", couponClassifyId=" + couponClassifyId + ", couponDes=" + couponDes
				+ ", couponStarttime=" + couponStarttime + ", couponCondtion=" + couponCondtion + ", couponReduceMoney="
				+ couponReduceMoney + ", userUsed=" + userUsed + ", drawDate=" + drawDate + ", usedDate=" + usedDate
				+ ", usedEndTime=" + usedEndTime + ", createTime=" + createTime + ", goodsid=" + goodsid + ", storeid="
				+ storeid + ", nickName=" + nickName + ", telno=" + telno + "]";
	}

}
