package dianfan.entities.loginmanage;

import java.sql.Timestamp;

/**
 * @ClassName AdminInfo
 * @Description 管理员信息
 * @author sz
 * @date 2018年7月16日 下午1:32:41
 */
public class AdminInfo {

	private String id; //` varchar(50) NOT NULL COMMENT '主键id',
	private String account; //` varchar(50) DEFAULT NULL COMMENT '账号',
	
	private String roleName; //` 角色
	private String roleId; //` 角色id 
	
	private int entkbn; //` 数据有效性
	
	private String password; //` varchar(64) DEFAULT NULL COMMENT '密码',
	
	
	private Timestamp loginDatetime; // 最近一次的登陆时间
	private String loginIp; // 最近一次的'登录IP',
	private String browser; //最近一次的登陆是使用的 '浏览器',
	private String logId; //登录日志ID,
	
	
	
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getLogId() {
		return logId;
	}
	public void setLogId(String logId) {
		this.logId = logId;
	}
	public Timestamp getLoginDatetime() {
		return loginDatetime;
	}
	public void setLoginDatetime(Timestamp loginDatetime) {
		this.loginDatetime = loginDatetime;
	}
	public String getLoginIp() {
		return loginIp;
	}
	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}
	public String getBrowser() {
		return browser;
	}
	public void setBrowser(String browser) {
		this.browser = browser;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}


	
	public int getEntkbn() {
		return entkbn;
	}
	public void setEntkbn(int entkbn) {
		this.entkbn = entkbn;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	@Override
	public String toString() {
		return "AdminInfo [id=" + id + ", account=" + account + ", roleName=" + roleName + ", roleId=" + roleId
				+ ", entkbn=" + entkbn + ", password=" + password + ", loginDatetime=" + loginDatetime + ", loginIp="
				+ loginIp + ", browser=" + browser + ", logId=" + logId + "]";
	}


	

	


	
}
