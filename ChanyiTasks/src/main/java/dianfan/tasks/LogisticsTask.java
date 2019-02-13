package dianfan.tasks;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import dianfan.service.goods.LogisticsServices;

/**
 * @ClassName LogisticsTask
 * @Package dianfan.tasks
 * @Description 物流相关 Task
 * @author sz
 * @date 2018年7月26日 上午9:45:46
 * @version V1.0
 */
@Controller
public class LogisticsTask {

	
	/**
	 * 注入： #LogisticsServices
	 */
	@Autowired
	LogisticsServices logisticsServices;
	
	
	/**
	 * @Title: tradeSerSignin
	 * @Description: 货物到达(正常下单) -> 自动签收
	 * @throws ParseException 
	 * @time: 2018年7月24日 下午5:38:28
	 */
	@Scheduled(cron = "0 0 */8 * * ?") // 每8个小时执行一次
	public void tradeSerSignin () throws ParseException {
		// 货物到达 -> 自动签收
		logisticsServices.goodsArriveForAutomaticReceipt();
	}
	

	/**
	 * @Title: tradeSerSigninToSales
	 * @Description: 1.货物到达(退/换货寄回仓库) -> 自动签收 || 2.将售后状态表中，对应订单的售后状态改成(05：待验收 )
	 * @throws ParseException 
	 * @time: 2018年7月24日 下午5:38:28
	 */
	@Scheduled(cron = "0 0 */8 * * ?") // 每8个小时执行一次
	public void tradeSerSigninToSales  () throws ParseException {
		// 货物到达(退/换货寄回仓库) -> 自动签收
		logisticsServices.goodsArriveForAutomaticReceiptToSales();
	}
	
	
	/**
	 * @Title: tradeSerSigninToSales
	 * @Description: 货物到达(退货验收失败退回) -> 自动签收 || 2.将售后状态表中，对应订单的售后状态改成(22：退货验收不成功 - 待收货 ) || 3.需要将订单的状态改成(43售后完成)
	 * @throws ParseException 
	 * @time: 2018年7月24日 下午5:38:28
	 */
	@Scheduled(cron = "0 0 */8 * * ?") // 每8个小时执行一次
	public void tradeSerSigninToCheck  () throws ParseException {
		// 货物到达(退货验收失败退回) -> 自动签收
		logisticsServices.goodsArriveForAutomaticReceiptToRetreatCheck();
	}
	
	
	/**
	 * @Title: tradeSerSigninToSales
	 * @Description: 货物到达(换货重新发货) -> 自动签收 || 2.将售后状态表中，对应订单的售后状态改成(82：换货验收成功 - 待收货 ) || 3.需要将订单的状态改成(43售后完成)
	 * @throws ParseException 
	 * @time: 2018年7月24日 下午5:38:28
	 */
	@Scheduled(cron = "0 0 */8 * * ?") // 每8个小时执行一次
	public void tradeSerSigninToAgain () throws ParseException {
		// 货物到达(换货重新发货) -> 自动签收
		logisticsServices.goodsArriveForAutomaticReceiptToAgain();
	}
	
	
	/**
	 * @Title: tradeSerSigninToSales
	 * @Description: 货物到达(换货验收失败退回) -> 自动签收 || 2.将售后状态表中，对应订单的售后状态改成(62：换货验收不成功 - 待收货 ) || 3.需要将订单的状态改成(43售后完成)
	 * @throws ParseException 
	 * @time: 2018年7月24日 下午5:38:28
	 */
	@Scheduled(cron = "0 0 */8 * * ?") // 每8个小时执行一次
	public void tradeSerSigninToTrade () throws ParseException {
		// 货物到达(换货验收失败退回) -> 自动签收
		logisticsServices.goodsArriveForAutomaticReceiptToTrade();
	}
	
	
	/**
	 * @Title: systemConfirmReceipt
	 * @Description: 货物已到达后,用户7天后没有点击确认收货 -> 系统将自动确认收货，计算分润等。
	 * @throws ParseException 
	 * @time: 2018年7月24日 下午5:38:28
	 */
	@Scheduled(cron = "0 0 */8 * * ?") // 每8个小时执行一次
	public void systemConfirmReceipt () throws ParseException {
		// 货物自动签收后7天，用户未点击确认收货，自动将自动确认收货
		logisticsServices.systemConfirmReceipt();
	}
	
}
