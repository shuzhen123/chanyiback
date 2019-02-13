package dianfan.entities.commission;
/**
 * @ClassName UserBindRealtion
 * @Description 用户绑定关系
 * @author cjy
 * @date 2018年7月5日 上午10:28:59
 */
public class UserBindRealtion {
	private String userid; //用户id
	private String salerid; //导购id
	private String consumerid; //消费商id
	private String storeid; //体验店id
	private String roleDistinguish; //角色区分(01：大区经理，02：运营服务商，03：市场开发经理，04：城市经理，05：体验店，06：导购，07：消费商，08：普通人)
	private String areaCode; //收货地址code
	
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getSalerid() {
		return salerid;
	}
	public void setSalerid(String salerid) {
		this.salerid = salerid;
	}
	public String getConsumerid() {
		return consumerid;
	}
	public void setConsumerid(String consumerid) {
		this.consumerid = consumerid;
	}
	public String getStoreid() {
		return storeid;
	}
	public void setStoreid(String storeid) {
		this.storeid = storeid;
	}
	public String getRoleDistinguish() {
		return roleDistinguish;
	}
	public void setRoleDistinguish(String roleDistinguish) {
		this.roleDistinguish = roleDistinguish;
	}
	@Override
	public String toString() {
		return "UserBindRealtion [userid=" + userid + ", salerid=" + salerid + ", consumerid=" + consumerid
				+ ", storeid=" + storeid + ", roleDistinguish=" + roleDistinguish + ", areaCode=" + areaCode + "]";
	}
}
