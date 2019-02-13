/**  
* @Title: FactoryAdminRelate.java
* @Package dianfan.entities.factory
* @Description: TODO
* @author yl
* @date 2018年7月18日 下午6:05:24
* @version V1.0  
*/ 
package dianfan.entities.factory;

/** @ClassName FactoryAdminRelate
 * @Description 
 * @author yl
 * @date 2018年7月18日 下午6:05:24
 */
public class FactoryAdminRelate {
	
	private String id;
	private String adminId;
	private String factoryId;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAdminId() {
		return adminId;
	}
	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}
	public String getFactoryId() {
		return factoryId;
	}
	public void setFactoryId(String factoryId) {
		this.factoryId = factoryId;
	}
	@Override
	public String toString() {
		return "FactoryAdminRelate [id=" + id + ", adminId=" + adminId + ", factoryId=" + factoryId + "]";
	}

}
