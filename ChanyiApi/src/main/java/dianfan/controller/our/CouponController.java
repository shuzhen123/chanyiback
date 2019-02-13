/**  
* @Title: TestController.java
* @Package dianfan.controller
* @Description: TODO
* @author Administrator
* @date 2018年5月17日 下午2:17:06
* @version V1.0  
*/
package dianfan.controller.our;

import java.util.Map;

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
import dianfan.service.our.CouponService;
import dianfan.service.our.PersonalInfoService;

/**
 * @ClassName CouponController
 * @Description 获取优惠券列表
 * @author yl
 * @date 2018年6月28日 上午10:22:48
 */
@Scope(WebApplicationContext.SCOPE_REQUEST)
@Controller
@RequestMapping(value = "/coupon")
public class CouponController {
	/**
	 * 注入：#CouponService
	 */
	@Autowired
	private CouponService couponService;
	@Autowired
	private RedisTokenService redisTokenService;
	@Autowired
	private PersonalInfoService personalInfoService;

	/**
	 * @Title: findCouponList
	 * @Description: 获取优惠券列表
	 * @param userid
	 *            用户id
	 * @return 获取优惠券列表
	 * @throws:
	 * @time: 2018年6月28日 上午11:02:05
	 */
	@SystemControllerLog(method = "findCouponList", logtype = ConstantIF.LOG_TYPE_1, description = "获取优惠券列表")
	@ApiOperation(value = "获取优惠券列表", httpMethod = "GET", notes = "获取优惠券列表", response = ResultBean.class)
	@RequestMapping(value = "/findCouponList", method = RequestMethod.GET)
	@ResponseBody
	public ResultBean findCouponList(
			@ApiParam(value = "token值") @RequestParam(value = ConstantIF.ACCESSTOKEN) String accesstoken,
			@ApiParam(value = "是否已使用") @RequestParam(value = "userused") Integer userused,
			@ApiParam(value = "第几页") @RequestParam(value = "pagenum", required = true) Integer pagenum,
			@ApiParam(value = "每页的条数") @RequestParam(value = "count", required = true) Integer count,
			@ApiParam(value = "为空返回优惠券列表，为1时返回可用优惠券列表") @RequestParam(value = "used", required = false) String used
			) {
		/*
		 * 此处做值判断
		 */
		TokenModel tokens = redisTokenService.getToken(accesstoken);
		Map<String, Object> couponlists = null;
		if (tokens != null) {
			String userid = tokens.getUserid();
			if (StringUtils.isNotEmpty(userid)) {
				// 此处调用业务逻辑层
				UserInfoModel uim = personalInfoService.getUserInfo(userid);
				if (uim != null) {
					couponlists = couponService.findCouponList(userid,userused,pagenum,count,used);
					// if (couponlists!=null && couponlists.size()>0) {
					return new ResultBean(couponlists);
					// }else {
					// return new ResultBean("2003",ResultApiMsg.C_2003);
					// }
				} 
			}
		}
		return new ResultBean("001", ResultApiMsg.C_001);

	}
	
	/**
	 * @Title: checkEnableCoupon
	 * @Description: 卡券可用性检测
	 * @param accesstoken
	 * @param couponid
	 * @return
	 * @throws:
	 * @time: 2018年8月2日 下午3:53:52
	 * @author cjy
	 */
	@SystemControllerLog(method = "checkEnableCoupon", logtype = ConstantIF.LOG_TYPE_1, description = "卡券可用性检测")
	@ApiOperation(value = "卡券可用性检测", httpMethod = "GET", notes = "获取优惠券列表", response = ResultBean.class)
	@RequestMapping(value = "/checkEnableCoupon", method = RequestMethod.GET)
	@ResponseBody
	public ResultBean checkEnableCoupon(
			@ApiParam(value = "token值") @RequestParam(value = ConstantIF.ACCESSTOKEN) String accesstoken,
			@ApiParam(value = "优惠券id") @RequestParam(value = "couponid") String couponid) {
		TokenModel tokens = redisTokenService.getToken(accesstoken);
		boolean b = couponService.checkEnableCoupon(couponid, tokens.getUserid());
		return new ResultBean(b);
		
	}

}
