/**  
* @Title: WithdrawCashController.java
* @Package dianfan.controller.our
* @Description: TODO
* @author yl
* @date 2018年7月2日 上午10:09:17
* @version V1.0  
*/
package dianfan.controller.our;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;

import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

import dianfan.annotations.SystemControllerLog;
import dianfan.constant.ConstantIF;
import dianfan.constant.ResultApiMsg;
import dianfan.entities.our.UserInfoModel;
import dianfan.models.ResultBean;
import dianfan.models.TokenModel;
import dianfan.service.impl.RedisTokenService;
import dianfan.service.our.PersonalInfoService;
import dianfan.service.our.WithdrawCashService;
import dianfan.util.RegexUtils;

/**
 * @ClassName WithdrawCashController
 * @Description
 * @author yl
 * @date 2018年7月2日 上午10:09:17
 */
@Scope(WebApplicationContext.SCOPE_REQUEST)
@Controller
@RequestMapping(value = "/withdraw")
public class WithdrawCashController {

	/**
	 * 注入：#withdrawCashService redisTokenService personalInfoService
	 */
	@Autowired
	private WithdrawCashService withdrawCashService;
	@Autowired
	private RedisTokenService redisTokenService;
	@Autowired
	private PersonalInfoService personalInfoService;

	@SystemControllerLog(method = "withdrawCashApply", logtype = ConstantIF.LOG_TYPE_1, description = "申请提现")
	@ApiOperation(value = "申请提现", httpMethod = "POST", notes = "申请提现", response = ResultBean.class)
	@RequestMapping(value = "/withdrawCashApply", method = RequestMethod.POST)
	@ResponseBody
	public ResultBean withdrawCashApply(
			@ApiParam(value = "token值") @RequestParam(value = ConstantIF.ACCESSTOKEN) String accesstoken,
			@ApiParam(value = "提现金额") @RequestParam(value = "money") String money,
			@ApiParam(value = "银行卡号") @RequestParam(value = "bankno") String bankno,
			@ApiParam(value = "提现申请人姓名") @RequestParam(value = "withdrawer") String withdrawer) {
	/**
	 * 判断银行卡号
	 */
		if (!RegexUtils.banknoRegex(bankno)) {
			return new ResultBean("2015", ResultApiMsg.C_2015);
		}
		
		TokenModel tokens = redisTokenService.getToken(accesstoken);
		if (tokens != null) {
			String userid = tokens.getUserid();
			if (StringUtils.isNotEmpty(userid)) {
				// 此处调用业务逻辑层
				UserInfoModel uim = personalInfoService.getUserInfo(userid);
				if (uim != null) {
					withdrawCashService.addWithdrawCash(userid, new BigDecimal(money),
							ConstantIF.WITHDRAW_APPLY_STATUS01, ConstantIF.BANK_STATUS01,bankno,withdrawer);
					return new ResultBean();
				} else {
					return new ResultBean("2001", ResultApiMsg.C_2001);
				}
			}
		}
		return new ResultBean("001", ResultApiMsg.C_001);

	}

}
