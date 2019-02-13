/**  
* @Title: CityManagerApplyController.java
* @Package dianfan.controller.our
* @Description: TODO
* @author yl
* @date 2018年6月29日 下午2:50:59
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
import dianfan.service.our.CityManagerApplyService;
import dianfan.service.our.PersonalInfoService;
import dianfan.util.RegexUtils;

/**
 * @ClassName CityManagerApplyController
 * @Description 申请成为运营服务商
 * @author yl
 * @date 2018年6月29日 下午2:50:59
 */
@Scope(WebApplicationContext.SCOPE_REQUEST)
@Controller
@RequestMapping(value = "/cmanager")
public class CityManagerApplyController {

	/**
	 * 注入 redisTokenService personalInfoService
	 */
	@Autowired
	private CityManagerApplyService cityManagerApplyService;
	@Autowired
	private RedisTokenService redisTokenService;
	@Autowired
	private PersonalInfoService personalInfoService;

	@SystemControllerLog(method = "cityManagerApply", logtype = ConstantIF.LOG_TYPE_1, description = "申请成为城市经理")
	@ApiOperation(value = "申请成为城市经理", httpMethod = "POST", notes = "申请成为城市经理", response = ResultBean.class)
	@RequestMapping(value = "/cityManagerApply", method = RequestMethod.POST)
	@ResponseBody
	public ResultBean cityManagerApply(
			@ApiParam(value = "token值") @RequestParam(value = ConstantIF.ACCESSTOKEN, required = true) String accesstoken,
			@ApiParam(value = "手机号") @RequestParam(value = "applyphonenum", required = true) String applyphonenum,
			@ApiParam(value = "申请人姓名") @RequestParam(value = "applyname", required = true) String applyname,
			@ApiParam(value = "姓名") @RequestParam(value = "realname", required = true) String realname,
			@ApiParam(value = "身份证号码") @RequestParam(value = "idcardno", required = true) String idcardno,
			@ApiParam(value = "身份证正面") @RequestParam(value = "idcardfont", required = true) String idcardfont,
			@ApiParam(value = "身份证反面") @RequestParam(value = "idcardback", required = true) String idcardback,
			@ApiParam(value = "身份证有效期") @RequestParam(value = "idcardvaliddate", required = true) String idcardvaliddate,
			@ApiParam(value = "手持身份证照片") @RequestParam(value = "handleidcard", required = true) String handleidcard,
			@ApiParam(value = "城市code") @RequestParam(value = "cityCode", required = true) String cityCode) {

		/**
		 * 验证手机格式
		 */
		if (!RegexUtils.phoneRegex(applyphonenum)) {
			return new ResultBean("002", ResultApiMsg.C_002);
		}
		/**
		 * 验证身份证格式
		 */
		if (!RegexUtils.isIDCard(idcardno)) {
			return new ResultBean("2009", ResultApiMsg.C_2009);
		}
		// 限制真实姓名位数
		int realnamelen = realname.length();
		if (realnamelen > 64) {
			return new ResultBean("2023", ResultApiMsg.C_2023);
		}
		// 限制申请人姓名位数
		int applynames = applyname.length();
		if (applynames > 25) {
			return new ResultBean("C_2038", ResultApiMsg.C_2038);
		}
		TokenModel tokens = redisTokenService.getToken(accesstoken);
		if (tokens != null) {
			// 如果用户存在则不进数据库
			String userid = tokens.getUserid();
			if (StringUtils.isNotEmpty(userid)) {
				int cStatus = cityManagerApplyService.getCityManagerApply(userid);
				// int coopereByPhoneStatus =
				// CityManagerApplyService.getCooperationApplyByPhone(applyphonenum);
				if (cStatus > 0) {
					cityManagerApplyService.updateCityManagerApply(userid, applyphonenum, applyname, realname, idcardno,
							idcardfont, idcardback, idcardvaliddate, handleidcard, cityCode);
					return new ResultBean();
				}
				// 此处调用业务逻辑层
				UserInfoModel uim = personalInfoService.getUserInfo(userid);
				if (uim != null) {
					// 此处调用业务逻辑层
					ResultBean rb = cityManagerApplyService.addCityManagerApply(userid, applyphonenum, applyname,
							realname, idcardno, idcardfont, idcardback, idcardvaliddate, handleidcard,
							ConstantIF.APPLY_STATUS3, cityCode);
					return rb;
				}
			}
		}
		return new ResultBean("001", ResultApiMsg.C_001);
	}

}