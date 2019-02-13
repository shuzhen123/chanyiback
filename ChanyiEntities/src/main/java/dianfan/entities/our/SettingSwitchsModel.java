/**  
* @Title: SettingSwitchsModel.java
* @Package dianfan.entities.our
* @Description: TODO
* @author yl
* @date 2018年7月2日 上午10:56:13
* @version V1.0  
*/ 
package dianfan.entities.our;

import java.sql.Timestamp;

/** @ClassName SettingSwitchsModel
 * @Description 
 * @author yl
 * @date 2018年7月2日 上午10:56:13
 */
public class SettingSwitchsModel {
	
	private Integer id;// int(8) NOT NULL COMMENT '主键id'
	private Integer consumerApplyFlag;// tinyint(1) DEFAULT '1' COMMENT '消费商审批开关(默认开启)'
	private String consumerDes;// varchar(50) DEFAULT NULL COMMENT '消费商审批开关描述'
	private Timestamp createTime;// datetime DEFAULT NULL COMMENT '创建时间'
	private String createBy;// varchar(50) DEFAULT NULL COMMENT '创建者'
	private Timestamp updateTime;// timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE private String CURRENT_TIMESTAMP COMMENT '更新时间'private String 
	private String updateBy;// varchar(50) DEFAULT NULL COMMENT '更新者'private String 
	private String entkbn;// int(1) DEFAULT '0' COMMENT '数据有效区分(0:数据有效1:数据无效9:逻辑删除)'
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
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
	public String getEntkbn() {
		return entkbn;
	}
	public void setEntkbn(String entkbn) {
		this.entkbn = entkbn;
	}
	@Override
	public String toString() {
		return "SettingSwitchsModel [id=" + id + ", consumerApplyFlag=" + consumerApplyFlag + ", consumerDes="
				+ consumerDes + ", createTime=" + createTime + ", createBy=" + createBy + ", updateTime=" + updateTime
				+ ", updateBy=" + updateBy + ", entkbn=" + entkbn + "]";
	}
	

}
