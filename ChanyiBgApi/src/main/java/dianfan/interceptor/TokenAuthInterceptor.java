package dianfan.interceptor;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.fasterxml.jackson.databind.ObjectMapper;

import dianfan.annotations.UnCheckedFilter;
import dianfan.constant.ConstantIF;
import dianfan.constant.ResultApiMsg;
import dianfan.constant.ResultBgMsg;
import dianfan.dao.mapper.login.UserLoginMapper;
import dianfan.models.ResultBean;
import dianfan.models.TokenModel;
import dianfan.service.impl.RedisTokenService;

/**
 * @ClassName TokenAuthInterceptor
 * @Description token验证拦截器
 * @author cjy
 * @date 2018年6月30日 上午10:19:38
 */
public class TokenAuthInterceptor extends HandlerInterceptorAdapter {
	@Autowired
	private UserLoginMapper userLoginMapper;
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

		// 获取accesstoken
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
			//检测是否被锁
			boolean b = userLoginMapper.checkAdminLocked(model.getUserid());
			if(!b) {
				//账号被锁
				ResultBean rb = new ResultBean("4022", ResultApiMsg.C_4022);
				ObjectMapper mapper = new ObjectMapper();
				String res = mapper.writeValueAsString(rb);
				response.flushBuffer();
				response.getWriter().write(res);
				response.getWriter().close();
				return false;
			}
			return true;
		} else {
			// 验证失败
			ResultBean rb = new ResultBean("001", ResultBgMsg.C_001);
			ObjectMapper mapper = new ObjectMapper();
			String res = mapper.writeValueAsString(rb);
			response.flushBuffer();
			response.getWriter().write(res);
			response.getWriter().close();
		}
		return false;
	}

}
