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
import dianfan.service.adminmanage.AdminFeedbackService;
import dianfan.service.impl.RedisTokenService;
import dianfan.util.StringUtility;
/**
 * @ClassName FeedbackManage
 * @Description 反馈意见
 * @author sz
 * @date 2018年7月18日 下午2:57:23
 */
@Scope(WebApplicationContext.SCOPE_REQUEST)
@Controller
@RequestMapping(value = "/feedback")
public class FeedbackManage {
	
	/**
	 * 注入： #FeedbackService
	 */
	@Autowired
	private AdminFeedbackService feedbackService;
	
	/**
	 * 注入: #RedisTokenService
	 */
	@Autowired
	private RedisTokenService redisTokenService;
	
	
	/**
	 * @Title: feedbackList
	 * @Description: 用户意见反馈列表
	 * @param accesstoken accesstoken
	 * @return 
	 * @throws:
	 * @time: 2018年7月18日 下午3:09:14
	 */
	@SystemControllerLog(method = "feedbackList", logtype = ConstantIF.LOG_TYPE_2, description = "用户意见反馈列表")
	@RequestMapping(value = "/feedbackList", method = RequestMethod.POST)
	public @ResponseBody ResultBean feedbackList (
			@ApiParam(value="accesstoken") @RequestParam(value="accesstoken") String accesstoken,
			@ApiParam(value="请求页") @RequestParam(value=ConstantIF.PAGE, defaultValue=ConstantIF.PAGE_START_STR) int page,
			@ApiParam(value="每页条数") @RequestParam(value=ConstantIF.PAGE_SIZE, defaultValue=ConstantIF.PAGE_OFFSET_STR) int length,
			@ApiParam(value="按状态筛选") @RequestParam(value="type",required=false) String type,
			@ApiParam(value="按开始时间") @RequestParam(value="starttime",required=false) String starttime,
			@ApiParam(value="按结束时间") @RequestParam(value="endtime",required=false) String endtime
			) {
		// 构建返回模型
		ResultBean result = new ResultBean();
		// 调库查询
		result = feedbackService.feedbackList(length, page, type, starttime, endtime);
		// 返回
		return result;
	}
	
	
	/**
	 * @Title: editfeedbackType
	 * @Description: 修改反馈状态 
	 * @param accesstoken accesstoken
	 * @param type 状态 (01 待解决 02 已解决)
	 * @return ResultBean
	 * @throws: 
	 * @time: 2018年7月18日 下午4:44:32
	 */
	@SystemControllerLog(method = "editfeedbackType", logtype = ConstantIF.LOG_TYPE_2, description = "修改反馈状态")
	@RequestMapping(value = "/editfeedbackType", method = RequestMethod.POST)
	public @ResponseBody ResultBean editfeedbackType(
			@ApiParam(value="accesstoken") @RequestParam(value="accesstoken") String accesstoken,
			@ApiParam(value="状态") @RequestParam(value="type") String type,
			@ApiParam(value="反馈ID") @RequestParam(value="id") String id
			) {
		// 构建返回模型
		ResultBean result = new ResultBean();
		// 获取登陆者的ID
		TokenModel token = redisTokenService.getToken(accesstoken);
		String userid = token.getUserid();
		// 基础验证
		if (StringUtility.isNull(type)) {
			// 参数错误
			return new ResultBean("501",ResultBgMsg.C_501);
		}
		result = feedbackService.updataFeedbackType(type, userid, id);
		
		// 返回
		return result;
	}
	
	
	/**
	 * @Title: delfeedback
	 * @Description: 删除反馈状态
	 * @param accesstoken accesstoken
	 * @param ids 反馈IDs
	 * @return ResultBean
	 * @throws:
	 * @time: 2018年7月18日 下午5:14:36
	 */
	@SystemControllerLog(method = "delfeedback", logtype = ConstantIF.LOG_TYPE_2, description = "删除反馈状态")
	@RequestMapping(value = "/delfeedback", method = RequestMethod.POST)
	public @ResponseBody ResultBean delfeedback(
			@ApiParam(value="accesstoken") @RequestParam(value="accesstoken") String accesstoken,
			@ApiParam(value="反馈IDS") @RequestParam(value="ids") String ids
			) {
		// 构建返回模型
		ResultBean result = new ResultBean();
		// 获取登陆者的ID
		TokenModel token = redisTokenService.getToken(accesstoken);
		String userid = token.getUserid();
		// 基础验证
		if (StringUtility.isNull(ids)) {
			// 参数错误
			return new ResultBean("501",ResultBgMsg.C_501);
		}
		// 删除操作
		result = feedbackService.delfeedbackType(ids,userid);
		// 返回
		return result;
	}
	
	
}
