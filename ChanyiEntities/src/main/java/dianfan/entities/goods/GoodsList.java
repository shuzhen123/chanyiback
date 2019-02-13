/**  
* @Title: Goods.java
* @Package dianfan.entities.our
* @Description: TODO
* @author yl
* @date 2018年6月30日 上午10:36:16
* @version V1.0  
*/ 
package dianfan.entities.goods;

/**
 * @ClassName GoodsList
 * @Description 商品列表
 * @author cjy
 * @date 2018年7月4日 下午1:53:34
 */
public class GoodsList {
	
	private String id;// varchar(50) NOT NULL COMMENT '主键id'
	private String goodsTitle;// varchar(50) DEFAULT NULL COMMENT '商品标题'
	private Integer isEasySpelling;// tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否易拼(默认否：false)'
	private String goodsTitleEn;//varchar(50) DEFAULT NULL COMMENT '商品英文标题'
	private String goodsDesc;// text COMMENT '商品描述'
	private Double price;// decimal(102) DEFAULT NULL COMMENT '非定制的商品价格'
	private String picAddr;// varchar(2) DEFAULT NULL COMMENT '商品图片'
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getGoodsTitle() {
		return goodsTitle;
	}
	public void setGoodsTitle(String goodsTitle) {
		this.goodsTitle = goodsTitle;
	}
	public Integer getIsEasySpelling() {
		return isEasySpelling;
	}
	public void setIsEasySpelling(Integer isEasySpelling) {
		this.isEasySpelling = isEasySpelling;
	}
	public String getGoodsTitleEn() {
		return goodsTitleEn;
	}
	public void setGoodsTitleEn(String goodsTitleEn) {
		this.goodsTitleEn = goodsTitleEn;
	}
	public String getGoodsDesc() {
		return goodsDesc;
	}
	public void setGoodsDesc(String goodsDesc) {
		this.goodsDesc = goodsDesc;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getPicAddr() {
		return picAddr;
	}
	public void setPicAddr(String picAddr) {
		this.picAddr = picAddr;
	}
	@Override
	public String toString() {
		return "GoodsList [id=" + id + ", goodsTitle=" + goodsTitle + ", isEasySpelling=" + isEasySpelling
				+ ", goodsTitleEn=" + goodsTitleEn + ", goodsDesc=" + goodsDesc + ", price=" + price + ", picAddr="
				+ picAddr + "]";
	}
}
