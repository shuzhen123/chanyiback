/**  
* @Title: PersonalInfoController.java
* @Package dianfan.controller.our
* @Description: TODO
* @author yl
* @date 2018年6月28日 下午4:33:10
* @version V1.0  
*/ 
package dianfan.controller.our;

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
import dianfan.util.PropertyUtil;

/** @ClassName PersonalInfoController
 * @Description 获取个人信息
 * @author yl
 * @date 2018年6月28日 下午4:33:10
 */
@Scope(WebApplicationContext.SCOPE_REQUEST)
@Controller
@RequestMapping(value = "/our")
public class PersonalInfoController {
	
	/**
	 * 注入：#PersonalInfoService
	 */
	@Autowired
	private PersonalInfoService personalInfoService;
	/**
	 * 注入: RedisTokenService
	 */
	@Autowired
	private RedisTokenService redisTokenService;
	
	@SystemControllerLog(method = "getUserInfo", logtype = ConstantIF.LOG_TYPE_1, description = "获取用户信息")
	@ApiOperation(value = "获取用户信息", httpMethod = "GET", notes = "获取用户信息", response = ResultBean.class)
	@RequestMapping(value = "/getUserInfo", method = RequestMethod.GET)
	@ResponseBody
	public ResultBean getUserInfo(@ApiParam(value = "token值") @RequestParam(value = ConstantIF.ACCESSTOKEN) String accesstoken) {
		// 1.获取token
		TokenModel tokens = redisTokenService.getToken(accesstoken);
		UserInfoModel users = null;
		if (tokens !=null) {
			String userid = tokens.getUserid();
			if (StringUtils.isNotEmpty(userid)) {
				//此处调用业务逻辑层
				users = personalInfoService.getUserInfo(userid);
				if (users != null) {
					String unions = users.getUnionId();
					if (StringUtils.isNotEmpty(unions)) {
						users.setUnionId("1");
					}else {
						users.setUnionId("0");
					}
					users.setAvatarUrl(PropertyUtil.getProperty("domain")+users.getAvatarUrl());
					return new ResultBean(users);
				}
			}
		}
		return new ResultBean("001",ResultApiMsg.C_001);
	
	}
	

}
