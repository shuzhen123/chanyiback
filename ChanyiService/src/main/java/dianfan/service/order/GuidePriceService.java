/**  
* @Title: GuidePriceService.java
* @Package dianfan.service.order
* @Description: TODO
* @author yl
* @date 2018年8月20日 上午10:42:32
* @version V1.0  
*/ 
package dianfan.service.order;

import dianfan.annotations.SystemServiceLog;
import dianfan.models.ResultBean;

/** @ClassName GuidePriceService
 * @Description 
 * @author yl
 * @date 2018年8月20日 上午10:42:32
 */
public interface GuidePriceService {
	
	/**
	 * @Title: updateOrderDiscount
	 * @Description: 导购设置折扣金额
	 * @param userid 导购userid
	 * @param orderid 订单id
	 * @param discount 折扣价格
	 * @return
	 * @throws:
	 * @time: 2018年8月20日 上午10:44:17
	 */
	ResultBean updateOrderDiscount(String userid,String orderid,String discount);
	/**
	 * @Title: getMaxDiscount
	 * @Description: 最大折扣
	 * @param userid 用户id
	 * @param orderid 订单id
	 * @return
	 * @throws:
	 * @time: 2018年8月20日 下午3:02:03
	 */
	ResultBean getMaxDiscount(String userid,String orderid);
	/**
	 * @Title: fildOrderInfo
	 * @Description: 获取用户订单详情
	 * @param userid
	 *            用户id
	 * @param orderid
	 *            订单id
	 * @return ResultBean
	 * @throws:
	 * @time: 2018年7月6日 上午10:42:55
	 */
	@SystemServiceLog(method = "queryOrderInfo", description = "获取订单详情")
	ResultBean queryOrderInfo(String orderid);

}
