/**  
* @Title: ConsumerApplyModel.java
* @Package dianfan.entities.our
* @Description: TODO
* @author yl
* @date 2018年6月30日 下午1:13:54
* @version V1.0  
*/ 
package dianfan.entities.our;

import java.sql.Timestamp;

/** @ClassName ConsumerApplyModel
 * @Description 消费商model
 * @author yl
 * @date 2018年6月30日 下午1:13:54
 */
public class ConsumerApplyModel {
	
	private String id;// varchar(50) NOT NULL COMMENT '主键id'
	private String applyUserId;// varchar(50) DEFAULT NULL COMMENT '申请人id'
	private String status;// varchar(2) DEFAULT NULL COMMENT '申请状态 01通过 02-未通过'
	private String fReason;// varchar(150) DEFAULT NULL COMMENT '失败原因'
	private Timestamp createTime;// datetime DEFAULT NULL COMMENT '创建时间'
	private Integer entkbn;// int(1) DEFAULT '0' COMMENT '数据有效区分(0:数据有效1:数据无效9:逻辑删除)'
	private String createBy;//创建者
	private String updateBy;//更新者
	private Integer version;//版本号
	//以下数据仅用于列表显示
	private String nickName;//用户昵称
	private String telno;
	private String avatarUrl;
	private String createTimeStart;// datetime DEFAULT NULL COMMENT '创建时间'
	private String createTimeEnd;// datetime DEFAULT NULL COMMENT '创建时间'
	private Integer start;
	private Integer offset;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getApplyUserId() {
		return applyUserId;
	}
	public void setApplyUserId(String applyUserId) {
		this.applyUserId = applyUserId;
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
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public Integer getEntkbn() {
		return entkbn;
	}
	public void setEntkbn(Integer entkbn) {
		this.entkbn = entkbn;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getTelno() {
		return telno;
	}
	public void setTelno(String telno) {
		this.telno = telno;
	}
	public String getAvatarUrl() {
		return avatarUrl;
	}
	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
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
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}

}
