/**  
* @Title: ExperApplyServiceImpl.java
* @Package dianfan.service.our.impl
* @Description: TODO
* @author yl
* @date 2018年6月28日 下午8:09:15
* @version V1.0  
*/
package dianfan.service.our.impl;

import java.math.BigDecimal;
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
import dianfan.dao.mapper.our.ExperApplyMapper;
import dianfan.entities.ExperiencestoreApply;
import dianfan.entities.ExperiencestoreApplyClassify;
import dianfan.entities.role.Role;
import dianfan.entities.role.UserRole;
import dianfan.entities.user.LowerUpperRelate;
import dianfan.map.tencent.GeocoderAttribute;
import dianfan.map.tencent.TencentMapApi;
import dianfan.models.ResultBean;
import dianfan.service.our.ExperApplyService;
import dianfan.util.PropertyUtil;
import dianfan.util.UUIDUtil;

/**
 * @ClassName ExperApplyServiceImpl
 * @Description
 * @author yl
 * @date 2018年6月28日 下午8:09:15
 */
@Service
public class ExperApplyServiceImpl implements ExperApplyService {

	@Autowired
	private ExperApplyMapper experApplyMapper;

	/*
	 * (non-Javadoc) <p>Title: addExperApply</p> <p>Description: </p>
	 * 
	 * @param applyuserid
	 * 
	 * @param applyaddr
	 * 
	 * @param area
	 * 
	 * @param goodsclassifyid
	 * 
	 * @param applyphonenum
	 * 
	 * @param doorheadurl
	 * 
	 * @param innerurl00
	 * 
	 * @param innerurl01
	 * 
	 * @param businesslicenceurl
	 * 
	 * @param realname
	 * 
	 * @param idcardno
	 * 
	 * @param idcardfont
	 * 
	 * @param idcardback
	 * 
	 * @param idcardvaliddate
	 * 
	 * @param handleidcard link: @see
	 * dianfan.service.our.ExperApplyService#addExperApply(java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String, java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String, java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String, java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional
	public ResultBean addExperApply(String applyuserid, String applyname, String applyaddr, String area,
			String applycurrentbusiness, String applyphonenum, String doorheadurl, String innerurl00, String innerurl01,
			String businesslicenceurl, String status, String cityCode, String businessWeeklyStart,
			String businessWeeklyEnd, String businessTimeStart, String businessTimeEnd, String goodsClassifyId) {
		// TODO Auto-generated method stub
		ExperiencestoreApply esa = new ExperiencestoreApply();
		String eaid = UUIDUtil.getUUID();
		esa.setId(eaid);
		esa.setApplyName(applyname);
		esa.setApplyUserId(applyuserid);
		esa.setArea(area);
		esa.setApplyPhoneNum(applyphonenum);
		int indexOf3 = doorheadurl.indexOf("upload");
		String doorheadurls = doorheadurl.substring(indexOf3);
		esa.setDoorheadUrl(doorheadurls);
		// 修改图片的路径
		int indexOf = innerurl00.indexOf("upload");
		String innerurl00s = innerurl00.substring(indexOf);
		esa.setInnerUrl00(innerurl00s);
		int indexOf1 = innerurl01.indexOf("upload");
		String innerurl01s = innerurl01.substring(indexOf1);
		esa.setInnerUrl01(innerurl01s);
		int indexOf2 = businesslicenceurl.indexOf("upload");
		String businesslicenceurls = businesslicenceurl.substring(indexOf2);
		esa.setBusinessLicenceUrl(businesslicenceurls);
		esa.setStatus(status);
		esa.setCreateBy(applyuserid);
		esa.setApplyCurrentBusiness(applycurrentbusiness);
		esa.setCityCode(cityCode);
		esa.setBusinessTimeStart(businessTimeStart);
		esa.setBusinessTimeEnd(businessTimeEnd);
		esa.setBusinessWeeklyStart(businessWeeklyStart);
		esa.setBusinessWeeklyEnd(businessWeeklyEnd);
		//详细地理信息解析成经纬度
		TencentMapApi tma = new TencentMapApi();
		String pcaname = null;
		if (StringUtils.isNotEmpty(cityCode)) {
			pcaname = experApplyMapper.getPCityAreaName(cityCode);
			esa.setPcaname(pcaname);
		}
		if (StringUtils.isNotEmpty(pcaname) && StringUtils.isNotEmpty(applyaddr)) {
			GeocoderAttribute ga = tma.getLngLatLaction(pcaname+applyaddr);
			if (ga !=null && (ga.getLng() !=null && ga.getLat() !=null)) {		
				esa.setLatitude(String.valueOf(ga.getLat()));
				esa.setLongitude(String.valueOf(ga.getLng()));
			   }else {
				   return new ResultBean("2008",ResultBgMsg.C_2008);
			   }
			esa.setApplyAddr(applyaddr);
		}
		experApplyMapper.addExperApply(esa);
		// 添加体验店品类
		if (StringUtils.isNotEmpty(goodsClassifyId)) {
			ExperiencestoreApplyClassify eac = new ExperiencestoreApplyClassify();
			eac.setExperiencestoreApplyId(eaid);
			String[] gcids = goodsClassifyId.split(",");
			eac.setGcid(gcids);
			eac.setCreateBy(applyuserid);
			experApplyMapper.addExperApplyClassify(eac);
		}
		return new ResultBean();
	}

	/**
	 * @Title: updateExperApply
	 * @Description:
	 * @param applyuserid
	 * @param applyaddr
	 * @param area
	 * @param goodsclassifyid
	 * @param applyphonenum
	 * @param doorheadurl
	 * @param innerurl00
	 * @param innerurl01
	 * @param businesslicenceurl
	 * @throws:
	 * @time: 2018年7月11日 下午3:43:51
	 */
	@Override
	@Transactional
	public ResultBean updateExperApply(String applyuserid, String applyname, String applyaddr, String area,
			String applycurrentbusiness, String applyphonenum, String doorheadurl, String innerurl00, String innerurl01,
			String businesslicenceurl, String cityCode, String businessWeeklyStart, String businessWeeklyEnd,
			String businessTimeStart, String businessTimeEnd, String goodsClassifyId) {
		// TODO Auto-generated method stub
		int versionInfo = experApplyMapper.getVersionInfo(applyuserid);
		ExperiencestoreApply esa = new ExperiencestoreApply();
		esa.setApplyUserId(applyuserid);
		esa.setApplyName(applyname);
		esa.setArea(area);
		esa.setApplyPhoneNum(applyphonenum);
		esa.setCityCode(cityCode);
		int indexOf3 = doorheadurl.indexOf("upload");
		String doorheadurls = doorheadurl.substring(indexOf3);
		esa.setDoorheadUrl(doorheadurls);
		// 修改图片的路径
		int indexOf = innerurl00.indexOf("upload");
		String innerurl00s = innerurl00.substring(indexOf);
		esa.setInnerUrl00(innerurl00s);
		int indexOf1 = innerurl01.indexOf("upload");
		String innerurl01s = innerurl01.substring(indexOf1);
		esa.setInnerUrl01(innerurl01s);
		int indexOf2 = businesslicenceurl.indexOf("upload");
		String businesslicenceurls = businesslicenceurl.substring(indexOf2);
		esa.setBusinessLicenceUrl(businesslicenceurls);
		esa.setUpdateBy(applyuserid);
		esa.setApplyCurrentBusiness(applycurrentbusiness);
		esa.setBusinessTimeStart(businessTimeStart);
		esa.setBusinessTimeEnd(businessTimeEnd);
		esa.setBusinessWeeklyStart(businessWeeklyStart);
		esa.setBusinessWeeklyEnd(businessWeeklyEnd);
		esa.setVersion(versionInfo);
		//详细地理信息解析成经纬度
		TencentMapApi tma = new TencentMapApi();
		String pcaname = null;
		if (StringUtils.isNotEmpty(cityCode)) {
			pcaname = experApplyMapper.getPCityAreaName(cityCode);
			esa.setPcaname(pcaname);
		}
		if (StringUtils.isNotEmpty(pcaname) && StringUtils.isNotEmpty(applyaddr)) {
			GeocoderAttribute ga = tma.getLngLatLaction(pcaname+applyaddr);
			if (ga !=null && (ga.getLng() !=null && ga.getLat() !=null)) {		
				esa.setLatitude(String.valueOf(ga.getLat()));
				esa.setLongitude(String.valueOf(ga.getLng()));
			   }else {
				   return new ResultBean("2008",ResultBgMsg.C_2008);
			   }
			
			esa.setApplyAddr(applyaddr);
		}
		experApplyMapper.updateExperApply(esa);
		String experienid = experApplyMapper.getEaid(applyuserid);
		int eanum = experApplyMapper.getEaclassifyNum(experienid);
		if (eanum > 0) {
			// 删除体验店品类
			experApplyMapper.delEaclassify(experienid);
		}
		// 添加体验店品类
		if (StringUtils.isNotEmpty(goodsClassifyId)) {
			ExperiencestoreApplyClassify eac = new ExperiencestoreApplyClassify();
			eac.setExperiencestoreApplyId(experienid);
			String[] gcids = goodsClassifyId.split(",");
			eac.setGcid(gcids);
			eac.setCreateBy(applyuserid);
			experApplyMapper.addExperApplyClassify(eac);
		}
		return new ResultBean();
	}

	/*
	 * (non-Javadoc) <p>Title: getExperApply</p> <p>Description: </p>
	 * 
	 * @param applyuserid
	 * 
	 * @return link: @see
	 * dianfan.service.our.ExperApplyService#getExperApply(java.lang.String)
	 */
	@Override
	public int getExperApply(String applyuserid) {
		// TODO Auto-generated method stub
		return experApplyMapper.getExperApply(applyuserid);
	}

	/*
	 * (non-Javadoc) <p>Title: getExperApplyByPhone</p> <p>Description:
	 * 验证此手机号码是否已存在</p>
	 * 
	 * @param phone 手机号码
	 * 
	 * @return link: @see
	 * dianfan.service.our.ExperApplyService#getExperApplyByPhone(java.lang.String)
	 */
	@Override
	public int getExperApplyByPhone(String phoneno) {
		// TODO Auto-generated method stub
		return experApplyMapper.getExperApplyByPhone(phoneno);
	}

	/*
	 * (non-Javadoc) <p>Title: findExperApplyList</p> <p>Description: </p>
	 * 
	 * @param status
	 * 
	 * @param applyname
	 * 
	 * @param citycode
	 * 
	 * @param applyphonenum
	 * 
	 * @param areastart
	 * 
	 * @param areaend
	 * 
	 * @param createTimeStart
	 * 
	 * @param createTimeEnd
	 * 
	 * @param page
	 * 
	 * @param pageSize
	 * 
	 * @return link: @see
	 * dianfan.service.our.ExperApplyService#findExperApplyList(java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String, java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String, int, int)
	 */
	@Override
	public ResultBean findExperApplyList(String status, String applyname, String citycode, String applyphonenum,
			String areastart, String areaend, String createTimeStart, String createTimeEnd, String applyCurrentBusiness,
			int page, int pageSize) {
		Map<String, Object> data = new HashMap<String, Object>();
		ExperiencestoreApply ea = new ExperiencestoreApply();
		ea.setStatus(status);
		ea.setApplyName(applyname);
		ea.setCreateTimeStart(createTimeStart);
		ea.setCreateTimeEnd(createTimeEnd);
		if (StringUtils.isNotEmpty(areastart)) {
			ea.setAreastart(new BigDecimal(areastart));
		}
		if (StringUtils.isNotEmpty(areaend)) {
			ea.setAreaend(new BigDecimal(areaend));
		}
		ea.setCityCode(citycode);
		ea.setApplyCurrentBusiness(applyCurrentBusiness);
		ea.setApplyPhoneNum(applyphonenum);
		int count = experApplyMapper.getExperApplyNum(ea);
		// 设置总页数
		data.put("totalcount", count);
		if (count < (page - 1) * pageSize || count == 0) {
			// 空的返回实体
			data.put("experlist", new ArrayList<>());
			// 未删选到数据
			return new ResultBean(data);
		}
		// 起始的条数
		ea.setStart((page - 1) * pageSize);
		// 分页偏移量 10
		ea.setOffset(pageSize);
		List<ExperiencestoreApply> experlist = experApplyMapper.findBgExperApplyList(ea);
		if (experlist != null && experlist.size() > 0) {
			for (int i = 0; i < experlist.size(); i++) {
				// 获取城市id
				String uppercitycode = experApplyMapper.getUpperCity(experlist.get(i).getId());
				experlist.get(i).setUpperCity(uppercitycode);
				if (StringUtils.isNotEmpty(experlist.get(i).getDoorheadUrl())) {
					experlist.get(i)
							.setDoorheadUrl(PropertyUtil.getProperty("domain") + experlist.get(i).getDoorheadUrl());
				}
				if (StringUtils.isNotEmpty(experlist.get(i).getBusinessLicenceUrl())) {
					experlist.get(i).setBusinessLicenceUrl(
							PropertyUtil.getProperty("domain") + experlist.get(i).getBusinessLicenceUrl());
				}
				if (StringUtils.isNotEmpty(experlist.get(i).getInnerUrl00())) {
					experlist.get(i)
							.setInnerUrl00(PropertyUtil.getProperty("domain") + experlist.get(i).getInnerUrl00());
				}
				if (StringUtils.isNotEmpty(experlist.get(i).getInnerUrl01())) {
					experlist.get(i)
							.setInnerUrl01(PropertyUtil.getProperty("domain") + experlist.get(i).getInnerUrl01());
				}
				String pcaname = experApplyMapper.getPCityAreaName(experlist.get(i).getCityCode());
				experlist.get(i).setPcaname(pcaname);
			}
		}
		data.put("experlist", experlist);
		return new ResultBean(data);
	}

	/*
	 * (non-Javadoc) <p>Title: updateBgExperApply</p> <p>Description: </p>
	 * 
	 * @param userid
	 * 
	 * @param experienid
	 * 
	 * @param applyUserId
	 * 
	 * @param status
	 * 
	 * @param fReason
	 * 
	 * @param roledistinguish
	 * 
	 * @param cityCode
	 * 
	 * @param businessWeeklyStart
	 * 
	 * @param businessWeeklyEnd
	 * 
	 * @param businessTimeStart
	 * 
	 * @param businessTimeEnd
	 * 
	 * @param applyCurrentBusiness
	 * 
	 * @param goodsClassifyId
	 * 
	 * @param contractUrl link: @see
	 * dianfan.service.our.ExperApplyService#updateBgExperApply(java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String, java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String, java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String, java.lang.String,
	 * java.lang.String)
	 */
	@Override
	@Transactional
	public ResultBean updateBgExperApply(String userid, String experienid, String applyname, String applyUserId,
			String status, String fReason, String roledistinguish, String applyaddr, String area,
			String applycurrentbusiness, String applyphonenum, String doorheadurl, String innerurl00, String innerurl01,
			String businesslicenceurl, String areaCode, String businessWeeklyStart, String businessWeeklyEnd,
			String businessTimeStart, String businessTimeEnd, String goodsClassifyId) {
		// TODO Auto-generated method stub
		ExperiencestoreApply ea = new ExperiencestoreApply();
		int version = experApplyMapper.getVersionInfo(applyUserId);
		//获取区域code
		String experareacode =null;
		UserRole ur = new UserRole();
		ea.setId(experienid);
		ea.setStatus(status);

		ea.setApplyName(applyname);
		ea.setArea(area);
		ea.setApplyPhoneNum(applyphonenum);
 		if (StringUtils.isNotEmpty(doorheadurl)) {
			int indexOf3 = doorheadurl.indexOf("upload");
			String doorheadurls = doorheadurl.substring(indexOf3);
			ea.setDoorheadUrl(doorheadurls);
		}
		if (StringUtils.isNotEmpty(innerurl00)) {
			// 修改图片的路径
			int indexOf = innerurl00.indexOf("upload");
			String innerurl00s = innerurl00.substring(indexOf);
			ea.setInnerUrl00(innerurl00s);
		}
		if (StringUtils.isNotEmpty(innerurl01)) {
			int indexOf1 = innerurl01.indexOf("upload");
			String innerurl01s = innerurl01.substring(indexOf1);
			ea.setInnerUrl01(innerurl01s);
		}
		if (StringUtils.isNotEmpty(businesslicenceurl)) {
			int indexOf2 = businesslicenceurl.indexOf("upload");
			String businesslicenceurls = businesslicenceurl.substring(indexOf2);
			ea.setBusinessLicenceUrl(businesslicenceurls);
		}

		ea.setApplyCurrentBusiness(applycurrentbusiness);
		if (StringUtils.isNotEmpty(experareacode)) {
			experareacode = areaCode;
		}
		ea.setCityCode(experareacode);
		ea.setBusinessTimeStart(businessTimeStart);
		ea.setBusinessTimeEnd(businessTimeEnd);
		ea.setBusinessWeeklyStart(businessWeeklyStart);
		ea.setBusinessWeeklyEnd(businessWeeklyEnd);
		//详细地理信息解析成经纬度
		TencentMapApi tma = new TencentMapApi();
		String pcaname = null;
		if (StringUtils.isNotEmpty(areaCode)) {
			pcaname = experApplyMapper.getPCityAreaName(areaCode);
			ea.setPcaname(pcaname);
		}
		if (StringUtils.isNotEmpty(pcaname) && StringUtils.isNotEmpty(applyaddr)) {
			GeocoderAttribute ga = tma.getLngLatLaction(pcaname+applyaddr);
			if (ga !=null && (ga.getLng() !=null && ga.getLat() !=null)) {		
				ea.setLatitude(String.valueOf(ga.getLat()));
				ea.setLongitude(String.valueOf(ga.getLng()));
			   }else {
				   return new ResultBean("2008",ResultBgMsg.C_2008);
			   }
			
			ea.setApplyAddr(applyaddr);
		}
		if (ConstantIF.APPLY_STATUS1.equals(status)) {
			Role r = experApplyMapper.getUserRole(roledistinguish);
			ur.setUserid(applyUserId);
			ur.setRoleid(r.getId());
			ur.setDescption(r.getRoleName());
			experApplyMapper.updateExperApplyRole(ur);
			// 绑定关系
			LowerUpperRelate lur = new LowerUpperRelate();
			ExperiencestoreApply eapply = experApplyMapper.getBgExperApply(experienid);
			if (!StringUtils.isNotEmpty(areaCode) && StringUtils.isNotEmpty(eapply.getCityCode())) {
				experareacode = eapply.getCityCode();
			}
			// 获取城市id
			String citycode = experApplyMapper.getSupAreaCode(experareacode);
			// 获取上级id
			String supUserid = experApplyMapper.getSupUserid(citycode);
			lur.setId(UUIDUtil.getUUID());
			lur.setUpperUserId(supUserid);
			lur.setDownUserId(applyUserId);
			lur.setCreateBy(userid);
			// 添加用户上下级关系
			experApplyMapper.addLowerUpperRelate(lur);
		} else {
			ea.setfReason(fReason);
		}
		ea.setVersion(version);
		ea.setUpdateBy(userid);
		experApplyMapper.updateExpereStatus(ea);
		//更新用户表区域code
		if (StringUtils.isNotEmpty(experareacode)) {
			experApplyMapper.updateUserInfoAreaCode(applyUserId, experareacode);
		}
		return new ResultBean();

	}

}
