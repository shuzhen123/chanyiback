/**  
* @Title: FactoryController.java
* @Package dianfan.controller.factory
* @Description: TODO
* @author yl
* @date 2018年7月17日 下午12:48:51
* @version V1.0  
*/ 
package dianfan.controller.factory;

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
import dianfan.service.factory.FactoryService;
import dianfan.service.impl.RedisTokenService;

/** @ClassName FactoryController
 * @Description 
 * @author yl
 * @date 2018年7月17日 下午12:48:51
 */
@Scope("request")
@Controller
@RequestMapping(value = "/factory")
public class FactoryController {
	
	@Autowired
	private FactoryService factoryService;
	/**
	 * 注入： #RedisTokenService
	 */
	@Autowired
	private RedisTokenService redisTokenService;
	
	@SystemControllerLog(method = "findFactoryList", logtype = ConstantIF.LOG_TYPE_1, description = "获取工厂列表")
	@ApiOperation(value = "findFactoryList", httpMethod = "GET", notes = "获取工厂列表", response = ResultBean.class)
	@RequestMapping(value = "/findFactoryList", method = RequestMethod.GET)
	public @ResponseBody ResultBean findFactoryList(
			@ApiParam(value = "token值") @RequestParam(value = ConstantIF.ACCESSTOKEN) String accesstoken,
			@ApiParam(value = "区域code") @RequestParam(value = "areacode", required = false) String areacode,
			@ApiParam(value="请求页") @RequestParam(value=ConstantIF.PAGE, defaultValue=ConstantIF.PAGE_START_STR) int page,
			@ApiParam(value="每页条数") @RequestParam(value=ConstantIF.PAGE_SIZE, defaultValue=ConstantIF.PAGE_OFFSET_STR) int pageSize
			) {
		      // 创建返回数据
				ResultBean result = factoryService.findFactoryList(areacode,page,pageSize);
				// 4.成功
				return result;
		 }
	
	/**
	 * @Title: addFactory
	 * @Description: 添加工厂
	 * @param accesstoken token值
	 * @param factoryname 工厂名称
	 * @param adminid 工厂账号
	 * @param longitude 经度
	 * @param latitude 纬度
	 * @param factoryaddr 地址
	 * @param areacode 区域code
	 * @return
	 * @throws:
	 * @time: 2018年7月17日 下午4:33:20
	 */
	@SystemControllerLog(method = "addFactory", logtype = ConstantIF.LOG_TYPE_1, description = "添加工厂")
	@ApiOperation(value = "addFactory", httpMethod = "POST", notes = "添加工厂", response = ResultBean.class)
	@RequestMapping(value = "/addFactory", method = RequestMethod.POST)
	public @ResponseBody ResultBean addFactory(
			@ApiParam(value = "token值") @RequestParam(value = ConstantIF.ACCESSTOKEN) String accesstoken,
			@ApiParam(value = "工厂名称") @RequestParam(value = "factoryname", required = false) String factoryname,
			@ApiParam(value = "工厂地址") @RequestParam(value = "factoryaddr",required = false) String factoryaddr,
			@ApiParam(value = "区域code") @RequestParam(value = "areacode",required = false) String areacode,
			@ApiParam(value = "管理员id列表") @RequestParam(value = "adminids",required = false) String adminids
			) {
		TokenModel tokens = redisTokenService.getToken(accesstoken);
		ResultBean rb = new ResultBean();
		if (tokens != null) {
			String userid = tokens.getUserid();
			// 创建返回数据
			rb = factoryService.addFactory(userid,factoryname, factoryaddr, areacode, adminids);
			// 4.成功
		}else {
			return new ResultBean("001",ResultBgMsg.C_001);
		}
		return rb;
	}
	/**
	 * @Title: updateFactory
	 * @Description: 修改工厂
	 * @param accesstoken token值
	 * @param factoryid 工厂id
	 * @param factoryname 工厂名称
	 * @param adminid 工厂账号
	 * @param longitude 经度
	 * @param latitude 纬度
	 * @param factoryaddr 地址
	 * @param areacode 区域code
	 * @return
	 * @throws:
	 * @time: 2018年7月17日 下午4:33:20
	 */
	@SystemControllerLog(method = "updateFactory", logtype = ConstantIF.LOG_TYPE_1, description = "修改工厂")
	@ApiOperation(value = "updateFactory", httpMethod = "POST", notes = "修改工厂", response = ResultBean.class)
	@RequestMapping(value = "/updateFactory", method = RequestMethod.POST)
	public @ResponseBody ResultBean updateFactory(
			@ApiParam(value = "token值") @RequestParam(value = ConstantIF.ACCESSTOKEN) String accesstoken,
			@ApiParam(value = "工厂id") @RequestParam(value = "factoryid", required = true) String factoryid,
			@ApiParam(value = "工厂名称") @RequestParam(value = "factoryname", required = false) String factoryname,
			@ApiParam(value = "工厂账号") @RequestParam(value = "adminid", required = false) String adminid,
			@ApiParam(value = "工厂地址") @RequestParam(value = "factoryaddr",required = false) String factoryaddr,
			@ApiParam(value = "区域code") @RequestParam(value = "areacode",required = false) String areacode,
			@ApiParam(value = "管理员id列表") @RequestParam(value = "adminids",required = false) String adminids
			) {
		ResultBean rb = new ResultBean();
		TokenModel tokens = redisTokenService.getToken(accesstoken);
		if (tokens != null) {
			String userid = tokens.getUserid();
			// 创建返回数据
			rb = factoryService.updateFactory(userid,factoryid,factoryname,adminid,factoryaddr, areacode, adminids);
			// 4.成功
		}else {
			return new ResultBean("001",ResultBgMsg.C_001);
		}
		return rb;
	}
	/**
	 * @Title: delFactory
	 * @Description:  删除工厂
	 * @param accesstoken 
	 * @param factoryid 工厂id
	 * @return
	 * @throws:
	 * @time: 2018年7月19日 上午10:41:24
	 */
	@SystemControllerLog(method = "delFactory", logtype = ConstantIF.LOG_TYPE_1, description = "删除工厂")
	@ApiOperation(value = "delFactory", httpMethod = "POST", notes = "删除工厂", response = ResultBean.class)
	@RequestMapping(value = "/delFactory", method = RequestMethod.POST)
	public @ResponseBody ResultBean delFactory(
			@ApiParam(value = "token值") @RequestParam(value = ConstantIF.ACCESSTOKEN) String accesstoken,
			@ApiParam(value = "工厂id") @RequestParam(value = "factoryid", required = true) String factoryid
			) {
		TokenModel tokens = redisTokenService.getToken(accesstoken);
		if (tokens != null) {
			String userid = tokens.getUserid();
			// 创建返回数据
			factoryService.delFactory(userid, factoryid);
			// 4.成功
		}else {
			return new ResultBean("001",ResultBgMsg.C_001);
		}
		return new ResultBean();
	}

}
