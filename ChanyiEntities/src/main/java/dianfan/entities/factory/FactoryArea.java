/**  
* @Title: FactoryArea.java
* @Package dianfan.entities.factory
* @Description: TODO
* @author yl
* @date 2018年7月17日 下午1:04:11
* @version V1.0  
*/ 
package dianfan.entities.factory;

import java.sql.Timestamp;
import java.util.Arrays;

/** @ClassName FactoryArea
 * @Description 
 * @author yl
 * @date 2018年7月17日 下午1:04:11
 */
public class FactoryArea {
	
	private String id;
	private String factoryId;
	private String areaCode;
	private Timestamp createTime;// datetime DEFAULT NULL COMMENT '创建时间',
	private String createBy;// varchar(50) DEFAULT NULL COMMENT '创建者id',
	private Timestamp updateTime;// timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
	private String updateBy;// varchar(50) DEFAULT NULL COMMENT '更新者',
	private Integer entkbn;// int(1) DEFAULT '0' COMMENT '数据有效区分(0:数据有效1:数据无效9:逻辑删除)',
	private Integer version;// int(11) NOT NULL DEFAULT '0' COMMENT '版本号',
	private String[] areacodes;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFactoryId() {
		return factoryId;
	}
	public void setFactoryId(String factoryId) {
		this.factoryId = factoryId;
	}
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
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
	public String[] getAreacodes() {
		return areacodes;
	}
	public void setAreacodes(String[] areacodes) {
		this.areacodes = areacodes;
	}
	@Override
	public String toString() {
		return "FactoryArea [id=" + id + ", factoryId=" + factoryId + ", areaCode=" + areaCode + ", createTime="
				+ createTime + ", createBy=" + createBy + ", updateTime=" + updateTime + ", updateBy=" + updateBy
				+ ", entkbn=" + entkbn + ", version=" + version + ", areacodes=" + Arrays.toString(areacodes) + "]";
	}

}
