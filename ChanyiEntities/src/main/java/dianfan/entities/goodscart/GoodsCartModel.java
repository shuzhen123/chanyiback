/**  
* @Title: GoodsCart.java
* @Package dianfan.entities.goodscart
* @Description: TODO
* @author yl
* @date 2018年7月3日 下午3:36:33
* @version V1.0  
*/ 
package dianfan.entities.goodscart;

import java.math.BigDecimal;
import java.sql.Timestamp;

/** @ClassName GoodsCart
 * @Description 
 * @author yl
 * @date 2018年7月3日 下午3:36:33
 */
public class GoodsCartModel {
	
	private String id;// varchar(50) NOT NULL COMMENT 'id 自增'
	private String userId;// varchar(50) DEFAULT NULL COMMENT '用户id'
	private String goodsId;// varchar(50) DEFAULT NULL COMMENT '商品id'
	private String goodsPriceId;// varchar(50) DEFAULT NULL COMMENT '商品价格表id'
	private String name;// varchar(50) DEFAULT NULL COMMENT '名称'
	private Integer num;// int(9) DEFAULT NULL COMMENT '数量'
	private BigDecimal unit;// varchar(18) DEFAULT NULL COMMENT '单位 01:张（床）'
	private String picAddr;// varchar(255) DEFAULT NULL COMMENT '图片存储地址 此地址对应床垫成品图'
	private Timestamp joinTime;// timestamp NOT NULL DEFAULT CURRENTTIMESTAMP ON UPDATE CURRENTTIMESTAMP COMMENT '加入时间'
	private Integer entkbn;// int(1) DEFAULT '0' COMMENT '数据有效区分(0:数据有效1:数据无效9:逻辑删除)'
	private Integer version;// int(11) NOT NULL DEFAULT '0' COMMENT '版本号'
	private String specName;//varchar(50) NOT NULL COMMENT '规格名称'
	private BigDecimal price; //价格
	private String goodsClassifyId;//商品分类id
	private String specIds;
	private String goodsTitleEn;
	
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
	public String getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	public String getGoodsPriceId() {
		return goodsPriceId;
	}
	public void setGoodsPriceId(String goodsPriceId) {
		this.goodsPriceId = goodsPriceId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public BigDecimal getUnit() {
		return unit;
	}
	public void setUnit(BigDecimal unit) {
		this.unit = unit;
	}
	public String getPicAddr() {
		return picAddr;
	}
	public void setPicAddr(String picAddr) {
		this.picAddr = picAddr;
	}
	public Timestamp getJoinTime() {
		return joinTime;
	}
	public void setJoinTime(Timestamp joinTime) {
		this.joinTime = joinTime;
	}
	public Integer getEntkbn() {
		return entkbn;
	}
	public void setEntkbn(Integer entkbn) {
		this.entkbn = entkbn;
	}
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	public String getSpecName() {
		return specName;
	}
	public void setSpecName(String specName) {
		this.specName = specName;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	public String getGoodsClassifyId() {
		return goodsClassifyId;
	}
	public void setGoodsClassifyId(String goodsClassifyId) {
		this.goodsClassifyId = goodsClassifyId;
	}
	public String getSpecIds() {
		return specIds;
	}
	public void setSpecIds(String specIds) {
		this.specIds = specIds;
	}
	public String getGoodsTitleEn() {
		return goodsTitleEn;
	}
	public void setGoodsTitleEn(String goodsTitleEn) {
		this.goodsTitleEn = goodsTitleEn;
	}
	@Override
	public String toString() {
		return "GoodsCartModel [id=" + id + ", userId=" + userId + ", goodsId=" + goodsId + ", goodsPriceId="
				+ goodsPriceId + ", name=" + name + ", num=" + num + ", unit=" + unit + ", picAddr=" + picAddr
				+ ", joinTime=" + joinTime + ", entkbn=" + entkbn + ", version=" + version + ", specName=" + specName
				+ ", price=" + price + ", goodsClassifyId=" + goodsClassifyId + ", specIds=" + specIds
				+ ", goodsTitleEn=" + goodsTitleEn + "]";
	}
}
