package dianfan.controller.system;

import java.util.Arrays;
import java.util.List;

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
import dianfan.service.adminmanage.AdminService;
import dianfan.service.impl.RedisTokenService;
import dianfan.util.StringUtility;

/**
 * @ClassName AdminManage
 * @Description 后台管理员相关
 * @author sz
 * @date 2018年7月16日 下午4:53:03
 */
@Scope(WebApplicationContext.SCOPE_REQUEST)
@Controller
@RequestMapping(value = "/admin")
public class AdminManage {

	/**
	 * 注入： #AdminService
	 */
	@Autowired
	private AdminService adminService;
	/**
	 * 注入: #RedisTokenService
	 */
	@Autowired
	private RedisTokenService redisTokenService;

	/**
	 * @Title: adminList
	 * @Description: 获取管理员列表
	 * @param start
	 *            开始条数
	 * @param length
	 *            分页偏移量
	 * @param page
	 *            第几页
	 * @return ResultBean
	 * @throws:
	 * @time: 2018年7月17日 上午9:40:28
	 */
	@SystemControllerLog(method = "adminList", logtype = ConstantIF.LOG_TYPE_1, description = "获取管理员列表")
	@RequestMapping(value = "/adminList", method = RequestMethod.GET)
	public @ResponseBody ResultBean adminList(
			@ApiParam(value = "accesstoken") @RequestParam(value = ConstantIF.ACCESSTOKEN) String accesstoken,
			@ApiParam(value = "每页条数") @RequestParam(value = ConstantIF.PAGE_SIZE, defaultValue = ConstantIF.PAGE_OFFSET_STR) int length,
			@ApiParam(value = "请求页") @RequestParam(value = ConstantIF.PAGE, defaultValue = ConstantIF.PAGE_START_STR) int page) {

		// 构架返回数据
		ResultBean result = new ResultBean();
		// 调库查询
		result = adminService.findAdminList(length, page);
		// 返回
		return result;
	}

	/**
	 * @Title: addAdmin
	 * @Description: 添加管理员
	 * @param account
	 *            账号
	 * @param password
	 *            密码
	 * @param role
	 *            角色
	 * @return ResultBean
	 * @throws:
	 * @time: 2018年7月17日 下午3:53:42
	 */
	@SystemControllerLog(method = "addAdmin", logtype = ConstantIF.LOG_TYPE_1, description = "添加管理员")
	@RequestMapping(value = "/addAdmin", method = RequestMethod.POST)
	public @ResponseBody ResultBean addAdmin(
			@ApiParam(value = "accesstoken") @RequestParam(value = ConstantIF.ACCESSTOKEN) String accesstoken,
			@ApiParam(value = "账号") @RequestParam(value = "account") String account,
			@ApiParam(value = "密码") @RequestParam(value = "password") String password,
			@ApiParam(value = "角色ID") @RequestParam(value = "role") String role) {
		// 构架返回数据
		ResultBean result = new ResultBean();
		TokenModel token = redisTokenService.getToken(accesstoken);
		String userid = token.getUserid();
		// 入参验证
		if (StringUtility.isNull(account) || StringUtility.isNull(password)) {
			// ！账号或密码不得为空
			return new ResultBean("4007", ResultBgMsg.C_4007);
		}
		if (StringUtility.isNull(role)) {
			// ！角色不可为空
			return new ResultBean("4009", ResultBgMsg.C_4009);
		}
		if (password.length() < 6 || password.length() > 15) {
			// 密码区间在6到15位之间
			return new ResultBean("4018", ResultBgMsg.C_4018);
		}
		// 密码md5 加密
		String Md5password = MD5.string2MD5(password);
		String accounts = account.trim();
		result = adminService.addAdmin(accounts, Md5password, role, userid);
		// 返回
		return result;
	}

	/**
	 * @Title: editAdmin
	 * @Description: 修改管理员
	 * @param account
	 *            账号
	 * @param password
	 *            密码
	 * @param role
	 *            角色
	 * @return ResultBean
	 * @throws:
	 * @time: 2018年7月17日 下午4:18:42
	 */
	@SystemControllerLog(method = "editAdmin", logtype = ConstantIF.LOG_TYPE_1, description = "修改管理员")
	@RequestMapping(value = "/editAdmin", method = RequestMethod.POST)
	public @ResponseBody ResultBean editAdmin(
			@ApiParam(value = "accesstoken") @RequestParam(value = ConstantIF.ACCESSTOKEN) String accesstoken,
			@ApiParam(value = "管理员的ID") @RequestParam(value = "adminid") String adminid,
			@ApiParam(value = "账号") @RequestParam(value = "account") String account,
			@ApiParam(value = "密码") @RequestParam(value = "password", required = false) String password,
			@ApiParam(value = "角色ID") @RequestParam(value = "role") String role) {
		// 构架返回数据
		ResultBean result = new ResultBean();
		TokenModel token = redisTokenService.getToken(accesstoken);
		String loginid = token.getUserid();
		// 入参验证
		if (StringUtility.isNull(account)) {
			// ！账号不得为空
			return new ResultBean("4007", ResultBgMsg.C_4007);
		}
		if (StringUtility.isNull(role)) {
			// ！角色不可为空
			return new ResultBean("4009", ResultBgMsg.C_4009);
		}
		String Md5password = null;
		if (!StringUtility.isNull(password)) {
			if (password.length() < 6 || password.length() > 15) {
				// 密码区间在6到15位之间
				return new ResultBean("4018", ResultBgMsg.C_4018);
			}
			Md5password = MD5.string2MD5(password);
		}
		result = adminService.updataAdmin(account, Md5password, role, adminid, loginid);
		// 返回
		return result;
	}

	/**
	 * @Title: delAdmin
	 * @Description: 修改管理员状态
	 * @param ids
	 *            管理员IDs
	 * @param flag
	 *            状态
	 * @return
	 * @throws:
	 * @time: 2018年7月17日 下午4:31:54
	 */
	@SystemControllerLog(method = "delAdmin", logtype = ConstantIF.LOG_TYPE_2, description = "删除管理员")
	@RequestMapping(value = "/delAdmin", method = RequestMethod.POST)
	public @ResponseBody ResultBean delAdmin(
			@ApiParam(value = "accesstoken") @RequestParam(value = ConstantIF.ACCESSTOKEN) String accesstoken,
			@ApiParam(value = "管理员IDs") @RequestParam(value = "ids") String ids,
			@ApiParam(value = "状态 ") @RequestParam(value = "flag") String flag) {
		// 构架返回数据
		ResultBean result = new ResultBean();
		// 1.获取token
		TokenModel token = redisTokenService.getToken(accesstoken);

		// 入参判断
		if (StringUtility.isNull(ids)) {
			// 参数不可为空
			return new ResultBean("501", ResultBgMsg.C_501);
		}
		// 将ids转成list
		List<String> adminid = Arrays.asList(ids.split(","));
		// 判断是否包含自身
		if (adminid.contains(token.getUserid())) {
			if ("1".equals(flag)) {
				// 自身不可以冻结自身账号
				return new ResultBean("4016", ResultBgMsg.C_4016);
			} else if ("9".equals(flag)) {
				// 自身不可以删除自身账号
				return new ResultBean("4012", ResultBgMsg.C_4017);
			}
		}
		adminService.delAdmin(adminid, flag, token.getUserid());
		// 返回
		return result;
	}

}
