package dianfan.controller.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;
import com.wordnik.swagger.annotations.ApiParam;
import dianfan.annotations.SystemControllerLog;
import dianfan.constant.ConstantIF;
import dianfan.constant.ResultBgMsg;
import dianfan.models.ResultBean;
import dianfan.models.TokenModel;
import dianfan.service.base.RegionService;
import dianfan.service.impl.RedisTokenService;
import dianfan.util.StringUtility;

/**
 * @ClassName RegionManage
 * @Description 区域管理相关 后台
 * @author sz
 * @date 2018年7月26日 上午10:43:38
 */
@Scope(WebApplicationContext.SCOPE_REQUEST)
@Controller
@RequestMapping(value = "/region")
public class RegionManage {
	
	/**
	 * 注入： #RegionService
	 */
	@Autowired
	private RegionService regionService;
	/**
	 * 注入： #RedisTokenService
	 */
	@Autowired
	private RedisTokenService redisTokenService;

	
	
	/**
	 * @Title: findRegionList
	 * @Description: 获取大区列表
	 * @param accesstoken accesstoken
	 * @return
	 * @throws:
	 * @time: 2018年7月26日 上午11:09:39
	 */
	@SystemControllerLog(method = "findRegionList", logtype = ConstantIF.LOG_TYPE_2, description = "获取大区列表")
	@RequestMapping(value = "/findRegionList", method = RequestMethod.POST)	
	public @ResponseBody ResultBean findRegionList (
			@ApiParam(value="accesstoken") @RequestParam(value=ConstantIF.ACCESSTOKEN) String accesstoken
			) {
		// 构建返回模型
		ResultBean result = new ResultBean();
		TokenModel token = redisTokenService.getToken(accesstoken);
		if (token == null ) {
			// 登录信息已过期，请重新登录
			return new ResultBean("001",ResultBgMsg.C_001);
		}
		// 获取大区列表
		result = regionService.findRegionList();
		
		// 返回
		return result;
	}
	
	/**
	 * @Title: updataRegionList
	 * @Description: 更新大区和省关系
	 * @param accesstoken accesstoken
	 * @param regionid 大区id
	 * @param codeids 省 ids
	 * @return
	 * @throws:
	 * @time: 2018年7月26日 上午11:47:11
	 */
	@SystemControllerLog(method = "updataRegionList", logtype = ConstantIF.LOG_TYPE_2, description = "更新大区和省关系")
	@RequestMapping(value = "/updataRegionList", method = RequestMethod.POST)	
	public @ResponseBody ResultBean updataRegionList (
			@ApiParam(value="accesstoken") @RequestParam(value=ConstantIF.ACCESSTOKEN) String accesstoken,
			@ApiParam(value="大区ID") @RequestParam(value="regionid") String regionid,
			@ApiParam(value="省份ID") @RequestParam(value="codeids") String codeids
			) {
		// 构建返回模型
		ResultBean result = new ResultBean();
		// 基础验证
		if (StringUtility.isNull(regionid)) {
			return new ResultBean("501",ResultBgMsg.C_501);
		}
		result = regionService.updataRegionList(regionid, codeids);
		// 返回
		return result;		
	}
	
	
	

}
