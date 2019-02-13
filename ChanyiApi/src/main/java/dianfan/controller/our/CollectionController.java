/**  
* @Title: CollectionController.java
* @Package dianfan.controller.our
* @Description: TODO
* @author yl
* @date 2018年6月30日 上午10:12:02
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
import dianfan.service.our.CollectionService;
import dianfan.service.our.PersonalInfoService;

/**
 * @ClassName CollectionController
 * @Description 用户商品收藏列表
 * @author yl
 * @date 2018年6月30日 上午10:12:02
 */
@Scope(WebApplicationContext.SCOPE_REQUEST)
@Controller
@RequestMapping(value = "/collect")
public class CollectionController {
	/**
	 * 注入：#CollectionService RedisTokenService PersonalInfoService
	 */
	@Autowired
	private CollectionService collectionService;
	@Autowired
	private RedisTokenService redisTokenService;
	@Autowired
	private PersonalInfoService personalInfoService;

	/**
	 * @Title: findCouponList
	 * @Description: 获取用户商品收藏列表
	 * @param userid
	 *            用户id
	 * @return 获取用户商品收藏列表
	 * @throws:
	 * @time: 2018年6月28日 上午11:02:05
	 */
	@SystemControllerLog(method = "findGoodsList", logtype = ConstantIF.LOG_TYPE_1, description = "获取用户商品收藏列表")
	@ApiOperation(value = "获取用户商品收藏列表", httpMethod = "GET", notes = "获取用户商品收藏列表", response = ResultBean.class)
	@RequestMapping(value = "/findGoodsList", method = RequestMethod.GET)
	@ResponseBody
	public ResultBean findGoodsList(
			@ApiParam(value = "token值") @RequestParam(value = ConstantIF.ACCESSTOKEN) String accesstoken,
			@ApiParam(value = "第几页") @RequestParam(value = "page", defaultValue = "1", required = false) Integer page,
			@ApiParam(value = "每页的条数") @RequestParam(value = "pagecounts", required = false) Integer pagecounts) {
		/*
		 * 此处做值判断
		 */
		TokenModel tokens = redisTokenService.getToken(accesstoken);
		ResultBean goodslists = null;
		if (tokens != null) {
			String userid = tokens.getUserid();
			if (StringUtils.isNotEmpty(userid)) {
				// 此处调用业务逻辑层
				UserInfoModel uim = personalInfoService.getUserInfo(userid);
				if (uim != null) {
					goodslists = collectionService.findCollectionList(userid,page,pagecounts);
					/* if (goodslists != null && goodslists.size() > 0) { */
					return new ResultBean(goodslists);
					/*
					 * } else { return new ResultBean("2006", ResultApiMsg.C_2006); }
					 */
				}
			}
		}
		return new ResultBean("001", ResultApiMsg.C_001);

	}

	@SystemControllerLog(method = "cancelCollection", logtype = ConstantIF.LOG_TYPE_1, description = "取消收藏")
	@ApiOperation(value = "取消收藏", httpMethod = "POST", notes = "取消收藏", response = ResultBean.class)
	@RequestMapping(value = "/cancelCollection", method = RequestMethod.POST)
	public @ResponseBody ResultBean cancelCollection(
			@ApiParam(value = "token值") @RequestParam(value = ConstantIF.ACCESSTOKEN) String accesstoken,
			@ApiParam(value = "商品id") @RequestParam(value = "goodsid") String goodsid) {
		/*
		 * 此处做值判断
		 */
		if (StringUtils.isEmpty(accesstoken)) {
			return new ResultBean("501", ResultApiMsg.C_501);
		}
		TokenModel tokens = redisTokenService.getToken(accesstoken);
		if (tokens != null) {
			String userid = tokens.getUserid();
			if (StringUtils.isNotEmpty(userid)) {
				// 此处调用业务逻辑层
				UserInfoModel uim = personalInfoService.getUserInfo(userid);
				if (uim != null) {
					collectionService.delCollection(userid, goodsid);
					return new ResultBean();
				}
			}
		}
		return new ResultBean("001", ResultApiMsg.C_001);
	}

}
