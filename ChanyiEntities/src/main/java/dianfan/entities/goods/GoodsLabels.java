package dianfan.entities.goods;

import java.sql.Timestamp;

/**
 * @ClassName GoodsLabels
 * @Description 商品标签 
 * @author sz
 * @date 2018年7月9日 上午10:12:01
 */
public class GoodsLabels {

	private String id; //varchar(50) NOT NULL COMMENT '主键id',
	private String labelName; //varchar(50) NOT NULL COMMENT '标签名称',
	private Timestamp createTime; //varchar(50) NOT NULL COMMENT '创建时间',
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLabelName() {
		return labelName;
	}
	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	@Override
	public String toString() {
		return "GoodsLabels [id=" + id + ", labelName=" + labelName + ", createTime=" + createTime + "]";
	}
}
