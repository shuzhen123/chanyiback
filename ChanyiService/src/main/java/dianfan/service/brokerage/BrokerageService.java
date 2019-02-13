package dianfan.service.brokerage;

import dianfan.models.ResultBean;

/**
 * @ClassName BrokerageService
 * @Description 易盟佣金流水相关 接口
 * @author sz
 * @date 2018年7月25日 下午1:46:22
 */
public interface BrokerageService {


	/**
	 * @Title: findBrokerageList
	 * @Description: 获取佣金流水列表
	 * @param userid 登陆者id
	 * @param page 请求页面
	 * @param pageSize 每页请求数 
	 * @param starttime 开始时间
	 * @param endtime 结束时间
	 * @param role 角色
	 * @param lowfee 最底佣金
	 * @param upfee 最高佣金
	 * @param user 用户
	 * @return
	 * @throws:
	 * @time: 2018年7月25日 下午2:06:00
	 */
	ResultBean findBrokerageList(String userid, int page, int pageSize, String starttime, String endtime, String role,
			String bdlowfee, String bdupfee, String user,String code);
	/**
	 * @Title: findWithdrawDepositList
	 * @Description: 用户提现流水表
	 * @param userid 登陆者id
	 * @param page 请求页面
	 * @param pageSize 每页请求数 
	 * @param starttime 开始时间
	 * @param endtime 结束时间
	 * @param role 角色
	 * @param lowfee 最底提现
	 * @param upfee 最高提现
	 * @param user 用户
	 * @return
	 * @throws:
	 * @time: 2018年7月25日 下午4:16:30
	 */
	ResultBean findWithdrawDepositList(String userid, int page, int pageSize, String starttime, String endtime,
			String role, String lowfee, String upfee, String user);

	/**
	 * @Title: withdrawalApproval
	 * @Description: 提现申请审批
	 * @param userid  userid
	 * @param flag 状态（1:审批通过 2:审批不通过）
	 * @param id 流水ID
	 * @return
	 * @throws:
	 * @time: 2018年7月25日 下午5:24:24
	 */
	ResultBean updatawithdrawalApproval(String userid, String flag, String ids,String bankFReason,String bankReceiptNo);



	

	
	
	
}
