package dianfan.entities.order;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @ClassName UserWithdrawDepositStream
 * @Description 
 * @author sz
 * @date 2018年7月25日 下午4:26:15
 */
public class UserWithdrawDepositStream {

	private String id; //varchar(50) NOT NULL COMMENT '主键id',
	private String userId; //varchar(50) DEFAULT NULL COMMENT '用户id',
	private BigDecimal money; //decimal(15,2) DEFAULT NULL COMMENT '提现金额',
	private String applyStatus; //varchar(1) DEFAULT NULL COMMENT '审批状态(0:待审批 1:审批通过 2:审批不通过)',
	private String bankStatus; //varchar(1) DEFAULT NULL COMMENT '银行状态(1:提交到银行2：提现失败3：提现成功4：撤销提交)',
	private String bankFreason; //varchar(150) DEFAULT NULL COMMENT '银行提现失败原因',
	private String bankNo; //varchar(50) DEFAULT NULL COMMENT '银行单号',
	private Timestamp bankTtime; //datetime DEFAULT NULL COMMENT '银行通过时间',
	private String wxdepositStatus; //varchar(1) DEFAULT NULL COMMENT '微信零钱状态(1:提交到银行2：提现失败3：提现成功4：撤销提交)',
	private String wxdepositFreason; //varchar(150) DEFAULT NULL COMMENT '微信零钱失败原因',
	private String wxdepositNo; //varchar(50) DEFAULT NULL COMMENT '微信零钱单号',
	private Timestamp wxdepositTtime; //datetime DEFAULT NULL COMMENT '微信零钱通过时间',
	private String alipayDepositStatus; //varchar(1) DEFAULT NULL COMMENT '支付宝零钱状态(1:提交到银行2：提现失败3：提现成功4：撤销提交)',
	private String alipayDepositFreason; //varchar(150) DEFAULT NULL COMMENT '支付宝零钱提现失败原因',
	private String alipayDepositNo; //varchar(50) DEFAULT NULL COMMENT '支付宝零钱单号',
	private Timestamp alipayDepositTtime; //datetime DEFAULT NULL COMMENT '支付宝零钱通过时间',
	private Timestamp applyTime; //datetime DEFAULT NULL COMMENT '申请时间',
	private Timestamp createTime; //datetime DEFAULT NULL COMMENT '创建时间',
	private String createBy; //varchar(50) DEFAULT NULL COMMENT '创建者',
	private Timestamp updateTime; //timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
	private String updateBy; //varchar(50) DEFAULT NULL COMMENT '更新者',
	private int entkbn; //int(1) DEFAULT '0' COMMENT '数据有效区分(0:数据有效1:数据无效9:逻辑删除)',
	
	
	private String roleid; // 角色ID
	private String role; // 角色名
	private String username; // 用户名
	
	
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
	public BigDecimal getMoney() {
		return money;
	}
	public void setMoney(BigDecimal money) {
		this.money = money;
	}
	public String getApplyStatus() {
		return applyStatus;
	}
	public void setApplyStatus(String applyStatus) {
		this.applyStatus = applyStatus;
	}
	public String getBankStatus() {
		return bankStatus;
	}
	public void setBankStatus(String bankStatus) {
		this.bankStatus = bankStatus;
	}
	public String getBankFreason() {
		return bankFreason;
	}
	public void setBankFreason(String bankFreason) {
		this.bankFreason = bankFreason;
	}
	public String getBankNo() {
		return bankNo;
	}
	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}
	public Timestamp getBankTtime() {
		return bankTtime;
	}
	public void setBankTtime(Timestamp bankTtime) {
		this.bankTtime = bankTtime;
	}
	public String getWxdepositStatus() {
		return wxdepositStatus;
	}
	public void setWxdepositStatus(String wxdepositStatus) {
		this.wxdepositStatus = wxdepositStatus;
	}
	public String getWxdepositFreason() {
		return wxdepositFreason;
	}
	public void setWxdepositFreason(String wxdepositFreason) {
		this.wxdepositFreason = wxdepositFreason;
	}
	public String getWxdepositNo() {
		return wxdepositNo;
	}
	public void setWxdepositNo(String wxdepositNo) {
		this.wxdepositNo = wxdepositNo;
	}
	public Timestamp getWxdepositTtime() {
		return wxdepositTtime;
	}
	public void setWxdepositTtime(Timestamp wxdepositTtime) {
		this.wxdepositTtime = wxdepositTtime;
	}
	public String getAlipayDepositStatus() {
		return alipayDepositStatus;
	}
	public void setAlipayDepositStatus(String alipayDepositStatus) {
		this.alipayDepositStatus = alipayDepositStatus;
	}
	public String getAlipayDepositFreason() {
		return alipayDepositFreason;
	}
	public void setAlipayDepositFreason(String alipayDepositFreason) {
		this.alipayDepositFreason = alipayDepositFreason;
	}
	public String getAlipayDepositNo() {
		return alipayDepositNo;
	}
	public void setAlipayDepositNo(String alipayDepositNo) {
		this.alipayDepositNo = alipayDepositNo;
	}
	public Timestamp getAlipayDepositTtime() {
		return alipayDepositTtime;
	}
	public void setAlipayDepositTtime(Timestamp alipayDepositTtime) {
		this.alipayDepositTtime = alipayDepositTtime;
	}
	public Timestamp getApplyTime() {
		return applyTime;
	}
	public void setApplyTime(Timestamp applyTime) {
		this.applyTime = applyTime;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public Timestamp getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	public int getEntkbn() {
		return entkbn;
	}
	public void setEntkbn(int entkbn) {
		this.entkbn = entkbn;
	}
	public String getRoleid() {
		return roleid;
	}
	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	@Override
	public String toString() {
		return "UserWithdrawDepositStream [id=" + id + ", userId=" + userId + ", money=" + money + ", applyStatus="
				+ applyStatus + ", bankStatus=" + bankStatus + ", bankFreason=" + bankFreason + ", bankNo=" + bankNo
				+ ", bankTtime=" + bankTtime + ", wxdepositStatus=" + wxdepositStatus + ", wxdepositFreason="
				+ wxdepositFreason + ", wxdepositNo=" + wxdepositNo + ", wxdepositTtime=" + wxdepositTtime
				+ ", alipayDepositStatus=" + alipayDepositStatus + ", alipayDepositFreason=" + alipayDepositFreason
				+ ", alipayDepositNo=" + alipayDepositNo + ", alipayDepositTtime=" + alipayDepositTtime + ", applyTime="
				+ applyTime + ", createTime=" + createTime + ", createBy=" + createBy + ", updateTime=" + updateTime
				+ ", updateBy=" + updateBy + ", entkbn=" + entkbn + ", roleid=" + roleid + ", role=" + role
				+ ", username=" + username + "]";
	}
	
	
	
}
