/**  
* @Title: CityManagerApply.java
* @Package dianfan.entities.our
* @Description: TODO
* @author yl
* @date 2018年8月2日 下午4:11:04
* @version V1.0  
*/ 
package dianfan.entities.our;

/** @ClassName CityManagerApply
 * @Description 
 * @author yl
 * @date 2018年8月2日 下午4:11:04
 */
public class CityManagerApply {
	
	private String id;// varchar(50) NOT NULL COMMENT '主键id'
	private String applyname;
	private String applyUserId;// varchar(50) DEFAULT NULL COMMENT '申请人id'
	private String telno;// varchar(20) DEFAULT NULL COMMENT '手机号码'
	private String status;// varchar(2) DEFAULT NULL COMMENT '申请状态 01通过 02-未通过03待审核'
	private String fReason;// varchar(150) DEFAULT NULL COMMENT '失败原因'
	private String createTime;// datetime DEFAULT NULL COMMENT '创建时间'
	private String createBy;// varchar(50) DEFAULT NULL COMMENT '创建者'
	private String updateTime;// timestamp NOT NULL DEFAULT CURRENTTIMESTAMP ON UPDATE CURRENTTIMESTAMP COMMENT '更新时间'
	private String updateBy;// varchar(50) DEFAULT NULL COMMENT '更新者'
	private Integer entkbn;// int(1) DEFAULT '0' COMMENT '数据有效区分(0:数据有效1:数据无效9:逻辑删除)'
	private Integer version;// int(11) NOT NULL DEFAULT '0' COMMENT '版本号'
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getApplyname() {
		return applyname;
	}
	public void setApplyname(String applyname) {
		this.applyname = applyname;
	}
	public String getApplyUserId() {
		return applyUserId;
	}
	public void setApplyUserId(String applyUserId) {
		this.applyUserId = applyUserId;
	}
	public String getTelno() {
		return telno;
	}
	public void setTelno(String telno) {
		this.telno = telno;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getfReason() {
		return fReason;
	}
	public void setfReason(String fReason) {
		this.fReason = fReason;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
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
	@Override
	public String toString() {
		return "CityManagerApply [id=" + id + ", applyname=" + applyname + ", applyUserId=" + applyUserId + ", telno="
				+ telno + ", status=" + status + ", fReason=" + fReason + ", createTime=" + createTime + ", createBy="
				+ createBy + ", updateTime=" + updateTime + ", updateBy=" + updateBy + ", entkbn=" + entkbn
				+ ", version=" + version + "]";
	}
	
}
