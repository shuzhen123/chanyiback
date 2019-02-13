/**  
* @Title: UserBounsDetail.java
* @Package dianfan.entities.our
* @Description: TODO
* @author yl
* @date 2018年6月30日 下午12:33:13
* @version V1.0  
*/ 
package dianfan.entities.our;

import java.sql.Timestamp;

/** @ClassName UserBounsDetail
 * @Description 
 * @author yl
 * @date 2018年6月30日 下午12:33:13
 */
public class UserBounsDetailModel {
	
	private String id;// varchar(50) DEFAULT NULL COMMENT 'id'
	private String userId;// varchar(50) DEFAULT NULL COMMENT '用户编号'
	private String orderNo;// varchar(50) DEFAULT NULL COMMENT '订单编号'
	private String bounsPercent;// decimal(105) DEFAULT NULL COMMENT '提成百分比'
	private Double bounsFee;// decimal(102) DEFAULT NULL COMMENT '提成总额'
	private String bounsFrom;// varchar(2) DEFAULT NULL COMMENT '提成来源 01-自己 02-下线'
	private String userBounsStatus;// varchar(2) DEFAULT NULL COMMENT '提成状态（01：OK 02:退货03：重新发货 ）'
	private String reason;// varchar(150) DEFAULT NULL COMMENT '无效原因'
	private Timestamp createTime;// datetime DEFAULT NULL COMMENT '创建时间'
	private String entkbn;// int(1) DEFAULT '0' COMMENT '数据有效区分(0:数据有效1:数据无效9:逻辑删除)'
	
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
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getBounsPercent() {
		return bounsPercent;
	}
	public void setBounsPercent(String bounsPercent) {
		this.bounsPercent = bounsPercent;
	}
	public Double getBounsFee() {
		return bounsFee;
	}
	public void setBounsFee(Double bounsFee) {
		this.bounsFee = bounsFee;
	}
	public String getBounsFrom() {
		return bounsFrom;
	}
	public void setBounsFrom(String bounsFrom) {
		this.bounsFrom = bounsFrom;
	}
	public String getUserBounsStatus() {
		return userBounsStatus;
	}
	public void setUserBounsStatus(String userBounsStatus) {
		this.userBounsStatus = userBounsStatus;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public String getEntkbn() {
		return entkbn;
	}
	public void setEntkbn(String entkbn) {
		this.entkbn = entkbn;
	}
	@Override
	public String toString() {
		return "UserBounsDetailModel [id=" + id + ", userId=" + userId + ", orderNo=" + orderNo + ", bounsPercent="
				+ bounsPercent + ", bounsFee=" + bounsFee + ", bounsFrom=" + bounsFrom + ", userBounsStatus="
				+ userBounsStatus + ", reason=" + reason + ", createTime=" + createTime + ", entkbn=" + entkbn + "]";
	}
}
