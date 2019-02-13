package dianfan.entities.banner;

import java.util.Date;

/** @ClassName Banner
 * @Description 轮播图
 * @author zwb
 * @date 2018年6月28日 下午1:53:45
 */ 
public class Banner {
	private String id;//主键id
	
	private String title;//标题
	
	private String content;//内容
	
	private String advertorialId;//软文id
	
	private String picAddr;//图片地址
	
	private String adDesc;//描述
	
	private String sort;//排序
	
	private Date createTime;//创建时间
	
	private String createBy;//创建者
	
	private Date updateTime;//更新时间
	
	private String updateBy;//更新者
	
	private Integer entkbn;//数据有效区分
	
	private Integer version;//版本

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAdvertorialId() {
		return advertorialId;
	}

	public void setAdvertorialId(String advertorialId) {
		this.advertorialId = advertorialId;
	}

	public String getPicAddr() {
		return picAddr;
	}

	public void setPicAddr(String picAddr) {
		this.picAddr = picAddr;
	}

	public String getAdDesc() {
		return adDesc;
	}

	public void setAdDesc(String adDesc) {
		this.adDesc = adDesc;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
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
		return "Banner [id=" + id + ", title=" + title + ", content=" + content + ", advertorialId=" + advertorialId
				+ ", picAddr=" + picAddr + ", adDesc=" + adDesc + ", sort=" + sort + ", createTime=" + createTime
				+ ", createBy=" + createBy + ", updateTime=" + updateTime + ", updateBy=" + updateBy + ", entkbn="
				+ entkbn + ", version=" + version + "]";
	}

	
	
	

}
