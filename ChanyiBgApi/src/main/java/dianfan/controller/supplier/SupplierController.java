/**  
* @Title: SupplierController.java
* @Package dianfan.controller.supplier
* @Description: TODO
* @author yl
* @date 2018年7月23日 下午2:01:17
* @version V1.0  
*/ 
package dianfan.controller.supplier;

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
import dianfan.service.our.SupplierApplyService;

/** @ClassName SupplierController
 * @Description 
 * @author yl
 * @date 2018年7月23日 下午2:01:17
 */
@Scope("request")
@Controller
@RequestMapping(value = "/bgsupplier")
public class SupplierController {
	/**
	 * 注入： #RedisTokenService
	 */
	@Autowired
	private RedisTokenService redisTokenService;
	@Autowired
	private SupplierApplyService supplierApplyService;
	
	@SystemControllerLog(method = "findBgSupplierList", logtype = ConstantIF.LOG_TYPE_1, description = "获取运营服务商列表")
	@ApiOperation(value = "findBgSupplierList", httpMethod = "GET", notes = "获取运营服务商列表", response = ResultBean.class)
	@RequestMapping(value = "/findBgSupplierList", method = RequestMethod.GET)
	public @ResponseBody ResultBean findBgSupplierList(
			@ApiParam(value = "token值") @RequestParam(value = ConstantIF.ACCESSTOKEN) String accesstoken,
			@ApiParam(value = "申请状态 01通过 02-未通过03待审核") @RequestParam(value = "status", required = false) String status,
			@ApiParam(value = "联系人姓名") @RequestParam(value = "contacts", required = false) String contacts,
			@ApiParam(value = "公司名称") @RequestParam(value = "companyName", required = false) String companyName,
			/*@ApiParam(value = "注册资金") @RequestParam(value = "registeredCapitalMoney", required = false) String registeredCapitalMoney,*/
			@ApiParam(value = "供应品类") @RequestParam(value = "supplyCategory", required = false) String supplyCategory,
			@ApiParam(value = "Start(创建时间)") @RequestParam(value = "createTimeStart", required = false) String createTimeStart,
			@ApiParam(value = "End(创建时间)") @RequestParam(value = "createTimeEnd", required = false) String createTimeEnd,
			@ApiParam(value="请求页") @RequestParam(value=ConstantIF.PAGE, defaultValue=ConstantIF.PAGE_START_STR) int page,
			@ApiParam(value="每页条数") @RequestParam(value=ConstantIF.PAGE_SIZE, defaultValue=ConstantIF.PAGE_OFFSET_STR) int pageSize
			) {
		   ResultBean result = new ResultBean();
				TokenModel tokens = redisTokenService.getToken(accesstoken);
				if (tokens != null) {
		            // 创建返回数据
				   result = supplierApplyService.findSupplierList(status, contacts, companyName, supplyCategory, createTimeStart, createTimeEnd, page, pageSize);
				}else {
					return new ResultBean("001",ResultBgMsg.C_001);
				}
				// 4.成功
				return result;
		 }
	
	@SystemControllerLog(method = "updateSupplierApply", logtype = ConstantIF.LOG_TYPE_1, description = "供应商申请审批")
	@ApiOperation(value = "供应商申请审批", httpMethod = "POST", notes = "供应商申请审批", response = ResultBean.class)
	@RequestMapping(value = "/updateSupplierApply", method = RequestMethod.POST)
	@ResponseBody
	public ResultBean updateSupplierApply(
			@ApiParam(value = "token值") @RequestParam(value = ConstantIF.ACCESSTOKEN, required = true) String accesstoken,
			@ApiParam(value = "供应商id") @RequestParam(value = "supplierid", required = true) String supplierid,
			@ApiParam(value = "申请人id") @RequestParam(value = "applyUserId", required = true) String applyUserId,
			@ApiParam(value = "申请状态 01通过 02-未通过03待审核") @RequestParam(value = "applystatus", required = true) String applystatus,
			@ApiParam(value = "失败原因") @RequestParam(value = "fReason", required = false) String fReason) {
		/*
		 * 此处做值判断
		 */
		TokenModel tokens = redisTokenService.getToken(accesstoken);
		if (tokens != null) {
			String userid = tokens.getUserid();
			if (StringUtils.isNotEmpty(userid)) {
				// 此处调用业务逻辑层
				supplierApplyService.updateSupplierApplyStatus(userid, supplierid, applyUserId, applystatus, fReason);
				return new ResultBean();
			}
		}
		return new ResultBean("001", ResultBgMsg.C_001);
	}

}
