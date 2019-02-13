package dianfan.service.order.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dianfan.dao.mapper.order.OrderClassMapper;
import dianfan.entities.order.AfterSale;
import dianfan.entities.order.Order;
import dianfan.entities.order.OrderGoods;
import dianfan.entities.order.TradeSer;
import dianfan.service.order.OrderCommissionService;
import dianfan.service.order.PayNotifyService;

/**
 * @ClassName PayNotifyServiceImpl
 * @Description 支付异步通知服务
 * @author cjy
 * @date 2018年7月7日 下午3:46:12
 */
@Service
public class PayNotifyServiceImpl implements PayNotifyService {

	@Autowired
	private OrderCommissionService orderCommissionService;
	@Autowired
	private OrderClassMapper orderClassMapper;
	/*
	 * (non-Javadoc)
	 * <p>Title: alipayNotify</p>
	 * <p>Description: 支付宝支付处理</p>
	 * @param params
	 * @param pid
	 * @param appid
	 * @return
	 * link: @see dianfan.service.order.PayNotifyService#alipayNotify(java.util.Map, java.lang.String, java.lang.String)
	 */
	@Transactional
	@Override
	public boolean orderNotify(String out_trade_no, BigDecimal total_amount, String trade_no) {
		// 1、商户需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，params.get("out_trade_no")
		Order order = orderClassMapper.findTradeSerByserialNumber(out_trade_no);
		if (order == null) {
			//无此订单
			return true;
		}

		// 2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额），
		if (total_amount.compareTo(order.getPayFee()) != 0) {
			// 此处金额不匹配，做日志处理
			return false;
		}

		// 校验通过
		
		//是否为易拼订单
		if(StringUtils.equals(order.getGoodsType(), "02")) {
			//是易拼订单
			//根据拼团id获取已付款订单
			List<Order> es_order = orderClassMapper.getEasySpellingOrdersCount(order.getEasySpellingId());
			
			//根据拼团id获取拼团参数的人数
			int person_count = orderClassMapper.getEasySpellingPersonCount(order.getEasySpellingId());
			
			if(es_order.size()+1 == person_count) {
				es_order.add(order);
				//拼团且付款人数已达到要求
				//更改其它拼团订单状态order_status=02-待审核
				for(Order o : es_order) {
					//分配商品到工厂
					//获取订单收货地址对应的工厂
					String factoryid = orderClassMapper.findFactoryIdFromAddrid(o.getAddressId());
					if(factoryid != null) {
						//有可以分配的工厂
						o.setFactoryListId(factoryid);
						o.setGoodsStatus("10");
					}else {
						//无可分配的工厂
						o.setGoodsStatus("11");
					}
					o.setOrderStatus("02");
					orderClassMapper.updateOrderStatus(o);
					//佣金计算
					orderCommissionService.calculateCommission(o.getOrderid());
					//清空绑定关系
					orderClassMapper.updateBindRelation(o.getUserid());
				}
			}else {
				//拼团付款人数未达到要求
				//更改本次订单状态order_status=61付款未拼满
				order.setOrderStatus("61");
				orderClassMapper.updateOrderStatus(order);
			}
		}else {
			//普通订单
			//分配商品到工厂
			//获取订单收货地址对应的工厂
			String factoryid = orderClassMapper.findFactoryIdFromAddrid(order.getAddressId());
			if(factoryid != null) {
				//有可以分配的工厂
				order.setFactoryListId(factoryid);
				order.setGoodsStatus("10");
			}else {
				//无可分配的工厂
				order.setGoodsStatus("11");
			}
			//佣金计算
			orderCommissionService.calculateCommission(order.getOrderid());
			//更改订单状态
			order.setOrderStatus("02");
			orderClassMapper.updateOrderStatus(order);
			//清空绑定关系
			orderClassMapper.updateBindRelation(order.getUserid());
		}
		//更新交易流水表
		order.setTradeNo(trade_no);
		orderClassMapper.updateOrderTradeSer(order);
		
		//获取订单中商品id
		List<String> ids = orderClassMapper.findOrderGoodsIds(order.getOrderid());
		//商品销量+1
		orderClassMapper.goodsSalesCountInc(ids);
		
		
		String userid = order.getUserid();
		// 判断是否是体验店下单
		int type = orderClassMapper.getFindUserType(userid);
		// 体验店下单后，插入t_experiencestore_order
		if (type == 1) {
			// 获取利用订单ID，查询出订单下的商品
			String orderid = order.getOrderid();
			// 查询订单下的商品
			List<OrderGoods> orderGoods = orderClassMapper.findorderGoods(orderid);
			// 获取体验店的ID
			String shopId = orderClassMapper.getshopId(orderid);
			// 创建入参条件 
			Map<String, Object> param = new HashMap<>();
			param.put("orderGoods", orderGoods);
			param.put("shopId", shopId);
			param.put("orderid", orderid);
			// 插入到体验店订单表  表中
			orderClassMapper.addExperiencestoreOrder(param);
		}
		
		// 成功
		return true;
	}
	
	/*
	 * (non-Javadoc)
	 * <p>Title: orderRefundNotify</p>
	 * <p>Description: 退款处理</p>
	 * @param out_refund_no 商户退款单号
	 * @param refund_fee 退款金额
	 * @param return_msg 返回信息
	 * @return
	 * link: @see dianfan.service.order.PayNotifyService#orderRefundNotify(java.lang.String, java.math.BigDecimal, java.lang.String)
	 */
	@Transactional
	@Override
	public boolean orderRefundNotify(String out_refund_no, BigDecimal refund_fee, String return_msg) {
		TradeSer tradeSer = orderClassMapper.getTradeSerDataByPayid(out_refund_no);
		if(tradeSer == null) {
			//无此退款单号订单
			return true;
		}
		
		if(return_msg == null) {
			//退款成功
			
			if(refund_fee.compareTo(tradeSer.getRefundMoney()) != 0) {
				//退款金额与订单请求退款金额不一致
				return false;
			}
			
			//校验成功
			tradeSer.setOrderStatus("06");
		}else {
			//退款异常
			tradeSer.setOrderStatus("07");
			tradeSer.setRefundFailReason(return_msg);
		}
		
		//更新流水表
		orderClassMapper.updateTradeSerRefundStatus(tradeSer);
		//更新订单表
		Order o = new Order();
		o.setOrderid(tradeSer.getOrderid());
		o.setOrderStatus("21");
		orderClassMapper.updateOrderRefundStatus(o);
		return true;
	}

	/* (non-Javadoc)
	 * <p>Title: returnGoodsOrderRefundNotify</p>
	 * <p>Description: </p>
	 * @param out_refund_no
	 * @param settlement_refund_fee
	 * @param return_msg
	 * @return
	 * link: @see dianfan.service.order.PayNotifyService#returnGoodsOrderRefundNotify(java.lang.String, java.math.BigDecimal, java.lang.String)
	 */ 
	@Override
	@Transactional
	public boolean returnGoodsOrderRefundNotify(String out_refund_no, BigDecimal refund_fee,
			String return_msg) {
		TradeSer tradeSer = orderClassMapper.getTradeSerDataByPayid(out_refund_no);
		if(tradeSer == null) {
			//无此退款单号订单
			return true;
		}
		
		if(return_msg == null) {
			//退款成功
			
			if(refund_fee.compareTo(tradeSer.getRefundMoney()) != 0) {
				//退款金额与订单请求退款金额不一致
				return false;
			}
			//校验成功
			tradeSer.setOrderStatus("06");
			AfterSale as = new AfterSale();
			as.setResult("41");
			as.setOrderId(tradeSer.getOrderid());
			orderClassMapper.updateAfterSales(as);
		}else {
			//退款异常
			tradeSer.setOrderStatus("07");
			tradeSer.setRefundFailReason(return_msg);
		}
		
		//更新流水表
		orderClassMapper.updateTradeSerRefundStatus(tradeSer);
		//更新订单表
		Order o = new Order();
		o.setOrderid(tradeSer.getOrderid());
		o.setOrderStatus("43");
		orderClassMapper.updateOrderRefundStatus(o);
		return true;
	}

}
