package dianfan.controller.login;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import dianfan.entities.loginmanage.AdminInfo;
import dianfan.entities.loginmanage.AdminLoginLog;
import dianfan.entities.role.Role;
import dianfan.md5.MD5;
import dianfan.models.ResultBean;
import dianfan.service.adminlogin.AdminLoginService;
import dianfan.service.impl.RedisService;
import dianfan.service.impl.RedisTokenService;
import dianfan.util.BrowserUtil;
import dianfan.util.IpUtil;
import dianfan.util.VerifyCodeUtils;

/**
 * @ClassName LoginManage
 * @Description 后台登录 相关管理
 * @author sz
 * @date 2018年7月16日 上午11:38:12
 */
@Scope(WebApplicationContext.SCOPE_REQUEST)
@Controller
@RequestMapping(value = "/adminlogin")
public class LoginManage {
	
	/**
	 * 注入： #AdminLoginService
	 */
	@Autowired
	private AdminLoginService adminLoginService; 
	
	/**
	 * 注入: #RedisTokenService
	 */
	@Autowired
	private RedisTokenService redisTokenService;
	/**
	 * 注入: #redisService
	 */
	@Autowired
	private RedisService redisService;
	
	

	/**
	 * @Title: getVerifyCode
	 * @Description: 获取图片验证码
	 * @param request
	 * @param response
	 * @throws:
	 * @time: 2018年7月16日 上午11:46:12
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
	 * @param request 请求
	 * @param response 响应
	 * @param attributes 重定向控制器
	 * @return
	 * @throws:
	 * @time: 2018年7月16日 下午12:00:29
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
		
		password = MD5.string2MD5(password);
		
		// 验证账号密码是否正确
		AdminInfo dminInfo = adminLoginService.getAdminInfo(account, password);
		// 验证
		if (dminInfo == null) {
			// 验证未通过，账号或密码错误
			return new ResultBean("4002" , ResultBgMsg.C_4002);
		}
		if (dminInfo.getEntkbn() == 1) {
			// 账户已被冻结
			return new ResultBean("4011" , ResultBgMsg.C_4011);
		}
		/* 获取登陆这的角色 */
		// 获取用户对应的角色
		Role adminrole = adminLoginService.findAdminRole(dminInfo.getId());
		// 08普通用户判断
		if (adminrole == null) {
			// 普通人无权限进入
			return new ResultBean("4003" , ResultBgMsg.C_4003);
		}
		String role = adminrole.getId();
		// 角色下的权限
		List<String> rolelist = adminLoginService.findAdminPopedom(role);
		// 转化成一个string 已英文“,” 分割的字符串
		String roles = org.apache.commons.lang3.StringUtils.join(rolelist, ",");
		
		//登录成功
		String accesstoken = redisTokenService.createToken(dminInfo.getId());
		
		// 从请求中获取 用户登陆日志
		String ip = IpUtil.getIpAddr(request);
		String browserName = BrowserUtil.getBrowserName(request.getHeader("user-agent"));
		// 将用户的登陆日志插入数据库
		adminLoginService.addUserLoginLog(ip, browserName, dminInfo.getId());
		
		// 查询用户登陆记录
		AdminLoginLog adminLoginLog = adminLoginService.getAdminLoginLog(dminInfo.getId());
		
		// 构建返回参数	
		Map<String, Object> data = new HashMap<>();
		// accesstoken
		data.put("accesstoken", accesstoken);
		// 角色
		data.put("adminrole", adminrole);
		// 权限
		data.put("roles", roles);
		// 用户登陆日志
		data.put("adminLoginLog", adminLoginLog);
		// 返回
		return new ResultBean(data);
	}
	
	
	
	
	
	
	
	

}
