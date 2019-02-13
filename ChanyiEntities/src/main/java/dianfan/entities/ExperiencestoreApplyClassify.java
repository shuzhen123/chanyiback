/**  
* @Title: ExperiencestoreApplyClassify.java
* @Package dianfan.entities
* @Description: TODO
* @author yl
* @date 2018年6月28日 下午8:25:10
* @version V1.0  
*/ 
package dianfan.entities;

import java.util.Arrays;

/** @ClassName ExperiencestoreApplyClassify
 * @Description 
 * @author yl
 * @date 2018年6月28日 下午8:25:10
 */
public class ExperiencestoreApplyClassify {
	
	private String id;// varchar(50) NOT NULL COMMENT '主键id'
	private String goodsClassifyId;// varchar(50) DEFAULT NULL COMMENT '分类表id'
	private String experiencestoreApplyId;// varchar(50) DEFAULT NULL COMMENT '体验店申请id'
	private String createTime; // datetime DEFAULT NULL COMMENT '创建时间'
	private String createBy; // varchar(50) DEFAULT NULL COMMENT '创建者'
	private String updateTime; // timestamp NOT NULL DEFAULT CURRENTTIMESTAMP ON UPDATE CURRENTTIMESTAMP COMMENT '更新时间'
	private String updateBy; // varchar(50) DEFAULT NULL COMMENT '更新者'
	private String entkbn; // int(1) DEFAULT '0' COMMENT '数据有效区分(0:数据有效1:数据无效9:逻辑删除)'
	private String version; // int(11) NOT NULL DEFAULT '0' COMMENT '版本号'
	private String[] gcid;//分类id列表
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getGoodsClassifyId() {
		return goodsClassifyId;
	}
	public void setGoodsClassifyId(String goodsClassifyId) {
		this.goodsClassifyId = goodsClassifyId;
	}
	public String getExperiencestoreApplyId() {
		return experiencestoreApplyId;
	}
	public void setExperiencestoreApplyId(String experiencestoreApplyId) {
		this.experiencestoreApplyId = experiencestoreApplyId;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
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
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String[] getGcid() {
		return gcid;
	}
	public void setGcid(String[] gcid) {
		this.gcid = gcid;
	}
	@Override
	public String toString() {
		return "ExperiencestoreApplyClassify [id=" + id + ", goodsClassifyId=" + goodsClassifyId
				+ ", experiencestoreApplyId=" + experiencestoreApplyId + ", createTime=" + createTime + ", createBy="
				+ createBy + ", updateTime=" + updateTime + ", updateBy=" + updateBy + ", entkbn=" + entkbn
				+ ", version=" + version + ", gcid=" + Arrays.toString(gcid) + "]";
	}

}
