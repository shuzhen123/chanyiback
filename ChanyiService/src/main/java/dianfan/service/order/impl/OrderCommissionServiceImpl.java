package dianfan.service.order.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dianfan.constant.ConstantIF;
import dianfan.dao.mapper.order.OrderCommissionMapper;
import dianfan.entities.commission.GoodsCommission;
import dianfan.entities.commission.UserBindRealtion;
import dianfan.entities.commission.UserBounsDetail;
import dianfan.entities.commission.UserLastMoney;
import dianfan.entities.commission.UserRoleDist;
import dianfan.entities.order.Order;
import dianfan.service.order.OrderCommissionService;
/**
 * @ClassName OrderCommissionServiceImpl
 * @Description 佣金计算服务
 * @author cjy
 * @date 2018年7月4日 下午5:43:31
 */
@Service
public class OrderCommissionServiceImpl implements OrderCommissionService {

	@Autowired
	private OrderCommissionMapper orderCommissionMapper;
	
	/*
	 * (non-Javadoc)
	 * <p>Title: calculateCommission</p>
	 * <p>Description: 订单佣金计算-新</p>
	 * @param orderid
	 * @author cjy
	 * link: @see dianfan.service.order.OrderCommissionService#calculateCommission(java.lang.String)
	 * @time: 2018年8月23日
	 */
	@Transactional
	@Override
	public void calculateCommission(String orderid) {
		//根据订单id获取对应的用户id、用户角色、绑定的消费关系
		UserBindRealtion relation = orderCommissionMapper.getUserConsumeRelation(orderid);
		//订单对应的角色区分
		String role = relation.getRoleDistinguish();
		
		//过滤不参与佣金分润的角色区分(01:大区经理, 99:合伙人, 02:运营服务商, 03:市场开发经理, 04:城市经理, 05:体验店, 06:导购, 07:消费商, 08:普通人)
		if(!StringUtils.equals(role, ConstantIF.ROLE_DISTINGUISH05) && 
				!StringUtils.equals(role, ConstantIF.ROLE_DISTINGUISH06) && 
				!StringUtils.equals(role, ConstantIF.ROLE_DISTINGUISH07) && 
				!StringUtils.equals(role, ConstantIF.ROLE_DISTINGUISH08)) return;
		//获取订单总金额、实付金额、优惠券金额、折扣金额
		Order money = orderCommissionMapper.getOrderMoney(orderid);
		
		if(money.getTotalFee().compareTo(new BigDecimal(0)) == 0) {
			return;
		}
		
		//获取订单的商品价格信息
		List<GoodsCommission> commission = orderCommissionMapper.findOrderGoodsInfo(orderid);
		if(commission.size() < 1) {
			//订单中无商品，报错
			throw new RuntimeException("订单"+orderid+"中无商品信息，无法参与佣金计算！");
		}
		
		// 收货地址对应的 运营服务商id/城市经理id
		UserRoleDist osp_cm = orderCommissionMapper.getOSPOrCMByAddr(orderid);
		
		//提成数据队列
		List<UserBounsDetail> bouns_data = new ArrayList<>();
		
		if(StringUtils.equals(role, ConstantIF.ROLE_DISTINGUISH05)) {
			//05:体验店购买(运营服务商、城市经理 佣金计算-上下级关系)
			
			//运营服务商id/城市经理id
			if(osp_cm != null) bounsData(money, osp_cm.getRoleDistinguish(), commission, osp_cm.getUserid(), orderid, bouns_data);
		}else if(StringUtils.equals(role, ConstantIF.ROLE_DISTINGUISH06)) {
			//06:导购购买(上级体验店、运营服务商、城市经理 佣金计算-上下级关系)
			//导购->上级体验店
			String storeid = orderCommissionMapper.getStoreBySalerid(relation.getUserid());
			bounsData(money, role, commission, storeid, orderid, bouns_data);
			
			//运营服务商id/城市经理id
			if(osp_cm != null) bounsData(money, osp_cm.getRoleDistinguish(), commission, osp_cm.getUserid(), orderid, bouns_data);
		}else if(StringUtils.equals(role, ConstantIF.ROLE_DISTINGUISH07)) {
			//07:消费商购买(运营服务商、城市经理 佣金计算-地区关系)
			
			//运营服务商id/城市经理id
			if(osp_cm != null && StringUtils.equals(osp_cm.getRoleDistinguish(), ConstantIF.ROLE_DISTINGUISH02)) 
				bounsData(money, osp_cm.getRoleDistinguish(), commission, osp_cm.getUserid(), orderid, bouns_data);
		}else if(StringUtils.equals(role, ConstantIF.ROLE_DISTINGUISH08)) {
			//普通人购买
			if(relation.getSalerid() == null && relation.getConsumerid() == null && relation.getStoreid() == null) {
				//未绑定任何关系
				
				//运营服务商id/城市经理id
				if(osp_cm != null && StringUtils.equals(osp_cm.getRoleDistinguish(), ConstantIF.ROLE_DISTINGUISH02)) bounsData(money, osp_cm.getRoleDistinguish(), commission, osp_cm.getUserid(), orderid, bouns_data);
			}else if(relation.getSalerid() == null && relation.getConsumerid() != null && relation.getStoreid() == null) {
				//只绑定消费商
				
				//消费商佣金计算
				bounsData(money, ConstantIF.ROLE_DISTINGUISH07, commission, relation.getConsumerid(), orderid, bouns_data);
				
				//运营服务商id/城市经理id
				if(osp_cm != null && StringUtils.equals(osp_cm.getRoleDistinguish(), ConstantIF.ROLE_DISTINGUISH02)) bounsData(money, osp_cm.getRoleDistinguish(), commission, osp_cm.getUserid(), orderid, bouns_data);
			}else if(relation.getSalerid() != null && relation.getConsumerid() == null && relation.getStoreid() == null) {
				//只绑定导购
				
				//导购佣金计算
				bounsData(money, ConstantIF.ROLE_DISTINGUISH06, commission, relation.getSalerid(), orderid, bouns_data);
				//导购->上级体验店
				String storeid = orderCommissionMapper.getStoreBySalerid(relation.getSalerid());
				if(storeid != null) bounsData(money, ConstantIF.ROLE_DISTINGUISH05 + "-1", commission, storeid, orderid, bouns_data);
				//运营服务商id/城市经理id
				if(osp_cm != null) bounsData(money, osp_cm.getRoleDistinguish(), commission, osp_cm.getUserid(), orderid, bouns_data);
			}else if(relation.getSalerid() == null && relation.getConsumerid() == null && relation.getStoreid() != null) {
				//只绑定体验店
				
				//体验店佣金（导购不拿佣金）
				bounsData(money, ConstantIF.ROLE_DISTINGUISH05 + "-2", commission, relation.getStoreid(), orderid, bouns_data);
				//运营服务商id/城市经理id
				if(osp_cm != null) bounsData(money, osp_cm.getRoleDistinguish(), commission, osp_cm.getUserid(), orderid, bouns_data);
			}else if(relation.getSalerid() != null && relation.getConsumerid() == null && relation.getStoreid() != null) {
				//绑定导购+体验店
				
				//判断是否使用了线下红包
				boolean use_coupon = orderCommissionMapper.checkOfflineCouponType(orderid);
				if(use_coupon) {
					//使用了线下红包，只分配体验店及以上等级的人的佣金
					//体验店佣金
					bounsData(money, ConstantIF.ROLE_DISTINGUISH05 + "-2", commission, relation.getStoreid(), orderid, bouns_data);
				}else {
					//未使用线下红包
					//导购佣金
					bounsData(money, ConstantIF.ROLE_DISTINGUISH06, commission, relation.getSalerid(), orderid, bouns_data);
					//体验店佣金
					bounsData(money, ConstantIF.ROLE_DISTINGUISH05 + "-1", commission, relation.getStoreid(), orderid, bouns_data);
				}
				
				//运营服务商id/城市经理id
				if(osp_cm != null) bounsData(money, osp_cm.getRoleDistinguish(), commission, osp_cm.getUserid(), orderid, bouns_data);
			}else if(relation.getSalerid() == null && relation.getConsumerid() != null && relation.getStoreid() != null) {
				//绑定消费商+体验店
				
				//判断是否使用了线下红包
				boolean use_coupon = orderCommissionMapper.checkOfflineCouponType(orderid);
				if(use_coupon) {
					//使用了线下红包，只分配体验店及以上等级的人的佣金
					//体验店佣金
					bounsData(money, ConstantIF.ROLE_DISTINGUISH05 + "-2", commission, relation.getStoreid(), orderid, bouns_data);
				}else {
					//未使用线下红包
					//消费商佣金
					bounsData(money, ConstantIF.ROLE_DISTINGUISH07, commission, relation.getConsumerid(), orderid, bouns_data);
					//体验店佣金
					bounsData(money, ConstantIF.ROLE_DISTINGUISH05 + "-1", commission, relation.getStoreid(), orderid, bouns_data);
				}
				
				//运营服务商id/城市经理id
				if(osp_cm != null) bounsData(money, osp_cm.getRoleDistinguish(), commission, osp_cm.getUserid(), orderid, bouns_data);
			}
		}
		
		//插入佣金数据
		if(!bouns_data.isEmpty()) {
			orderCommissionMapper.addCommission(bouns_data);
		}
	}
	
	/**
	 * @Title: bounsData
	 * @Description: 佣金计算
	 * @param order_price 订单实付总金额
	 * @param couponMoney 订单使用的优惠券金额
	 * @param role_dist 佣金计算对象
	 * @param commission 商品折扣关系
	 * @param bouns_data 提成数据队列
	 * @throws:
	 * @time: 2018年8月23日 上午11:45:53
	 * @author cjy
	 */
	private void bounsData(
			Order money, //订单各项金额
			String role_dist, //佣金计算对象
			List<GoodsCommission> commission, //商品折扣关系
			String userid, //佣金分配用户id
			String orderid, //订单id
			List<UserBounsDetail> bouns_data) {
		UserBounsDetail ubd = new UserBounsDetail();
		
		BigDecimal total_fee = new BigDecimal(0);
		
		//除法保留位数
		int retain = 8;
		//进位方式
		int carrySaveArray = BigDecimal.ROUND_HALF_DOWN;
		
		
		if(StringUtils.equals(ConstantIF.ROLE_DISTINGUISH02, role_dist)) {  
			//运营服务商佣金计算:(体验店折扣 - 运营服务商折扣) * (零售价-如果用了优惠券) * 数量
			for(GoodsCommission gc : commission) {
				//体验店折扣 - 运营服务商折扣
				BigDecimal bd1 = gc.getExpDiscount().subtract(gc.getCpsDiscount());
				//优惠券
				BigDecimal bd3 = gc.getPrice().multiply(money.getCouponReduceFee()).divide(money.getTotalFee(), retain, carrySaveArray);
				//零售价-如果用了优惠券
				BigDecimal bd2 = gc.getPrice().subtract(bd3);
				
				BigDecimal total = bd1.multiply(bd2).multiply(new BigDecimal(gc.getGoodsCount()));
				
				total_fee = total_fee.add(total);
			}
		}else if(StringUtils.equals(ConstantIF.ROLE_DISTINGUISH04, role_dist)) {
			//城市经理佣金:(零售价-如果用了优惠券)*运营服务商折扣*0.1 * 数量
			for(GoodsCommission gc : commission) {
				//优惠券
				BigDecimal bd1 = gc.getPrice().multiply(money.getCouponReduceFee()).divide(money.getTotalFee(), retain, carrySaveArray);
				BigDecimal total = gc.getPrice().subtract(bd1).multiply(gc.getCpsDiscount())
						.multiply(new BigDecimal(0.1)).multiply(new BigDecimal(gc.getGoodsCount()));
				total_fee = total_fee.add(total);
			}
		}else if(StringUtils.equals("05-1", role_dist)) {
			//体验店佣金(导购拿佣金):(原价 - 如果用了优惠券) * （导购折扣-体验店折扣）			
			for(GoodsCommission gc : commission) {
				//导购折扣-体验店折扣
				BigDecimal bd1 = gc.getSpgDiscount().subtract(gc.getExpDiscount());
				//优惠券
				BigDecimal bd3 = gc.getPrice().multiply(money.getCouponReduceFee()).divide(money.getTotalFee(), retain, carrySaveArray);
				//零售价-如果用了优惠券
				BigDecimal bd2 = gc.getPrice().subtract(bd3);
				
				BigDecimal total = bd2.multiply(bd1).multiply(new BigDecimal(gc.getGoodsCount()));
				total_fee = total_fee.add(total);
			}
		}else if(StringUtils.equals("05-2", role_dist)) {
			//体验店佣金(导购不拿佣金):[(原价 - 如果用了优惠券)*(1 - 体验店折扣)] * 数量      - 如果导购打了折
			for(GoodsCommission gc : commission) {
				//优惠券
				BigDecimal bd3 = gc.getPrice().multiply(money.getCouponReduceFee()).divide(money.getTotalFee(), retain, carrySaveArray);
				//零售价-如果用了优惠券
				BigDecimal bd1 = gc.getPrice().subtract(bd3);
				//1 - 体验店折扣
				BigDecimal bd2 = new BigDecimal(1).subtract(gc.getExpDiscount());
				
				BigDecimal total = bd1.multiply(bd2).multiply(new BigDecimal(gc.getGoodsCount()));  
				total_fee = total_fee.add(total);
			}
			total_fee = total_fee.subtract(money.getDiscountFee());
		}else if(StringUtils.equals(ConstantIF.ROLE_DISTINGUISH06, role_dist)) {
			//导购佣金:(原价 - 如果用了优惠券) * （1-导购折扣） - 如果导购打了折
			for(GoodsCommission gc : commission) {
				//优惠券
				BigDecimal bd3 = gc.getPrice().multiply(money.getCouponReduceFee()).divide(money.getTotalFee(), retain, carrySaveArray);
				//零售价-如果用了优惠券
				BigDecimal bd1 = gc.getPrice().subtract(bd3);
				//1 - 体验店折扣
				BigDecimal bd2 = new BigDecimal(1).subtract(gc.getSpgDiscount());
				BigDecimal total = bd1.multiply(bd2).multiply(new BigDecimal(gc.getGoodsCount()));
				total_fee = total_fee.add(total);
			}
			total_fee = total_fee.subtract(money.getDiscountFee());
		}else if(StringUtils.equals(ConstantIF.ROLE_DISTINGUISH07, role_dist)) {
			//消费商佣金:(原价 - 如果用了优惠券) * 0.15
			for(GoodsCommission gc : commission) {
				//优惠券
				BigDecimal bd3 = gc.getPrice().multiply(money.getCouponReduceFee()).divide(money.getTotalFee(), retain, carrySaveArray);
				//零售价-如果用了优惠券
				BigDecimal total = gc.getPrice().subtract(bd3).multiply(new BigDecimal(0.15)).multiply(new BigDecimal(gc.getGoodsCount()));
				total_fee = total_fee.add(total);
			}
		}else return; 
		
		//分佣金的用户id
		ubd.setUserId(userid);
		//订单id
		ubd.setOrderNo(orderid);
		//获取佣金比例
		
		//提成总额(精度为小数点后一位数值)
		ubd.setBounsFee(total_fee.setScale(2, BigDecimal.ROUND_HALF_UP));
		//当前用户余额
		BigDecimal lastMoney = orderCommissionMapper.getLastMoney(userid);
		lastMoney = lastMoney.add(ubd.getBounsFee());
		//当前余额(精度为小数点后一位数值)
		ubd.setcLastMoney(lastMoney.setScale(2, BigDecimal.ROUND_HALF_UP));
		//添加到佣金队列
		bouns_data.add(ubd);
		//账户余额处理
//		orderCommissionMapper.updateCLastMoney(userid, ubd.getcLastMoney());
	}
	
	/*
	 * (non-Javadoc)
	 * <p>Title: commissionReturn</p>
	 * <p>Description: 退货返佣金</p>
	 * @param orderid 订单id
	 * link: @see dianfan.service.order.OrderCommissionService#commissionReturn(java.lang.String)
	 */
	@Transactional
	@Override
	public void commissionReturn(String orderid) {
		//根据订单id获取订单提成的人员userid
		List<UserBounsDetail> users_commission = orderCommissionMapper.findCommissionUser(orderid);
		
		if(users_commission.isEmpty()) return;
		
		//获取人员的剩余提现金额
		List<String> ids = new ArrayList<>();
		Map<String, BigDecimal> uc = new HashMap<>();
		for(UserBounsDetail ubd : users_commission) {
			ids.add(ubd.getUserId());
			uc.put(ubd.getUserId(), ubd.getBounsFee());
		}
		List<UserLastMoney> userLastMoney = orderCommissionMapper.findUserLastMoney(ids);
		
		//余额计算
		for(UserLastMoney ulm : userLastMoney) {
			//账户余额
			BigDecimal lastMoney = ulm.getLastMoney();
			//返还金额
			BigDecimal returnMoney = uc.get(ulm.getUserid());
			
			lastMoney = lastMoney.subtract(returnMoney);
			ulm.setLastMoney(lastMoney.setScale(1, BigDecimal.ROUND_HALF_UP));
		}
		//更新提成状态
		orderCommissionMapper.updateUserBounsDetail(orderid, "02");
		
		//更新余额
		orderCommissionMapper.updateUserLastMoney(userLastMoney);
	}

}
