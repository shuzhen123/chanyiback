package dianfan.service.goods;

import dianfan.entities.goods.BgGoods;
import dianfan.entities.goods.GoodsModels;
import dianfan.models.ResultBean;

/**
 * @ClassName BgGoodsService
 * @Description 商品服务
 * @author cjy
 * @date 2018年7月17日 下午3:46:15
 */
public interface BgGoodsService {

    /**
     * @Title: findGoodsList
     * @Description: 获取商品列表
     * @param page 请求页
     * @param pageSize 每页条数
     * @param goodsTitle 商品标题
     * @param easySpelling 是否易拼(0否，1是，默认全部)
     * @param goodsClassifyid 商品分类id
     * @param l_price 非定制的商品价格-低价区间
     * @param h_price 非定制的商品价格-高价区间
     * @param upperlower 上下架(0下架，1上架，默认全部)
     * @param sales_sort 是否按销售数量排序(1是，默认否)
     * @param coll_sort 是否按收藏数量排序(1是，默认否)
     * @param price_sort 是否按价格排序(1是，默认否)
     * @param labelid_arr 商品标签数组
     * @param attrid_arr 商品属性数组
     * @param staffid 员工id
     * @return
     * @throws:
     * @time: 2018年7月18日 下午5:41:57
     */
	ResultBean findGoodsList(int page, int pageSize, String goodsTitle, Integer easySpelling, String goodsClassifyid, String l_price, String h_price, 
			Integer upperlower, Integer sales_sort, Integer coll_sort, Integer price_sort, String[] labelid_arr, String[] attrid_arr, String staffid);
	
	/**
	 * @Title: getGoodsInfoDetail
	 * @Description: 商品详情
	 * @param goodsid 商品id
	 * @return
	 * @throws:
	 * @time: 2018年7月19日 上午11:27:01
	 */
	ResultBean getGoodsInfoDetail(String goodsid);
	
	/**
	 * @Title: addGoods
	 * @Description: 新增商品
	 * @param gm 商品基础数据
	 * @param labelid_arr 商品标签数组
	 * @param attrid_arr 商品属性数组
	 * @param pics 商品图片（json字符串格式）
	 * @param specs 商品规格（json字符串格式）
	 * @param goodsPrices 商品价格（json字符串格式）
	 * @return
	 * @throws:
	 * @time: 2018年7月18日 上午11:21:57
	 */
	ResultBean addGoods(BgGoods gm, String[] labelid_arr, String[] attrid_arr, String pics, String specs,
			String goodsPrices);
	
	/**
	 * @Title: editGoods
	 * @Description: 
	 * @param gm
	 * @param labelid_arr
	 * @param attrid_arr
	 * @param pics
	 * @param specs
	 * @param goodsPrices
	 * @return
	 * @throws:
	 * @time: 2018年7月23日 上午10:54:39
	 * @author cjy
	 */
	ResultBean editGoods(BgGoods gm, String[] labelid_arr, String[] attrid_arr, String pics, String specs,
			String goodsPrices);

	/**
	 * @Title: delGoods
	 * @Description: 批量删除商品
	 * @param goodsids 商品id数组
	 * @param operator 操作者id
	 * @throws:
	 * @time: 2018年7月18日 下午3:32:31
	 */
	void delGoods(String[] goodsids, String operator);
	
	/**
	 * @Title: upDownGoods
	 * @Description: 商品上下架
	 * @param goodsids 商品id数组
	 * @param action 上下架动作（0下架，1上架）
	 * @param operator 操作者id
	 * @throws:
	 * @time: 2018年7月24日 上午9:24:11
	 * @author cjy
	 */
	void upDownGoods(String[] goodsids, int action, String operator);

	/**
	 * @Title: findStaffBindGoods
	 * @Description: 员工下的商品列表（简单列表）
	 * @param staffid 员工id
	 * @return
	 * @throws:
	 * @time: 2018年7月23日 上午11:52:07
	 * @author cjy
	 */
	ResultBean findStaffBindGoods(String staffid);
	
	/**
	 * @Title: unbindStaffGoods
	 * @Description: 解绑 员工下的商品
	 * @param goodsids 商品id数组
	 * @param staffid 员工id
	 * @param operator 操作者id
	 * @throws:
	 * @time: 2018年7月23日 下午12:19:51
	 * @author cjy
	 */
	void unbindStaffGoods(String[] goodsids, String staffid, String operator);
	
	/**
	 * @Title: bindStaffGoods
	 * @Description: 绑定 员工下的商品
	 * @param goodsids 商品数据
	 * @param staffid 员工id
	 * @param operator 操作者id
	 * @throws:
	 * @time: 2018年7月23日 下午1:49:23
	 * @author cjy
	 */
	ResultBean bindStaffGoods(String goodsids, String staffid, String operator);

	/**
	 * @Title: sortStaffGoods
	 * @Description: 排序员工下的商品
	 * @param goodsSortData 商品排序信息
	 * @param staffid 员工id
	 * @param operator 操作者id
	 * @throws:
	 * @time: 2018年7月23日 下午1:26:29
	 * @author cjy
	 */
	ResultBean sortStaffGoods(String goodsSortData, String staffid, String operator);

	

}
