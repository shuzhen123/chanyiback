package dianfan.controller.goods;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;

import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

import dianfan.annotations.SystemControllerLog;
import dianfan.annotations.UnCheckedFilter;
import dianfan.constant.ConstantIF;
import dianfan.constant.ResultApiMsg;
import dianfan.entities.goods.JoinGoods;
import dianfan.models.ResultBean;
import dianfan.models.TokenModel;
import dianfan.service.goods.GoodsService;
import dianfan.service.impl.RedisTokenService;
import dianfan.util.StringUtility;

/**
 * @ClassName GoodsController
 * @Description 商品相关Controller
 * @author sz
 * @date 2018年7月3日 上午11:11:47
 */
@Scope(WebApplicationContext.SCOPE_REQUEST)
@Controller
@RequestMapping(value = "/goods")
public class GoodsController {
	
	/**
	 * 注入： #GoodsService
	 */
	@Autowired
	private GoodsService goodsService;
	
	/**
	 * 注入: #RedisTokenService
	 */
	@Autowired
	private RedisTokenService redisTokenService;
	

	/**
	 * @Title: getGoodsList
	 * @Description: 获取商品的列表
	 * @param joinGoods 
	 * 			入参model
	 * @return ResultBean
	 * @throws: 
	 * @time: 2018年7月3日 下午2:42:16
	 */
	@SystemControllerLog(method = "getGoodsList", logtype = ConstantIF.LOG_TYPE_1, description = "获取商品的列表")
	@ApiOperation(value = "getGoodsList", httpMethod = "POST", notes = " 获取商品的列表", response = ResultBean.class)
	@RequestMapping(value = "/getGoodsList", method = RequestMethod.POST)
	@UnCheckedFilter
	public @ResponseBody ResultBean getGoodsList(@ApiParam(value="接收商品分类列表的入参项") JoinGoods joinGoods) {
		String accesstoken = joinGoods.getAccesstoken();
		TokenModel token = redisTokenService.getToken(accesstoken);
		if(redisTokenService.checkToken(token)) {
			//从accesstoken中获取userid
			joinGoods.setUserid(token.getUserid());
		}
		return goodsService.findGoodsList(joinGoods);
	}
	
	/**
	 * @Title: getGoodsFiltrate
	 * @Description: 获取商品筛选项
	 * @return
	 * @throws:
	 * @time: 2018年7月4日 下午3:32:43
	 */
	@SystemControllerLog(method = "getGoodsFiltrate", logtype = ConstantIF.LOG_TYPE_1, description = "获取商品筛选项")
	@ApiOperation(value = "getGoodsFiltrate", httpMethod = "GET", notes = "获取商品筛选项", response = ResultBean.class)
	@RequestMapping(value = "/getGoodsFiltrate", method = RequestMethod.GET)
	@UnCheckedFilter
	public @ResponseBody ResultBean getGoodsFiltrate(
			@ApiParam(value="分类id") @RequestParam(value="classifyid", required=false) String classifyid,
			@ApiParam(value="员工是否可以易拼(0非易拼，1易拼)") @RequestParam(value="easySpelling") Integer easy_spelling
			) {
		return goodsService.findGoodsFiltrate(classifyid, easy_spelling);
	}
	
	
	/**
	 * @Title: goodsAddFavorites
	 * @Description:  商品加入收藏
	 * @param accesstoken 
	 * 				 accesstoken
	 * @param goodsid 
	 * 				商品的id
	 * @return ResultBean
	 * @throws: 
	 * @time: 2018年7月3日 下午3:51:58
	 */
	@SystemControllerLog(method = "goodsAddFavorites", logtype = ConstantIF.LOG_TYPE_1, description = "商品加入收藏")
	@ApiOperation(value = "goodsAddFavorites", httpMethod = "POST", notes = " 商品加入收藏", response = ResultBean.class)
	@RequestMapping(value = "/goodsAddFavorites", method = RequestMethod.POST)
	public @ResponseBody ResultBean goodsAddFavorites(
			@ApiParam(value="accesstoken") @RequestParam(value=ConstantIF.ACCESSTOKEN,required=true) String accesstoken,
			@ApiParam(value="商品的id（id之间以','分隔）") @RequestParam(value="goodsid") String goodsid
			) {
		// 1.入参验证
		String[] ids = null;
		if (goodsid != null) {
			ids = goodsid.split(",");
		}
		// 验证goodsid是否为空
		if (ids == null || ids.length < 1) {
			return new ResultBean("501",ResultApiMsg.C_501);
		}
		// 2.获取token
		TokenModel token = redisTokenService.getToken(accesstoken);
		// 3.库操作 返回
		return goodsService.addGoodsFavorites(token.getUserid(), ids);
	}
	
	
	/**
	 * @Title: goodsDelFavorites
	 * @Description: 商品取消收藏
	 * @param accesstoken 
	 * 			accesstoken
	 * @param goodsid 
	 * 			商品的id
	 * @return
	 * @throws:
	 * @time: 2018年7月3日 下午4:38:12
	 */
	@SystemControllerLog(method = "goodsDelFavorites", logtype = ConstantIF.LOG_TYPE_1, description = "商品取消收藏")
	@ApiOperation(value = "goodsDelFavorites", httpMethod = "POST", notes = " 商品取消收藏", response = ResultBean.class)
	@RequestMapping(value = "/goodsDelFavorites", method = RequestMethod.POST)
	public @ResponseBody ResultBean goodsDelFavorites(
			@ApiParam(value="accesstoken") @RequestParam(value=ConstantIF.ACCESSTOKEN,required=true) String accesstoken,
			@ApiParam(value="商品的id") @RequestParam(value="goodsid") String goodsid
			) {
		// 1.入参验证
		if (StringUtility.isNull(goodsid)) {
			// !参数错误
			return new ResultBean("501", ResultApiMsg.C_501);
		}
		// 2.获取token
		TokenModel token = redisTokenService.getToken(accesstoken);
		// 3.库操作 返回
		return goodsService.delGoodsFavorites(token.getUserid(), goodsid);
	}
	
	
	/**
	 * @Title: goodsDetails
	 * @Description: 商品的详情
	 * @param accesstoken 
	 * 			accesstoken
	 * @param goodsid 
	 * 			商品的id
	 * @return
	 * @throws:
	 * @time: 2018年7月3日 下午4:44:58
	 */
	@SystemControllerLog(method = "goodsDetails", logtype = ConstantIF.LOG_TYPE_1, description = "商品详情")
	@ApiOperation(value = "goodsDetails", httpMethod = "POST", notes = "商品详情", response = ResultBean.class)
	@RequestMapping(value = "/goodsDetails", method = RequestMethod.POST)
	@UnCheckedFilter
	public @ResponseBody ResultBean goodsDetails(
			@ApiParam(value="accesstoken") @RequestParam(value=ConstantIF.ACCESSTOKEN,required=false) String accesstoken,
			@ApiParam(value="商品的id") @RequestParam(value="goodsid") String goodsid
			) {
		
		// 1.获取token
		TokenModel token = redisTokenService.getToken(accesstoken);
		// userid 
		String userid;
		if (StringUtils.isEmpty(token)) {
			// 没有传入accesstoken 时 ，设置userid设置为“/”，保证在筛选是否收藏的时候，结果一定是未收藏
			userid = "/";
		} else {
			// 验证token是否过期
			boolean pass = redisTokenService.checkToken(token);
			if(!pass) {
				//token验证失败
				return new ResultBean("001", ResultApiMsg.C_001);
			}
			userid = token.getUserid();
		}
		// 2.入参验证
		if (StringUtility.isNull(goodsid)) {
			// !参数错误
			return new ResultBean("501", ResultApiMsg.C_501);
		}
		
		// 3.库操作 返回
		return goodsService.getGoodsDetails(userid, goodsid);
	}
	
	/**
	 * @Title: goodsSort
	 * @Description: 商品自定义排序
	 * @param accesstoken
	 * @param goodsid
	 * @return
	 * @throws:
	 * @time: 2018年7月6日 下午1:41:00
	 */
	@SystemControllerLog(method = "updateGoodsSortDefind", logtype = ConstantIF.LOG_TYPE_1, description = "商品自定义排序")
	@ApiOperation(value = "updateGoodsSortDefind", httpMethod = "POST", notes = "商品自定义排序", response = ResultBean.class)
	@RequestMapping(value = "/updateGoodsSort", method = RequestMethod.POST)
	public @ResponseBody ResultBean updateGoodsSortDefind(
			@ApiParam(value="accesstoken") @RequestParam(value=ConstantIF.ACCESSTOKEN) String accesstoken,
			@ApiParam(value="商品的id") @RequestParam(value="goodsid") String goodsid) {
		// 1.获取token
		TokenModel token = redisTokenService.getToken(accesstoken);
		goodsService.updateGoodsSort(token.getUserid(), goodsid);
		return new ResultBean();
	}
	
	
}
