/**  
* @Title: UserBounsDetail.java
* @Package dianfan.entities.our
* @Description: TODO
* @author yl
* @date 2018年6月30日 下午12:33:13
* @version V1.0  
*/ 
package dianfan.entities.our;

import java.sql.Timestamp;

/** @ClassName UserBounsDetail
 * @Description 
 * @author yl
 * @date 2018年6月30日 下午12:33:13
 */
public class MoneyEntryExit {
	
	private String money;
	private Timestamp createtime;
	private String status;
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	public Timestamp getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "MoneyEntryExit [money=" + money + ", createtime=" + createtime + ", status=" + status + "]";
	}
	
}
