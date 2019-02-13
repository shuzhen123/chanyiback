package dianfan.controller.bgym;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;

import com.wordnik.swagger.annotations.ApiParam;

import dianfan.annotations.SystemControllerLog;
import dianfan.constant.ConstantIF;
import dianfan.constant.ResultBgMsg;
import dianfan.models.ResultBean;
import dianfan.models.TokenModel;
import dianfan.service.brokerage.BrokerageService;
import dianfan.service.impl.RedisTokenService;

/**
 * @ClassName BrokerageController
 * @Description 易盟佣金流水相关 Controller
 * @author sz
 * @date 2018年7月25日 下午1:40:18
 */
@Scope(WebApplicationContext.SCOPE_REQUEST)
@Controller
@RequestMapping(value = "/brokerage")
public class BrokerageController {
	
	
	/**
	 * 注入: #RedisTokenService
	 */
	@Autowired
	private RedisTokenService redisTokenService;
	/**
	 * 注入: #RedisTokenService
	 */
	@Autowired
	private BrokerageService brokerageService;
	
	
	
	
	/**
	 * @Title: findBrokerageList
	 * @Description: 获取佣金流水列表
	 * @param accesstoken accesstoken
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
	 * @time: 2018年7月25日 下午2:07:34
	 */
	@SystemControllerLog(method = "findBrokerageList", logtype = ConstantIF.LOG_TYPE_2, description = "获取佣金流水列表")
	@RequestMapping(value = "/findBrokerageList", method = RequestMethod.POST)	
	public @ResponseBody ResultBean findBrokerageList (
			@ApiParam(value="accesstoken") @RequestParam(value=ConstantIF.ACCESSTOKEN) String accesstoken,
			@ApiParam(value="请求页") @RequestParam(value=ConstantIF.PAGE, defaultValue=ConstantIF.PAGE_START_STR) int page,
			@ApiParam(value="每页条数") @RequestParam(value=ConstantIF.PAGE_SIZE, defaultValue=ConstantIF.PAGE_OFFSET_STR) int pageSize,
			@ApiParam(value="按开始时间") @RequestParam(value="starttime",required=false) String starttime,
			@ApiParam(value="按结束时间") @RequestParam(value="endtime",required=false) String endtime,
			@ApiParam(value="按角色") @RequestParam(value="role",required=false) String role,
			@ApiParam(value="最低提成额") @RequestParam(value="lowfee",required=false) String lowfee,
			@ApiParam(value="最高提成额") @RequestParam(value="upfee",required=false) String upfee,
			@ApiParam(value="按用户搜索") @RequestParam(value="user",required=false) String user,
			@ApiParam(value="按市搜索") @RequestParam(value="code",required=false) String code
			) {
		// 构建返回模型
		ResultBean result = new ResultBean();
		TokenModel token = redisTokenService.getToken(accesstoken);
		if (org.springframework.util.StringUtils.isEmpty(token)) {
			// 登陆信息已经过期
			return new ResultBean("001",ResultBgMsg.C_001);
		}
		String userid = token.getUserid();
		
		
		result = brokerageService.findBrokerageList(userid,page,pageSize,starttime,endtime,role,lowfee,upfee,user,code);
		// 返回
		return result;
		
		
	}
	
	
	/**
	 * @Title: findWithdrawDepositList
	 * @Description: 获取用户提现流水表
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
	 * @time: 2018年7月25日 下午4:17:29
	 */
	@SystemControllerLog(method = "findWithdrawDepositList", logtype = ConstantIF.LOG_TYPE_2, description = "获取提现流水列表")
	@RequestMapping(value = "/findWithdrawDepositList", method = RequestMethod.POST)	
	public @ResponseBody ResultBean findWithdrawDepositList (
			@ApiParam(value="accesstoken") @RequestParam(value=ConstantIF.ACCESSTOKEN) String accesstoken,
			@ApiParam(value="请求页") @RequestParam(value=ConstantIF.PAGE, defaultValue=ConstantIF.PAGE_START_STR) int page,
			@ApiParam(value="每页条数") @RequestParam(value=ConstantIF.PAGE_SIZE, defaultValue=ConstantIF.PAGE_OFFSET_STR) int pageSize,
			@ApiParam(value="按开始时间") @RequestParam(value="starttime",required=false) String starttime,
			@ApiParam(value="按结束时间") @RequestParam(value="endtime",required=false) String endtime,
			@ApiParam(value="按角色") @RequestParam(value="role",required=false) String role,
			@ApiParam(value="最低提现额") @RequestParam(value="lowfee",required=false) String lowfee,
			@ApiParam(value="最高提现额") @RequestParam(value="upfee",required=false) String upfee,
			@ApiParam(value="按用户搜索") @RequestParam(value="user",required=false) String user
			) {
		// 构建返回模型
		ResultBean result = new ResultBean();
		TokenModel token = redisTokenService.getToken(accesstoken);
		if (org.springframework.util.StringUtils.isEmpty(token)) {
			// 登陆信息已经过期
			return new ResultBean("001",ResultBgMsg.C_001);
		}
		String userid = token.getUserid();
		
		result = brokerageService.findWithdrawDepositList(userid,page,pageSize,starttime,endtime,role,lowfee,upfee,user);
		// 返回
		return result;
		
	}
	
	
	
	
	/**
	 * @Title: withdrawalApproval
	 * @Description: 提现申请审批
	 * @param accesstoken accesstoken
	 * @param flag 状态（1:审批通过 2:审批不通过）
	 * @param id 流水IDs
	 * @return
	 * @throws:
	 * @time: 2018年7月25日 下午5:23:37
	 */
	@SystemControllerLog(method = "withdrawalApproval", logtype = ConstantIF.LOG_TYPE_2, description = "提现申请审批")
	@RequestMapping(value = "/withdrawalApproval", method = RequestMethod.POST)
	public @ResponseBody ResultBean withdrawalApproval (
			@ApiParam(value="accesstoken") @RequestParam(value=ConstantIF.ACCESSTOKEN) String accesstoken,
			@ApiParam(value="状态（1:审批通过 2:审批不通过）") @RequestParam(value="flag") String flag,
			@ApiParam(value="流水ID") @RequestParam(value="ids") String ids,
			@ApiParam(value="银行提现失败原因") @RequestParam(value="bankFReason") String bankFReason,
			@ApiParam(value="银行回执号") @RequestParam(value="bankReceiptNo") String bankReceiptNo
			) {
		// 构建返回模型
		ResultBean result = new ResultBean();
		TokenModel token = redisTokenService.getToken(accesstoken);
		if (org.springframework.util.StringUtils.isEmpty(token)) {
			// 登陆信息已经过期
			return new ResultBean("001",ResultBgMsg.C_001);
		}
		String userid = token.getUserid();
		result = brokerageService.updatawithdrawalApproval(userid, flag, ids,bankFReason,bankReceiptNo);
		// 返回
		return result;
	}

}
