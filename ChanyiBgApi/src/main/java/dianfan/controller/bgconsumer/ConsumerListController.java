/**  
* @Title: ConsumerListController.java
* @Package dianfan.controller.bgconsumer
* @Description: TODO
* @author yl
* @date 2018年7月20日 下午2:49:18
* @version V1.0  
*/
package dianfan.controller.bgconsumer;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

import dianfan.annotations.SystemControllerLog;
import dianfan.constant.ConstantIF;
import dianfan.constant.ResultBgMsg;
import dianfan.models.ResultBean;
import dianfan.models.TokenModel;
import dianfan.service.impl.RedisTokenService;
import dianfan.service.our.ConsumerApplyService;

/**
 * @ClassName ConsumerListController
 * @Description
 * @author yl
 * @date 2018年7月20日 下午2:49:18
 */
@Scope("request")
@Controller
@RequestMapping(value = "/bgconsumer")
public class ConsumerListController {

	/**
	 * 注入： #RedisTokenService
	 */
	@Autowired
	private RedisTokenService redisTokenService;
	@Autowired
	private ConsumerApplyService consumerApplyService;

	@SystemControllerLog(method = "findBgConsumerList", logtype = ConstantIF.LOG_TYPE_1, description = "获取消费商列表")
	@ApiOperation(value = "findBgConsumerList", httpMethod = "GET", notes = "获取消费商列表", response = ResultBean.class)
	@RequestMapping(value = "/findBgConsumerList", method = RequestMethod.GET)
	public @ResponseBody ResultBean findBgConsumerList(
			@ApiParam(value = "token值") @RequestParam(value = ConstantIF.ACCESSTOKEN) String accesstoken,
			@ApiParam(value = "申请状态 01通过 02-未通过03待审核") @RequestParam(value = "status", required = false) String status,
			@ApiParam(value = "用户昵称") @RequestParam(value = "nickName", required = false) String nickName,
			@ApiParam(value = "Start(创建时间)") @RequestParam(value = "createTimeStart", required = false) String createTimeStart,
			@ApiParam(value = "End(创建时间)") @RequestParam(value = "createTimeEnd", required = false) String createTimeEnd,
			@ApiParam(value = "请求页") @RequestParam(value = ConstantIF.PAGE, defaultValue = ConstantIF.PAGE_START_STR) int page,
			@ApiParam(value = "每页条数") @RequestParam(value = ConstantIF.PAGE_SIZE, defaultValue = ConstantIF.PAGE_OFFSET_STR) int pageSize) {
		ResultBean result = new ResultBean();
		TokenModel tokens = redisTokenService.getToken(accesstoken);
		if (tokens != null) {
			// 创建返回数据
			result = consumerApplyService.findConsumerList(status, nickName, createTimeStart, createTimeEnd, page,
					pageSize);
		} else {
			return new ResultBean("001", ResultBgMsg.C_001);
		}
		// 4.成功
		return result;
	}

	/**
	 * @Title: updateConsumerApply
	 * @Description: 消费商申请审批
	 * @param accesstoken
	 * @param consumerid
	 * @param status
	 * @param fReason
	 * @return
	 * @throws:
	 * @time: 2018年7月23日 下午4:24:46
	 */
	@SystemControllerLog(method = "updateConsumerApply", logtype = ConstantIF.LOG_TYPE_1, description = "消费商申请审批")
	@ApiOperation(value = "消费商申请审批", httpMethod = "POST", notes = "消费商申请审批", response = ResultBean.class)
	@RequestMapping(value = "/updateConsumerApply", method = RequestMethod.POST)
	@ResponseBody
	public ResultBean updateConsumerApply(
			@ApiParam(value = "token值") @RequestParam(value = ConstantIF.ACCESSTOKEN, required = true) String accesstoken,
			@ApiParam(value = "消费商id") @RequestParam(value = "consumerid", required = true) String consumerid,
			@ApiParam(value = "申请人id") @RequestParam(value = "applyUserId", required = true) String applyUserId,
			@ApiParam(value = "申请状态 01通过 02-未通过03待审核") @RequestParam(value = "status", required = true) String status,
			@ApiParam(value = "失败原因") @RequestParam(value = "fReason", required = false) String fReason) {
		/*
		 * 此处做值判断
		 */
		TokenModel tokens = redisTokenService.getToken(accesstoken);
		if (tokens != null) {
			String userid = tokens.getUserid();
			if (StringUtils.isNotEmpty(userid)) {
				// 此处调用业务逻辑层
				consumerApplyService.updateConsumerApply(userid,consumerid,applyUserId, status,fReason, ConstantIF.ROLE_DISTINGUISH07);
				return new ResultBean();
			}
		}
		return new ResultBean("001", ResultBgMsg.C_001);
	}

}
