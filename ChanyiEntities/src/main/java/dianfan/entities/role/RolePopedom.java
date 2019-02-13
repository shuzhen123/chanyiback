package dianfan.entities.role;

/**
 * @ClassName RolePopedom
 * @Description 管理员角色与权限表
 * @author sz
 * @date 2018年7月18日 下午1:45:00
 */
public class RolePopedom {
	
	private String id; // varchar(50) NOT NULL COMMENT '主键id',
	private String roleid; // varchar(50) DEFAULT NULL COMMENT '角色ID ',
	private String popedomid; // varchar(50) DEFAULT NULL COMMENT '权限Id',
	private String popedom; // varchar(1) DEFAULT NULL COMMENT '权限 （1为可用，2为不可用） ',
	
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
	@Override
	public String toString() {
		return "RolePopedom [id=" + id + ", roleid=" + roleid + ", popedomid=" + popedomid + ", popedom=" + popedom
				+ "]";
	}
	
	

}
