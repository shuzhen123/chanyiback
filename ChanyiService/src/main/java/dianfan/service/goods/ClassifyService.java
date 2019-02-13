package dianfan.service.goods;

import dianfan.entities.goods.GoodsClassify;
import dianfan.models.ResultBean;

/**
 * @ClassName ClassifyService
 * @Description 商品相关（接口）
 * @author sz
 * @date 2018年7月2日 上午10:14:13
 */
public interface ClassifyService {

	/**
	 * @Title: fildGoodsClassify
	 * @Description: 商品分类列表
	 * @return ResultBean
	 * @throws:
	 * @time: 2018年7月3日 上午10:11:01
	 */
	ResultBean fildGoodsClassify();
	
	/**
	 * @Title: findGoodsClassifyList
	 * @Description: 获取商品分类列表
	 * @param used 只获取可用分类（1可用）
	 * @return
	 * @throws:
	 * @time: 2018年7月17日 下午3:55:06
	 */
	ResultBean findGoodsClassifyList(String used);

	/**
	 * @Title: addGoodsClassify
	 * @Description: 添加商品分类
	 * @param goodsClassify 商品分类信息
	 * @return
	 * @throws:
	 * @time: 2018年7月16日 上午11:46:01
	 */
	ResultBean addGoodsClassify(GoodsClassify goodsClassify);

	/**
	 * @Title: editGoodsClassify
	 * @Description: 修改商品分类
	 * @param goodsClassify 商品分类信息
	 * @return
	 * @throws:
	 * @time: 2018年7月16日 下午2:38:09
	 */
	ResultBean editGoodsClassify(GoodsClassify goodsClassify);

	/**
	 * @Title: delGoodsClassify
	 * @Description: 删除/禁用/启用 商品分类
	 * @param classifyid 商品分类id
	 * @param status 操作状态(0启用，1禁用，9删除)
	 * @param update_userid 更新者id
	 * @return
	 * @throws:
	 * @time: 2018年7月16日 下午3:42:23
	 */
	ResultBean delGoodsClassify(String classifyids, int status, String update_userid);

}
