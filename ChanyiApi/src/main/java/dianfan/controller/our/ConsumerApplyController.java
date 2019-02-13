/**  
* @Title: ConsumerApplyController.java
* @Package dianfan.controller.our
* @Description: TODO
* @author yl
* @date 2018年6月30日 下午1:15:30
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
import dianfan.dao.mapper.our.SettingSwitchsMapper;
import dianfan.entities.our.UserInfoModel;
import dianfan.models.ResultBean;
import dianfan.models.TokenModel;
import dianfan.service.impl.RedisTokenService;
import dianfan.service.our.ConsumerApplyService;
import dianfan.service.our.PersonalInfoService;

/** @ClassName ConsumerApplyController
 * @Description 申请成为消费商
 * @author yl
 * @date 2018年6月30日 下午1:15:30
 */
@Scope(WebApplicationContext.SCOPE_REQUEST)
@Controller
@RequestMapping(value = "/consumer")
public class ConsumerApplyController {
	/**
	 * 注入ConsumerApplyService redisTokenService personalInfoService
	 */
	@Autowired
	private ConsumerApplyService consumerApplyService;
	@Autowired
	private RedisTokenService redisTokenService;
	@Autowired
	private PersonalInfoService personalInfoService;
	@Autowired
	private SettingSwitchsMapper settingSwitchsMapper;
	
	@SystemControllerLog(method = "consumerApply", logtype = ConstantIF.LOG_TYPE_1, description = "申请成为消费商")
	@ApiOperation(value = "申请成为消费商", httpMethod = "POST", notes = "申请成为消费商", response = ResultBean.class)
	@RequestMapping(value = "/consumerApply", method = RequestMethod.POST)
	@ResponseBody
	public ResultBean consumerApply(@ApiParam(value = "token值") @RequestParam(value = ConstantIF.ACCESSTOKEN, required = true) String accesstoken) {
		/*
		 * 此处做值判断
		 */
		TokenModel tokens = redisTokenService.getToken(accesstoken);
		if (tokens !=null) {
			String userid = tokens.getUserid();
			if (StringUtils.isNotEmpty(userid)) {
				//如果用户已是消费商则不进数据库
				int consumerApplyStatus = consumerApplyService.getConsumerApply(userid);
				if (consumerApplyStatus>0) {
					return new ResultBean();
				}
				// 此处调用业务逻辑层
				UserInfoModel uim = personalInfoService.getUserInfo(userid);
				if (uim !=null) {
					Integer applyFlag = settingSwitchsMapper.getSettingSwitchs();
					if (ConstantIF.APPLY_FLAG.equals(applyFlag)) {
						// 此处调用业务逻辑层
						consumerApplyService.addConsumerApply(userid,ConstantIF.APPLY_STATUS1,ConstantIF.ROLE_DISTINGUISH07);
					}else {
						consumerApplyService.addConsumerApply(userid,ConstantIF.APPLY_STATUS3,ConstantIF.ROLE_DISTINGUISH07);
					}			
					return new ResultBean();
				}
			}
		}
		return new ResultBean("001",ResultApiMsg.C_001);
	}

}