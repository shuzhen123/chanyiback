package dianfan.controller.login;

import java.io.UnsupportedEncodingException;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;

import com.aliyuncs.exceptions.ClientException;
import com.fasterxml.jackson.core.JsonProcessingException;
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

/**
 * @ClassName SmsCodeController
 * @Description 手机短信相关Controller
 * @author sz
 * @date 2018年6月29日 下午3:09:09
 */
@Scope(WebApplicationContext.SCOPE_REQUEST)
@Controller
@RequestMapping(value = "/sms")
public class SmsCodeController {
	
	
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
	 * @Title: registerSMS
	 * @Description: 发送注册手机验证码
	 * @param telno
	 * 			手机号码
	 * @return
	 * @throws ClientException 
	 * @throws JsonProcessingException 
	 * @throws UnsupportedEncodingException 
	 * @throws:
	 * @time: 2018年6月29日 下午3:16:51
	 */
	@SystemControllerLog(method = "registerSMS", logtype = ConstantIF.LOG_TYPE_1, description = "发送注册手机验证码")
	@ApiOperation(value = "registerSMS", httpMethod = "GET", notes = "发送注册手机验证码", response = ResultBean.class)
	@RequestMapping(value = "/registerSMS", method = RequestMethod.GET)
	@UnCheckedFilter
	public @ResponseBody ResultBean registerSMS(
			@ApiParam("手机号码") @RequestParam(value = "telno") String telno)
			throws UnsupportedEncodingException, JsonProcessingException, ClientException{
		
		// 1.验证手机号码格式
		if (!RegexUtils.phoneRegex(telno)) {
			// !手机号码格式不对
			return new ResultBean("4008", ResultApiMsg.C_4008);
		}
		// 2.调库验证，成功返回
		return userService.registerSMS(telno);
	}
	
	
	/**
	 * @Title: boundTelno
	 * @Description: 绑定手机号
	 * @param accesstoken accesstoken
	 * @param telno 手机号码
	 * @param smscode 验证码
	 * @return
	 * @throws:
	 * @time: 2018年6月29日 下午6:21:17
	 */
	@SystemControllerLog(method = "boundTelno", logtype = ConstantIF.LOG_TYPE_1, description = "绑定手机号")
	@ApiOperation(value = "boundTelno", httpMethod = "POST", notes = "绑定手机号", response = ResultBean.class)
	@RequestMapping(value = "/boundTelno", method = RequestMethod.POST)
	public @ResponseBody ResultBean boundTelno (
			@ApiParam(value="accesstoken") @RequestParam(value=ConstantIF.ACCESSTOKEN) String accesstoken,
			@ApiParam("手机号码") @RequestParam(value = "telno") String telno,
			@ApiParam("验证码") @RequestParam(value = "smscode") String smscode,
			@ApiParam("设置的密码") @RequestParam(value = "pwd") String pwd
			) {
		// 1.获取用户id
		TokenModel token = redisTokenService.getToken(accesstoken);
		
		/*boolean pass = redisTokenService.checkToken(token);
		if (!pass) {
			// token验证失败
			return new ResultBean("001",ResultApiMsg.C_001);
		}*/
		
		// 1.验证手机号码格式
		if (!RegexUtils.phoneRegex(telno)) {
			// !错误：手机号码格式不对
			return new ResultBean("4008", ResultApiMsg.C_4008);
		}
		if (StringUtils.isEmpty(smscode)) {
			// !错误：短信验证码不正确
			return new ResultBean("4011", ResultApiMsg.C_4011);
		}
		/* -密码MD5加密- */
		String md5pwd = MD5.string2MD5(pwd);
		
		// 3.调库验证，成功返回
		return userService.addTelnoBysms(token.getUserid(), telno, smscode,md5pwd);
	}
	
	
	
	
	/**
	 * @Title: getResetSMS
	 * @Description: 获取重置密码短信验证码
	 * @param telno 
	 * 			手机号码
	 * @return
	 * @throws ClientException 
	 * @throws JsonProcessingException 
	 * @throws UnsupportedEncodingException 
	 * @throws:
	 * @time: 2018年6月29日 下午4:54:13
	 */
	@UnCheckedFilter
	@SystemControllerLog(method = "getResetSMS", logtype = ConstantIF.LOG_TYPE_1, description = "获取重置密码短信验证码")
	@ApiOperation(value = "getResetSMS", httpMethod = "GET", notes = "获取重置密码短信验证码", response = ResultBean.class)
	@RequestMapping(value = "/getResetSMS", method = RequestMethod.GET)
	public @ResponseBody ResultBean getResetSMS(@ApiParam("手机号码") @RequestParam(value = "telno") String telno)
			throws UnsupportedEncodingException, JsonProcessingException, ClientException {
		// 1.验证手机号码格式
		if (!RegexUtils.phoneRegex(telno)) {
			// !手机号码格式
			return new ResultBean("4008", ResultApiMsg.C_4008);
		}
		// 2.调库验证，成功返回
		return userService.getResetSMS(telno);
	}
	
	/**
	 * @Title: resetPassword
	 * @Description: 验证码方式重置密码 (忘记密码)
	 * @param telno 手机号
	 * @param smscode 收到的重置密码
	 * @return
	 * @throws:
	 * @time: 2018年6月29日 下午10:34:14
	 */
	@UnCheckedFilter
	@SystemControllerLog(method = "resetPassword", logtype = ConstantIF.LOG_TYPE_1, description = "验证码方式重置密码")
	@ApiOperation(value = "resetPassword", httpMethod = "POST", notes = "验证码方式重置密码", response = ResultBean.class)
	@RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
	public @ResponseBody ResultBean resetPassword(
			@ApiParam("手机号码") @RequestParam(value = "telno") String telno,
			@ApiParam("验证码") @RequestParam(value = "smscode") String smscode,
			@ApiParam("新密码") @RequestParam(value = "newPassword") String newPassword
			) {
		
		// 1.验证手机号码格式
		if (!RegexUtils.phoneRegex(telno)) {
			// !手机号码格式
			return new ResultBean("4008", ResultApiMsg.C_4008);
		}
		if (StringUtils.isEmpty(smscode)) {
			// !短信验证码不正确
			return new ResultBean("4011", ResultApiMsg.C_4011);
		}
		
		/* -密码MD5加密- */
		String md5pwd = MD5.string2MD5(newPassword);
		
		// 4.调库验证，成功返回
		return userService.updatePasswordBysms(telno, smscode, md5pwd);
	}
	
	

}
