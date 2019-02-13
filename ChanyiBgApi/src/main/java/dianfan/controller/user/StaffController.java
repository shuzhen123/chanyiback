package dianfan.controller.user;

import java.util.List;

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
import dianfan.entities.user.StaffExtra;
import dianfan.models.ResultBean;
import dianfan.models.TokenModel;
import dianfan.service.impl.RedisTokenService;
import dianfan.service.usermanage.StaffService;
import dianfan.util.RegexUtils;

/**
 * @ClassName StaffController
 * @Description 员工管理
 * @author cjy
 * @date 2018年7月25日 上午9:30:27
 */
@Scope(WebApplicationContext.SCOPE_REQUEST)
@Controller
@RequestMapping(value = "/user/staff")
public class StaffController {
	@Autowired
	private RedisTokenService redisTokenService;
	@Autowired
	private StaffMapper staffMapper;
	@Autowired
	private StaffService staffService;
	
	@SystemControllerLog(method = "staffList", logtype = ConstantIF.LOG_TYPE_2, description = "易盟员工列表")
	@ApiOperation(value = "易盟员工列表", httpMethod = "GET", notes = "易盟员工列表", response = ResultBean.class)
	@RequestMapping(value = "/staffList", method = RequestMethod.GET)
	public @ResponseBody ResultBean staffList(
			@ApiParam(value="请求页") @RequestParam(value=ConstantIF.PAGE, defaultValue=ConstantIF.PAGE_START_STR) int page,
			@ApiParam(value="每页条数") @RequestParam(value=ConstantIF.PAGE_SIZE, defaultValue=ConstantIF.PAGE_OFFSET_STR) int pageSize,
			@ApiParam(value="按用户名搜索") @RequestParam(value="name",required=false) String name,
			@ApiParam(value="按手机号搜索") @RequestParam(value="telno",required=false) String telno,
			@ApiParam(value="按角色") @RequestParam(value="role",required=false) String role,
			@ApiParam(value="按区域") @RequestParam(value="areacode",required=false) String areacode,
			@ApiParam(value="按大区") @RequestParam(value="regionscode",required=false) String regionscode) {
		
		return staffService.findStaffList(page,pageSize,name,telno,role,areacode,regionscode);
	}
	
	/**
	 * @Title: staffDetail
	 * @Description: 易盟员工详情
	 * @param staffid 员工id
	 * @return
	 * @throws:
	 * @time: 2018年7月26日 下午1:43:01
	 * @author cjy
	 */
	@SystemControllerLog(method = "editStaff", logtype = ConstantIF.LOG_TYPE_2, description = "易盟员工详情")
	@ApiOperation(value = "易盟员工详情", httpMethod = "POST", notes = "易盟员工详情", response = ResultBean.class)
	@RequestMapping(value = "/staffDetail", method = RequestMethod.POST)
	public @ResponseBody ResultBean staffDetail(@ApiParam(value="员工id") @RequestParam(value="staffid") String staffid) {
		return staffService.getStaffDetail(staffid);
	}
	
	/**
	 * @Title: addStaff
	 * @Description: 添加易盟员工
	 * @return
	 * @throws:
	 * @time: 2018年7月26日 上午9:30:44
	 * @author cjy
	 */
	@SystemControllerLog(method = "addStaff", logtype = ConstantIF.LOG_TYPE_2, description = "添加易盟员工")
	@ApiOperation(value = "添加易盟员工", httpMethod = "POST", notes = "添加易盟员工", response = ResultBean.class)
	@RequestMapping(value = "/addStaff", method = RequestMethod.POST)
	public @ResponseBody ResultBean addStaff(
			@ApiParam(value="accesstoken") @RequestParam(value=ConstantIF.ACCESSTOKEN) String accesstoken,
			@ApiParam(value="角色id") @RequestParam(value="roleid") String roleid,
			
			@ApiParam(value="姓名") @RequestParam(value="realName") String realName,
			@ApiParam(value="性别(0未知，1男，2女)") @RequestParam(value="sex") String sex,
			@ApiParam(value="手机号码") @RequestParam(value="telno") String telno,
			@ApiParam(value="密码") @RequestParam(value="pwd") String pwd,
			@ApiParam(value="用户来源(01：小程序02：app 03 手机网站 04其他)") @RequestParam(value="source", required=false, defaultValue="04") String source,
			
			@ApiParam(value="省") @RequestParam(value="provCode", required=false) String provCode,
			@ApiParam(value="市") @RequestParam(value="cityCode", required=false) String cityCode,
			
			//大区经理
			@ApiParam(value="大区id") @RequestParam(value="regionid", required=false) String regionid,
			
			//城市经理（运营服务商）
			@ApiParam(value="身份证号码") @RequestParam(value="idcardNo", required=false) String idcardNo,
			@ApiParam(value="身份证有效期") @RequestParam(value="idcardValidDate", required=false) String idcardValidDate,
			@ApiParam(value="手持身份证照片") @RequestParam(value="handleIdcard", required=false) String handleIdcard,
			@ApiParam(value="身份证正面") @RequestParam(value="idcardFont", required=false) String idcardFont,
			@ApiParam(value="身份证反面") @RequestParam(value="idcardBack", required=false) String idcardBack,
			@ApiParam(value="合同地址/资料") @RequestParam(value="contractUrl", required=false) String contractUrl,
			
			//运营服务商
			@ApiParam(value="公司名称") @RequestParam(value="companyName", required=false) String companyName,
			@ApiParam(value="营业执照") @RequestParam(value="businessLicense", required=false) String businessLicense) {
		
		TokenModel token = redisTokenService.getToken(accesstoken);
		
		//用户基本信息
		UserInfo user = new UserInfo();
		if(StringUtils.isEmpty(roleid)) return new ResultBean("3007", "员工角色" + ResultBgMsg.C_3007);
		else user.setRoleid(roleid);
		if(StringUtils.isEmpty(realName)) return new ResultBean("3000", "员工姓名" + ResultBgMsg.C_3000);
		else user.setNickName(realName);
		if(StringUtils.isEmpty(sex)) return new ResultBean("3007", "员工性别" + ResultBgMsg.C_3007);
		else user.setSex(sex);
		
		if(StringUtils.isEmpty(telno)) return new ResultBean("3000", "手机号码" + ResultBgMsg.C_3000);
		else if(!RegexUtils.phoneRegex(telno)) return new ResultBean("002", ResultBgMsg.C_002);
		else user.setTelno(telno);
		//验证手机号码
		boolean bool = staffMapper.checkPhone(telno.trim(), null);
		if(bool) {
			//手机号码重复
			return new ResultBean("3001", "手机号码" + ResultBgMsg.C_3001);
		}
		
		if(StringUtils.isEmpty(pwd)) return new ResultBean("3000", "密码" + ResultBgMsg.C_3000);
		else user.setPwd(MD5Util.getMD5Format(pwd));
		user.setSource(source);
		user.setCreateBy(token.getUserid());
		//用户扩展信息
		StaffExtra se = new StaffExtra();
		se.setRealName(realName);
		se.setCreateBy(token.getUserid());
		//用户角色标记
		String role_tag;
		
		if(StringUtils.equals(roleid, "31aee8cd7db111e88dd352540054a904")) {
			//大区经理
			if(StringUtils.isEmpty(regionid.trim())) return new ResultBean("3007", "大区信息" + ResultBgMsg.C_3007);
			
			bool = staffMapper.checkRegionalManager(regionid, null);
			if(bool) return new ResultBean("3001", "大区经理" + ResultBgMsg.C_3001);
			
			role_tag = "01";
		}else if(StringUtils.equals(roleid, "806d7b177db111e88dd352540054a904")) {
			//运营服务商
			bool = staffMapper.checkCityManager("806d7b177db111e88dd352540054a904", cityCode, null);
			if(bool) return new ResultBean("3001", "运营服务商" + ResultBgMsg.C_3001);
			
			if(StringUtils.isEmpty(companyName)) return new ResultBean("3000", "公司名称" + ResultBgMsg.C_3000);
			else se.setCompanyName(companyName);
			if(StringUtils.isEmpty(businessLicense)) return new ResultBean("3007", "营业执照" + ResultBgMsg.C_3007);
			else se.setBusinessLicense(businessLicense.trim());
			
			role_tag = "02";
		}else if(StringUtils.equals(roleid, "807725ad7db111e88dd352540054a904")) {
			//城市经理
			bool = staffMapper.checkCityManager("806d7b177db111e88dd352540054a904", cityCode, null);
			if(bool) return new ResultBean("3001", "运营服务商" + ResultBgMsg.C_3001);
			bool = staffMapper.checkCityManager("807725ad7db111e88dd352540054a904", cityCode, null);
			if(bool) return new ResultBean("3001", "城市经理" + ResultBgMsg.C_3001);
			
			role_tag = "04";
		}else {
			//未知角色
			return new ResultBean("3009", ResultBgMsg.C_3009);
		}
		
		
		if(StringUtils.equals(roleid, "806d7b177db111e88dd352540054a904") || 
			StringUtils.equals(roleid, "807725ad7db111e88dd352540054a904")) {
			//运营服务商 && 城市经理
			
			if(StringUtils.isEmpty(idcardNo)) return new ResultBean("3000", "身份证号码" + ResultBgMsg.C_3000);
			else se.setIdcardNo(idcardNo.trim());
			if(StringUtils.isEmpty(idcardValidDate)) return new ResultBean("3000", "身份证有效期" + ResultBgMsg.C_3000);
			else se.setIdcardValidDate(idcardValidDate.trim());
			if(StringUtils.isEmpty(handleIdcard)) return new ResultBean("3007", "手持身份证照片" + ResultBgMsg.C_3007);
			else se.setHandleIdcard(handleIdcard.trim());
			if(StringUtils.isEmpty(idcardFont)) return new ResultBean("3007", "身份证正面照片" + ResultBgMsg.C_3007);
			else se.setIdcardFont(idcardFont.trim());
			if(StringUtils.isEmpty(idcardBack)) return new ResultBean("3007", "身份证反面照片" + ResultBgMsg.C_3007);
			else se.setIdcardBack(idcardBack.trim());
			if(StringUtils.isEmpty(contractUrl)) return new ResultBean("3007", "合同/资料" + ResultBgMsg.C_3007);
			else se.setContractUrl(contractUrl.trim());
			
			se.setAreaCode(cityCode);
			user.setAreaCode(cityCode);
		}
		staffService.addStaff(user, se, role_tag, regionid);
		return new ResultBean();
	}
	
	/**
	 * @Title: editStaff
	 * @Description: 修改易盟员工
	 * @return
	 * @throws:
	 * @time: 2018年7月26日 下午2:13:04
	 * @author cjy
	 */
	@SystemControllerLog(method = "editStaff", logtype = ConstantIF.LOG_TYPE_2, description = "修改易盟员工")
	@ApiOperation(value = "修改易盟员工", httpMethod = "POST", notes = "修改易盟员工", response = ResultBean.class)
	@RequestMapping(value = "/editStaff", method = RequestMethod.POST)
	public @ResponseBody ResultBean editStaff(
			@ApiParam(value="accesstoken") @RequestParam(value=ConstantIF.ACCESSTOKEN) String accesstoken,
			
			@ApiParam(value="员工id") @RequestParam(value="staffid") String staffid,
			@ApiParam(value="姓名") @RequestParam(value="realName") String realName,
			@ApiParam(value="性别(0未知，1男，2女)") @RequestParam(value="sex") String sex,
			@ApiParam(value="手机号码") @RequestParam(value="telno") String telno,
			
			@ApiParam(value="省") @RequestParam(value="provCode", required=false) String provCode,
			@ApiParam(value="市") @RequestParam(value="cityCode", required=false) String cityCode,
			
			//大区经理
			@ApiParam(value="大区id") @RequestParam(value="regionid", required=false) String regionid,
			
			//城市经理（运营服务商）
			@ApiParam(value="身份证号码") @RequestParam(value="idcardNo", required=false) String idcardNo,
			@ApiParam(value="身份证有效期") @RequestParam(value="idcardValidDate", required=false) String idcardValidDate,
			@ApiParam(value="手持身份证照片") @RequestParam(value="handleIdcard", required=false) String handleIdcard,
			@ApiParam(value="身份证正面") @RequestParam(value="idcardFont", required=false) String idcardFont,
			@ApiParam(value="身份证反面") @RequestParam(value="idcardBack", required=false) String idcardBack,
			@ApiParam(value="合同地址/资料") @RequestParam(value="contractUrl", required=false) String contractUrl,
			
			//运营服务商
			@ApiParam(value="公司名称") @RequestParam(value="companyName", required=false) String companyName,
			@ApiParam(value="营业执照") @RequestParam(value="businessLicense", required=false) String businessLicense) {
		TokenModel token = redisTokenService.getToken(accesstoken);
		FileUploadType fut = new FileUploadType();
		UserInfo user = new UserInfo();
		user.setId(staffid);
		if(StringUtils.isEmpty(realName)) return new ResultBean("3000", "员工姓名" + ResultBgMsg.C_3000);
		else user.setNickName(realName);
		if(StringUtils.isEmpty(sex)) return new ResultBean("3007", "员工性别" + ResultBgMsg.C_3007);
		else user.setSex(sex);
		
		if(StringUtils.isEmpty(telno)) return new ResultBean("3000", "手机号码" + ResultBgMsg.C_3000);
		else if(!RegexUtils.phoneRegex(telno)) return new ResultBean("002", ResultBgMsg.C_002);
		else user.setTelno(telno);
		//验证手机号码
		boolean bool = staffMapper.checkPhone(telno.trim(), staffid);
		//手机号码重复
		if(bool) return new ResultBean("3001", "手机号码" + ResultBgMsg.C_3001);
		user.setUpdateBy(token.getUserid());
		
		//用户扩展信息
		StaffExtra se = new StaffExtra();
		se.setRealName(realName);
		se.setUpdateBy(token.getUserid());
		
		//获取用户角色
		String roleid = staffMapper.getStaffRoleid(staffid);
		//用户角色标记
		String role_tag;
		if(StringUtils.equals(roleid, "807c477e7db111e88dd352540054a904")) {
			//体验店
			role_tag = "05";
		}else if(StringUtils.equals(roleid, "31aee8cd7db111e88dd352540054a904")) {
			//大区经理
			bool = staffMapper.checkRegionalManager(regionid, staffid);
			if(bool) return new ResultBean("3001", "大区经理" + ResultBgMsg.C_3001);
			
			role_tag = "01";
		}else if(StringUtils.equals(roleid, "806d7b177db111e88dd352540054a904")) {
			//运营服务商
			bool = staffMapper.checkCityManager("806d7b177db111e88dd352540054a904", cityCode, staffid);
			if(bool) return new ResultBean("3001", "运营服务商" + ResultBgMsg.C_3001);
			
			if(StringUtils.isEmpty(companyName)) return new ResultBean("3000", "公司名称" + ResultBgMsg.C_3000);
			else se.setCompanyName(companyName);
			if(StringUtils.isEmpty(businessLicense)) return new ResultBean("3007", "营业执照" + ResultBgMsg.C_3007);
			else se.setBusinessLicense(businessLicense.trim());
			
			role_tag = "02";
		}else if(StringUtils.equals(roleid, "8072924c7db111e88dd352540054a904") || StringUtils.equals(roleid, "807725ad7db111e88dd352540054a904")) {
			//市场开发经理 || 城市经理
			bool = staffMapper.checkCityManager("806d7b177db111e88dd352540054a904", cityCode, staffid);
			if(bool) return new ResultBean("3001", "运营服务商" + ResultBgMsg.C_3001);
			bool = staffMapper.checkCityManager("807725ad7db111e88dd352540054a904", cityCode, staffid);
			if(bool) return new ResultBean("3001", "城市经理" + ResultBgMsg.C_3001);
			
			role_tag = "04";
		}else {
			//未知角色类型
			return new ResultBean("3009", ResultBgMsg.C_3009);
		}
		
		if(StringUtils.equals(roleid, "806d7b177db111e88dd352540054a904") || 
			StringUtils.equals(roleid, "8072924c7db111e88dd352540054a904") ||
			StringUtils.equals(roleid, "807725ad7db111e88dd352540054a904")) {
			//运营服务商 && 城市经理 && 市场开发经理
			
			if(StringUtils.isEmpty(idcardNo)) return new ResultBean("3000", "身份证号码" + ResultBgMsg.C_3000);
			else se.setIdcardNo(idcardNo.trim());
			if(StringUtils.isEmpty(idcardValidDate)) return new ResultBean("3000", "身份证有效期" + ResultBgMsg.C_3000);
			else se.setIdcardValidDate(idcardValidDate.trim());
			if(StringUtils.isEmpty(handleIdcard)) return new ResultBean("3007", "手持身份证照片" + ResultBgMsg.C_3007);
			else {
				se.setHandleIdcard(handleIdcard.substring(handleIdcard.indexOf(fut.FILE_DIR, 0), handleIdcard.length()));
			}
			if(StringUtils.isEmpty(idcardFont)) return new ResultBean("3007", "身份证正面照片" + ResultBgMsg.C_3007);
			else {
				se.setIdcardFont(idcardFont.substring(idcardFont.indexOf(fut.FILE_DIR, 0), idcardFont.length()));
			}
			if(StringUtils.isEmpty(idcardBack)) return new ResultBean("3007", "身份证反面照片" + ResultBgMsg.C_3007);
			else {
				se.setIdcardBack(idcardBack.substring(idcardBack.indexOf(fut.FILE_DIR, 0), idcardBack.length()));
			}
			if(StringUtils.isEmpty(contractUrl)) return new ResultBean("3007", "合同/资料" + ResultBgMsg.C_3007);
			else {
				String[] urls = contractUrl.split(",");
				for(int i=0; i<urls.length; i++) {
					String url = urls[i];
					urls[i] = url.substring(url.indexOf(fut.FILE_DIR, 0), url.length());
				}
				se.setContractUrl(StringUtils.join(urls, ","));
			}
			
			se.setAreaCode(cityCode);
			user.setAreaCode(cityCode);
		}
		
		staffService.editStaff(user, se, role_tag, regionid);
		return new ResultBean();
	}
	
	/**
	 * @Title: freezeStaff
	 * @Description: 冻结/解冻 易盟员工
	 * @return
	 * @throws:
	 * @time: 2018年7月27日 上午9:42:23
	 * @author cjy
	 */
	@SystemControllerLog(method = "freezeStaff", logtype = ConstantIF.LOG_TYPE_2, description = "冻结/解冻 易盟员工")
	@ApiOperation(value = "冻结/解冻 易盟员工", httpMethod = "POST", notes = "冻结/解冻 易盟员工", response = ResultBean.class)
	@RequestMapping(value = "/freezeStaff", method = RequestMethod.POST)
	public @ResponseBody ResultBean freezeStaff(
			@ApiParam(value="accesstoken") @RequestParam(value=ConstantIF.ACCESSTOKEN) String accesstoken,
			@ApiParam(value="员工id") @RequestParam(value="staffid") String staffid,
			@ApiParam(value="动作（0解冻，1冻结）") @RequestParam(value="action") int action) {
		if(action != 0 && action != 1) return new ResultBean("3006", ResultBgMsg.C_3006);
		TokenModel token = redisTokenService.getToken(accesstoken);
		staffService.updateFreezeStaff(staffid, action, token.getUserid());
		return new ResultBean();
	}
	
	/**
	 * @Title: migrateStaffList
	 * @Description: 迁移员工列表
	 * @return
	 * @throws:
	 * @time: 2018年7月27日 下午4:37:14
	 * @author cjy
	 */
	@SystemControllerLog(method = "migrateStaffList", logtype = ConstantIF.LOG_TYPE_2, description = "迁移员工列表")
	@ApiOperation(value = "迁移员工列表", httpMethod = "GET", notes = "迁移员工列表", response = ResultBean.class)
	@RequestMapping(value = "/migrateStaffList", method = RequestMethod.GET)
	public @ResponseBody ResultBean migrateStaffList(
			@ApiParam(value="员工id") @RequestParam(value="staffid") String staffid,
			@ApiParam(value="角色id") @RequestParam(value="roleid") String roleid) {
		List<UserInfo> staffList = staffService.migrateStaffList(staffid, roleid);
		return new ResultBean(staffList);
	}
	
	/**
	 * @Title: migrateStaff
	 * @Description: 迁移员工
	 * @return
	 * @throws:
	 * @time: 2018年7月27日 上午10:12:30
	 * @author cjy
	 */
	@SystemControllerLog(method = "migrateStaff", logtype = ConstantIF.LOG_TYPE_2, description = "迁移员工")
	@ApiOperation(value = "迁移员工", httpMethod = "POST", notes = "迁移员工", response = ResultBean.class)
	@RequestMapping(value = "/migrateStaff", method = RequestMethod.POST)
	public @ResponseBody ResultBean migrateStaff(
			@ApiParam(value="accesstoken") @RequestParam(value=ConstantIF.ACCESSTOKEN) String accesstoken,
			@ApiParam(value="数据流入员工id") @RequestParam(value="inStaffid") String inStaffid,
			@ApiParam(value="数据流出员工id") @RequestParam(value="outStaffid") String outStaffid,
			@ApiParam(value="角色id") @RequestParam(value="roleid", required=false) String roleid,
			@ApiParam(value="迁移原因") @RequestParam(value="reason") String reason) {
		
		if(StringUtils.isEmpty(outStaffid)) return new ResultBean("501", ResultBgMsg.C_501);
		
		TokenModel token = redisTokenService.getToken(accesstoken);
		staffService.migrateStaff(inStaffid, outStaffid, roleid, reason, token.getUserid());
		return new ResultBean();
	}
}
