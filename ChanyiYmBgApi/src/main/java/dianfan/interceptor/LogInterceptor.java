package dianfan.interceptor;

import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.fasterxml.jackson.databind.ObjectMapper;

import dianfan.annotations.RequestLogOp;
import dianfan.constant.ConstantIF;
import dianfan.models.TokenModel;
import dianfan.service.impl.LogOpService;
import dianfan.service.impl.RedisTokenService;
import dianfan.util.IpUtil;
import dianfan.util.StringUtility;

/**
 * 
 * @Title: LogInterceptor.java
 * @Package dianfan.interceptor
 * @Description: 记录系统日志拦截器
 * @author Administrator
 * @date 2018年5月11日 上午10:14:32
 * @version V1.0
 */
public class LogInterceptor extends HandlerInterceptorAdapter {
	/**
	 * 注入token类
	 */
	@Autowired
	private RedisTokenService manager;

	/**
	 * 注入写日志类
	 */
	@Autowired
	private LogOpService logInfoService;

	/**
	 * 生成视图之前执行postHandle
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		if (handler instanceof HandlerMethod) {
			Method method = ((HandlerMethod) handler).getMethod();
			if (method.isAnnotationPresent(RequestLogOp.class)) {
				// 获取request中所有参数的方法
				Map<Object, Object> map = new HashMap<Object, Object>();
				// 参数置空
				Enumeration<?> enu = request.getParameterNames();
				while (enu.hasMoreElements()) {
					String paraName = (String) enu.nextElement();
					// 打印参数
					System.out.println(paraName + ": " + request.getParameter(paraName));
					map.put(paraName, request.getParameter(paraName));
				}
				ObjectMapper json = new ObjectMapper();
				// 把map对象转成json格式的String字符串
				String params = json.writeValueAsString(map);
				RequestLogOp rl = method.getAnnotation(RequestLogOp.class);
				// 写系统日志
				String accesstoken = request.getParameter(ConstantIF.ACCESSTOKEN);
				if (!StringUtility.isNull(accesstoken)) {
					// 获取token
					TokenModel model = manager.getToken(accesstoken);
					logInfoService.writeLog(Long.parseLong(rl.logtype()), rl.description(), rl.method(),
							String.valueOf(model.getUserid()), params, IpUtil.getIpAddr(request));
				} else {
					logInfoService.writeLog(Long.parseLong(rl.logtype()), rl.description(), rl.method(), rl.userid(),
							params, IpUtil.getIpAddr(request));
				}
			}
		}
	}
}
