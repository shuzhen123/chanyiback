package dianfan.entities.goods;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

/**
 * @ClassName Goods
 * @Description 商品表 list
 * @author sz
 * @date 2018年7月3日 下午12:47:01
 */
public class GoodsModels {
	
	private String id; //varchar(50) NOT NULL COMMENT '主键id',
	private String goodsTitle; //varchar(50) DEFAULT NULL COMMENT '商品标题',
	
	private String goodsType; //商品所属的分类
	
	private Integer isEasySpelling; //tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否易拼(默认否：false)',
	
	private BigDecimal priceEasySpelling; //易拼拼团显示的商品价格
	
	private Integer isStaffEasySpelling; //tinyint(1) '1' COMMENT '员工是否可以易拼(默认可：true)',
	private String goodsTitleEn; //varchar(50) DEFAULT NULL COMMENT '商品英文标题',
	private String goodsClassifyId; //varchar(50) DEFAULT NULL COMMENT '商品分类id',
	private String goodsDesc; //text COMMENT '商品描述',
	private BigDecimal price; //decimal(10,2) DEFAULT NULL COMMENT '非定制的商品价格',
	private int collectionNum; //int(8) DEFAULT NULL COMMENT '收藏数量',
	private Integer upperlower; //tinyint(1) NOT NULL DEFAULT '0' COMMENT '上下架(默认非上架false)',
	private String goodsCategory; //varchar(2) DEFAULT NULL COMMENT '商品类别（01：非定制 02：定制）',
	private Timestamp createTime; //datetime DEFAULT NULL COMMENT '创建时间',
	private String createBy; //varchar(50) DEFAULT NULL COMMENT '创建者',
	private Timestamp updateTime; //timestamp NOT NULL DEFAULT CURRENT_TIMES'更新时间',
	private String updateBy; //varchar(50) DEFAULT NULL COMMENT '更新者',
	private Integer version; //int(11) NOT NULL DEFAULT '0' COMMENT '版本号',
	
	/*  --列表显示 需要的参数 --   */
	private List<GoodsPics> goodsPics; //商品的轮播图
	private List<GoodsPicsQx> goodsDetails; //商品的详情图
	
	private String goodsThumbnail; //商品的缩略图
	
	private Integer collectFlag; //商品是否是收藏的状态
	/* --商品标签-- */
	//private String labelIdS; //商品的标签
	private String labelName; //商品的标签ids对应的名称
	
	
	
	public String getGoodsType() {
		return goodsType;
	}
	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}
	public String getGoodsThumbnail() {
		return goodsThumbnail;
	}
	public void setGoodsThumbnail(String goodsThumbnail) {
		this.goodsThumbnail = goodsThumbnail;
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
	public int getCollectionNum() {
		return collectionNum;
	}
	public void setCollectionNum(int collectionNum) {
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
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	public List<GoodsPics> getGoodsPics() {
		return goodsPics;
	}
	public void setGoodsPics(List<GoodsPics> goodsPics) {
		this.goodsPics = goodsPics;
	}
	public Integer getCollectFlag() {
		return collectFlag;
	}
	public void setCollectFlag(Integer collectFlag) {
		this.collectFlag = collectFlag;
	}
	

	public String getLabelName() {
		return labelName;
	}
	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}
	public BigDecimal getPriceEasySpelling() {
		return priceEasySpelling;
	}
	public void setPriceEasySpelling(BigDecimal priceEasySpelling) {
		this.priceEasySpelling = priceEasySpelling;
	}


	public List<GoodsPicsQx> getGoodsDetails() {
		return goodsDetails;
	}
	public void setGoodsDetails(List<GoodsPicsQx> goodsDetails) {
		this.goodsDetails = goodsDetails;
	}
	@Override
	public String toString() {
		return "GoodsModels [id=" + id + ", goodsTitle=" + goodsTitle + ", goodsType=" + goodsType + ", isEasySpelling="
				+ isEasySpelling + ", priceEasySpelling=" + priceEasySpelling + ", isStaffEasySpelling="
				+ isStaffEasySpelling + ", goodsTitleEn=" + goodsTitleEn + ", goodsClassifyId=" + goodsClassifyId
				+ ", goodsDesc=" + goodsDesc + ", price=" + price + ", collectionNum=" + collectionNum + ", upperlower="
				+ upperlower + ", goodsCategory=" + goodsCategory + ", createTime=" + createTime + ", createBy="
				+ createBy + ", updateTime=" + updateTime + ", updateBy=" + updateBy + ", version=" + version
				+ ", goodsPics=" + goodsPics + ", goodsDetails=" + goodsDetails + ", goodsThumbnail=" + goodsThumbnail
				+ ", collectFlag=" + collectFlag + ", labelName=" + labelName + "]";
	}


	

	

	



	
}
