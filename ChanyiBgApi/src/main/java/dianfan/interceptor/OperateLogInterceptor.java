package dianfan.interceptor;

import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.fasterxml.jackson.databind.ObjectMapper;

import dianfan.annotations.UnCheckedFilter;
import dianfan.constant.ConstantIF;
import dianfan.constant.ResultBgMsg;
import dianfan.models.ResultBean;
import dianfan.models.TokenModel;
import dianfan.service.adminlogin.AdminLoginService;
import dianfan.service.impl.RedisTokenService;
import dianfan.util.IpUtil;
import dianfan.util.StringUtility;

/**
 * @ClassName OperateLogInterceptor
 * @Description 业务日志拦截器
 * @author sz
 * @date 2018年7月27日 上午10:00:16
 */
public class OperateLogInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	private RedisTokenService tokenService;
	@Autowired
	AdminLoginService adminLoginService;
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
		// 获取项目名称
		String webcontentName = request.getContextPath();
		// 权限验证
		// 获取uri
		String requestUri = request.getRequestURI();
		if (requestUri.contains("loginAction") || requestUri.contains("getVerifyCode")) {
			// 如果是登陆接口,或者是获取图片验证码的接口，暂时先不记录直接放行
			
			return true;
		}
		
		// 发布的后端项目名称
		boolean fbUrl = requestUri.contains(webcontentName);
		if (!fbUrl)
			return true;
		else {
			// 登录信息是否已过期判断
			String accesstoken = request.getParameter(ConstantIF.ACCESSTOKEN);
			TokenModel model = tokenService.getToken(accesstoken);
			if (StringUtils.isEmpty(model)) {
				ResultBean rb = new ResultBean("001", ResultBgMsg.C_001);
				ObjectMapper mapper = new ObjectMapper();
				String res = mapper.writeValueAsString(rb);
				response.flushBuffer();
				response.getWriter().write(res);
				response.getWriter().close();
				return false;
			}
			/*--整理需要添加的数据--*/
			// 1.获取userid
			String userid = model.getUserid();
			
			// 2.获取用户访问的接口
			// 2.1 获取 请求地址
			requestUri = requestUri.substring(webcontentName.length());
			// 2.2 通过requestUri 去查找对应的ID
			String popedomid = adminLoginService.getPopedomidByUri(requestUri);
			if  (StringUtility.isNull(popedomid)) {
				return true;
			}
			
			// 3.获取用户的角色ID
			String roleid = adminLoginService.getRoleidByUserid(userid);
			
			// 4.操作内容
			
			// 获取request中所有参数的方法
			Map<Object, Object> map = new HashMap<Object, Object>();
			// 参数置空
			Enumeration<?> enu = request.getParameterNames();
			while (enu.hasMoreElements()) {
				String paraName = (String) enu.nextElement();
				// 打印参数
				System.out.println(paraName + ":" + request.getParameter(paraName));
				map.put(paraName, request.getParameter(paraName));
			}
			ObjectMapper json = new ObjectMapper();
			// 把map对象转成json格式的String字符串
			String operate = json.writeValueAsString(map);
			
			// 5.获取登陆ip
			String ip = IpUtil.getIpAddr(request);
			// 插入到系统日志表中
			adminLoginService.addOperateLog(userid, popedomid, roleid, operate, ip);
			}
		return true;
	}
	
}
