/**  
* @Title: GuidePriceServiceImpl.java
* @Package dianfan.service.order.impl
* @Description: TODO
* @author yl
* @date 2018年8月20日 上午10:45:10
* @version V1.0  
*/ 
package dianfan.service.order.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dianfan.constant.ResultApiMsg;
import dianfan.dao.mapper.order.GuidePriceMapper;
import dianfan.dao.mapper.order.OrderClassMapper;
import dianfan.entities.goods.GoodsSpec;
import dianfan.entities.order.OrderGoods;
import dianfan.entities.order.OrderModel;
import dianfan.entities.our.Goods;
import dianfan.models.ResultBean;
import dianfan.service.order.GuidePriceService;
import dianfan.util.PropertyUtil;

/** @ClassName GuidePriceServiceImpl
 * @Description 
 * @author yl
 * @date 2018年8月20日 上午10:45:10
 */
@Service
public class GuidePriceServiceImpl implements GuidePriceService{
	
	@Autowired
	private GuidePriceMapper guidePriceMapper;
	@Autowired
	private OrderClassMapper orderClassMapper;

	/* (non-Javadoc)
	 * <p>Title: updateOrderDiscount</p>
	 * <p>Description: </p>
	 * @param userid
	 * @param orderid
	 * @param discount
	 * @return
	 * link: @see dianfan.service.order.GuidePriceService#updateOrderDiscount(java.lang.String, java.lang.String, java.lang.String)
	 */ 
	@Override
	@Transactional
	public ResultBean updateOrderDiscount(String userid, String orderid, String discount) {
		// TODO Auto-generated method stub
		int guides = guidePriceMapper.getIsNotGuider(userid);
		if (guides>0) {
			int everyman = guidePriceMapper.getIsNotEveryman(orderid);
			if (everyman == 0) {
				return new ResultBean("2037",ResultApiMsg.C_2037);
			} 
			//获取订单实付金额
			//BigDecimal orderPrice = guidePriceMapper.getOrderPrice(orderid);
			// 2.获取详情
			OrderModel info = orderClassMapper.getBgOrderInfo(orderid);
			BigDecimal totalfee = info.getTotalFee();
			BigDecimal disfee = info.getDiscountFee();
			BigDecimal couponfee = info.getCouponReduceFee();
			BigDecimal totalmoney = info.getTotalFee();
			if (disfee == null) {
				disfee = new BigDecimal("0.00");
			}
			if (couponfee == null) {
				couponfee = new BigDecimal("0.00");
			}
			BigDecimal gsbg = new BigDecimal("0.00");
			List<OrderGoods> oglist = info.getOrderGoods();
			if (oglist !=null && oglist.size()>0) {
				for (int i = 0; i < oglist.size(); i++) {
					BigDecimal spg = oglist.get(i).getSpgSaleDiscount();
					BigDecimal prices = oglist.get(i).getUnitPrice();
					BigDecimal num = new BigDecimal(oglist.get(i).getNum());
					//普通商品折扣价
					gsbg = gsbg.add(prices.subtract(prices.multiply(couponfee).divide(totalmoney, 4, RoundingMode.HALF_DOWN)).multiply(num).multiply(spg));
					
				}
			}
			BigDecimal realpayfee = gsbg.setScale(2, RoundingMode.HALF_UP);
			//总金额 减去 优惠金额【后台手动减免 2018/08/15 MOD】减去 优惠券减免额总【2018/08/15 ADD】
			BigDecimal realpay = totalfee.subtract(disfee).subtract(couponfee);
			if (realpay.compareTo(realpayfee) !=1) {
				return new ResultBean("2035",ResultApiMsg.C_2035);
			}else {
				//订单价格减去导购折扣
				BigDecimal gpDiscount = realpay.subtract(gsbg);
				//折扣金额
				BigDecimal bds = new BigDecimal(discount);
				if (gpDiscount.compareTo(bds) != -1) {
					BigDecimal finalPrice = realpay.subtract(bds);
					guidePriceMapper.updateOrderDiscount(orderid, bds,finalPrice);
					//绑定消费关系
					//订单用户
					String orderuserid = info.getUserId();
					guidePriceMapper.bindGuideUserRelate(orderuserid, userid);
				}else {
					return new ResultBean("2036",ResultApiMsg.C_2036);
				}
			}
		}else {
			return new ResultBean("2034",ResultApiMsg.C_2034);
		}
		
		return new ResultBean();
	}

	/* (non-Javadoc)
	 * <p>Title: getMaxDiscount</p>
	 * <p>Description: </p>
	 * @param userid
	 * @param orderid
	 * @return
	 * link: @see dianfan.service.order.GuidePriceService#getMaxDiscount(java.lang.String, java.lang.String)
	 */ 
	@Override
	public ResultBean getMaxDiscount(String userid, String orderid) {
		// TODO Auto-generated method stub
		int guides = guidePriceMapper.getIsNotGuider(userid);
		if (guides>0) {
			//获取订单实付金额
			BigDecimal orderPrice = guidePriceMapper.getOrderPrice(orderid);
			//获取商品id
			List<OrderGoods> oglist = guidePriceMapper.findOrderGoods(orderid);
			List<String> goodsids = new ArrayList<String>();
			if (oglist !=null && oglist.size()>0) {
				for (int i = 0; i < oglist.size(); i++) {
				   goodsids.add(oglist.get(i).getGoodsId());
				}
			}
			List<Goods> gslist = guidePriceMapper.findGoodsInfo(goodsids);
			//导购折扣后的金额
			BigDecimal gsbg = new BigDecimal("0.00");
			if (gslist !=null && gslist.size()>0) {
				for (int i = 0; i < gslist.size(); i++) {
					BigDecimal spg = gslist.get(i).getSpgSaleDiscount();
					BigDecimal prices = gslist.get(i).getPrice();
					gsbg.add(spg.multiply(prices));
				}
			}
			if (orderPrice.compareTo(gsbg) ==1) {
				//订单价格减去导购折扣
				BigDecimal gpDiscount = orderPrice.subtract(gsbg);
				return new ResultBean(gpDiscount);
			}else {
				return new ResultBean("2035",ResultApiMsg.C_2035);
			}
			
		}else {
			return new ResultBean("2034",ResultApiMsg.C_2034);
		}
	}

	/* (non-Javadoc)
	 * <p>Title: queryOrderInfo</p>
	 * <p>Description: </p>
	 * @param orderid
	 * @return
	 * link: @see dianfan.service.order.GuidePriceService#queryOrderInfo(java.lang.String)
	 */ 
	@Override
	public ResultBean queryOrderInfo(String orderid) {
		// 2.获取详情
		OrderModel info = orderClassMapper.queryOrderInfo(orderid);
		// 3.获取订单列表中的区域code，去省市区县表中将完整信息返回出来
		if (org.springframework.util.StringUtils.isEmpty(info)) {
			return new ResultBean(new ArrayList<>());
		}
		String code = info.getAreaCode();
		String addressCode = orderClassMapper.findAreaCode(code);
		// 将查出来的地域信息添加到详情中
		info.setAddressCode(addressCode);
		// 获取所有的规格和对应名称
		List<GoodsSpec> specList = orderClassMapper.fildSpecList();
		// 将上面查出来的数据转化成一个map 方便下面去转化
		Map<String, String> size = new HashMap<>();
		for (GoodsSpec gs : specList) {
			size.put(gs.getId(), gs.getTypeAndName());
		}
		// 获取其中的specIds
		List<OrderGoods> orderGoods = info.getOrderGoods();
		if (!org.springframework.util.StringUtils.isEmpty(orderGoods)) {
			for (OrderGoods order : orderGoods) {
				String str = "";
				String specIds = order.getSpecIds();
				if (specIds != null && "".equals(specIds)) {
					String[] s1 = specIds.split(",");
					for (int i = 0; i < s1.length; i++) {
						for (String s2 : size.keySet()) {
							if (s1[i].equals(s2)) {
								str += size.get(s2);
							}
						}
					}
					order.setSpecList(str);
				}
			}
		}

		// 3.将商品的图片的途径修改一下
		if (info != null) {
			List<OrderGoods> pic = info.getOrderGoods();
			for (OrderGoods url : pic) {
				url.setPicAddr(PropertyUtil.getProperty("domain") + url.getPicAddr());
			}
		}
		// 4.成功
		return new ResultBean(info);
	}

}
