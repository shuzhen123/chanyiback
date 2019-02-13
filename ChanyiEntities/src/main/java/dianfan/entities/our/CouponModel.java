/**  
* @Title: CouponModel.java
* @Package dianfan.entities.our
* @Description: TODO
* @author yl
* @date 2018年7月3日 上午11:17:15
* @version V1.0  
*/ 
package dianfan.entities.our;

import java.sql.Timestamp;
import java.util.List;

import dianfan.entities.goods.GoodsClassify;
import dianfan.entities.store.Store;

/** @ClassName CouponModel
 * @Description 
 * @author yl
 * @date 2018年7月3日 上午11:17:15
 */
public class CouponModel {
	
	private String ucrid;//用户相关优惠券id
	private String id; // 优惠券id'
	private String couponName; // varchar(50) DEFAULT NULL COMMENT '优惠券名称'
	private Integer couponNum; // int(8) DEFAULT NULL COMMENT '优惠券数量'
	private Timestamp couponEndtime; // datetime DEFAULT NULL COMMENT '优惠券截止日期'
	private String couponApply; // varchar(2) DEFAULT NULL COMMENT '使用(01:应用02：停止应用)'
	private String couponType; // varchar(2) DEFAULT NULL COMMENT '优惠券类型(01:商家优惠券02：注册优惠券)'
	private String couponClassifyId; // text COMMENT '优惠券id（可多选，如果为空，全场通用）'
	private List<GoodsClassify> goodsClassify; // text COMMENT '优惠券id（可多选，如果为空，全场通用）'
	private String couponDes; // varchar(250) DEFAULT NULL COMMENT '优惠券描述'
	private Timestamp couponStarttime; // datetime DEFAULT NULL COMMENT '优惠券投放时间'
	private Timestamp createTime; // datetime DEFAULT NULL COMMENT '创建时间'
	private Integer userUsed; // tinyint(1) DEFAULT '0' COMMENT '用户是否使用(默认未被使用)'
	private Timestamp drawDate; // datetime DEFAULT NULL COMMENT '领取日期'
	private Timestamp usedDate; // datetime DEFAULT NULL COMMENT '使用日期'
	private Timestamp usedEndTime; // datetime DEFAULT NULL COMMENT '截止日期'
	private String couponCondtion;//优惠券满足条件
	private String couponReduceMoney;//优惠券优惠金额
	private String goodsid;
	private List<GoodsModel> goods;
	private String storeid;
	private List<Store> store;
	
	public List<GoodsClassify> getGoodsClassify() {
		return goodsClassify;
	}
	public void setGoodsClassify(List<GoodsClassify> goodsClassify) {
		this.goodsClassify = goodsClassify;
	}
	public List<GoodsModel> getGoods() {
		return goods;
	}
	public void setGoods(List<GoodsModel> goods) {
		this.goods = goods;
	}
	public List<Store> getStore() {
		return store;
	}
	public void setStore(List<Store> store) {
		this.store = store;
	}
	public String getGoodsid() {
		return goodsid;
	}
	public void setGoodsid(String goodsid) {
		this.goodsid = goodsid;
	}
	public String getStoreid() {
		return storeid;
	}
	public void setStoreid(String storeid) {
		this.storeid = storeid;
	}
	
	public String getUcrid() {
		return ucrid;
	}

	public void setUcrid(String ucrid) {
		this.ucrid = ucrid;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCouponName() {
		return couponName;
	}

	public void setCouponName(String couponName) {
		this.couponName = couponName;
	}

	public Integer getCouponNum() {
		return couponNum;
	}

	public void setCouponNum(Integer couponNum) {
		this.couponNum = couponNum;
	}

	public Timestamp getCouponEndtime() {
		return couponEndtime;
	}

	public void setCouponEndtime(Timestamp couponEndtime) {
		this.couponEndtime = couponEndtime;
	}

	public String getCouponApply() {
		return couponApply;
	}

	public void setCouponApply(String couponApply) {
		this.couponApply = couponApply;
	}

	public String getCouponType() {
		return couponType;
	}

	public void setCouponType(String couponType) {
		this.couponType = couponType;
	}

	public String getCouponClassifyId() {
		return couponClassifyId;
	}

	public void setCouponClassifyId(String couponClassifyId) {
		this.couponClassifyId = couponClassifyId;
	}

	public String getCouponDes() {
		return couponDes;
	}

	public void setCouponDes(String couponDes) {
		this.couponDes = couponDes;
	}

	public Timestamp getCouponStarttime() {
		return couponStarttime;
	}

	public void setCouponStarttime(Timestamp couponStarttime) {
		this.couponStarttime = couponStarttime;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Integer getUserUsed() {
		return userUsed;
	}

	public void setUserUsed(Integer userUsed) {
		this.userUsed = userUsed;
	}

	public Timestamp getDrawDate() {
		return drawDate;
	}

	public void setDrawDate(Timestamp drawDate) {
		this.drawDate = drawDate;
	}

	public Timestamp getUsedDate() {
		return usedDate;
	}

	public void setUsedDate(Timestamp usedDate) {
		this.usedDate = usedDate;
	}

	public Timestamp getUsedEndTime() {
		return usedEndTime;
	}

	public void setUsedEndTime(Timestamp usedEndTime) {
		this.usedEndTime = usedEndTime;
	}

	public String getCouponCondtion() {
		return couponCondtion;
	}

	public void setCouponCondtion(String couponCondtion) {
		this.couponCondtion = couponCondtion;
	}

	public String getCouponReduceMoney() {
		return couponReduceMoney;
	}

	public void setCouponReduceMoney(String couponReduceMoney) {
		this.couponReduceMoney = couponReduceMoney;
	}

	@Override
	public String toString() {
		return "CouponModel [id=" + id + ", couponName=" + couponName + ", couponNum=" + couponNum + ", couponEndtime="
				+ couponEndtime + ", couponApply=" + couponApply + ", couponType=" + couponType + ", couponClassifyId="
				+ couponClassifyId + ", couponDes=" + couponDes + ", couponStarttime=" + couponStarttime
				+ ", createTime=" + createTime + ", userUsed=" + userUsed + ", drawDate=" + drawDate + ", usedDate="
				+ usedDate + ", usedEndTime=" + usedEndTime + ", couponCondtion=" + couponCondtion
				+ ", couponReduceMoney=" + couponReduceMoney + "]";
	}

}
