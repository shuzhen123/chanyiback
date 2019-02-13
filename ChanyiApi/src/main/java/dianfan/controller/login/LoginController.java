package dianfan.controller.login;

import java.io.IOException;

import org.jaxen.function.IdFunction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;

import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

import dianfan.annotations.SystemControllerLog;
import dianfan.annotations.UnCheckedFilter;
import dianfan.constant.ConstantIF;
import dianfan.constant.ResultApiMsg;
import dianfan.md5.MD5;
import dianfan.models.ResultBean;
import dianfan.models.TokenModel;
import dianfan.service.impl.RedisTokenService;
import dianfan.service.login.UserService;
import dianfan.util.RegexUtils;
import dianfan.util.StringUtility;


/**
 * @ClassName LoginController
 * @Description 用户登陆Controller
 * @author sz
 * @date 2018年6月28日 下午3:08:20
 */
@Scope(WebApplicationContext.SCOPE_REQUEST)
@Controller
@RequestMapping(value = "/login")
public class LoginController {
	
	/**
	 * 注入: #UserService
	 */
	@Autowired
	private UserService userService;
	
	/**
	 * 注入: #RedisTokenService
	 */
	@Autowired
	private RedisTokenService redisTokenService;
	
	/**
	 * @Title: weiXinLogin
	 * @Description: 微信授权登录
	 * @param code
	 * 			code
	 * @param encryptedData
	 * 			encryptedData 加密后的用户信息
	 * @param iv
	 * 			解密算法
	 * @return
	 * @throws IOException 
	 * @throws:
	 * @time: 2018年6月29日 上午10:16:58
	 */
	@UnCheckedFilter
	@SystemControllerLog(method = "weiXinLogin", logtype = ConstantIF.LOG_TYPE_1, description = "微信授权登录")
	@ApiOperation(value = "weiXinLogin", httpMethod = "POST", notes = "微信授权登录", response = ResultBean.class)
	@RequestMapping(value = "/weiXinLogin", method = RequestMethod.POST)
	public @ResponseBody ResultBean weiXinLogin (
			@ApiParam(value="code") @RequestParam(value="code") String code,
			@ApiParam(value="用户信息的加密数据") @RequestParam(value="encryptedData") String encryptedData,
			@ApiParam(value="加密算法的初始向量") @RequestParam(value="iv") String iv
			) throws IOException {
		
		// 1.判断code是否为空
		if(StringUtility.isNull(code)) {
			// code不为空，入参错误
			return new ResultBean("4002",ResultApiMsg.C_4002);
		}
		// 2.code接受成功 ,验证用户登陆，获取用户数据    
		return userService.getUserInfo(code, encryptedData, iv);
	}
	
	/**
	 * @Title: ChangePassword
	 * @Description: 修改密码
	 * @param accesstoken 
	 * 			accesstoken
	 * @param oldPassword 
	 * 			原密码
	 * @param newPassword 
	 * 			新密码
	 * @return
	 * @throws:
	 * @time: 2018年6月29日 上午12:02:17
	 */
	@SystemControllerLog(method = "changePassword", logtype = ConstantIF.LOG_TYPE_1, description = "修改密码")
	@ApiOperation(value = "changePassword", httpMethod = "POST", notes = "修改密码", response = ResultBean.class)
	@RequestMapping(value = "/changePassword", method = RequestMethod.POST)
	public @ResponseBody ResultBean changePassword (
			@ApiParam(value="accesstoken") @RequestParam(value=ConstantIF.ACCESSTOKEN) String accesstoken,
			@ApiParam(value="原密码") @RequestParam(value="oldPassword") String oldPassword,
			@ApiParam(value="新密码") @RequestParam(value="newPassword") String newPassword
			) {
		// 创建返回值对象
		ResultBean result = null;
		
		// 1.获取token
		TokenModel token = redisTokenService.getToken(accesstoken);
		
		/*--密码使用 MD5 加密 一下 .--*/
		// 旧密码
		String MD5oldPassword = MD5.string2MD5(oldPassword);
		// 新密码
		String MD5newPassword = MD5.string2MD5(newPassword);
		
		// 3.进行验证操作
		result = userService.updatePassword(token.getUserid(), MD5oldPassword, MD5newPassword);
		// 4.返回
		return result;
	}
	
	/**
	 * @Title: phoneLogin
	 * @Description: 手机号码登录
	 * @param telno 
	 * 			手机号码
	 * @param pwd 
	 * 			登录密码
	 * @return
	 * @throws:
	 * @time: 2018年6月29日 上午11:51:16
	 */
	@UnCheckedFilter
	@SystemControllerLog(method = "phoneLogin", logtype = ConstantIF.LOG_TYPE_1, description = "手机号码登录")
	@ApiOperation(value = "phoneLogin", httpMethod = "POST", notes = "手机号码登录", response = ResultBean.class)
	@RequestMapping(value = "/phoneLogin", method = RequestMethod.POST)
	public @ResponseBody ResultBean phoneLogin (
			@ApiParam(value="手机号码") @RequestParam(value="telno") String telno,
			@ApiParam(value="登录密码") @RequestParam(value="pwd") String pwd,
			@ApiParam(value="code") @RequestParam(value="code") String code,
			@ApiParam(value="用户信息的加密数据") @RequestParam(value="encryptedData") String encryptedData,
			@ApiParam(value="加密算法的初始向量") @RequestParam(value="iv") String iv
			) {
		// 1.入参基本判断
		if  (!RegexUtils.phoneRegex(telno) || StringUtility.isNull(pwd)) {
			// 2.手机号码合适不对或者密码为空
			return new ResultBean("4005",ResultApiMsg.C_4005);
		}
		// 2.判断code是否为空
		if(StringUtility.isNull(code)) {
			// code不为空，入参错误
			return new ResultBean("4002",ResultApiMsg.C_4002);
		}
		/*--密码使用 MD5 加密 一下 --*/
		String MD5pwd = MD5.string2MD5(pwd);
		// 3.基础验证成功，调库验证，返回
		return userService.getUserBytelno(telno, MD5pwd,code,encryptedData,iv);
	}
	
	
	/**
	 * @Title: userBindingWX
	 * @Description: 手机号码登录补全用户信息
	 * @param accesstoken accesstoken
	 * @param code code
	 * @param encryptedData encryptedData
	 * @param iv iv
	 * @return
	 * @throws IOException
	 * @throws:
	 * @time: 2018年8月17日 下午12:35:54
	 */
	@SystemControllerLog(method = "userBindingWX", logtype = ConstantIF.LOG_TYPE_1, description = "手机号码登录补全用户信息")
	@ApiOperation(value = "userBindingWX", httpMethod = "POST", notes = "手机号码登录补全用户信息", response = ResultBean.class)
	@RequestMapping(value = "/userBindingWX", method = RequestMethod.POST)
	public @ResponseBody ResultBean userBindingWX (
			@ApiParam(value="accesstoken") @RequestParam(value=ConstantIF.ACCESSTOKEN) String accesstoken,
			@ApiParam(value="code") @RequestParam(value="code") String code,
			@ApiParam(value="用户信息的加密数据") @RequestParam(value="encryptedData") String encryptedData,
			@ApiParam(value="加密算法的初始向量") @RequestParam(value="iv") String iv
			) throws IOException {
		// 创建返回值对象
		ResultBean result = null;
		
		// 1.获取token
		TokenModel token = redisTokenService.getToken(accesstoken);
		if (StringUtils.isEmpty(token)) {
			return new ResultBean("001",ResultApiMsg.C_001);
		}
		
		result = userService.addBindingWX(token.getUserid(), encryptedData, code, iv);
		
		// 4.返回
		return result;
	}
	
	
	
	/**
	 * @Title: loginOut
	 * @Description: 用户登出
	 * @return
	 * @throws:
	 * @time: 2018年6月30日 下午2:22:24
	 */
	@SystemControllerLog(method = "loginOut", logtype = ConstantIF.LOG_TYPE_1, description = "用户登出")
	@ApiOperation(value = "loginOut", httpMethod = "POST", notes = "用户登出", response = ResultBean.class)
	@RequestMapping(value = "/loginOut", method = RequestMethod.POST)
	public @ResponseBody ResultBean loginOut(
			@ApiParam(value="accesstoken") @RequestParam(value=ConstantIF.ACCESSTOKEN) String accesstoken
			) {
		// 判断入参是否存在
		if (StringUtility.isNull(accesstoken)) {
			// !错误：code为空
			return new ResultBean("4002",ResultApiMsg.C_4002);
		}
		// 删除token
		redisTokenService.deleteToken(accesstoken);
		// 成功，返回
		return new ResultBean();
	}
	
	
	
	
	
	
}
