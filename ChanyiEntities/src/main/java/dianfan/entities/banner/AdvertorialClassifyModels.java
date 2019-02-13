package dianfan.entities.banner;

import java.sql.Timestamp;

/**
 * @ClassName AdvertorialClassify
 * @Description 文章类别表
 * @author sz
 * @date 2018年7月20日 上午10:03:16
 */
public class AdvertorialClassifyModels {
	
	private String classifyId; //varchar(50) NOT NULL COMMENT '类型id\r\n 此处无需刻意按规则对分类进行编号',
	private String classifyName; //varchar(30) DEFAULT NULL COMMENT '类型名字\r\n分类名称',
	private String classifyParentId; //varchar(9) DEFAULT NULL COMMENT '上级菜单id\r\n 如是二级菜单需要填写',
	private Integer classifyNavSort; //int(2) DEFAULT NULL COMMENT '导航栏类目排序',
	private String picAddr; //varchar(128) DEFAULT NULL COMMENT '图片地址(URL)',
	private Timestamp createTime; //datetime DEFAULT NULL COMMENT '创建时间',
	private String createBy; //varchar(50) DEFAULT NULL COMMENT '创建者id',
	private Timestamp updateTime; //timestamp NOT NULL DEFAULT CURRENT_TIMESTAMCOMMENT '更新时间',
	private String updateBy; //varchar(50) DEFAULT NULL COMMENT '更新者',
	private int entkbn; //int(1) DEFAULT '0' COMMENT '数据有效区分(0:数据有效1:数据无效9:逻辑删除)',
	private Integer version; //int(11) NOT NULL DEFAULT '0' COMMENT '版本号',
	
	private String account; // '更新者的名字',
	
	
	
	
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getClassifyId() {
		return classifyId;
	}
	public void setClassifyId(String classifyId) {
		this.classifyId = classifyId;
	}
	public String getClassifyName() {
		return classifyName;
	}
	public void setClassifyName(String classifyName) {
		this.classifyName = classifyName;
	}
	public String getClassifyParentId() {
		return classifyParentId;
	}
	public void setClassifyParentId(String classifyParentId) {
		this.classifyParentId = classifyParentId;
	}
	public Integer getClassifyNavSort() {
		return classifyNavSort;
	}
	public void setClassifyNavSort(Integer classifyNavSort) {
		this.classifyNavSort = classifyNavSort;
	}
	public String getPicAddr() {
		return picAddr;
	}
	public void setPicAddr(String picAddr) {
		this.picAddr = picAddr;
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
	public int getEntkbn() {
		return entkbn;
	}
	public void setEntkbn(int entkbn) {
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
		return "AdvertorialClassifyModels [classifyId=" + classifyId + ", classifyName=" + classifyName
				+ ", classifyParentId=" + classifyParentId + ", classifyNavSort=" + classifyNavSort + ", picAddr="
				+ picAddr + ", createTime=" + createTime + ", createBy=" + createBy + ", updateTime=" + updateTime
				+ ", updateBy=" + updateBy + ", entkbn=" + entkbn + ", version=" + version + ", account=" + account
				+ "]";
	}
	
	

	
	
}
