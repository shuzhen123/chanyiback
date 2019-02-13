/**  
* @Title: CouponMapper.java
* @Package dianfan.dao.mapper.our
* @Description: TODO
* @author yl
* @date 2018年6月28日 下午2:38:37
* @version V1.0  
*/
package dianfan.dao.mapper.our;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import dianfan.entities.Coupon;
import dianfan.entities.UserCouponDetail;
import dianfan.entities.UserCouponRelate;
import dianfan.entities.UserInfo;
import dianfan.entities.goods.GoodsClassify;
import dianfan.entities.our.GoodsModel;
import dianfan.entities.our.UserInfoModel;
import dianfan.entities.store.Store;

/**
 * @ClassName CouponMapper
 * @Description
 * @author yl
 * @date 2018年6月28日 下午2:38:37
 */
public interface CouponMapper {

	/**
	 * @Title: findCouponIdList
	 * @Description:
	 * @param userId
	 *            用户id
	 * @return 优惠券id列表
	 * @throws:
	 * @time: 2018年6月28日 下午3:30:52
	 */
	List<UserCouponRelate> findCouponIdList(Map<String, Object> params);

	/**
	 * @Title: findCouponList
	 * @Description:
	 * @param couponids
	 * @return 优惠券列表
	 * @throws:
	 * @time: 2018年6月28日 下午3:38:20
	 */
	List<Coupon> findCouponList(@Param("couponids") List<String> couponids);

	/**
	 * @Title: getCouponCount
	 * @Description:
	 * @param params
	 * @return 优惠券总条数（加条件）
	 * @throws:
	 * @time: 2018年7月3日 下午2:06:47
	 */
	Integer getCouponCount(Map<String, Object> params);

	/**
	 * @Title: getCoupon
	 * @Description:
	 * @param couponid
	 *            优惠券id
	 * @return 获取优惠券信息
	 * @throws:
	 * @time: 2018年7月7日 下午6:18:14
	 */
	Coupon getCoupon(String couponid);

	/**
	 * @Title: updateCoupon
	 * @Description:
	 * @param ucr
	 * @throws:
	 * @time: 2018年7月9日 上午10:41:48
	 */
	@Update("update t_user_coupon_relate set user_used=#{userUsed},used_date=#{usedDate},update_by=#{updateBy} where id=#{id}")
	void updateCoupon(UserCouponRelate ucr);

	/**
	 * @Title: updateResverCoupon
	 * @Description:
	 * @param ucr
	 * @throws:
	 * @time: 2018年7月9日 上午10:41:48
	 */
	@Update("update t_user_coupon_relate set user_used=#{userUsed},used_date=null,update_by='taskbatch',version= version+1 where id=#{id} and entkbn=0")
	void updateResverCoupon(UserCouponRelate ucr);

	/**
	 * @Title: findBgCouponList 后台优惠券列表
	 * @Description:
	 * @param params
	 *            入参
	 * @return
	 * @throws:
	 * @time: 2018年7月18日 上午11:10:12
	 */
	List<Coupon> findBgCouponList(Map<String, Object> params);

	/**
	 * @Title: findBgCouponNum
	 * @Description: 统计优惠券数量
	 * @param params
	 * @return
	 * @throws:
	 * @time: 2018年7月18日 下午12:37:16
	 */
	int getBgCouponNum(Map<String, Object> params);

	/**
	 * @Title: addBgCoupon
	 * @Description: 添加优惠券分类
	 * @param copon
	 *            实体入参
	 * @throws:
	 * @time: 2018年7月18日 下午3:36:44
	 */
	@Insert("insert into t_coupon (id,coupon_name,coupon_num,coupon_endtime,coupon_apply,coupon_type,coupon_condtion,coupon_reduce_money,coupon_classify_id,coupon_des,coupon_starttime,create_time,create_by,entkbn,goods_id,store_id) values "
			+ "(replace(uuid(),'-',''),#{couponName},#{couponNum},#{couponEndtime},#{couponApply},#{couponType},#{couponCondtion},#{couponReduceMoney},#{couponClassifyId},#{couponDes},now(),now(),#{createBy},0,#{goodsid},#{storeid})")
	void addBgCoupon(Coupon copon);

	/**
	 * @Title: updateBgCoupon
	 * @Description:
	 * @param copon
	 * @throws:
	 * @time: 2018年7月19日 下午1:17:39
	 */
	void updateBgCoupon(Coupon copon);

	/**
	 * @Title: updateBgCouponNum
	 * @Description:
	 * @param copon
	 * @throws:
	 * @time: 2018年7月19日 下午4:18:43
	 */
	@Update("update t_coupon set coupon_num=#{couponNum},update_by=#{updateBy},version=version+1 where id=#{id} and version=#{version}")
	void updateBgCouponNum(Coupon cou);

	/**
	 * @Title: getCouponVersion
	 * @Description: 获取优惠券版本号
	 * @param id
	 *            优惠券id
	 * @return String
	 * @throws:
	 * @time: 2018年7月19日 下午2:08:53
	 */
	@Select("select version from t_coupon where id=#{id}")
	int getCouponVersion(String id);

	/**
	 * @Title: delCoupons
	 * @Description: 删除优惠券
	 * @param couponids
	 * @throws:
	 * @time: 2018年7月19日 下午2:14:48
	 */
	void delCoupons(@Param("userid") String userid, @Param("couponids") String[] couponids);

	/**
	 * @Title: delUserCouponRelates
	 * @Description: 删除用户优惠券相关表
	 * @param couponids
	 * @throws:
	 * @time: 2018年7月19日 下午2:14:48
	 */
	void delUserCouponRelates(@Param("userid") String userid, @Param("couponids") String[] couponids);

	/**
	 * @Title: getBgCouponDetail
	 * @Description: 获取优惠券详情
	 * @param couponid
	 *            优惠券id
	 * @return
	 * @throws:
	 * @time: 2018年7月19日 下午2:36:02
	 */
	Coupon getBgCouponDetail(@Param("couponid") String couponid);

	/**
	 * @Title: findBgCouponDetailNum
	 * @Description:
	 * @param params
	 * @return
	 * @throws:
	 * @time: 2018年7月20日 下午12:55:37
	 */
	int getBgCouponDetailNum(Map<String, Object> params);

	/**
	 * @Title: findBgCouponDetail
	 * @Description:
	 * @param params
	 * @return
	 * @throws:
	 * @time: 2018年7月19日 下午6:25:19
	 */
	List<UserCouponDetail> findBgCouponDetail(Map<String, Object> params);

	/**
	 * @Title: addUCRelate
	 * @Description: 发放优惠券
	 * @param params
	 *            入参
	 * @throws:
	 * @time: 2018年7月19日 下午3:39:42
	 */
	void addUCRelate(Map<String, Object> params);

	/**
	 * @Title: getGoodsClassifyName
	 * @Description:
	 * @param ccids
	 * @return
	 * @throws:
	 * @time: 2018年7月24日 下午4:29:29
	 */
	String getGoodsClassifyName(@Param("ccids") String[] ccids);

	/**
	 * @Title: batchAddUCRelate
	 * @Description:
	 * @param ucr
	 * @throws:
	 * @time: 2018年7月26日 下午4:44:26
	 */
	void batchAddUCRelate(@Param("ucr") List<UserCouponRelate> ucr);

	/**
	 * @Title: getUserListNum
	 * @Description: 获取用户数量
	 * @param param
	 * @return
	 * @throws:
	 * @time: 2018年7月27日 下午6:23:38
	 */
	int getUserListNum(Map<String, Object> param);

	/**
	 * @Title: findUserList
	 * @Description: 获取用户列表
	 * @param param
	 * @return
	 * @throws:
	 * @time: 2018年7月23日 下午12:03:07
	 */
	List<UserInfo> findUserList(Map<String, Object> param);

	/**
	 * @Title: checkEnableCoupon
	 * @Description: 卡券可用性检测
	 * @param couponid
	 * @param userid
	 * @return
	 * @throws:
	 * @time: 2018年8月2日 下午3:52:32
	 * @author cjy
	 */
	boolean checkEnableCoupon(@Param("couponid") String couponid, @Param("userid") String userid);

	boolean checkEnableSaler(@Param("couponid") String couponid, @Param("userid") String userid);

	/**
	 * @Title: findCouponClassify
	 * @Description: 获取分类
	 * @param split
	 * @return
	 * @throws:
	 * @time: 2018年8月3日 下午1:57:37
	 * @author cjy
	 */
	List<GoodsClassify> findCouponClassify(String[] ids);

	/**
	 * @Title: findGoods
	 * @Description: 获取商品
	 * @param split
	 * @return
	 * @throws:
	 * @time: 2018年8月3日 下午2:18:29
	 * @author cjy
	 */
	List<GoodsModel> findGoods(String[] ids);

	/**
	 * @Title: findSotre
	 * @Description: 获取门店
	 * @param ids
	 * @return
	 * @throws:
	 * @time: 2018年8月3日 下午2:40:48
	 * @author cjy
	 */
	List<Store> findSotre(String[] ids);

	/**
	 * @Title: updateOrderReateIdClear
	 * @Description: 清空订单中优惠券相关id
	 * @param orderid
	 * @throws:
	 * @time: 2018年8月29日 下午12:25:41
	 */
	@Update("update t_order set coupon_relate_id='' where order_id=#{orderid}")
	void updateOrderReateIdClear(String orderid);

	/**
	 * @Title: checkEnableCoupon1
	 * @Description: 
	 * @param couponid
	 * @param userid
	 * @return
	 * @throws:
	 * @time: 2018年8月30日 上午11:48:04
	 * @author cjy
	 */
	@Select("select saler_qr_num salerQrNum, experiencestore_qr_num experiencestoreQrNum from t_user_userinfo where id=#{id}")
	UserInfoModel checkEnableCoupon1(String userid);

}
