/**  
* @Title: OrderSuperior.java
* @Package dianfan.entities.order
* @Description: TODO
* @author yl
* @date 2018年7月17日 上午11:26:22
* @version V1.0  
*/ 
package dianfan.entities.order;

import java.math.BigDecimal;

/** @ClassName OrderSuperior
 * @Description 订单上级信息
 * @author yl
 * @date 2018年7月17日 上午11:26:22
 */
public class OrderSuperior {
	
	private String userId;
	private  String nickName;
	private String sex;
	private String avatarUrl;
	private String telno;
	private BigDecimal  bounsFee;
	private String bounsFrom;//提成来源01-自己02-下线
	private String userBounsStatus;//提成状态（01：OK 02:退货03：重新发货 ）
	private BigDecimal cLastMoney;//当前余额
	private BigDecimal bounsPercent;//提成百分比
	private String roleName;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getAvatarUrl() {
		return avatarUrl;
	}
	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}
	public String getTelno() {
		return telno;
	}
	public void setTelno(String telno) {
		this.telno = telno;
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
	public BigDecimal getcLastMoney() {
		return cLastMoney;
	}
	public void setcLastMoney(BigDecimal cLastMoney) {
		this.cLastMoney = cLastMoney;
	}
	public BigDecimal getBounsPercent() {
		return bounsPercent;
	}
	public void setBounsPercent(BigDecimal bounsPercent) {
		this.bounsPercent = bounsPercent;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	@Override
	public String toString() {
		return "OrderSuperior [userId=" + userId + ", nickName=" + nickName + ", sex=" + sex + ", avatarUrl="
				+ avatarUrl + ", telno=" + telno + ", bounsFee=" + bounsFee + ", bounsFrom=" + bounsFrom
				+ ", userBounsStatus=" + userBounsStatus + ", cLastMoney=" + cLastMoney + ", bounsPercent="
				+ bounsPercent + ", roleName=" + roleName + "]";
	}

}
