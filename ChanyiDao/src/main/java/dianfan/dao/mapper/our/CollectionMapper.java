/**  
* @Title: CollectionMapper.java
* @Package dianfan.dao.mapper.our
* @Description: TODO
* @author yl
* @date 2018年6月30日 上午10:33:58
* @version V1.0  
*/ 
package dianfan.dao.mapper.our;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import dianfan.entities.our.UserCollection;
import dianfan.entities.our.GoodsModel;

/** @ClassName CollectionMapper
 * @Description 
 * @author yl
 * @date 2018年6月30日 上午10:33:58
 */
@Repository
public interface CollectionMapper {
	
	/**
	 * @Title: findGoodsIdList
	 * @Description: 
	 * @param userId 用户id
	 * @return 商品id列表
	 * @throws:
	 * @time: 2018年6月28日 下午3:30:52
	 */
	List<UserCollection> findGoodsIdList(@Param("userId") String userId);
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
	 * @Title: findGoodsListPic
	 * @Description: 商品图片列表
	 * @param params
	 * @return
	 * @throws:
	 * @time: 2018年7月11日 下午3:06:21
	 */
	List<GoodsModel> findGoodsListPic(Map<String, Object> params);
	/**
	 * @Title: getGoodsListPicNum
	 * @Description: 统计收藏总数
	 * @param goodsids
	 * @return
	 * @throws:
	 * @time: 2018年7月6日 下午3:07:53
	 */
	Integer getGoodsListPicNum(@Param("goodsids") List<String> goodsids);
	
	/**
	 * @Title: delCollection
	 * @Description: 取消收藏
	 * @param goodsid 商品id
	 * @throws:
	 * @time: 2018年6月30日 下午12:11:07
	 */
	void delCollection(Map<String, Object> params);
	/**
	 * @Title: updateGoodCollectionNum
	 * @Description: 
	 * @param goodsids
	 * @throws:
	 * @time: 2018年7月19日 下午3:27:41
	 */
	void updateGoodCollectionNum(@Param("goodsids") String[] goodsids);

}
