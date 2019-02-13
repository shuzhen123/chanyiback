package dianfan.entities;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

/**
 * @ClassName: UserInfo 
 * @Description: 用户信息表
 * @author sz
 * @date 2018年6月28日 上午12:12:41
 */
public class UserInfo {
	
	private String id;//varchar(50) NOT NULL COMMENT '主键id',
	private String unionId;//varchar(50) DEFAULT NULL COMMENT '微信开放平台UNIONID',
	private String wxpubOpenid;//varchar(50) DEFAULT NULL COMMENT '微信公众号OPENID',
	private String wxsmallOpenid;//varchar(50) DEFAULT NULL COMMENT '微信小程序OPENID',
	private String nickName; //varchar(100) DEFAULT NULL COMMENT '昵称',
	private String country; //varchar(100) DEFAULT NULL COMMENT '国家',
	private String sex; //varchar(1) DEFAULT NULL COMMENT '性别',
	private String avatarUrl;//varchar(150) DEFAULT NULL COMMENT '头像地址',
	private String telno; //varchar(20) DEFAULT NULL COMMENT '手机号码',
	private String areaCode; //varchar(8) DEFAULT NULL COMMENT '地区code',
	private String bankCardNo;//varchar(50) DEFAULT NULL COMMENT '银行卡号',
	private String bankCardName;//varchar(50) DEFAULT NULL COMMENT '银行卡银行名称',
	private String bankShort;//varchar(50) DEFAULT NULL COMMENT '银行简称',
	private Integer locked; //tinyint(1) DEFAULT NULL COMMENT '账户冻结(false默认不冻结)',
	private String pwd; //varchar(64) DEFAULT NULL COMMENT '密码',
	private String qrNum;//varchar(50) DEFAULT NULL COMMENT '用户二维码随机数',
	private String extraId;//varchar(50) DEFAULT NULL COMMENT '资料id',
	private Timestamp createTime;//datetime DEFAULT NULL COMMENT '创建时间',
	private String createBy; //varchar(50) DEFAULT NULL COMMENT '创建者',
	private String createName; //varchar(50) DEFAULT NULL COMMENT '创建者名称',
	private Timestamp updateTime;//timestamp NOT NULL DEFAULT  '更新时间',
	private String updateBy; //varchar(50) DEFAULT NULL COMMENT '更新者',
	private String updateName; //varchar(50) DEFAULT NULL COMMENT '更新者名称',
	private String source; //用户来源(01：小程序02：app 03 手机网站 04其他)',
	
	private BigDecimal lastMoney; //余额
	private int entkbn; //余额
	
	private List<UserAddress> UserAddress; // 一对多关系 关联用户的收货地址表 UserAddress
	
	// 后台用户管理页面需要显示添加的字段
	private String roleid; //用户角色
	private String role; //用户角色
	private String areatext; // 通过区域的查出来的code对应的区域的完整信息
	
	private Integer goodsCount; // 用户商品数量
	

	public String getCreateName() {
		return createName;
	}
	public void setCreateName(String createName) {
		this.createName = createName;
	}
	public String getUpdateName() {
		return updateName;
	}
	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}
	public Integer getGoodsCount() {
		return goodsCount;
	}
	public void setGoodsCount(Integer goodsCount) {
		this.goodsCount = goodsCount;
	}
	public String getRoleid() {
		return roleid;
	}
	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}
	public int getEntkbn() {
		return entkbn;
	}
	public void setEntkbn(int entkbn) {
		this.entkbn = entkbn;
	}
	public BigDecimal getLastMoney() {
		return lastMoney;
	}
	public void setLastMoney(BigDecimal lastMoney) {
		this.lastMoney = lastMoney;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public List<UserAddress> getUserAddress() {
		return UserAddress;
	}
	public void setUserAddress(List<UserAddress> userAddress) {
		UserAddress = userAddress;
	}
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
	public String getWxpubOpenid() {
		return wxpubOpenid;
	}
	public void setWxpubOpenid(String wxpubOpenid) {
		this.wxpubOpenid = wxpubOpenid;
	}
	public String getWxsmallOpenid() {
		return wxsmallOpenid;
	}
	public void setWxsmallOpenid(String wxsmallOpenid) {
		this.wxsmallOpenid = wxsmallOpenid;
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
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	public String getBankCardNo() {
		return bankCardNo;
	}
	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}
	public String getBankCardName() {
		return bankCardName;
	}
	public void setBankCardName(String bankCardName) {
		this.bankCardName = bankCardName;
	}
	public String getBankShort() {
		return bankShort;
	}
	public void setBankShort(String bankShort) {
		this.bankShort = bankShort;
	}
	public Integer getLocked() {
		return locked;
	}
	public void setLocked(Integer locked) {
		this.locked = locked;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getQrNum() {
		return qrNum;
	}
	public void setQrNum(String qrNum) {
		this.qrNum = qrNum;
	}
	public String getExtraId() {
		return extraId;
	}
	public void setExtraId(String extraId) {
		this.extraId = extraId;
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
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getAreatext() {
		return areatext;
	}
	public void setAreatext(String areatext) {
		this.areatext = areatext;
	}
	@Override
	public String toString() {
		return "UserInfo [id=" + id + ", unionId=" + unionId + ", wxpubOpenid=" + wxpubOpenid + ", wxsmallOpenid="
				+ wxsmallOpenid + ", nickName=" + nickName + ", country=" + country + ", sex=" + sex + ", avatarUrl="
				+ avatarUrl + ", telno=" + telno + ", areaCode=" + areaCode + ", bankCardNo=" + bankCardNo
				+ ", bankCardName=" + bankCardName + ", bankShort=" + bankShort + ", locked=" + locked + ", pwd=" + pwd
				+ ", qrNum=" + qrNum + ", extraId=" + extraId + ", createTime=" + createTime + ", createBy=" + createBy
				+ ", updateTime=" + updateTime + ", updateBy=" + updateBy + ", source=" + source + ", lastMoney="
				+ lastMoney + ", entkbn=" + entkbn + ", UserAddress=" + UserAddress + ", roleid=" + roleid + ", role="
				+ role + ", areatext=" + areatext + "]";
	}




	
	
	 

}