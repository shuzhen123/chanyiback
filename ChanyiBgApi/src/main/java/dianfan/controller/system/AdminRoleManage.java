package dianfan.controller.system;

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
import dianfan.service.adminmanage.AdminRoleService;
import dianfan.service.impl.RedisTokenService;
import dianfan.util.StringUtility;

/**
 * @ClassName AdminRoleManage
 * @Description 管理员角色相关
 * @author sz
 * @date 2018年7月17日 下午5:41:45
 */
@Scope(WebApplicationContext.SCOPE_REQUEST)
@Controller
@RequestMapping(value = "/adminRole")
public class AdminRoleManage {
	
	/**
	 * 注入: #RedisTokenService
	 */
	@Autowired
	private RedisTokenService redisTokenService;
	
	/**
	 * 注入: #AdminRoleService
	 */
	@Autowired
	private AdminRoleService adminRoleService;
	
	
	
	/**
	 * @Title: admiRolenList
	 * @Description: 获取管理员角色列表
	 * @param accesstoken accesstoken
	 * @return ResultBean
	 * @throws:
	 * @time: 2018年7月17日 下午6:26:56
	 */
	@SystemControllerLog(method = "adminRoleList", logtype = ConstantIF.LOG_TYPE_1, description = "获取管理员角色列表")
	@RequestMapping(value = "/adminRoleList", method = RequestMethod.POST)
	public @ResponseBody ResultBean adminRoleList (
			@ApiParam(value="accesstoken") @RequestParam(value=ConstantIF.ACCESSTOKEN) String accesstoken
			) {
		// 构建返回模型
		ResultBean result = new ResultBean();
		// 调库查询
		result = adminRoleService.findadmiRolenList();
		// 返回
		return result;
	}
	
	
	/**
	 * @Title: addAdmiRolen
	 * @Description: 添加管理员角色
	 * @param accesstoken accesstoken
	 * @param distinguish 角色区分
	 * @param name 角色名称
	 * @param description 角色描述
	 * @param roleids 权限IDs
	 * @return ResultBean
	 * @throws:
	 * @time: 2018年7月17日 下午7:01:22
	 */
	@SystemControllerLog(method = "addAdminRole", logtype = ConstantIF.LOG_TYPE_1, description = "添加管理员角色")
	@RequestMapping(value = "/addAdminRole", method = RequestMethod.POST)
	public @ResponseBody ResultBean addAdminRole (
			@ApiParam(value="accesstoken") @RequestParam(value=ConstantIF.ACCESSTOKEN) String accesstoken, 
			@ApiParam(value="角色区分") @RequestParam(value="distinguish") String distinguish,
			@ApiParam(value="角色名称") @RequestParam(value="name") String name,
			@ApiParam(value="角色描述") @RequestParam(value="description") String description,
			@ApiParam(value="角色下的权限") @RequestParam(value="roleids") String roleids
			) {
		// 构建返回模型
		ResultBean result = new ResultBean();
		// 入参非空验证
		if (StringUtility.isNull(distinguish) ||StringUtility.isNull(name) ||StringUtility.isNull(description)) {
			// 入参请填写完整
			return new ResultBean("4013" ,ResultBgMsg.C_4013);
		}
		if (StringUtility.isNull(roleids)) {
			// 权限不可为空
			return new ResultBean("4015" ,ResultBgMsg.C_4015);
		}
		result = adminRoleService.addAdmiRolen(distinguish,name,description,roleids);
		// 返回
		return result;
	}
	
	
	
	/**
	 * @Title: editAdmiRolen
	 * @Description: 修改角色
	 * @param accesstoken accesstoken
	 * @param distinguish 角色区分
	 * @param name 角色名称
	 * @param description 角色描述
	 * @param roleids 权限IDs
	 * @return
	 * @throws:
	 * @time: 2018年7月18日 上午9:43:41
	 */
	@SystemControllerLog(method = "editAdminRole", logtype = ConstantIF.LOG_TYPE_1, description = "修改角色")
	@RequestMapping(value = "/editAdminRole", method = RequestMethod.POST)
	public @ResponseBody ResultBean editAdminRole (
			@ApiParam(value="accesstoken") @RequestParam(value=ConstantIF.ACCESSTOKEN) String accesstoken, 
			@ApiParam(value="当前修改的角色ID") @RequestParam(value="id") String id,
			@ApiParam(value="角色区分") @RequestParam(value="distinguish") String distinguish,
			@ApiParam(value="角色名称") @RequestParam(value="name") String name,
			@ApiParam(value="角色描述") @RequestParam(value="description") String description,
			@ApiParam(value="角色下的权限") @RequestParam(value="roleids") String roleids
			) {
		// 构建返回模型
		ResultBean result = new ResultBean();
		// 获取  登陆者id
		TokenModel token = redisTokenService.getToken(accesstoken);
		String userid = token.getUserid();
		
		if (StringUtility.isNull(id)) {
			// 参数错误
			return new ResultBean("501" ,ResultBgMsg.C_501);
		}
		// 入参非空验证
		if (StringUtility.isNull(distinguish) ||StringUtility.isNull(name) ||StringUtility.isNull(description)) {
			// 入参请填写完整
			return new ResultBean("4013" ,ResultBgMsg.C_4013);
		}
		if (StringUtility.isNull(roleids)) {
			// 权限不可为空
			return new ResultBean("4015" ,ResultBgMsg.C_4015);
		}
		result = adminRoleService.updataAdmiRolen(distinguish,name,description,roleids,userid,id);
		// 返回
		return result;
	}
	
	/**
	 * @Title: delAdminRole
	 * @Description: 删除角色
	 * @param accesstoken accesstoken
	 * @param ids 角色ID
	 * @return
	 * @throws:
	 * @time: 2018年7月18日 上午11:16:09
	 */
	@SystemControllerLog(method = "delAdminRole", logtype = ConstantIF.LOG_TYPE_1, description = "删除角色")
	@RequestMapping(value = "/delAdminRole", method = RequestMethod.POST)
	public @ResponseBody ResultBean delAdminRole (
			@ApiParam(value="accesstoken") @RequestParam(value=ConstantIF.ACCESSTOKEN) String accesstoken, 
			@ApiParam(value="需要删除的角色ids") @RequestParam(value="ids") String ids
			) {
		// 构建返回模型
		ResultBean result = new ResultBean();
		// 获取  登陆者id
		TokenModel token = redisTokenService.getToken(accesstoken);
		String userid = token.getUserid();
		// 非空验证
		if (StringUtility.isNull(ids)) {
			// 参数错误
			return new ResultBean("501" ,ResultBgMsg.C_501);
		}
		result = adminRoleService.delAdmiRolen(userid,ids);
		// 返回
		return result;
	}
	
	/**
	 * @Title: findAdminPopedom
	 * @Description: 获取管理员权限列表
	 * @param accesstoken accesstoken
	 * @return ResultBean
	 * @throws:
	 * @time: 2018年7月18日 下午2:26:19
	 */
	@SystemControllerLog(method = "findAdminPopedom", logtype = ConstantIF.LOG_TYPE_1, description = "获取管理员权限列表")
	@RequestMapping(value = "/findAdminPopedom", method = RequestMethod.POST)
	public @ResponseBody ResultBean findAdminPopedom(
			@ApiParam(value="accesstoken") @RequestParam(value=ConstantIF.ACCESSTOKEN) String accesstoken
			) {
		// 构建返回模型
		ResultBean result = new ResultBean();
		result = adminRoleService.findAdminPopedom();
		// 返回
		return result;
	}
	
	
}
