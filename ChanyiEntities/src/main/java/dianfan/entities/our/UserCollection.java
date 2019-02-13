/**  
* @Title: Collection.java
* @Package dianfan.entities.our
* @Description: TODO
* @author yl
* @date 2018年6月30日 上午10:17:08
* @version V1.0  
*/ 
package dianfan.entities.our;

/** @ClassName Collection
 * @Description 
 * @author yl
 * @date 2018年6月30日 上午10:17:08
 */
public class UserCollection {
	
	private String id;// varchar(50) NOT NULL COMMENT '主键id'
	private String goodsId;// varchar(50) NOT NULL COMMENT '商品id'
	private String userId;// varchar(50) NOT NULL COMMENT '用户id'
	private Integer entkbn;// int(1) NOT NULL DEFAULT '0' COMMENT '数据有效区分(0:数据有效1:数据无效9:逻辑删除)'
	
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
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Integer getEntkbn() {
		return entkbn;
	}
	public void setEntkbn(Integer entkbn) {
		this.entkbn = entkbn;
	}
	@Override
	public String toString() {
		return "Collection [id=" + id + ", goodsId=" + goodsId + ", userId=" + userId + ", entkbn=" + entkbn + "]";
	}

}
