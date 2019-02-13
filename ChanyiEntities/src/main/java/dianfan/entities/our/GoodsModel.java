/**  
* @Title: GoodsModel.java
* @Package dianfan.entities.our
* @Description: TODO
* @author yl
* @date 2018年6月30日 上午10:56:21
* @version V1.0  
*/ 
package dianfan.entities.our;

import java.math.BigDecimal;
import java.sql.Timestamp;

/** @ClassName GoodsModel
 * @Description 
 * @author yl
 * @date 2018年6月30日 上午10:56:21
 */
public class GoodsModel {
	
	private String id;// varchar(50) NOT NULL COMMENT '主键id'
	private String goodsTitle;// varchar(50) DEFAULT NULL COMMENT '商品标题'
	private Integer isEasySpelling;// tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否易拼(默认否：false)'
	private Integer isStaffEasySpelling;// tinyint(1) NOT NULL DEFAULT '1' COMMENT '员工是否可以易拼(默认可：true)'
	private String goodsTitleEn;//varchar(50) DEFAULT NULL COMMENT '商品英文标题'
	private String goodsClassifyId;// varchar(50) DEFAULT NULL COMMENT '商品分类id'
	private String goodsDesc;// text COMMENT '商品描述'
	private BigDecimal price;// decimal(102) DEFAULT NULL COMMENT '非定制的商品价格'
	private Integer collectionNum;// int(8) DEFAULT NULL COMMENT '收藏数量'
	private Integer upperlower;// tinyint(1) NOT NULL DEFAULT '0' COMMENT '上下架(默认非上架false)'
	private String goodsCategory;// varchar(2) DEFAULT NULL COMMENT '商品类别（01：非定制 02：定制）'
	private Timestamp createTime;// datetime DEFAULT NULL COMMENT '创建时间'
	private String entkbn;// int(1) DEFAULT '0' COMMENT '数据有效区分(0:数据有效1:数据无效9:逻辑删除)'
	private String picAddr; //商品图片
	private BigDecimal priceEasySpelling;
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
	public Integer getIsStaffEasySpelling() {
		return isStaffEasySpelling;
	}
	public void setIsStaffEasySpelling(Integer isStaffEasySpelling) {
		this.isStaffEasySpelling = isStaffEasySpelling;
	}
	public String getGoodsTitleEn() {
		return goodsTitleEn;
	}
	public void setGoodsTitleEn(String goodsTitleEn) {
		this.goodsTitleEn = goodsTitleEn;
	}
	public String getGoodsClassifyId() {
		return goodsClassifyId;
	}
	public void setGoodsClassifyId(String goodsClassifyId) {
		this.goodsClassifyId = goodsClassifyId;
	}
	public String getGoodsDesc() {
		return goodsDesc;
	}
	public void setGoodsDesc(String goodsDesc) {
		this.goodsDesc = goodsDesc;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public Integer getCollectionNum() {
		return collectionNum;
	}
	public void setCollectionNum(Integer collectionNum) {
		this.collectionNum = collectionNum;
	}
	public Integer getUpperlower() {
		return upperlower;
	}
	public void setUpperlower(Integer upperlower) {
		this.upperlower = upperlower;
	}
	public String getGoodsCategory() {
		return goodsCategory;
	}
	public void setGoodsCategory(String goodsCategory) {
		this.goodsCategory = goodsCategory;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public String getEntkbn() {
		return entkbn;
	}
	public void setEntkbn(String entkbn) {
		this.entkbn = entkbn;
	}
	public String getPicAddr() {
		return picAddr;
	}
	public void setPicAddr(String picAddr) {
		this.picAddr = picAddr;
	}
	public BigDecimal getPriceEasySpelling() {
		return priceEasySpelling;
	}
	public void setPriceEasySpelling(BigDecimal priceEasySpelling) {
		this.priceEasySpelling = priceEasySpelling;
	}
	@Override
	public String toString() {
		return "GoodsModel [id=" + id + ", goodsTitle=" + goodsTitle + ", isEasySpelling=" + isEasySpelling
				+ ", isStaffEasySpelling=" + isStaffEasySpelling + ", goodsTitleEn=" + goodsTitleEn
				+ ", goodsClassifyId=" + goodsClassifyId + ", goodsDesc=" + goodsDesc + ", price=" + price
				+ ", collectionNum=" + collectionNum + ", upperlower=" + upperlower + ", goodsCategory=" + goodsCategory
				+ ", createTime=" + createTime + ", entkbn=" + entkbn + ", picAddr=" + picAddr + ", priceEasySpelling="
				+ priceEasySpelling + "]";
	}

}
