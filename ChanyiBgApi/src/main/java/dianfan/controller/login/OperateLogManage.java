package dianfan.controller.login;

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
import dianfan.constant.ConstantIF;
import dianfan.constant.ResultBgMsg;
import dianfan.models.ResultBean;
import dianfan.models.TokenModel;
import dianfan.service.adminlogin.AdminLoginService;
import dianfan.service.impl.RedisTokenService;

/**
 * @ClassName OperateLogManage
 * @Description 获取业务日志相关  Manage
 * @author sz
 * @date 2018年7月27日 下午1:13:50
 */
@Scope(WebApplicationContext.SCOPE_REQUEST)
@Controller
@RequestMapping(value = "/operateLog")
public class OperateLogManage {
	
	@Autowired
	private AdminLoginService adminLoginService;
	@Autowired
	private RedisTokenService redisTokenService;
	
	
	/**
	 * @Title: findOperateLogList
	 * @Description: 获取业务日志列表
	 * @param accesstoken accesstoken
	 * @param page 第几页
	 * @param pageSize 每页第几条
	 * @param name 名字搜索
	 * @param role 角色搜索
	 * @param starttime 开始时间
	 * @param endtime 结束时间
	 * @return ResultBean
	 * @throws:
	 * @time: 2018年7月27日 下午1:32:53
	 */
	@SystemControllerLog(method = "findOperateLogList", logtype = ConstantIF.LOG_TYPE_2, description = "获取业务日志列表")
	@RequestMapping(value = "/findOperateLogList", method = RequestMethod.POST)	
	public @ResponseBody ResultBean findOperateLogList (
			@ApiParam(value="accesstoken") @RequestParam(value=ConstantIF.ACCESSTOKEN) String accesstoken,
			@ApiParam(value="请求页") @RequestParam(value=ConstantIF.PAGE, defaultValue=ConstantIF.PAGE_START_STR) int page,
			@ApiParam(value="每页条数") @RequestParam(value=ConstantIF.PAGE_SIZE, defaultValue=ConstantIF.PAGE_OFFSET_STR) int pageSize,
			@ApiParam(value="按用户名搜索") @RequestParam(value="name",required=false) String name,
			@ApiParam(value="按角色") @RequestParam(value="roleid",required=false) String role,
			@ApiParam(value="按开始时间") @RequestParam(value="starttime",required=false) String starttime,
			@ApiParam(value="按结束时间") @RequestParam(value="endtime",required=false) String endtime
			) {
		// 构建返回模型
		ResultBean result = new ResultBean();
		TokenModel token = redisTokenService.getToken(accesstoken);
		if (org.springframework.util.StringUtils.isEmpty(token)) {
			// 登陆信息已经过期
			return new ResultBean("001",ResultBgMsg.C_001);
		}
		String userid = token.getUserid();
		result = adminLoginService.findOperateLogList(userid,page,pageSize,name,role,starttime,endtime);
		
		return result;
	}
	
	
	

}
