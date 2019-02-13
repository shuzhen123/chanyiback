package dianfan.controller.store;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;

import com.gexin.rp.sdk.base.uitls.MD5Util;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

import dianfan.annotations.SystemControllerLog;
import dianfan.constant.ConstantIF;
import dianfan.constant.ResultBgMsg;
import dianfan.dao.mapper.userManage.StaffMapper;
import dianfan.entities.FileUploadType;
import dianfan.entities.UserInfo;
import dianfan.entities.store.Store;
import dianfan.models.ResultBean;
import dianfan.models.TokenModel;
import dianfan.service.impl.RedisTokenService;
import dianfan.service.store.EasyRegimentStoreService;
import dianfan.util.RegexUtils;

/**
 * @ClassName StoreController
 * @Description 体验店管理
 * @author cjy
 * @date 2018年7月17日 下午2:08:03
 */
@Scope(WebApplicationContext.SCOPE_REQUEST)
@Controller
@RequestMapping(value = "/store")
public class StoreController {
	@Autowired
	private EasyRegimentStoreService easyRegimentStoreService;
	@Autowired
	private RedisTokenService redisTokenService;
	@Autowired
	private StaffMapper staffMapper;
	
	/**
	 * @Title: findStoreList
	 * @Description: 获取体验店列表
	 * @return
	 * @throws:
	 * @time: 2018年7月17日 下午2:06:52
	 */
	@SystemControllerLog(method = "findStoreList", logtype = ConstantIF.LOG_TYPE_2, description = "获取体验店列表")
	@ApiOperation(value = "获取体验店列表", httpMethod = "GET", notes = "获取v列表", response = ResultBean.class)
	@RequestMapping(value = "/storeList", method = RequestMethod.GET)
	public @ResponseBody ResultBean findStoreList(
			@ApiParam(value="请求起始页") @RequestParam(value=ConstantIF.PAGE, required=false, defaultValue=ConstantIF.PAGE_START_STR) int page,
			@ApiParam(value="每页请求条数") @RequestParam(value=ConstantIF.PAGE_SIZE, required=false, defaultValue=ConstantIF.PAGE_OFFSET_STR) int pageSize,
			
			@ApiParam(value="体验店名称") @RequestParam(value="applyName", required=false) String applyName,
			@ApiParam(value="手机号") @RequestParam(value="phone", required=false) String phone,
			@ApiParam(value="申请状态 01通过 02-未通过03待审核") @RequestParam(value="status", required=false) String status,
			
			@ApiParam(value="城市code(可为省、市、区县code)") @RequestParam(value="cityCode", required=false) String cityCode,
			
			@ApiParam(value="营业日起始") @RequestParam(value="weeklyStart", required=false) String weeklyStart,
			@ApiParam(value="营业日结束") @RequestParam(value="weeklyEnd", required=false) String weeklyEnd,
			
			@ApiParam(value="营业开始时间") @RequestParam(value="timeStart", required=false) String timeStart,
			@ApiParam(value="营业结束时间") @RequestParam(value="timeEnd", required=false) String timeEnd) {
		
		Store store = new Store();
		
		if(applyName != null && StringUtils.isNotEmpty(applyName.trim())) store.setApplyName(applyName.trim());
		if(phone != null && StringUtils.isNotEmpty(phone.trim())) store.setApplPhoneNum(phone.trim());
		if(status != null && StringUtils.isNotEmpty(status.trim())) store.setStatus(status.trim());
		if(cityCode != null && StringUtils.isNotEmpty(cityCode.trim())) store.setCityCode(cityCode.trim());
		if(weeklyStart != null && StringUtils.isNotEmpty(weeklyStart.trim())) store.setBusinessWeeklyStart(weeklyStart.trim());
		if(weeklyEnd != null && StringUtils.isNotEmpty(weeklyEnd.trim())) store.setBusinessWeeklyEnd(weeklyEnd.trim());
		if(timeStart != null && StringUtils.isNotEmpty(timeStart.trim())) store.setBusinessTimeStart(timeStart.trim());
		if(timeEnd != null && StringUtils.isNotEmpty(timeEnd.trim())) store.setBusinessTimeEnd(timeEnd.trim());
		
		return easyRegimentStoreService.findStoreList(page, pageSize, store);
	}
	
	/**
	 * @Title: storeDetail
	 * @Description: 体验店详情
	 * @param storeid 体验店id
	 * @return
	 * @throws:
	 * @time: 2018年7月24日 下午12:24:30
	 * @author cjy
	 */
	@SystemControllerLog(method = "storeDetail", logtype = ConstantIF.LOG_TYPE_2, description = "体验店详情")
	@ApiOperation(value = "体验店详情", httpMethod = "GET", notes = "体验店详情", response = ResultBean.class)
	@RequestMapping(value = "/storeDetail", method = RequestMethod.GET)
	public @ResponseBody ResultBean storeDetail(@ApiParam(value="体验店id") @RequestParam(value="storeid") String storeid) {
		return easyRegimentStoreService.getStoreDetail(storeid);
	}
	
	/**
	 * @Title: addStore
	 * @Description: 新增体验店
	 * @return
	 * @throws:
	 * @time: 2018年7月24日 上午10:09:18
	 * @author cjy
	 */
	@SystemControllerLog(method = "addStore", logtype = ConstantIF.LOG_TYPE_2, description = "新增体验店")
	@ApiOperation(value = "新增体验店", httpMethod = "POST", notes = "新增体验店", response = ResultBean.class)
	@RequestMapping(value = "/addStore", method = RequestMethod.POST)
	public @ResponseBody ResultBean addStore(
			@ApiParam(value="accesstoken") @RequestParam(value=ConstantIF.ACCESSTOKEN) String accesstoken,
			@ApiParam(value="体验店名称") @RequestParam(value="applyName") String applyName,
			@ApiParam(value="城市code") @RequestParam(value="cityCode") String cityCode,
			@ApiParam(value="手机号") @RequestParam(value="applyPhoneNum", required=false) String applyPhoneNum,
			@ApiParam(value="体验店地址") @RequestParam(value="applyAddr") String applyAddr,
			@ApiParam(value="体验店面积") @RequestParam(value="area") String area,
			@ApiParam(value="门头url") @RequestParam(value="doorheadUrl") String doorheadUrl,
			@ApiParam(value="店内url00") @RequestParam(value="innerUrl00", required=false) String innerUrl00,
			@ApiParam(value="店内url01") @RequestParam(value="innerUrl01", required=false) String innerUrl01,
			@ApiParam(value="营业执照地址") @RequestParam(value="businessLicenceUrl", required=false) String businessLicenceUrl,
			@ApiParam(value="营业日起始") @RequestParam(value="businessWeeklyStart") String businessWeeklyStart,
			@ApiParam(value="营业日结束") @RequestParam(value="businessWeeklyEnd") String businessWeeklyEnd,
			@ApiParam(value="营业结束时间") @RequestParam(value="businessTimeEnd") String businessTimeEnd,
			@ApiParam(value="营业开始时间") @RequestParam(value="businessTimeStart") String businessTimeStart,
//			@ApiParam(value="手持身份证照片") @RequestParam(value="handleIdcard", required=false) String handleIdcard,
			@ApiParam(value="申请店主营业务") @RequestParam(value="applyCurrentBusiness", required=false) String applyCurrentBusiness,
			@ApiParam(value="体验店分类id（多id之间使用英文','分隔）") @RequestParam(value="applyClassifyIds") String applyClassifyIds,
			
			@ApiParam(value="体验店申请人id") @RequestParam(value="applyUserId", required=false) String applyUserId,
			
			@ApiParam(value="姓名") @RequestParam(value="nickName", required=false) String nickName,
			@ApiParam(value="性别(0未知，1男，2女)") @RequestParam(value="sex", required=false) String sex,
			@ApiParam(value="手机号码") @RequestParam(value="telno", required=false) String telno,
			@ApiParam(value="密码") @RequestParam(value="pwd", required=false) String pwd,
			@ApiParam(value="用户来源(01：小程序02：app 03 手机网站 04其他)") @RequestParam(value="source", required=false, defaultValue="04") String source
			
			) {
		TokenModel token = redisTokenService.getToken(accesstoken);
		Store store = new Store();
		FileUploadType fut = new FileUploadType();
		UserInfo user = null;
		if(applyUserId == null || StringUtils.isEmpty(applyUserId.trim())) {
			//新增体验店和体验店用户
			//用户基本信息
			user = new UserInfo();
			if(StringUtils.isEmpty(nickName)) return new ResultBean("3000", "员工姓名" + ResultBgMsg.C_3000);
			else user.setNickName(nickName);
			if(StringUtils.isEmpty(sex)) return new ResultBean("3007", "员工性别" + ResultBgMsg.C_3007);
			else user.setSex(sex);
			if(StringUtils.isEmpty(telno)) return new ResultBean("3000", "手机号码" + ResultBgMsg.C_3000);
			else if(!RegexUtils.phoneRegex(telno)) return new ResultBean("002", ResultBgMsg.C_002);
			else user.setTelno(telno);
			//验证手机号码
			boolean bool = staffMapper.checkPhone(telno.trim(), null);
			if(bool) return new ResultBean("3001", "手机号码" + ResultBgMsg.C_3001);
			
			if(StringUtils.isEmpty(pwd)) return new ResultBean("3000", "密码" + ResultBgMsg.C_3000);
			else user.setPwd(MD5Util.getMD5Format(pwd));
			user.setAreaCode(cityCode);
			user.setSource(source);
			user.setCreateBy(token.getUserid());
		} else store.setApplyUserid(applyUserId.trim());
		
		if(applyName == null || StringUtils.isEmpty(applyName.trim())) return new ResultBean("3000", "体验店名称" + ResultBgMsg.C_3000);
		else store.setApplyName(applyName.trim());
		
		if(cityCode == null || StringUtils.isEmpty(cityCode.trim())) return new ResultBean("3007", "体验店所在城市" + ResultBgMsg.C_3007);
		else store.setCityCode(cityCode.trim());
		
		if(applyPhoneNum != null && StringUtils.isNotEmpty(applyPhoneNum.trim())) store.setApplPhoneNum(applyPhoneNum.trim());
		
		if(applyAddr == null || StringUtils.isEmpty(applyAddr.trim())) return new ResultBean("3000", "体验店地址" + ResultBgMsg.C_3000);
		else store.setApplyAddr(applyAddr.trim());
		
		if(area == null || StringUtils.isEmpty(area.trim())) return new ResultBean("3000", "体验店面积" + ResultBgMsg.C_3000);
		else store.setArea(area.trim());
		
		if(doorheadUrl == null || StringUtils.isEmpty(doorheadUrl.trim())) return new ResultBean("3007", "体验店门头图片" + ResultBgMsg.C_3007);
		else {
			store.setDoorheadUrl(doorheadUrl.substring(doorheadUrl.indexOf(fut.FILE_DIR, 0)));
		}
		
		if(innerUrl00 != null && StringUtils.isNotEmpty(innerUrl00.trim())) store.setInnerUrl00(innerUrl00.substring(innerUrl00.indexOf(fut.FILE_DIR, 0)));
		if(innerUrl01 != null && StringUtils.isNotEmpty(innerUrl01.trim())) store.setInnerUrl01(innerUrl01.substring(innerUrl01.indexOf(fut.FILE_DIR, 0)));
		if(businessLicenceUrl != null && StringUtils.isNotEmpty(businessLicenceUrl.trim())) store.setBusinessLicenceUrl(businessLicenceUrl.substring(businessLicenceUrl.indexOf(fut.FILE_DIR, 0)));
		
		if(businessWeeklyStart == null || StringUtils.isEmpty(businessWeeklyStart.trim())) return new ResultBean("3000", "体验店营业起始日" + ResultBgMsg.C_3000);
		else store.setBusinessWeeklyStart(businessWeeklyStart.trim());
		if(businessWeeklyEnd == null || StringUtils.isEmpty(businessWeeklyEnd.trim())) return new ResultBean("3000", "体验店营业结束日" + ResultBgMsg.C_3000);
		else store.setBusinessWeeklyEnd(businessWeeklyEnd.trim());
		
		if(businessTimeEnd == null || StringUtils.isEmpty(businessTimeEnd.trim())) return new ResultBean("3000", "体验店营业结束时间" + ResultBgMsg.C_3000);
		else store.setBusinessTimeEnd(businessTimeEnd.trim());
		if(businessTimeStart == null || StringUtils.isEmpty(businessTimeStart.trim())) return new ResultBean("3000", "体验店营业开始时间" + ResultBgMsg.C_3000);
		else store.setBusinessTimeStart(businessTimeStart.trim());
		
//		if(handleIdcard != null && StringUtils.isNotEmpty(handleIdcard.trim())) store.setHandleIdcard(handleIdcard.trim());
		
		if(applyCurrentBusiness != null && StringUtils.isNotEmpty(applyCurrentBusiness.trim())) store.setApplyCurrentBusiness(applyCurrentBusiness.trim());
		
		if(applyClassifyIds == null || StringUtils.isEmpty(applyClassifyIds.trim())) return new ResultBean("3007", "体验店分类" + ResultBgMsg.C_3007);
		
		store.setCreateBy(token.getUserid());
		
		return easyRegimentStoreService.addStore(store, applyClassifyIds.split(","), user);
	}
	
	/**
	 * @Title: editStore
	 * @Description: 编辑体验店
	 * @return
	 * @throws:
	 * @time: 2018年7月24日 下午3:03:07
	 * @author cjy
	 */
	@SystemControllerLog(method = "editStore", logtype = ConstantIF.LOG_TYPE_2, description = "编辑体验店")
	@ApiOperation(value = "编辑体验店", httpMethod = "POST", notes = "编辑体验店", response = ResultBean.class)
	@RequestMapping(value = "/editStore", method = RequestMethod.POST)
	public @ResponseBody ResultBean editStore(
			@ApiParam(value="accesstoken") @RequestParam(value=ConstantIF.ACCESSTOKEN) String accesstoken,
			@ApiParam(value="体验店id") @RequestParam(value="storeid") String storeid,
			@ApiParam(value="体验店名称") @RequestParam(value="applyName") String applyName,
			@ApiParam(value="城市code") @RequestParam(value="cityCode") String cityCode,
			@ApiParam(value="手机号") @RequestParam(value="applyPhoneNum", required=false) String applyPhoneNum,
			@ApiParam(value="体验店地址") @RequestParam(value="applyAddr") String applyAddr,
			@ApiParam(value="体验店面积") @RequestParam(value="area") String area,
			@ApiParam(value="门头url") @RequestParam(value="doorheadUrl") String doorheadUrl,
			@ApiParam(value="店内url00") @RequestParam(value="innerUrl00", required=false) String innerUrl00,
			@ApiParam(value="店内url01") @RequestParam(value="innerUrl01", required=false) String innerUrl01,
			@ApiParam(value="营业执照地址") @RequestParam(value="businessLicenceUrl", required=false) String businessLicenceUrl,
			@ApiParam(value="营业日起始") @RequestParam(value="businessWeeklyStart") String businessWeeklyStart,
			@ApiParam(value="营业日结束") @RequestParam(value="businessWeeklyEnd") String businessWeeklyEnd,
			@ApiParam(value="营业结束时间") @RequestParam(value="businessTimeEnd") String businessTimeEnd,
			@ApiParam(value="营业开始时间") @RequestParam(value="businessTimeStart") String businessTimeStart,
			@ApiParam(value="申请店主营业务") @RequestParam(value="applyCurrentBusiness", required=false) String applyCurrentBusiness,
			@ApiParam(value="体验店分类id（多id之间使用英文','分隔）") @RequestParam(value="applyClassifyIds") String applyClassifyIds
			) {
		FileUploadType fut = new FileUploadType();
		Store store = new Store();
		store.setId(storeid);
		
		if(applyName == null || StringUtils.isEmpty(applyName.trim())) return new ResultBean("3000", "体验店名称" + ResultBgMsg.C_3000);
		else store.setApplyName(applyName.trim());
		
		if(cityCode == null || StringUtils.isEmpty(cityCode.trim())) return new ResultBean("3007", "体验店所在城市" + ResultBgMsg.C_3007);
		else store.setCityCode(cityCode.trim());
		
		if(applyPhoneNum != null && StringUtils.isNotEmpty(applyPhoneNum.trim())) store.setApplPhoneNum(applyPhoneNum.trim());
		
		if(applyAddr == null || StringUtils.isEmpty(applyAddr.trim())) return new ResultBean("3000", "体验店地址" + ResultBgMsg.C_3000);
		else store.setApplyAddr(applyAddr.trim());
		
		if(area == null || StringUtils.isEmpty(area.trim())) return new ResultBean("3000", "体验店面积" + ResultBgMsg.C_3000);
		else store.setArea(area.trim());
		
		if(doorheadUrl == null || StringUtils.isEmpty(doorheadUrl.trim())) return new ResultBean("3007", "体验店门头图片" + ResultBgMsg.C_3007);
		else store.setDoorheadUrl(doorheadUrl.substring(doorheadUrl.indexOf(fut.FILE_DIR, 0)==-1?0:doorheadUrl.indexOf(fut.FILE_DIR, 0)));
		
		if(innerUrl00 != null && StringUtils.isNotEmpty(innerUrl00.trim())) store.setInnerUrl00(innerUrl00.substring(innerUrl00.indexOf(fut.FILE_DIR, 0)==-1?0:innerUrl00.indexOf(fut.FILE_DIR, 0)));
		if(innerUrl01 != null && StringUtils.isNotEmpty(innerUrl01.trim())) store.setInnerUrl01(innerUrl01.substring(innerUrl01.indexOf(fut.FILE_DIR, 0)==-1?0:innerUrl01.indexOf(fut.FILE_DIR, 0)));
		if(businessLicenceUrl != null && StringUtils.isNotEmpty(businessLicenceUrl.trim())) store.setBusinessLicenceUrl(businessLicenceUrl.substring(businessLicenceUrl.indexOf(fut.FILE_DIR, 0)==-1?0:businessLicenceUrl.indexOf(fut.FILE_DIR, 0)));
		
		if(businessWeeklyStart == null || StringUtils.isEmpty(businessWeeklyStart.trim())) return new ResultBean("3000", "体验店营业起始日" + ResultBgMsg.C_3000);
		else store.setBusinessWeeklyStart(businessWeeklyStart.trim());
		if(businessWeeklyEnd == null || StringUtils.isEmpty(businessWeeklyEnd.trim())) return new ResultBean("3000", "体验店营业结束日" + ResultBgMsg.C_3000);
		else store.setBusinessWeeklyEnd(businessWeeklyEnd.trim());
		
		if(businessTimeEnd == null || StringUtils.isEmpty(businessTimeEnd.trim())) return new ResultBean("3000", "体验店营业结束时间" + ResultBgMsg.C_3000);
		else store.setBusinessTimeEnd(businessTimeEnd.trim());
		if(businessTimeStart == null || StringUtils.isEmpty(businessTimeStart.trim())) return new ResultBean("3000", "体验店营业开始时间" + ResultBgMsg.C_3000);
		else store.setBusinessTimeStart(businessTimeStart.trim());
		
		if(applyCurrentBusiness != null || StringUtils.isNotEmpty(applyCurrentBusiness.trim())) store.setApplyCurrentBusiness(applyCurrentBusiness.trim());
		
		if(applyClassifyIds == null || StringUtils.isEmpty(applyClassifyIds.trim())) return new ResultBean("3007", "体验店分类" + ResultBgMsg.C_3007);
		
		TokenModel token = redisTokenService.getToken(accesstoken);
		store.setUpdateBy(token.getUserid());
		
		return easyRegimentStoreService.editStore(store, applyClassifyIds.split(","));
	}
	
	/**
	 * @Title: startStopStore
	 * @Description: 禁用/启用 体验店
	 * @return
	 * @throws:
	 * @time: 2018年7月28日 下午12:08:47
	 * @author cjy
	 */
	@SystemControllerLog(method = "startStopStore", logtype = ConstantIF.LOG_TYPE_2, description = "禁用/启用 体验店")
	@ApiOperation(value = "禁用/启用 体验店", httpMethod = "POST", notes = "禁用/启用 体验店", response = ResultBean.class)
	@RequestMapping(value = "/startStopStore", method = RequestMethod.POST)
	public @ResponseBody ResultBean startStopStore(
			@ApiParam(value="accesstoken") @RequestParam(value=ConstantIF.ACCESSTOKEN) String accesstoken,
			@ApiParam(value="体验店id列表（id之间使用英文’,’分隔）") @RequestParam(value="storeids") String storeids,
			@ApiParam(value="动作（0启用，1禁用）") @RequestParam(value="action") int action) {
		if(storeids == null || StringUtils.isEmpty(storeids.trim())) return new ResultBean("501", ResultBgMsg.C_501);
		if(action != 0 && action != 1) return new ResultBean("3006", ResultBgMsg.C_3006);
		TokenModel token = redisTokenService.getToken(accesstoken);
		easyRegimentStoreService.startStopStore(storeids.split(","), action, token.getUserid());
		return new ResultBean();
	}
	
}
