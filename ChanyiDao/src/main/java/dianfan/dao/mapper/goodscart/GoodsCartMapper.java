/**  
* @Title: GoodsCartMapper.java
* @Package dianfan.dao.mapper.goodscart
* @Description: TODO
* @author yl
* @date 2018年7月3日 下午4:16:40
* @version V1.0  
*/ 
package dianfan.dao.mapper.goodscart;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import dianfan.entities.goods.GoodsPics;
import dianfan.entities.goods.GoodsPriceModel;
import dianfan.entities.goodscart.GoodsCartModel;
import dianfan.entities.goodscart.GoodsCartModels;
import dianfan.entities.our.GoodsModel;

/** @ClassName GoodsCartMapper
 * @Description 
 * @author yl
 * @date 2018年7月3日 下午4:16:40
 */
@Repository
public interface GoodsCartMapper {
	
	/**
	 * @Title: addShoppingCart
	 * @Description: 
	 * @param gcm 购物车信息
	 * @throws:
	 * @time: 2018年7月3日 下午4:11:34
	 */
	void addShoppingCart(GoodsCartModel gcm);
	
	/**
	 * @Title: findGoodsCart
	 * @Description: 
	 * @param userid 用户id
	 * @return List<GoodsCartModel>
	 * @throws:
	 * @time: 2018年7月3日 下午8:07:44
	 */
	List<GoodsCartModel> findGoodsCart(String userid);
	
	/**
	 * @Title: getGoodsInfo
	 * @Description: 
	 * @param goodsid
	 * @return
	 * @throws:
	 * @time: 2018年7月3日 下午8:33:02
	 */
	GoodsModel getGoodsInfo(String goodsid);
	/**
	 * @Title: getGoodsInfomation
	 * @Description:  获取商品信息
	 * @param goodsid 商品id
	 * @return
	 * @throws:
	 * @time: 2018年7月9日 下午5:02:56
	 */
	GoodsModel getGoodsInfomation(String goodsid);
	/**
	 * @Title: getGoodsPriceInfo
	 * @Description: 获取商品价格表信息
	 * @param goodsid 商品id
	 * @return GoodsPriceModel
	 * @throws:
	 * @time: 2018年7月4日 上午9:59:27
	 */
	GoodsPriceModel getGoodsPriceInfo(String goodspriceid);
	/**
	 * @Title: getGoodsPicInfo
	 * @Description: 获取商品图片信息
	 * @param goodsid 商品id
	 * @return GoodsPics
	 * @throws:
	 * @time: 2018年7月4日 上午10:26:10
	 */
	GoodsPics getGoodsPicInfo(GoodsPics gp);
	/**
	 * @Title: findShoppingCartList
	 * @Description: 
	 * @param userid 用户id
	 * @return 购物车列表
	 * @throws:
	 * @time: 2018年7月3日 下午8:03:48
	 */
	List<GoodsCartModel> findShoppingCartList(Map<String, Object> params);
	
	/**
	 * @Title: getShoppingCarGoodsInfo
	 * @Description: 根据购物车id 查询购物车商品信息
	 * @param goodscartid
	 * @return
	 * @throws:
	 * @time: 2018年7月5日 下午3:16:55
	 */
	GoodsCartModel getShoppingCarGoodsInfo(String goodscartid);
	
	/**
	 * @Title: findGoodsList
	 * @Description: 
	 * @param Goodsids
	 * @return 商品列表
	 * @throws:
	 * @time: 2018年6月28日 下午3:38:20
	 */
	List<GoodsModel> findGoodsList(@Param("goodsids") List<String> goodsids);
	/**
	 * @Title: findGoodsPriceList
	 * @Description: 
	 * @param goodspriceids 价格表id
	 * @return 商品价格列表
	 * @throws:
	 * @time: 2018年7月4日 上午11:30:52
	 */
	List<GoodsPriceModel> findGoodsPriceList(@Param("goodspriceids") List<String> goodspriceids);
	/**
	 * @Title: findGoodsSpecList
	 * @Description: 
	 * @param goodsspecids 商品规格id
	 * @return 规格信息列表
	 * @throws:
	 * @time: 2018年7月4日 下午12:44:21
	 */
	GoodsCartModel getGoodsCartSpecList(GoodsCartModels goodsCartModels);
	/**
	 * @Title: getGoodsCartByUserGidGpid
	 * @Description: 根据userid 、goodsid、goodpriceid 查询购物车中商品的信息
	 * @param goodsCartModels
	 * @return
	 * @throws:
	 * @time: 2018年7月5日 上午10:48:28
	 */
	GoodsCartModel getGoodsCartByUserGidGpid(GoodsCartModels goodsCartModels);
	/**
	 * @Title: updateGoodsNum
	 * @Description: 
	 * @param id 购物车id
	 * @throws:
	 * @time: 2018年7月5日 上午11:11:34
	 */
	void updateGoodsNum(Map<String, Object> params);
	/**
	 * @Title: delShoppingCart
	 * @Description: 删除购物车
	 * @param goodscartid 购物车id
	 * @throws:
	 * @time: 2018年7月5日 下午2:33:01
	 */
	void delShoppingCart(@Param("goodscartids") String[] goodscartids);
	/**
	 * @Title: updateShoppingCartGoodsSpec
	 * @Description: 修改购物车商品品类
	 * @param goodscartids 购物车id
	 * @throws:
	 * @time: 2018年7月5日 下午3:25:27
	 */
	void updateShoppingCarGoodsSpec(Map<String, Object> params);
	
	/**
	 * @Title: getGoodsCartNum
	 * @Description: 统计购物车商品数量
	 * @param userid 用户id
	 * @return
	 * @throws:
	 * @time: 2018年7月5日 下午4:02:53
	 */
	Integer getGoodsCartNum(String userid); 
	
	/**
	 * @Title: getSingleGoodsNum
	 * @Description:  单件商品数量
	 * @param userid 用户id
	 * @param goodsId 商品id
	 * @param goodsPriceId 多规格商品id
	 * @return
	 * @throws:
	 * @time: 2018年8月25日 上午11:24:58
	 */
	Integer getSingleGoodsNum(@Param("userid") String userid,@Param("goodsId") String goodsId,@Param("goodsPriceId") String goodsPriceId);
	
	/**
	 * @Title: findGoodsCartList
	 * @Description: 根据购物车id  获取购物车列表
	 * @param goodscartid 购物车id
	 * @return 获取 购物车列表
	 * @throws:
	 * @time: 2018年7月7日 下午4:46:34
	 */
	List<GoodsCartModel> findGoodsCartList(@Param("goodscartids") String[] goodscartids);
	/**
	 * @Title: findGoodsCartLists
	 * @Description: 根据购物车id查询购物车商品信息列表 
	 * @param goodscartids
	 * @return
	 * @throws:
	 * @time: 2018年7月11日 上午10:58:06
	 */
	List<GoodsCartModel> findGoodsCartLists(@Param("goodscartids") String[] goodscartids);
	
	/**
	 * @Title: getGoodsPicAddress
	 * @Description:  图片地址
	 * @param goodsid 商品id
	 * @return
	 * @throws:
	 * @time: 2018年7月11日 上午10:57:12
	 */
	@Select("select pic_addr from t_goods_pics where goods_id=#{goodsid} and pic_type='01' and pic_pc_mobile='02' and entkbn=0 ")
	String getGoodsPicAddress(String goodsid);
	
	/**
	 * @Title: getVersionInfo
	 * @Description: 获取当前版本信息
	 * @param userid 用户id
	 * @return
	 * @throws:
	 * @time: 2018年7月13日 下午3:12:31
	 */
	@Select("select version from t_goods_cart where id=#{goodscartid}")
	int getVersionInfo(String goodscartid);
	/**
	 * @Title: getGoodsPrice
	 * @Description:  获取商品价格
	 * @param goodsid 商品id
	 * @return
	 * @throws:
	 * @time: 2018年8月28日 下午3:07:59
	 */
	@Select("select price from t_goods_list where id=#{goodsid}")
	BigDecimal getGoodsPrice(String goodsid);
	/**
	 * @Title: getGoodsPrice
	 * @Description:  获取多规格商品价格
	 * @param goodsid 商品id
	 * @return
	 * @throws:
	 * @time: 2018年8月28日 下午3:07:59
	 */
	@Select("select price from t_goods_price where id=#{goodsid}")
	BigDecimal getGoodsSpecificationPrice(String goodsid);
	/**
	 * @Title: getDelGoodsSpecNum
	 * @Description: 
	 * @param specs
	 * @return
	 * @throws:
	 * @time: 2018年8月29日 下午2:27:20
	 */
	int getDelGoodsSpecNum(@Param("specs") String[] specs);
	

}
