/**  
* @Title: OrderDeliveryRelate.java
* @Package dianfan.entities.logistics
* @Description: TODO
* @author yl
* @date 2018年7月24日 下午5:10:25
* @version V1.0  
*/ 
package dianfan.entities.logistics;

/** @ClassName OrderDeliveryRelate
 * @Description 
 * @author yl
 * @date 2018年7月24日 下午5:10:25
 */
public class OrderDeliveryRelate {
	
	private String id;// varchar(50) NOT NULL COMMENT '主键id'
	private String orderId;// varchar(50) DEFAULT NULL COMMENT '订单id'
	private String deliveryId;// varchar(50) DEFAULT NULL COMMENT '物流id'
	private String deliveryType;// varchar(2) DEFAULT NULL COMMENT '物流类型（01：发货物流，02：退货物流）'
	private String deliveryStatus;// varchar(2) DEFAULT NULL COMMENT '状态（01：在途02：到达签收03:延时再送）'
	private String expressType;// varchar(2) DEFAULT NULL COMMENT '快递类型（1:京东大件物流）'
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
	public String getDeliveryId() {
		return deliveryId;
	}
	public void setDeliveryId(String deliveryId) {
		this.deliveryId = deliveryId;
	}
	public String getDeliveryType() {
		return deliveryType;
	}
	public void setDeliveryType(String deliveryType) {
		this.deliveryType = deliveryType;
	}
	public String getDeliveryStatus() {
		return deliveryStatus;
	}
	public void setDeliveryStatus(String deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}
	public String getExpressType() {
		return expressType;
	}
	public void setExpressType(String expressType) {
		this.expressType = expressType;
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
		return "OrderDeliveryRelate [id=" + id + ", orderId=" + orderId + ", deliveryId=" + deliveryId
				+ ", deliveryType=" + deliveryType + ", deliveryStatus=" + deliveryStatus + ", expressType="
				+ expressType + ", entkbn=" + entkbn + ", version=" + version + "]";
	}

}
