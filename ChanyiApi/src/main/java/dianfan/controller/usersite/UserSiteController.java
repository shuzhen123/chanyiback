package dianfan.controller.usersite;

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
import dianfan.models.ResultBean;
import dianfan.models.TokenModel;
import dianfan.service.impl.RedisTokenService;
import dianfan.service.usersite.UsersiteService;
import dianfan.util.RegexUtils;
import dianfan.util.StringUtility;

/**
 * @ClassName UserSiteController
 * @Description 用户地址相关controller
 * @author sz
 * @date 2018年6月30日 下午3:18:57
 */
@Scope(WebApplicationContext.SCOPE_REQUEST)
@Controller
@RequestMapping(value = "/usersite")
public class UserSiteController {

	/**
	 * 注入: #UsersiteService
	 */
	@Autowired
    private UsersiteService usersiteService;
	/**
	 * 注入: #RedisTokenService
	 */
	@Autowired
	private RedisTokenService redisTokenService;
	
	
	/**
	 * @Title: getUserSiteList
	 * @Description: 获取用户的后货地址列表(查)
	 * @param accesstoken 
	 *      	userid
	 * @return ResultBean
	 * @throws:
	 * @time: 2018年6月30日 下午4:09:18
	 */
	@SystemControllerLog(method = "getUserSiteList", logtype = ConstantIF.LOG_TYPE_1, description = "获取用户收获地址列表")
	@ApiOperation(value = "getUserSiteList", httpMethod = "POST", notes = "获取用户收获地址列表", response = ResultBean.class)
	@RequestMapping(value = "/getUserSiteList", method = RequestMethod.POST)
	public @ResponseBody ResultBean getUserSiteList(
			@ApiParam(value="accesstoken") @RequestParam(value=ConstantIF.ACCESSTOKEN) String accesstoken
			) {
		
		// 1.获取token
		TokenModel token = redisTokenService.getToken(accesstoken);
		
		// 2.调库获取用户收货列表返回
		return usersiteService.findUserSiteList(token.getUserid());
	}
	
	
	/**
	 * @Title: addUserSite
	 * @Description: 增加用户的收货地址
	 * @param accesstoken
	 * 			accesstoken
	 * @param name 
	 * 			收件人姓名
	 * @param telno 
	 * 			收件人电话
	 * @param areaCode 
	 * 			收件人所在区域
	 * @param detailAddr
	 * 			详细地址
	 * @return ResultBean
	 * @throws:
	 * @time: 2018年7月1日 下午12:12:12
	 */
	@SystemControllerLog(method = "addUserSite", logtype = ConstantIF.LOG_TYPE_1, description = "增加用户收获地址")
	@ApiOperation(value = "addUserSite", httpMethod = "POST", notes = "增加用户收获地址", response = ResultBean.class)
	@RequestMapping(value = "/addUserSite", method = RequestMethod.POST)
	public @ResponseBody ResultBean addUserSite(
			@ApiParam(value="accesstoken") @RequestParam(value=ConstantIF.ACCESSTOKEN) String accesstoken,
			@ApiParam(value="收件人姓名") @RequestParam(value="name") String name,
			@ApiParam(value="联系方式") @RequestParam(value="telno") String telno,
			@ApiParam(value="区域code") @RequestParam(value="areaCode") String areaCode,
			@ApiParam(value="详细地址") @RequestParam(value="detailAddr") String detailAddr
			) {
		// 1.获取token
		TokenModel token = redisTokenService.getToken(accesstoken);
		// 2.手机号码格式验证
		if (!RegexUtils.phoneRegex(telno)) {
			// ！请输入正确的手机号码
			return new ResultBean("4008",ResultApiMsg.C_4008);
		}
		// 3.入参空值验证
		if (StringUtility.isNull(name) || StringUtility.isNull(areaCode) || StringUtility.isNull(detailAddr)) {
			// ！请将收货地址前些完整
			return new ResultBean("4012",ResultApiMsg.C_4012);
		}
		
		// 验证入参是否超过数据库的字段
		if (name.length() > 32) {
			// ！收件人姓名过长
			return new ResultBean("4105",ResultApiMsg.C_4105);
		}
		if (detailAddr.length() > 250) {
			// ！收货详细地址过长
			return new ResultBean("4106",ResultApiMsg.C_4106);
		}
		
		// 4.基础验证成功，数据加入数据库，返回
		return usersiteService.addUserSite(token.getUserid(),name,telno,areaCode,detailAddr);
	}
	
	/**
	 * @Title: updateUserSite
	 * @Description: 修改用户的收货地址
	 * @param accesstoken
	 * 			accesstoken
	 * @param name 
	 * 			收件人姓名
	 * @param siteid 
	 * 			收货地址id
	 * @param telno 
	 * 			收件人电话
	 * @param areaCode 
	 * 			收件人所在区域
	 * @param detailAddr
	 * 			详细地址
	 * @return ResultBean
	 * @throws:
	 * @time: 2018年7月1日 下午12:12:12
	 */
	@SystemControllerLog(method = "updateUserSite", logtype = ConstantIF.LOG_TYPE_1, description = "修改用户收货地址")
	@ApiOperation(value = "updateUserSite", httpMethod = "POST", notes = "修改用户收货地址", response = ResultBean.class)
	@RequestMapping(value = "/updateUserSite", method = RequestMethod.POST)
	public @ResponseBody ResultBean updateUserSite(
			@ApiParam(value="accesstoken") @RequestParam(value=ConstantIF.ACCESSTOKEN) String accesstoken,
			@ApiParam(value="收货地址id") @RequestParam(value="siteid") String siteid,
			@ApiParam(value="收件人姓名") @RequestParam(value="name") String name,
			@ApiParam(value="联系方式") @RequestParam(value="telno") String telno,
			@ApiParam(value="区域code") @RequestParam(value="areaCode") String areaCode,
			@ApiParam(value="详细地址") @RequestParam(value="detailAddr") String detailAddr
			) {
		// 1.获取token
		TokenModel token = redisTokenService.getToken(accesstoken);
		
		// 1.手机号码格式验证
		if (!RegexUtils.phoneRegex(telno)) {
			// ！请输入正确的手机号码
			return new ResultBean("4005",ResultApiMsg.C_4005);
		}
		// 2.入参空值验证
		if (StringUtility.isNull(name) || StringUtility.isNull(areaCode) || StringUtility.isNull(detailAddr)) {
			// ！请将收货地址前些完整
			return new ResultBean("4012",ResultApiMsg.C_4012);
		}
		// 将超出数据库字段的数字截去（目前是截去数据库对应字段的一半）
		// 验证入参是否超过数据库的字段
		if (name.length() > 32) {
			// ！收件人姓名过长
			return new ResultBean("4105",ResultApiMsg.C_4105);
		}
		if (detailAddr.length() > 250) {
			// ！收货详细地址过长
			return new ResultBean("4106",ResultApiMsg.C_4106);
		}
		// 3.基础验证成功，调库操作，返回
		return usersiteService.updateUserSite(name,telno,areaCode,detailAddr,siteid,token.getUserid());
	}

	/**
	 * @Title: delUserSite
	 * @Description: 逻辑删除对应的收货地址
	 * @param siteid 
	 * 			收货地址id
	 * @return ResultBean
	 * @throws:
	 * @time: 2018年7月1日 下午1:44:51
	 */
	@SystemControllerLog(method = "delUserSite", logtype = ConstantIF.LOG_TYPE_1, description = "删除用户收获地址")
	@ApiOperation(value = "delUserSite", httpMethod = "POST", notes = "修改用户收获地址列表", response = ResultBean.class)
	@RequestMapping(value = "/delUserSite", method = RequestMethod.POST)
	public @ResponseBody ResultBean delUserSite(
			@ApiParam(value="accesstoken") @RequestParam(value=ConstantIF.ACCESSTOKEN) String accesstoken,
			@ApiParam(value="收货地址id") @RequestParam(value="siteid"
			) String siteid) {
		// 1.获取token
		TokenModel token = redisTokenService.getToken(accesstoken);
		
		// 2.验证siteid是否为空
		if (StringUtility.isNull(siteid)) {
			// !收货地址id为空
			return new ResultBean("501",ResultApiMsg.C_501); 
		}
		// 3.调库执行，返回
		return usersiteService.delUserSiteByid(siteid,token.getUserid());
	}
}
