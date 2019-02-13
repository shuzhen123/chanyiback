/**  
* @Title: SupplierApply.java
* @Package dianfan.entities.our
* @Description: TODO
* @author yl
* @date 2018年6月29日 下午5:27:00
* @version V1.0  
*/ 
package dianfan.entities.our;

import java.sql.Timestamp;

/** @ClassName SupplierApply
 * @Description 
 * @author yl
 * @date 2018年6月29日 下午5:27:00
 */
public class SupplierApply {
	
	private String id;// varchar(50) NOT NULL COMMENT '主键id（UUID）'
	private String companyName;//  varchar(50) DEFAULT NULL COMMENT '公司名称'
	private String registeredCapitalMoney;//  varchar(50) DEFAULT NULL COMMENT '注册资金'
	private String supplyCategory;//  varchar(128) DEFAULT NULL COMMENT '供应品类\r\n            private String 酒店床垫、酒店床架、民用床垫、婴儿床品、枕头'private String 
	private String cooperationCase;//  varchar(255) DEFAULT Nprivate String ULL COMMENT '合作案例'private String 
	private String contacts;//  varchar(20) DEFAULT NULL COMMENT '联系人'private String 
	private String contactsPhone;//  varchar(20) DEFAULT NULL COMMENT '联系电话'
	private String eMail;//  varchar(50) DEFAULT NULL COMMENT '电子邮箱'
	private String userId;// varchar(50) DEFAULT NULL COMMENT '用户分配的id'
	private String status;// 申请状态 01通过 02-未通过03待审核
	private Timestamp createTime;// datetime DEFAULT NULL COMMENT '创建时间'
	private String createBy;// varchar(50) DEFAULT NULL COMMENT '创建者'
	private Timestamp updateTime;// timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE private String CURRENT_TIMESTAMP COMMENT '更新时间'private String 
	private String updateBy;// varchar(50) DEFAULT NULL COMMENT '更新者'private String 
	private Integer entkbn;// int(1) DEFAULT '0' COMMENT '数据有效区分(0:数据有效1:数据无效9:逻辑删除)'
	private Integer version;// int(11) NOT NULL DEFAULT '0' COMMENT '版本号'
	private String fReason;
	//以下为后台列表显示数据
	private String createTimeStart;
	private String createTimeEnd;
	private Integer start;
	private Integer offset;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getRegisteredCapitalMoney() {
		return registeredCapitalMoney;
	}

	public void setRegisteredCapitalMoney(String registeredCapitalMoney) {
		this.registeredCapitalMoney = registeredCapitalMoney;
	}

	public String getSupplyCategory() {
		return supplyCategory;
	}

	public void setSupplyCategory(String supplyCategory) {
		this.supplyCategory = supplyCategory;
	}

	public String getCooperationCase() {
		return cooperationCase;
	}

	public void setCooperationCase(String cooperationCase) {
		this.cooperationCase = cooperationCase;
	}

	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

	public String getContactsPhone() {
		return contactsPhone;
	}

	public void setContactsPhone(String contactsPhone) {
		this.contactsPhone = contactsPhone;
	}

	public String geteMail() {
		return eMail;
	}

	public void seteMail(String eMail) {
		this.eMail = eMail;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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
		return "SupplierApply [id=" + id + ", companyName=" + companyName + ", registeredCapitalMoney="
				+ registeredCapitalMoney + ", supplyCategory=" + supplyCategory + ", cooperationCase=" + cooperationCase
				+ ", contacts=" + contacts + ", contactsPhone=" + contactsPhone + ", eMail=" + eMail + ", userId="
				+ userId + ", status=" + status + ", createTime=" + createTime + ", createBy=" + createBy
				+ ", updateTime=" + updateTime + ", updateBy=" + updateBy + ", entkbn=" + entkbn + ", version="
				+ version + "]";
	}
}
