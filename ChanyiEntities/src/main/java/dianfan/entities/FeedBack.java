/**  
* @Title: FeedBack.java
* @Package dianfan.entities
* @Description: TODO
* @author yl
* @date 2018年6月28日 下午6:28:01
* @version V1.0  
*/
package dianfan.entities;

import java.sql.Timestamp;

/**
 * @ClassName FeedBack
 * @Description
 * @author yl
 * @date 2018年6月28日 下午6:28:01
 */
public class FeedBack {

	private String id;// varchar(50) NOT NULL COMMENT '主键id'
	private String userId;// varchar(50) DEFAULT NULL COMMENT '可以为空'
	private String nickName;// 反馈人的昵称
	private String picUrl;// text COMMENT '图片地址'
	private String telno;//  varchar(2) DEFAULT NULL COMMENT '电话号码'
	private String feedbackType;// varchar(2) DEFAULT NULL COMMENT ''反馈状态\r\n  01：待解决\r\n  02：已解决\r\n',
	private String content;// varchar(512) DEFAULT NULL COMMENT '反馈内容'
	private Timestamp createTime; // datetime DEFAULT NULL COMMENT '创建时间'
	private String createBy; // varchar(50) DEFAULT NULL COMMENT '创建者'
	private Timestamp updateTime; // timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;COMMENT '更新时间'
	private String updateBy; // varchar(50) DEFAULT NULL COMMENT '更新者'
	private Integer entkbn; // int(1) DEFAULT '0' COMMENT '数据有效区分(0:数据有效1:数据无效9:逻辑删除)'
	private Integer version; // int(11) NOT NULL DEFAULT '0' COMMENT '版本号'
	
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	public String getTelno() {
		return telno;
	}
	public void setTelno(String telno) {
		this.telno = telno;
	}
	public String getFeedbackType() {
		return feedbackType;
	}
	public void setFeedbackType(String feedbackType) {
		this.feedbackType = feedbackType;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
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
	@Override
	public String toString() {
		return "FeedBack [id=" + id + ", userId=" + userId + ", nickName=" + nickName + ", picUrl=" + picUrl
				+ ", telno=" + telno + ", feedbackType=" + feedbackType + ", content=" + content + ", createTime="
				+ createTime + ", createBy=" + createBy + ", updateTime=" + updateTime + ", updateBy=" + updateBy
				+ ", entkbn=" + entkbn + ", version=" + version + "]";
	}


}
