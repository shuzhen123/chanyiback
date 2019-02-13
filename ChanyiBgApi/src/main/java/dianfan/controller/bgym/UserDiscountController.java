/**  
* @Title: UserDiscountController.java
* @Package dianfan.controller.bgym
* @Description: TODO
* @author yl
* @date 2018年7月25日 下午5:44:56
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
import dianfan.service.userrole.UserRoleService;

/** @ClassName UserDiscountController
 * @Description 
 * @author yl
 * @date 2018年7月25日 下午5:44:56
 */
@Scope("request")
@Controller
@RequestMapping(value = "/bgym")
public class UserDiscountController {
	
	@Autowired
	private UserRoleService userRoleService;
	@Autowired
	private RedisTokenService redisTokenService;
	/**
	 * @Title: findUserDiscountList
	 * @Description: 
	 * @param accesstoken
	 * @return
	 * @throws:
	 * @time: 2018年7月25日 下午6:00:40
	 */
	@SystemControllerLog(method = "findUserDiscountList", logtype = ConstantIF.LOG_TYPE_1, description = "获取员工折扣")
	@ApiOperation(value = "findUserDiscountList", httpMethod = "GET", notes = "获取员工折扣", response = ResultBean.class)
	@RequestMapping(value = "/findUserDiscountList", method = RequestMethod.GET)
	public @ResponseBody ResultBean findUserDiscountList(
			@ApiParam(value = "token值") @RequestParam(value = ConstantIF.ACCESSTOKEN) String accesstoken) {
		  ResultBean result = new ResultBean();
			TokenModel tokens = redisTokenService.getToken(accesstoken);
			if (tokens != null) {
		        result = userRoleService.findUserDiscountList();
			}else {
				return new ResultBean("001",ResultBgMsg.C_001);
			}
			return result;
	}
	/**
	 * @Title: updateUserDiscount
	 * @Description: 
	 * @param accesstoken
	 * @param roleid
	 * @param salediscount
	 * @return
	 * @throws:
	 * @time: 2018年7月25日 下午6:00:37
	 */
	@SystemControllerLog(method = "updateUserDiscount", logtype = ConstantIF.LOG_TYPE_1, description = "设置员工折扣")
	@ApiOperation(value = "updateUserDiscount", httpMethod = "POST", notes = "设置员工折扣", response = ResultBean.class)
	@RequestMapping(value = "/updateUserDiscount", method = RequestMethod.POST)
	public @ResponseBody ResultBean updateUserDiscount(
			@ApiParam(value = "token值") @RequestParam(value = ConstantIF.ACCESSTOKEN) String accesstoken,
			@ApiParam(value = "角色表id") @RequestParam(value = "roleid", required = true) String roleid,
			@ApiParam(value = "折扣") @RequestParam(value = "salediscount", required = true) String salediscount) {
		ResultBean result = new ResultBean();
		TokenModel tokens = redisTokenService.getToken(accesstoken);
		if (tokens != null) {
			userRoleService.updateUserDiscount(roleid, salediscount);
		}else {
			return new ResultBean("001",ResultBgMsg.C_001);
		}
		return result;
	}


}
