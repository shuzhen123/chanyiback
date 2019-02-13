package dianfan.entities.goods;

/**
 * @ClassName GoodsSpec
 * @Description 商品分类
 * @author cjy
 * @date 2018年7月3日 下午2:54:31
 */
public class GoodsSpec {
	
	private String id; //varchar(50) NOT NULL COMMENT '主键id',
	private String specName; //规格名称
	private String specCatagory; //规格分类
	//--------------------------------------
	private String typeAndName; //分类对应规格
	
	
	public String getTypeAndName() {
		return typeAndName;
	}
	public void setTypeAndName(String typeAndName) {
		this.typeAndName = typeAndName;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSpecName() {
		return specName;
	}
	public void setSpecName(String specName) {
		this.specName = specName;
	}
	public String getSpecCatagory() {
		return specCatagory;
	}
	public void setSpecCatagory(String specCatagory) {
		this.specCatagory = specCatagory;
	}
	@Override
	public String toString() {
		return "GoodsSpec [id=" + id + ", specName=" + specName + ", specCatagory=" + specCatagory + ", typeAndName="
				+ typeAndName + "]";
	}
	
	
}
