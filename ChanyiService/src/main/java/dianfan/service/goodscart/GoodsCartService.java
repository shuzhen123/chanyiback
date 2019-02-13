/**  
* @Title: GoodsCartService.java
* @Package dianfan.service.goodscart
* @Description: TODO
* @author yl
* @date 2018年7月3日 下午3:50:44
* @version V1.0  
*/ 
package dianfan.service.goodscart;

import dianfan.models.ResultBean;

/** @ClassName GoodsCartService
 * @Description 
 * @author yl
 * @date 2018年7月3日 下午3:50:44
 */
public interface GoodsCartService {
	
	/**
	 * @Title: addShoppingCart
	 * @Description: 
	 * @param userid 用户id
	 * @param goodsid 商品id
	 * @param num 数量
	 * @param unit 单位
	 * @param picaddr 图片存储地址 此地址对应床垫成品图
	 * @param picpcmobile 图片类型01：PC端图片 02：Mobile端图片类型
	 * @throws:
	 * @time: 2018年7月3日 下午4:11:34
	 */
	void addShoppingCart(String userid,String goodsid,String goodspriceid,Integer num);
	
	/**
	 * @Title: findShoppingCartList
	 * @Description: 
	 * @param userid 用户id
	 * @return 购物车列表
	 * @throws:
	 * @time: 2018年7月3日 下午8:03:48
	 */
	ResultBean findShoppingCartList(String userid,Integer page,Integer pagecounts);
	/**
	 * @Title: delShoppingCart
	 * @Description: 
	 * @param userid 用户id
	 * @param goodscartid 购物车 
	 * @throws:
	 * @time: 2018年7月5日 下午2:27:47
	 */
	void delShoppingCart(String userid,String goodscartid);
	
	/**
	 * @Title: updateShoppingCartGoodsNum
	 * @Description: 
	 * @param userid
	 * @param goodscartid
	 * @throws:
	 * @time: 2018年7月5日 下午2:48:58
	 */
	void updateShoppingCartGoodsNum(String userid,String goodscartid,String num);
	/**
	 * @Title: updateShoppingCartGoodsNum
	 * @Description: 
	 * @param userid 用户id
	 * @param goodscartid 购物车商品id
	 * @param specid 商品规格id
	 * @throws:
	 * @time: 2018年7月5日 下午3:06:24
	 */
	void updateShoppingCartGoodsSpec(String userid,String goodscartid,String goodspriceid);
	
	/**
	 * @Title: getGoodsCartNums
	 * @Description: 获取购物车商品数
	 * @param userid 用户id
	 * @return
	 * @throws:
	 * @time: 2018年7月13日 上午11:07:33
	 */
	int getGoodsCartNums(String userid,String goodsid,String goodspriceid);
	
}
