/**  
* @Title: DeliveryModels.java
* @Package dianfan.entities.logistics
* @Description: TODO
* @author yl
* @date 2018年8月8日 下午5:25:55
* @version V1.0  
*/ 
package dianfan.entities.logistics;

/** @ClassName DeliveryModels
 * @Description 
 * @author yl
 * @date 2018年8月8日 下午5:25:55
 */
public class DeliveryModels {
	
	private String senderName;// varchar(50) DEFAULT NULL COMMENT '寄件人姓名',
	private String senderMob;// varchar(50) DEFAULT NULL COMMENT '寄件人手机',
	private String senderAddr;// varchar(200) DEFAULT NULL COMMENT '寄件人地址',
	private String receiverName;// varchar(50) DEFAULT NULL COMMENT '收件人姓名',
	private String receiverMob;// varchar(50) DEFAULT NULL COMMENT '收件人手机',
	private String receiverAddr;// varchar(200) DEFAULT NULL COMMENT '收货人地址',
	public String getSenderName() {
		return senderName;
	}
	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}
	public String getSenderMob() {
		return senderMob;
	}
	public void setSenderMob(String senderMob) {
		this.senderMob = senderMob;
	}
	
	public String getSenderAddr() {
		return senderAddr;
	}
	public void setSenderAddr(String senderAddr) {
		this.senderAddr = senderAddr;
	}
	public String getReceiverName() {
		return receiverName;
	}
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}
	public String getReceiverMob() {
		return receiverMob;
	}
	public void setReceiverMob(String receiverMob) {
		this.receiverMob = receiverMob;
	}

	public String getReceiverAddr() {
		return receiverAddr;
	}
	public void setReceiverAddr(String receiverAddr) {
		this.receiverAddr = receiverAddr;
	}

}
