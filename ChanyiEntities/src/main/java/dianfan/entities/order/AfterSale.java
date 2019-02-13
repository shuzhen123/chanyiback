/**  
* @Title: AfterSale.java
* @Package dianfan.entities.order
* @Description: TODO
* @author yl
* @date 2018年8月3日 下午2:00:13
* @version V1.0  
*/ 
package dianfan.entities.order;

import java.sql.Timestamp;

/** @ClassName AfterSale
 * @Description 
 * @author yl
 * @date 2018年8月3日 下午2:00:13
 */
public class AfterSale {
	
	private String id;// varchar(50) NOT NULL COMMENT '主键id'
	private String orderId;// varchar(50) DEFAULT NULL COMMENT '订单号'
	private String userId;// varchar(50) DEFAULT NULL COMMENT '处理人员id'
	private String reason;// varchar(250) DEFAULT NULL COMMENT '换货、退货退款原因'
	private String picUrls;// text COMMENT '换货、退货退款凭证'
	private String remark;// varchar(250) DEFAULT NULL COMMENT '换货、退货退款理由'
	private String handleStatus;// varchar(2) DEFAULT NULL COMMENT '处理状态（01：换货02：退货退款）'
	private String result;// varchar(2) DEFAULT '01' COMMENT '结果（01:待审核02：拒绝03：通过04：通知京东上门取货（填收货地址）待取货 07：待收货 08：待签收 09：验收不成功   10： 退货验收成功并退款11 ：退货验收不成功 重新发货 12： 换货验收成功重新发货 13：换货验收不成功 重新发货 ）'
	private String resultFReason;// varchar(250) DEFAULT NULL COMMENT  '拒绝理由'
	private String deliveryId;// varchar(50) DEFAULT NULL COMMENT '退货物流id'
	private Timestamp createTime;// datetime DEFAULT NULL COMMENT '创建时间
	private String createBy;// varchar(50) DEFAULT NULL COMMENT '创建者' 
	private Timestamp updateTime;// datetime DEFAULT CURRENTTIMESTAMP ON UPDATE CURRENTTIMESTAMP COMMENT '更新时间'
	private String updateBy;// varchar(50) DEFAULT NULL COMMENT '更新者'
	private Integer entkbn;// int(1) DEFAULT '0' COMMENT '数据有效区分(0:数据有效1:数据无效9:逻辑删除)'
	private Integer version;// int(11) NOT NULL DEFAULT '0' COMMENT '版本号'
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getPicUrls() {
		return picUrls;
	}
	public void setPicUrls(String picUrls) {
		this.picUrls = picUrls;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getHandleStatus() {
		return handleStatus;
	}
	public void setHandleStatus(String handleStatus) {
		this.handleStatus = handleStatus;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getResultFReason() {
		return resultFReason;
	}
	public void setResultFReason(String resultFReason) {
		this.resultFReason = resultFReason;
	}
	public String getDeliveryId() {
		return deliveryId;
	}
	public void setDeliveryId(String deliveryId) {
		this.deliveryId = deliveryId;
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
		return "AfterSale [id=" + id + ", orderId=" + orderId + ", userId=" + userId + ", reason=" + reason
				+ ", picUrls=" + picUrls + ", remark=" + remark + ", handleStatus=" + handleStatus + ", result="
				+ result + ", resultFReason=" + resultFReason + ", deliveryId=" + deliveryId + ", createTime="
				+ createTime + ", createBy=" + createBy + ", updateTime=" + updateTime + ", updateBy=" + updateBy
				+ ", entkbn=" + entkbn + ", version=" + version + "]";
	}

}
