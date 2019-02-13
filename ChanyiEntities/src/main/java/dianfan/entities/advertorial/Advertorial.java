package dianfan.entities.advertorial;

import java.util.Date;

/** @ClassName Advertorial
 * @Description 文章实体类
 * @author zwb
 * @date 2018年6月28日 下午5:58:38
 */ 
public class Advertorial {
	
	private String id;//主键id
	
	private String classifyId;//类型id
	
	private String classifyName;//类型名称
	
	private String title;//标题
	
	private String content;//内容
	
	private Integer visitCounts;//浏览量
	
	private String picAddr;//图片地址URL
	
	private Integer thumbUpNum;//点赞数量(真)
	
	private Integer thumbUpNumN;//点赞数量(假)
	
	private Integer recommend;//推荐（true）默认false
	
	private Integer sort;//排序
	
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

	public String getClassifyId() {
		return classifyId;
	}

	public void setClassifyId(String classifyId) {
		this.classifyId = classifyId;
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

	public Integer getVisitCounts() {
		return visitCounts;
	}

	public void setVisitCounts(Integer visitCounts) {
		this.visitCounts = visitCounts;
	}

	public String getPicAddr() {
		return picAddr;
	}

	public void setPicAddr(String picAddr) {
		this.picAddr = picAddr;
	}

	public Integer getThumbUpNum() {
		return thumbUpNum;
	}

	public void setThumbUpNum(Integer thumbUpNum) {
		this.thumbUpNum = thumbUpNum;
	}

	public Integer getThumbUpNumN() {
		return thumbUpNumN;
	}

	public void setThumbUpNumN(Integer thumbUpNumN) {
		this.thumbUpNumN = thumbUpNumN;
	}

	public Integer getRecommend() {
		return recommend;
	}

	public void setRecommend(Integer recommend) {
		this.recommend = recommend;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
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

	public String getClassifyName() {
		return classifyName;
	}

	public void setClassifyName(String classifyName) {
		this.classifyName = classifyName;
	}

	@Override
	public String toString() {
		return "Advertorial [id=" + id + ", classifyId=" + classifyId + ", classifyName=" + classifyName + ", title="
				+ title + ", content=" + content + ", visitCounts=" + visitCounts + ", picAddr=" + picAddr
				+ ", thumbUpNum=" + thumbUpNum + ", thumbUpNumN=" + thumbUpNumN + ", recommend=" + recommend + ", sort="
				+ sort + ", createTime=" + createTime + ", createBy=" + createBy + ", updateTime=" + updateTime
				+ ", updateBy=" + updateBy + ", entkbn=" + entkbn + ", version=" + version + "]";
	}

	

	
	

	
	
	

}
