/**  
* @Title: TradeCloseTask.java
* @Package dianfan.tasks
* @Description: TODO
* @author Administrator
* @date 2018年7月9日 下午4:13:46
* @version V1.0  
*/
package dianfan.tasks;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import dianfan.constant.ConstantIF;
import dianfan.entities.order.TradeSer;
import dianfan.service.order.OrderClassService;

/**
 * @Title: TradeCloseTask.java
 * @Package dianfan.tasks
 * @Description: 交易流水相关定时任务
 * @author Administrator
 * @date 2018年7月9日 下午4:13:46
 * @version V1.0
 */
@Controller
public class TradeSerTask {
	@Autowired
	OrderClassService orderClassService;

	/**
	 * @Title: tradeSerClose
	 * @Description:发起退款申请（添加退款理由）--04- 发起退款理由
	 * @throws:
	 * @time: 2018年7月9日 下午4:15:28
	 */
	@Scheduled(cron = "0/1 * * * * ?") // 间隔1s执行
	public void tradeSerClose() {
		orderClassService.updateTradeSerPayStatus();

	}

	/**
	 * @Title: tradeSerRefund
	 * @Description: 05-退款中->更新成功退款中（退款成功的回调微信）， 否则更新退款失败
	 * @throws:
	 * @time: 2018年7月9日 下午4:15:28
	 */
	@Scheduled(cron = "0/60 * * * * ?") // 间隔60s执行
	public void tradeSerRefund() {
		List<TradeSer> ls = orderClassService.selectTradeSerPayStatus(ConstantIF.PAY_STATUS_04);
		// 要退款的订单编号
		List<String> orderIds = new ArrayList<String>();
		for (int i = 0; i < ls.size(); i++) {
			orderIds.add(ls.get(i).getOrderid());
		}
		if (orderIds.size() > 0) {
			orderClassService.orderRefund(orderIds);
		}
	}

	/**
	 * @Title: restartTradeSerRefund
	 * @Description: 退款失败的流水尝试没8小时做一次退款
	 * @throws ParseException
	 * @time: 2018年7月24日 下午5:38:28
	 */
	@Scheduled(cron = "0 0 */8 * * ?") // 每8个小时执行一次
	public void restartTradeSerRefund() {
		// 退款失败的流水尝试没8小时做一次退款
		List<TradeSer> ls = orderClassService.selectTradeSerPayStatus(ConstantIF.PAY_STATUS_07);
		// 要退款的订单编号
		List<String> orderIds = new ArrayList<String>();
		for (int i = 0; i < ls.size(); i++) {
			orderIds.add(ls.get(i).getOrderid());
		}
		if (orderIds.size() > 0) {
			orderClassService.orderRefund(orderIds);
		}
	}

}
