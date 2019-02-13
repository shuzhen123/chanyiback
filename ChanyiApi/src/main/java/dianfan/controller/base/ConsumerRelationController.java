package dianfan.controller.base;

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
import dianfan.models.ResultBean;
import dianfan.models.TokenModel;
import dianfan.service.base.ConsumerRelationService;
import dianfan.service.impl.RedisTokenService;

/**
 * @ClassName ConsumeRelationController
 * @Description 消费关系
 * @author cjy
 * @date 2018年7月3日 上午9:33:36
 */
@Scope(WebApplicationContext.SCOPE_REQUEST)
@Controller
@RequestMapping(value = "/consumer/relation")
public class ConsumerRelationController {
	/**
	 * 注入: #ConsumerRelationService
	 */
	@Autowired
	private ConsumerRelationService consumerRelationService;
	@Autowired
	private RedisTokenService redisTokenService;
	
	/**
	 * @Title: bind
	 * @Description: 绑定消费关系
	 * @param accesstoken accesstoken
	 * @param qr_num 二维码数据
	 * @return
	 * @throws:
	 * @time: 2018年7月3日 上午9:46:44
	 */
	@SystemControllerLog(method = "bind", logtype = ConstantIF.LOG_TYPE_1, description = "绑定消费关系")
	@ApiOperation(value = "绑定消费关系", httpMethod = "POST", notes = "绑定消费关系", response = ResultBean.class)
	@RequestMapping(value = "/bind", method = RequestMethod.POST)
	@ResponseBody
	public ResultBean bind(
			@ApiParam(value = "token值") @RequestParam(value = ConstantIF.ACCESSTOKEN) String accesstoken,
			@ApiParam(value = "二维码值") @RequestParam(value = "qr_num") String qr_num) {
		TokenModel tokens = redisTokenService.getToken(accesstoken);
		try {
			consumerRelationService.bindConsumerRelation(tokens.getUserid(), qr_num);
		} catch (Exception e) {
		}
		return new ResultBean();
	}
	
	/**
	 * @Title: bind1
	 * @Description: 绑定上下级关系
	 * @param accesstoken accesstoken
	 * @param qr_num 二维码数据
	 * @return
	 * @throws:
	 * @time: 2018年7月3日 上午9:46:44
	 */
	@SystemControllerLog(method = "bind", logtype = ConstantIF.LOG_TYPE_1, description = "绑定上下级关系")
	@ApiOperation(value = "绑定上下级关系", httpMethod = "POST", notes = "绑定上下级关系", response = ResultBean.class)
	@RequestMapping(value = "/bind1", method = RequestMethod.POST)
	@ResponseBody
	public ResultBean bind1(
			@ApiParam(value = "token值") @RequestParam(value = ConstantIF.ACCESSTOKEN) String accesstoken,
			@ApiParam(value = "二维码值") @RequestParam(value = "qr_num") String qr_num) {
		TokenModel tokens = redisTokenService.getToken(accesstoken);
		return consumerRelationService.bindUpLowRelation(tokens.getUserid(), qr_num);
	}
}
