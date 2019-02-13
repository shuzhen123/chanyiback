/**  
* @Title: UserCouponRelate.java
* @Package dianfan.entities
* @Description: TODO
* @author yl
* @date 2018年6月28日 下午3:09:48
* @version V1.0  
*/
package dianfan.entities;

import java.sql.Timestamp;

/**
 * @ClassName UserCouponRelate
 * @Description
 * @author yl
 * @date 2018年6月28日 下午3:09:48
 */
public class UserCouponRelate {

	private String id; // varchar(50) NOT NULL COMMENT '主键id'
	private String couponId; // varchar(50) NOT NULL COMMENT '优惠券id'
	private String userId; // varchar(50) NOT NULL COMMENT '用户id'
	private Integer userUsed; // tinyint(1) DEFAULT '0' COMMENT '用户是否使用(默认未被使用)'
	private Timestamp drawDate; // datetime DEFAULT NULL COMMENT '领取日期'
	private Timestamp usedDate; // datetime DEFAULT NULL COMMENT '使用日期'
	private Timestamp usedEndTime; // datetime DEFAULT NULL COMMENT '截止日期'
	private Timestamp createTime; // datetime DEFAULT NULL COMMENT '创建时间'
	private String createBy; // varchar(50) DEFAULT NULL COMMENT '创建者'
	private Timestamp updateTime; // timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
	private String updateBy; // varchar(50) DEFAULT NULL COMMENT '更新者'
	private Integer entkbn; // int(1) DEFAULT '0' COMMENT '数据有效区分(0:数据有效1:数据无效9:逻辑删除)'
	private Integer version; // int(11) NOT NULL DEFAULT '0' COMMENT '版本号'
	private String[] userids;
	private String goodsid;
	private String storeid;
	
	public String getGoodsid() {
		return goodsid;
	}
	public void setGoodsid(String goodsid) {
		this.goodsid = goodsid;
	}
	public String getStoreid() {
		return storeid;
	}
	public void setStoreid(String storeid) {
		this.storeid = storeid;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCouponId() {
		return couponId;
	}
	public void setCouponId(String couponId) {
		this.couponId = couponId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Integer getUserUsed() {
		return userUsed;
	}
	public void setUserUsed(Integer userUsed) {
		this.userUsed = userUsed;
	}
	public Timestamp getDrawDate() {
		return drawDate;
	}
	public void setDrawDate(Timestamp drawDate) {
		this.drawDate = drawDate;
	}
	public Timestamp getUsedDate() {
		return usedDate;
	}
	public void setUsedDate(Timestamp usedDate) {
		this.usedDate = usedDate;
	}
	public Timestamp getUsedEndTime() {
		return usedEndTime;
	}
	public void setUsedEndTime(Timestamp usedEndTime) {
		this.usedEndTime = usedEndTime;
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
	public String[] getUserids() {
		return userids;
	}
	public void setUserids(String[] userids) {
		this.userids = userids;
	}
	@Override
	public String toString() {
		return "UserCouponRelate [id=" + id + ", couponId=" + couponId + ", userId=" + userId + ", userUsed=" + userUsed
				+ ", drawDate=" + drawDate + ", usedDate=" + usedDate + ", usedEndTime=" + usedEndTime + ", createTime="
				+ createTime + ", createBy=" + createBy + ", updateTime=" + updateTime + ", updateBy=" + updateBy
				+ ", entkbn=" + entkbn + ", version=" + version + "]";
	}
}
