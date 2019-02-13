package dianfan.controller.user;

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
import dianfan.md5.MD5;
import dianfan.models.ResultBean;
import dianfan.models.TokenModel;
import dianfan.service.impl.RedisTokenService;
import dianfan.service.usermanage.UserServiceManage;
import dianfan.util.RegexUtils;
import dianfan.util.StringUtility;

/**
 * @ClassName UserManage
 * @Description 后台用户管理
 * @author sz
 * @date 2018年7月23日 上午11:13:55
 */
@Scope(WebApplicationContext.SCOPE_REQUEST)
@Controller
@RequestMapping(value = "/userManage")
public class UserManage {
	
	
	/**
	 * 注入: #RedisTokenService
	 */
	@Autowired
	private RedisTokenService redisTokenService;
	
	/**
	 * 注入: #UserManage
	 */
	@Autowired
	private UserServiceManage userServiceManage;
	
	
	/**
	 * @Title: findUserList
	 * @Description: 获取用户列表
	 * @param accesstoken accesstoken
	 * @return
	 * @throws:
	 * @time: 2018年7月23日 上午11:27:14
	 */
	@SystemControllerLog(method = "findUserList", logtype = ConstantIF.LOG_TYPE_2, description = "获取用户列表")
	@RequestMapping(value = "/findUserList", method = RequestMethod.POST)	
	public @ResponseBody ResultBean findUserList (
			@ApiParam(value="accesstoken") @RequestParam(value=ConstantIF.ACCESSTOKEN) String accesstoken,
			@ApiParam(value="请求页") @RequestParam(value=ConstantIF.PAGE, defaultValue=ConstantIF.PAGE_START_STR) int page,
			@ApiParam(value="每页条数") @RequestParam(value=ConstantIF.PAGE_SIZE, defaultValue=ConstantIF.PAGE_OFFSET_STR) int pageSize,
			@ApiParam(value="按用户名搜索") @RequestParam(value="name",required=false) String name,
			@ApiParam(value="按手机号搜索") @RequestParam(value="telno",required=false) String telno,
			@ApiParam(value="按来源搜索") @RequestParam(value="source",required=false) String source,
			@ApiParam(value="按开始时间") @RequestParam(value="starttime",required=false) String starttime,
			@ApiParam(value="按结束时间") @RequestParam(value="endtime",required=false) String endtime,
			@ApiParam(value="按角色") @RequestParam(value="role",required=false) String role,
			@ApiParam(value="按性别") @RequestParam(value="sex",required=false) String sex,
			@ApiParam(value="按区域") @RequestParam(value="areacode",required=false) String areacode,
			@ApiParam(value="用户状态") @RequestParam(value="entkbn",required=false) Integer entkbn
			) {
		// 构建返回模型
		ResultBean result = new ResultBean();
		TokenModel token = redisTokenService.getToken(accesstoken);
		if (org.springframework.util.StringUtils.isEmpty(token)) {
			// 登陆信息已经过期
			return new ResultBean("001",ResultBgMsg.C_001);
		}
		String userid = token.getUserid();
		if (page == 0 || pageSize == 0) {
			return new ResultBean("501",ResultBgMsg.C_501);
		}
		// 调库查询
		result = userServiceManage.findUserList(userid,page,pageSize,name,telno,source,starttime,endtime,role,sex,areacode,entkbn);
		// 返回
		return result;
	}
	
	
	/**
	 * @Title: editUserPwd
	 * @Description: 修改用户密码
	 * @param accesstoken accesstoken
	 * @param id 被修改的ID
	 * @param newPwd 新密码
	 * @return
	 * @throws:
	 * @time: 2018年7月23日 下午2:21:33
	 */
	@SystemControllerLog(method = "editUserPwd", logtype = ConstantIF.LOG_TYPE_2, description = "修改用户密码")
	@RequestMapping(value = "/editUserPwd", method = RequestMethod.POST)
	public @ResponseBody ResultBean editUserPwd (
			@ApiParam(value="accesstoken") @RequestParam(value=ConstantIF.ACCESSTOKEN) String accesstoken,
			@ApiParam(value="用户ID") @RequestParam(value="id") String id,
			@ApiParam(value="新密码") @RequestParam(value="newPwd") String newPwd
			) {
		// 构建返回模型
		ResultBean result = new ResultBean();
		TokenModel token = redisTokenService.getToken(accesstoken);
		if (org.springframework.util.StringUtils.isEmpty(token)) {
			// 登陆信息已经过期
			return new ResultBean("001",ResultBgMsg.C_001);
		}
		String userid = token.getUserid();
		if (newPwd.length() < 6 || newPwd.length() > 12) {
			return new ResultBean("4018",ResultBgMsg.C_4018);
		}
		String md5Pwd = MD5.string2MD5(newPwd);
		result = userServiceManage.updataUserPwd(userid,id,md5Pwd);
		// 返回
		return result;
	}
	
	/**
	 * @Title: editUserTelno
	 * @Description: 修改用户手机号
	 * @param accesstoken accesstoken
	 * @param id 被修改的ID
	 * @param telno 新手机
	 * @return
	 * @throws:
	 * @time: 2018年7月23日 下午2:21:33
	 */
	@SystemControllerLog(method = "editUserTelno", logtype = ConstantIF.LOG_TYPE_2, description = "修改用户手机号")
	@RequestMapping(value = "/editUserTelno", method = RequestMethod.POST)
	public @ResponseBody ResultBean editUserTelno (
			@ApiParam(value="accesstoken") @RequestParam(value=ConstantIF.ACCESSTOKEN) String accesstoken,
			@ApiParam(value="用户ID") @RequestParam(value="id") String id,
			@ApiParam(value="新手机号") @RequestParam(value="telno") String telno
			) {
		// 构建返回模型
		ResultBean result = new ResultBean();
		TokenModel token = redisTokenService.getToken(accesstoken);
		if (org.springframework.util.StringUtils.isEmpty(token)) {
			// 登陆信息已经过期
			return new ResultBean("001",ResultBgMsg.C_001);
		}
		String userid = token.getUserid();
		if (!RegexUtils.phoneRegex(telno) ) {
			// 手机号码格式不正确
			return new ResultBean("002",ResultBgMsg.C_002);
		}
		
		result = userServiceManage.updataUserTelno(userid,id,telno);
		// 返回
		return result;
	}
	
	/**
	 * @Title: editUserType
	 * @Description: 修改用户状态
	 * @param accesstoken
	 * @param ids
	 * @param type
	 * @return
	 * @throws:
	 * @time: 2018年7月23日 下午2:59:41
	 */
	@SystemControllerLog(method = "editUserType", logtype = ConstantIF.LOG_TYPE_2, description = "修改用户状态")
	@RequestMapping(value = "/editUserType", method = RequestMethod.POST)
	public @ResponseBody ResultBean editUserType (
			@ApiParam(value="accesstoken") @RequestParam(value=ConstantIF.ACCESSTOKEN) String accesstoken,
			@ApiParam(value="用户IDs") @RequestParam(value="ids") String ids,
			@ApiParam(value="状态") @RequestParam(value="type") int type
			) {
		// 构建返回模型
		ResultBean result = new ResultBean();
		TokenModel token = redisTokenService.getToken(accesstoken);
		if (org.springframework.util.StringUtils.isEmpty(token)) {
			// 登陆信息已经过期
			return new ResultBean("001",ResultBgMsg.C_001);
		}
		String userid = token.getUserid();
		if (StringUtility.isNull(ids)) {
			return new ResultBean("501",ResultBgMsg.C_501);
		}
		result = userServiceManage.updataUserType(userid, ids, type);
		
		// 返回
		return result;
	}
	

}
