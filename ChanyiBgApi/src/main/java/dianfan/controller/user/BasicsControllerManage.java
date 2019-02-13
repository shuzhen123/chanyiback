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
import dianfan.models.ResultBean;
import dianfan.models.TokenModel;
import dianfan.service.impl.RedisTokenService;
import dianfan.service.usermanage.BasicsService;
import dianfan.util.StringUtility;


/**
 * @ClassName BasicsControllerManage
 * @Description 系统数据
 * @author sz
 * @date 2018年7月23日 下午3:09:48
 */
@Scope(WebApplicationContext.SCOPE_REQUEST)
@Controller
@RequestMapping(value = "/basics")
public class BasicsControllerManage {
	
	/**
	 * 注入 : #BasicsService
	 */
	@Autowired
	private BasicsService basicsService;
	
	/**
	 * 注入: #RedisTokenService
	 */
	@Autowired
	private RedisTokenService redisTokenService;
	
	
	
	/**
	 * @Title: findBasicsData
	 * @Description: 获取系统数据
	 * @param accesstoken
	 * @return
	 * @throws:
	 * @time: 2018年7月23日 下午3:21:20
	 */
	@SystemControllerLog(method = "findBasicsData", logtype = ConstantIF.LOG_TYPE_2, description = "获取基础数据")
	@RequestMapping(value = "/findBasicsData", method = RequestMethod.POST)	
	public @ResponseBody ResultBean findBasicsData (
			@ApiParam(value="accesstoken") @RequestParam(value=ConstantIF.ACCESSTOKEN) String accesstoken
			) {
		// 构建返回模型
		ResultBean result = new ResultBean();
		TokenModel token = redisTokenService.getToken(accesstoken);
		if (org.springframework.util.StringUtils.isEmpty(token)) {
			// 登陆信息已经过期
			return new ResultBean("001",ResultBgMsg.C_001);
		}
		String userid = token.getUserid();
		result = basicsService.findBasicsData(userid);
		// 返回
		return result;
	}
	
	
	
	
	
	/**
	 * @Title: findSettingSwitchs
	 * @Description: 获取设置开关列表
	 * @param accesstoken accesstoken
	 * @return ResultBean
	 * @throws:
	 * @time: 2018年7月24日 上午9:28:27
	 */
	@SystemControllerLog(method = "findSettingSwitchs", logtype = ConstantIF.LOG_TYPE_2, description = "获取设置开关列表")
	@RequestMapping(value = "/findSettingSwitchs", method = RequestMethod.POST)	
	public @ResponseBody ResultBean findSettingSwitchs (
			@ApiParam(value="accesstoken") @RequestParam(value=ConstantIF.ACCESSTOKEN) String accesstoken
			) {
		// 构建返回模型
		ResultBean result = new ResultBean();
		TokenModel token = redisTokenService.getToken(accesstoken);
		if (org.springframework.util.StringUtils.isEmpty(token)) {
			// 登陆信息已经过期
			return new ResultBean("001",ResultBgMsg.C_001);
		}
		String userid = token.getUserid();
		result = basicsService.findSettingSwitchs(userid);
		// 返回
		return result;
	}
	
	
	/**
	 * @Title: editSettingSwitchs
	 * @Description: 修改设置开关列表
	 * @param accesstoken accesstoken
	 * @return ResultBean
	 * @throws:
	 * @time: 2018年7月24日 上午9:28:27
	 */
	@SystemControllerLog(method = "editSettingSwitchs", logtype = ConstantIF.LOG_TYPE_2, description = "修改设置开关列表")
	@RequestMapping(value = "/editSettingSwitchs", method = RequestMethod.POST)	
	public @ResponseBody ResultBean editSettingSwitchs (
			@ApiParam(value="accesstoken") @RequestParam(value=ConstantIF.ACCESSTOKEN) String accesstoken,
			@ApiParam(value="开关id") @RequestParam(value="id") String ids,
			@ApiParam(value="消费商审批开关") @RequestParam(value="type") int flag,
			@ApiParam(value="消费商审批开关描述") @RequestParam(value="des") String des
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
		result = basicsService.updataeditSettingSwitchs(userid,ids,flag,des);
		// 返回
		return result;
	}
	
	
	
	/*---------------------------------------------------后台自用接口-----------------------------------------------------------------*/
	
	/**
	 * @Title: findPopedomList
	 * @Description: 获取权限菜单表
	 * @param accesstoken
	 * @return 
	 * @throws:
	 * @time: 2018年7月24日 下午12:01:19
	 */
	@SystemControllerLog(method = "findPopedomList", logtype = ConstantIF.LOG_TYPE_2, description = "获取权限菜单表")
	@RequestMapping(value = "/findPopedomList", method = RequestMethod.POST)	
	public @ResponseBody ResultBean findPopedomList (
			@ApiParam(value="accesstoken") @RequestParam(value=ConstantIF.ACCESSTOKEN) String accesstoken
			) {
		// 构建返回模型
		ResultBean result = new ResultBean();
		TokenModel token = redisTokenService.getToken(accesstoken);
		if (org.springframework.util.StringUtils.isEmpty(token)) {
			// 登陆信息已经过期
			return new ResultBean("001",ResultBgMsg.C_001);
		}
		String userid = token.getUserid();
		result = basicsService.findPopedomList(userid);
		// 返回
		return result;
	}
	
	
	
	/**
	 * @Title: addAdminPopedoms
	 * @Description: 添加管理员权限自用
	 * @param accesstoken accesstoken
	 * @param popedomname 权限名称
	 * @param popedomfatherid 权限父ID
	 * @param popedomurl 树的连接路径
	 * @param sort 排序
	 * @param kind kind
	 * @return
	 * @throws:
	 * @time: 2018年7月24日 下午3:09:47
	 */
	@SystemControllerLog(method = "addAdminPopedoms", logtype = ConstantIF.LOG_TYPE_2, description = "添加管理员权限自用")
	@RequestMapping(value = "/addAdminPopedoms", method = RequestMethod.POST)	
	public @ResponseBody ResultBean addAdminPopedoms (
			@ApiParam(value="accesstoken") @RequestParam(value=ConstantIF.ACCESSTOKEN) String accesstoken,
			@ApiParam(value="权限名称") @RequestParam(value="popedomname") String popedomname,
			@ApiParam(value="权限父ID") @RequestParam(value="popedomfatherid") String popedomfatherid,
			@ApiParam(value="树的连接路径") @RequestParam(value="popedomurl") String popedomurl,
			@ApiParam(value="排序") @RequestParam(value="sort") String sort,
			@ApiParam(value="kind") @RequestParam(value="kind") String kind
			) {
		// 构建返回模型
		ResultBean result = new ResultBean();
		TokenModel token = redisTokenService.getToken(accesstoken);
		if (org.springframework.util.StringUtils.isEmpty(token)) {
			// 登陆信息已经过期
			return new ResultBean("001",ResultBgMsg.C_001);
		}
		String userid = token.getUserid();
		if (StringUtility.isNull(popedomname) || StringUtility.isNull(sort) || StringUtility.isNull(kind)) {
			// 添加的权限信息不完整
			return new ResultBean("4026",ResultBgMsg.C_4026);
		}
		result = basicsService.addAdminPopedoms(userid,popedomname,popedomfatherid,popedomurl,sort,kind);
		// 返回
		return result;
	}
	
	/**
	 * @Title: editAdminPopedoms
	 * @Description: 修改管理员权限自用
	 * @param accesstoken accesstoken
	 * @param popedomname 权限名称
	 * @param popedomfatherid 权限父ID
	 * @param popedomurl 树的连接路径
	 * @param sort 排序
	 * @param kind kind
	 * @return
	 * @throws:
	 * @time: 2018年7月24日 下午3:09:47
	 */
	@SystemControllerLog(method = "editAdminPopedoms", logtype = ConstantIF.LOG_TYPE_2, description = "修改管理员权限自用")
	@RequestMapping(value = "/editAdminPopedoms", method = RequestMethod.POST)	
	public @ResponseBody ResultBean editAdminPopedoms (
			@ApiParam(value="accesstoken") @RequestParam(value=ConstantIF.ACCESSTOKEN) String accesstoken,
			@ApiParam(value="权限名称") @RequestParam(value="popedomname") String popedomname,
			@ApiParam(value="权限id") @RequestParam(value="id") String id,
			@ApiParam(value="权限父ID") @RequestParam(value="popedomfatherid") String popedomfatherid,
			@ApiParam(value="树的连接路径") @RequestParam(value="popedomurl") String popedomurl,
			@ApiParam(value="排序") @RequestParam(value="sort") String sort,
			@ApiParam(value="kind") @RequestParam(value="kind") String kind
			) {
		// 构建返回模型
		ResultBean result = new ResultBean();
		TokenModel token = redisTokenService.getToken(accesstoken);
		if (org.springframework.util.StringUtils.isEmpty(token)) {
			// 登陆信息已经过期
			return new ResultBean("001",ResultBgMsg.C_001);
		}
		String userid = token.getUserid();
		if (StringUtility.isNull(popedomname) || StringUtility.isNull(sort) || StringUtility.isNull(kind)) {
			// 添加的权限信息不完整
			return new ResultBean("4026",ResultBgMsg.C_4026);
		}
		result = basicsService.updataAdminPopedoms(userid,popedomname,popedomfatherid,popedomurl,sort,kind,id);
		// 返回
		return result;
	}
	
	/**
	 * @Title: delAdminPopedoms
	 * @Description: 删除管理员权限 自用
	 * @param accesstoken accesstoken
	 * @param ids  权限id
	 * @return
	 * @throws:
	 * @time: 2018年7月24日 下午4:00:02
	 */
	@SystemControllerLog(method = "delAdminPopedoms", logtype = ConstantIF.LOG_TYPE_2, description = "删除管理员权限 自用")
	@RequestMapping(value = "/delAdminPopedoms", method = RequestMethod.POST)	
	public @ResponseBody ResultBean delAdminPopedoms (
			@ApiParam(value="accesstoken") @RequestParam(value=ConstantIF.ACCESSTOKEN) String accesstoken,
			@ApiParam(value="权限ids") @RequestParam(value="ids") String ids ) {
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
		result = basicsService.delAdminPopedoms(userid, ids);
		
		// 返回
		return result;
	}
	
	
	
}
