/**  
* @Title: UserRolePopedomModel.java
* @Package dianfan.entities.urpopedom
* @Description: TODO
* @author yl
* @date 2018年7月25日 上午10:28:31
* @version V1.0  
*/ 
package dianfan.entities.urpopedom;

/** @ClassName UserRolePopedomModel
 * @Description 
 * @author yl
 * @date 2018年7月25日 上午10:28:31
 */
public class UserRolePopedomModel {
	
	private String id;
	private String roleid;
	private String popedomid;
	private String popedom;
	private String roleDistinguish;
	private String roleName;
	private String saleDiscount;
	private String nickName;
	private String sex;
	private String avatarUrl;
	private String popedomname;
	private String popedomfatherid;
	private String popedomurl;
	private Integer start;
	private Integer offset;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRoleid() {
		return roleid;
	}
	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}
	public String getPopedomid() {
		return popedomid;
	}
	public void setPopedomid(String popedomid) {
		this.popedomid = popedomid;
	}
	public String getPopedom() {
		return popedom;
	}
	public void setPopedom(String popedom) {
		this.popedom = popedom;
	}
	public String getRoleDistinguish() {
		return roleDistinguish;
	}
	public void setRoleDistinguish(String roleDistinguish) {
		this.roleDistinguish = roleDistinguish;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getSaleDiscount() {
		return saleDiscount;
	}
	public void setSaleDiscount(String saleDiscount) {
		this.saleDiscount = saleDiscount;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getAvatarUrl() {
		return avatarUrl;
	}
	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}
	public String getPopedomname() {
		return popedomname;
	}
	public void setPopedomname(String popedomname) {
		this.popedomname = popedomname;
	}
	public String getPopedomfatherid() {
		return popedomfatherid;
	}
	public void setPopedomfatherid(String popedomfatherid) {
		this.popedomfatherid = popedomfatherid;
	}
	public String getPopedomurl() {
		return popedomurl;
	}
	public void setPopedomurl(String popedomurl) {
		this.popedomurl = popedomurl;
	}
	public Integer getStart() {
		return start;
	}
	public void setStart(Integer start) {
		this.start = start;
	}
	public Integer getOffset() {
		return offset;
	}
	public void setOffset(Integer offset) {
		this.offset = offset;
	}
	@Override
	public String toString() {
		return "UserRolePopedomModel [id=" + id + ", roleid=" + roleid + ", popedomid=" + popedomid + ", popedom="
				+ popedom + ", roleDistinguish=" + roleDistinguish + ", roleName=" + roleName + ", saleDiscount="
				+ saleDiscount + ", nickName=" + nickName + ", sex=" + sex + ", avatarUrl=" + avatarUrl
				+ ", popedomname=" + popedomname + ", popedomfatherid=" + popedomfatherid + ", popedomurl=" + popedomurl
				+ ", start=" + start + ", offset=" + offset + "]";
	}
}
