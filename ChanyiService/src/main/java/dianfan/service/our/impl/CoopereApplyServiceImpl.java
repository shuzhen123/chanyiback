/**  
* @Title: CoopereApplyServiceImpl.java
* @Package dianfan.service.our.impl
* @Description: TODO
* @author yl
* @date 2018年6月29日 下午3:08:16
* @version V1.0  
*/ 
package dianfan.service.our.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dianfan.constant.ConstantIF;
import dianfan.constant.ResultBgMsg;
import dianfan.dao.mapper.our.CityManagerApplyMapper;
import dianfan.dao.mapper.our.CoopereApplyMapper;
import dianfan.dao.mapper.userManage.StaffMapper;
import dianfan.entities.our.CooperationApply;
import dianfan.entities.role.Role;
import dianfan.entities.role.UserRole;
import dianfan.entities.user.LowerUpperRelate;
import dianfan.entities.user.StaffExtra;
import dianfan.models.ResultBean;
import dianfan.service.our.CoopereApplyService;
import dianfan.util.PropertyUtil;
import dianfan.util.UUIDUtil;

/** @ClassName CoopereApplyServiceImpl
 * @Description 
 * @author yl
 * @date 2018年6月29日 下午3:08:16
 */
@Service
public class CoopereApplyServiceImpl implements CoopereApplyService{
	
	@Autowired
	private CityManagerApplyMapper cityManagerApplyMapper;
	@Autowired
	private CoopereApplyMapper coopereApplyMapper;
	@Autowired
	private StaffMapper staffMapper;

	/* (non-Javadoc)
	 * <p>Title: addCoopereApply</p>
	 * <p>Description: </p>
	 * @param userid 用户id
	 * @param applyname 申请人姓名
	 * @param applyphonenum 手机号
	 * @param applyaddr 地址
	 * link: @see dianfan.service.our.CoopereApplyService#addCoopereApply(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */ 
	@Override
	@Transactional
	public ResultBean addCoopereApply(String userid, String applyname, String applyphonenum, String applyaddr,String realname,String idcardno,String idcardfont,String idcardback,String idcardvaliddate,String handleidcard,String status,String cityCode,String businessLicense,String companyName) {
		// TODO Auto-generated method stub
		Map<String, Object> param = new HashMap<String, Object>();
		Boolean cooperebool = cityManagerApplyMapper.checkCityManager("806d7b177db111e88dd352540054a904", cityCode, null);
		Boolean citymanagerbool = cityManagerApplyMapper.checkCityManager("807725ad7db111e88dd352540054a904", cityCode, null);
		if(cooperebool) return new ResultBean("3001", "运营服务商" + ResultBgMsg.C_3001);
		if(citymanagerbool) return new ResultBean("3001", "城市经理" + ResultBgMsg.C_3001);
		CooperationApply ca = new CooperationApply();
		//UserRole ur = new UserRole();
		ca.setUserId(userid);
		ca.setApplyAddr(applyaddr);
		ca.setApplyName(applyname);
		ca.setStatus(status);
		ca.setApplyPhoneNum(applyphonenum);
		ca.setRealName(realname);
		ca.setIdcardNo(idcardno);
		int indexOf = idcardfont.indexOf("upload");
		String idcardfonts = idcardfont.substring(indexOf);
		ca.setIdcardFont(idcardfonts);
		int indexOf1 = idcardback.indexOf("upload");
		String idcardbacks = idcardback.substring(indexOf1);
		ca.setIdcardBack(idcardbacks);
		ca.setIdcardValidDate(idcardvaliddate);
		int indexOf2 = handleidcard.indexOf("upload");
		String handleidcards = handleidcard.substring(indexOf2);
		ca.setHandleIdcard(handleidcards);
		ca.setCreateBy(userid);
		/*Role r = coopereApplyMapper.getUserRole(roledistinguish);
		ur.setUserid(userid);
		ur.setRoleid(r.getId());
		ur.setDescption(r.getRoleName());
		coopereApplyMapper.updateCooperationApplyRole(ur);*/
		coopereApplyMapper.addCoopereApply(ca);
		//添加用户资料补充表
		StaffExtra setra = new StaffExtra();
		String uuids = UUIDUtil.getUUID();
		setra.setId(uuids);
		setra.setAreaCode(cityCode);
		setra.setCompanyName(companyName);
		setra.setBusinessLicense(businessLicense);
		setra.setCreateBy(userid);
		setra.setIdcardBack(idcardback);
		setra.setIdcardFont(idcardfont);
		setra.setIdcardNo(idcardno);
		setra.setIdcardValidDate(idcardvaliddate);
		setra.setRealName(realname);
		setra.setHandleIdcard(handleidcard);
		staffMapper.addStaffExtra(setra);
		//添加用户与用户资料的关系
		int userversion = coopereApplyMapper.getUserVersionInfo(userid);
		param.put("id", userid);
		param.put("extraid", uuids);
		param.put("version", userversion);
		param.put("userid", userid);
		coopereApplyMapper.updataUserExtraId(param);
		return new ResultBean();
	}

	/* (non-Javadoc)
	 * <p>Title: getCooperationApply</p>
	 * <p>Description: </p>
	 * @param userid
	 * @return
	 * link: @see dianfan.service.our.CoopereApplyService#getCooperationApply(java.lang.String)
	 */ 
	@Override
	public int getCooperationApply(String userid) {
		// TODO Auto-generated method stub
		int coopereStatus = coopereApplyMapper.getCooperationApply(userid);
		return coopereStatus;
	}

	/* (non-Javadoc)
	 * <p>Title: getCooperationApplyByPhone</p>
	 * <p>Description: 验证手机号是否存在</p>
	 * @param phonenum 手机号码
	 * @return
	 * link: @see dianfan.service.our.CoopereApplyService#getCooperationApplyByPhone(java.lang.String)
	 */ 
	@Override
	public int getCooperationApplyByPhone(String phonenum) {
		// TODO Auto-generated method stub
		return coopereApplyMapper.getCooperationApplyByPhone(phonenum);
	}

	/* (non-Javadoc)
	 * <p>Title: updateCoopereApply</p>
	 * <p>Description: </p>
	 * @param userid
	 * @param applyname
	 * @param applyphonenum
	 * @param applyaddr
	 * @param realname
	 * @param idcardno
	 * @param idcardfont
	 * @param idcardback
	 * @param idcardvaliddate
	 * @param handleidcard
	 * link: @see dianfan.service.our.CoopereApplyService#updateCoopereApply(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */ 
	@Override
	@Transactional
	public void updateCoopereApply(String userid, String applyname, String applyphonenum, String applyaddr,
			String realname, String idcardno, String idcardfont, String idcardback, String idcardvaliddate,
			String handleidcard,String cityCode,String businessLicense,String companyName) {
		// TODO Auto-generated method stub
		int versionInfo = coopereApplyMapper.getVersionInfo(userid);
		CooperationApply ca = new CooperationApply();
		//UserRole ur = new UserRole();
		ca.setUserId(userid);
		ca.setApplyAddr(applyaddr);
		ca.setApplyName(applyname);
		ca.setApplyPhoneNum(applyphonenum);
		ca.setRealName(realname);
		ca.setIdcardNo(idcardno);
		ca.setIdcardValidDate(idcardvaliddate);
		int indexOf = idcardfont.indexOf("upload");
		String idcardfonts = idcardfont.substring(indexOf);
		ca.setIdcardFont(idcardfonts);
		int indexOf1 = idcardback.indexOf("upload");
		String idcardbacks = idcardback.substring(indexOf1);
		ca.setIdcardBack(idcardbacks);
		ca.setIdcardValidDate(idcardvaliddate);
		int indexOf2 = handleidcard.indexOf("upload");
		String handleidcards = handleidcard.substring(indexOf2);
		ca.setHandleIdcard(handleidcards);
		ca.setUpdateBy(userid);
		ca.setVersion(versionInfo);
		coopereApplyMapper.updateCoopereApply(ca);
		String extraid = coopereApplyMapper.getExtraId(userid);
		//添加用户资料补充表
		StaffExtra setra = new StaffExtra();
		setra.setId(extraid);
		setra.setAreaCode(cityCode);
		setra.setCompanyName(companyName);
		setra.setBusinessLicense(businessLicense);
		setra.setIdcardBack(idcardback);
		setra.setIdcardFont(idcardfont);
		setra.setIdcardNo(idcardno);
		setra.setIdcardValidDate(idcardvaliddate);
		setra.setRealName(realname);
		setra.setHandleIdcard(handleidcard);
		setra.setUpdateBy(userid);
		//获取版本号
		int extraVersion = coopereApplyMapper.getExtraVersion(extraid);
		setra.setVersion(extraVersion);
		staffMapper.updateUserExtra(setra);
	}

	/* (non-Javadoc)
	 * <p>Title: findOperateServiceList</p>
	 * <p>Description: </p>
	 * @param status
	 * @param applyname
	 * @param realname
	 * @param icardno
	 * @param createTimeStart
	 * @param createTimeEnd
	 * @param page
	 * @param pageSize
	 * @return
	 * link: @see dianfan.service.our.CoopereApplyService#findOperateServiceList(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, int, int)
	 */ 
	@Override
	public ResultBean findOperateServiceList(String status, String applyname,String realname,String idcardno,String createTimeStart,String createTimeEnd,int page,int pageSize) {
		// TODO Auto-generated method stub
		Map<String, Object> data = new HashMap<String, Object>();
		CooperationApply ca = new CooperationApply();
		ca.setStatus(status);
		ca.setApplyName(applyname);
		ca.setRealName(realname);
		ca.setIdcardNo(idcardno);
		ca.setCreateTimeStart(createTimeStart);
		ca.setCreateTimeEnd(createTimeEnd);
		int count = coopereApplyMapper.getOperateServicerNum(ca);
		data.put("totalcount", count);
		if (count < (page - 1) * pageSize || count == 0) {
			// 空的返回实体
			data.put("calist", new ArrayList<>());
			// 未删选到数据
			return new ResultBean(data);
		}
		// 起始的条数
		ca.setStart((page - 1) * pageSize);
		// 分页偏移量 10
		ca.setOffset(pageSize);
		List<CooperationApply> calist = coopereApplyMapper.findOperateServicerList(ca);
		if (calist !=null && calist.size()>0) {
			for (int i = 0; i < calist.size(); i++) {
				calist.get(i).setIdcardBack(PropertyUtil.getProperty("domain")+calist.get(i).getIdcardBack());
				calist.get(i).setIdcardFont(PropertyUtil.getProperty("domain")+calist.get(i).getIdcardFont());
				calist.get(i).setHandleIdcard(PropertyUtil.getProperty("domain")+calist.get(i).getHandleIdcard());
			}
		}
		data.put("calist", calist);
		return new ResultBean(data);
	}

	/* (non-Javadoc)
	 * <p>Title: updateOperaServerApply</p>
	 * <p>Description: </p>
	 * @param userid
	 * @param operaserverid
	 * @param applyUserId
	 * @param applystatus
	 * @param fReason
	 * @param roledistinguish
	 * link: @see dianfan.service.our.CoopereApplyService#updateOperaServerApply(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */ 
	@Override
	@Transactional
	public ResultBean updateOperaServerApply(String userid, String operaserverid, String applyUserId, String applystatus,String fReason, String roledistinguish,String contractUrl) {
		// TODO Auto-generated method stub
		CooperationApply ca = new CooperationApply();
		//获取城市code
		String cityCode = null;
		int version = coopereApplyMapper.getVersionInfo(applyUserId);
		UserRole ur = new UserRole();
		ca.setId(Integer.valueOf(operaserverid));
		ca.setStatus(applystatus);
		if (ConstantIF.APPLY_STATUS1.equals(applystatus)) {
			//获取用户上下级关系表(易盟关系)信息
			int lowerUpperNum = coopereApplyMapper.getLowerUpperRelate(applyUserId);
			if (lowerUpperNum>0) {
				return new ResultBean("2009", ResultBgMsg.C_2009);
			}
			Role r = coopereApplyMapper.getUserRole(roledistinguish);
			ur.setUserid(applyUserId);
			ur.setRoleid(r.getId());
			ur.setDescption(r.getRoleName());
			coopereApplyMapper.updateCooperationApplyRole(ur);
			//修改用户资料
			StaffExtra setra = new StaffExtra();
			String extraid = coopereApplyMapper.getExtraId(applyUserId);
			if (!StringUtils.isNotEmpty(extraid)) {
				int extraVersion = coopereApplyMapper.getExtraVersion(extraid);
				setra.setId(extraid);
				setra.setVersion(extraVersion);
				setra.setContractUrl(contractUrl);
				setra.setUpdateBy(userid);
				staffMapper.updateUserinfoExtra(setra);
			}
			
			//绑定关系
			LowerUpperRelate lur = new LowerUpperRelate();
			//获取城市code
			cityCode = coopereApplyMapper.getExtraCityCode(extraid);
			//获取上级code
			String pricode = coopereApplyMapper.getPriCode(cityCode);
			//获取大区经理id
			String regionalManagerId = coopereApplyMapper.getRegionalManagerId(pricode);
			lur.setId(UUIDUtil.getUUID());
			lur.setUpperUserId(regionalManagerId);
			lur.setDownUserId(applyUserId);
			lur.setCreateBy(userid);
			//添加用户上下级关系
			coopereApplyMapper.addLowerUpperRelate(lur);
		}else {
			ca.setfReason(fReason);
		}
		ca.setVersion(version);
		ca.setUpdateBy(userid);
		coopereApplyMapper.updateCoopereApplyStatus(ca);
		if (StringUtils.isNotEmpty(cityCode)) {
			//修改用户表区域code
			coopereApplyMapper.updateUserInfoAreaCode(applyUserId, cityCode);
		}
		return new ResultBean();
	}

}
