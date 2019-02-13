package dianfan.service.usermanage.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dianfan.annotations.SystemServiceLog;
import dianfan.dao.mapper.base.AreaMapper;
import dianfan.dao.mapper.our.SupplierApplyMapper;
import dianfan.dao.mapper.userManage.StaffMapper;
import dianfan.entities.UserInfo;
import dianfan.entities.base.Regionss;
import dianfan.entities.role.Role;
import dianfan.entities.user.StaffExtra;
import dianfan.models.ResultBean;
import dianfan.service.usermanage.StaffService;
import dianfan.util.PropertyUtil;
import dianfan.util.StringUtility;
import dianfan.util.UUIDUtil;

/**
 * @ClassName StaffServiceManageImpl
 * @Description 易盟员工服务
 * @author cjy
 * @date 2018年7月25日 上午11:03:19
 */
@Service
public class StaffServiceImpl implements StaffService {

	@Autowired
	private StaffMapper staffMapper;
	@Autowired
	private AreaMapper areaMapper;
	@Autowired
	private SupplierApplyMapper supplierApplyMapper;

	/*
	 * (non-Javadoc) <p>Title: findStaffList</p> <p>Description: 易盟员工列表</p>
	 * 
	 * @param page
	 * 
	 * @param pageSize
	 * 
	 * @param name
	 * 
	 * @param telno
	 * 
	 * @param role
	 * 
	 * @param areacode
	 * 
	 * @return
	 * 
	 * @author cjy link: @see
	 * dianfan.service.usermanage.StaffService#findStaffList(int, int,
	 * java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	@SystemServiceLog(method = "findStaffList", description = "易盟员工列表")
	public ResultBean findStaffList(int page, int pageSize, String name, String telno, String role, String areacode,
			String regionscode) {
		// 构建返回参数模型
		Map<String, Object> data = new HashMap<>();

		// 创建入参容器
		Map<String, Object> param = new HashMap<>();
		param.put("start", (page - 1) * pageSize);
		param.put("pageSize", pageSize);
		param.put("name", name);
		param.put("telno", telno);
		// 按角色
		param.put("role", role);
		// 区域code
		param.put("areacode", areacode);
		param.put("regionscode", regionscode);

		// 获取用户列表总数
		int count = staffMapper.findUserCount(param);
		data.put("count", count);
		if (count == 0 || count <= (page - 1) * pageSize) {
			data.put("userList", new ArrayList<>());
			return new ResultBean(data);
		}
		String domain = PropertyUtil.getProperty("domain");
		// 获取用户列表
		List<UserInfo> userList = staffMapper.findUserList(param);
		// 将查出来的区域code，转成完整区域信息返回
		for (UserInfo info : userList) {
			if (info.getAvatarUrl() != null)
				info.setAvatarUrl(domain + info.getAvatarUrl());
			if (!StringUtility.isNull(info.getAreaCode())) {
				int level;
				if (info.getAreaCode().lastIndexOf("0000") != -1) {
					// 省级code
					level = 1;
				} else if (info.getAreaCode().lastIndexOf("00") != -1) {
					// 市级code
					level = 2;
				} else {
					// 区县code
					level = 3;
				}
				String addrByCode = areaMapper.getAddrByCode(info.getAreaCode(), level);
				info.setAreatext(addrByCode);
			}

			if (StringUtils.equals(info.getRoleid(), "31aee8cd7db111e88dd352540054a904")) {
				// 获取所在大区
				String regionName = areaMapper.getStaffRegionName(info.getId());
				info.setAreatext(regionName);
			}
		}

		data.put("userList", userList);
		return new ResultBean(data);
	}

	/*
	 * (non-Javadoc) <p>Title: getStaffDetail</p> <p>Description: 易盟员工详情</p>
	 * 
	 * @param staffid 易盟员工id
	 * 
	 * @return
	 * 
	 * @author cjy link: @see
	 * dianfan.service.usermanage.StaffService#getStaffDetail(java.lang.String)
	 */
	@Override
	@SystemServiceLog(method = "getStaffDetail", description = "易盟员工详情")
	public ResultBean getStaffDetail(String staffid) {
		String domain = PropertyUtil.getProperty("domain");
		// 获取用户基本信息
		UserInfo staffDetail = staffMapper.getStaffDetail(staffid);
		if (StringUtils.isNotEmpty(staffDetail.getAvatarUrl()))
			staffDetail.setAvatarUrl(domain + staffDetail.getAvatarUrl());
		// 获取地区
		if (StringUtils.isNotEmpty(staffDetail.getAreaCode())) {
			int level;
			if (staffDetail.getAreaCode().lastIndexOf("0000") != -1) {
				// 省级code
				level = 1;
			} else if (staffDetail.getAreaCode().lastIndexOf("00") != -1) {
				// 市级code
				level = 2;
			} else {
				// 区县code
				level = 3;
			}
			String addr = areaMapper.getAddrByCode(staffDetail.getAreaCode(), level);
			staffDetail.setAreatext(addr);
		}

		// 获取大区信息
		Regionss regions = staffMapper.getRegionsInfo(staffid);

		// 获取用户补充资料
		StaffExtra extra = staffMapper.getStaffExtraInfo(staffDetail.getExtraId());
		if (extra != null) {
			// 处理图片链接
			if (StringUtils.isNotEmpty(extra.getHandleIdcard()))
				extra.setHandleIdcard(domain + extra.getHandleIdcard());
			if (StringUtils.isNotEmpty(extra.getIdcardFont()))
				extra.setIdcardFont(domain + extra.getIdcardFont());
			if (StringUtils.isNotEmpty(extra.getIdcardBack()))
				extra.setIdcardBack(domain + extra.getIdcardBack());
			if (StringUtils.isNotEmpty(extra.getBusinessLicense()))
				extra.setBusinessLicense(domain + extra.getBusinessLicense());
			if (StringUtils.isNotEmpty(extra.getContractUrl())) {
				String[] ctts = extra.getContractUrl().split(",");
				for (int i = 0; i < ctts.length; i++) {
					ctts[i] = domain + ctts[i];
				}
				extra.setContractUrl(StringUtils.join(ctts, ","));
			}
		}

		Map<String, Object> ret = new HashMap<>();
		ret.put("staffDetail", staffDetail);
		ret.put("regions", regions);
		ret.put("extra", extra);

		return new ResultBean(ret);
	}

	/*
	 * (non-Javadoc) <p>Title: addStaff</p> <p>Description: 添加易盟员工</p>
	 * 
	 * @param user 员工数据
	 * 
	 * @param se 员工补充资料
	 * 
	 * @param role_tag 角色标记
	 * 
	 * @return
	 * 
	 * @author cjy link: @see
	 * dianfan.service.usermanage.StaffService#addStaff(dianfan.entities.user.
	 * StaffInfo)
	 */
	@Transactional
	@Override
	@SystemServiceLog(method = "addStaff", description = "添加易盟员工")
	public void addStaff(UserInfo user, StaffExtra se, String role_tag, String regionid) {
		if (StringUtils.equals(role_tag, "02") || StringUtils.equals(role_tag, "04")) {
			// 城市经理/运营服务商
			// 添加用户补充资料
			se.setId(UUIDUtil.getUUID());
			staffMapper.addStaffExtra(se);
			user.setExtraId(se.getId());
		}

		// 添加员工信息
		user.setId(UUIDUtil.getUUID());
		staffMapper.addStaffInfo(user);
		// 角色信息
		Role role = supplierApplyMapper.getUserRole2(user.getRoleid());
		// 添加角色关系
		staffMapper.bindStaffRoleRelation(user.getId(), user.getRoleid(), role.getRoleName());

		if (StringUtils.equals(role_tag, "01")) {
			// 添加了大区经理，绑定大区经理区域关系
			staffMapper.bindStaffRegionalRelation(user.getId(), regionid, se.getCreateBy());
			// 获取此大区下的员工
			List<String> staffs = staffMapper.findRegionalStaff(regionid);
			if (!staffs.isEmpty()) {
				// 修改上下级关系
				staffMapper.updateStaffRegionalRelation(staffs, user.getId(), se.getCreateBy());
			}
		} else if (StringUtils.equals(role_tag, "02")) {
			// 添加了运营服务商，需将区域下的城市经理转变为市场开发经理
			// 获取区域下的城市经理
			List<String> city_staff = staffMapper.findCityManageByAraeCode(se.getAreaCode());
			if (!city_staff.isEmpty()) {
				// 将区域下的城市经理转变为市场开发经理
				staffMapper.changeStaffRole(city_staff, se.getCreateBy());
			}
		}

		if (StringUtils.equals(role_tag, "02") || StringUtils.equals(role_tag, "04")) {
			// 城市经理
			// 获取对应大区经理id
			String regionsManageid = staffMapper.getRegionsManageid(se.getAreaCode());
			// 绑定上下级关系
			staffMapper.bindUserLowerUpperRelate(regionsManageid, user.getId(), se.getCreateBy());
			// 获取城市下的体验店用户id
			List<String> storeUserid = staffMapper.findStoreByCityCode(se.getAreaCode());
			if (!storeUserid.isEmpty())
				// 更新与体验店的上下级关系
				staffMapper.updateStaffRegionalRelation(storeUserid, user.getId(), se.getCreateBy());
		}
	}

	/*
	 * (non-Javadoc) <p>Title: editStaff</p> <p>Description: 修改易盟员工</p>
	 * 
	 * @param user 员工数据
	 * 
	 * @param se 员工扩展数据
	 * 
	 * @param role_tag 角色标记
	 * 
	 * @param regionid 大区id
	 * 
	 * @author cjy link: @see
	 * dianfan.service.usermanage.StaffService#editStaff(dianfan.entities.UserInfo,
	 * dianfan.entities.user.StaffExtra, java.lang.String, java.lang.String)
	 */
	@Transactional
	@Override
	@SystemServiceLog(method = "editStaff", description = "修改易盟员工")
	public void editStaff(UserInfo user, StaffExtra se, String role_tag, String regionid) {
		// 修改用户基础数据
		staffMapper.updateStaffInfo(user);

		if (StringUtils.equals(role_tag, "01")) {
			// 大区经理,更新大区id
			staffMapper.updateStaffRegionalRelation1(user.getId(), regionid, user.getUpdateBy());
			// 清除原大区经理下的上下级关系
			staffMapper.unbindStaffRegionalRelation(user.getId(), user.getUpdateBy());
			// 获取此大区下的员工
			List<String> staffs = staffMapper.findRegionalStaff(regionid);
			// 修改上下级关系
			if (!staffs.isEmpty())
				staffMapper.updateStaffRegionalRelation(staffs, user.getId(), user.getUpdateBy());
		} else if (StringUtils.equals(role_tag, "02")) {
			// 运营服务商
			// 获取扩展数据id
			String extid = staffMapper.getExtraid(user.getId());
			se.setId(extid);
			// 更新扩展数据
			staffMapper.updateExtraInfo(se);
			// 获取城市下的体验店用户id
			List<String> storeUserid = staffMapper.findStoreByCityCode(se.getAreaCode());
			if (!storeUserid.isEmpty())
				// 更新与体验店的上下级关系
				staffMapper.updateStaffRegionalRelation(storeUserid, user.getId(), se.getUpdateBy());
		} else if (StringUtils.equals(role_tag, "04")) {
			// 获取扩展数据id
			String extid = staffMapper.getExtraid(user.getId());
			se.setId(extid);
			// 更新扩展数据
			staffMapper.updateExtraInfo(se);
		}
	}

	/*
	 * (non-Javadoc) <p>Title: updateFreezeStaff</p> <p>Description: 冻结/解冻 易盟员工</p>
	 * 
	 * @param staffid 员工id
	 * 
	 * @param action 动作（0解冻，1冻结）
	 * 
	 * @param operater 操作者
	 * 
	 * @author cjy link: @see
	 * dianfan.service.usermanage.StaffService#updateFreezeStaff(java.lang.String,
	 * int, java.lang.String)
	 */
	@Transactional
	@Override
	@SystemServiceLog(method = "updateFreezeStaff", description = "冻结/解冻 易盟员工")
	public void updateFreezeStaff(String staffid, int action, String operater) {
		// 获取员工角色
		String roleid = staffMapper.getStaffRoleid(staffid);

		if (StringUtils.equals(roleid, "806d7b177db111e88dd352540054a904")) {
			// 运营服务商
			if (action == 1) {
				// 获取运营服务商下的市场开发经理
				String id = staffMapper.getMarketDevelopmentManager(staffid);
				// 冻结旗下市场开发经理
				if (id != null)
					staffMapper.updateStaffAccoutLocked(id, action, operater);
			}
		}
		if (StringUtils.equals(roleid, "807c477e7db111e88dd352540054a904")) {
			// 员工是体验店,禁用体验店
			// staffMapper.stopStore(staffid, action, operater);
		}
		staffMapper.updateStaffAccoutLocked(staffid, action, operater);
	}

	/*
	 * (non-Javadoc) <p>Title: migrateStaff</p> <p>Description: 迁移员工</p>
	 * 
	 * @param inStaffid 数据流入员工id数组
	 * 
	 * @param outStaffids 数据流入员工id列表
	 * 
	 * @param operater 操作者
	 * 
	 * @author cjy link: @see
	 * dianfan.service.usermanage.StaffService#migrateStaff(java.lang.String,
	 * java.lang.String[], java.lang.String)
	 */
	@Transactional
	@Override
	@SystemServiceLog(method = "migrateStaff", description = "迁移员工")
	public void migrateStaff(String inStaffid, String outStaffid, String roleid, String reason, String operater) {
		staffMapper.updateBindRelation(inStaffid, outStaffid, reason, operater);
		// if(StringUtils.equals(roleid, "31aee8cd7db111e88dd352540054a904")) {
		// //大区经理
		// staffMapper.updateBindRelation1(inStaffid, outStaffid, reason, operater);
		// }else if(StringUtils.equals(roleid, "806d7b177db111e88dd352540054a904")) {
		// //运营服务商
		// staffMapper.updateBindRelation1(inStaffid, outStaffid, reason, operater);
		// }else if(StringUtils.equals(roleid, "807725ad7db111e88dd352540054a904")) {
		// //城市经理
		// staffMapper.updateBindRelation1(inStaffid, outStaffid, reason, operater);
		// }else if(StringUtils.equals(roleid, "807c477e7db111e88dd352540054a904")) {
		// //体验店
		// staffMapper.updateBindRelation2(inStaffid, outStaffid, reason, operater);
		// }
	}

	/*
	 * (non-Javadoc) <p>Title: migrateStaffList</p> <p>Description: 迁移员工列表</p>
	 * 
	 * @return
	 * 
	 * @author cjy link: @see
	 * dianfan.service.usermanage.StaffService#migrateStaffList()
	 */
	@Override
	@SystemServiceLog(method = "migrateStaffList", description = "迁移员工列表")
	public List<UserInfo> migrateStaffList(String staffid, String roleid) {
		if (StringUtils.equals(roleid, "31aee8cd7db111e88dd352540054a904")) {
			// 大区经理
			return staffMapper.findMigrateStaffList1(staffid);
		} else if (StringUtils.equals(roleid, "806d7b177db111e88dd352540054a904")) {
			// 运营服务商
			return staffMapper.findMigrateStaffList2(staffid);
		} else if (StringUtils.equals(roleid, "807725ad7db111e88dd352540054a904")) {
			// 城市经理
			return staffMapper.findMigrateStaffList2(staffid);
		} else if (StringUtils.equals(roleid, "807c477e7db111e88dd352540054a904")) {
			// 体验店
			return staffMapper.findMigrateStaffList3(staffid);
		}
		return null;
	}

}
