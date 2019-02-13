package dianfan.entities;

import java.sql.Timestamp;

/**
 * @ClassName UserAddress
 * @Description 用户收货地址
 * @author sz
 * @date 2018年6月30日 下午4:13:45
 */
public class UserAddress {
	
	private String id; //varchar(50) NOT NULL COMMENT '主键id',
	private String userId; //varchar(50) DEFAULT NULL COMMENT '用户id',
	private String name; //varchar(32) DEFAULT NULL COMMENT '收件人姓名',
	private String telno; //varchar(32) DEFAULT NULL COMMENT '收件人联系方式',
	private String areaCode; //varchar(8) DEFAULT NULL COMMENT '地区code',
	private String detailAddr; //varchar(250) DEFAULT NULL COMMENT '详细地址',
	private Timestamp createTime; //datetime DEFAULT NULL COMMENT '创建时间',
	private String createBy; //varchar(50) DEFAULT NULL COMMENT '创建者',
	private Timestamp updateAime; //timestamp NOT NULL DEFAULT  '更新时间',
	private String updateBy; //varchar(50) DEFAULT NULL COMMENT '更新者',
	private Integer version; //
	
	
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
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
	public Timestamp getUpdateAime() {
		return updateAime;
	}
	public void setUpdateAime(Timestamp updateAime) {
		this.updateAime = updateAime;
	}
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	@Override
	public String toString() {
		return "UserAddress [id=" + id + ", userId=" + userId + ", name=" + name + ", telno=" + telno + ", areaCode="
				+ areaCode + ", detailAddr=" + detailAddr + ", createTime=" + createTime + ", createBy=" + createBy
				+ ", updateAime=" + updateAime + ", updateBy=" + updateBy + "]";
	}
	
	

}
