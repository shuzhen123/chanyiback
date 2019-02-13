/**  
* @Title: FeedbackController.java
* @Package dianfan.controller.our
* @Description: TODO
* @author yl
* @date 2018年6月28日 下午6:09:03
* @version V1.0  
*/
package dianfan.controller.our;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;

import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

import dianfan.annotations.SystemControllerLog;
import dianfan.constant.ConstantIF;
import dianfan.constant.ResultApiMsg;
import dianfan.entities.our.UserInfoModel;
import dianfan.models.ResultBean;
import dianfan.models.TokenModel;
import dianfan.service.impl.RedisTokenService;
import dianfan.service.our.FeedbackService;
import dianfan.service.our.PersonalInfoService;
import dianfan.util.RegexUtils;

/**
 * @ClassName FeedbackController
 * @Description
 * @author yl
 * @date 2018年6月28日 下午6:09:03
 */
@Scope(WebApplicationContext.SCOPE_REQUEST)
@Controller
@RequestMapping(value = "/feedback")
public class FeedbackController {

	/**
	 * 注入：#FeedbackService
	 */
	@Autowired
	private FeedbackService feedbackService;
	@Autowired
	private RedisTokenService redisTokenService;
	@Autowired
	private PersonalInfoService personalInfoService;

	/**
	 * @Title: addFeedback
	 * @Description:
	 * @param id
	 * @return
	 * @throws:
	 * @time: 2018年6月28日 下午6:17:49
	 */
	@SystemControllerLog(method = "addFeedback", logtype = ConstantIF.LOG_TYPE_1, description = "提交意见反馈")
	@ApiOperation(value = "提交意见反馈", httpMethod = "POST", notes = "提交意见反馈", response = ResultBean.class)
	@RequestMapping(value = "/addFeedback", method = RequestMethod.POST)
	@ResponseBody
	public ResultBean addFeedback(
			@ApiParam(value = "token值") @RequestParam(value = ConstantIF.ACCESSTOKEN, required = true) String accesstoken,
			@ApiParam(value = "图片地址") @RequestParam(value = "picurl", required = true) String picurl,
			@ApiParam(value = "手机号码") @RequestParam(value = "telno", required = true) String telno,
			@ApiParam(value = "反馈内容") @RequestParam(value = "content", required = true) String content) {

		/**
		 * 验证手机格式
		 */
		if (!RegexUtils.phoneRegex(telno)) {
			return new ResultBean("002", ResultApiMsg.C_002);
		}
		
		//图片地址限制位数
		int piclen = picurl.length();
		if (piclen>20000) {
			return new ResultBean("2025",ResultApiMsg.C_2025);
		}
		
		//反馈内容限制位数
		int contentlen = content.length();
		if (contentlen>256) {
			return new ResultBean("2026",ResultApiMsg.C_2026);
		}

		TokenModel tokens = redisTokenService.getToken(accesstoken);
		if (tokens !=null) {
			String userid = tokens.getUserid();
			if (StringUtils.isNotEmpty(userid)) {
				// 此处调用业务逻辑层
				UserInfoModel uim = personalInfoService.getUserInfo(userid);
				if (uim !=null) {
					feedbackService.addFeedback(userid,picurl,telno,content);
					return new ResultBean();
				}
			}
		}
		return new ResultBean("001",ResultApiMsg.C_001);
	}

}
