package dianfan.controller.banner;

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
import dianfan.service.banner.BannerService;


/** @ClassName BannerController
 * @Description 轮播图控制器
 * @author zwb
 * @date 2018年6月28日 下午2:20:41
 */ 
@Scope(WebApplicationContext.SCOPE_REQUEST)
@Controller
@RequestMapping(value = "/banner")
public class BannerController {
	
	/**
	 * 轮播图相关service
	 */
	@Autowired
	BannerService bannerService;
	
	/** @Title: findBanners
	 * @Description: 获取轮播图列表
	 * @param 
	 * @return ResultBean
	 * @throws:
	 * @time: 2018年6月28日 下午2:36:02
	 */ 
	@SystemControllerLog(method = "findBanners", logtype = ConstantIF.LOG_TYPE_1, description = "获取轮播图列表")
	@ApiOperation(value = "获取轮播图列表", httpMethod = "GET", notes = "获取轮播图列表", response = ResultBean.class)
	@RequestMapping(value = "/findBanners", method = RequestMethod.GET)
	@ResponseBody
	@UnCheckedFilter
	public ResultBean findBanners() {
		//返回查询出来的轮播图列表
		return new ResultBean(bannerService.findBanners());
	}
	
	

}
