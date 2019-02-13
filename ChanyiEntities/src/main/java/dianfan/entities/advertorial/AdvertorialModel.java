package dianfan.entities.advertorial;

import java.util.Date;

/** @ClassName AdvertorialModel
 * @Description 文章Model类
 * @author zwb
 * @date 2018年7月3日 下午1:46:11
 */ 
public class AdvertorialModel {
	//主键id
	private String id;
	//分类id
	private String classifyId;
	//标题
	private String title;
	//内容
	private String content;
	//浏览量
	private Integer visitCounts;
	//图片地址
	private String picAddr;
	//点赞总量
	private Integer thumbUpTotal;
	//创建时间
	private Date createTime;
	//创建者
	private String createBy;
	
	private Integer praise;
	
	
	public Integer getPraise() {
		return praise;
	}
	public void setPraise(Integer praise) {
		this.praise = praise;
	}
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
	public Integer getThumbUpTotal() {
		return thumbUpTotal;
	}
	public void setThumbUpTotal(Integer thumbUpTotal) {
		this.thumbUpTotal = thumbUpTotal;
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
	@Override
	public String toString() {
		return "AdvertorialModel [id=" + id + ", classifyId=" + classifyId + ", title=" + title + ", content=" + content
				+ ", visitCounts=" + visitCounts + ", picAddr=" + picAddr + ", thumbUpTotal=" + thumbUpTotal
				+ ", createTime=" + createTime + ", createBy=" + createBy + "]";
	}
	
	
	

}
