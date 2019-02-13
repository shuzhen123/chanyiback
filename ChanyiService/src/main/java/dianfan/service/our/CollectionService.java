package dianfan.service.our;

import dianfan.models.ResultBean;

public interface CollectionService {

	/**
	 * @Title: findCollectionList
	 * @Description: 根据用户id获取多条商品数据
	 * @param id 用户名
	 * @return List<Collection> 优惠券列表
	 * @throws:
	 * @time: 2018年6月28日 上午11:02:29
	 */
	ResultBean findCollectionList(String id,Integer page,Integer pagecounts);
	
	/**
	 * @Title: delCollection
	 * @Description: 取消收藏
	 * @param goodsid
	 * @throws:
	 * @time: 2018年6月30日 下午12:09:10
	 */
	void delCollection(String userid,String goodsid);
	
}
