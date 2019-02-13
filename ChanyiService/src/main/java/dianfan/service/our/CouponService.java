package dianfan.service.our;

import java.util.Map;

import dianfan.entities.UserCouponRelate;
import dianfan.models.ResultBean;

public interface CouponService {

	/**
	 * @Title: findCouponList
	 * @Description: 根据用户id获取多条优惠券数据
	 * @param id
	 *            用户名
	 * @return List<Coupon> 优惠券列表
	 * @throws:
	 * @time: 2018年6月28日 上午11:02:29
	 */
	Map<String, Object> findCouponList(String id, Integer userused, Integer pageNum, Integer count, String used);

	/**
	 * @Title: updateCoupon
	 * @Description:
	 * @param ucr
	 * @throws:
	 * @time: 2018年7月9日 下午12:46:52
	 */
	void updateCoupon(UserCouponRelate ucr);
	
	/**
	 * @Title: findBgCouponList
	 * @Description: 
	 * @param couponname 优惠券名称
	 * @param coupontype 分类
	 * @param couponendtimestart 结束日期（start）
	 * @param couponendtimeend 结束日期（end）
	 * @param couponapply 应用
	 * @param couponreducemoneystart 
	 * @param couponreducemoneyend
	 * @param couponstarttimestart
	 * @param couponstarttimeend
	 * @param createtimestart
	 * @param createtimeend
	 * @param page
	 * @param pagecounts
	 * @return
	 * @throws:
	 * @time: 2018年7月18日 下午12:45:11
	 */
	ResultBean findBgCouponList(String couponname,String coupontype,String couponendtimestart,String couponendtimeend,String couponapply,String couponreducemoneystart,String couponreducemoneyend,String couponstarttimestart,String couponstarttimeend,String createtimestart, String createtimeend,int page,int pagecounts);
	
	/**
	 * @Title: addBgCoupon
	 * @Description: 
	 * @param couponname 优惠券名称
	 * @param coupontype 优惠券类型
	 * @param couponendtime 优惠券结束时间
	 * @param couponapply 使用(01:应用02：停止应用)
	 * @param couponreducemoney  优惠券优惠金额
	 * @param couponstarttime 优惠券投放时间
	 * @param couponcondtion 优惠券满足条件
	 * @param coupondes 优惠券描述
	 * @param couponclassifyid 商品分类id（可多选，如果为空，全场通用）
	 * @param goodsid 商品id
	 * @param storeid 门店id
	 * @throws:
	 * @time: 2018年7月18日 下午3:17:22
	 */
	void addBgCoupon(String userid,String couponname,String coupontype,String couponendtime,String couponapply,String couponreducemoney,String couponstarttime,String couponcondtion,String coupondes,String couponclassifyid,String goodsid,String storeid);
	/**
	 * @Title: updateBgCoupon
	 * @Description: 修改优惠券
	 * @param couponid 优惠券id
	 * @param couponname 优惠券名称
	 * @param coupontype 优惠券类型
	 * @param couponendtime 优惠券结束时间
	 * @param couponapply 使用(01:应用02：停止应用)
	 * @param couponreducemoney  优惠券优惠金额
	 * @param couponstarttime 优惠券投放时间
	 * @param couponcondtion 优惠券满足条件
	 * @param coupondes 优惠券描述
	 * @param couponclassifyid 商品分类id（可多选，如果为空，全场通用）
	 * @param goodsid 商品id
	 * @param storeid 门店id
	 * @throws:
	 * @time: 2018年7月18日 下午3:17:22
	 */
	void updateBgCoupon(String userid,String couponid,String couponname,String coupontype,String couponendtime,String couponapply,
			String couponreducemoney,String couponstarttime,String couponcondtion,String coupondes,String couponclassifyid,String goodsid, String storeid);
    /**
     * @Title: delBgCoupon
     * @Description: 
     * @param userid
     * @param couponid
     * @throws:
     * @time: 2018年7月19日 下午1:49:31
     */
	void delBgCoupon(String userid,String couponid);
	
	/**
	 * @Title: getBgCouponDetail
	 * @Description: 获取优惠券详情
	 * @param couponid 优惠券id
	 * @return
	 * @throws:
	 * @time: 2018年7月19日 下午2:36:02
	 */
	ResultBean findBgCouponDetail(String couponname,String coupontype,String couponendtimestart,String couponendtimeend,String couponapply,String couponreducemoneystart,String couponreducemoneyend,String couponstarttimestart,String couponstarttimeend,String createtimestart, String createtimeend,String drawdatestart,String drawdateend,String useddatestart,String useddateend,String usedendtimestart,String usedendtimeend,String userused,String nickName,String telno,int page,int pagecounts);
	/**
	 * @Title: addUCRelate 
	 * @Description: 发放优惠券
	 * @param userid 用户id
	 * @param couponid 优惠券id
	 * @param usedendtime 截止日期
	 * @throws:
	 * @time: 2018年7月19日 下午3:37:40
	 */
	ResultBean addUCRelate(String userid,String couponid,String usedendtime,String name, String telno, String source,
			String starttime, String endtime, String role, String sex, String areacode);

	/**
	 * @Title: checkEnableCoupon
	 * @Description: 卡券可用性检测
	 * @param couponid 优惠券id
	 * @param userid 用户id
	 * @return
	 * @throws:
	 * @time: 2018年8月2日 下午3:34:09
	 * @author cjy
	 */
	boolean checkEnableCoupon(String couponid, String userid);
}
