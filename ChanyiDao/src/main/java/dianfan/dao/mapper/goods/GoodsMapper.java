package dianfan.dao.mapper.goods;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import dianfan.entities.goods.GoodsAttr;
import dianfan.entities.goods.GoodsClassify;
import dianfan.entities.goods.GoodsLabels;
import dianfan.entities.goods.GoodsList;
import dianfan.entities.goods.GoodsListLabels;
import dianfan.entities.goods.GoodsModels;
import dianfan.entities.goods.JoinGoods;


/**
 * @ClassName ClassifyMapper
 * @Description 商品分类dao
 * @author sz
 * @date 2018年7月2日 上午10:55:25
 */
@Repository
public interface GoodsMapper {

	/**
	 * @Title: fildGoodsClassify
	 * @Description: 获取商品分类信息
	 * @return GoodsClassify
	 * @throws:
	 * @time: 2018年7月3日 上午10:13:42
	 */
	List<GoodsClassify> fildGoodsClassify();
	
	/**
	 * @Title: findGoodsidsByUserid
	 * @Description: 获取用户下的商品列表
	 * @param userid 用户id
	 * @return
	 * @throws:
	 * @time: 2018年7月4日 上午10:22:31
	 */
	List<String> findGoodsidsByUserid(String userid);
	
	/**
	 * @Title: findGoodsByAttr
	 * @Description: 获取满足商品属性的商品
	 * @param joinGoods
	 * @return
	 * @throws:
	 * @time: 2018年7月13日 上午10:28:34
	 */
	List<GoodsAttr> findGoodsByAttr(JoinGoods joinGoods);
	
	/**
	 * @Title: findGoodsFiltrate
	 * @Description: 获取商品属性列表
	 * @return
	 * @throws:
	 * @time: 2018年7月4日 下午3:43:36
	 */
	List<GoodsAttr> findGoodsFiltrate(@Param("classifyid")String classifyid, @Param("easySpelling")Integer easy_spelling);
	
	/**
	 * @Title: findGoodsCount
	 * @Description: 获取商品数量
	 * @param joinGoods 请求入参
	 * @return
	 * @throws:
	 * @time: 2018年7月4日 下午1:50:21
	 */
	int findGoodsCount(JoinGoods joinGoods);
	
	/**
	 * @Title: findGoodsList
	 * @Description: 获取商品列表
	 * @param joinGoods 请求入参
	 * @return
	 * @throws:
	 * @time: 2018年7月4日 下午1:49:55
	 */
	List<GoodsList> findGoodsList(JoinGoods joinGoods);
	
	
	
	
	


	/**
	 * @Title: getGoodsByid
	 * @Description: 通过id查找商品是否存在
	 * @param goodsid 
	 * 			商品id
	 * @return int
	 * @throws:
	 * @time: 2018年7月3日 下午3:58:17
	 */
	@Select("SELECT count(*) FROM t_goods_list WHERE  id = #{goodsid} AND entkbn = 0 AND upperlower = 1")
	int getGoodsByid(String goodsid);


	/**
	 * @Title: addGoodsFavorites
	 * @Description: 将商品加入收藏夹
	 * @param data
	 * @throws:
	 * @time: 2018年7月3日 下午4:05:13
	 */
	void addGoodsFavorites(Map<String, Object> data);


	/**
	 * @Title: delGoodsFavorites
	 * @Description: 取消商品的收藏
	 * @param data
	 * @throws:
	 * @time: 2018年7月3日 下午4:32:48
	 */
	@Update("UPDATE t_user_collection SET entkbn = 9 WHERE user_id = #{userid} AND goods_id = #{goodsid}")
	void delGoodsFavorites(Map<String, Object> data);


	/**
	 * @Title: getGoodsDetails
	 * @Description: 获取商品的详情 
	 * @param data
	 * @return GoodsModels
	 * @throws:
	 * @time: 2018年7月3日 下午5:06:36
	 */
	GoodsModels getGoodsDetails(Map<String, Object> data);


	/**
	 * @Title: checkGoodsFavorites
	 * @Description: 检测商品是否被收藏过
	 * @param data
	 * @return String
	 * @throws:
	 * @time: 2018年7月4日 上午9:58:27
	 */
	List<String> checkGoodsFavorites(Map<String, Object> data);

	/**
	 * @Title: getMaxGoodsSort
	 * @Description: 获取用户下的商品的最大排序数
	 * @param userid 用户id
	 * @throws:
	 * @time: 2018年7月6日 下午1:48:54
	 */
	@Select("select max(sort) from t_goods_user where user_id=#{userid} and entkbn=0")
	Integer getMaxGoodsSort(String userid);

	/**
	 * @Title: updateGoodsSort
	 * @Description: 更新商品排序
	 * @param userid 用户id
	 * @param goodsid 商品id
	 * @throws:
	 * @time: 2018年7月6日 下午1:51:22
	 */
	@Update("update t_goods_user set sort=#{sort} where  user_id=#{userid} and goods_id=#{goodsid}")
	void updateGoodsSort(@Param("userid") String userid, @Param("goodsid") String goodsid, @Param("sort") int sort);

	/**
	 * @Title: fildGoodsLabels
	 * @Description: 获取商品下的所有的标签
	 * @return GoodsLabels 返回的是所有的商品的标签
	 * @throws:
	 * @time: 2018年7月9日 上午10:14:55
	 */
	List<GoodsLabels> fildGoodsLabels();

	/**
	 * @Title: fildGoodsListLabels
	 * @Description: 获取商品下的标签
	 * @param goodsid  商品的ID
	 * @return
	 * @throws:
	 * @time: 2018年7月9日 上午11:27:49
	 */
	List<GoodsListLabels> fildGoodsListLabels(String goodsid);

	/**
	 * @Title: goodsCollectionNumInc
	 * @Description: 商品收藏数量+1
	 * @param used_id
	 * @throws:
	 * @time: 2018年8月27日 上午10:01:54
	 * @author cjy
	 */
	void goodsCollectionNumInc(List<String> used_id);

}
