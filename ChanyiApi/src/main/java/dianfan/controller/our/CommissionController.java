/**  
* @Title: CommissionController.java
* @Package dianfan.controller.our
* @Description: TODO
* @author yl
* @date 2018年6月30日 下午12:40:52
* @version V1.0  
*/ 
package dianfan.controller.our;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import dianfan.entities.our.MoneyEntryExit;
import dianfan.entities.our.UserInfoModel;
import dianfan.models.ResultBean;
import dianfan.models.TokenModel;
import dianfan.service.impl.RedisTokenService;
import dianfan.service.our.CommissionService;
import dianfan.service.our.PersonalInfoService;

/** @ClassName CommissionController
 * @Description 获取佣金列表
 * @author yl
 * @date 2018年6月30日 下午12:40:52
 */
@Scope(WebApplicationContext.SCOPE_REQUEST)
@Controller
@RequestMapping(value = "/commision")
public class CommissionController {
	
	/**
	 * 注入 commissionService redisTokenService personalInfoService
	 */
	@Autowired
	private CommissionService commissionService;
	@Autowired
	private RedisTokenService redisTokenService;
	@Autowired
	private PersonalInfoService personalInfoService;
	
	@SystemControllerLog(method = "findCommissionList", logtype = ConstantIF.LOG_TYPE_1, description = "获取佣金列表")
	@ApiOperation(value = "获取佣金列表", httpMethod = "GET", notes = "获取佣金列表", response = ResultBean.class)
	@RequestMapping(value = "/findCommissionList", method = RequestMethod.GET)
	@ResponseBody
	public ResultBean findCommissionList(@ApiParam(value = "token值") @RequestParam(value =ConstantIF.ACCESSTOKEN) String accesstoken) {
		/*
		 * 此处做值判断
		 */
		TokenModel tokens = redisTokenService.getToken(accesstoken);
		List<MoneyEntryExit> meelist = null;
		Map<String, Object> results = new HashMap<String, Object>();
		if (tokens !=null) {
			String userid = tokens.getUserid();
			if (StringUtils.isNotEmpty(userid)) {
				//此处调用业务逻辑层
				UserInfoModel uim = personalInfoService.getUserInfo(userid);
				if (uim !=null) {
					meelist = commissionService.findUserMoneyEntryExit(userid);
					UserInfoModel uinfo = personalInfoService.getUserInfo(userid);
					BigDecimal lastmoney = uinfo.getLastMoney();
					results.put("meelist", meelist);
					results.put("lastmoney", lastmoney);
					return new ResultBean(results);
				}
			}
			}
		return new ResultBean("001",ResultApiMsg.C_001);
		
	}

}
