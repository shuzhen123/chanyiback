/**  
* @Title: PermissionInterceptor.java
* @Package dianfan.interceptor
* @Description: TODO
* @author Administrator
* @date 2018年7月19日 下午4:17:47
* @version V1.0  
*/
package dianfan.interceptor;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.camel.util.concurrent.SynchronousExecutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import dianfan.annotations.UnCheckedFilter;
import dianfan.service.adminlogin.AdminLoginService;
import dianfan.service.impl.RedisTokenService;

/**
 * @Title: PermissionInterceptor.java
 * @Package dianfan.interceptor
 * @Description: 权限验证
 * @author Administrator
 * @date 2018年7月19日 下午4:17:47
 * @version V1.0
 */
public class PermissionInterceptor extends HandlerInterceptorAdapter {
	@Autowired
	AdminLoginService adminLoginService;

	@Autowired
	private RedisTokenService tokenService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		response.setCharacterEncoding("utf-8");
		response.setHeader("Content-Type", "application/json");
		response.setHeader("Access-Control-Allow-Origin", "*"); // 解决跨域访问报错
		response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
		response.setHeader("Access-Control-Max-Age", "3600"); // 设置过期时间
		response.setHeader("Access-Control-Allow-Headers",
				"Origin, X-Requested-With, Content-Type, Accept, client_id, uuid, Authorization");
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // 支持HTTP 1.1.
		response.setHeader("Pragma", "no-cache"); // 支持HTTP 1.0.
		// 1、如果不是映射到方法直接通过
		if (!(handler instanceof HandlerMethod)) {
			return true;
		}
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		Method method = handlerMethod.getMethod();
		// swagger接口放行
		if (request.getServletPath().contains("/api-docs")) {
			return true;
		}

		// 2、检测是否为放行api
		if (method.getAnnotation(UnCheckedFilter.class) != null) {
			return true;
		}

		// 权限验证
		// 获取uri
		String requestUri = request.getRequestURI();
		System.err.println("---------");
		// 获取项目名称
		String webcontentName = request.getContextPath();
		// 发布的后端项目名称
		boolean fbUrl = requestUri.contains(webcontentName);
		if (!fbUrl)
			return true;
		else {
			/*requestUri = requestUri.substring(webcontentName.length());
			System.out.println("请求地址" + requestUri);
			// 获取userid
			String accesstoken = request.getParameter(ConstantIF.ACCESSTOKEN);
			if (StringUtils.isEmpty(accesstoken)) {
				// 未登录
				ResultBean rb = new ResultBean("001", ResultBgMsg.C_001);
				ObjectMapper mapper = new ObjectMapper();
				String res = mapper.writeValueAsString(rb);
				response.flushBuffer();
				response.getWriter().write(res);
				response.getWriter().close();
				return false;
			}
			// 验证token
			TokenModel model = tokenService.getToken(accesstoken);
			// 验证通过
			if (tokenService.checkToken(model)) {
				// 获取管理员权限菜单
				int counts = adminLoginService.selectPopedomsPermission(model.getUserid(), requestUri);
				// 如果存在将放行
				if (counts > 0) {
					return true;
				} else {
					// 否则异常权限
					ResultBean rb = new ResultBean("003", ResultBgMsg.C_003);
					ObjectMapper mapper = new ObjectMapper();
					String res = mapper.writeValueAsString(rb);
					response.flushBuffer();
					response.getWriter().write(res);
					response.getWriter().close();
					return false;
				}
			}*/
		}
		return true;
	}
}
