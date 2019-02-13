package dianfan.entities.loginmanage;

import java.sql.Timestamp;

/**
 * @ClassName AdminLoginLog
 * @Description  用户登陆记录表
 * @author sz
 * @date 2018年7月17日 上午11:19:30
 */
public class AdminLoginLog {
	private String logId; // varchar(50) NOT NULL COMMENT '登录日志ID',
	private Timestamp loginDatetime; // timestamp NOT '登录时间',
	private String loginIp; // varchar(20) DEFAULT NULL COMMENT '登录IP',
	private String userId; // varchar(50) DEFAULT NULL COMMENT '用户ID',
	private String browser; // varchar(50) DEFAULT NULL COMMENT '浏览器',
	
	
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
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getBrowser() {
		return browser;
	}
	public void setBrowser(String browser) {
		this.browser = browser;
	}
	@Override
	public String toString() {
		return "AdminLoginLog [logId=" + logId + ", loginDatetime=" + loginDatetime + ", loginIp=" + loginIp
				+ ", userId=" + userId + ", browser=" + browser + "]";
	}
	
	
	

}
