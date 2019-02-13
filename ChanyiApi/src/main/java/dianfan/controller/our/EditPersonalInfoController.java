/**  
* @Title: EditPersonalInfoController.java
* @Package dianfan.controller.our
* @Description: TODO
* @author yl
* @date 2018年6月28日 下午5:19:53
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

/**
 * @ClassName EditPersonalInfoController
 * @Description 编辑个人信息
 * @author yl
 * @date 2018年6月28日 下午5:19:53
 */
@Scope(WebApplicationContext.SCOPE_REQUEST)
@Controller
@RequestMapping(value = "/our")
public class EditPersonalInfoController {

	/**
	 * 注入：#CouponService
	 */
	@Autowired
	private PersonalInfoService personalInfoService;
	@Autowired
	private RedisTokenService redisTokenService;
	/**
	 * @Title: updatePersonalInfo
	 * @Description:
	 * @param id
	 * @return
	 * @throws:
	 * @time: 2018年6月28日 下午5:22:08
	 */
	@SystemControllerLog(method = "updatePersonalInfo", logtype = ConstantIF.LOG_TYPE_1, description = "修改用户信息")
	@ApiOperation(value = "修改用户信息", httpMethod = "POST", notes = "修改用户信息", response = ResultBean.class)
	@RequestMapping(value = "/updatePersonalInfo", method = RequestMethod.POST)
	@ResponseBody
	public ResultBean updatePersonalInfo(
			@ApiParam(value = "token值") @RequestParam(value = ConstantIF.ACCESSTOKEN, required = true) String accesstoken,
			@ApiParam(value = "用户性别") @RequestParam(value = "sex", required = false) String sex,
			@ApiParam(value = "用户昵称") @RequestParam(value = "nickname", required = false) String nickname,
			@ApiParam(value = "用户头像") @RequestParam(value = "avatarurl", required = false) String avatarurl) {
		/*
		 * 此处做值判断
		 */
		//限制位数
		if (nickname !=null && !StringUtils.isNotEmpty(nickname)) {
			int nicklen = nickname.length();
			if (nicklen>50) {
				return new ResultBean("2020",ResultApiMsg.C_2020);
			}
		}
		TokenModel tokens = redisTokenService.getToken(accesstoken);
		UserInfoModel uim = null;
		if (tokens !=null) {
			String userid = tokens.getUserid();
			if (StringUtils.isNotEmpty(userid)) {
				 uim = personalInfoService.getUserInfo(userid);
					if(uim !=null) {
						// 此处调用业务逻辑层
						personalInfoService.updateUserInfo(userid,sex,nickname,avatarurl);
						UserInfoModel users = personalInfoService.getUserInfo(userid);
						users.setAvatarUrl(PropertyUtil.getProperty("domain")+users.getAvatarUrl());
						if (StringUtils.isNotEmpty(users.getUnionId())) {
							users.setUnionId("1");
						}else {
							users.setUnionId("0");
						}
						return new ResultBean(users);
					}
			}
		}
		return new ResultBean("001",ResultApiMsg.C_001);

	}

}
