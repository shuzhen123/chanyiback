/**  
* @Title: UserRole.java
* @Package dianfan.entities.role
* @Description: TODO
* @author yl
* @date 2018年7月3日 下午5:56:45
* @version V1.0  
*/ 
package dianfan.entities.role;

/** @ClassName UserRole
 * @Description 
 * @author yl
 * @date 2018年7月3日 下午5:56:45
 */
public class UserRole {
	
	private String id;
	private String userid;
	private String roleid;
	private String descption;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getRoleid() {
		return roleid;
	}
	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}
	public String getDescption() {
		return descption;
	}
	public void setDescption(String descption) {
		this.descption = descption;
	}
	@Override
	public String toString() {
		return "UserRole [id=" + id + ", userid=" + userid + ", roleid=" + roleid + ", descption=" + descption + "]";
	}
	

}
