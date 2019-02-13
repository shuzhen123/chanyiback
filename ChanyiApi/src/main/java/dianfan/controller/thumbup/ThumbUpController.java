package dianfan.controller.thumbup;

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
import dianfan.constant.ResultBgMsg;
import dianfan.models.ResultBean;
import dianfan.models.TokenModel;
import dianfan.service.advertorial.AdvertorialService;
import dianfan.service.impl.RedisTokenService;

/**
 * @ClassName ThumbUpController
 * @Description 点赞控制器
 * @author zwb
 * @date 2018年6月29日 下午4:46:28
 */
@Scope(WebApplicationContext.SCOPE_REQUEST)
@Controller
@RequestMapping(value = "/thumbUp")
public class ThumbUpController {

	/**
	 * 文章service
	 */
	@Autowired
	AdvertorialService advertorialService;

	/**
	 * redisService
	 */
	@Autowired
	RedisTokenService redisTokenService;

	/**
	 * @Title: confirmThumbUp
	 * @Description: 点赞
	 * @param id
	 * @param userId
	 * @return ResultBean
	 * @throws:
	 * @time: 2018年6月29日 下午4:47:10
	 */
	@SystemControllerLog(method = "confirmThumbUp", logtype = ConstantIF.LOG_TYPE_1, description = "点赞")
	@ApiOperation(value = "点赞", httpMethod = "POST", notes = "点赞", response = ResultBean.class)
	@RequestMapping(value = "/confirmThumbUp", method = RequestMethod.POST)
	@ResponseBody
	public ResultBean confirmThumbUp(@ApiParam(value = "文章id") @RequestParam(value = "id") String id,
			@ApiParam(value = "token值") @RequestParam(value = ConstantIF.ACCESSTOKEN) String accesstoken) {
		//
		if (StringUtils.isEmpty(id)) {
			return new ResultBean("501", ResultBgMsg.C_501);
		}
		TokenModel tokens = redisTokenService.getToken(accesstoken);
		if (tokens != null) {
			String userId = tokens.getUserid();
			if (StringUtils.isNotEmpty(userId)) {
				return advertorialService.confirmThumbUp(id, userId);
			}
		}
		return new ResultBean("001", ResultApiMsg.C_001);
	}

	/**
	 * @Title: cancelThumbUp
	 * @Description: 取消点赞
	 * @param id
	 * @param userId
	 * @return ResultBean
	 * @throws:
	 * @time: 2018年6月29日 下午4:47:32
	 */
	@SystemControllerLog(method = "cancelThumbUp", logtype = ConstantIF.LOG_TYPE_1, description = "取消点赞")
	@ApiOperation(value = "取消点赞", httpMethod = "POST", notes = "取消点赞", response = ResultBean.class)
	@RequestMapping(value = "/cancelThumbUp", method = RequestMethod.POST)
	@ResponseBody
	public ResultBean cancelThumbUp(@ApiParam(value = "文章id") @RequestParam(value = "id") String id,
			@ApiParam(value = "token值") @RequestParam(value = ConstantIF.ACCESSTOKEN) String accesstoken) {

		if (StringUtils.isEmpty(id)) {
			return new ResultBean("501", ResultBgMsg.C_501);
		}
		TokenModel tokens = redisTokenService.getToken(accesstoken);
		if (tokens != null) {
			String userId = tokens.getUserid();
			if (StringUtils.isNotEmpty(userId)) {
				return advertorialService.cancelThumbUp(id, userId);
			}
		}
		return new ResultBean("001", ResultApiMsg.C_001);
	}

}
