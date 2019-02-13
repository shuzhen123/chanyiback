/**  
* @Title: GoodsCartSpecModel.java
* @Package dianfan.entities.goodscart
* @Description: TODO
* @author yl
* @date 2018年7月4日 下午3:00:44
* @version V1.0  
*/ 
package dianfan.entities.goodscart;

import java.math.BigDecimal;

/** @ClassName GoodsCartSpecModel
 * @Description 
 * @author yl
 * @date 2018年7月4日 下午3:00:44
 */
public class GoodsCartSpecModel {
	
	private String id;// varchar(50) NOT NULL COMMENT 'id 自增'
	private String userId;// varchar(50) DEFAULT NULL COMMENT '用户id'
	private String goodsId;// varchar(50) DEFAULT NULL COMMENT '商品id'
	private String goodsPriceId;// varchar(50) DEFAULT NULL COMMENT '商品价格表id'
	private String name;// varchar(50) DEFAULT NULL COMMENT '名称'
	private Integer num;// int(9) DEFAULT NULL COMMENT '数量'
	private String picAddr;// varchar(255) DEFAULT NULL COMMENT '图片存储地址 此地址对应床垫成品图'
	private String specIds; //商品规格表多个以逗号分隔
	private BigDecimal price; //价格
	private String specName; //规格名称
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
	public String getPicAddr() {
		return picAddr;
	}
	public void setPicAddr(String picAddr) {
		this.picAddr = picAddr;
	}
	public String getSpecIds() {
		return specIds;
	}
	public void setSpecIds(String specIds) {
		this.specIds = specIds;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public String getSpecName() {
		return specName;
	}
	public void setSpecName(String specName) {
		this.specName = specName;
	}
	@Override
	public String toString() {
		return "GoodsCartSpecModel [id=" + id + ", userId=" + userId + ", goodsId=" + goodsId + ", goodsPriceId="
				+ goodsPriceId + ", name=" + name + ", num=" + num + ", picAddr=" + picAddr + ", specIds=" + specIds
				+ ", price=" + price + ", specName=" + specName + "]";
	}
	

}
