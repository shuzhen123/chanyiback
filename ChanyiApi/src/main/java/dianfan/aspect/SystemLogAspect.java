/**  
* @Title: SystemLogAspect.java
* @Package dianfan.aspect
* @Description: TODO
* @author Administrator
* @date 2018年5月19日 下午1:41:31
* @version V1.0  
*/
package dianfan.aspect;

import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fasterxml.jackson.databind.ObjectMapper;

import dianfan.annotations.SystemControllerLog;
import dianfan.annotations.SystemServiceLog;
import dianfan.constant.ConstantIF;
import dianfan.logger.Logger;
import dianfan.models.TokenModel;
import dianfan.service.impl.LogOpService;
import dianfan.service.impl.RedisTokenService;
import dianfan.util.IpUtil;
import dianfan.util.StringUtility;

/**
 * @Title: SystemLogAspect.java
 * @Package dianfan.aspect
 * @Description: 系统日志记录
 * @author Administrator
 * @date 2018年5月19日 下午1:41:31
 * @version V1.0
 */
@Component
@Aspect
public class SystemLogAspect {
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

	// Service层切点
	@Pointcut("@annotation(dianfan.annotations.SystemServiceLog)")
	public void serviceAspect() {
	}

	// Controller层切点
	@Pointcut("@annotation(dianfan.annotations.SystemControllerLog)")
	public void controllerAspect() {
	}

	/**
	 * 前置通知 用于拦截Controller层记录用户的操作
	 * 
	 * @param joinPoint
	 *            切点
	 */
	@Before("controllerAspect()")
	public void doBefore(JoinPoint joinPoint) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		TokenModel model = null;
		// 写系统日志
		String accesstoken = request.getParameter(ConstantIF.ACCESSTOKEN);
		if (!StringUtility.isNull(accesstoken)) {
			// 获取token
			model = manager.getToken(accesstoken);
		}
		try {
			// *========控制台输出=========*//
			System.out.println("=====前置通知开始=====");
			System.out.println("请求方法:"
					+ (joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()"));
			System.out.println("方法描述:" + getControllerMethodDescription(joinPoint));
			// *========数据库日志=========*//
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
			if (!StringUtility.isNull(accesstoken)) {
				// 获取token
				logInfoService.writeLog(Long.parseLong(ConstantIF.LOG_TYPE_3),
						getControllerMethodDescription(joinPoint),
						joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()",
						String.valueOf(model.getUserid()), params, IpUtil.getIpAddr(request));
			} else {
				logInfoService.writeLog(Long.parseLong(ConstantIF.LOG_TYPE_3),
						getControllerMethodDescription(joinPoint),
						joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()",
						"", params, IpUtil.getIpAddr(request));
			}
			// 保存数据库
			System.out.println("=====前置通知结束=====");
		} catch (Exception e) {
			// 记录本地异常日志
			Logger.error("==前置通知异常==");
			Logger.error("异常信息:{}", e);
		}
	}

	/**
	 * 异常通知 用于拦截service层记录异常日志
	 * 
	 * @param joinPoint
	 * @param e
	 */
	@AfterThrowing(pointcut = "serviceAspect()", throwing = "e")
	public void doAfterThrowing(JoinPoint joinPoint, Throwable e) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		TokenModel model = null;
		// 写系统日志
		String accesstoken = request.getParameter(ConstantIF.ACCESSTOKEN);
		if (!StringUtility.isNull(accesstoken)) {
			// 获取token
			model = manager.getToken(accesstoken);
		}
		try {
			/* ========控制台输出========= */
			System.out.println("=====异常通知开始=====");
			System.out.println("异常代码:" + e.getClass().getName());
			System.out.println("异常信息:" + e.getMessage());
			System.out.println("异常方法:"
					+ (joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()"));
			System.out.println("方法描述:" + getServiceMthodDescription(joinPoint));
			/* ==========数据库日志========= */
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
			if (!StringUtility.isNull(accesstoken)) {
				// 获取token
				logInfoService.writeLog(Long.parseLong(ConstantIF.LOG_TYPE_4), getServiceMthodDescription(joinPoint),
						joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()",
						String.valueOf(model.getUserid()), params, IpUtil.getIpAddr(request));
			} else {
				logInfoService.writeLog(Long.parseLong(ConstantIF.LOG_TYPE_4), getServiceMthodDescription(joinPoint),
						joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()",
						"", params, IpUtil.getIpAddr(request));
			}
			System.out.println("=====异常通知结束=====");
		} catch (Exception ex) {
			// 记录本地异常日志
			Logger.error("==异常通知异常==");
			Logger.error("异常信息:{}", ex);
		}
		/* ==========记录本地异常日志========== */
	}

	/**
	 * 获取注解中对方法的描述信息 用于service层注解
	 * 
	 * @param joinPoint
	 *            切点
	 * @return 方法描述
	 * @throws Exception
	 */
	public static String getServiceMthodDescription(JoinPoint joinPoint) throws Exception {
		String targetName = joinPoint.getTarget().getClass().getName();
		String methodName = joinPoint.getSignature().getName();
		Object[] arguments = joinPoint.getArgs();
		Class targetClass = Class.forName(targetName);
		Method[] methods = targetClass.getMethods();
		String description = "";
		for (Method method : methods) {
			if (method.getName().equals(methodName)) {
				Class[] clazzs = method.getParameterTypes();
				if (clazzs.length == arguments.length) {
					description = method.getAnnotation(SystemServiceLog.class).description();
					break;
				}
			}
		}
		return description;
	}

	/**
	 * 获取注解中对方法的描述信息 用于Controller层注解
	 * 
	 * @param joinPoint
	 *            切点
	 * @return 方法描述
	 * @throws Exception
	 */
	public static String getControllerMethodDescription(JoinPoint joinPoint) throws Exception {
		String targetName = joinPoint.getTarget().getClass().getName();
		String methodName = joinPoint.getSignature().getName();
		Object[] arguments = joinPoint.getArgs();
		Class targetClass = Class.forName(targetName);
		Method[] methods = targetClass.getMethods();
		String description = "";
		for (Method method : methods) {
			if (method.getName().equals(methodName)) {
				Class[] clazzs = method.getParameterTypes();
				if (clazzs.length == arguments.length) {
					description = method.getAnnotation(SystemControllerLog.class).description();
					break;
				}
			}
		}
		return description;
	}
}
