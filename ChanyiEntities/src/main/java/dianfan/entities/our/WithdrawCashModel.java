/**  
* @Title: WithdrawCash.java
* @Package dianfan.entities.our
* @Description: TODO
* @author yl
* @date 2018年7月2日 上午10:01:09
* @version V1.0  
*/ 
package dianfan.entities.our;

import java.math.BigDecimal;
import java.sql.Timestamp;

/** @ClassName WithdrawCash
 * @Description 提现model
 * @author yl
 * @date 2018年7月2日 上午10:01:09
 */
public class WithdrawCashModel {
	
	private String id;// varchar(50) NOT NULL COMMENT '主键id'
	private String userId;// varchar(50) DEFAULT NULL COMMENT '用户id'
	private String realName;
	private BigDecimal money;// decimal(152) DEFAULT NULL COMMENT '提现金额'
	private String applyStatus;// varchar(1) DEFAULT NULL COMMENT '审批状态(0:待审批 1:审批通过 2:审批不通过)'
	private String bankStatus;// varchar(1) DEFAULT NULL COMMENT '银行状态(1:提交到银行2：提现失败3：提现成功4：撤销提交)'
	private String bankno;
	private String cardNo;
	private String bankCode;
	private Timestamp applyTime;// datetime DEFAULT NULL COMMENT '申请时间'
	private Timestamp createTime;// datetime DEFAULT NULL COMMENT '创建时间'
	private String entkbn;// int(1) DEFAULT '0' COMMENT '数据有效区分(0:数据有效1:数据无效9:逻辑删除)'
	private String createBy;//创建者
	
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
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
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
	public String getBankno() {
		return bankno;
	}
	public void setBankno(String bankno) {
		this.bankno = bankno;
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
	public String getEntkbn() {
		return entkbn;
	}
	public void setEntkbn(String entkbn) {
		this.entkbn = entkbn;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	@Override
	public String toString() {
		return "WithdrawCashModel [id=" + id + ", userId=" + userId + ", money=" + money + ", applyStatus="
				+ applyStatus + ", bankStatus=" + bankStatus + ", bankno=" + bankno + ", applyTime=" + applyTime
				+ ", createTime=" + createTime + ", entkbn=" + entkbn + ", createBy=" + createBy + "]";
	}
}
