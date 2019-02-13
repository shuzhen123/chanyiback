package dianfan.entities.loginmanage;

import java.sql.Timestamp;

/**
 * @ClassName OperateLogs
 * @Description 系统日志
 * @author sz
 * @date 2018年7月27日 下午1:58:08
 */
public class OperateLogs {

	private String id; //varchar(50) NOT NULL COMMENT '主键id日志表id，uuid',
	private String userId; //varchar(50) DEFAULT NULL COMMENT '用户id,记录操作用户',
	private String popedomId; //varchar(225) DEFAULT NULL COMMENT '模块id',
	private String roleId; //varchar(50) DEFAULT NULL COMMENT '角色',
	private String operate; //text COMMENT '操作',
	private Timestamp time; //datetime DEFAULT NULL COMMENT '操作时间',
	private String ip; //varchar(50) DEFAULT NULL COMMENT 'ip地址',
	
	private String rolename; // 角色名称
	private String account; // 账号
	private String popedomname; // 模块全称    父模块+子模块
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPopedomId() {
		return popedomId;
	}
	public void setPopedomId(String popedomId) {
		this.popedomId = popedomId;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getOperate() {
		return operate;
	}
	public void setOperate(String operate) {
		this.operate = operate;
	}
	public Timestamp getTime() {
		return time;
	}
	public void setTime(Timestamp time) {
		this.time = time;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getRolename() {
		return rolename;
	}
	public void setRolename(String rolename) {
		this.rolename = rolename;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPopedomname() {
		return popedomname;
	}
	public void setPopedomname(String popedomname) {
		this.popedomname = popedomname;
	}
	@Override
	public String toString() {
		return "OperateLogs [id=" + id + ", userId=" + userId + ", popedomId=" + popedomId + ", roleId=" + roleId
				+ ", operate=" + operate + ", time=" + time + ", ip=" + ip + ", rolename=" + rolename + ", account="
				+ account + ", popedomname=" + popedomname + "]";
	}


	
	
}
