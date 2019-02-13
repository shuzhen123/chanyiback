package dianfan.entities.commission;

import java.math.BigDecimal;

/**
 * @ClassName RoleCommissionPercentage
 * @Description 角色的佣金比例
 * @author cjy
 * @date 2018年7月5日 上午11:05:25
 */
public class RoleCommissionPercentage {
	private String roleid; // 角色id
	private String roleDistinguish; // 角色区分
	private String scene; // 应用场景
	private BigDecimal proportion; // 佣金比例
	public String getRoleid() {
		return roleid;
	}
	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}
	public String getRoleDistinguish() {
		return roleDistinguish;
	}
	public void setRoleDistinguish(String roleDistinguish) {
		this.roleDistinguish = roleDistinguish;
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
	@Override
	public String toString() {
		return "RoleCommissionPercentage [roleid=" + roleid + ", roleDistinguish=" + roleDistinguish + ", scene="
				+ scene + ", proportion=" + proportion + "]";
	}
}
