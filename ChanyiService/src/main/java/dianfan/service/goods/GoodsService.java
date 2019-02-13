package dianfan.service.goods;

import dianfan.entities.goods.JoinGoods;
import dianfan.models.ResultBean;

/**
 * @ClassName GoodsService
 * @Description 商品相关 接口
 * @author sz
 * @date 2018年7月3日 上午11:13:07
 */
public interface GoodsService {


	/**
	 * @Title: fildGoodsList
	 * @Description: 获取商品列表
	 * @param joinGoods 
	 * 			请求参数
	 * @return ResultBean
	 * @throws: 
	 * @time: 2018年7月3日 下午2:36:59
	 */
	ResultBean findGoodsList(JoinGoods joinGoods);
	
	/**
	 * @Title: findGoodsFiltrate
	 * @Description: 获取商品筛选项
	 * @param classifyid 商品分类id 
	 * @return
	 * @throws:
	 * @time: 2018年7月5日 下午2:44:43
	 */
	ResultBean findGoodsFiltrate(String classifyid, Integer easy_spelling);
	/**
	 * @Title: addFavorites
	 * @Description: 加入收藏夹
	 * @param userid 
	 * 			用户id
	 * @param ids 
	 * 			商品id
	 * @return ResultBean
	 * @throws:
	 * @time: 2018年7月3日 下午3:54:13
	 */
	ResultBean addGoodsFavorites(String userid, String[] ids);

	/**
	 * @Title: delGoodsFavorites
	 * @Description: 取消收藏 
	 * @param userid 
	 * 			用户id
	 * @param goodsid 
	 * 			商品id
	 * @return ResultBean
	 * @throws:
	 * @time: 2018年7月3日 下午4:27:30
	 */
	ResultBean delGoodsFavorites(String userid, String goodsid);

	/**
	 * @Title: getGoodsDetails
	 * @Description: 获取商品的详情
	 * @param userid 
	 * 			用户id
	 * @param goodsid 
	 * 			商品id
	 * @return ResultBean
	 * @throws:
	 * @time: 2018年7月3日 下午4:45:28
	 */
	ResultBean getGoodsDetails(String userid, String goodsid);

	/**
	 * @Title: updateGoodsSort
	 * @Description: 商品自定义排序
	 * @param userid 用户id
	 * @param goodsid 商品id
	 * @throws:
	 * @time: 2018年7月6日 下午1:43:26
	 */
	void updateGoodsSort(String userid, String goodsid);

	/* *****************后台****************** */
	
}
