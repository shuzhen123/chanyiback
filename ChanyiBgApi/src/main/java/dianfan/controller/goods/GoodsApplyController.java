package dianfan.controller.goods;

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
import dianfan.constant.ResultBgMsg;
import dianfan.models.ResultBean;
import dianfan.models.TokenModel;
import dianfan.service.goods.GoodsApplyService;
import dianfan.service.impl.RedisTokenService;

/**
 * @ClassName GoodsApplyController
 * @Description 商品申请管理
 * @author cjy
 * @date 2018年7月23日 下午2:58:17
 */
@Scope(WebApplicationContext.SCOPE_REQUEST)
@Controller
@RequestMapping(value = "/goods/apply")
public class GoodsApplyController {
	@Autowired
	private GoodsApplyService goodsApplyService;
	@Autowired
	private RedisTokenService redisTokenService;
	
	/**
	 * @Title: goodsApplyList
	 * @Description: 商品申请列表
	 * @return
	 * @throws:
	 * @time: 2018年7月23日 下午4:59:33
	 * @author cjy
	 */
	@SystemControllerLog(method = "addGoodsAttr", logtype = ConstantIF.LOG_TYPE_2, description = "商品申请列表")
	@ApiOperation(value = "商品申请列表", httpMethod = "POST", notes = "商品申请列表", response = ResultBean.class)
	@RequestMapping(value = "/goodsApplyList", method = RequestMethod.POST)
	public @ResponseBody ResultBean goodsApplyList(
			@ApiParam(value="请求页") @RequestParam(value=ConstantIF.PAGE, defaultValue=ConstantIF.PAGE_START_STR) int page,
			@ApiParam(value="每页条数") @RequestParam(value=ConstantIF.PAGE_SIZE, defaultValue=ConstantIF.PAGE_OFFSET_STR) int pageSize,
			
			@ApiParam(value="申请状态（00:待审核 01：申请通过 02：未通过）") @RequestParam(value="applyStatus", required=false) String applyStatus,
			@ApiParam(value="起始时间筛选") @RequestParam(value="startTime", required=false) String startTime,
			@ApiParam(value="截止时间筛选") @RequestParam(value="endTime", required=false) String endTime
			) {
		if(applyStatus == null || StringUtils.isEmpty(applyStatus.trim()))
			applyStatus = null;
		else
			applyStatus = applyStatus.trim();
		
		if(startTime == null || StringUtils.isEmpty(startTime.trim()))
			startTime = null;
		else 
			startTime = startTime.trim();
		
		if(endTime == null || StringUtils.isEmpty(endTime.trim()))
			endTime = null;
		else
			endTime = endTime.trim();
		
		return goodsApplyService.findGoodsApplyList(page, pageSize, applyStatus, startTime, endTime);
	}
	
	/**
	 * @Title: goodsApplyApprove
	 * @Description: 商品申请审批
	 * @return
	 * @throws:
	 * @time: 2018年7月23日 下午5:24:03
	 * @author cjy
	 */
	@SystemControllerLog(method = "goodsApplyApprove", logtype = ConstantIF.LOG_TYPE_2, description = "商品申请审批")
	@ApiOperation(value = "商品申请审批", httpMethod = "POST", notes = "商品申请审批", response = ResultBean.class)
	@RequestMapping(value = "/goodsApplyApprove", method = RequestMethod.POST)
	public @ResponseBody ResultBean goodsApplyApprove(
			@ApiParam(value="accesstoken") @RequestParam(value=ConstantIF.ACCESSTOKEN) String accesstoken,
			@ApiParam(value="申请id") @RequestParam(value="applyid") String applyid,
			@ApiParam(value="审核状态（01：申请通过 02：未通过）") @RequestParam(value="applyStatus") String applyStatus,
			@ApiParam(value="备注") @RequestParam(value="remark", required=false) String remark) {
		if(StringUtils.equals(applyStatus, "02") && StringUtils.isEmpty(remark)) {
			return new ResultBean("3005", ResultBgMsg.C_3005);
		}
		TokenModel token = redisTokenService.getToken(accesstoken);
		return goodsApplyService.goodsApplyApprove(applyid, applyStatus, remark, token.getUserid());
	}
}
