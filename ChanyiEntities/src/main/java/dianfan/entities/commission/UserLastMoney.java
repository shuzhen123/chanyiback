package dianfan.entities.commission;

import java.math.BigDecimal;

/**
 * @ClassName UserLastMoney
 * @Description 用户剩余体现金额
 * @author cjy
 * @date 2018年7月6日 上午11:32:15
 */
public class UserLastMoney {

	private String userid; //用户id
	private BigDecimal lastMoney; //余额
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public BigDecimal getLastMoney() {
		return lastMoney;
	}
	public void setLastMoney(BigDecimal lastMoney) {
		this.lastMoney = lastMoney;
	}
	@Override
	public String toString() {
		return "UserLastMoney [userid=" + userid + ", lastMoney=" + lastMoney + "]";
	}
}
