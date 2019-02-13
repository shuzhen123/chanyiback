/**  
* @Title: GuidePriceMapper.java
* @Package dianfan.dao.mapper.order
* @Description: TODO
* @author yl
* @date 2018年8月20日 上午10:46:50
* @version V1.0  
*/ 
package dianfan.dao.mapper.order;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import dianfan.entities.goods.GoodsPriceModel;
import dianfan.entities.order.OrderGoods;
import dianfan.entities.our.Goods;

/** @ClassName GuidePriceMapper
 * @Description 
 * @author yl
 * @date 2018年8月20日 上午10:46:50
 */
@Repository
public interface GuidePriceMapper {
	
	/**
	 * @Title: getIsNotGuider
	 * @Description: 判断是否是导购
	 * @param userid 用户id
	 * @return
	 * @throws:
	 * @time: 2018年8月20日 上午11:16:26
	 */
	@Select("select count(*) from t_user_role tur inner join m_role mr on tur.roleid=mr.id where mr.id='8081bddd7db111e88dd352540054a904' and tur.userid=#{userid}")
	int getIsNotGuider(String userid);
	
	/**
	 * @Title: getIsNotGuider
	 * @Description: 判断是否是普通人
	 * @param userid 用户id
	 * @return
	 * @throws:
	 * @time: 2018年8月20日 上午11:16:26
	 */
	@Select("select count(*) from t_user_role tur inner join m_role mr on tur.roleid=mr.id inner join t_order tor on tor.user_id=tur.userid where mr.id='808c51e37db111e88dd352540054a904' and tor.order_id=#{orderid}")
	int getIsNotEveryman(String orderid);
	
	/**
	 * @Title: getOrderPrice
	 * @Description: 获取订单实付金额
	 * @param orderid 订单id
	 * @return
	 * @throws:
	 * @time: 2018年8月20日 上午11:29:15
	 */
	@Select("select pay_fee from t_order where order_id=#{orderid}")
	BigDecimal getOrderPrice(String orderid);
	
	/**
	 * @Title: findOrderGoods
	 * @Description: 获取商品id
	 * @param orderid 订单id
	 * @return
	 * @throws:
	 * @time: 2018年8月20日 下午1:23:08
	 */
	@Select("select DISTINCT tog.goods_id goodsId,tog.goods_price_id goodsPriceId from t_order_goods tog inner join t_goods_list tgl on tog.goods_id=tgl.id where tog.order_id=#{orderid}")
	List<OrderGoods> findOrderGoods(String orderid);
	/**
	 * @Title: findGoodsInfo
	 * @Description: 获取商品信息
	 * @param goodsids 商品id
	 * @return
	 * @throws:
	 * @time: 2018年8月20日 下午2:54:06
	 */
	List<Goods> findGoodsInfo(@Param("goodsids") List<String> goodsids);
	
	@Select("select price,spg_sale_discount spgSaleDiscount,exp_sale_discount expSaleDiscount,cps_sale_discount cpsSaleDiscount  from t_goods_list where id=#{goodsid}")
	Goods getGoodsInfo(String goodsid);
	/**
	 * @Title: updateOrderDiscount
	 * @Description: 修改订单折扣价格
	 * @param orderid 订单id
	 * @param discount 折扣金额
	 * @throws:
	 * @time: 2018年8月20日 下午2:54:27
	 */
	@Update("update t_order set spg_reduce_fee=#{discount},pay_fee=#{finalprice} where order_id=#{orderid}")
	void updateOrderDiscount(@Param("orderid") String orderid,@Param("discount") BigDecimal discount,@Param("finalprice") BigDecimal finalprice);
	/**
	 * @Title: findGoodsPrices
	 * @Description: 获取商品价格表信息
	 * @param goodspriceids 价格表ids
	 * @return
	 * @throws:
	 * @time: 2018年8月21日 上午10:09:46
	 */
	List<GoodsPriceModel> findGoodsPrices(@Param("goodspriceids") List<String> goodspriceids);
	
	@Select("select tgp.price,tgl.spg_sale_discount spgSaleDiscount,tgl.exp_sale_discount expSaleDiscount,tgl.cps_sale_discount cpsSaleDiscount from t_goods_price tgp inner join t_goods_list tgl on tgp.goods_id=tgl.id where tgp.id=#{goodspriceid}")
	GoodsPriceModel getGoodsPrice(String goodspriceid);
	
	/**
	 * @Title: bindGuideUserRelate
	 * @Description: 用户和导购绑定消费关系并情况消费商关系
	 * @param oruserid 用户id
	 * @param guserid 导购id
	 * @throws:
	 * @time: 2018年8月21日 下午5:18:57
	 */
	@Update("update t_user_userinfo orinfo set orinfo.saler_qr_num =(select grinfo.qr_num from (select ginfo.qr_num from t_user_userinfo ginfo where ginfo.id=#{guserid}) grinfo),orinfo.consumer_qr_num = null where orinfo.id=#{oruserid}")
	void bindGuideUserRelate(@Param("oruserid") String oruserid,@Param("guserid") String guserid);

}
