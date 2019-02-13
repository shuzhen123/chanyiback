package dianfan.entities.goods;

import java.math.BigDecimal;
import java.sql.Timestamp;
/**
 * @ClassName BgGoods
 * @Description 商品数据（后台使用）
 * @author cjy
 * @date 2018年7月19日 上午10:22:58
 */
public class BgGoods {
	private String id;// varchar(50) NOT NULL COMMENT '主键id'
	private String goodsTitle;// varchar(50) DEFAULT NULL COMMENT '商品标题'
	private Integer isEasySpelling;// tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否易拼(默认否：false)'
	private Integer isStaffEasySpelling;// tinyint(1) NOT NULL DEFAULT '1' COMMENT '员工是否可以易拼(默认可：true)'
	private String goodsTitleEn;//varchar(50) DEFAULT NULL COMMENT '商品英文标题'
	
	private String goodsClassifyId;// varchar(50) DEFAULT NULL COMMENT '商品分类id'
	private String goodsClassifyName;// varchar(50) DEFAULT NULL COMMENT '商品分类名称'
	private String goodsClassifyNameEn;// varchar(50) DEFAULT NULL COMMENT '商品分类英文名称'
	
	private String goodsDesc;// text COMMENT '商品描述'
	private BigDecimal price;// decimal(102) DEFAULT NULL COMMENT '非定制的商品价格'
	private BigDecimal priceEasySpelling;// decimal(10,2) DEFAULT NULL COMMENT '易拼拼团显示的商品价格',
	private Integer salesCount;// int(8) DEFAULT NULL COMMENT '销售数量'
	private Integer collectionNum;// int(8) DEFAULT NULL COMMENT '收藏数量'
	private Integer upperlower;// tinyint(1) NOT NULL DEFAULT '0' COMMENT '上下架(默认非上架false)'
	private String goodsCategory;// varchar(2) DEFAULT NULL COMMENT '商品类别（01：非定制 02：定制）'
	
	private Timestamp createTime;// datetime DEFAULT NULL COMMENT '创建时间'
	private String createBy;// varchar(50) DEFAULT NULL COMMENT '创建者id'
	private String createName;// varchar(50) DEFAULT NULL COMMENT '创建者姓名'
	
	private Timestamp updateTime;// datetime DEFAULT NULL COMMENT '更新时间'
	private String updateBy;// varchar(50) DEFAULT NULL COMMENT '更新者id'
	private String updateName;// varchar(50) DEFAULT NULL COMMENT '更新者姓名'
	
	private Integer entkbn;// int(1) DEFAULT '0' COMMENT '数据有效区分(0:数据有效1:数据无效9:逻辑删除)'

	private String thumbPic;// varchar(50) DEFAULT NULL COMMENT '商品缩略图'
	
	private String sort;// varchar(50) DEFAULT NULL COMMENT '用户商品排序'
	
	private String productSku; //varchar(50) DEFAULT NULL COMMENT '商品编码',
	private String isFragile; //varchar(1) DEFAULT NULL COMMENT '是否易碎（1：是2：否）',
	private String isCod; //varchar(1) DEFAULT NULL COMMENT '是否货到付款（1：是 0：否）',
	private String receiveable; //varchar(50) DEFAULT NULL COMMENT '代收金额',
	private String onDoorPickUp; //varchar(1) DEFAULT NULL COMMENT '上门揽件标记（1：是2：否）',
	private String isGuarantee; //varchar(1) DEFAULT NULL COMMENT '是否保价（1：是2：否）',
	private String guaranteeMoney; //varchar(50) DEFAULT NULL COMMENT '保价金额',
	private String weight; //varchar(50) DEFAULT NULL COMMENT '重量（kg）',
	private String length; //varchar(50) DEFAULT NULL COMMENT '长度(mm)',
	private String width; //varchar(50) DEFAULT NULL COMMENT '宽度(mm)',
	private String height; //varchar(50) DEFAULT NULL COMMENT '高度(mm)',
	private String installFlag; //varchar(1) DEFAULT NULL COMMENT '是否安维',
	private String thridCategoryNo; //varchar(20) DEFAULT NULL COMMENT '三级分类编码（安维必填）',
	private String brandNo; //varchar(50) DEFAULT NULL COMMENT '品牌id',
	
	private BigDecimal spgSaleDiscount;// decimal(102) DEFAULT NULL COMMENT '导购折扣'
	private BigDecimal expSaleDiscount;// decimal(102) DEFAULT NULL COMMENT '体验店折扣'
	private BigDecimal cpsSaleDiscount;// decimal(102) DEFAULT NULL COMMENT '运营服务商折扣'
	
	
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
	public String getGoodsClassifyName() {
		return goodsClassifyName;
	}
	public void setGoodsClassifyName(String goodsClassifyName) {
		this.goodsClassifyName = goodsClassifyName;
	}
	public String getGoodsClassifyNameEn() {
		return goodsClassifyNameEn;
	}
	public void setGoodsClassifyNameEn(String goodsClassifyNameEn) {
		this.goodsClassifyNameEn = goodsClassifyNameEn;
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
	public BigDecimal getPriceEasySpelling() {
		return priceEasySpelling;
	}
	public void setPriceEasySpelling(BigDecimal priceEasySpelling) {
		this.priceEasySpelling = priceEasySpelling;
	}
	public Integer getSalesCount() {
		return salesCount;
	}
	public void setSalesCount(Integer salesCount) {
		this.salesCount = salesCount;
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
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public String getCreateName() {
		return createName;
	}
	public void setCreateName(String createName) {
		this.createName = createName;
	}
	public Timestamp getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	public String getUpdateName() {
		return updateName;
	}
	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}
	public Integer getEntkbn() {
		return entkbn;
	}
	public void setEntkbn(Integer entkbn) {
		this.entkbn = entkbn;
	}
	public String getThumbPic() {
		return thumbPic;
	}
	public void setThumbPic(String thumbPic) {
		this.thumbPic = thumbPic;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getProductSku() {
		return productSku;
	}
	public void setProductSku(String productSku) {
		this.productSku = productSku;
	}
	public String getIsFragile() {
		return isFragile;
	}
	public void setIsFragile(String isFragile) {
		this.isFragile = isFragile;
	}
	public String getIsCod() {
		return isCod;
	}
	public void setIsCod(String isCod) {
		this.isCod = isCod;
	}
	public String getReceiveable() {
		return receiveable;
	}
	public void setReceiveable(String receiveable) {
		this.receiveable = receiveable;
	}
	public String getOnDoorPickUp() {
		return onDoorPickUp;
	}
	public void setOnDoorPickUp(String onDoorPickUp) {
		this.onDoorPickUp = onDoorPickUp;
	}
	public String getIsGuarantee() {
		return isGuarantee;
	}
	public void setIsGuarantee(String isGuarantee) {
		this.isGuarantee = isGuarantee;
	}
	public String getGuaranteeMoney() {
		return guaranteeMoney;
	}
	public void setGuaranteeMoney(String guaranteeMoney) {
		this.guaranteeMoney = guaranteeMoney;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getLength() {
		return length;
	}
	public void setLength(String length) {
		this.length = length;
	}
	public String getWidth() {
		return width;
	}
	public void setWidth(String width) {
		this.width = width;
	}
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	public String getInstallFlag() {
		return installFlag;
	}
	public void setInstallFlag(String installFlag) {
		this.installFlag = installFlag;
	}
	public String getThridCategoryNo() {
		return thridCategoryNo;
	}
	public void setThridCategoryNo(String thridCategoryNo) {
		this.thridCategoryNo = thridCategoryNo;
	}
	public String getBrandNo() {
		return brandNo;
	}
	public void setBrandNo(String brandNo) {
		this.brandNo = brandNo;
	}
	@Override
	public String toString() {
		return "BgGoods [id=" + id + ", goodsTitle=" + goodsTitle + ", isEasySpelling=" + isEasySpelling
				+ ", isStaffEasySpelling=" + isStaffEasySpelling + ", goodsTitleEn=" + goodsTitleEn
				+ ", goodsClassifyId=" + goodsClassifyId + ", goodsClassifyName=" + goodsClassifyName
				+ ", goodsClassifyNameEn=" + goodsClassifyNameEn + ", goodsDesc=" + goodsDesc + ", price=" + price
				+ ", priceEasySpelling=" + priceEasySpelling + ", salesCount=" + salesCount + ", collectionNum="
				+ collectionNum + ", upperlower=" + upperlower + ", goodsCategory=" + goodsCategory + ", createTime="
				+ createTime + ", createBy=" + createBy + ", createName=" + createName + ", updateTime=" + updateTime
				+ ", updateBy=" + updateBy + ", updateName=" + updateName + ", entkbn=" + entkbn + ", thumbPic="
				+ thumbPic + ", sort=" + sort + ", productSku=" + productSku + ", isFragile=" + isFragile + ", isCod="
				+ isCod + ", receiveable=" + receiveable + ", onDoorPickUp=" + onDoorPickUp + ", isGuarantee="
				+ isGuarantee + ", guaranteeMoney=" + guaranteeMoney + ", weight=" + weight + ", length=" + length
				+ ", width=" + width + ", height=" + height + ", installFlag=" + installFlag + ", thridCategoryNo="
				+ thridCategoryNo + ", brandNo=" + brandNo + "]";
	}

}