/**  
* @Title: CouponListController.java
* @Package dianfan.controller.bgcoupon
* @Description: TODO
* @author yl
* @date 2018年7月18日 上午10:31:58
* @version V1.0  
*/ 
package dianfan.controller.bgcoupon;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

import dianfan.annotations.SystemControllerLog;
import dianfan.constant.ConstantIF;
import dianfan.constant.ResultBgMsg;
import dianfan.models.ResultBean;
import dianfan.models.TokenModel;
import dianfan.service.impl.RedisTokenService;
import dianfan.service.our.CouponService;

/** @ClassName CouponListController
 * @Description 优惠券相关操作
 * @author yl
 * @date 2018年7月18日 上午10:31:58
 */
@Scope("request")
@Controller
@RequestMapping(value = "/bgcouponlist")
public class CouponListController {
	
	/**
	 * 注入： #RedisTokenService
	 */
	@Autowired
	private RedisTokenService redisTokenService;
	@Autowired
	private CouponService couponService;
	
	/**
	 * @Title: findBgCouponList
	 * @Description:  按条件获取优惠券列表
	 * @param accesstoken token值
	 * @param couponname 优惠券名称
	 * @param coupontype  优惠券类型(01:商家优惠券02：注册优惠券03：红包)
	 * @param couponendtimestart Start(优惠券截止日期)
	 * @param couponendtimeend End(优惠券截止日期)
	 * @param couponapply 使用(01:应用02：停止应用)
	 * @param couponreducemoneystart Start(优惠券优惠金额)
	 * @param couponreducemoneyend End(优惠券优惠金额)
	 * @param couponstarttimestart Start(优惠券投放时间)
	 * @param couponstarttimeend End(优惠券投放时间)
	 * @param createtimestart Start(创建时间)
	 * @param createtimeend End(创建时间)
	 * @param page 第几页
	 * @param pagecounts 每页的条数
	 * @return
	 * @throws:
	 * @time: 2018年7月18日 下午1:25:49
	 */
	@SystemControllerLog(method = "findBgCouponList", logtype = ConstantIF.LOG_TYPE_1, description = "获取优惠券分类列表")
	@ApiOperation(value = "findBgCouponList", httpMethod = "GET", notes = "获取优惠券分类列表", response = ResultBean.class)
	@RequestMapping(value = "/findBgCouponList", method = RequestMethod.GET)
	public @ResponseBody ResultBean findBgCouponList(
			@ApiParam(value = "token值") @RequestParam(value = ConstantIF.ACCESSTOKEN) String accesstoken,
			@ApiParam(value = "优惠券名称") @RequestParam(value = "couponname", required = false) String couponname,
			@ApiParam(value = "优惠券类型(01:商家优惠券02：注册优惠券03：红包)") @RequestParam(value = "coupontype", required = false) String coupontype,
			@ApiParam(value = "Start(优惠券截止日期)") @RequestParam(value = "couponendtimestart", required = false) String couponendtimestart,
			@ApiParam(value = "End(优惠券截止日期)") @RequestParam(value = "couponendtimeend", required = false) String couponendtimeend,
			@ApiParam(value = "使用(01:应用02：停止应用)") @RequestParam(value = "couponapply", required = false) String couponapply,
			@ApiParam(value = "Start(优惠券优惠金额)") @RequestParam(value = "couponreducemoneystart", required = false) String couponreducemoneystart,
			@ApiParam(value = "End(优惠券优惠金额)") @RequestParam(value = "couponreducemoneyend", required = false) String couponreducemoneyend,
			@ApiParam(value = "Start(优惠券投放时间)") @RequestParam(value = "couponstarttimestart", required = false) String couponstarttimestart,
			@ApiParam(value = "End(优惠券投放时间)") @RequestParam(value = "couponstarttimeend", required = false) String couponstarttimeend,
			@ApiParam(value = "Start(创建时间)") @RequestParam(value = "createtimestart", required = false) String createtimestart,
			@ApiParam(value = "End(创建时间)") @RequestParam(value = "createtimeend", required = false) String createtimeend,
			@ApiParam(value="请求页") @RequestParam(value=ConstantIF.PAGE, defaultValue=ConstantIF.PAGE_START_STR) int page,
			@ApiParam(value="每页条数") @RequestParam(value=ConstantIF.PAGE_SIZE, defaultValue=ConstantIF.PAGE_OFFSET_STR) int pageSize
			) {
		   ResultBean result = new ResultBean();
				TokenModel tokens = redisTokenService.getToken(accesstoken);
				if (tokens != null) {
		            // 创建返回数据
				   result = couponService.findBgCouponList(couponname,coupontype,couponendtimestart,couponendtimeend,couponapply,couponreducemoneystart,couponreducemoneyend,couponstarttimestart,couponstarttimeend,createtimestart, createtimeend,page, pageSize);
				}else {
					return new ResultBean("001",ResultBgMsg.C_001);
				}
				// 4.成功
				return result;
		 }
	
	/**
	 * @Title: addCoupon
	 * @Description: 
	 * @param accesstoken
	  * @param couponname 优惠券名称
	 * @param coupontype 优惠券类型
	 * @param couponendtime 优惠券结束时间
	 * @param couponapply 使用(01:应用02：停止应用)
	 * @param couponreducemoney  优惠券优惠金额
	 * @param couponstarttime 优惠券投放时间
	 * @param couponcondtion 优惠券满足条件
	 * @param coupondes 优惠券描述
	 * @param couponclassifyid 商品分类id（可多选，如果为空，全场通用）
	 * @return
	 * @throws:
	 * @time: 2018年7月19日 上午10:43:23
	 */
	@SystemControllerLog(method = "addCoupon", logtype = ConstantIF.LOG_TYPE_1, description = "添加优惠券分类")
	@ApiOperation(value = "addCoupon", httpMethod = "POST", notes = "添加优惠券分类", response = ResultBean.class)
	@RequestMapping(value = "/addCoupon", method = RequestMethod.POST)
	public @ResponseBody ResultBean addCoupon(
			@ApiParam(value = "token值") @RequestParam(value = ConstantIF.ACCESSTOKEN) String accesstoken,
			@ApiParam(value = "优惠券名称") @RequestParam(value = "couponname", required = false) String couponname,
			@ApiParam(value = "优惠券类型(01:商家优惠券02：注册优惠券03：红包)") @RequestParam(value = "coupontype", required = false) String coupontype,
			@ApiParam(value = "优惠券截止日期") @RequestParam(value = "couponendtime", required = false) String couponendtime,
			@ApiParam(value = "使用(01:应用02：停止应用)") @RequestParam(value = "couponapply", required = false) String couponapply,
			@ApiParam(value = "优惠券优惠金额") @RequestParam(value = "couponreducemoney", required = false) String couponreducemoney,
			@ApiParam(value = "优惠券投放时间") @RequestParam(value = "couponstarttime", required = false) String couponstarttime,
			@ApiParam(value = "优惠券满足条件") @RequestParam(value = "couponcondtion", required = false) String couponcondtion,
			@ApiParam(value = "优惠券描述") @RequestParam(value = "coupondes", required = false) String coupondes,
			@ApiParam(value = "商品分类id（可多选，如果为空，全场通用）") @RequestParam(value = "couponclassifyid", required = false) String couponclassifyid,
			
			@ApiParam(value = "商品id") @RequestParam(value = "goodsid", required = false) String goodsid,
			@ApiParam(value = "体验店id") @RequestParam(value = "storeid", required = false) String storeid
			) {
				//优惠券名称限制位数
				if (StringUtils.isNotEmpty(couponname)) {
					int dalen = couponname.length();
					if (dalen>25) {
						return new ResultBean("2007",ResultBgMsg.C_2007);
					}
				}
		       //优惠券描述限制位数
				if (StringUtils.isNotEmpty(coupondes)) {
					int dalen = coupondes.length();
					if (dalen>120) {
						return new ResultBean("2005",ResultBgMsg.C_2005);
					}
				}
				//商品分类id限制位数
				if (StringUtils.isNotEmpty(couponclassifyid)) {
					int dalen = couponclassifyid.length();
					if (dalen>20000) {
						return new ResultBean("2006",ResultBgMsg.C_2006);
					}
				}
		      // 创建返回数据
				ResultBean result = new ResultBean();
				TokenModel tokens = redisTokenService.getToken(accesstoken);
				if (tokens != null) {
					String userid = tokens.getUserid();
				// 3.调库
					couponService.addBgCoupon(userid,couponname, coupontype, couponendtime, couponapply, couponreducemoney, 
							couponstarttime,couponcondtion,coupondes,couponclassifyid, goodsid, storeid);
				}else {
					return new ResultBean("001",ResultBgMsg.C_001);
				}
				// 4.成功
				return result;
		 }
	/**
	 * @Title: updateBgCoupon
	 * @Description: 
	 * @param accesstoken
	 * @param couponname 优惠券名称
	 * @param coupontype 优惠券类型
	 * @param couponendtime 优惠券结束时间
	 * @param couponapply 使用(01:应用02：停止应用)
	 * @param couponreducemoney  优惠券优惠金额
	 * @param couponstarttime 优惠券投放时间
	 * @param couponcondtion 优惠券满足条件
	 * @param coupondes 优惠券描述
	 * @param couponclassifyid 商品分类id（可多选，如果为空，全场通用）
	 * @return
	 * @throws:
	 * @time: 2018年7月19日 上午10:43:23
	 */
	@SystemControllerLog(method = "updateBgCoupon", logtype = ConstantIF.LOG_TYPE_1, description = "修改优惠券分类")
	@ApiOperation(value = "updateBgCoupon", httpMethod = "POST", notes = "修改优惠券分类", response = ResultBean.class)
	@RequestMapping(value = "/updateBgCoupon", method = RequestMethod.POST)
	public @ResponseBody ResultBean updateBgCoupon(
			@ApiParam(value = "token值") @RequestParam(value = ConstantIF.ACCESSTOKEN) String accesstoken,
			@ApiParam(value = "优惠券id") @RequestParam(value = "couponid", required = true) String couponid,
			@ApiParam(value = "优惠券名称") @RequestParam(value = "couponname", required = false) String couponname,
			@ApiParam(value = "优惠券类型(01:商家优惠券02：注册优惠券03：红包)") @RequestParam(value = "coupontype", required = false) String coupontype,
			@ApiParam(value = "优惠券截止日期") @RequestParam(value = "couponendtime", required = false) String couponendtime,
			@ApiParam(value = "使用(01:应用02：停止应用)") @RequestParam(value = "couponapply", required = false) String couponapply,
			@ApiParam(value = "优惠券优惠金额") @RequestParam(value = "couponreducemoney", required = false) String couponreducemoney,
			@ApiParam(value = "优惠券投放时间") @RequestParam(value = "couponstarttime", required = false) String couponstarttime,
			@ApiParam(value = "优惠券满足条件") @RequestParam(value = "couponcondtion", required = false) String couponcondtion,
			@ApiParam(value = "优惠券描述") @RequestParam(value = "coupondes", required = false) String coupondes,
			@ApiParam(value = "商品分类id（可多选，如果为空，全场通用）") @RequestParam(value = "couponclassifyid", required = false) String couponclassifyid,
			@ApiParam(value = "商品id") @RequestParam(value = "goodsid", required = false) String goodsid,
			@ApiParam(value = "体验店id") @RequestParam(value = "storeid", required = false) String storeid
			) {
		//优惠券名称限制位数
		if (StringUtils.isNotEmpty(couponname)) {
			int dalen = couponname.length();
			if (dalen>25) {
				return new ResultBean("2007",ResultBgMsg.C_2007);
			}
		}
		//优惠券描述限制位数
		if (StringUtils.isNotEmpty(coupondes)) {
			int dalen = coupondes.length();
			if (dalen>120) {
				return new ResultBean("2005",ResultBgMsg.C_2005);
			}
		}
		//商品分类id限制位数
		if (StringUtils.isNotEmpty(couponclassifyid)) {
			int dalen = couponclassifyid.length();
			if (dalen>20000) {
				return new ResultBean("2006",ResultBgMsg.C_2006);
			}
		}
		// 创建返回数据
		ResultBean result = new ResultBean();
		TokenModel tokens = redisTokenService.getToken(accesstoken);
		if (tokens != null) {
			String userid = tokens.getUserid();
			// 3.调库
			couponService.updateBgCoupon(userid,couponid,couponname, coupontype, couponendtime, couponapply, couponreducemoney, 
					couponstarttime,couponcondtion,coupondes,couponclassifyid, goodsid, storeid);
		}else {
			return new ResultBean("001",ResultBgMsg.C_001);
		}
		// 4.成功
		return result;
	}
	/**
	 * @Title: delBgCoupon
	 * @Description:  删除优惠券
	 * @param accesstoken 
	 * @param couponid 优惠券id
	 * @return
	 * @throws:
	 * @time: 2018年7月19日 下午2:33:16
	 */
	@SystemControllerLog(method = "delBgCoupon", logtype = ConstantIF.LOG_TYPE_1, description = "删除优惠券分类")
	@ApiOperation(value = "delBgCoupon", httpMethod = "POST", notes = "删除优惠券分类", response = ResultBean.class)
	@RequestMapping(value = "/delBgCoupon", method = RequestMethod.POST)
	public @ResponseBody ResultBean delBgCoupon(
			@ApiParam(value = "token值") @RequestParam(value = ConstantIF.ACCESSTOKEN) String accesstoken,
			@ApiParam(value = "优惠券id") @RequestParam(value = "couponid", required = true) String couponid
			) {
		// 创建返回数据
		ResultBean result = new ResultBean();
		TokenModel tokens = redisTokenService.getToken(accesstoken);
		if (tokens != null) {
			String userid = tokens.getUserid();
			// 3.调库
			couponService.delBgCoupon(userid,couponid);
		}else {
			return new ResultBean("001",ResultBgMsg.C_001);
		}
		// 4.成功
		return result;
	}
	
	/**
	 * @Title: getBgCouponDetail
	 * @Description: 获取优惠券详情
	 * @param accesstoken
	 * @param couponid 优惠券id
	 * @return
	 * @throws:
	 * @time: 2018年7月19日 下午3:30:53
	 */
	@SystemControllerLog(method = "getBgCouponDetail", logtype = ConstantIF.LOG_TYPE_1, description = "获取优惠券详情")
	@ApiOperation(value = "getBgCouponDetail", httpMethod = "GET", notes = "获取优惠券详情", response = ResultBean.class)
	@RequestMapping(value = "/getBgCouponDetail", method = RequestMethod.GET)
	public @ResponseBody ResultBean getBgCouponDetail(
			@ApiParam(value = "token值") @RequestParam(value = ConstantIF.ACCESSTOKEN) String accesstoken,
			@ApiParam(value = "优惠券名称") @RequestParam(value = "couponname", required = false) String couponname,
			@ApiParam(value = "优惠券类型(01:商家优惠券02：注册优惠券03：红包)") @RequestParam(value = "coupontype", required = false) String coupontype,
			@ApiParam(value = "Start(优惠券截止日期)") @RequestParam(value = "couponendtimestart", required = false) String couponendtimestart,
			@ApiParam(value = "End(优惠券截止日期)") @RequestParam(value = "couponendtimeend", required = false) String couponendtimeend,
			@ApiParam(value = "使用(01:应用02：停止应用)") @RequestParam(value = "couponapply", required = false) String couponapply,
			@ApiParam(value = "Start(优惠券优惠金额)") @RequestParam(value = "couponreducemoneystart", required = false) String couponreducemoneystart,
			@ApiParam(value = "End(优惠券优惠金额)") @RequestParam(value = "couponreducemoneyend", required = false) String couponreducemoneyend,
			@ApiParam(value = "Start(优惠券投放时间)") @RequestParam(value = "couponstarttimestart", required = false) String couponstarttimestart,
			@ApiParam(value = "End(优惠券投放时间)") @RequestParam(value = "couponstarttimeend", required = false) String couponstarttimeend,
			@ApiParam(value = "Start(创建时间)") @RequestParam(value = "createtimestart", required = false) String createtimestart,
			@ApiParam(value = "End(创建时间)") @RequestParam(value = "createtimeend", required = false) String createtimeend,
			@ApiParam(value = "Start(领取日期)") @RequestParam(value = "drawdatestart", required = false) String drawdatestart,
			@ApiParam(value = "End(领取日期)") @RequestParam(value = "drawdateend", required = false) String drawdateend,
			@ApiParam(value = "Start(使用日期)") @RequestParam(value = "useddatestart", required = false) String useddatestart,
			@ApiParam(value = "End(使用日期)") @RequestParam(value = "useddateend", required = false) String useddateend,
			@ApiParam(value = "Start(截止日期)") @RequestParam(value = "usedendtimestart", required = false) String usedendtimestart,
			@ApiParam(value = "End(截止日期)") @RequestParam(value = "usedendtimeend", required = false) String usedendtimeend,
			@ApiParam(value = "用户是否使用(默认未被使用) 0:未使用1:已使用") @RequestParam(value = "userused", required = false) String userused,
			@ApiParam(value = "用户昵称") @RequestParam(value = "nickName", required = false) String nickName,
			@ApiParam(value = "手机号") @RequestParam(value = "telno", required = false) String telno,
			@ApiParam(value="请求页") @RequestParam(value=ConstantIF.PAGE, defaultValue=ConstantIF.PAGE_START_STR) int page,
			@ApiParam(value="每页条数") @RequestParam(value=ConstantIF.PAGE_SIZE, defaultValue=ConstantIF.PAGE_OFFSET_STR) int pageSize
			) {
		// 创建返回数据
		ResultBean result = new ResultBean();
		TokenModel tokens = redisTokenService.getToken(accesstoken);
		if (tokens != null) {
			// 3.调库
			result = couponService.findBgCouponDetail(couponname,coupontype,couponendtimestart,couponendtimeend,couponapply,couponreducemoneystart,couponreducemoneyend,couponstarttimestart,couponstarttimeend,createtimestart, createtimeend,drawdatestart,drawdateend,useddatestart,useddateend,usedendtimestart,usedendtimeend,userused,nickName,telno,page, pageSize);
		}else {
			return new ResultBean("001",ResultBgMsg.C_001);
		}
		// 4.成功
		return result;
	}
	
	@SystemControllerLog(method = "grantCoupon", logtype = ConstantIF.LOG_TYPE_1, description = "发放优惠券")
	@ApiOperation(value = "grantCoupon", httpMethod = "POST", notes = "发放优惠券", response = ResultBean.class)
	@RequestMapping(value = "/grantCoupon", method = RequestMethod.POST)
	public @ResponseBody ResultBean grantCoupon(
			@ApiParam(value = "token值") @RequestParam(value = ConstantIF.ACCESSTOKEN) String accesstoken,
			@ApiParam(value = "优惠券id") @RequestParam(value = "couponid", required = true) String couponid,
			@ApiParam(value = "截止日期(例如:2018-08-11 23:59:59)") @RequestParam(value = "usedendtime", required = true) String usedendtime,
			@ApiParam(value="按用户名搜索") @RequestParam(value="name",required=false) String name,
			@ApiParam(value="按手机号搜索") @RequestParam(value="telno",required=false) String telno,
			@ApiParam(value="按来源搜索") @RequestParam(value="source",required=false) String source,
			@ApiParam(value="按开始时间") @RequestParam(value="starttime",required=false) String starttime,
			@ApiParam(value="按结束时间") @RequestParam(value="endtime",required=false) String endtime,
			@ApiParam(value="按角色") @RequestParam(value="role",required=false) String role,
			@ApiParam(value="按性别") @RequestParam(value="sex",required=false) String sex,
			@ApiParam(value="按区域") @RequestParam(value="areacode",required=false) String areacode
			) {
		// 创建返回数据
		ResultBean result = new ResultBean();
		TokenModel tokens = redisTokenService.getToken(accesstoken);
		if (tokens != null) {
			String userid = tokens.getUserid();
			// 3.调库
			result = couponService.addUCRelate(userid,couponid,usedendtime,name,telno,source,starttime,endtime,role,sex,areacode);
		}else {
			return new ResultBean("001",ResultBgMsg.C_001);
		}
		// 4.成功
		return result;
	}
}
