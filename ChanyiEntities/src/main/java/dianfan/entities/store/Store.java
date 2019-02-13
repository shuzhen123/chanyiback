package dianfan.entities.store;

import java.sql.Timestamp;

/**
 * @ClassName Store
 * @Description 体验店
 * @author cjy
 * @date 2018年7月24日 上午10:13:06
 */
public class Store {
	private String id; //varchar(50) NOT NULL COMMENT 'id',
	private String applyName; //varchar(50) DEFAULT NULL COMMENT '体验店名称',
	private String cityCode; //varchar(50) DEFAULT NULL COMMENT '城市code',
	private String cityCodeAddr; //varchar(128) DEFAULT NULL COMMENT '省市区',
	private String applPhoneNum; //varchar(50) DEFAULT NULL COMMENT '手机号',
	private String applyAddr; //varchar(128) DEFAULT NULL COMMENT '地址',
	private String status; //varchar(2) DEFAULT NULL COMMENT '申请状态 01通过 02-未通过03待审核',
	private String fReason; //varchar(150) DEFAULT NULL COMMENT '失败原因',
	private String longitude; //varchar(50) DEFAULT NULL COMMENT '经度',
	private String latitude; //varchar(50) DEFAULT NULL COMMENT '纬度',
	private String area; //varchar(50) DEFAULT NULL COMMENT '面积',
	private String doorheadUrl; //varchar(128) DEFAULT NULL COMMENT '门头url',
	private String innerUrl00; //varchar(128) DEFAULT NULL COMMENT '店内url00',
	private String innerUrl01; //varchar(128) DEFAULT NULL COMMENT '店内url01',
	private String businessLicenceUrl; //varchar(128) DEFAULT NULL COMMENT '营业执照地址',
	private String businessWeeklyStart; //varchar(50) DEFAULT NULL COMMENT '营业日起始',
	private String businessWeeklyEnd; //varchar(50) DEFAULT NULL COMMENT '营业日结束',
	private String businessTimeEnd; //time DEFAULT NULL COMMENT '营业结束时间',
	private String businessTimeStart; //time DEFAULT NULL COMMENT '营业开始时间',
	private String handleIdcard; //varchar(150) DEFAULT NULL COMMENT '手持身份证照片',
	private String applyUserid; //varchar(50) DEFAULT NULL COMMENT '体验店申请人id',
	private String applyUserName; //varchar(50) DEFAULT NULL COMMENT '体验店申请人姓名',
	private Timestamp createTime; //datetime DEFAULT NULL COMMENT '创建时间',
	private String createBy; //varchar(50) DEFAULT NULL COMMENT '创建者id',
	private String createName; //varchar(50) DEFAULT NULL COMMENT '创建者姓名',
	private String updateBy; //varchar(50) DEFAULT NULL COMMENT '更新者id',
	private String updateName; //varchar(50) DEFAULT NULL COMMENT '更新者姓名',
	private Integer entkbn; //int(1) DEFAULT '0' COMMENT '数据有效区分(0:数据有效1:数据无效9:逻辑删除)',
	private String applyCurrentBusiness; //varchar(150) DEFAULT NULL COMMENT '申请店主营业务',
	private String dev_id; //varchar(50) DEFAULT NULL COMMENT '开发人员id【2018/08/15 ADD】', 
	private Timestamp dev_time; //datetime DEFAULT NULL COMMENT '开发时间【2018/08/15 ADD】', 
	private String dev_ways; //varchar(2) NOT NULL DEFAULT '01' COMMENT '01:后台添加 02:线下扫码添加【2018/08/15 ADD】', 
	
	public String getDev_id() {
		return dev_id;
	}
	public void setDev_id(String dev_id) {
		this.dev_id = dev_id;
	}
	public Timestamp getDev_time() {
		return dev_time;
	}
	public void setDev_time(Timestamp dev_time) {
		this.dev_time = dev_time;
	}
	public String getDev_ways() {
		return dev_ways;
	}
	public void setDev_ways(String dev_ways) {
		this.dev_ways = dev_ways;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getApplyName() {
		return applyName;
	}
	public void setApplyName(String applyName) {
		this.applyName = applyName;
	}
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	public String getCityCodeAddr() {
		return cityCodeAddr;
	}
	public void setCityCodeAddr(String cityCodeAddr) {
		this.cityCodeAddr = cityCodeAddr;
	}
	public String getApplPhoneNum() {
		return applPhoneNum;
	}
	public void setApplPhoneNum(String applPhoneNum) {
		this.applPhoneNum = applPhoneNum;
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
	public String getfReason() {
		return fReason;
	}
	public void setfReason(String fReason) {
		this.fReason = fReason;
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
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getDoorheadUrl() {
		return doorheadUrl;
	}
	public void setDoorheadUrl(String doorheadUrl) {
		this.doorheadUrl = doorheadUrl;
	}
	public String getInnerUrl00() {
		return innerUrl00;
	}
	public void setInnerUrl00(String innerUrl00) {
		this.innerUrl00 = innerUrl00;
	}
	public String getInnerUrl01() {
		return innerUrl01;
	}
	public void setInnerUrl01(String innerUrl01) {
		this.innerUrl01 = innerUrl01;
	}
	public String getBusinessLicenceUrl() {
		return businessLicenceUrl;
	}
	public void setBusinessLicenceUrl(String businessLicenceUrl) {
		this.businessLicenceUrl = businessLicenceUrl;
	}
	public String getBusinessWeeklyStart() {
		return businessWeeklyStart;
	}
	public void setBusinessWeeklyStart(String businessWeeklyStart) {
		this.businessWeeklyStart = businessWeeklyStart;
	}
	public String getBusinessWeeklyEnd() {
		return businessWeeklyEnd;
	}
	public void setBusinessWeeklyEnd(String businessWeeklyEnd) {
		this.businessWeeklyEnd = businessWeeklyEnd;
	}
	public String getBusinessTimeEnd() {
		return businessTimeEnd;
	}
	public void setBusinessTimeEnd(String businessTimeEnd) {
		this.businessTimeEnd = businessTimeEnd;
	}
	public String getBusinessTimeStart() {
		return businessTimeStart;
	}
	public void setBusinessTimeStart(String businessTimeStart) {
		this.businessTimeStart = businessTimeStart;
	}
	public String getHandleIdcard() {
		return handleIdcard;
	}
	public void setHandleIdcard(String handleIdcard) {
		this.handleIdcard = handleIdcard;
	}
	public String getApplyUserid() {
		return applyUserid;
	}
	public void setApplyUserid(String applyUserid) {
		this.applyUserid = applyUserid;
	}
	public String getApplyUserName() {
		return applyUserName;
	}
	public void setApplyUserName(String applyUserName) {
		this.applyUserName = applyUserName;
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
	public String getCreateName() {
		return createName;
	}
	public void setCreateName(String createName) {
		this.createName = createName;
	}
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	public String getUpdateName() {
		return updateName;
	}
	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}
	public Integer getEntkbn() {
		return entkbn;
	}
	public void setEntkbn(Integer entkbn) {
		this.entkbn = entkbn;
	}
	public String getApplyCurrentBusiness() {
		return applyCurrentBusiness;
	}
	public void setApplyCurrentBusiness(String applyCurrentBusiness) {
		this.applyCurrentBusiness = applyCurrentBusiness;
	}
	@Override
	public String toString() {
		return "Store [id=" + id + ", applyName=" + applyName + ", cityCode=" + cityCode + ", cityCodeAddr="
				+ cityCodeAddr + ", applPhoneNum=" + applPhoneNum + ", applyAddr=" + applyAddr + ", status=" + status
				+ ", fReason=" + fReason + ", longitude=" + longitude + ", latitude=" + latitude + ", area=" + area
				+ ", doorheadUrl=" + doorheadUrl + ", innerUrl00=" + innerUrl00 + ", innerUrl01=" + innerUrl01
				+ ", businessLicenceUrl=" + businessLicenceUrl + ", businessWeeklyStart=" + businessWeeklyStart
				+ ", businessWeeklyEnd=" + businessWeeklyEnd + ", businessTimeEnd=" + businessTimeEnd
				+ ", businessTimeStart=" + businessTimeStart + ", handleIdcard=" + handleIdcard + ", applyUserid="
				+ applyUserid + ", applyUserName=" + applyUserName + ", createTime=" + createTime + ", createBy="
				+ createBy + ", createName=" + createName + ", updateBy=" + updateBy + ", updateName=" + updateName
				+ ", entkbn=" + entkbn + ", applyCurrentBusiness=" + applyCurrentBusiness + ", dev_id=" + dev_id
				+ ", dev_time=" + dev_time + ", dev_ways=" + dev_ways + "]";
	}
}
