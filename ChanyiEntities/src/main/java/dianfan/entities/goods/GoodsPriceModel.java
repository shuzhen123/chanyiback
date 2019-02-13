/**  
* @Title: GoodsPriceModel.java
* @Package dianfan.entities.goods
* @Description: TODO
* @author yl
* @date 2018年7月4日 上午9:55:58
* @version V1.0  
*/ 
package dianfan.entities.goods;

import java.math.BigDecimal;
import java.sql.Timestamp;

/** @ClassName GoodsPriceModel
 * @Description 
 * @author yl
 * @date 2018年7月4日 上午9:55:58
 */
public class GoodsPriceModel {
	
	private String id; //varchar(50) NOT NULL COMMENT '主键id',
	private String goodsId;//商品id
	private String specIds; //商品规格表多个以逗号分隔
	private String goodsPic; //商品图片（以规格小）
	private BigDecimal price; //价格
	private String specName;//规格名
	private Timestamp createTime;//创建时间
	private BigDecimal priceEasySpelling; //拼团价格
	private BigDecimal spgSaleDiscount;//导购折扣【2018/08/15 ADD】
	private BigDecimal expSaleDiscount;//体验店折扣【2018/08/15 ADD】
	private BigDecimal cpsSaleDiscount;//运营服务商折扣【2018/08/15 ADD】
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	public String getSpecIds() {
		return specIds;
	}
	public void setSpecIds(String specIds) {
		this.specIds = specIds;
	}
	public String getGoodsPic() {
		return goodsPic;
	}
	public void setGoodsPic(String goodsPic) {
		this.goodsPic = goodsPic;
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
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public BigDecimal getPriceEasySpelling() {
		return priceEasySpelling;
	}
	public void setPriceEasySpelling(BigDecimal priceEasySpelling) {
		this.priceEasySpelling = priceEasySpelling;
	}
	public BigDecimal getSpgSaleDiscount() {
		return spgSaleDiscount;
	}
	public void setSpgSaleDiscount(BigDecimal spgSaleDiscount) {
		this.spgSaleDiscount = spgSaleDiscount;
	}
	public BigDecimal getExpSaleDiscount() {
		return expSaleDiscount;
	}
	public void setExpSaleDiscount(BigDecimal expSaleDiscount) {
		this.expSaleDiscount = expSaleDiscount;
	}
	public BigDecimal getCpsSaleDiscount() {
		return cpsSaleDiscount;
	}
	public void setCpsSaleDiscount(BigDecimal cpsSaleDiscount) {
		this.cpsSaleDiscount = cpsSaleDiscount;
	}
	@Override
	public String toString() {
		return "GoodsPriceModel [id=" + id + ", goodsId=" + goodsId + ", specIds=" + specIds + ", goodsPic=" + goodsPic
				+ ", price=" + price + ", specName=" + specName + ", createTime=" + createTime + ", priceEasySpelling="
				+ priceEasySpelling + ", spgSaleDiscount=" + spgSaleDiscount + ", expSaleDiscount=" + expSaleDiscount
				+ ", cpsSaleDiscount=" + cpsSaleDiscount + "]";
	}
	
}
