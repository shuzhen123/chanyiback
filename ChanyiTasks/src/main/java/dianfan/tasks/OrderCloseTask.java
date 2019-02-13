/**  
* @Title: OrderCloseTask.java
* @Package dianfan.task
* @Description: 订单关闭定时任务
* @author hj
* @date 2018年3月2日 下午3:34:20
* @version V1.0  
*/
package dianfan.tasks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import dianfan.service.order.OrderClassService;
import dianfan.service.our.CouponService;

/**
 * @Title: OrderCloseTask.java
 * @Package dianfan.tasks
 * @Description: 订单关闭定时任务
 * @author hj
 * @date 2018年3月2日 下午3:34:20
 * @version V1.0
 */
@Controller
public class OrderCloseTask {
	@Autowired
	OrderClassService orderClassService;

	@Autowired
	CouponService couponService;

	/**
	 * @Title: espOrderClose
	 * @Description: 关闭易拼订单【待付款超过应该付款截止时间的】
	 * @throws Exception
	 * @throws:
	 * @time: 2018年7月7日 下午7:36:35
	 */
	@Scheduled(cron = "0/1 * * * * ?") // 间隔1s执行
	public void espOrderCloseOutPay() throws Exception {
		// 关闭易拼订单【待付款超过应该付款截止时间的】
		orderClassService.espOrderCloseOutPay();
	}

	/**
	 * @Title: espOrderClose
	 * @Description: 关闭易拼订单【付款了但未拼满并且订单超过易拼发起截止时间】
	 * @throws Exception
	 * @throws:
	 * @time: 2018年7月7日 下午7:36:35
	 */
	@Scheduled(cron = "0/1 * * * * ?") // 间隔1s执行
	public void espOrderCloseOutEspEndTime() throws Exception {
		// 付款了但未拼满并且订单超过易拼发起截止时间
		orderClassService.espOrderCloseOutEspEndTime();
	}

	/**
	 * @Title: returnCoupon
	 * @Description: 返还主动关闭和被动关闭的非易拼订单，返还被动关闭的易拼订单
	 * @throws Exception
	 * @throws:
	 * @time: 2018年8月29日 上午11:12:21
	 */
	@Scheduled(cron = "0/1 * * * * ?") // 间隔1s执行
	public void returnCoupon() throws Exception {
		// 查询返还主动关闭和被动关闭的非易拼订单,返还主动关闭的和被动关闭的易拼订单
		orderClassService.returnCouponCloseOrders();
	}

}
