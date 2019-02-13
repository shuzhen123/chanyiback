/**  
* @Title: ExperApplyController.java
* @Package dianfan.controller.our
* @Description: TODO
* @author yl
* @date 2018年6月28日 下午7:09:49
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
import dianfan.service.our.ExperApplyService;
import dianfan.service.our.PersonalInfoService;
import dianfan.util.RegexUtils;

/**
 * @ClassName ExperApplyController
 * @Description 申请成为体验店
 * @author yl
 * @date 2018年6月28日 下午7:09:49
 */
@Scope(WebApplicationContext.SCOPE_REQUEST)
@Controller
@RequestMapping(value = "/exper")
public class ExperApplyController {

	/**
	 * 注入：#ExperApplyService
	 */
	@Autowired
	private ExperApplyService experApplyService;
	@Autowired
	private RedisTokenService redisTokenService;
	@Autowired
	private PersonalInfoService personalInfoService;

	@SystemControllerLog(method = "addExperApply", logtype = ConstantIF.LOG_TYPE_1, description = "申请成为体验店")
	@ApiOperation(value = "申请成为体验店", httpMethod = "POST", notes = "申请成为体验店", response = ResultBean.class)
	@RequestMapping(value = "/addExperApply", method = RequestMethod.POST)
	@ResponseBody
	public ResultBean addExperApply(
			@ApiParam(value = "token值") @RequestParam(value = ConstantIF.ACCESSTOKEN, required = true) String accesstoken,
			@ApiParam(value = "体验店名称") @RequestParam(value = "applyname", required = false) String applyname,
			@ApiParam(value = "位置") @RequestParam(value = "applyaddr", required = true) String applyaddr,
			@ApiParam(value = "面积") @RequestParam(value = "area", required = true) String area,
			@ApiParam(value = "申请店主营业务") @RequestParam(value = "applycurrentbusiness", required = true) String applycurrentbusiness,
			@ApiParam(value = "联系方式") @RequestParam(value = "applyphonenum", required = true) String applyphonenum,
			@ApiParam(value = "门头照片") @RequestParam(value = "doorheadurl", required = true) String doorheadurl,
			@ApiParam(value = "店内照片一") @RequestParam(value = "innerurl00", required = true) String innerurl00,
			@ApiParam(value = "店内照片二") @RequestParam(value = "innerurl01", required = true) String innerurl01,
			@ApiParam(value = "营业执照") @RequestParam(value = "businesslicenceurl", required = true) String businesslicenceurl,
			@ApiParam(value = "区域code") @RequestParam(value = "areaCode", required = false) String areaCode,
			@ApiParam(value = "营业日起始") @RequestParam(value = "businessWeeklyStart", required = false) String businessWeeklyStart,
			@ApiParam(value = "营业日结束") @RequestParam(value = "businessWeeklyEnd", required = false) String businessWeeklyEnd,
			@ApiParam(value = "营业开始时间") @RequestParam(value = "businessTimeStart", required = false) String businessTimeStart,
			@ApiParam(value = "营业结束时间") @RequestParam(value = "businessTimeEnd", required = false) String businessTimeEnd,
			@ApiParam(value = "体验店分类") @RequestParam(value = "goodsClassifyId", required = false) String goodsClassifyId) {

		/**
		 * 验证手机格式
		 */
		if (!RegexUtils.phoneRegex(applyphonenum)) {
			return new ResultBean("002", ResultApiMsg.C_002);
		}

		// 限制位数
		int addrlen = applyaddr.length();
		if (addrlen > 64) {
			return new ResultBean("2024", ResultApiMsg.C_2024);
		}
		int acblen = applycurrentbusiness.length();
		if (acblen > 75) {
			return new ResultBean("2032", ResultApiMsg.C_2032);
		}

		TokenModel tokens = redisTokenService.getToken(accesstoken);
		if (tokens != null) {
			String applyuserid = tokens.getUserid();
			if (StringUtils.isNotEmpty(applyuserid)) {
				int experApplyStatus = experApplyService.getExperApply(applyuserid);
				//int experApplyByPhoneStatus = experApplyService.getExperApplyByPhone(applyphonenum);
				if (experApplyStatus > 0) {
					ResultBean rb = experApplyService.updateExperApply(applyuserid,applyname, applyaddr, area, applycurrentbusiness,
							applyphonenum, doorheadurl, innerurl00, innerurl01, businesslicenceurl,areaCode,
							businessWeeklyStart, businessWeeklyEnd, businessTimeStart, businessTimeEnd,
							goodsClassifyId);
					return rb;
				}
				// 此处调用业务逻辑层
				UserInfoModel uim = personalInfoService.getUserInfo(applyuserid);
				if (uim != null) {
					// 此处调用业务逻辑层
					ResultBean rb = experApplyService.addExperApply(applyuserid,applyname, applyaddr, area, applycurrentbusiness, applyphonenum,
							doorheadurl, innerurl00, innerurl01, businesslicenceurl, ConstantIF.APPLY_STATUS3, areaCode,
							businessWeeklyStart, businessWeeklyEnd, businessTimeStart, businessTimeEnd,
							goodsClassifyId);
					return rb;
				}
			}
		}
		return new ResultBean("001", ResultApiMsg.C_001);
	}

}
