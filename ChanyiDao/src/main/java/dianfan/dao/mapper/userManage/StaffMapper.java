package dianfan.dao.mapper.userManage;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import dianfan.entities.UserInfo;
import dianfan.entities.base.Regionss;
import dianfan.entities.user.StaffExtra;

/**
 * @ClassName StaffManageMapper
 * @Description 易盟员工dao
 * @author cjy
 * @date 2018年7月25日 上午11:04:23
 */
@Repository
public interface StaffMapper {

	/**
	 * @param param
	 * @Title: findUserCount
	 * @Description: 获取用户列表 数量
	 * @return
	 * @throws:
	 * @time: 2018年7月23日 上午11:45:40
	 */
	int findUserCount(Map<String, Object> param);

	/**
	 * @Title: findUserList
	 * @Description: 获取用户列表
	 * @param param
	 * @return
	 * @throws:
	 * @time: 2018年7月23日 下午12:03:07
	 */
	List<UserInfo> findUserList(Map<String, Object> param);

	/**
	 * @Title: getStaffDetail
	 * @Description: 根据员工id获取用户详情
	 * @param staffid
	 *            员工id
	 * @return
	 * @throws:
	 * @time: 2018年7月26日 上午11:30:44
	 * @author cjy
	 */
	UserInfo getStaffDetail(String staffid);

	/**
	 * @Title: checkPhone
	 * @Description: 手机号码重复性检测
	 * @param telno
	 *            手机号码
	 * @param staffid
	 *            员工id
	 * @return
	 * @throws:
	 * @time: 2018年7月26日 下午3:01:45
	 * @author cjy
	 */
	boolean checkPhone(@Param("telno") String telno, @Param("staffid") String staffid);

	/**
	 * @Title: checkRegionalManager
	 * @Description: 大区经理重复检测
	 * @param regionid
	 *            大区id
	 * @param staffid
	 *            员工id
	 * @return
	 * @throws:
	 * @time: 2018年7月25日 上午11:06:18
	 * @author cjy
	 */
	boolean checkRegionalManager(@Param("regionid") String regionid, @Param("staffid") String staffid);

	/**
	 * @Title: checkCityManager
	 * @Description: 检测对应角色的用户是否存在
	 * @param roleid
	 *            角色id
	 * @param cityCode
	 *            市级code
	 * @param staffid
	 *            用户id
	 * @return
	 * @throws:
	 * @time: 2018年7月25日 下午12:24:37
	 * @author cjy
	 */
	boolean checkCityManager(@Param("roleid") String roleid, @Param("provCode") String provCode,
			@Param("staffid") String staffid);

	/**
	 * @Title: addStaffExtra
	 * @Description: 添加用户补充资料
	 * @param se
	 *            用户补充资料
	 * @throws:
	 * @time: 2018年7月25日 下午4:18:40
	 * @author cjy
	 */
	void addStaffExtra(StaffExtra se);

	/**
	 * @Title: addStaffInfo
	 * @Description: 添加员工信息
	 * @param user
	 *            员工信息
	 * @throws:
	 * @time: 2018年7月25日 下午5:08:17
	 * @author cjy
	 */
	void addStaffInfo(UserInfo user);

	/**
	 * @Title: addStaffRoleRelation
	 * @Description: 添加角色关系
	 * @param staffid
	 *            员工id
	 * @param roleid
	 *            角色id
	 * @throws:
	 * @time: 2018年7月25日 下午5:20:01
	 * @author cjy
	 */
	@Insert("insert into t_user_role (id, userid, roleid,descption) values (replace(uuid(),'-',''), #{staffid}, #{roleid},#{descption})")
	void bindStaffRoleRelation(@Param("staffid") String staffid, @Param("roleid") String roleid,
			@Param("descption") String descption);

	/**
	 * @Title: bindStaffRegionalRelation
	 * @Description: 绑定大区经理区域关系
	 * @param staffid
	 *            员工id
	 * @param regionid
	 *            大区id
	 * @param areaCode
	 *            区域code
	 * @param operater
	 *            添加者id
	 * @throws:
	 * @time: 2018年7月25日 下午5:31:34
	 * @author cjy
	 */
	@Insert("insert into t_user_regions (id, user_id, region_id, create_time, create_by) values "
			+ "(replace(uuid(),'-',''), #{staffid}, #{regionid}, now(), #{operater})")
	void bindStaffRegionalRelation(@Param("staffid") String staffid, @Param("regionid") String regionid,
			@Param("operater") String operater);

	/**
	 * @Title: findRegionalStaff
	 * @Description: 获取此大区下的员工
	 * @param regionid
	 *            大区id
	 * @return
	 * @throws:
	 * @time: 2018年7月26日 下午3:32:44
	 * @author cjy
	 */
	List<String> findRegionalStaff(String regionid);

	/**
	 * @Title: findCityManageByAraeCode
	 * @Description: 获取区域下的城市经理
	 * @param areaCode
	 *            区域code
	 * @return
	 * @throws:
	 * @time: 2018年7月25日 下午5:37:03
	 * @author cjy
	 */
	@Select("select role.userid from t_user_role role, t_user_userinfo users, t_userinfo_extra ext where "
			+ "role.userid=users.id and users.extra_id=ext.id and ext.entkbn=0 and users.entkbn != 9 and ext.area_code=#{areaCode} and role.roleid='807725ad7db111e88dd352540054a904'")
	List<String> findCityManageByAraeCode(String areaCode);

	/**
	 * @Title: changeStaffRole
	 * @Description: 将区域下的城市经理转变为市场开发经理
	 * @param city_staff
	 *            城市经理id列表
	 * @param operater
	 *            操作者id
	 * @throws:
	 * @time: 2018年7月25日 下午5:46:21
	 * @author cjy
	 */
	void changeStaffRole(@Param("staffs") List<String> city_staff, @Param("operater") String operater);

	/**
	 * @Title: getRegionsInfo
	 * @Description: 根据员工id获取大区信息
	 * @param staffid
	 *            员工id
	 * @return
	 * @throws:
	 * @time: 2018年7月26日 下午1:27:36
	 * @author cjy
	 */
	@Select("select reg.id, reg.name from t_user_regions ureg, m_region_name reg "
			+ "where ureg.region_id=reg.id and ureg.user_id=#{staffid}")
	Regionss getRegionsInfo(String staffid);

	/**
	 * @Title: getStaffExtraInfo
	 * @Description: 获取用户补充资料
	 * @param extraid
	 *            补充资料id
	 * @return
	 * @throws:
	 * @time: 2018年7月26日 下午1:32:29
	 * @author cjy
	 */
	StaffExtra getStaffExtraInfo(String extraid);

	/**
	 * @Title: getRegionsManageid
	 * @Description: 获取对应大区经理id
	 * @param areaCode
	 *            市级code
	 * @throws:
	 * @time: 2018年7月26日 下午2:32:10
	 * @author cjy
	 */
	String getRegionsManageid(String areaCode);

	/**
	 * @Title: bindUserLowerUpperRelate
	 * @Description: 绑定上下级关系
	 * @param regionsManageid
	 *            上级员工id
	 * @param staffid
	 *            下级员工id
	 * @param operater
	 *            添加者id
	 * @throws:
	 * @time: 2018年7月26日 下午2:46:31
	 * @author cjy
	 */
	@Insert("insert into t_user_lower_upper_relate (id, upper_user_id, down_user_id, create_time, create_by) values "
			+ "(replace(uuid(),'-',''), #{regionsManageid}, #{staffid}, now(), #{operater})")
	void bindUserLowerUpperRelate(@Param("regionsManageid") String regionsManageid, @Param("staffid") String staffid,
			@Param("operater") String operater);

	/**
	 * @Title: updateStaffRegionalRelation
	 * @Description: 修改上下级关系
	 * @param staffs
	 *            员工id集合
	 * @param regionsManageid
	 *            大区经理id
	 * @param operater
	 *            操作者id
	 * @throws:
	 * @time: 2018年7月26日 下午3:41:38
	 * @author cjy
	 */
	void updateStaffRegionalRelation(@Param("staffs") List<String> staffs,
			@Param("regionsManageid") String regionsManageid, @Param("operater") String operater);

	/**
	 * @Title: getStoreUpperid
	 * @Description: 获取体验店上级（城市经理/运营服务商）
	 * @param cityCode
	 *            区县级code
	 * @throws:
	 * @time: 2018年7月26日 下午5:27:53
	 * @author cjy
	 */
	String getStoreUpperid(String cityCode);

	/**
	 * @Title: findStoreByCityCode
	 * @Description: 获取城市下的体验店用户id
	 * @param areaCode
	 *            市级code
	 * @return
	 * @throws:
	 * @time: 2018年7月26日 下午5:50:52
	 * @author cjy
	 */
	@Select("select apply_user_id from t_experiencestore_apply where "
			+ "city_code in (select area_code from m_area_code where sup_area_code=#{areaCode})")
	List<String> findStoreByCityCode(String areaCode);

	/**
	 * @Title: updateStaffAccoutLocked
	 * @Description: 冻结/解冻 易盟员工
	 * @param staffid
	 *            员工id
	 * @param action
	 *            动作
	 * @param operater
	 *            操作者id
	 * @throws:
	 * @time: 2018年7月27日 上午9:55:11
	 * @author cjy
	 */
	@Update("update t_user_userinfo set locked=#{action}, update_by=#{operater} where id=#{staffid}")
	void updateStaffAccoutLocked(@Param("staffid") String staffid, @Param("action") int action,
			@Param("operater") String operater);

	/**
	 * @Title: getStaffRoleid
	 * @Description: 获取员工角色
	 * @param staffid
	 *            员工id
	 * @throws:
	 * @time: 2018年7月27日 上午10:46:55
	 * @author cjy
	 */
	@Select("select roleid from t_user_role where userid=#{staffid}")
	String getStaffRoleid(String staffid);

	/**
	 * @Title: getMarketDevelopmentManager
	 * @Description: 获取运营服务商下的市场开发经理
	 * @param staffid
	 *            运营服务商id
	 * @return
	 * @throws:
	 * @time: 2018年7月27日 上午11:59:35
	 * @author cjy
	 */
	@Select("select role.userid from t_user_lower_upper_relate relate,t_user_role role,t_user_userinfo users where "
			+ "relate.down_user_id=role.userid and role.userid=users.id and users.entkbn!=9 and relate.upper_user_id = #{staffid} and role.roleid='8072924c7db111e88dd352540054a904'")
	String getMarketDevelopmentManager(String staffid);

	/**
	 * @Title: updateStaffInfo
	 * @Description: 修改员工基础数据
	 * @param user
	 *            员工数据
	 * @throws:
	 * @time: 2018年7月27日 下午2:42:14
	 * @author cjy
	 */
	@Update("update t_user_userinfo set nick_name=#{nickName}, area_code=#{areaCode}, sex=#{sex}, telno=#{telno}, update_by=#{updateBy}, version=version+1 where id=#{id}")
	void updateStaffInfo(UserInfo user);

	/**
	 * @Title: updateStaffRegionalRelation
	 * @Description: 大区经理 更新大区id
	 * @param staffid
	 *            大区经理id
	 * @param regionid
	 *            大区id
	 * @param operater
	 *            操作者id
	 * @throws:
	 * @time: 2018年7月27日 下午2:46:35
	 * @author cjy
	 */
	@Update("update t_user_regions set region_id=#{regionid}, update_by=#{operater}, version = version+1 where user_id=#{staffid}")
	void updateStaffRegionalRelation1(@Param("staffid") String staffid, @Param("regionid") String regionid,
			@Param("operater") String operater);

	/**
	 * @Title: unbindStaffRegionalRelation
	 * @Description: 清除原大区经理下的上下级关系
	 * @param staffid
	 *            大区经理id
	 * @throws:
	 * @time: 2018年7月27日 下午2:49:30
	 * @author cjy
	 */
	@Update("update t_user_lower_upper_relate set upper_user_id=null, update_by=#{operater}, version=version+1 where upper_user_id=#{staffid}")
	void unbindStaffRegionalRelation(@Param("staffid") String staffid, @Param("operater") String operater);

	/**
	 * @Title: getExtraid
	 * @Description: 获取扩展数据id
	 * @param staffid
	 *            员工id
	 * @return
	 * @throws:
	 * @time: 2018年7月27日 下午3:08:39
	 * @author cjy
	 */
	@Select("select extra_id from t_user_userinfo where id=#{staffid}")
	String getExtraid(String staffid);

	/**
	 * @Title: updateExtraInfo
	 * @Description: 更新扩展数据
	 * @param se
	 * @throws:
	 * @time: 2018年7月27日 下午3:15:44
	 * @author cjy
	 */
	@Update("update t_userinfo_extra set real_name=#{realName}, company_name=#{companyName}, area_code=#{areaCode}, handle_idcard=#{handleIdcard}, "
			+ "idcard_no=#{idcardNo}, idcard_font=#{idcardFont}, idcard_back=#{idcardBack}, idcard_valid_date=#{idcardValidDate}, contract_url=#{contractUrl}, "
			+ "update_by=#{createBy}, version=version+1, business_license=#{businessLicense} where id=#{id}")
	void updateExtraInfo(StaffExtra se);

	@Select("select DISTINCT users.id, users.nick_name from t_user_regions reg1,t_user_regions reg2,t_user_userinfo users where "
			+ "reg2.region_id=reg1.region_id and reg2.user_id=users.id and reg1.user_id=#{staffid} and users.entkbn!=9 and users.locked=0")
	List<UserInfo> findMigrateStaffList1(String staffid);

	List<UserInfo> findMigrateStaffList2(String staffid);

	List<UserInfo> findMigrateStaffList3(String staffid);

	/* 迁移 */
	@Update("update t_user_lower_upper_relate set upper_user_id=#{inStaffid}, distroy_relate_resaon=#{reason}, update_by=#{operater}, version=version+1 where upper_user_id=#{outStaffid}")
	void updateBindRelation(@Param("inStaffid") String inStaffid, @Param("outStaffid") String outStaffid,
			@Param("reason") String reason, @Param("operater") String operater);

	/**
	 * @Title: updateUserinfoExtra
	 * @Description:
	 * @param se
	 * @throws:
	 * @time: 2018年7月30日 下午3:43:12
	 */
	@Update("update t_userinfo_extra set contract_url=#{contractUrl},update_by=#{updateBy},version=version+1 where id=#{id} and version=#{version}")
	void updateUserinfoExtra(StaffExtra se);

	/**
	 * @Title: updateUserExtra
	 * @Description:
	 * @param se
	 * @throws:
	 * @time: 2018年8月2日 下午12:08:10
	 */
	void updateUserExtra(StaffExtra se);

}
