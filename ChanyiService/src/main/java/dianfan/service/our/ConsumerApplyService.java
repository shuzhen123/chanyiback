/**  
* @Title: ConsumerApplyService.java
* @Package dianfan.service.our
* @Description: TODO
* @author yl
* @date 2018年6月30日 下午1:17:27
* @version V1.0  
*/ 
package dianfan.service.our;

import dianfan.models.ResultBean;

/** @ClassName ConsumerApplyService
 * @Description 申请成为消费商service
 * @author yl
 * @date 2018年6月30日 下午1:17:27
 */
public interface ConsumerApplyService {
	
	/**
	 * @Title: addConsumerApply
	 * @Description: 
	 * @param userid 申请人id
	 * @throws:
	 * @time: 2018年6月30日 下午1:25:39
	 */
	void addConsumerApply(String userid,String applystatus,String roledistinguish);
	/**
	 * @Title: updateConsumerApply
	 * @Description: 后台审批修改
	 * @param userid 申请人id
	 * @throws:
	 * @time: 2018年6月30日 下午1:25:39
	 */
	void updateConsumerApply(String userid,String consumerid,String applyUserId,String applystatus,String fReason,String roledistinguish);
	
	/**
	 * @Title: getConsumerApply
	 * @Description: 判断是否已经是消费商
	 * @param userid
	 * @return
	 * @throws:
	 * @time: 2018年6月30日 下午1:26:47
	 */
	int getConsumerApply(String userid);
	/**
	 * @Title: findConsumerList
	 * @Description: 
	 * @param status 状态
	 * @param nickName 昵称
	 * @param createTimeStart 创建时间start
	 * @param createTimeEnd 创建时间end
	 * @return
	 * @throws:
	 * @time: 2018年7月20日 下午2:34:44
	 */
	ResultBean findConsumerList(String status,String nickName,String createTimeStart,String createTimeEnd,int page,int pageSize);

}
