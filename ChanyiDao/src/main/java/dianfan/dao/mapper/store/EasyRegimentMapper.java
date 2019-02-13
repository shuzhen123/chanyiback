/**  
* @Title: WithdrawCashMapper.java
* @Package dianfan.dao.mapper.our
* @Description: TODO
* @author yl
* @date 2018年7月2日 上午10:39:13
* @version V1.0  
*/ 
package dianfan.dao.mapper.store;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import dianfan.entities.Coupon;
import dianfan.entities.ExperiencestoreApply;
import dianfan.entities.goods.GoodsClassify;
import dianfan.entities.goods.GoodsModels;
import dianfan.entities.store.ESSearchParam;
import dianfan.entities.store.ExperienceStore;
import dianfan.entities.store.Store;

/**
 * @ClassName EasyRegimentMapper
 * @Description 易团dao
 * @author cjy
 * @date 2018年7月6日 下午3:55:46
 */
@Repository
public interface EasyRegimentMapper {
	
	/**
	 * @Title: findStore
	 * @Description: 获取体验店列表数量
	 * @param essp
	 * @return
	 * @throws:
	 * @time: 2018年7月6日 下午4:02:26
	 */
	int findEasyRegimentStoreCount(ESSearchParam essp);
	
	/**
	 * @Title: findStore
	 * @Description: 获取体验店列表
	 * @param essp
	 * @return
	 * @throws:
	 * @time: 2018年7月6日 下午4:02:26
	 */
	List<ExperienceStore> findEasyRegimentStore(ESSearchParam essp);

	/**
	 * @Title: findGoodsClassifyByLevel
	 * @Description: 根据等级获取商品分类
	 * @param level 等级
	 * @return
	 * @throws:
	 * @time: 2018年7月6日 下午7:17:41
	 */
	@Select("select id, classify_name classifyName, classify_name_en classifyNameEn from t_goods_classify where classify_level=#{level} and entkbn=0")
	List<GoodsClassify> findGoodsClassifyByLevel(int level);

	/**
	 * @Title: getStoreCount
	 * @Description: 根据筛选条件获取体验店数量
	 * @param store 筛选参数
	 * @param cityCodes 城市code列表
	 * @return
	 * @throws:
	 * @time: 2018年7月17日 下午2:34:30
	 */
	int getStoreCount(@Param("store")Store store, @Param("cityCodes")List<String> cityCodes);
	
	/**
	 * @Title: getStoreList
	 * @Description: 根据筛选条件获取体验店列表
	 * @param pageStart 起始页
	 * @param pageSize 偏移量
	 * @param store 筛选参数
	 * @param cityCodes 城市code列表
	 * @return
	 * @throws:
	 * @time: 2018年7月24日 下午5:25:25
	 * @author cjy
	 */
	List<Store> getStoreList(@Param("pageStart")int pageStart, @Param("pageSize")int pageSize, @Param("store")Store store, @Param("cityCodes")List<String> cityCodes);
	
	/**
	 * @Title: getStoreDetail
	 * @Description: 体验店详情
	 * @param storeid 体验店id
	 * @return
	 * @throws:
	 * @time: 2018年7月24日 下午1:16:35
	 * @author cjy
	 */
	Store getStoreDetail(String storeid);
	
	/**
	 * @Title: findStoreClassify
	 * @Description: 获取体验店的分类
	 * @param storeid 体验店id
	 * @return
	 * @throws:
	 * @time: 2018年7月24日 下午2:12:27
	 * @author cjy
	 */
	List<GoodsClassify> findStoreClassify(String storeid);

	/**
	 * @Title: addStore
	 * @Description: 添加体验店
	 * @param store 体验店数据
	 * @throws:
	 * @time: 2018年7月24日 上午11:37:40
	 * @author cjy
	 */
	void addStore(Store store);

	/**
	 * @Title: addStoreClassify
	 * @Description: 添加体验店分类
	 * @param classifyids 分类列表
	 * @param storeid 体验店id
	 * @param operater 添加者id
	 * @throws:
	 * @time: 2018年7月24日 上午11:45:49
	 * @author cjy
	 */
	void addStoreClassify(@Param("classifyids")String[] classifyids, @Param("storeid")String storeid, @Param("operater")String operater);

	/**
	 * @Title: updateStore
	 * @Description: 编辑体验店
	 * @param store 体验店数据
	 * @throws:
	 * @time: 2018年7月24日 下午2:43:22
	 * @author cjy
	 */
	void updateStore(Store store);

	/**
	 * @Title: startStopStore
	 * @Description: 禁用/启用 体验店
	 * @param storeids 体验店id列表
	 * @param action 动作
	 * @param operater 操作者id
	 * @throws:
	 * @time: 2018年7月28日 下午12:14:46
	 * @author cjy
	 */
	void startStopStore(@Param("storeids")String[] storeids, @Param("action")int action, @Param("operater")String operater);

	/**
	 * @Title: delStoreClassify
	 * @Description: 删除体验店分类
	 * @param storeids 体验店id数组
	 * @param operater 操作者id
	 * @throws:
	 * @time: 2018年7月24日 下午3:12:10
	 * @author cjy
	 */
	void delStoreClassify(@Param("storeids")String[] storeids, @Param("operater")String operater);

	/**
	 * @Title: findExperienceInfo
	 * @Description: 获取体验店的详情 
	 * @param id 体验店的ID
	 * @return
	 * @throws:
	 * @time: 2018年8月2日 下午1:46:48
	 */
	ExperiencestoreApply findExperienceInfo(String id);

	/**
	 * @Title: findGoodsListByExs
	 * @Description: 获取体验店下的商品的详情
	 * @param id 体验店的ID
	 * @return
	 * @throws:
	 * @time: 2018年8月2日 下午2:08:38
	 */
	List<GoodsModels> findGoodsListByExs(String id);

	/**
	 * @Title: findCoupons
	 * @Description: 获取体验店下的优惠券
	 * @param id
	 * @return
	 * @throws:
	 * @time: 2018年8月2日 下午3:26:17
	 */
	List<Coupon> findCoupons(String id);

	/**
	 * @Title: getCheckUserCoupon
	 * @Description: 检查用户是否领取过该优惠券
	 * @param param 
	 * @return
	 * @throws:
	 * @time: 2018年8月3日 上午10:47:51
	 */
	@Select(" SELECT COUNT(*) FROM t_user_coupon_relate WHERE coupon_id = #{id} AND user_id = #{userid} AND user_used = 0 ")
	int getCheckUserCoupon(Map<String, Object> param);


	/**
	 * @Title: getUsedEndTime
	 * @Description: 获取优惠券的截止时间
	 * @return
	 * @throws:used_end_time
	 * @time: 2018年8月3日 上午11:16:11
	 */
	@Select(" SELECT used_end_time FROM t_user_coupon_relate WHERE coupon_id = #{id} AND user_id = #{userid} AND user_used = 0 ")
	Date getUsedEndTime(Map<String, Object> param);

	/**
	 * @Title: getUserGetCoupon
	 * @Description: 领取优惠券
	 * @param param
	 * @throws:
	 * @time: 2018年8月3日 上午11:45:46
	 */
	void getUserGetCoupon(Map<String, Object> param);
	
	/**
	 * @Title: getCheckExperienceStoreVoucher
	 * @Description: 查看体验店下是否存在优惠券
	 * @param storeid 体验店的ID
	 * @return
	 * @throws:
	 * @time: 2018年8月8日 下午2:47:11
	 */
	@Select(" SELECT COUNT(*) FROM t_coupon WHERE coupon_endtime>now() and  (store_id LIKE CONCAT('%','','%') or store_id is null or ( store_id LIKE CONCAT('%',#{storeid},'%')))  and entkbn = 0 ")
	int getCheckExperienceStoreVoucher(String storeid);


	/**
	 * @Title: findVouchers
	 * @Description: 获取体验店下的优惠券
	 * @param storeid
	 * @return
	 * @throws:
	 * @time: 2018年8月8日 下午3:31:28
	 */
	@Select(" SELECT coupon_type FROM t_coupon WHERE coupon_endtime>now() and (store_id LIKE CONCAT('%','','%') or store_id is null or ( store_id LIKE CONCAT('%',#{storeid},'%'))) AND entkbn = 0 ")
	List<String> findVouchers(String storeid);

	/**
	 * @Title: uodateStoreUserAreaCode
	 * @Description: 修改体验店用户表对应的城市code
	 * @param id
	 * @throws:
	 * @time: 2018年8月27日 上午11:07:38
	 * @author cjy
	 */
	@Update("update t_user_userinfo set area_code=(select city_code from t_experiencestore_apply where id=#{id}) "+
			"where id=(select apply_user_id from t_experiencestore_apply where id=#{id})")
	void updateStoreUserAreaCode(String id);

	/**
	 * @Title: getCouponNum
	 * @Description: 通过优惠券id获取当前优惠券数量
	 * @param couponid 优惠券id
	 * @return
	 * @throws:
	 * @time: 2018年8月29日 上午10:38:31
	 */
	@Select("select coupon_num from t_coupon where id=#{couponid}")
	int getCouponNum(String couponid);
	/**
	 * @Title: updateCouponNum
	 * @Description: 更新优惠券分类数量
	 * @param couponid 优惠券id
	 * @throws:
	 * @time: 2018年8月29日 上午10:46:00
	 */
	@Select("update t_coupon set coupon_num = #{couponnum}  where id=#{couponid}")
	void updateCouponNum(@Param("couponnum") int couponnum,@Param("couponid") String couponid);

}
