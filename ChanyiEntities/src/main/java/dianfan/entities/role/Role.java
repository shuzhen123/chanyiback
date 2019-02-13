/**  
* @Title: Role.java
* @Package dianfan.entities.role
* @Description: TODO
* @author yl
* @date 2018年7月3日 下午6:00:55
* @version V1.0  
*/ 
package dianfan.entities.role;

import java.math.BigDecimal;
import java.sql.Timestamp;

/** @ClassName Role
 * @Description 
 * @author yl
 * @date 2018年7月3日 下午6:00:55
 */
public class Role {
	
	private String id;// varchar(50) NOT NULL COMMENT '主键id',
	private String roleDistinguish;// varchar(50) DEFAULT NULL COMMENT '角色区分',
	private String roleName;//varchar(50) DEFAULT NULL COMMENT '角色描述',
	private Timestamp createTime;//datetime DEFAULT NULL COMMENT '创建时间',
	private String roleDescription;//varchar(200) DEFAULT NULL COMMENT '角色描述',
//	private BigDecimal saleDiscount;// decimal(3,2) DEFAULT NULL COMMENT '商品折扣',
	private int entkbn;// int(1) DEFAULT '0' COMMENT '数据有效区分(0:数据有效1:数据无效9:逻辑删除)',
	private Integer rolenum;//角色对应的人数
	private String popedomid;//权限id
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public String getRoleDescription() {
		return roleDescription;
	}
	public void setRoleDescription(String roleDescription) {
		this.roleDescription = roleDescription;
	}
	/*public BigDecimal getSaleDiscount() {
		return saleDiscount;
	}
	public void setSaleDiscount(BigDecimal saleDiscount) {
		this.saleDiscount = saleDiscount;
	}*/
	public Integer getEntkbn() {
		return entkbn;
	}
	public void setEntkbn(Integer entkbn) {
		this.entkbn = entkbn;
	}
	public Integer getRolenum() {
		return rolenum;
	}
	public void setRolenum(Integer rolenum) {
		this.rolenum = rolenum;
	}
	public String getPopedomid() {
		return popedomid;
	}
	public void setPopedomid(String popedomid) {
		this.popedomid = popedomid;
	}
	@Override
	public String toString() {
		return "Role [id=" + id + ", roleDistinguish=" + roleDistinguish + ", roleName=" + roleName + ", createTime="
				+ createTime + ", roleDescription=" + roleDescription + ", entkbn="+ entkbn + "]";
	}

}
