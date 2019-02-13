package dianfan.controller.goods;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;

import com.wordnik.swagger.annotations.ApiOperation;

import dianfan.annotations.SystemControllerLog;
import dianfan.annotations.UnCheckedFilter;
import dianfan.constant.ConstantIF;
import dianfan.models.ResultBean;
import dianfan.service.goods.ClassifyService;


/**
 * @ClassName classifyController
 * @Description 商品分类相关
 * @author sz
 * @date 2018年7月2日 上午10:11:13
 */
@Scope(WebApplicationContext.SCOPE_REQUEST)
@Controller
@RequestMapping(value = "/goods/classify")
public class ClassifyController {
	

	/**
	 * 注入： #ClassifyService
	 */
	@Autowired
	private ClassifyService classifyService;
	
	
	
	/**
	 * @Title: getGoodsClassifyList
	 * @Description: 获取商品分类列表
	 * @return ResultBean
	 * @throws:
	 * @time: 2018年7月2日 上午11:14:49
	 */
	@SystemControllerLog(method = "getGoodsClassifyList", logtype = ConstantIF.LOG_TYPE_1, description = "获取商品分类列表")
	@ApiOperation(value = "getGoodsClassifyList", httpMethod = "GET", notes = "获取商品分类列表", response = ResultBean.class)
	@RequestMapping(value = "/getGoodsClassifyList", method = RequestMethod.GET)
	@UnCheckedFilter
	public @ResponseBody ResultBean getGoodsClassifyList() {
		// 创建一个返回bean
		ResultBean result = new ResultBean();
		result = classifyService.fildGoodsClassify();
		// 成功返回
		return result;
	}
	
	
	
}
