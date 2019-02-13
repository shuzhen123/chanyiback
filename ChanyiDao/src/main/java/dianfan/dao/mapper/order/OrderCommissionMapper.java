/**  
* @Title: WithdrawCashMapper.java
* @Package dianfan.dao.mapper.our
* @Description: TODO
* @author yl
* @date 2018年7月2日 上午10:39:13
* @version V1.0  
*/ 
package dianfan.dao.mapper.order;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import dianfan.entities.commission.GoodsCommission;
import dianfan.entities.commission.UserBindRealtion;
import dianfan.entities.commission.UserBounsDetail;
import dianfan.entities.commission.UserLastMoney;
import dianfan.entities.commission.UserRoleDist;
import dianfan.entities.order.Order;

/**
 * @ClassName OrderCommissionMapper
 * @Description 佣金dao
 * @author cjy
 * @date 2018年7月4日 下午5:46:22
 */
@Repository
public interface OrderCommissionMapper {

	/**
	 * @Title: getUserConsumeRelation
	 * @Description: 根据订单id获取对应的用户id及绑定的消费关系
	 * @param orderid 订单id
	 * @return
	 * @throws:
	 * @time: 2018年7月4日 下午6:07:04
	 */
	UserBindRealtion getUserConsumeRelation(String orderid);
	
	/**
	 * @Title: getOrderTotalMoney
	 * @Description: 获取订单各项金额
	 * @param orderid
	 * @return
	 * @throws:
	 * @time: 2018年8月24日 下午1:17:54
	 * @author cjy
	 */
	@Select("select total_fee totalFee, pay_fee payFee, IFNULL(coupon_reduce_fee,0) couponReduceFee, IFNULL(spg_reduce_fee,0)discountFee from t_order where order_id=#{orderid}")
	Order getOrderMoney(String orderid);

	/**
	 * @Title: getLastMoney
	 * @Description: 根据用户id获取余额
	 * @param userid 用户id
	 * @return
	 * @throws:
	 * @time: 2018年7月5日 下午3:10:30
	 */
	@Select("select ifnull(last_money,0) from t_user_userinfo where id=#{userid}")
	BigDecimal getLastMoney(String userid);

	/**
	 * @Title: updateCLastMoney
	 * @Description: 更新用户账户余额
	 * @param userid 用户id
	 * @param money 余额
	 * @throws:
	 * @time: 2018年7月5日 下午4:32:52
	 */
	@Update("update t_user_userinfo set last_money=#{money} where id=#{userid}")
	void updateCLastMoney(@Param(value="userid") String userid, @Param(value="money") BigDecimal money);

	/**
	 * @Title: getStoreBySalerid
	 * @Description: 获取导购对应的体验店
	 * @param userid 导购userid
	 * @return
	 * @throws:
	 * @time: 2018年7月5日 下午4:37:46
	 */
	@Select("select upper_user_id from t_user_lower_upper_relate where down_user_id=#{userid}")
	String getStoreBySalerid(String userid);

	/**
	 * @Title: checkCouponType
	 * @Description: 判断是否使用了线下红包
	 * @param orderid 订单id
	 * @return
	 * @throws:
	 * @time: 2018年7月5日 下午6:26:12
	 */
	@Select("select count(*) from t_order od, t_user_coupon_relate ucr, t_coupon cp "+
			"where od.coupon_relate_id=ucr.id and ucr.coupon_id=cp.id and od.order_id=#{orderid}")
	boolean checkCouponType(String orderid);
	
	/**
	 * @Title: checkOfflineCouponType
	 * @Description: 检测是否使用线下红包
	 * @param orderid
	 * @return
	 * @throws:
	 * @time: 2018年8月24日 下午6:14:53
	 * @author cjy
	 */
	@Select("select count(*) from t_order od, t_user_coupon_relate ucr, t_coupon cp "+
			"where od.coupon_relate_id=ucr.id and ucr.coupon_id=cp.id and cp.coupon_type='01' and od.order_id=#{orderid}")
	boolean checkOfflineCouponType(String orderid);

	/**
	 * @Title: checkConsumerWithStoreRelation
	 * @Description: 判断导购与体验店的关系
	 * @param consumerid 导购id
	 * @param storeid 体验店id
	 * @return
	 * @throws:
	 * @time: 2018年7月5日 下午6:36:18
	 */
	@Select("select count(*) from t_user_lower_upper_relate where upper_user_id=#{storeid} and down_user_id=#{consumerid} and entkbn=0")
	boolean checkConsumerWithStoreRelation(@Param(value="consumerid") String consumerid, @Param(value="storeid") String storeid);

	/**
	 * @Title: addCommission
	 * @Description: 插入佣金数据
	 * @param bouns_data 佣金数据队列
	 * @throws:
	 * @time: 2018年7月5日 下午6:47:11
	 */
	void addCommission(List<UserBounsDetail> bouns_data);

	/**
	 * @Title: findCommissionUser
	 * @Description: 根据订单id获取订单提成的人员userid
	 * @param orderid 订单id
	 * @return
	 * @throws:
	 * @time: 2018年7月6日 上午11:22:10
	 */
	@Select("select user_id userId, bouns_fee bounsFee from t_user_bouns_detail where order_no=#{orderid} and user_bouns_status='01' and entkbn=0")
	List<UserBounsDetail> findCommissionUser(String orderid);

	/**
	 * @Title: findUserLastMoney
	 * @Description: 获取用户剩余提现金额
	 * @param ids 用户id集合
	 * @return
	 * @throws:
	 * @time: 2018年7月6日 上午11:34:11
	 */
	List<UserLastMoney> findUserLastMoney(List<String> ids);

	/**
	 * @Title: updateUserBounsDetail
	 * @Description: 更新提成状态(01：OK 02:退货03：重新发货)
	 * @param orderid
	 * @throws:
	 * @time: 2018年7月6日 下午12:16:07
	 */
	@Update("update t_user_bouns_detail set user_bouns_status=#{bounsStatus} where order_no=#{orderid} and entkbn=0")
	void updateUserBounsDetail(@Param(value="orderid") String orderid, @Param(value="bounsStatus") String bounsStatus);

	/**
	 * @Title: updateUserLastMoney
	 * @Description: 更新余额
	 * @param userLastMoney 用户对应余额列表
	 * @throws:
	 * @time: 2018年7月6日 上午11:41:53
	 */
	void updateUserLastMoney(List<UserLastMoney> userLastMoney);
	
	/* ******************************************* */

	/**
	 * @Title: findOrderGoodsInfo
	 * @Description: 获取订单的商品价格信息
	 * @param orderid 订单id
	 * @return
	 * @throws:
	 * @time: 2018年8月23日 上午11:09:44
	 * @author cjy
	 */
	@Select("select " +
			"	og.goods_id goodsid, og.unit_price price, og.num goodsCount, " +
			"	gl.spg_sale_discount spgDiscount, gl.exp_sale_discount expDiscount, gl.cps_sale_discount cpsDiscount " +
			"from " +
			"	t_order_goods og, t_goods_list gl " +
			"where " +
			"	og.goods_id=gl.id and og.order_id=#{orderid}")
	List<GoodsCommission> findOrderGoodsInfo(String orderid);
	
	/**
	 * @Title: getLeaderByStoreid
	 * @Description: 根据体验店id获取上下级关系对应的 运营服务商/城市经理
	 * @param storeid 体验店id
	 * @return
	 * @throws:
	 * @time: 2018年8月23日 下午2:15:19
	 * @author cjy
	 */
	@Select("select ulu.upper_user_id userid, role.role_distinguish roleDistinguish from  t_user_lower_upper_relate ulu, t_user_role urole, m_role role "+
			"where ulu.upper_user_id=urole.userid and urole.roleid=role.id and ulu.down_user_id=#{storeid} ")
	UserRoleDist getOSPOrCMByStoreid(String storeid);
	
	/**
	 * @Title: getOSPOrCMByAddr
	 * @Description: 根据收货地获取对应的 运营服务商/城市经理
	 * @param storeid
	 * @return
	 * @throws:
	 * @time: 2018年8月23日 下午3:23:47
	 * @author cjy
	 */
	@Select("select users.id userid, role.role_distinguish roleDistinguish " +
			"from t_order o, m_area_code area, t_userinfo_extra ext, t_user_userinfo users, t_user_role urole, m_role role " +
			"where  " +
			"	o.area_code=area.area_code and ext.area_code=area.sup_area_code and ext.id=users.extra_id and users.id=urole.userid " +
			"	and urole.roleid=role.id and (role.role_distinguish='02' or role.role_distinguish='04') and o.order_id=#{orderid} ")
	UserRoleDist getOSPOrCMByAddr(String orderid);
	
	/**
	 * @Title: getRedPacket
	 * @Description: 线上红包
	 * @param orderid 订单id
	 * @throws:
	 * @time: 2018年8月23日 下午5:03:39
	 * @author cjy
	 */
	@Select("select cp.coupon_reduce_money from t_order orders, t_user_coupon_relate ucr, t_coupon cp "+
			"where orders.coupon_relate_id=ucr.id and ucr.coupon_id=cp.id and orders.order_id=#{orderid}")
	BigDecimal getRedPacket(String orderid);

}
