package dianfan.entities.goods;

/**
 * @ClassName GoodsListLabels
 * @Description 商品商品标签相关表
 * @author sz
 * @date 2018年7月9日 上午11:25:34
 */
public class GoodsListLabels {
	
	private String id; //varchar(50) NOT NULL COMMENT '主键id',
	private String goodsId; //varchar(50) DEFAULT NULL COMMENT '商品id',
	private String labelId; //varchar(50) DEFAULT NULL COMMENT '标签id',
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
	public String getLabelId() {
		return labelId;
	}
	public void setLabelId(String labelId) {
		this.labelId = labelId;
	}
	@Override
	public String toString() {
		return "GoodsListLabels [id=" + id + ", goodsId=" + goodsId + ", labelId=" + labelId + "]";
	}
	
	
	

}
