package dianfan.controller.store;

import java.io.IOException;
import java.text.ParseException;

import org.aspectj.apache.bcel.generic.ReturnaddressType;
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
import dianfan.constant.ResultBgMsg;
import dianfan.entities.store.ESSearchParam;
import dianfan.models.ResultBean;
import dianfan.models.TokenModel;
import dianfan.service.impl.RedisTokenService;
import dianfan.service.store.EasyRegimentStoreService;

/**
 * @ClassName StoreController
 * @Description 易团门店
 * @author cjy
 * @date 2018年7月6日 下午2:40:49
 */
@Scope(WebApplicationContext.SCOPE_REQUEST)
@Controller
@RequestMapping(value = "/store")
public class StoreController {

	@Autowired
	private EasyRegimentStoreService easyRegimentService;
	
	/**
	 * 注入: #RedisTokenService
	 */
	@Autowired
	private RedisTokenService redisTokenService;
	
	/**
	 * @Title: easyRegimentStore
	 * @Description: 易团体验店列表
	 * @return
	 * @throws IOException 
	 * @throws:
	 * @time: 2018年7月6日 下午2:42:45
	 */
	@SystemControllerLog(method = "easyRegimentStore", logtype = ConstantIF.LOG_TYPE_1, description = "易团体验店列表")
	@ApiOperation(value = "easyRegimentStore", httpMethod = "GET", notes = "易团体验店列表", response = ResultBean.class)
	@RequestMapping(value = "/easyRegimentStore", method = RequestMethod.GET)
	@UnCheckedFilter
	public @ResponseBody ResultBean easyRegimentStore(ESSearchParam essp) throws IOException {
		return easyRegimentService.findEasyRegimentStore(essp);
	}
	
	/**
	 * @Title: storeFiltrate
	 * @Description: 体验店筛选项
	 * @param cityCode 市级code
	 * @return
	 * @throws:
	 * @time: 2018年7月6日 下午7:05:25
	 */
	@SystemControllerLog(method = "storeFiltrate", logtype = ConstantIF.LOG_TYPE_1, description = "体验店筛选项")
	@ApiOperation(value = "storeFiltrate", httpMethod = "GET", notes = "体验店筛选项", response = ResultBean.class)
	@RequestMapping(value = "/storeFiltrate", method = RequestMethod.GET)
	@UnCheckedFilter
	public @ResponseBody ResultBean storeFiltrate(
			@ApiParam(value = "经度") @RequestParam(value="longitude", required=false) String longitude,
			@ApiParam(value = "纬度") @RequestParam(value="latitude", required=false) String latitude, 
			@ApiParam(value = "市级code") @RequestParam(value="cityCode", required=false) String cityCode) {
		return easyRegimentService.findStoreFiltrate(longitude, latitude, cityCode);
	}
	
	/**
	 * @Title: findExperienceInfo
	 * @Description: 获取体验店详情
	 * @return
	 * @throws:
	 * @time: 2018年8月2日 下午12:16:34
	 */
	@SystemControllerLog(method = "findExperienceInfo", logtype = ConstantIF.LOG_TYPE_1, description = "获取体验店详情")
	@ApiOperation(value = "findExperienceInfo", httpMethod = "GET", notes = "获取体验店详情", response = ResultBean.class)
	@RequestMapping(value = "/findExperienceInfo", method = RequestMethod.GET)
	@UnCheckedFilter
	public @ResponseBody ResultBean findExperienceInfo(
			@ApiParam(value = "体验店ID") @RequestParam(value="id") String id
			) {
		// 
		return easyRegimentService.findExperienceInfo(id);
	}
	
	
	/**
	 * @Title: userGetCoupon
	 * @Description: 用户领取体验店里的优惠券
	 * @param accesstoken 
	 * @return ResultBean
	 * @throws ParseException 
	 * @throws:
	 * @time: 2018年6月30日 下午4:09:18
	 */
	@SystemControllerLog(method = "userGetCoupon", logtype = ConstantIF.LOG_TYPE_1, description = "用户领取体验店里的优惠券")
	@ApiOperation(value = "userGetCoupon", httpMethod = "POST", notes = "用户领取体验店里的优惠券", response = ResultBean.class)
	@RequestMapping(value = "/userGetCoupon", method = RequestMethod.POST)
	public @ResponseBody ResultBean userGetCoupon(
			@ApiParam(value="accesstoken") @RequestParam(value=ConstantIF.ACCESSTOKEN) String accesstoken,
			@ApiParam(value="优惠券ID")  @RequestParam(value="id") String id
			) throws ParseException {
		
		// 1.获取token
		TokenModel token = redisTokenService.getToken(accesstoken);
		if (StringUtils.isEmpty(token)) {
			// 登陆信息已经过期
			return new ResultBean("001",ResultBgMsg.C_001);
		}
		
		// 2.调库
		return easyRegimentService.getUserGetCoupon(token.getUserid(),id);
	}
	
	
	
}
