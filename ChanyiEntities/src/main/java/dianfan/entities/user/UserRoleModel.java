package dianfan.entities.user;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @ClassName UserRole
 * @Description 角色权限
 * @author sz
 * @date 2018年7月3日 上午9:46:26
 */
public class UserRoleModel {
	
	private String id; //varchar(50) NOT NULL COMMENT '主键id',
	private String roleDistinguish; //varchar(50) DEFAULT NULL COMMENT '角色区分',
	private String roleName; //varchar(50) DEFAULT NULL COMMENT '角色描述',
	private Timestamp createTime; //datetime DEFAULT NULL COMMENT '创建时间',
	private String roleDescription; //varchar(200) DEFAULT NULL COMMENT '角色描述',
	private BigDecimal saleDiscount; //decimal(3,2) DEFAULT NULL COMMENT '商品折扣',
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
	public BigDecimal getSaleDiscount() {
		return saleDiscount;
	}
	public void setSaleDiscount(BigDecimal saleDiscount) {
		this.saleDiscount = saleDiscount;
	}
	@Override
	public String toString() {
		return "UserRole [id=" + id + ", roleDistinguish=" + roleDistinguish + ", roleName=" + roleName
				+ ", createTime=" + createTime + ", roleDescription=" + roleDescription + ", saleDiscount="
				+ saleDiscount + "]";
	}
	
	
	
		
	
}
