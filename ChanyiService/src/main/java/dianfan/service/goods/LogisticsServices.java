package dianfan.service.goods;

import java.text.ParseException;

/**
 * @ClassName LogisticsService
 * @Description 后台物流相关 接口
 * @author sz
 * @date 2018年7月24日 下午5:49:47
 */
public interface LogisticsServices {

	/**
	 * @Title: goodsArriveForAutomaticReceipt
	 * @Description: (正常下单)物流 :货物到达自动签收
	 * @throws:
	 * @time: 2018年7月24日 下午5:46:46
	 */
	void goodsArriveForAutomaticReceipt();
	
	/**
	 * @Title: goodsArriveForAutomaticReceipt
	 * @Description: (退换货物流)物流 :到达自动签收 
	 * @throws:
	 * @time: 2018年7月24日 下午5:46:46
	 */
	void goodsArriveForAutomaticReceiptToSales();
	
	/**
	 * @Title: goodsArriveForAutomaticReceipt
	 * @Description: (退货验收失败退回)物流 : 到达自动签收
	 * @throws:
	 * @time: 2018年7月24日 下午5:46:46
	 */
	void goodsArriveForAutomaticReceiptToRetreatCheck();
	
	
	/**
	 * @Title: goodsArriveForAutomaticReceipt
	 * @Description: (换货重新发货)物流 : 到达自动签收
	 * @throws:
	 * @time: 2018年7月24日 下午5:46:46
	 */
	void goodsArriveForAutomaticReceiptToAgain();
	
	/**
	 * @Title: goodsArriveForAutomaticReceipt
	 * @Description: (换货验收失败退回)物流 : 到达自动签收
	 * @throws:
	 * @time: 2018年7月24日 下午5:46:46
	 */
	void goodsArriveForAutomaticReceiptToTrade();
	

	/**
	 * @throws ParseException 
	 * @Title: systemConfirmReceipt
	 * @Description: 系统自动确认收货
	 * @throws:
	 * @time: 2018年8月3日 下午3:08:47
	 */
	void systemConfirmReceipt() throws ParseException;
}
