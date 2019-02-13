package dianfan.entities.user;

import java.sql.Timestamp;

/**
 * @ClassName SettingSwitchs
 * @Description 系统开关
 * @author sz
 * @date 2018年7月24日 上午9:33:38
 */
public class SettingSwitchs {

	private String id; //int(8) NOT NULL COMMENT '主键id',
	private Integer consumerApplyFlag; //tinyint(1) DEFAULT '1' COMMENT '消费商审批开关(默认开启)',
	private String consumerDes; //varchar(50) DEFAULT NULL COMMENT '消费商审批开关描述',
	private Timestamp createTime; //datetime DEFAULT NULL COMMENT '创建时间',
	private String createBy; //varchar(50) DEFAULT NULL COMMENT '创建者',
	private Timestamp updateTime; //timestamp NOT NULL DEF '更新时间',
	private String updateBy; //varchar(50) DEFAULT NULL COMMENT '更新者',
	private int entkbn; //int(1) DEFAULT '0' COMMENT '数据有效区分(0:数据有效1:数据无效9:逻辑删除)',
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Integer getConsumerApplyFlag() {
		return consumerApplyFlag;
	}
	public void setConsumerApplyFlag(Integer consumerApplyFlag) {
		this.consumerApplyFlag = consumerApplyFlag;
	}
	public String getConsumerDes() {
		return consumerDes;
	}
	public void setConsumerDes(String consumerDes) {
		this.consumerDes = consumerDes;
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
	@Override
	public String toString() {
		return "SettingSwitchs [id=" + id + ", consumerApplyFlag=" + consumerApplyFlag + ", consumerDes=" + consumerDes
				+ ", createTime=" + createTime + ", createBy=" + createBy + ", updateTime=" + updateTime + ", updateBy="
				+ updateBy + ", entkbn=" + entkbn + "]";
	}
	
	
	
}
