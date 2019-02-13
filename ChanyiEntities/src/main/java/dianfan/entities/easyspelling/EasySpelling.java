package dianfan.entities.easyspelling;

import java.util.Date;

/** @ClassName EasySpelling
 * @Description 易拼表实体类
 * @author zwb
 * @date 2018年7月3日 下午7:56:25
 */ 
public class EasySpelling {

//	`id` varchar(50) NOT NULL COMMENT '主键id',
//	  `user_id` varchar(50) NOT NULL COMMENT '用户id',
//	  `easy_spelling_parameter_id` varchar(50) NOT NULL COMMENT '易拼参数表id',
//	  `start_time` datetime DEFAULT NULL COMMENT '发起拼团开始时间',
//	  `end_time` datetime DEFAULT NULL COMMENT '发起拼团结束时间',
//	  `create_by` varchar(50) DEFAULT NULL COMMENT '创建者',
//	  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
//	  `update_by` varchar(50) DEFAULT NULL COMMENT '更新者',
//	  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
//	  `entkbn` int(1) DEFAULT '0' COMMENT '数据有效区分(0:数据有效1:数据无效9:逻辑删除)',
//	  `version` int(11) NOT NULL DEFAULT '0' COMMENT '版本号',
	
	//主键id
	private String id;
	
	//用户id
	private String userId;
	
	//易拼参数表id
	private String easySpellingParameterId;
	
	//发起拼团开始时间
	private Date startTime;
	
	//发起拼团结束时间
	private Date endTime;
	
	//创建者
	private String createBy;
	
	//创建时间
	private Date createTime;
	
	//更新者
	private String updateBy;
	
	//更新时间
	private Date updateTime;
	
	//数据有效区分(0:数据有效1:数据无效9:逻辑删除)
	private Integer entkbn;
	
	//版本号
	private Integer version;

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

	public String getEasySpellingParameterId() {
		return easySpellingParameterId;
	}

	public void setEasySpellingParameterId(String easySpellingParameterId) {
		this.easySpellingParameterId = easySpellingParameterId;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
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
		return "EasySpelling [id=" + id + ", userId=" + userId + ", easySpellingParameterId=" + easySpellingParameterId
				+ ", startTime=" + startTime + ", endTime=" + endTime + ", createBy=" + createBy + ", createTime="
				+ createTime + ", updateBy=" + updateBy + ", updateTime=" + updateTime + ", entkbn=" + entkbn
				+ ", version=" + version + "]";
	}

	
	
	
	

}
