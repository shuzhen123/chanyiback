package dianfan.service.goods;

import dianfan.models.ResultBean;

/**
 * @ClassName GoodsSpecService
 * @Description 商品规格服务
 * @author cjy
 * @date 2018年7月3日 下午2:38:37
 */
public interface GoodsSpecService {

	/**
	 * @Title: findGoodsSpecByGoodsid
	 * @Description: 根据商品id获取商品规格列表
	 * @param goodsid 商品id
	 * @return
	 * @throws:
	 * @time: 2018年7月3日 下午2:42:08
	 */
	ResultBean findGoodsSpecByGoodsid(String goodsid);

	/**
	 * @Title: findGoodsAttrList
	 * @Description: 获取商品属性列表
	 * @return
	 * @throws:
	 * @time: 2018年7月17日 下午4:46:02
	 */
	ResultBean findGoodsAttrList();

	/**
	 * @Title: addGoodsAttr
	 * @Description: 新增商品属性
	 * @param paramKey 商品属性名
	 * @param paramName 商品属性值
	 * @param userid 添加者id
	 * @return
	 * @throws:
	 * @time: 2018年7月17日 下午5:21:19
	 */
	ResultBean addGoodsAttr(String paramKey, String paramName, String userid);

	/**
	 * @Title: editGoodsAttr
	 * @Description: 修改商品属性
	 * @param attrid 商品属性id
	 * @param paramKey 商品属性名
	 * @param paramName 商品属性值
	 * @param userid 修改者id
	 * @return
	 * @throws:
	 * @time: 2018年7月17日 下午5:40:33
	 */
	ResultBean editGoodsAttr(String attrid, String paramKey, String paramName, String userid);

	/**
	 * @Title: delGoodsAttr
	 * @Description: 删除商品属性
	 * @param attrids 商品属性id数组
	 * @param userid 修改者id
	 * @return
	 * @throws:
	 * @time: 2018年7月17日 下午6:11:07
	 */
	ResultBean delGoodsAttr(String[] attrids, String userid);
}
