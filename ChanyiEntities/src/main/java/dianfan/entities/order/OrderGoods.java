package dianfan.entities.order;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import dianfan.entities.goods.GoodsSpec;

/**
 * @ClassName OrderGoods
 * @Description 订单中的商品表
 * @author sz
 * @date 2018年7月5日 下午4:26:52
 */
public class OrderGoods {

	private String id; //varchar(50) NOT NULL COMMENT '主键id',
	private String orderId; //varchar(50) NOT NULL COMMENT '订单号',
	
	private String goodsPriceId; // 商品价格表id  通过这个去价格表中获取对应的规格返回
	
	private String goodsId; //varchar(50) DEFAULT NULL COMMENT '商品id',
	private BigDecimal unitPrice; //decimal(10,2) DEFAULT NULL COMMENT '商品单价金额',
	private int num; //int(9) DEFAULT NULL COMMENT '商品数量',
	private String picAddr; //varchar(255) DEFAULT NULL COMMENT '图片存储地址',
	private String name; //varchar(50) DEFAULT NULL COMMENT '名称',
	private Timestamp createTime; //datetime DEFAULT NULL COMMENT '创建时间',
	private String createBy; //varchar(50) DEFAULT NULL COMMENT '创建者',
	private Timestamp updateTime; //timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
	private String updateBy; //varchar(50) DEFAULT NULL COMMENT '更新者',
	private int version; //int(11) NOT NULL DEFAULT '0' COMMENT '版本号',
	
	private String specIds; // 规格id s
	
	private String specList; // 规格返回数据
	
	private List<GoodsSpec> goodsSpec; // 商品的分类和规格 
	
	private BigDecimal spgSaleDiscount;// decimal(102) DEFAULT NULL COMMENT '导购折扣'
	private BigDecimal expSaleDiscount;// decimal(102) DEFAULT NULL COMMENT '体验店折扣'
	private BigDecimal cpsSaleDiscount;// decimal(102) DEFAULT NULL COMMENT '运营服务商折扣'
	

	
	public String getSpecIds() {
		return specIds;
	}
	public void setSpecIds(String specIds) {
		this.specIds = specIds;
	}
	public String getSpecList() {
		return specList;
	}
	public void setSpecList(String specList) {
		this.specList = specList;
	}
	public List<GoodsSpec> getGoodsSpec() {
		return goodsSpec;
	}
	public void setGoodsSpec(List<GoodsSpec> goodsSpec) {
		this.goodsSpec = goodsSpec;
	}
	public String getGoodsPriceId() {
		return goodsPriceId;
	}
	public void setGoodsPriceId(String goodsPriceId) {
		this.goodsPriceId = goodsPriceId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	public BigDecimal getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getPicAddr() {
		return picAddr;
	}
	public void setPicAddr(String picAddr) {
		this.picAddr = picAddr;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
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
		return "OrderGoods [id=" + id + ", orderId=" + orderId + ", goodsPriceId=" + goodsPriceId + ", goodsId="
				+ goodsId + ", unitPrice=" + unitPrice + ", num=" + num + ", picAddr=" + picAddr + ", name=" + name
				+ ", createTime=" + createTime + ", createBy=" + createBy + ", updateTime=" + updateTime + ", updateBy="
				+ updateBy + ", version=" + version + ", specIds=" + specIds + ", specList=" + specList + ", goodsSpec="
				+ goodsSpec + ", spgSaleDiscount=" + spgSaleDiscount + ", expSaleDiscount=" + expSaleDiscount
				+ ", cpsSaleDiscount=" + cpsSaleDiscount + "]";
	}
	
}
