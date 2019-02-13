package dianfan.dao.mapper.goods;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import dianfan.entities.goods.BgGoods;
import dianfan.entities.goods.GoodsAttr;
import dianfan.entities.goods.GoodsLabels;
import dianfan.entities.goods.GoodsModels;
import dianfan.entities.goods.GoodsPics;
import dianfan.entities.goods.GoodsPrice;
import dianfan.entities.goods.GoodsSpec;
import dianfan.entities.goods.StaffGoods;


/**
 * @ClassName ClassifyMapper
 * @Description 商品分类dao
 * @author sz
 * @date 2018年7月2日 上午10:55:25
 */
@Repository
public interface BgGoodsMapper {
	/**
	 * @Title: getScreenGoodsCount
	 * @Description: 根据条件获取筛选后的商品数量
     * @param goodsTitle 商品标题
     * @param easySpelling 是否易拼(0否，1是，默认全部)
     * @param goodsClassifyid 商品分类id
     * @param l_price 非定制的商品价格-低价区间
     * @param h_price 非定制的商品价格-高价区间
     * @param upperlower 上下架(0下架，1上架，默认全部)
     * @param labelid_arr 商品标签数组
     * @param attrid_arr 商品属性数组
     * @param staffid 员工id
	 * @return
	 * @throws:
	 * @time: 2018年7月18日 下午5:39:47
	 */
	int getScreenGoodsCount(
			@Param("goodsTitle")String goodsTitle, 
			@Param("easySpelling")Integer easySpelling, 
			@Param("goodsClassifyid")String goodsClassifyid,
			@Param("l_price")String l_price, 
			@Param("h_price")String h_price, 
			@Param("upperlower")Integer upperlower, 
			@Param("labelid_arr")String[] labelid_arr, 
			@Param("attrid_arr")String[] attrid_arr,
			@Param("staffid")String staffid);
	
	/**
	 * @Title: getScreenGoodsCount
	 * @Description: 根据条件获取筛选后的商品
     * @param pageStart 请求起始条数
     * @param pageSize 请求条数
     * @param goodsTitle 商品标题
     * @param easySpelling 是否易拼(0否，1是，默认全部)
     * @param goodsClassifyid 商品分类id
     * @param l_price 非定制的商品价格-低价区间
     * @param h_price 非定制的商品价格-高价区间
     * @param upperlower 上下架(0下架，1上架，默认全部)
     * @param sales_sort 是否按销售数量排序(1是，默认否)
     * @param coll_sort 是否按收藏数量排序(1是，默认否)
     * @param labelid_arr 商品标签数组
     * @param attrid_arr 商品属性数组
     * @param staffid 员工id
	 * @return
	 * @throws:
	 * @time: 2018年7月18日 下午5:39:47
	 * @author cjy
	 */
	List<BgGoods> findScreenGoodsList(
			@Param("pageStart")int pageStart, 
			@Param("pageSize")int pageSize, 
			@Param("goodsTitle")String goodsTitle, 
			@Param("easySpelling")Integer easySpelling, 
			@Param("goodsClassifyid")String goodsClassifyid,
			@Param("l_price")String l_price, 
			@Param("h_price")String h_price, 
			@Param("upperlower")Integer upperlower, 
			@Param("sales_sort")Integer sales_sort, 
			@Param("coll_sort")Integer coll_sort, 
			@Param("price_sort")Integer price_sort,
			@Param("labelid_arr")String[] labelid_arr, 
			@Param("attrid_arr")String[] attrid_arr,
			@Param("staffid")String staffid);
	
	/**
	 * @Title: getGoodsInfoDetailById
	 * @Description: 根据商品id获取商品详情
	 * @param goodsid 商品id
	 * @return
	 * @throws:
	 * @time: 2018年7月19日 上午11:28:50
	 * @author cjy
	 */
	BgGoods getGoodsInfoDetailById(String goodsid);
	
	/**
	 * @Title: findGoodsLabelById
	 * @Description: 根据商品id获取商品标签
	 * @param goodsid 商品id
	 * @return
	 * @throws:
	 * @time: 2018年7月19日 下午12:00:24
	 * @author cjy
	 */
	@Select("select label.id, label.label_name labelName " +
			"from t_goods_labels label,t_goods_list_labels glab " +
			"where glab.label_id=label.id and label.entkbn=0 and glab.goods_id=#{goodsid}")
	List<GoodsLabels> findGoodsLabelById(String goodsid);
	
	/**
	 * @Title: findGoodsAttrById
	 * @Description: 根据商品id获取商品属性
	 * @param goodsid 商品id
	 * @return
	 * @throws:
	 * @time: 2018年7月19日 下午12:15:04
	 * @author cjy
	 */
	@Select("select attr.id, attr.param_key paramKey, attr.param_name paramName " +
			"from t_goods_attributes attr, t_goods_attr_releate gattr " +
			"where gattr.attributes_id=attr.id and attr.entkbn=0 and gattr.goodsid=#{goodsid}")
	List<GoodsAttr> findGoodsAttrById(String goodsid);
	
	/**
	 * @Title: findGoodsSpecById
	 * @Description: 根据商品id获取商品规格
	 * @param goodsid 商品id
	 * @return
	 * @throws:
	 * @time: 2018年7月19日 下午1:33:06
	 * @author cjy
	 */
	@Select("select id, spec_name specName, spec_catagory specCatagory from t_goods_spec where goods_id=#{goodsid} and entkbn=0")
	List<GoodsSpec> findGoodsSpecById(String goodsid);
	
	/**
	 * @Title: findGoodsPriceById
	 * @Description: 根据商品id获取商品规格对应的价格
	 * @param goodsid 商品id
	 * @return
	 * @throws:
	 * @time: 2018年7月19日 下午2:05:18
	 * @author cjy
	 */
	@Select("select id, spec_ids specIds, goods_pic goodsPic, price price, price_easy_spelling priceEasySpelling "
			+ "from t_goods_price where goods_id=#{goodsid} and entkbn=0 order by spec_ids")
	List<GoodsPrice> findGoodsPriceById(String goodsid);
	
	/**
	 * @Title: findGoodsPicById
	 * @Description: 根据商品id获取商品图片
	 * @param goodsid 商品id
	 * @return
	 * @throws:
	 * @time: 2018年7月19日 下午2:47:55
	 * @author cjy
	 */
	@Select("select pic_id picId, pic_type picType, pic_sort picSort, pic_addr picAddr, pic_pc_mobile picPcMobile "
			+ "from t_goods_pics where goods_id=#{goodsid} and entkbn=0 order by pic_sort")
	List<GoodsPics> findGoodsPicById(String goodsid);
	
	/**
	 * @Title: addGoods
	 * @Description: 新增商品基础数据
	 * @param gm 商品基础数据
	 * @throws:
	 * @time: 2018年7月18日 上午11:26:46
	 * @author cjy
	 */
	void addGoods(BgGoods gm);

	/**
	 * @Title: delGoodsLabelRelation
	 * @Description: 删除商品与标签关系
	 * @param goodsid 商品id
	 * @throws:
	 * @time: 2018年7月19日 下午5:12:14
	 * @author cjy
	 */
	@Delete("delete from t_goods_list_labels where goods_id=#{goodsid}")
	void delGoodsLabelRelation(String goodsid);
	
	/**
	 * @Title: addGoodsLabelRelation
	 * @Description: 绑定商品与标签关系
	 * @param labelid_arr 标签数组
	 * @param goodsid 商品id
	 * @throws:
	 * @time: 2018年7月18日 上午11:34:42
	 */
	void addGoodsLabelRelation(@Param("labelids")String[] labelid_arr, @Param("goodsid")String goodsid);

	/**
	 * @Title: delGoodsAttrRelation
	 * @Description: 删除商品与属性关系
	 * @param goodsid 商品id
	 * @throws:
	 * @time: 2018年7月19日 下午5:16:43
	 * @author cjy
	 */
	@Delete("delete from t_goods_attr_releate where goodsid=#{goodsid}")
	void delGoodsAttrRelation(String goodsid);
	
	/**
	 * @Title: addGoodsAttrRelation
	 * @Description: 绑定商品与属性关系
	 * @param attrid_arr 商品属性数组
	 * @param goodsid 商品id
	 * @throws:
	 * @time: 2018年7月18日 上午11:58:05
	 */
	void addGoodsAttrRelation(@Param("attrids")String[] attrid_arr, @Param("goodsid")String goodsid);

	/**
	 * @Title: delGoodsPicsById
	 * @Description: 删除所有商品图片
	 * @param goodsid 商品id
	 * @param userid 更新者id
	 * @throws:
	 * @time: 2018年7月20日 下午2:13:06
	 * @author cjy
	 */
	@Update("update t_goods_pics set entkbn=9, update_by=#{userid} where goods_id=#{goodsid} and entkbn=0")
	void delGoodsPicsById(@Param("goodsid")String goodsid, @Param("userid")String userid);

	/**
	 * @Title: addGoodsPics
	 * @Description: 添加商品图片
	 * @param pic_list_data 商品图片数据
	 * @param goodsid 商品id
	 * @param userid 添加者id
	 * @throws:
	 * @time: 2018年7月18日 下午12:17:42
	 */
	void addGoodsPics(@Param("pics")List<Map<String, Object>> pic_list_data, @Param("goodsid")String goodsid, @Param("userid")String userid);
	
	/**
	 * @Title: delGoodsSpecAndPriceById
	 * @Description: 删除所有商品规格和价格
	 * @param goodsid 商品id
	 * @param userid 更新者id
	 * @throws:
	 * @time: 2018年7月20日 下午2:17:56
	 * @author cjy
	 */
	@Update("update t_goods_price price, t_goods_spec spec set price.entkbn=9, price.only_identity=null, price.update_by=#{userid}, spec.entkbn=9, spec.update_by=#{userid}  where price.goods_id=spec.goods_id and spec.goods_id=#{goodsid}")
	void delGoodsSpecAndPriceById(@Param("goodsid")String goodsid, @Param("userid")String userid);
	
	/**
	 * @Title: delGoodsSpecs
	 * @Description: 删除商品规格
	 * @param goodsid
	 * @param createBy
	 * @throws:
	 * @time: 2018年8月28日 下午7:08:38
	 * @author cjy
	 */
	@Update("update t_goods_spec set entkbn=9, update_by=#{userid} where goods_id=#{goodsid}")
	void delGoodsSpecs(@Param("goodsid")String goodsid, @Param("userid")String createBy);
	
	/**
	 * @Title: addGoodsSpecs
	 * @Description: 添加商品规格
	 * @param specids 商品规格数据
	 * @param goodsid 商品id
	 * @param createBy 创建者id
	 * @throws:
	 * @time: 2018年7月18日 下午1:49:38
	 */
	void addGoodsSpecs(@Param("specids")List<GoodsSpec> specids, @Param("goodsid")String goodsid, @Param("userid")String createBy);

	/**
	 * @Title: addGoodsPrices
	 * @Description: 添加商品价格
	 * @param prices 商品价格数据
	 * @param goodsid 商品id
	 * @param createBy 创建者id
	 * @throws:
	 * @time: 2018年7月18日 下午2:52:40
	 */
	void addGoodsPrices(@Param("prices")List<GoodsPrice> prices, @Param("goodsid")String goodsid, @Param("userid")String createBy);

	/**
	 * @Title: delGoods
	 * @Description: 批量删除商品
	 * @param goodsids 商品标签数组
	 * @param attrid_arr 商品属性数组
	 * @throws:
	 * @time: 2018年7月18日 下午3:35:33
	 */
	void delGoods(@Param("goodsids")String[] goodsids, @Param("userid")String userid);
	
	/**
	 * @Title: updateGoodsUpperLower
	 * @Description: 商品上下架
	 * @param goodsids 商品id数组
	 * @param action 上下架动作（0下架，1上架）
	 * @param operator 操作者id
	 * @throws:
	 * @time: 2018年7月24日 上午9:31:01
	 * @author cjy
	 */
	void updateGoodsUpperLower(@Param("goodsids")String[] goodsids, @Param("action")int action, @Param("operator")String operator);

	/**
	 * @Title: editGoods
	 * @Description: 修改商品基础数据
	 * @param gm 商品基础数据
	 * @throws:
	 * @time: 2018年7月19日 下午4:43:22
	 * @author cjy
	 */
	void editGoods(BgGoods gm);
	
	/**
	 * @Title: findStaffBindGoods
	 * @Description: 员工下的商品列表（简单列表）
	 * @param staffid 员工id
	 * @return
	 * @throws:
	 * @time: 2018年7月23日 上午11:54:30
	 * @author cjy
	 */
	List<StaffGoods> findStaffBindGoods(String staffid);
	
	/**
	 * @Title: checkBindedGoods
	 * @Description: 检测已绑定的商品
	 * @param goodsids 商品数组
	 * @param staffid 员工id
	 * @throws:
	 * @time: 2018年7月23日 上午11:22:08
	 * @author cjy
	 */
	List<String> checkBindedGoods(@Param("goodsids")String[] goodsids, @Param("staffid")String staffid);
	

	/**
	 * @Title: bindGoods
	 * @Description: 绑定商品
	 * @param goodsids 商品数组
	 * @param staffid 员工id
	 * @param operator 操作者id
	 * @throws:
	 * @time: 2018年7月23日 下午1:55:46
	 * @author cjy
	 */
	void bindGoods(@Param("goodsids")List<Map<String, Object>> goodsids, @Param("staffid")String staffid, @Param("operator")String operator);

	/**
	 * @Title: bindGoods
	 * @Description: 解绑商品
	 * @param ids 商品数组
	 * @param staffid 员工id
	 * @param operator 操作者id
	 * @throws:
	 * @time: 2018年7月23日 上午11:28:38
	 * @author cjy
	 */
	void unbindGoods(@Param("goodsids")String[] goodsids, @Param("staffid")String staffid, @Param("operator")String operator);

	/**
	 * @Title: updateStaffGoodsSort
	 * @Description: 更新商品排序数据
	 * @param data 商品排序数据
	 * @param staffid 用户id
	 * @param operator 操作者id
	 * @throws:
	 * @time: 2018年7月23日 下午1:37:01
	 * @author cjy
	 */
	void updateStaffGoodsSort(@Param("sortData")List<StaffGoods> data, @Param("staffid")String staffid, @Param("operator")String operator);

	/**
	 * @Title: findAvailableGoodsPrice
	 * @Description: 获取所有可用商品价格id
	 * @param id
	 * @return
	 * @throws:
	 * @time: 2018年8月28日 下午5:37:00
	 * @author cjy
	 */
	@Select("select id, only_identity onlyIdentity from t_goods_price where goods_id=#{id} and entkbn=0")
	List<GoodsPrice> findAvailableGoodsPrice(String id);
	
	/**
	 * @Title: updateGoodsCartStatus
	 * @Description: 将购物车中此价格的商品置为已失效
	 * @param goodsPriceIds 商品价格id列表
	 * @param goodsid 商品id
	 * @throws:
	 * @time: 2018年8月28日 下午6:22:45
	 * @author cjy
	 */
	void updateGoodsCartStatus(@Param("goodsPriceIds")List<String> goodsPriceIds, @Param("goodsid")String goodsid);
	void updateGoodsCartStatus1(String[] goodsids);

	/**
	 * @Title: findReserveGoodsPrice
	 * @Description: 获取保留的商品价格id列表
	 * @param price_list_data
	 * @return
	 * @throws:
	 * @time: 2018年8月28日 下午5:40:02
	 * @author cjy
	 */
	List<GoodsPrice> findReserveGoodsPrice(List<GoodsPrice> price_list_data);
	
	/**
	 * @Title: delGoodsPriceById
	 * @Description: 删除商品价格
	 * @param goodsids 商品价格id
	 * @param operater 更新者id
	 * @throws:
	 * @time: 2018年7月20日 下午2:17:56
	 * @author cjy
	 */
	void delGoodsPriceById(@Param("goodsids")List<String> goodsids, @Param("operator")String operator);

	/**
	 * @Title: updateOrderStatus
	 * @Description: 将未付款的订单中包含此价格的商品置为被动关闭
	 * @param goodsids 商品价格id列表
	 * @param goodsid 商品id
	 * @param operator 操作者id
	 * @throws:
	 * @time: 2018年8月28日 下午6:26:46
	 * @author cjy
	 */
	void updateOrderStatus(@Param("goodsPriceIds")List<String> goodsPriceIds, @Param("goodsid")String goodsid, @Param("operator")String operator);
	void updateOrderStatus1(@Param("goodsids")String[] goodsids, @Param("operator")String operator);

	/**
	 * @Title: updateGoodsPrice
	 * @Description: 更新商品价格
	 * @param reserve_price_data 商品价格
	 * @param operator 操作者id
	 * @throws:
	 * @time: 2018年8月28日 下午6:44:04
	 * @author cjy
	 */
	@Update("update t_goods_price set spec_ids=#{rpd.specIds}, goods_pic=#{rpd.goodsPic}, price=#{rpd.price}, price_easy_spelling=#{rpd.priceEasySpelling}, update_by=#{operator} where id=#{rpd.id}")
	void updateGoodsPrice(@Param("rpd")GoodsPrice reserve_price_data, @Param("operator")String operator);

	/**
	 * @Title: updateGoodsCart
	 * @Description: 更新购物车中此价格的商品
	 * @param reserve_price_data 商品价格
	 * @param goodsid 商品id
	 * @param operator 操作者id
	 * @throws:
	 * @time: 2018年8月28日 下午6:56:15
	 * @author cjy
	 */
	@Update("update t_goods_cart set pic_addr=#{rpd.goodsPic}, unit=#{rpd.price} where goods_price_id=#{rpd.id} and goods_id=#{goodsid}")
	void updateGoodsCart(@Param("rpd")GoodsPrice reserve_price_data, @Param("goodsid")String goodsid);

}
