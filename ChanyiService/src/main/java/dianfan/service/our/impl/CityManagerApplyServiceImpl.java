/**  
* @Title: CityManagerApplyServiceImpl.java
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
import dianfan.dao.mapper.userManage.StaffMapper;
import dianfan.entities.our.CityManagerApply;
import dianfan.entities.our.CityManagerModel;
import dianfan.entities.role.Role;
import dianfan.entities.role.UserRole;
import dianfan.entities.user.LowerUpperRelate;
import dianfan.entities.user.StaffExtra;
import dianfan.models.ResultBean;
import dianfan.service.our.CityManagerApplyService;
import dianfan.util.PropertyUtil;
import dianfan.util.UUIDUtil;

/** @ClassName CityManagerApplyServiceImpl
 * @Description 
 * @author yl
 * @date 2018年6月29日 下午3:08:16
 */
@Service
public class CityManagerApplyServiceImpl implements CityManagerApplyService{
	
	@Autowired
	private CityManagerApplyMapper cityManagerApplyMapper;
	@Autowired
	private StaffMapper staffMapper;

	/* (non-Javadoc)
	 * <p>Title: addCityManagerApply</p>
	 * <p>Description: </p>
	 * @param userid 用户id
	 * @param applyname 申请人姓名
	 * @param applyphonenum 手机号
	 * @param applyaddr 地址
	 * link: @see dianfan.service.our.CityManagerApplyService#addCityManagerApply(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */ 
	@Override
	@Transactional
	public ResultBean addCityManagerApply(String userid, String applyphonenum,String applyname,String realname,String idcardno,String idcardfont,String idcardback,String idcardvaliddate,String handleidcard,String status,String cityCode) {
		// TODO Auto-generated method stub
		Map<String, Object> param = new HashMap<String, Object>();
		Boolean cooperebool = cityManagerApplyMapper.checkCityManager("806d7b177db111e88dd352540054a904", cityCode, null);
		Boolean citymanagerbool = cityManagerApplyMapper.checkCityManager("807725ad7db111e88dd352540054a904", cityCode, null);
		if(cooperebool) return new ResultBean("3001", "运营服务商" + ResultBgMsg.C_3001);
		if(citymanagerbool) return new ResultBean("3001", "城市经理" + ResultBgMsg.C_3001);
		CityManagerApply ca = new CityManagerApply();
		ca.setId(UUIDUtil.getUUID());
		ca.setApplyname(applyname);
		ca.setApplyUserId(userid);
		ca.setTelno(applyphonenum);
		ca.setStatus("03");
		ca.setCreateBy(userid);
		cityManagerApplyMapper.addCityManagerApply(ca);
		//添加用户资料补充表
		StaffExtra setra = new StaffExtra();
		String uuids = UUIDUtil.getUUID();
		setra.setId(uuids);
		setra.setAreaCode(cityCode);
		setra.setCreateBy(userid);
		setra.setIdcardBack(idcardback);
		setra.setIdcardFont(idcardfont);
		setra.setIdcardNo(idcardno);
		setra.setIdcardValidDate(idcardvaliddate);
		setra.setRealName(realname);
		setra.setHandleIdcard(handleidcard);
		staffMapper.addStaffExtra(setra);
		//添加用户与用户资料的关系
		int userversion = cityManagerApplyMapper.getUserVersionInfo(userid);
		param.put("id", userid);
		param.put("extraid", uuids);
		param.put("version", userversion);
		param.put("userid", userid);
		cityManagerApplyMapper.updataUserExtraId(param);
		return new ResultBean();
	}

	/* (non-Javadoc)
	 * <p>Title: getCooperationApply</p>
	 * <p>Description: </p>
	 * @param userid
	 * @return
	 * link: @see dianfan.service.our.CityManagerApplyService#getCooperationApply(java.lang.String)
	 */ 
	@Override
	public int getCityManagerApply(String userid) {
		// TODO Auto-generated method stub
		int cStatus = cityManagerApplyMapper.getCityManagerApply(userid);
		return cStatus;
	}

	/* (non-Javadoc)
	 * <p>Title: updateCityManagerApply</p>
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
	 * link: @see dianfan.service.our.CityManagerApplyService#updateCityManagerApply(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */ 
	@Override
	@Transactional
	public void updateCityManagerApply(String userid,String applyphonenum,String applyname,
			String realname, String idcardno, String idcardfont, String idcardback, String idcardvaliddate,
			String handleidcard,String cityCode) {
		// TODO Auto-generated method stub
		int versionInfo = cityManagerApplyMapper.getVersionInfo(userid);
		CityManagerApply ca = new CityManagerApply();
		ca.setId(UUIDUtil.getUUID());
		ca.setApplyname(applyname);
		ca.setApplyUserId(userid);
		ca.setTelno(applyphonenum);
		ca.setUpdateBy(userid);
		ca.setVersion(versionInfo);
		cityManagerApplyMapper.updateCityManagerApply(ca);
		String extraid = cityManagerApplyMapper.getExtraId(userid);
		//添加用户资料补充表
		StaffExtra setra = new StaffExtra();
		setra.setId(extraid);
		setra.setAreaCode(cityCode);
		setra.setIdcardBack(idcardback);
		setra.setIdcardFont(idcardfont);
		setra.setIdcardNo(idcardno);
		setra.setIdcardValidDate(idcardvaliddate);
		setra.setRealName(realname);
		setra.setHandleIdcard(handleidcard);
		setra.setUpdateBy(userid);
		//获取版本号
		int extraVersion = cityManagerApplyMapper.getExtraVersion(extraid);
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
	 * link: @see dianfan.service.our.CityManagerApplyService#findOperateServiceList(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, int, int)
	 */ 
	@Override
	public ResultBean findCityManagerApplyList(String status,String applyname,String realname,String idcardno,String createTimeStart,String createTimeEnd,int page,int pageSize) {
		// TODO Auto-generated method stub
		Map<String, Object> data = new HashMap<String, Object>();
		CityManagerModel ca = new CityManagerModel();
		ca.setStatus(status);
		ca.setApplyname(applyname);
		ca.setRealName(realname);
		ca.setIdcardNo(idcardno);
		ca.setCreateTimeStart(createTimeStart);
		ca.setCreateTimeEnd(createTimeEnd);
		int count = cityManagerApplyMapper.getCityManagerNum(ca);
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
		List<CityManagerModel> calist = cityManagerApplyMapper.findCityManagerApplyList(ca);
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
	 * link: @see dianfan.service.our.CityManagerApplyService#updateOperaServerApply(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */ 
	@Override
	@Transactional
	public ResultBean updateBgCityManagerApply(String userid, String citymanagerid, String applystatus,String fReason, String roledistinguish,String contractUrl) {
		// TODO Auto-generated method stub
		CityManagerApply ca = new CityManagerApply();
		String applyUserId = cityManagerApplyMapper.getCityManageApplyUserid(citymanagerid);
		//获取城市code
		String cityCode = null;
		UserRole ur = new UserRole();
		ca.setId(citymanagerid);
		ca.setStatus(applystatus);
		if (ConstantIF.APPLY_STATUS1.equals(applystatus)) {
			//获取用户上下级关系表(易盟关系)信息
			int lowerUpperNum = cityManagerApplyMapper.getLowerUpperRelate(applyUserId);
			if (lowerUpperNum>0) {
				return new ResultBean("2009", ResultBgMsg.C_2009);
			}
			Role r = cityManagerApplyMapper.getUserRole(roledistinguish);
			ur.setUserid(applyUserId);
			ur.setRoleid(r.getId());
			ur.setDescption(r.getRoleName());
			cityManagerApplyMapper.updateCityManagerApplyRole(ur);
			//修改用户资料
			StaffExtra setra = new StaffExtra();
			String extraid = cityManagerApplyMapper.getExtraId(applyUserId);
			if (!StringUtils.isNotEmpty(extraid)) {
				int extraVersion = cityManagerApplyMapper.getExtraVersion(extraid);
				setra.setId(extraid);
				setra.setVersion(extraVersion);
				setra.setContractUrl(contractUrl);
				setra.setUpdateBy(userid);
				staffMapper.updateUserinfoExtra(setra);
			}
			
			//绑定关系
			LowerUpperRelate lur = new LowerUpperRelate();
			//获取城市code
			cityCode = cityManagerApplyMapper.getExtraCityCode(extraid);
			//获取上级code
			String pricode = cityManagerApplyMapper.getPriCode(cityCode);
			//获取大区经理id
			String regionalManagerId = cityManagerApplyMapper.getRegionalManagerId(pricode);
			lur.setId(UUIDUtil.getUUID());
			lur.setUpperUserId(regionalManagerId);
			lur.setDownUserId(applyUserId);
			lur.setCreateBy(userid);
			//添加用户上下级关系
			cityManagerApplyMapper.addLowerUpperRelate(lur);
		}else {
			ca.setfReason(fReason);
		}
		int version = cityManagerApplyMapper.getVersionInfo(applyUserId);
		ca.setVersion(version);
		ca.setUpdateBy(userid);
		cityManagerApplyMapper.updateCityManagerApplyStatus(ca);
		if (StringUtils.isNotEmpty(cityCode)) {
			//修改用户表区域code
			cityManagerApplyMapper.updateUserInfoAreaCode(applyUserId, cityCode);
		}
		return new ResultBean();
	}

}
