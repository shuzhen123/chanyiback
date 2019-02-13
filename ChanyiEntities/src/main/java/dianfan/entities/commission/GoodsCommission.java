package dianfan.entities.commission;

import java.math.BigDecimal;
/**
 * @ClassName GoodsCommission
 * @Description 商品佣金折扣信息-新
 * @author cjy
 * @date 2018年8月23日 上午11:08:51
 */
public class GoodsCommission {
	
	private String goodsid; // 商品id
	private BigDecimal price; // 商品原价
	private Integer goodsCount; // 商品数量
	private BigDecimal spgDiscount; // 导购折扣
	private BigDecimal expDiscount; // 体验店折扣
	private BigDecimal cpsDiscount; // 运营服务商折扣
	public String getGoodsid() {
		return goodsid;
	}
	public void setGoodsid(String goodsid) {
		this.goodsid = goodsid;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public Integer getGoodsCount() {
		return goodsCount;
	}
	public void setGoodsCount(Integer goodsCount) {
		this.goodsCount = goodsCount;
	}
	public BigDecimal getSpgDiscount() {
		return spgDiscount;
	}
	public void setSpgDiscount(BigDecimal spgDiscount) {
		this.spgDiscount = spgDiscount;
	}
	public BigDecimal getExpDiscount() {
		return expDiscount;
	}
	public void setExpDiscount(BigDecimal expDiscount) {
		this.expDiscount = expDiscount;
	}
	public BigDecimal getCpsDiscount() {
		return cpsDiscount;
	}
	public void setCpsDiscount(BigDecimal cpsDiscount) {
		this.cpsDiscount = cpsDiscount;
	}
	@Override
	public String toString() {
		return "GoodsCommission [goodsid=" + goodsid + ", price=" + price + ", goodsCount=" + goodsCount
				+ ", spgDiscount=" + spgDiscount + ", expDiscount=" + expDiscount + ", cpsDiscount=" + cpsDiscount
				+ "]";
	}
	
}
