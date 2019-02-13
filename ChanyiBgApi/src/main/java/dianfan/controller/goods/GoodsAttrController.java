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
import dianfan.service.goods.GoodsSpecService;
import dianfan.service.impl.RedisTokenService;

/**
 * @ClassName GoodsAttrController
 * @Description 商品属性管理
 * @author cjy
 * @date 2018年7月17日 下午4:40:47
 */
@Scope(WebApplicationContext.SCOPE_REQUEST)
@Controller
@RequestMapping(value = "/goods/attr")
public class GoodsAttrController {
	@Autowired
	private GoodsSpecService goodsSpecService;
	@Autowired
	private RedisTokenService redisTokenService;
	
	/**
	 * @Title: findGoodsAttrList
	 * @Description: 获取商品属性列表
	 * @return
	 * @throws:
	 * @time: 2018年7月17日 下午4:45:52
	 */
	@SystemControllerLog(method = "findGoodsAttrList", logtype = ConstantIF.LOG_TYPE_2, description = "获取商品属性列表")
	@ApiOperation(value = "获取商品属性列表", httpMethod = "GET", notes = "获取商品属性列表", response = ResultBean.class)
	@RequestMapping(value = "/attrList", method = RequestMethod.GET)
	public @ResponseBody ResultBean findGoodsAttrList() {
		return goodsSpecService.findGoodsAttrList();
	}
	
	/**
	 * @Title: addGoodsAttr
	 * @Description: 新增商品属性
	 * @param accesstoken
	 * @param paramKey
	 * @param paramName
	 * @return
	 * @throws:
	 * @time: 2018年7月17日 下午5:18:34
	 */
	@SystemControllerLog(method = "addGoodsAttr", logtype = ConstantIF.LOG_TYPE_2, description = "新增商品属性")
	@ApiOperation(value = "新增商品属性", httpMethod = "POST", notes = "新增商品属性", response = ResultBean.class)
	@RequestMapping(value = "/addGoodsAttr", method = RequestMethod.POST)
	public @ResponseBody ResultBean addGoodsAttr(
			@ApiParam(value="accesstoken") @RequestParam(value=ConstantIF.ACCESSTOKEN) String accesstoken,
			@ApiParam(value="商品属性名") @RequestParam(value="key") String paramKey,
			@ApiParam(value="商品属性值") @RequestParam(value="name") String paramName) {
		if(paramKey == null || StringUtils.isEmpty(paramKey.trim())) {
			return new ResultBean("3000", "商品属性名" + ResultBgMsg.C_3000);
		}
		
		if(paramName == null || StringUtils.isEmpty(paramName.trim())) {
			return new ResultBean("3000", "商品属性值" + ResultBgMsg.C_3000);
		}
		
		TokenModel token = redisTokenService.getToken(accesstoken);
		return goodsSpecService.addGoodsAttr(paramKey.trim(), paramName.trim(), token.getUserid());
	}
	
	/**
	 * @Title: editGoodsAttr
	 * @Description: 修改商品属性
	 * @param accesstoken
	 * @param attrid
	 * @param paramName
	 * @return
	 * @throws:
	 * @time: 2018年7月17日 下午5:39:51
	 */
	@SystemControllerLog(method = "editGoodsAttr", logtype = ConstantIF.LOG_TYPE_2, description = "修改商品属性")
	@ApiOperation(value = "修改商品属性", httpMethod = "POST", notes = "修改商品属性", response = ResultBean.class)
	@RequestMapping(value = "/editGoodsAttr", method = RequestMethod.POST)
	public @ResponseBody ResultBean editGoodsAttr(
			@ApiParam(value="accesstoken") @RequestParam(value=ConstantIF.ACCESSTOKEN) String accesstoken,
			@ApiParam(value="商品属性id") @RequestParam(value="attrid") String attrid,
			@ApiParam(value="商品属性名") @RequestParam(value="key") String paramKey,
			@ApiParam(value="商品属性值") @RequestParam(value="name") String paramName) {
		if(paramName == null || StringUtils.isEmpty(paramName.trim())) {
			return new ResultBean("3000", "商品属性值" + ResultBgMsg.C_3000);
		}
		
		TokenModel token = redisTokenService.getToken(accesstoken);
		return goodsSpecService.editGoodsAttr(attrid, paramKey.trim(), paramName.trim(), token.getUserid());
	}
	
	/**
	 * @Title: delGoodsAttr
	 * @Description: 删除商品属性
	 * @param accesstoken
	 * @param attrid
	 * @return
	 * @throws:
	 * @time: 2018年7月17日 下午6:10:55
	 */
	@SystemControllerLog(method = "delGoodsAttr", logtype = ConstantIF.LOG_TYPE_2, description = "删除商品属性")
	@ApiOperation(value = "删除商品属性", httpMethod = "POST", notes = "删除商品属性", response = ResultBean.class)
	@RequestMapping(value = "/delGoodsAttr", method = RequestMethod.POST)
	public @ResponseBody ResultBean delGoodsAttr(
			@ApiParam(value="accesstoken") @RequestParam(value=ConstantIF.ACCESSTOKEN) String accesstoken,
			@ApiParam(value="商品属性id集合（多id之间使用英文','分隔）") @RequestParam(value="attrids") String attrids) {
		if(attrids == null || StringUtils.isEmpty(attrids.trim())) {
			return new ResultBean("501", ResultBgMsg.C_501);
		}
		TokenModel token = redisTokenService.getToken(accesstoken);
		return goodsSpecService.delGoodsAttr(attrids.split(","), token.getUserid());
	}
}
