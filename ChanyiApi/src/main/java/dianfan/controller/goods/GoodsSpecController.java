package dianfan.controller.goods;

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
import dianfan.annotations.UnCheckedFilter;
import dianfan.constant.ConstantIF;
import dianfan.models.ResultBean;
import dianfan.service.goods.GoodsSpecService;

/**
 * @ClassName GoodsSpecController
 * @Description 商品规格
 * @author cjy
 * @date 2018年7月3日 下午2:37:18
 */
@Scope(WebApplicationContext.SCOPE_REQUEST)
@Controller
@RequestMapping(value = "/goods/spec")
public class GoodsSpecController {
	
	/**
	 * 注入： #GoodsSpecService
	 */
	@Autowired
	private GoodsSpecService goodsSpecService;
	
	/**
	 * @Title: getGoodsSpec
	 * @Description: 获取商品规格
	 * @param goodsid 商品id
	 * @return
	 * @throws:
	 * @time: 2018年7月3日 下午2:39:59
	 */
	@SystemControllerLog(method = "getGoodsSpec", logtype = ConstantIF.LOG_TYPE_1, description = "获取商品规格")
	@ApiOperation(value = "getGoodsSpec", httpMethod = "POST", notes = "获取商品规格", response = ResultBean.class)
	@RequestMapping(value = "/getGoodsSpec", method = RequestMethod.POST)
	@UnCheckedFilter
	public @ResponseBody ResultBean getGoodsSpec(@ApiParam(value="商品id") @RequestParam(value="goodsid") String goodsid) {
		return goodsSpecService.findGoodsSpecByGoodsid(goodsid);
	}
	
	
	

}
