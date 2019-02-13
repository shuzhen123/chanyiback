/**  
* @Title: CommisionModel.java
* @Package dianfan.entities.commision
* @Description: TODO
* @author yl
* @date 2018年7月25日 下午3:32:09
* @version V1.0  
*/ 
package dianfan.entities.commision;

import java.math.BigDecimal;

/** @ClassName CommisionModel
 * @Description 
 * @author yl
 * @date 2018年7月25日 下午3:32:09
 */
public class CommisionModel {
	
	private String id;
	private String roleId;
	private String scene;
	private BigDecimal proportion;
	private String roleName;
	private Integer version;
	private String updateBy;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getScene() {
		return scene;
	}
	public void setScene(String scene) {
		this.scene = scene;
	}
	public BigDecimal getProportion() {
		return proportion;
	}
	public void setProportion(BigDecimal proportion) {
		this.proportion = proportion;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	@Override
	public String toString() {
		return "CommisionModel [id=" + id + ", roleId=" + roleId + ", scene=" + scene + ", proportion=" + proportion
				+ ", roleName=" + roleName + ", version=" + version + ", updateBy=" + updateBy + "]";
	}
}
