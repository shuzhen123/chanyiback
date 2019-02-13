package dianfan.entities.role;

import java.sql.Timestamp;

public class TRole {

	private String id;// varchar(50) NOT NULL COMMENT '主键id',
	private String roleDistinguish;// varchar(50) DEFAULT NULL COMMENT '角色区分',
	private String roleName;//varchar(50) DEFAULT NULL COMMENT '角色描述',
	private Timestamp createTime;//datetime DEFAULT NULL COMMENT '创建时间',
	private String roleDescription;//varchar(200) DEFAULT NULL COMMENT '角色描述',
	private int entkbn;// int(1) DEFAULT '0' COMMENT '数据有效区分(0:数据有效1:数据无效9:逻辑删除)',
	
	private String popedom;// 角色下对应的ID
	
	
	public String getPopedom() {
		return popedom;
	}
	public void setPopedom(String popedom) {
		this.popedom = popedom;
	}
	public void setEntkbn(int entkbn) {
		this.entkbn = entkbn;
	}
	
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
	@Override
	public String toString() {
		return "TRole [id=" + id + ", roleDistinguish=" + roleDistinguish + ", roleName=" + roleName + ", createTime="
				+ createTime + ", roleDescription=" + roleDescription + ", entkbn=" + entkbn + ", popedom=" + popedom
				+ "]";
	}
	
}
