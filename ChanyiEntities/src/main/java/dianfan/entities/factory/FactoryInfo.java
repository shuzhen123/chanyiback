/**  
* @Title: Factory.java
* @Package dianfan.entities.factory
* @Description: TODO
* @author yl
* @date 2018年7月17日 下午1:00:28
* @version V1.0  
*/ 
package dianfan.entities.factory;

import java.sql.Timestamp;

/** @ClassName Factory
 * @Description 
 * @author yl
 * @date 2018年7月17日 下午1:00:28
 */
public class FactoryInfo {
	
	private String id;// varchar(50) NOT NULL COMMENT '主键id',
	private String factoryName;//工厂名称 varchar(50) NOT NULL COMMENT
	private String adminId; //varchar(50) DEFAULT NULL COMMENT '工厂账号',
	private String longitude;// varchar(50) DEFAULT NULL COMMENT '经度',
	private String latitude;// varchar(50) DEFAULT NULL COMMENT '纬度',
	private String factoryAddr;// varchar(150) DEFAULT NULL COMMENT '工厂地址',
	private Timestamp createTime;// datetime DEFAULT NULL COMMENT '创建时间',
	private String createBy;// varchar(50) DEFAULT NULL COMMENT '创建者id',
	private Timestamp updateTime;// timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
	private String updateBy;// varchar(50) DEFAULT NULL COMMENT '更新者',
	private Integer entkbn;// int(1) DEFAULT '0' COMMENT '数据有效区分(0:数据有效1:数据无效9:逻辑删除)',
	private Integer version;// int(11) NOT NULL DEFAULT '0' COMMENT '版本号',
	private transient String pcareaName;//拼接的省市区code
	private transient String detailAreaName;//拼接的省市区code
	private  String areaCode;//区code
	private  String adminids;//管理员
	
	public String getAdminids() {
		return adminids;
	}
	public void setAdminids(String adminids) {
		this.adminids = adminids;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFactoryName() {
		return factoryName;
	}
	public void setFactoryName(String factoryName) {
		this.factoryName = factoryName;
	}
	public String getAdminId() {
		return adminId;
	}
	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getFactoryAddr() {
		return factoryAddr;
	}
	public void setFactoryAddr(String factoryAddr) {
		this.factoryAddr = factoryAddr;
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
	public String getPcareaName() {
		return pcareaName;
	}
	public void setPcareaName(String pcareaName) {
		this.pcareaName = pcareaName;
	}
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	public String getDetailAreaName() {
		return detailAreaName;
	}
	public void setDetailAreaName(String detailAreaName) {
		this.detailAreaName = detailAreaName;
	}
	@Override
	public String toString() {
		return "FactoryInfo [id=" + id + ", factoryName=" + factoryName + ", adminId=" + adminId + ", longitude="
				+ longitude + ", latitude=" + latitude + ", factoryAddr=" + factoryAddr + ", createTime=" + createTime
				+ ", createBy=" + createBy + ", updateTime=" + updateTime + ", updateBy=" + updateBy + ", entkbn="
				+ entkbn + ", version=" + version + ", areaCode=" + areaCode + "]";
	}
}
