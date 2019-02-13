package dianfan.entities.commission;

/**
 * @ClassName UserRoleDist
 * @Description 用户id与角色区分
 * @author cjy
 * @date 2018年7月5日 下午3:15:09
 */
public class UserRoleDist {
	private String userid; // 用户id
	private String roleDistinguish; // 角色区分
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getRoleDistinguish() {
		return roleDistinguish;
	}
	public void setRoleDistinguish(String roleDistinguish) {
		this.roleDistinguish = roleDistinguish;
	}
	@Override
	public String toString() {
		return "UserRoleDist [userid=" + userid + ", roleDistinguish=" + roleDistinguish + "]";
	}
}
