package dianfan.controller.login;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;

import com.wordnik.swagger.annotations.ApiParam;

import dianfan.annotations.SystemControllerLog;
import dianfan.annotations.UnCheckedFilter;
import dianfan.constant.ConstantIF;
import dianfan.constant.ResultBgMsg;
import dianfan.md5.MD5;
import dianfan.models.ResultBean;
import dianfan.service.impl.RedisService;
import dianfan.service.ym.login.YmUserLoginService;
import dianfan.util.VerifyCodeUtils;

/**
 * @ClassName LoginManage
 * @Description 登录
 * @author cjy
 * @date 2018年9月17日 下午2:05:17
 */
@Scope(WebApplicationContext.SCOPE_REQUEST)
@Controller
@RequestMapping(value = "/ymLogin")
public class LoginManage {
	@Autowired
	private RedisService redisService;
	@Autowired
	private YmUserLoginService ymUserLoginService;
	
	/**
	 * @Title: getVerifyCode
	 * @Description: 获取图片验证码
	 * @param request
	 * @param response
	 * @throws:
	 * @time: 2018年9月17日 下午2:06:53
	 * @author cjy
	 */
	@UnCheckedFilter
	@SystemControllerLog(method = "getVerifyCode", logtype = ConstantIF.LOG_TYPE_5, description = "获取图片验证码")
	@RequestMapping(value = "/getVerifyCode", method = RequestMethod.GET)
	public void getVerifyCode(HttpServletRequest request, HttpServletResponse response) {
		
		String random = request.getParameter("random");

		// 生成随机字串
		String verifyCode = VerifyCodeUtils.generateVerifyCode(4);
		
		// 生成图片
		int w = 100, h = 30;
		try {
			VerifyCodeUtils.outputImage(w, h, response.getOutputStream(), verifyCode);
			redisService.set(random, verifyCode, 300L);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/**
	 * @Title: loginAction
	 * @Description: 登录操作
	 * @return
	 * @throws:
	 * @time: 2018年9月17日 下午2:07:57
	 * @author cjy
	 */
	@UnCheckedFilter
	@SystemControllerLog(method = "loginAction", logtype = ConstantIF.LOG_TYPE_5, description = "登录操作")
	@RequestMapping(value = "/loginAction", method = RequestMethod.POST)
	public @ResponseBody ResultBean loginAction (
			HttpServletRequest request,
			@ApiParam(value="用户名") @RequestParam(value="account") String account,
			@ApiParam(value="密码") @RequestParam(value="password") String password,
			@ApiParam(value="图片验证码") @RequestParam(value="verifycode") String verifycode,
			@ApiParam(value="图片随机数") @RequestParam(value="random") String random
			) {
		// 验证码验证
		if (verifycode == null) {
			// 验证码不正确
			return new ResultBean("4001" , ResultBgMsg.C_4001);
		}
		//取验证码
		String vcode = redisService.get(random);
		
		if(vcode == null || !StringUtils.equals(vcode.toLowerCase(), verifycode.toLowerCase())) {
			// 验证码不正确
			return new ResultBean("4001" , ResultBgMsg.C_4001);
		}
		
		// 验证账号密码是否正确
		return ymUserLoginService.ymUserLogin(account, MD5.string2MD5(password));
	}

}
