/**  
* @Title: SupplierApplyController.java
* @Package dianfan.controller.our
* @Description: TODO
* @author yl
* @date 2018年6月29日 下午5:31:12
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
import dianfan.service.our.PersonalInfoService;
import dianfan.service.our.SupplierApplyService;
import dianfan.util.RegexUtils;

/**
 * @ClassName SupplierApplyController
 * @Description 申请成为原材料供应商
 * @author yl
 * @date 2018年6月29日 下午5:31:12
 */
@Scope(WebApplicationContext.SCOPE_REQUEST)
@Controller
@RequestMapping(value = "/supplier")
public class SupplierApplyController {
	/**
	 * 注入 redisTokenService personalInfoService
	 */
	@Autowired
	private SupplierApplyService supplierApplyService;
	@Autowired
	private RedisTokenService redisTokenService;
	@Autowired
	private PersonalInfoService personalInfoService;

	@SystemControllerLog(method = "supplierApply", logtype = ConstantIF.LOG_TYPE_1, description = "申请成为原材料供应商")
	@ApiOperation(value = "申请成为原材料供应商", httpMethod = "POST", notes = "申请成为原材料供应商", response = ResultBean.class)
	@RequestMapping(value = "/supplierApply", method = RequestMethod.POST)
	@ResponseBody
	public ResultBean supplierApply(
			@ApiParam(value = "token值") @RequestParam(value = ConstantIF.ACCESSTOKEN, required = true) String accesstoken,
			@ApiParam(value = "公司名称") @RequestParam(value = "companyname", required = true) String companyname,
			@ApiParam(value = "注册资金") @RequestParam(value = "registeredcapitalmoney", required = true) String registeredcapitalmoney,
			@ApiParam(value = "供应品类") @RequestParam(value = "supplycategory", required = true) String supplycategory,
			@ApiParam(value = "合作案例") @RequestParam(value = "cooperationcase", required = true) String cooperationcase,
			@ApiParam(value = "联系人") @RequestParam(value = "contacts", required = true) String contacts,
			@ApiParam(value = "联系电话") @RequestParam(value = "contactsphone", required = true) String contactsphone,
			@ApiParam(value = "电子邮箱") @RequestParam(value = "email", required = true) String email) {
		/**
		 * 验证手机格式
		 */
		if (!RegexUtils.phoneRegex(contactsphone)) {
			return new ResultBean("002", ResultApiMsg.C_002);
		}
		/**
		 * 验证邮箱格式
		 */
		if (!RegexUtils.isEmail(email)) {
			return new ResultBean("2013", ResultApiMsg.C_2013);
		}
		
		//公司名称限制位数
		int cnlen = companyname.length();
		if (cnlen>25) {
			return new ResultBean("2027",ResultApiMsg.C_2027);
		}
		
		//注册资金限制位数
		int rcmlen = registeredcapitalmoney.length();
		if (rcmlen>25) {
			return new ResultBean("2028",ResultApiMsg.C_2028);
		}
		
		//供应品类限制位数
		int sclen = supplycategory.length();
		if (sclen>64) {
			return new ResultBean("2029",ResultApiMsg.C_2029);
		}
		
		//合作案例限制位数
		int cclen = cooperationcase.length();
		if (cclen>110) {
			return new ResultBean("2030",ResultApiMsg.C_2030);
		}
		
		//联系人限制位数
		int contactslen = contacts.length();
		if (contactslen>10) {
			return new ResultBean("2031",ResultApiMsg.C_2031);
		}
		
		TokenModel tokens = redisTokenService.getToken(accesstoken);
		if (tokens != null) {
			String userid = tokens.getUserid();
			if (StringUtils.isNotEmpty(userid)) {
				int supplierApplyStatus = supplierApplyService.getSupplierApply(userid);
				int supplierApplyByPhoneStatus= supplierApplyService.getSupplierApplyByPhone(contactsphone);
				if ((supplierApplyStatus>0) || (supplierApplyByPhoneStatus>0)) {
					supplierApplyService.updateSupplierApply(userid, companyname, registeredcapitalmoney, supplycategory, cooperationcase, contacts, contactsphone, email);
					return new ResultBean();
				}
				// 此处调用业务逻辑层
				UserInfoModel uim = personalInfoService.getUserInfo(userid);
				if (uim != null) {
					// 此处调用业务逻辑层
					supplierApplyService.addSupplierApply(userid, companyname, registeredcapitalmoney, supplycategory, cooperationcase, contacts, contactsphone, email,ConstantIF.APPLY_STATUS3);
					return new ResultBean();
				} 
			}
		}
		return new ResultBean("001", ResultApiMsg.C_001);
	}

}