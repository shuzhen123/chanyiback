/**  
* @Title: CoopereApplyMapper.java
* @Package dianfan.dao.mapper.our
* @Description: TODO
* @author yl
* @date 2018年6月29日 下午3:10:28
* @version V1.0  
*/ 
package dianfan.dao.mapper.our;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import dianfan.entities.our.CooperationApply;
import dianfan.entities.role.Role;
import dianfan.entities.role.UserRole;
import dianfan.entities.user.LowerUpperRelate;

/** @ClassName CoopereApplyMapper
 * @Description 
 * @author yl
 * @date 2018年6月29日 下午3:10:28
 */
@Repository
public interface CoopereApplyMapper {
	/**
	 * @Title: addCoopereApply
	 * @Description: 申请成为运营服务商
	 * @param cooperationApply
	 * @throws:
	 * @time: 2018年6月30日 下午4:08:22
	 */
	void addCoopereApply(CooperationApply cooperationApply);
	
	/**
	 * @Title: updateCoopereApply
	 * @Description: 
	 * @param cooperationApply
	 * @throws:
	 * @time: 2018年7月11日 下午4:09:41
	 */
	void updateCoopereApply(CooperationApply cooperationApply);
	/**
	 * @Title: updateCoopereApplyStatus
	 * @Description: 
	 * @param cooperationApply
	 * @throws:
	 * @time: 2018年7月23日 下午5:57:28
	 */
	void updateCoopereApplyStatus(CooperationApply cooperationApply);
	
	/**
	 * @Title: getCooperationApply
	 * @Description: 
	 * @param userid 用户id
	 * @return
	 * @throws:
	 * @time: 2018年6月30日 下午4:08:56
	 */
	int getCooperationApply(String userid);
	
	/**
	 * @Title: getCooperationApplyByPhone
	 * @Description: 
	 * @param phonenum 手机号码
	 * @return
	 * @throws:
	 * @time: 2018年6月30日 下午4:09:14
	 */
	int getCooperationApplyByPhone(String phonenum);
	/**
	 * @Title: getUserRole
	 * @Description: 
	 * @param roledistinguish 权限有效区分
	 * @return Role
	 * @throws:
	 * @time: 2018年7月3日 下午6:15:02
	 */
	Role getUserRole(String roledistinguish);
	/**
	 * @Title: updateCooperationApplyRole
	 * @Description: 
	 * @param cam
	 * @throws:
	 * @time: 2018年7月3日 下午7:09:47
	 */
	void updateCooperationApplyRole(UserRole cam);
	/**
	 * @Title: getVersionInfo
	 * @Description: 获取当前版本信息（运营服务商）
	 * @param userid 用户id
	 * @return
	 * @throws:
	 * @time: 2018年7月13日 下午3:12:31
	 */
	@Select("select version from t_cooperation_apply where user_id=#{userid}")
	int getVersionInfo(String userid);
	/**
	 * @Title: getUserVersionInfo
	 * @Description: 获取用户信息表版本号
	 * @param userid 用户id
	 * @return
	 * @throws:
	 * @time: 2018年7月30日 上午10:04:55
	 */
	@Select("select version from t_user_userinfo where id=#{userid}")
	int getUserVersionInfo(String userid);
	/**
	 * @Title: getOperateServicerNum
	 * @Description: 根据条件获取运营服务商人数
	 * @return
	 * @throws:
	 * @time: 2018年7月23日 上午10:49:47
	 */
	int getOperateServicerNum(CooperationApply ca);
	/**
	 * @Title: getOperateServicer
	 * @Description: 获取运营服务商信息
	 * @param operaserverid 运营服务商id
	 * @return
	 * @throws:
	 * @time: 2018年7月28日 下午5:03:56
	 */
	CooperationApply getOperateServicer(String operaserverid);
	/**
	 * @Title: findOperateServiceList
	 * @Description: 根据条件获取服务商列表
	 * @param status 状态
	 * @param applyname 
	 * @param realname
	 * @param idcardno
	 * @param createTimeStart
	 * @param createTimeEnd
	 * @return
	 * @throws:
	 * @time: 2018年7月23日 上午10:51:08
	 */
	List<CooperationApply> findOperateServicerList(CooperationApply ca);
	/**
	 * @Title: checkCityManager
	 * @Description: 检测对应角色的用户是否存在
	 * @param roleid 角色id
	 * @param cityCode 市级code
	 * @param staffid 用户id
	 * @return
	 * @throws:
	 * @time: 2018年7月25日 下午12:24:37
	 * @author cjy
	 */
	boolean checkCityManager(@Param("roleid")String roleid, @Param("provCode")String provCode, @Param("staffid")String staffid);
	/**
	 * @Title: getPriCode
	 * @Description: 获取省code
	 * @param citycode 城市code
	 * @return
	 * @throws:
	 * @time: 2018年7月30日 下午4:06:31
	 */
	@Select("select sup_area_code from m_area_code where area_code=#{citycode}")
	String getPriCode(String citycode);
	/**
	 * @Title: getRegionalManagerId
	 * @Description: 获取大区经理id
	 * @param citycode 城市code
	 * @return
	 * @throws:
	 * @time: 2018年7月28日 下午6:32:49
	 */
	@Select("select tuu.id from t_user_regions tur inner join m_area_code mac on tur.region_id=mac.region_id inner join t_user_userinfo tuu on tuu.id=tur.user_id where mac.area_code=#{citycode} and tuu.entkbn=0 and tuu.locked=0")
	String getRegionalManagerId(String citycode);
	/**
	 * @Title: addLowerUpperRelate
	 * @Description: 添加用户上下级关系
	 * @param lur
	 * @throws:
	 * @time: 2018年7月28日 下午6:51:14
	 */
	@Insert("insert into t_user_lower_upper_relate (id,upper_user_id,down_user_id,create_time,create_by) values (#{id},#{upperUserId},#{downUserId},now(),#{createBy})")
	void addLowerUpperRelate(LowerUpperRelate lur);
	/**
	 * @Title: updataUserExtraId
	 * @Description: 用户表插入资料id
	 * @param param
	 * @throws:
	 * @time: 2018年7月30日 上午10:00:33
	 */
	@Update(" update t_user_userinfo set extra_id = #{extraid},update_by = #{userid},version=version+1 where id = #{id} and version = #{version}")
	void updataUserExtraId(Map<String, Object> param);
	/**
	 * @Title: getExtraId
	 * @Description: 获取资料id
	 * @param userid 用户id
	 * @return
	 * @throws:
	 * @time: 2018年7月30日 下午3:24:29
	 */
	@Select("select extra_id from t_user_userinfo where id=#{userid}")
	String getExtraId(String userid);
	/**
	 * @Title: getExtraVersion
	 * @Description: 获取用户资料version
	 * @param extraid 用户资料id
	 * @return
	 * @throws:
	 * @time: 2018年7月30日 下午3:35:55
	 */
	@Select("select version from t_userinfo_extra where id=#{extraid}")
	int getExtraVersion(String extraid);
	/**
	 * @Title: getExtraCityCode
	 * @Description: 获取城市id
	 * @param extraid
	 * @return
	 * @throws:
	 * @time: 2018年8月2日 下午12:21:47
	 */
	@Select("select area_code from t_userinfo_extra where id=#{extraid}")
	String getExtraCityCode(String extraid);

	/**
	 * @Title: getLowerUpperRelate
	 * @Description: 获取用户上下级关系表(易盟关系)
	 * @param userid 用户id
	 * @return
	 * @throws:
	 * @time: 2018年7月30日 下午4:27:33
	 */
	@Select("select count(*) from t_user_lower_upper_relate where down_user_id=#{userid}")
	int getLowerUpperRelate(String userid);
	/**
	 * @Title: updateUserInfoAreaCode
	 * @Description: 更新用户区域code
	 * @param userid 用户id
	 * @param areacode 区域code
	 * @throws:
	 * @time: 2018年8月27日 下午12:45:56
	 */
	@Update("update t_user_userinfo set area_code=#{areacode} where id=#{userid}")
	void updateUserInfoAreaCode(@Param("userid") String userid,@Param("areacode") String areacode);


}
