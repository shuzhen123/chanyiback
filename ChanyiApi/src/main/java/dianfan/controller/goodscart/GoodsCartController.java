/**  
* @Title: GoodsCartController.java
* @Package dianfan.controller.goodscart
* @Description: TODO
* @author yl
* @date 2018年7月3日 下午3:47:53
* @version V1.0  
*/
package dianfan.controller.goodscart;

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
import dianfan.service.goodscart.GoodsCartService;
import dianfan.service.impl.RedisTokenService;
import dianfan.service.our.PersonalInfoService;

/**
 * @ClassName GoodsCartController
 * @Description 购物车
 * @author yl
 * @date 2018年7月3日 下午3:47:53
 */
@Scope(WebApplicationContext.SCOPE_REQUEST)
@Controller
@RequestMapping(value = "/gcart")
public class GoodsCartController {

	/**
	 * 注入：#GoodsCartService RedisTokenService PersonalInfoService
	 */
	@Autowired
	private GoodsCartService goodsCartService;
	@Autowired
	private RedisTokenService redisTokenService;
	@Autowired
	private PersonalInfoService personalInfoService;

	@SystemControllerLog(method = "addGoodsCart", logtype = ConstantIF.LOG_TYPE_1, description = "添加购物车")
	@ApiOperation(value = "添加购物车", httpMethod = "POST", notes = "添加购物车", response = ResultBean.class)
	@RequestMapping(value = "/addGoodsCart", method = RequestMethod.POST)
	@ResponseBody
	public ResultBean addGoodsCart(
			@ApiParam(value = "token值") @RequestParam(value = ConstantIF.ACCESSTOKEN) String accesstoken,
			@ApiParam(value = "商品id") @RequestParam(value = "goodsid") String goodsid,
			@ApiParam(value = "商品价格表id") @RequestParam(value = "goodspriceid", required = false) String goodspriceid,
			@ApiParam(value = "数量") @RequestParam(value = "num") Integer num) {
		/*
		 * 此处做值判断
		 */
		TokenModel tokens = redisTokenService.getToken(accesstoken);
		if (tokens != null) {
			String userid = tokens.getUserid();
			if (StringUtils.isNotEmpty(userid)) {
				// 此处调用业务逻辑层
				UserInfoModel uim = personalInfoService.getUserInfo(userid);
				//判断购物车单件商品的数量不能超过999
				if (uim != null) {
					if (num>999) {
						return new ResultBean("2019",ResultApiMsg.C_2019);
					}
					goodsCartService.addShoppingCart(userid, goodsid, goodspriceid, num);
					return new ResultBean();
				} 
			}
		}
		return new ResultBean("001", ResultApiMsg.C_001);

	}

	@SystemControllerLog(method = "delGoodsCart", logtype = ConstantIF.LOG_TYPE_1, description = "删除购物车")
	@ApiOperation(value = "删除购物车", httpMethod = "POST", notes = "删除购物车", response = ResultBean.class)
	@RequestMapping(value = "/delGoodsCart", method = RequestMethod.POST)
	@ResponseBody
	public ResultBean delGoodsCart(
			@ApiParam(value = "token值") @RequestParam(value = ConstantIF.ACCESSTOKEN) String accesstoken,
			@ApiParam(value = "购物车id") @RequestParam(value = "goodscartid") String goodscartid) {
		/*
		 * 此处做值判断
		 */
		TokenModel tokens = redisTokenService.getToken(accesstoken);
		if (tokens != null) {
			String userid = tokens.getUserid();
			if (StringUtils.isNotEmpty(userid)) {
				// 此处调用业务逻辑层
				UserInfoModel uim = personalInfoService.getUserInfo(userid);
				if (uim != null) {
					goodsCartService.delShoppingCart(userid, goodscartid);
					return new ResultBean();
				} else {
					return new ResultBean("2001", ResultApiMsg.C_2001);
				}
			}
		}
		return new ResultBean("001", ResultApiMsg.C_001);

	}

	@SystemControllerLog(method = "updateGoodsNum", logtype = ConstantIF.LOG_TYPE_1, description = "修改购物车数量")
	@ApiOperation(value = "修改购物车数量", httpMethod = "POST", notes = "修改购物车数量", response = ResultBean.class)
	@RequestMapping(value = "/updateGoodsNum", method = RequestMethod.POST)
	@ResponseBody
	public ResultBean updateGoodsNum(
			@ApiParam(value = "token值") @RequestParam(value = ConstantIF.ACCESSTOKEN) String accesstoken,
			@ApiParam(value = "购物车id") @RequestParam(value = "goodscartid") String goodscartid,
			@ApiParam(value = "商品数量") @RequestParam(value = "num") String num) {
		/*
		 * 此处做值判断
		 */
		TokenModel tokens = redisTokenService.getToken(accesstoken);
		if (tokens != null) {
			String userid = tokens.getUserid();
			if (StringUtils.isNotEmpty(userid)) {
				// 此处调用业务逻辑层
				UserInfoModel uim = personalInfoService.getUserInfo(userid);
				if (uim != null) {
					goodsCartService.updateShoppingCartGoodsNum(userid, goodscartid,num);
					return new ResultBean();
				} else {
					return new ResultBean("2001", ResultApiMsg.C_2001);
				}
			}
		}
		return new ResultBean("001", ResultApiMsg.C_001);

	}
	
	@SystemControllerLog(method = "updateGoodsSpec", logtype = ConstantIF.LOG_TYPE_1, description = "修改购物车商品规格")
	@ApiOperation(value = "修改购物车商品规格", httpMethod = "POST", notes = "修改购物车商品规格", response = ResultBean.class)
	@RequestMapping(value = "/updateGoodsSpec", method = RequestMethod.POST)
	@ResponseBody
	public ResultBean updateGoodsSpec(
			@ApiParam(value = "token值") @RequestParam(value = ConstantIF.ACCESSTOKEN) String accesstoken,
			@ApiParam(value = "购物车id") @RequestParam(value = "goodscartid") String goodscartid,
			@ApiParam(value = "商品价格id") @RequestParam(value = "goodspriceid") String goodspriceid) {
		/*
		 * 此处做值判断
		 */
		TokenModel tokens = redisTokenService.getToken(accesstoken);
		if (tokens != null) {
			String userid = tokens.getUserid();
			if (StringUtils.isNotEmpty(userid)) {
				// 此处调用业务逻辑层
				UserInfoModel uim = personalInfoService.getUserInfo(userid);
				if (uim != null) {
					goodsCartService.updateShoppingCartGoodsSpec(userid, goodscartid,goodspriceid);
					return new ResultBean();
				} else {
					return new ResultBean("2001", ResultApiMsg.C_2001);
				}
			}
		}
		return new ResultBean("001", ResultApiMsg.C_001);
		
	}

}
