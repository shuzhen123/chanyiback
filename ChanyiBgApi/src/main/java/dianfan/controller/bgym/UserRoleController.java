/**  
* @Title: UserRoleController.java
* @Package dianfan.controller.bgym
* @Description: TODO
* @author yl
* @date 2018年7月24日 上午10:39:48
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

/** @ClassName UserRoleController
 * @Description 
 * @author yl
 * @date 2018年7月24日 上午10:39:48
 */
@Scope("request")
@Controller
@RequestMapping(value = "/bgym")
public class UserRoleController {
	
	@Autowired
	private UserRoleService userRoleService;
	@Autowired
	private RedisTokenService redisTokenService;
	
	@SystemControllerLog(method = "findBgUserRoleList", logtype = ConstantIF.LOG_TYPE_1, description = "获取员工角色列表")
	@ApiOperation(value = "findBgUserRoleList", httpMethod = "GET", notes = "获取员工角色列表", response = ResultBean.class)
	@RequestMapping(value = "/findBgUserRoleList", method = RequestMethod.GET)
	public @ResponseBody ResultBean findBgUserRoleList(
			@ApiParam(value = "token值") @RequestParam(value = ConstantIF.ACCESSTOKEN) String accesstoken) {
		  ResultBean result = new ResultBean();
			TokenModel tokens = redisTokenService.getToken(accesstoken);
			if (tokens != null) {
		        result = userRoleService.findUserRoleList();
			}else {
				return new ResultBean("001",ResultBgMsg.C_001);
			}
			return result;
	}

}
