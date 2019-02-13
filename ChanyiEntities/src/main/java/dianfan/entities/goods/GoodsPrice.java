package dianfan.entities.goods;

import java.math.BigDecimal;

/**
 * @ClassName GoodsPrice
 * @Description 商品价格
 * @author cjy
 * @date 2018年7月3日 下午3:04:11
 */
public class GoodsPrice {
	
	private String id; //varchar(50) NOT NULL COMMENT '主键id',
	private String specIds; //商品规格表多个以逗号分隔
	private String goodsPic; //商品图片（以规格小）
	private BigDecimal price; //价格
	private BigDecimal priceEasySpelling; //价格
	
	private String onlyIdentity; //商品价格唯一性标记
	
	public String getOnlyIdentity() {
		return onlyIdentity;
	}
	public void setOnlyIdentity(String onlyIdentity) {
		this.onlyIdentity = onlyIdentity;
	}
	public BigDecimal getPriceEasySpelling() {
		return priceEasySpelling;
	}
	public void setPriceEasySpelling(BigDecimal priceEasySpelling) {
		this.priceEasySpelling = priceEasySpelling;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	@Override
	public String toString() {
		return "GoodsPrice [id=" + id + ", specIds=" + specIds + ", goodsPic=" + goodsPic + ", price=" + price
				+ ", priceEasySpelling=" + priceEasySpelling + "]";
	}


	
	
}
