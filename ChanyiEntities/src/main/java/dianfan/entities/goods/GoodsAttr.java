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
 * @ClassName GoodsAttr
 * @Description 商品属性列表
 * @author cjy
 * @date 2018年7月4日 下午3:40:57
 */
public class GoodsAttr {
	private String id; // 
	private String paramKey; // 商品属性key
	private String paramName; // 商品属性名称
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getParamKey() {
		return paramKey;
	}
	public void setParamKey(String paramKey) {
		this.paramKey = paramKey;
	}
	public String getParamName() {
		return paramName;
	}
	public void setParamName(String paramName) {
		this.paramName = paramName;
	}
	@Override
	public String toString() {
		return "GoodsAttr [id=" + id + ", paramKey=" + paramKey + ", paramName=" + paramName + "]";
	}
}
