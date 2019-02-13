package dianfan.controller.goods;

import java.util.List;

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
import dianfan.entities.goods.GoodsLabels;
import dianfan.models.ResultBean;
import dianfan.models.TokenModel;
import dianfan.service.goods.LabelService;
import dianfan.service.impl.RedisTokenService;

/**
 * @ClassName GoodsLabelController
 * @Description 商品标签管理
 * @author cjy
 * @date 2018年7月17日 上午11:06:01
 */
@Scope(WebApplicationContext.SCOPE_REQUEST)
@Controller
@RequestMapping(value = "/goods/label")
public class GoodsLabelController {
	@Autowired
	private LabelService labelService;
	@Autowired
	private RedisTokenService redisTokenService;
	
	/**
	 * @Title: findGoodsLabelList
	 * @Description: 获取商品标签列表
	 * @return
	 * @throws:
	 * @time: 2018年7月17日 上午11:07:49
	 */
	@SystemControllerLog(method = "findGoodsLabelList", logtype = ConstantIF.LOG_TYPE_2, description = "获取商品标签列表")
	@ApiOperation(value = "获取商品标签列表", httpMethod = "GET", notes = "获取商品标签列表", response = ResultBean.class)
	@RequestMapping(value = "/labelList", method = RequestMethod.GET)
	public @ResponseBody ResultBean findGoodsLabelList() {
		List<GoodsLabels> labelList = labelService.findGoodsLabelList();
		return new ResultBean(labelList);
	}
	
	/**
	 * @Title: addGoodsLabel
	 * @Description: 新增商品标签
	 * @param accesstoken
	 * @param classifyParentid
	 * @param classifyName
	 * @param classifyNameEn
	 * @param sort
	 * @return
	 * @throws:
	 * @time: 2018年7月17日 上午11:25:27
	 */
	@SystemControllerLog(method = "addGoodsLabel", logtype = ConstantIF.LOG_TYPE_2, description = "新增商品标签")
	@ApiOperation(value = "新增商品标签", httpMethod = "POST", notes = "新增商品标签", response = ResultBean.class)
	@RequestMapping(value = "/addGoodsLabel", method = RequestMethod.POST)
	public @ResponseBody ResultBean addGoodsLabel(
			@ApiParam(value="accesstoken") @RequestParam(value=ConstantIF.ACCESSTOKEN) String accesstoken,
			@ApiParam(value="商品标签名称") @RequestParam(value="labelName") String labelName) {
		
		if(labelName == null || StringUtils.isEmpty(labelName.trim())) {
			return new ResultBean("3000", "商品标签名称" + ResultBgMsg.C_3000);
		}else {
			labelName = labelName.trim();
		}
		
		TokenModel token = redisTokenService.getToken(accesstoken);
		
		return labelService.addGoodsLabel(labelName, token.getUserid());
	}
	
	/**
	 * @Title: editGoodsLabel
	 * @Description: 商品标签修改
	 * @param accesstoken
	 * @param labelid
	 * @param labelName
	 * @return
	 * @throws:
	 * @time: 2018年7月17日 下午12:13:33
	 */
	@SystemControllerLog(method = "editGoodsLabel", logtype = ConstantIF.LOG_TYPE_2, description = "商品标签修改")
	@ApiOperation(value = "商品标签修改", httpMethod = "POST", notes = "商品标签修改", response = ResultBean.class)
	@RequestMapping(value = "/editGoodsLabel", method = RequestMethod.POST)
	public @ResponseBody ResultBean editGoodsLabel(
			@ApiParam(value="accesstoken") @RequestParam(value=ConstantIF.ACCESSTOKEN) String accesstoken,
			@ApiParam(value="商品标签id") @RequestParam(value="labelid") String labelid,
			@ApiParam(value="商品标签名称") @RequestParam(value="labelName") String labelName) {
		
		if(labelName == null || StringUtils.isEmpty(labelName.trim())) {
			return new ResultBean("3000", "商品标签名称" + ResultBgMsg.C_3000);
		}else {
			labelName = labelName.trim();
		}
		
		TokenModel token = redisTokenService.getToken(accesstoken);
		
		return labelService.editGoodsLabel(labelid, labelName, token.getUserid());
	}
	
	/**
	 * @Title: delGoodsLabel
	 * @Description: 商品标签删除
	 * @param accesstoken
	 * @param labelid
	 * @return
	 * @throws:
	 * @time: 2018年7月17日 下午1:05:25
	 */
	@SystemControllerLog(method = "delGoodsLabel", logtype = ConstantIF.LOG_TYPE_2, description = "商品标签删除")
	@ApiOperation(value = "商品标签删除", httpMethod = "POST", notes = "商品标签删除", response = ResultBean.class)
	@RequestMapping(value = "/delGoodsLabel", method = RequestMethod.POST)
	public @ResponseBody ResultBean delGoodsLabel(
			@ApiParam(value="accesstoken") @RequestParam(value=ConstantIF.ACCESSTOKEN) String accesstoken,
			@ApiParam(value="商品标签id列表（id之间使用英文','分隔）") @RequestParam(value="labelids") String labelids) {
		TokenModel token = redisTokenService.getToken(accesstoken);
		if(labelids == null || StringUtils.isEmpty(labelids.trim())) {
			return new ResultBean("501", ResultBgMsg.C_501);
		}
		return labelService.delGoodsLabel(labelids.split(","), token.getUserid());
	}
}
