/**  
* @Title: OperateServiceController.java
* @Package dianfan.controller.bgoperate
* @Description: TODO
* @author yl
* @date 2018年7月23日 上午10:28:00
* @version V1.0  
*/
package dianfan.controller.bgoperate;

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
import dianfan.service.our.CoopereApplyService;

/**
 * @ClassName OperateServiceController
 * @Description
 * @author yl
 * @date 2018年7月23日 上午10:28:00
 */
@Scope("request")
@Controller
@RequestMapping(value = "/bgoperate")
public class OperateServiceController {
	/**
	 * 注入： #RedisTokenService
	 */
	@Autowired
	private RedisTokenService redisTokenService;
	@Autowired
	private CoopereApplyService coopereApplyService;

	@SystemControllerLog(method = "findBgOperateServiceList", logtype = ConstantIF.LOG_TYPE_1, description = "获取运营服务商列表")
	@ApiOperation(value = "findBgOperateServiceList", httpMethod = "GET", notes = "获取运营服务商列表", response = ResultBean.class)
	@RequestMapping(value = "/findBgOperateServiceList", method = RequestMethod.GET)
	public @ResponseBody ResultBean findBgOperateServiceList(
			@ApiParam(value = "token值") @RequestParam(value = ConstantIF.ACCESSTOKEN) String accesstoken,
			@ApiParam(value = "申请状态 01通过 02-未通过03待审核") @RequestParam(value = "status", required = false) String status,
			@ApiParam(value = "申请人姓名") @RequestParam(value = "applyname", required = false) String applyname,
			@ApiParam(value = "真实姓名") @RequestParam(value = "realname", required = false) String realname,
			@ApiParam(value = "身份证号码") @RequestParam(value = "idcardno", required = false) String idcardno,
			@ApiParam(value = "Start(创建时间)") @RequestParam(value = "createTimeStart", required = false) String createTimeStart,
			@ApiParam(value = "End(创建时间)") @RequestParam(value = "createTimeEnd", required = false) String createTimeEnd,
			@ApiParam(value = "请求页") @RequestParam(value = ConstantIF.PAGE, defaultValue = ConstantIF.PAGE_START_STR) int page,
			@ApiParam(value = "每页条数") @RequestParam(value = ConstantIF.PAGE_SIZE, defaultValue = ConstantIF.PAGE_OFFSET_STR) int pageSize) {
		ResultBean result = new ResultBean();
		TokenModel tokens = redisTokenService.getToken(accesstoken);
		if (tokens != null) {
			// 创建返回数据
			result = coopereApplyService.findOperateServiceList(status, applyname, realname, idcardno, createTimeStart,
					createTimeEnd, page, pageSize);
		} else {
			return new ResultBean("001", ResultBgMsg.C_001);
		}
		// 4.成功
		return result;
	}

	@SystemControllerLog(method = "updateOperaServerApply", logtype = ConstantIF.LOG_TYPE_1, description = "运营服务商申请审批")
	@ApiOperation(value = "运营服务商申请审批", httpMethod = "POST", notes = "运营服务商申请审批", response = ResultBean.class)
	@RequestMapping(value = "/updateOperaServerApply", method = RequestMethod.POST)
	@ResponseBody
	public ResultBean updateOperaServerApply(
			@ApiParam(value = "token值") @RequestParam(value = ConstantIF.ACCESSTOKEN, required = true) String accesstoken,
			@ApiParam(value = "运营服务商id") @RequestParam(value = "operaserverid", required = true) String operaserverid,
			@ApiParam(value = "申请人id") @RequestParam(value = "applyUserId", required = true) String applyUserId,
			@ApiParam(value = "申请状态 01通过 02-未通过03待审核") @RequestParam(value = "status", required = true) String status,
			@ApiParam(value = "失败原因") @RequestParam(value = "fReason", required = false) String fReason,
			@ApiParam(value = "合同图片地址") @RequestParam(value = "contractUrl", required = false) String contractUrl) {
		/*
		 * 此处做值判断
		 */
		TokenModel tokens = redisTokenService.getToken(accesstoken);
		if (tokens != null) {
			String userid = tokens.getUserid();
			if (StringUtils.isNotEmpty(userid)) {
				// 此处调用业务逻辑层
				ResultBean rb = coopereApplyService.updateOperaServerApply(userid, operaserverid, applyUserId, status,
						fReason, ConstantIF.ROLE_DISTINGUISH02,contractUrl);
				return rb;
			}
		}
		return new ResultBean("001", ResultBgMsg.C_001);
	}

}
