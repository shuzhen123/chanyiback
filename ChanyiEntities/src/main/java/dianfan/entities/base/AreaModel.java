/**  
* @Title: AreaModel.java
* @Package dianfan.entities.base
* @Description: TODO
* @author yl
* @date 2018年7月18日 下午2:46:33
* @version V1.0  
*/ 
package dianfan.entities.base;

/** @ClassName AreaModel
 * @Description 
 * @author yl
 * @date 2018年7月18日 下午2:46:33
 */
public class AreaModel {
	
	private String areaCode;
	private String areaName;
	private String supAreaCode;
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public String getSupAreaCode() {
		return supAreaCode;
	}
	public void setSupAreaCode(String supAreaCode) {
		this.supAreaCode = supAreaCode;
	}
	@Override
	public String toString() {
		return "AreaModel [areaCode=" + areaCode + ", areaName=" + areaName + ", supAreaCode=" + supAreaCode + "]";
	}
	
}
