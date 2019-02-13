package dianfan.entities.goods;

import java.sql.Timestamp;

public class GoodsPicsQx {

	private String picId; //varchar(50) NOT NULL COMMENT '图片ID',
	private String goodsId; //varchar(50) DEFAULT NULL COMMENT '商品ID',
	private String picType; //varchar(2) DEFAULT NULL COMMENT '图片类型\01：商品缩略图\\ 分类list页面\02：商品内容详情图片\\ 商品详情banner轮播图\03：详情图',
	private int picSort; //int(3) DEFAULT NULL COMMENT '图片排序',
	private String picAddr; //varchar(255) DEFAULT NULL COMMENT '图片存储地址',
	private String picPcMobile; //varchar(2) DEFAULT NULL COMMENT '图片类型\01：PC端图片\02：Mobile端图片类型',
	private Timestamp createTime; //datetime DEFAULT NULL COMMENT '创建时间',
	private String createBy; //varchar(50) DEFAULT NULL COMMENT '创建者',
	private Timestamp updateTime; //timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
	private String updateBy; //varchar(50) DEFAULT NULL COMMENT '更新者',
	private int entkbn; //int(1) DEFAULT '0' COMMENT '数据有效区分(0:数据有效1:数据无效9:逻辑删除)',
	private int version; //int(11) NOT NULL DEFAULT '0' COMMENT '版本号',
	public String getPicId() {
		return picId;
	}
	public void setPicId(String picId) {
		this.picId = picId;
	}
	public String getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	public String getPicType() {
		return picType;
	}
	public void setPicType(String picType) {
		this.picType = picType;
	}
	public int getPicSort() {
		return picSort;
	}
	public void setPicSort(int picSort) {
		this.picSort = picSort;
	}
	public String getPicAddr() {
		return picAddr;
	}
	public void setPicAddr(String picAddr) {
		this.picAddr = picAddr;
	}
	public String getPicPcMobile() {
		return picPcMobile;
	}
	public void setPicPcMobile(String picPcMobile) {
		this.picPcMobile = picPcMobile;
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
	public int getEntkbn() {
		return entkbn;
	}
	public void setEntkbn(int entkbn) {
		this.entkbn = entkbn;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	@Override
	public String toString() {
		return "GoodsPics [picId=" + picId + ", goodsId=" + goodsId + ", picType=" + picType + ", picSort=" + picSort
				+ ", picAddr=" + picAddr + ", picPcMobile=" + picPcMobile + ", createTime=" + createTime + ", createBy="
				+ createBy + ", updateTime=" + updateTime + ", updateBy=" + updateBy + ", entkbn=" + entkbn
				+ ", version=" + version + "]";
	}
	
}
