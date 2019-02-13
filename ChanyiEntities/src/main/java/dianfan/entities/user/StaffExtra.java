package dianfan.entities.user;

import java.sql.Timestamp;

/**
 * @ClassName StaffExtra
 * @Description 用户资料补充
 * @author cjy
 * @date 2018年7月25日 下午3:03:58
 */
public class StaffExtra {
	private String id; //varchar(50) NOT NULL COMMENT '主键id',
	private String realName; //varchar(50) DEFAULT NULL COMMENT '姓名',
	private String companyName; //varchar(150) DEFAULT NULL COMMENT '公司名称',
	private String areaCode; //varchar(8) DEFAULT NULL COMMENT '地址code',
	private String handleIdcard; //varchar(150) DEFAULT NULL COMMENT '手持身份证照片',
	private String idcardNo; //varchar(20) DEFAULT NULL COMMENT '身份证号码',
	private String idcardFont; //varchar(150) DEFAULT NULL COMMENT '身份证正面',
	private String idcardBack; //varchar(150) DEFAULT NULL COMMENT '身份证反面',
	private String idcardValidDate; //varchar(50) DEFAULT NULL COMMENT '身份证有效期',
	private String contractUrl; //varchar(450) DEFAULT NULL COMMENT '合同地址',
	private Timestamp createTime; //datetime DEFAULT NULL COMMENT '创建时间',
	private String createBy; //varchar(50) DEFAULT NULL COMMENT '创建者id',
	private Timestamp updateTime; //timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
	private String updateBy; //varchar(50) DEFAULT NULL COMMENT '更新者id',
	private Integer entkbn; //int(1) DEFAULT '0' COMMENT '数据有效区分(0:数据有效1:数据无效9:逻辑删除)',
	private Integer version; //int(11) NOT NULL DEFAULT '0' COMMENT '版本号',
	private String businessLicense; //varchar(150) DEFAULT NULL COMMENT '营业执照',
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
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
	public String getContractUrl() {
		return contractUrl;
	}
	public void setContractUrl(String contractUrl) {
		this.contractUrl = contractUrl;
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
	public String getBusinessLicense() {
		return businessLicense;
	}
	public void setBusinessLicense(String businessLicense) {
		this.businessLicense = businessLicense;
	}
	@Override
	public String toString() {
		return "StaffExtra [id=" + id + ", realName=" + realName + ", companyName=" + companyName + ", areaCode="
				+ areaCode + ", handleIdcard=" + handleIdcard + ", idcardNo=" + idcardNo + ", idcardFont=" + idcardFont
				+ ", idcardBack=" + idcardBack + ", idcardValidDate=" + idcardValidDate + ", contractUrl=" + contractUrl
				+ ", createTime=" + createTime + ", createBy=" + createBy + ", updateTime=" + updateTime + ", updateBy="
				+ updateBy + ", entkbn=" + entkbn + ", version=" + version + ", businessLicense=" + businessLicense
				+ "]";
	}
}
