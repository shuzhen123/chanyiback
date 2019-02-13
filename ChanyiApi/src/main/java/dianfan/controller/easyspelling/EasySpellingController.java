package dianfan.controller.easyspelling;

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
import dianfan.models.ResultBean;
import dianfan.service.easyspelling.EasySpellingService;

/** @ClassName EasySpellingController
 * @Description 拼团控制器
 * @author zwb
 * @date 2018年7月3日 下午5:36:28
 */ 
@Scope(WebApplicationContext.SCOPE_REQUEST)
@Controller
@RequestMapping(value = "/easyspelling")
public class EasySpellingController {
	
	/** 
	 * 易拼业务层
	 */
	@Autowired
	EasySpellingService easySpellingService;
	
	/** @Title: easyspellingList
	 * @Description: 获取拼团列表
	 * @param goodsId
	 * @return ResultBean
	 * @throws:
	 * @time: 2018年7月4日 下午2:38:23
	 */ 
	@SystemControllerLog(method = "easyspellingList", logtype = ConstantIF.LOG_TYPE_1, description = "获取拼团列表")
	@ApiOperation(value = "获取拼团列表", httpMethod = "GET", notes = "获取拼团列表", response = ResultBean.class)
	@RequestMapping(value = "/easyspellingList", method = RequestMethod.GET)
	@ResponseBody
	@UnCheckedFilter
	public ResultBean easyspellingList(@ApiParam(value = "商品id") @RequestParam(value = "goodsId") String goodsId,
			@ApiParam(value = "第几页") @RequestParam(value = "pageNum") Integer pageNum,
			@ApiParam(value = "每页的条数") @RequestParam(value = "count") Integer count
			) {
		//判断参数
		if(StringUtils.isEmpty(goodsId)) {
			return new ResultBean("501",ResultApiMsg.C_501);
		}
		if(StringUtils.isEmpty(pageNum)) {
			return new ResultBean("501",ResultApiMsg.C_501);
		}
		if(StringUtils.isEmpty(count)) {
			return new ResultBean("501",ResultApiMsg.C_501);
		}
		//返回查询出来的拼团信息
		return easySpellingService.findEasySpellingList(goodsId,pageNum,count);
	}
	
	

}
