/**  
* @Title: ExperienceListController.java
* @Package dianfan.controller.bgexpera
* @Description: TODO
* @author yl
* @date 2018年7月20日 下午2:57:21
* @version V1.0  
*/ 
package dianfan.controller.bgexpera;

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
import dianfan.service.our.ExperApplyService;

/** @ClassName ExperienceListController
 * @Description 
 * @author yl
 * @date 2018年7月20日 下午2:57:21
 */
@Scope("request")
@Controller
@RequestMapping(value = "/bgexper")
public class ExperienceListController {
	
	/**
	 * 注入： #RedisTokenService
	 */
	@Autowired
	private RedisTokenService redisTokenService;
	@Autowired
	private ExperApplyService experApplyService;
	
	@SystemControllerLog(method = "findBgexperApplyList", logtype = ConstantIF.LOG_TYPE_1, description = "获取体验店列表")
	@ApiOperation(value = "findBgexperApplyList", httpMethod = "GET", notes = "获取体验店列表", response = ResultBean.class)
	@RequestMapping(value = "/findBgexperApplyList", method = RequestMethod.GET)
	public @ResponseBody ResultBean findBgexperApplyList(
			@ApiParam(value = "token值") @RequestParam(value = ConstantIF.ACCESSTOKEN) String accesstoken,
			@ApiParam(value = "申请状态 01通过 02-未通过03待审核") @RequestParam(value = "status", required = false) String status,
			@ApiParam(value = "申请人姓名") @RequestParam(value = "applyname", required = false) String applyname,
			@ApiParam(value = "城市code") @RequestParam(value = "citycode", required = false) String citycode,
			@ApiParam(value = "手机号") @RequestParam(value = "applyphonenum", required = false) String applyphonenum,
			@ApiParam(value = "开始面积") @RequestParam(value = "areastart", required = false) String areastart,
			@ApiParam(value = "结束面积") @RequestParam(value = "areaend", required = false) String areaend,
			@ApiParam(value = "Start(创建时间)") @RequestParam(value = "createTimeStart", required = false) String createTimeStart,
			@ApiParam(value = "End(创建时间)") @RequestParam(value = "createTimeEnd", required = false) String createTimeEnd,
			@ApiParam(value = "申请店主营业务") @RequestParam(value = "applyCurrentBusiness", required = false) String applyCurrentBusiness,
			@ApiParam(value="请求页") @RequestParam(value=ConstantIF.PAGE, defaultValue=ConstantIF.PAGE_START_STR) int page,
			@ApiParam(value="每页条数") @RequestParam(value=ConstantIF.PAGE_SIZE, defaultValue=ConstantIF.PAGE_OFFSET_STR) int pageSize
			) {
		   ResultBean result = new ResultBean();
				TokenModel tokens = redisTokenService.getToken(accesstoken);
				if (tokens != null) {
		            // 创建返回数据
				   result = experApplyService.findExperApplyList(status, applyname,citycode, applyphonenum,areastart,areaend,createTimeStart, createTimeEnd,applyCurrentBusiness, page, pageSize);
				}else {
					return new ResultBean("001",ResultBgMsg.C_001);
				}
				// 4.成功
				return result;
		 }
	
	/**
	 * @Title: updateExperienApply
	 * @Description: 
	 * @param accesstoken
	 * @param experienid
	 * @param applyUserId
	 * @param status
	 * @param fReason
	 * @return
	 * @throws:
	 * @time: 2018年7月23日 下午5:43:43
	 */
	@SystemControllerLog(method = "updateExperienApply", logtype = ConstantIF.LOG_TYPE_1, description = "体验店申请审批")
	@ApiOperation(value = "体验店申请审批", httpMethod = "POST", notes = "体验店申请审批", response = ResultBean.class)
	@RequestMapping(value = "/updateExperienApply", method = RequestMethod.POST)
	@ResponseBody
	public ResultBean updateExperienApply(
			@ApiParam(value = "token值") @RequestParam(value = ConstantIF.ACCESSTOKEN, required = true) String accesstoken,
			@ApiParam(value = "体验店id") @RequestParam(value = "experienid", required = true) String experienid,
			@ApiParam(value = "申请人姓名") @RequestParam(value = "applyname", required = false) String applyname,
			@ApiParam(value = "位置") @RequestParam(value = "applyaddr", required = false) String applyaddr,
			@ApiParam(value = "面积") @RequestParam(value = "area", required = false) String area,
			@ApiParam(value = "申请店主营业务") @RequestParam(value = "applycurrentbusiness", required = false) String applycurrentbusiness,
			@ApiParam(value = "联系方式") @RequestParam(value = "applyphonenum", required = false) String applyphonenum,
			@ApiParam(value = "门头照片") @RequestParam(value = "doorheadurl", required = false) String doorheadurl,
			@ApiParam(value = "店内照片一") @RequestParam(value = "innerurl00", required = false) String innerurl00,
			@ApiParam(value = "店内照片二") @RequestParam(value = "innerurl01", required = false) String innerurl01,
			@ApiParam(value = "营业执照") @RequestParam(value = "businesslicenceurl", required = false) String businesslicenceurl,
			@ApiParam(value = "区域code") @RequestParam(value = "areaCode", required = false) String areaCode,
			@ApiParam(value = "营业日起始") @RequestParam(value = "businessWeeklyStart", required = false) String businessWeeklyStart,
			@ApiParam(value = "营业日结束") @RequestParam(value = "businessWeeklyEnd", required = false) String businessWeeklyEnd,
			@ApiParam(value = "营业开始时间") @RequestParam(value = "businessTimeStart", required = false) String businessTimeStart,
			@ApiParam(value = "营业结束时间") @RequestParam(value = "businessTimeEnd", required = false) String businessTimeEnd,
			@ApiParam(value = "体验店分类") @RequestParam(value = "goodsClassifyId", required = false) String goodsClassifyId,
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
				ResultBean rb = experApplyService.updateBgExperApply(userid,experienid,applyname,applyUserId,status,fReason, ConstantIF.ROLE_DISTINGUISH05,applyaddr, area, applycurrentbusiness, applyphonenum,
						doorheadurl, innerurl00, innerurl01, businesslicenceurl, areaCode,
						businessWeeklyStart, businessWeeklyEnd, businessTimeStart, businessTimeEnd,
						goodsClassifyId);
				return rb;
			}
		}
		return new ResultBean("001", ResultBgMsg.C_001);
	}

}
