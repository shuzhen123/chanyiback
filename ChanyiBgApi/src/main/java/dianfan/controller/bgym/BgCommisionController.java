/**  
* @Title: BgCommisionController.java
* @Package dianfan.controller.bgym
* @Description: TODO
* @author yl
* @date 2018年7月25日 下午3:28:50
* @version V1.0  
*/ 
package dianfan.controller.bgym;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

import dianfan.annotations.SystemControllerLog;
import dianfan.constant.ConstantIF;
import dianfan.constant.ResultBgMsg;
import dianfan.models.ResultBean;
import dianfan.models.TokenModel;
import dianfan.service.impl.RedisTokenService;
import dianfan.service.our.CommissionService;

/** @ClassName BgCommisionController
 * @Description 
 * @author yl
 * @date 2018年7月25日 下午3:28:50
 */
@Scope("request")
@Controller
@RequestMapping(value = "/bgym")
public class BgCommisionController {
	
	@Autowired
	private CommissionService commissionService;
	@Autowired
	private RedisTokenService redisTokenService;
	
	/**
	 * @Title: findBgCommisionList
	 * @Description: 
	 * @param accesstoken
	 * @return
	 * @throws:
	 * @time: 2018年7月25日 下午6:28:45
	 */
	@SystemControllerLog(method = "findBgCommisionList", logtype = ConstantIF.LOG_TYPE_1, description = "获取员工佣金比例")
	@ApiOperation(value = "findBgCommisionList", httpMethod = "GET", notes = "获取员工佣金比例", response = ResultBean.class)
	@RequestMapping(value = "/findBgCommisionList", method = RequestMethod.GET)
	public @ResponseBody ResultBean findBgCommisionList(
			@ApiParam(value = "token值") @RequestParam(value = ConstantIF.ACCESSTOKEN) String accesstoken) {
		  ResultBean result = new ResultBean();
			TokenModel tokens = redisTokenService.getToken(accesstoken);
			if (tokens != null) {
		        result = commissionService.findCommissionList();
			}else {
				return new ResultBean("001",ResultBgMsg.C_001);
			}
			return result;
	}
	
	/**
	 * @Title: updateBgCommision
	 * @Description: 
	 * @param accesstoken
	 * @param commisionid
	 * @param proportion
	 * @return
	 * @throws:
	 * @time: 2018年7月25日 下午6:28:41
	 */
	@SystemControllerLog(method = "updateBgCommision", logtype = ConstantIF.LOG_TYPE_1, description = "设置员工佣金比例")
	@ApiOperation(value = "updateBgCommision", httpMethod = "POST", notes = "设置员工佣金比例", response = ResultBean.class)
	@RequestMapping(value = "/updateBgCommision", method = RequestMethod.POST)
	public @ResponseBody ResultBean updateBgCommision(
			@ApiParam(value = "token值") @RequestParam(value = ConstantIF.ACCESSTOKEN) String accesstoken,
			@ApiParam(value = "佣金设置表id") @RequestParam(value = "commisionid", required = true) String commisionid,
			@ApiParam(value = "比例") @RequestParam(value = "proportion", required = true) String proportion) {
		ResultBean result = new ResultBean();
		TokenModel tokens = redisTokenService.getToken(accesstoken);
		if (tokens != null) {
			String userid = tokens.getUserid();
			commissionService.updateCommission(userid,commisionid, proportion);
		}else {
			return new ResultBean("001",ResultBgMsg.C_001);
		}
		return result;
	}


}
