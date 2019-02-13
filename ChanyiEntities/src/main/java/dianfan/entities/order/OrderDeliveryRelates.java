package dianfan.entities.order;

import java.sql.Date;
import java.util.List;

import dianfan.entities.logistics.LogisticsModel;

/**
 * @ClassName OrderDeliveryRelate
 * @Description 订单物流相关
 * @author sz
 * @date 2018年7月24日 下午5:59:07
 */
public class OrderDeliveryRelates {

	private String id; //varchar(50) NOT NULL COMMENT '主键id',
	private String orderId; //varchar(50) DEFAULT NULL COMMENT '订单id',
	private String deliveryId; //varchar(50) DEFAULT NULL COMMENT '物流id',
	private String deliveryType; //varchar(2) DEFAULT NULL COMMENT '物流类型（01：发货物流，02：退货物流）',
	private String deliveryStatus; //varchar(2) DEFAULT NULL COMMENT '状态（01：在途02：到达签收03:延时再送）',
	private String expressType; //varchar(2) DEFAULT NULL COMMENT '快递类型（1:京东大件物流）',
	private int entkbn; //int(1) DEFAULT '0' COMMENT '数据有效区分(0:数据有效1:数据无效9:逻辑删除)',
	private Date deliveryTime; //int(1) DEFAULT '0' COMMENT '数据有效区分(0:数据有效1:数据无效9:逻辑删除)',
	private List<LogisticsModel> logistics ; // 订单下的物流    一对多段关系
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
	public int getEntkbn() {
		return entkbn;
	}
	public void setEntkbn(int entkbn) {
		this.entkbn = entkbn;
	}
	public Date getDeliveryTime() {
		return deliveryTime;
	}
	public void setDeliveryTime(Date deliveryTime) {
		this.deliveryTime = deliveryTime;
	}
	public List<LogisticsModel> getLogistics() {
		return logistics;
	}
	public void setLogistics(List<LogisticsModel> logistics) {
		this.logistics = logistics;
	}
	@Override
	public String toString() {
		return "OrderDeliveryRelates [id=" + id + ", orderId=" + orderId + ", deliveryId=" + deliveryId
				+ ", deliveryType=" + deliveryType + ", deliveryStatus=" + deliveryStatus + ", expressType="
				+ expressType + ", entkbn=" + entkbn + ", deliveryTime=" + deliveryTime + ", logistics=" + logistics
				+ "]";
	}
	
	

	
	
	
	



	
}
