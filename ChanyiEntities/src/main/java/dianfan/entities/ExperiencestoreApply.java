/**  
* @Title: ExperiencestoreApply.java
* @Package dianfan.entities
* @Description: TODO
* @author yl
* @date 2018年6月28日 下午7:02:32
* @version V1.0  
*/ 
package dianfan.entities;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import dianfan.entities.goods.GoodsModels;

/** @ClassName ExperiencestoreApply
 * @Description 
 * @author yl
 * @date 2018年6月28日 下午7:02:32
 */
public class ExperiencestoreApply {
	
	private String id; // varchar(50) NOT NULL COMMENT 'id'
	private String status;// 申请状态 01通过 02-未通过03待审核
	private String applyName; // varchar(50) DEFAULT NULL COMMENT '申请人姓名'
	private String applyPhoneNum; // varchar(50) DEFAULT NULL COMMENT '手机号'
	private String applyAddr; // varchar(128) DEFAULT NULL COMMENT '地址'
	private String longitude; //varchar(50) DEFAULT NULL COMMENT '经度'
	private String latitude; // varchar(50) DEFAULT NULL COMMENT '纬度'
	private String area; // varchar(50) DEFAULT NULL COMMENT '面积'
	private String doorheadUrl; // varchar(128) DEFAULT NULL COMMENT '门头url'2
	private String innerUrl00; // varchar(128) DEFAULT NULL COMMENT '店内url00'2
	private String innerUrl01; // varchar(128) DEFAULT NULL COMMENT '店内url01'2
	private String businessLicenceUrl; // varchar(128) DEFAULT NULL COMMENT '营业执照地址'
	private String businessWeeklyStart; // varchar(50) DEFAULT NULL COMMENT '营业日起始'
	private String businessWeeklyEnd; // varchar(50) DEFAULT NULL COMMENT '营业日结束'
	private String businessTimeEnd; // time DEFAULT NULL COMMENT '营业结束时间'
	private String businessTimeStart; // time DEFAULT NULL COMMENT '营业开始时间'
	private String applyUserId; // varchar(50) DEFAULT NULL COMMENT '体验店id'
	private Timestamp createTime; // datetime DEFAULT NULL COMMENT '创建时间'
	private String createBy; // varchar(50) DEFAULT NULL COMMENT '创建者'
	private String updateTime; // timestamp NOT NULL DEFAULT CURRENTTIMESTAMP ON UPDATE CURRENTTIMESTAMP COMMENT '更新时间'
	private String updateBy; // varchar(50) DEFAULT NULL COMMENT '更新者'
	private Integer entkbn; // int(1) DEFAULT '0' COMMENT '数据有效区分(0:数据有效1:数据无效9:逻辑删除)'
	private Integer version; // int(11) NOT NULL DEFAULT '0' COMMENT '版本号'
	private String applyCurrentBusiness; //varchar(150) DEFAULT NULL COMMENT '申请店主营业务'
	private String devWays;//01:后台添加 02:线下扫码添加【2018/08/15 ADD】03:直接申请
	private String cityCode;//
	private String fReason;
	//以下数据用于列表显示
	private String createTimeStart;
	private String createTimeEnd;
	private BigDecimal areastart;
	private BigDecimal areaend;
	private Integer start;
	private Integer offset;
	private String upperCity;
	private String nickName;
	private String pcaname;
	
	// 体验店详情下的商品
	private List<GoodsModels> goodsList;
	private List<Coupon> coupons;
	
	
	public List<Coupon> getCoupons() {
		return coupons;
	}
	public void setCoupons(List<Coupon> coupons) {
		this.coupons = coupons;
	}
	public List<GoodsModels> getGoodsList() {
		return goodsList;
	}
	public void setGoodsList(List<GoodsModels> goodsList) {
		this.goodsList = goodsList;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	public String getApplyUserId() {
		return applyUserId;
	}
	public void setApplyUserId(String applyUserId) {
		this.applyUserId = applyUserId;
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
	public String getApplyCurrentBusiness() {
		return applyCurrentBusiness;
	}
	public void setApplyCurrentBusiness(String applyCurrentBusiness) {
		this.applyCurrentBusiness = applyCurrentBusiness;
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
	public BigDecimal getAreastart() {
		return areastart;
	}
	public void setAreastart(BigDecimal areastart) {
		this.areastart = areastart;
	}
	public BigDecimal getAreaend() {
		return areaend;
	}
	public void setAreaend(BigDecimal areaend) {
		this.areaend = areaend;
	}
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
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
	public String getDevWays() {
		return devWays;
	}
	public void setDevWays(String devWays) {
		this.devWays = devWays;
	}
	public String getUpperCity() {
		return upperCity;
	}
	public void setUpperCity(String upperCity) {
		this.upperCity = upperCity;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getPcaname() {
		return pcaname;
	}
	public void setPcaname(String pcaname) {
		this.pcaname = pcaname;
	}
	@Override
	public String toString() {
		return "ExperiencestoreApply [id=" + id + ", status=" + status + ", applyName=" + applyName + ", applyPhoneNum="
				+ applyPhoneNum + ", applyAddr=" + applyAddr + ", longitude=" + longitude + ", latitude=" + latitude
				+ ", area=" + area + ", doorheadUrl=" + doorheadUrl + ", innerUrl00=" + innerUrl00 + ", innerUrl01="
				+ innerUrl01 + ", businessLicenceUrl=" + businessLicenceUrl + ", businessWeeklyStart="
				+ businessWeeklyStart + ", businessWeeklyEnd=" + businessWeeklyEnd + ", businessTimeEnd="
				+ businessTimeEnd + ", businessTimeStart=" + businessTimeStart + ", applyUserId=" + applyUserId
				+ ", createTime=" + createTime + ", createBy=" + createBy + ", updateTime=" + updateTime + ", updateBy="
				+ updateBy + ", entkbn=" + entkbn + ", version=" + version + ", applyCurrentBusiness="
				+ applyCurrentBusiness + ", devWays=" + devWays + ", cityCode=" + cityCode + ", fReason=" + fReason
				+ ", createTimeStart=" + createTimeStart + ", createTimeEnd=" + createTimeEnd + ", areastart="
				+ areastart + ", areaend=" + areaend + ", start=" + start + ", offset=" + offset + ", upperCity="
				+ upperCity + ", nickName=" + nickName + ", pcaname=" + pcaname + ", goodsList=" + goodsList
				+ ", coupons=" + coupons + "]";
	}
	
}
