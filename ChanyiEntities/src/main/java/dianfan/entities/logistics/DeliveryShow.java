/**  
* @Title: DeliveryShow.java
* @Package dianfan.entities.logistics
* @Description: TODO
* @author yl
* @date 2018年8月8日 下午6:04:54
* @version V1.0  
*/ 
package dianfan.entities.logistics;

import java.util.List;
import java.util.Map;

/** @ClassName DeliveryShow
 * @Description 
 * @author yl
 * @date 2018年8月8日 下午6:04:54
 */
public class DeliveryShow {
	
	private String deliveryType;
	private String deliveryStatus;
	private List<Map> logisticsinfo;
	private LogisticsModel lms;
	
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
	public List<Map> getLogisticsinfo() {
		return logisticsinfo;
	}
	public void setLogisticsinfo(List<Map> logisticsinfo) {
		this.logisticsinfo = logisticsinfo;
	}
	public LogisticsModel getLms() {
		return lms;
	}
	public void setLms(LogisticsModel lms) {
		this.lms = lms;
	}

}
