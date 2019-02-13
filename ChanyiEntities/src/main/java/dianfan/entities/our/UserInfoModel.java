/**  
* @Title: UserInfoModel.java
* @Package dianfan.entities.our
* @Description: TODO
* @author yl
* @date 2018年6月29日 下午12:08:14
* @version V1.0  
*/ 
package dianfan.entities.our;

import java.math.BigDecimal;

/** @ClassName UserInfoModel
 * @Description 
 * @author yl
 * @date 2018年6月29日 下午12:08:14
 */
public class UserInfoModel {
	
	private String id;//varchar(50) NOT NULL COMMENT '主键id',
	private String unionId; //varchar(100) DEFAULT NULL COMMENT '昵称',
	private String nickName; //varchar(100) DEFAULT NULL COMMENT '昵称',
	private String sex; //varchar(1) DEFAULT NULL COMMENT '性别',
	private String avatarUrl;//varchar(150) DEFAULT NULL COMMENT '头像地址',
	private String telno; //varchar(20) DEFAULT NULL COMMENT '手机号码',
	private String qrNum;//varchar(50) DEFAULT NULL COMMENT '用户二维码随机数',
	private String salerQrNum;// varchar(50) DEFAULT NULL COMMENT '导购二维码随机数'
	private String consumerQrNum;// varchar(50) DEFAULT NULL COMMENT '消费商二维码随机数'
	private String experiencestoreQrNum;// varchar(50) DEFAULT NULL COMMENT '体验店二维码随机数'
	private String roleName;//角色名称
	private BigDecimal lastMoney;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUnionId() {
		return unionId;
	}
	public void setUnionId(String unionId) {
		this.unionId = unionId;
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
	public String getQrNum() {
		return qrNum;
	}
	public void setQrNum(String qrNum) {
		this.qrNum = qrNum;
	}
	public String getSalerQrNum() {
		return salerQrNum;
	}
	public void setSalerQrNum(String salerQrNum) {
		this.salerQrNum = salerQrNum;
	}
	public String getConsumerQrNum() {
		return consumerQrNum;
	}
	public void setConsumerQrNum(String consumerQrNum) {
		this.consumerQrNum = consumerQrNum;
	}
	public String getExperiencestoreQrNum() {
		return experiencestoreQrNum;
	}
	public void setExperiencestoreQrNum(String experiencestoreQrNum) {
		this.experiencestoreQrNum = experiencestoreQrNum;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public BigDecimal getLastMoney() {
		return lastMoney;
	}
	public void setLastMoney(BigDecimal lastMoney) {
		this.lastMoney = lastMoney;
	}
	@Override
	public String toString() {
		return "UserInfoModel [id=" + id + ", nickName=" + nickName + ", sex=" + sex + ", avatarUrl=" + avatarUrl
				+ ", telno=" + telno + ", qrNum=" + qrNum + ", salerQrNum=" + salerQrNum + ", consumerQrNum="
				+ consumerQrNum + ", experiencestoreQrNum=" + experiencestoreQrNum + ", roleName=" + roleName
				+ ", lastMoney=" + lastMoney + "]";
	}
}
