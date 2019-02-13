/**  
* @Title: CityManagerModel.java
* @Package dianfan.entities.our
* @Description: TODO
* @author yl
* @date 2018年8月2日 下午5:38:35
* @version V1.0  
*/ 
package dianfan.entities.our;

import java.sql.Timestamp;

/** @ClassName CityManagerModel
 * @Description 
 * @author yl
 * @date 2018年8月2日 下午5:38:35
 */
public class CityManagerModel {
	
	private String id;
	private String applyname;// varchar(20) DEFAULT NULL COMMENT '申请人姓名'
	private String telno;// varchar(20) DEFAULT NULL COMMENT '手机号码'
	private String status;// varchar(2) DEFAULT NULL COMMENT '申请状态 01通过 02-未通过03待审核'
	private String fReason;// varchar(150) DEFAULT NULL COMMENT '失败原因'
	private Timestamp createTime;// datetime DEFAULT NULL COMMENT '创建时间'
	private String nickName; //varchar(50) DEFAULT NULL COMMENT '姓名',
	private String realName; //varchar(50) DEFAULT NULL COMMENT '姓名',
	private String areaCode; //varchar(8) DEFAULT NULL COMMENT '地址code',
	private String handleIdcard; //varchar(150) DEFAULT NULL COMMENT '手持身份证照片',
	private String idcardNo; //varchar(20) DEFAULT NULL COMMENT '身份证号码',
	private String idcardFont; //varchar(150) DEFAULT NULL COMMENT '身份证正面',
	private String idcardBack; //varchar(150) DEFAULT NULL COMMENT '身份证反面',
	private String idcardValidDate; //varchar(50) DEFAULT NULL COMMENT '身份证有效期',
	private String createTimeStart;// datetime DEFAULT NULL COMMENT '创建时间'
	private String createTimeEnd;// datetime DEFAULT NULL COMMENT '创建时间'
	private Integer start;
	private Integer offset;
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
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	public String getHandleIdcard() {
		return handleIdcard;
	}
	public void setHandleIdcard(String handleIdcard) {
		this.handleIdcard = handleIdcard;
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
	@Override
	public String toString() {
		return "CityManagerModel [id=" + id + ", applyname=" + applyname + ", telno=" + telno + ", status=" + status
				+ ", fReason=" + fReason + ", createTime=" + createTime + ", nickName=" + nickName + ", realName="
				+ realName + ", areaCode=" + areaCode + ", handleIdcard=" + handleIdcard + ", idcardNo=" + idcardNo
				+ ", idcardFont=" + idcardFont + ", idcardBack=" + idcardBack + ", idcardValidDate=" + idcardValidDate
				+ ", createTimeStart=" + createTimeStart + ", createTimeEnd=" + createTimeEnd + ", start=" + start
				+ ", offset=" + offset + "]";
	}
}
