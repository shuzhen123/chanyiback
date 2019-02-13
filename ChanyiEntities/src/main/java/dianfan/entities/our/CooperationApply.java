/**  
* @Title: CooperationApply.java
* @Package dianfan.entities.our
* @Description: TODO
* @author yl
* @date 2018年6月29日 下午2:55:55
* @version V1.0  
*/ 
package dianfan.entities.our;

import java.sql.Timestamp;

/** @ClassName CooperationApply
 * @Description 
 * @author yl
 * @date 2018年6月29日 下午2:55:55
 */
public class CooperationApply {
	
	private Integer id;// int(9) NOT NULL COMMENT 'id'
	private String applyName;// varchar(50) DEFAULT NULL COMMENT '申请人姓名'
	private String applyPhoneNum;// varchar(50) DEFAULT NULL COMMENT '手机号'
	private String applyAddr;// varchar(128) DEFAULT NULL COMMENT '地址'
	private String status;// 申请状态 01通过 02-未通过03待审核
	private String userId;// varchar(50) DEFAULT NULL COMMENT '用户分配的id'
	private Timestamp createTime;// datetime DEFAULT NULL COMMENT '创建时间'
	private String createBy;// varchar(50) DEFAULT NULL COMMENT '创建者'
	private Timestamp updateTime;// timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE private String CURRENT_TIMESTAMP COMMENT '更新时间'private String 
	private String updateBy;// varchar(50) DEFAULT NULL COMMENT '更新者'private String 
	private Integer entkbn;// int(1) DEFAULT '0' COMMENT '数据有效区分(0:数据有效1:数据无效9:逻辑删除)'
	private Integer version;// int(11) NOT NULL DEFAULT '0' COMMENT '版本号'
	private String realName;// varchar(50) DEFAULT NULL COMMENT '姓名'
	private String idcardNo;// varchar(20) DEFAULT NULL COMMENT '身份证号码'
	private String idcardFont;// varchar(150) DEFAULT NULL COMMENT '身份证正面'
	private String idcardBack;// varchar(150) DEFAULT NULL COMMENT '身份证反面'
	private String idcardValidDate;// varchar(50) DEFAULT NULL COMMENT '身份证有效期'
	private String handleIdcard;// varchar(150) DEFAULT NULL COMMENT '手持身份证照片'
	private String fReason;
	//以下为后台列表显示数据
	private String createTimeStart;
	private String createTimeEnd;
	private Integer start;
	private Integer offset;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getApplyName() {
		return applyName;
	}
	public void setApplyName(String applyName) {
		this.applyName = applyName;
	}
	public String getApplyPhoneNum() {
		return applyPhoneNum;
	}
	public void setApplyPhoneNum(String applyPhoneNum) {
		this.applyPhoneNum = applyPhoneNum;
	}
	public String getApplyAddr() {
		return applyAddr;
	}
	public void setApplyAddr(String applyAddr) {
		this.applyAddr = applyAddr;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
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
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getIdcardNo() {
		return idcardNo;
	}
	public void setIdcardNo(String idcardNo) {
		this.idcardNo = idcardNo;
	}
	public String getIdcardFont() {
		return idcardFont;
	}
	public void setIdcardFont(String idcardFont) {
		this.idcardFont = idcardFont;
	}
	public String getIdcardBack() {
		return idcardBack;
	}
	public void setIdcardBack(String idcardBack) {
		this.idcardBack = idcardBack;
	}
	public String getIdcardValidDate() {
		return idcardValidDate;
	}
	public void setIdcardValidDate(String idcardValidDate) {
		this.idcardValidDate = idcardValidDate;
	}
	public String getHandleIdcard() {
		return handleIdcard;
	}
	public void setHandleIdcard(String handleIdcard) {
		this.handleIdcard = handleIdcard;
	}
	public String getCreateTimeStart() {
		return createTimeStart;
	}
	public void setCreateTimeStart(String createTimeStart) {
		this.createTimeStart = createTimeStart;
	}
	public String getCreateTimeEnd() {
		return createTimeEnd;
	}
	public void setCreateTimeEnd(String createTimeEnd) {
		this.createTimeEnd = createTimeEnd;
	}
	public Integer getStart() {
		return start;
	}
	public void setStart(Integer start) {
		this.start = start;
	}
	public Integer getOffset() {
		return offset;
	}
	public void setOffset(Integer offset) {
		this.offset = offset;
	}
	public String getfReason() {
		return fReason;
	}
	public void setfReason(String fReason) {
		this.fReason = fReason;
	}
	@Override
	public String toString() {
		return "CooperationApply [id=" + id + ", applyName=" + applyName + ", applyPhoneNum=" + applyPhoneNum
				+ ", applyAddr=" + applyAddr + ", status=" + status + ", userId=" + userId + ", createTime="
				+ createTime + ", createBy=" + createBy + ", updateTime=" + updateTime + ", updateBy=" + updateBy
				+ ", entkbn=" + entkbn + ", version=" + version + ", realName=" + realName + ", idcardNo=" + idcardNo
				+ ", idcardFont=" + idcardFont + ", idcardBack=" + idcardBack + ", idcardValidDate=" + idcardValidDate
				+ ", handleIdcard=" + handleIdcard + ", fReason=" + fReason + ", createTimeStart=" + createTimeStart
				+ ", createTimeEnd=" + createTimeEnd + ", start=" + start + ", offset=" + offset + "]";
	}
	
}
