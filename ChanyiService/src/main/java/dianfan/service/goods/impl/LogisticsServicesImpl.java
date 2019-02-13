package dianfan.service.goods.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dianfan.annotations.SystemServiceLog;
import dianfan.constant.ConstantIF;
import dianfan.dao.mapper.order.LogisticssMapper;
import dianfan.date.DateUtility;
import dianfan.entities.logistics.LogisticsModel;
import dianfan.entities.order.OrderDeliveryRelates;
import dianfan.models.ResultBean;
import dianfan.service.goods.LogisticsServices;
import dianfan.service.jd.transport.JdTransportService;
import dianfan.service.order.OrderClassService;

/**
 * @ClassName LogisticsServiceImpl
 * @Description  后台物流相关
 * @author sz
 * @date 2018年7月24日 下午5:51:10
 */
@Service
public class LogisticsServicesImpl implements LogisticsServices {
	
	/**
	 * 注入： #LogisticssMapper
	 */
	@Autowired
	private LogisticssMapper logisticsMapper;
	
	/**
	 * 注入： #JdTransportService
	 */
	@Autowired
	private JdTransportService jdTransportService;
	/**
	 * 注入： #OrderClassService
	 */
	@Autowired
	private OrderClassService orderClassService;
	

	/*
	 * (non-Javadoc)
	 * <p>Title: goodsArriveForAutomaticReceipt</p>
	 * <p>Description: 货物到达自动签收</p>
	 * link: @see dianfan.service.goods.LogisticsServices#goodsArriveForAutomaticReceipt()
	 */
	@Override
	@Transactional
	@SystemServiceLog(method = "goodsArriveForAutomaticReceipt",description = "货物到达自动签收")
	public void goodsArriveForAutomaticReceipt() {
		// 整理订单和物流关系
		List<OrderDeliveryRelates> logisticsInfo  = logisticsMapper.findLogisticsInfo();
		// 循环其中的每一组数据
		for (OrderDeliveryRelates order : logisticsInfo) {
			// 获取每一个订单下面的所有物流单号
			List<LogisticsModel> logistics = order.getLogistics();
			// 获取该订单下 一共有几条物流单号，目的是为了下面验证次订单下的所有货物是否都是到达状态
			int logisticssize = logistics.size();
			// 计数下面到达的物流信息
			int realitysize = 0;
			// 循环该数组， 将每一个物流单号循环出来
			for (LogisticsModel flowid : logistics) {
				String deliveryNo = flowid.getId();
				// 将每一个物流单号放置到京东接口中，去查询物流的信息
				ResultBean queryLwbByCondition = jdTransportService.queryLwbByCondition(deliveryNo);
				// 判断code是否是200
				if ("200".equals(queryLwbByCondition.getCode())) {
					// 获取返回的数据状态
					Object data2 = queryLwbByCondition.getData();
					
					if (!(data2 instanceof String)) {
						if (data2 instanceof List<?>) {
							List<Map<Object,Object>> data = (List<Map<Object,Object>>) queryLwbByCondition.getData();
							// 获取返回的list中的第一个map中的status,   “1960”视为到达 暂定！
							if(data != null && data.size() >= 1){
								Map<Object,Object> map =(Map<Object,Object>)data.get(0);
								// 如果返回信息显示已签收，就将订单物流相关 表中，物流对应的订单状态该为“02：到达签收”
								if (1960 == (int)map.get("status")) {
									realitysize += 1;
								}
							}
						}
					}
				
				}
			}
			// 如果该订单下的所有商品都已经到达，将对应订单状态该为 到达签收
			if (realitysize == logisticssize) {
				// 获取物流主键ID
				String id = order.getId();
				// 查询 订单物流相关 表 ，将对应订单状态该为 到达签收，并且添加货物到达的时间
				logisticsMapper.updataOrderDeliveryFlag(id);
			}
		
		}
	}

	
	
	/*
	 * (non-Javadoc)
	 * <p>Title: goodsArriveForAutomaticReceiptToSales</p>
	 * <p>Description: 退换货物流 到达自动签收</p>
	 * link: @see dianfan.service.goods.LogisticsServices#goodsArriveForAutomaticReceiptToSales()
	 */
	@Override
	@Transactional
	@SystemServiceLog(method = "goodsArriveForAutomaticReceipt",description = "退换货物流 到达自动签收")
	public void goodsArriveForAutomaticReceiptToSales() {
		// 整理订单和物流关系
		List<OrderDeliveryRelates> logisticsInfo  = logisticsMapper.findLogisticsInfoToSales();
		// 循环其中的每一组数据
		for (OrderDeliveryRelates order : logisticsInfo) {
			// 获取每一个订单下面的所有物流单号
			List<LogisticsModel> logistics = order.getLogistics();
			// 获取该订单下 一共有几条物流单号，目的是为了下面验证次订单下的所有货物是否都是到达状态
			int logisticssize = logistics.size();
			// 计数下面到达的物流信息
			int realitysize = 0;
			// 循环该数组， 将每一个物流单号循环出来
			for (LogisticsModel flowid : logistics) {
				String deliveryNo = flowid.getId();
				// 将每一个物流单号放置到京东接口中，去查询物流的信息
				ResultBean queryLwbByCondition = jdTransportService.queryLwbByCondition(deliveryNo);
				// 判断code是否是200
				if ("200".equals(queryLwbByCondition.getCode())) {
					// 获取返回的数据状态
					Object data2 = queryLwbByCondition.getData();
					
					if (!(data2 instanceof String)) {
						if (data2 instanceof List<?>) {
							List<Map<Object,Object>> data = (List<Map<Object,Object>>) queryLwbByCondition.getData();
							// 获取返回的list中的第一个map中的status,   “1960”视为到达 暂定！
							if(data != null && data.size() >= 1){
								Map<Object,Object> map =(Map<Object,Object>)data.get(0);
								// 如果返回信息显示已签收，就将订单物流相关 表中，物流对应的订单状态该为“02：到达签收”
								if (1960 == (int)map.get("status")) {
									realitysize += 1;
								}
							}
						}
					}
				
				}
			}
			// 如果该订单下的所有商品都已经到达，将对应订单状态该为 到达签收
			if (realitysize == logisticssize) {
				// 获取物流主键ID
				String id = order.getId();
				// 查询 订单物流相关 表 ，将对应订单状态该为 到达签收，并且添加货物到达的时间
				logisticsMapper.updataOrderDeliveryFlag(id);
				// 将售后表中，该订单的 售后状态 改成（05：待验收）
				logisticsMapper.updataAfterSale(id,"05");
			}
		
		}
	}
	
	/*
	 * (non-Javadoc)
	 * <p>Title: goodsArriveForAutomaticReceiptToTrade</p>
	 * <p>Description: 换货验收失败退回</p>
	 * link: @see dianfan.service.goods.LogisticsServices#goodsArriveForAutomaticReceiptToSales()
	 */
	@Override
	@Transactional
	@SystemServiceLog(method = "goodsArriveForAutomaticReceiptToTrade",description = "换货验收失败退回")
	public void goodsArriveForAutomaticReceiptToTrade() {
		// 整理订单和物流关系
		List<OrderDeliveryRelates> logisticsInfo  = logisticsMapper.findLogisticsInfoToTrade();
		// 循环其中的每一组数据
		for (OrderDeliveryRelates order : logisticsInfo) {
			// 获取每一个订单下面的所有物流单号
			List<LogisticsModel> logistics = order.getLogistics();
			// 获取该订单下 一共有几条物流单号，目的是为了下面验证次订单下的所有货物是否都是到达状态
			int logisticssize = logistics.size();
			// 计数下面到达的物流信息
			int realitysize = 0;
			// 循环该数组， 将每一个物流单号循环出来
			for (LogisticsModel flowid : logistics) {
				String deliveryNo = flowid.getId();
				// 将每一个物流单号放置到京东接口中，去查询物流的信息
				ResultBean queryLwbByCondition = jdTransportService.queryLwbByCondition(deliveryNo);
				// 判断code是否是200
				if ("200".equals(queryLwbByCondition.getCode())) {
					// 获取返回的数据状态
					Object data2 = queryLwbByCondition.getData();
					
					if (!(data2 instanceof String)) {
						if (data2 instanceof List<?>) {
							List<Map<Object,Object>> data = (List<Map<Object,Object>>) queryLwbByCondition.getData();
							// 获取返回的list中的第一个map中的status,   “1960”视为到达 暂定！
							if(data != null && data.size() >= 1){
								Map<Object,Object> map =(Map<Object,Object>)data.get(0);
								// 如果返回信息显示已签收，就将订单物流相关 表中，物流对应的订单状态该为“02：到达签收”
								if (1960 == (int)map.get("status")) {
									realitysize += 1;
								}
							}
						}
					}
				
				}
			}
			// 如果该订单下的所有商品都已经到达，将对应订单状态该为 到达签收
			if (realitysize == logisticssize) {
				// 获取物流主键ID
				String id = order.getId();
				// 查询 订单物流相关 表 ，将对应订单状态该为 到达签收，并且添加货物到达的时间
				logisticsMapper.updataOrderDeliveryFlag(id);
				//将售后表中，该订单的 售后状态 改成（62：换货验收不成功 - 待收货 ）
				logisticsMapper.updataAfterSale(id,"62");
				// 将订单的状态改成售后已经完成43
				logisticsMapper.updataOrderFlag(id);
			}
		}
	}
	
	
	
	/*
	 * (non-Javadoc)
	 * <p>Title: goodsArriveForAutomaticReceiptToSales</p>
	 * <p>Description: (退货验收失败退回)物流 : 到达自动签收</p>
	 * link: @see dianfan.service.goods.LogisticsServices#goodsArriveForAutomaticReceiptToSales()
	 */
	@Transactional
	@Override
	@SystemServiceLog(method = "goodsArriveForAutomaticReceiptToRetreatCheck",description = "(退货验收失败退回)物流 : 到达自动签收")	
	public void goodsArriveForAutomaticReceiptToRetreatCheck() {
		// 整理订单和物流关系
		List<OrderDeliveryRelates> logisticsInfo  = logisticsMapper.findLogisticsInfoToRetreatCheck();
		// 循环其中的每一组数据
		for (OrderDeliveryRelates order : logisticsInfo) {
			// 获取每一个订单下面的所有物流单号
			List<LogisticsModel> logistics = order.getLogistics();
			// 获取该订单下 一共有几条物流单号，目的是为了下面验证次订单下的所有货物是否都是到达状态
			int logisticssize = logistics.size();
			// 计数下面到达的物流信息
			int realitysize = 0;
			// 循环该数组， 将每一个物流单号循环出来
			for (LogisticsModel flowid : logistics) {
				String deliveryNo = flowid.getId();
				// 将每一个物流单号放置到京东接口中，去查询物流的信息
				ResultBean queryLwbByCondition = jdTransportService.queryLwbByCondition(deliveryNo);
				// 判断code是否是200
				if ("200".equals(queryLwbByCondition.getCode())) {
					// 获取返回的数据状态
					Object data2 = queryLwbByCondition.getData();
					
					if (!(data2 instanceof String)) {
						if (data2 instanceof List<?>) {
							List<Map<Object,Object>> data = (List<Map<Object,Object>>) queryLwbByCondition.getData();
							// 获取返回的list中的第一个map中的status,   “1960”视为到达 暂定！
							if(data != null && data.size() >= 1){
								Map<Object,Object> map =(Map<Object,Object>)data.get(0);
								// 如果返回信息显示已签收，就将订单物流相关 表中，物流对应的订单状态该为“02：到达签收”
								if (1960 == (int)map.get("status")) {
									realitysize += 1;
								}
							}
						}
					}
				
				}
			}
			// 如果该订单下的所有商品都已经到达，将对应订单状态该为 到达签收
			if (realitysize == logisticssize) {
				// 获取物流主键ID
				String id = order.getId();
				// 查询 订单物流相关 表 ，将对应订单状态该为 到达签收，并且添加货物到达的时间
				logisticsMapper.updataOrderDeliveryFlag(id);
				//将售后表中，该订单的 售后状态 改成（22：退货验收不成功 - 待收货 ）
				logisticsMapper.updataAfterSale(id,"22");
				// 将订单的状态改成售后已经完成43
				logisticsMapper.updataOrderFlag(id);
			}
		}
	}



	/*
	 * (non-Javadoc)
	 * <p>Title: goodsArriveForAutomaticReceiptToAgain</p>
	 * <p>Description: (换货重新发货)物流 : 到达自动签收</p>
	 * link: @see dianfan.service.goods.LogisticsServices#goodsArriveForAutomaticReceiptToSales()
	 */
	@Transactional
	@Override
	@SystemServiceLog(method = "goodsArriveForAutomaticReceiptToAgain",description = "(换货重新发货)物流 : 到达自动签收")	
	public void goodsArriveForAutomaticReceiptToAgain() {
		// 整理订单和物流关系
		List<OrderDeliveryRelates> logisticsInfo  = logisticsMapper.findLogisticsInfoToRetreatAgain();
		// 循环其中的每一组数据
		for (OrderDeliveryRelates order : logisticsInfo) {
			// 获取每一个订单下面的所有物流单号
			List<LogisticsModel> logistics = order.getLogistics();
			// 获取该订单下 一共有几条物流单号，目的是为了下面验证次订单下的所有货物是否都是到达状态
			int logisticssize = logistics.size();
			// 计数下面到达的物流信息
			int realitysize = 0;
			// 循环该数组， 将每一个物流单号循环出来
			for (LogisticsModel flowid : logistics) {
				String deliveryNo = flowid.getId();
				// 将每一个物流单号放置到京东接口中，去查询物流的信息
				ResultBean queryLwbByCondition = jdTransportService.queryLwbByCondition(deliveryNo);
				// 判断code是否是200
				if ("200".equals(queryLwbByCondition.getCode())) {
					// 获取返回的数据状态
					Object data2 = queryLwbByCondition.getData();
					
					if (!(data2 instanceof String)) {
						if (data2 instanceof List<?>) {
							List<Map<Object,Object>> data = (List<Map<Object,Object>>) queryLwbByCondition.getData();
							// 获取返回的list中的第一个map中的status,   “1960”视为到达 暂定！
							if(data != null && data.size() >= 1){
								Map<Object,Object> map =(Map<Object,Object>)data.get(0);
								// 如果返回信息显示已签收，就将订单物流相关 表中，物流对应的订单状态该为“02：到达签收”
								if (1960 == (int)map.get("status")) {
									realitysize += 1;
								}
							}
						}
					}
				
				}
			}
			// 如果该订单下的所有商品都已经到达，将对应订单状态该为 到达签收
			if (realitysize == logisticssize) {
				// 获取物流主键ID
				String id = order.getId();
				// 查询 订单物流相关 表 ，将对应订单状态该为 到达签收，并且添加货物到达的时间
				logisticsMapper.updataOrderDeliveryFlag(id);
				//将售后表中，该订单的 售后状态 改成（82：换货验收成功 - 待收货 ）
				logisticsMapper.updataAfterSale(id,"82");
				// 将订单的状态改成售后已经完成43
				logisticsMapper.updataOrderFlag(id);
			}
		}
	}
	

	/*
	 * (non-Javadoc)
	 * <p>Title: systemConfirmReceipt</p>
	 * <p>Description: 系统自动确认收货</p>
	 * link: @see dianfan.service.goods.LogisticsServices#systemConfirmReceipt()
	 */
	@Override
	@Transactional
	@SystemServiceLog(method = "systemConfirmReceipt",description = "系统自动确认收货")
	public void systemConfirmReceipt() throws ParseException {
		// 整理订单和物流关系
		List<OrderDeliveryRelates> logisticsInfo  = logisticsMapper.findLogisticsInfodelivery();
		
		for (OrderDeliveryRelates order : logisticsInfo) {
			// 确认用户是否已经自己确认了收货
			int type = logisticsMapper.getOrderFlagByOrderId(order.getOrderId());
			if (type != 0) {
				// 订单的到货时间
				Date delivery_time = logisticsMapper.getDeliveryTime(order.getOrderId());
				// 获取该订单的到货时间 并且➕上7天
				Date deliveryTime = DateUtility.getAddDayToTimeEnd(delivery_time, ConstantIF.CONFIRM_RECEIPT_SECONDS);
				// 获取当前时间
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
				String time = df.format(new Date());
				Date date=df.parse(time);
				// 比较大小
				int i = deliveryTime.compareTo(date); 
				if (i < 0) {
					// 说明过了7天用户还没有点击确认收货，系统将自动点击确认收货
					orderClassService.acceptOrderForXt(order.getOrderId());
				}
			}
		}
	}

}
