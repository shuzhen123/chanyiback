/**  
* @Title: GoodsCartModels.java
* @Package dianfan.entities.goodscart
* @Description: TODO
* @author yl
* @date 2018年7月4日 下午5:03:51
* @version V1.0  
*/ 
package dianfan.entities.goodscart;

import java.util.Arrays;

/** @ClassName GoodsCartModels
 * @Description 
 * @author yl
 * @date 2018年7月4日 下午5:03:51
 */
public class GoodsCartModels {
	
	private String id;
	private String[] goodsspecids;
	private String userid;
	private String goodsid;
	private String goodspriceid;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String[] getGoodsspecids() {
		return goodsspecids;
	}
	public void setGoodsspecids(String[] goodsspecids) {
		this.goodsspecids = goodsspecids;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getGoodsid() {
		return goodsid;
	}
	public void setGoodsid(String goodsid) {
		this.goodsid = goodsid;
	}
	public String getGoodspriceid() {
		return goodspriceid;
	}
	public void setGoodspriceid(String goodspriceid) {
		this.goodspriceid = goodspriceid;
	}
	@Override
	public String toString() {
		return "GoodsCartModels [id=" + id + ", goodsspecids=" + Arrays.toString(goodsspecids) + ", userid=" + userid
				+ ", goodsid=" + goodsid + ", goodspriceid=" + goodspriceid + "]";
	}

}
