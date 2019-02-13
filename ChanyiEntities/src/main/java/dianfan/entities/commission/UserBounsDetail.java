package dianfan.entities.commission;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.xml.crypto.Data;

/**
 * @ClassName UserBounsDetail
 * @Description 用户提成明细
 * @author cjy
 * @date 2018年7月5日 上午10:54:23
 */
public class UserBounsDetail {
	private String id; //varchar(50) DEFAULT NULL COMMENT 'id自增',
	private String userId; //varchar(50) DEFAULT NULL COMMENT '用户编号',
	private String orderNo; //varchar(50) DEFAULT NULL COMMENT '订单编号',
	private BigDecimal bounsPercent; //decimal(10,5) DEFAULT NULL COMMENT '提成百分比',
	private BigDecimal bounsFee; //decimal(10,2) DEFAULT NULL COMMENT '提成总额',
	private String bounsFrom; //varchar(2) DEFAULT NULL COMMENT '提成来源 01-自己02-下线',
	private String userBounsStatus; //varchar(2) DEFAULT NULL COMMENT '提成状态（01：OK 02:退货03：重新发货 ）',
	private String reason; //varchar(150) DEFAULT NULL COMMENT '无效原因',
	private Timestamp createTime; //varchar(150) DEFAULT NULL COMMENT '创建时间',
	private BigDecimal cLastMoney; //varchar(150) DEFAULT NULL COMMENT '当前余额',
	
	
	private String roleid; // 角色ID
	private String role; // 角色名
	private String username; // 用户名
	
	
	
	
	
	public String getRoleid() {
		return roleid;
	}
	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
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
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public BigDecimal getBounsPercent() {
		return bounsPercent;
	}
	public void setBounsPercent(BigDecimal bounsPercent) {
		this.bounsPercent = bounsPercent;
	}
	public BigDecimal getBounsFee() {
		return bounsFee;
	}
	public void setBounsFee(BigDecimal bounsFee) {
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
	public BigDecimal getcLastMoney() {
		return cLastMoney;
	}
	public void setcLastMoney(BigDecimal cLastMoney) {
		this.cLastMoney = cLastMoney;
	}
	@Override
	public String toString() {
		return "UserBounsDetail [id=" + id + ", userId=" + userId + ", orderNo=" + orderNo + ", bounsPercent="
				+ bounsPercent + ", bounsFee=" + bounsFee + ", bounsFrom=" + bounsFrom + ", userBounsStatus="
				+ userBounsStatus + ", reason=" + reason + ", createTime=" + createTime + ", cLastMoney=" + cLastMoney
				+ ", roleid=" + roleid + ", role=" + role + ", username=" + username + "]";
	}


}
