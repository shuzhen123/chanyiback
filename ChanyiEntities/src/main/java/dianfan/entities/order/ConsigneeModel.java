/**  
* @Title: ConsigneeModel.java
* @Package dianfan.entities.order
* @Description: TODO
* @author yl
* @date 2018年7月12日 上午10:34:43
* @version V1.0  
*/ 
package dianfan.entities.order;

/** @ClassName ConsigneeModel
 * @Description 
 * @author yl
 * @date 2018年7月12日 上午10:34:43
 */
public class ConsigneeModel {
	
	private String name;//收货人姓名
	private String telno;//收货人手机号
	private String areaCode;//地区code
	private String detailAddr;//详细地址
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTelno() {
		return telno;
	}
	public void setTelno(String telno) {
		this.telno = telno;
	}
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	public String getDetailAddr() {
		return detailAddr;
	}
	public void setDetailAddr(String detailAddr) {
		this.detailAddr = detailAddr;
	}
	@Override
	public String toString() {
		return "ConsigneeModel [name=" + name + ", telno=" + telno + ", areaCode=" + areaCode + ", detailAddr="
				+ detailAddr + "]";
	}

}
