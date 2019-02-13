package dianfan.entities.advertorialclassify;

import java.util.Date;

/** @ClassName AdvertorialClassify
 * @Description 文章类别实体类
 * @author zwb
 * @date 2018年6月28日 下午3:51:19
 */ 
public class AdvertorialClassify {
	
	private String classifyId;//类型id
	
	private String classifyName;//类型名字
	
	private String classifyParentId;//上级菜单id
	
	private String classifyNavSort;//导航栏类目排序
	
	private String picAddr;//图片地址URL
	
	private Date createTime;//创建时间
	
	private String createBy;//创建者
	
	private Date updateTime;//更新时间
	
	private String updateBy;//更新者
	
	private Integer entkbn;//数据有效区分
	
	private Integer version;//版本

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

	public String getClassifyNavSort() {
		return classifyNavSort;
	}

	public void setClassifyNavSort(String classifyNavSort) {
		this.classifyNavSort = classifyNavSort;
	}

	public String getPicAddr() {
		return picAddr;
	}

	public void setPicAddr(String picAddr) {
		this.picAddr = picAddr;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
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
		return "AdvertorialClassify [classifyId=" + classifyId + ", classifyName=" + classifyName
				+ ", classifyParentId=" + classifyParentId + ", classifyNavSort=" + classifyNavSort + ", picAddr="
				+ picAddr + ", createTime=" + createTime + ", createBy=" + createBy + ", updateTime=" + updateTime
				+ ", updateBy=" + updateBy + ", entkbn=" + entkbn + ", version=" + version + "]";
	}
	
	
	
	

}
