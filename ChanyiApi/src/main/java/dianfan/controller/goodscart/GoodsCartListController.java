/**  
* @Title: GoodsCartListController.java
* @Package dianfan.controller.goodscart
* @Description: TODO
* @author yl
* @date 2018年7月3日 下午5:29:44
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
import dianfan.dao.mapper.goodscart.GoodsCartMapper;
import dianfan.entities.our.UserInfoModel;
import dianfan.models.ResultBean;
import dianfan.models.TokenModel;
import dianfan.service.goodscart.GoodsCartService;
import dianfan.service.impl.RedisTokenService;
import dianfan.service.our.PersonalInfoService;

/**
 * @ClassName GoodsCartListController
 * @Description 获取购物车列表
 * @author yl
 * @date 2018年7月3日 下午5:29:44
 */
@Scope(WebApplicationContext.SCOPE_REQUEST)
@Controller
@RequestMapping(value = "/gcart")
public class GoodsCartListController {

	/**
	 * 注入：#GoodsCartService RedisTokenService PersonalInfoService
	 */
	@Autowired
	private GoodsCartService goodsCartService;
	@Autowired
	private RedisTokenService redisTokenService;
	@Autowired
	private PersonalInfoService personalInfoService;
	@Autowired
	private GoodsCartMapper goodsCartMapper;

	@SystemControllerLog(method = "findGoodsCartList", logtype = ConstantIF.LOG_TYPE_1, description = "获取购物车列表")
	@ApiOperation(value = "获取购物车列表", httpMethod = "GET", notes = "获取购物车列表", response = ResultBean.class)
	@RequestMapping(value = "/findGoodsCartList", method = RequestMethod.GET)
	@ResponseBody
	public ResultBean findGoodsCartList(
			@ApiParam(value = "token值") @RequestParam(value = ConstantIF.ACCESSTOKEN) String accesstoken,
			@ApiParam(value = "第几页") @RequestParam(value = "page", defaultValue = "1", required = false) Integer page,
			@ApiParam(value = "每页的条数") @RequestParam(value = "pagecounts", required = false) Integer pagecounts) {
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
					ResultBean gcmlist = goodsCartService.findShoppingCartList(userid,page,pagecounts);
					return new ResultBean(gcmlist);
				} else {
					return new ResultBean("2001", ResultApiMsg.C_2001);
				}
			}
		}
		return new ResultBean("001", ResultApiMsg.C_001);

	}

	@SystemControllerLog(method = "countGoodsCartNum", logtype = ConstantIF.LOG_TYPE_1, description = "获取购物车数量")
	@ApiOperation(value = "获取购物车数量", httpMethod = "GET", notes = "获取购物车数量", response = ResultBean.class)
	@RequestMapping(value = "/countGoodsCartNum", method = RequestMethod.GET)
	@ResponseBody
	public ResultBean countGoodsCartNum(
			@ApiParam(value = "token值") @RequestParam(value = ConstantIF.ACCESSTOKEN) String accesstoken) {
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
					Integer goodsidcount = goodsCartMapper.getGoodsCartNum(userid);
					return new ResultBean(goodsidcount);
				} else {
					return new ResultBean("2001", ResultApiMsg.C_2001);
				}
			}
		}
		return new ResultBean("001", ResultApiMsg.C_001);

	}

}
