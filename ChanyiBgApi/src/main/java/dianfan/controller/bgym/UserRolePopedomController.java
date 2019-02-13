/**  
* @Title: UserRolePopedom.java
* @Package dianfan.controller.bgym
* @Description: TODO
* @author yl
* @date 2018年7月25日 上午9:12:04
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
import dianfan.service.urpopedom.UserRolePopedomService;

/** @ClassName UserRolePopedom
 * @Description 
 * @author yl
 * @date 2018年7月25日 上午9:12:04
 */
@Scope("request")
@Controller
@RequestMapping(value = "/bgym")
public class UserRolePopedomController {
	
	@Autowired
	private UserRolePopedomService userRolePopedomService;
	@Autowired
	private RedisTokenService redisTokenService;
	
	@SystemControllerLog(method = "findBgUserRolePopedomList", logtype = ConstantIF.LOG_TYPE_1, description = "获取员工权限列表")
	@ApiOperation(value = "findBgUserRolePopedomList", httpMethod = "GET", notes = "获取员工权限列表", response = ResultBean.class)
	@RequestMapping(value = "/findBgUserRolePopedomList", method = RequestMethod.GET)
	public @ResponseBody ResultBean findBgUserRolePopedomList(
			@ApiParam(value = "token值") @RequestParam(value = ConstantIF.ACCESSTOKEN) String accesstoken) {
		  ResultBean result = new ResultBean();
			TokenModel tokens = redisTokenService.getToken(accesstoken);
			if (tokens != null) {
		        result = userRolePopedomService.findUrpopedomList();
			}else {
				return new ResultBean("001",ResultBgMsg.C_001);
			}
			return result;
	}
	@SystemControllerLog(method = "updateBgUserRolePopedom", logtype = ConstantIF.LOG_TYPE_1, description = "修改员工角色权限")
	@ApiOperation(value = "updateBgUserRolePopedom", httpMethod = "POST", notes = "修改员工角色权限", response = ResultBean.class)
	@RequestMapping(value = "/updateBgUserRolePopedom", method = RequestMethod.POST)
	public @ResponseBody ResultBean updateBgUserRolePopedom(
			@ApiParam(value = "token值") @RequestParam(value = ConstantIF.ACCESSTOKEN) String accesstoken,
			@ApiParam(value = "角色id") @RequestParam(value = "mrpid", required = true) String mrpid,
			@ApiParam(value = "角色描述") @RequestParam(value = "description", required = true) String description,
			@ApiParam(value = "权限 （1为可用，2为不可用）") @RequestParam(value = "popedom", required = false,defaultValue="1") String popedom,
			@ApiParam(value = "权限id") @RequestParam(value = "popedomid", required = true) String popedomid) {
		ResultBean result = new ResultBean();
		TokenModel tokens = redisTokenService.getToken(accesstoken);
		if (tokens != null) {
			userRolePopedomService.updateBgUserRolePopedom(mrpid,description,popedom, popedomid);
		}else {
			return new ResultBean("001",ResultBgMsg.C_001);
		}
		return result;
	}


}
