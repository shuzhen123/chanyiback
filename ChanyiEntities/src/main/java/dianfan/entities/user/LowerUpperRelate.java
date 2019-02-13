/**  
* @Title: LowerUpperRelate.java
* @Package dianfan.entities.user
* @Description: TODO
* @author yl
* @date 2018年7月28日 下午6:38:12
* @version V1.0  
*/ 
package dianfan.entities.user;

import java.sql.Timestamp;

/** @ClassName LowerUpperRelate
 * @Description 
 * @author yl
 * @date 2018年7月28日 下午6:38:12
 */
public class LowerUpperRelate {
	
	private String id;// varchar(50) NOT NULL COMMENT '主键'
	private String upperUserId;// varchar(50) DEFAULT NULL COMMENT '上级userid'
	private String downUserId;// varchar(50) NOT NULL COMMENT '下级userid'
	private String distroyRelateResaon;// varchar(150) DEFAULT NULL COMMENT '关系销毁原因、迁移理由'
	private Timestamp createTime;// datetime DEFAULT NULL COMMENT '创建时间'
	private String createBy;// varchar(50) DEFAULT NULL COMMENT '创建者'
	private Timestamp updateTime;// timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
	private String updateBy;// varchar(50) DEFAULT NULL COMMENT '更新者'
	private Integer entkbn;// int(1) DEFAULT '0' COMMENT '数据有效区分(0:数据有效1:数据无效9:逻辑删除)'
	private Integer version;// int(11) NOT NULL DEFAULT '0' COMMENT '版本号'
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUpperUserId() {
		return upperUserId;
	}
	public void setUpperUserId(String upperUserId) {
		this.upperUserId = upperUserId;
	}
	public String getDownUserId() {
		return downUserId;
	}
	public void setDownUserId(String downUserId) {
		this.downUserId = downUserId;
	}
	public String getDistroyRelateResaon() {
		return distroyRelateResaon;
	}
	public void setDistroyRelateResaon(String distroyRelateResaon) {
		this.distroyRelateResaon = distroyRelateResaon;
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
		return "LowerUpperRelate [id=" + id + ", upperUserId=" + upperUserId + ", downUserId=" + downUserId
				+ ", distroyRelateResaon=" + distroyRelateResaon + ", createTime=" + createTime + ", createBy="
				+ createBy + ", updateTime=" + updateTime + ", updateBy=" + updateBy + ", entkbn=" + entkbn
				+ ", version=" + version + "]";
	}

}
