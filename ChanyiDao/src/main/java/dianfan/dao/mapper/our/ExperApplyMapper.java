/**  
* @Title: ExperApplyMapper.java
* @Package dianfan.dao.mapper.our
* @Description: TODO
* @author yl
* @date 2018年6月28日 下午8:14:01
* @version V1.0  
*/ 
package dianfan.dao.mapper.our;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import dianfan.entities.ExperiencestoreApply;
import dianfan.entities.ExperiencestoreApplyClassify;
import dianfan.entities.role.Role;
import dianfan.entities.role.UserRole;
import dianfan.entities.user.LowerUpperRelate;

/** @ClassName ExperApplyMapper
 * @Description 
 * @author yl
 * @date 2018年6月28日 下午8:14:01
 */
public interface ExperApplyMapper {
	
	/**
	 * @Title: addExperApply
	 * @Description: 添加体验店申请 
	 * @param esa
	 * @throws:
	 * @time: 2018年6月28日 下午8:55:35
	 */
	void addExperApply(ExperiencestoreApply esa);
	/**
	 * @Title: updateExperApply
	 * @Description: 修改体验店申请
	 * @param esa
	 * @throws:
	 * @time: 2018年7月11日 下午3:37:08
	 */
	void updateExperApply(ExperiencestoreApply esa);
	/**
	 * @Title: updateExpereStatus
	 * @Description: 体验店申请审批
	 * @param esa
	 * @throws:
	 * @time: 2018年7月11日 下午3:37:08
	 */
	void updateExpereStatus(ExperiencestoreApply esa);
	
	/**
	 * @Title: getEaid 
	 * @Description: 查询体验店id
	 * @param userid 用户id
	 * @return
	 * @throws:
	 * @time: 2018年7月11日 下午3:40:22
	 */
	@Select("select id from t_experiencestore_apply where apply_user_id=#{userid}")
	String getEaid(String userid);
	/**
	 * @Title: getEaclassify
	 * @Description: 获取体验店品类个数
	 * @param eaid
	 * @throws:
	 * @time: 2018年7月30日 上午11:53:30
	 */
	@Select("select count(*) from  t_experiencestore_apply_classify where  experiencestore_apply_id=#{eaid}")
	int getEaclassifyNum(String eaid);
	/**
	 * @Title: delEaclassify
	 * @Description: 删除体验店品类
	 * @param eaid
	 * @throws:
	 * @time: 2018年7月30日 上午11:50:00
	 */
	@Update("update t_experiencestore_apply_classify set entkbn=9 where experiencestore_apply_id=#{eaid}")
	void delEaclassify(String eaid);
	
	/**
	 * @Title: addExperApplyClassify
	 * @Description: 添加体验店品类
	 * @param esc
	 * @throws:
	 * @time: 2018年6月28日 下午8:55:19
	 */
	void addExperApplyClassify(ExperiencestoreApplyClassify esc);
	/**
	 * @Title: getPCityAreaName
	 * @Description: 
	 * @param code
	 * @return
	 * @throws:
	 * @time: 2018年8月24日 下午3:43:59
	 */
	@Select("SELECT CONCAT(shen.area_name,shi.area_name,qu.area_name) addressCode FROM m_area_code shen, m_area_code shi, m_area_code qu " + 
			"WHERE qu.sup_area_code = shi.area_code AND shi.sup_area_code = shen.area_code AND qu.area_code = #{code} ")
	String getPCityAreaName(String code);
	
	/**
	 * @Title: updateExperApplyClassify
	 * @Description: 修改体验店品类
	 * @param esc
	 * @throws:
	 * @time: 2018年7月11日 下午3:41:33
	 */
	void updateExperApplyClassify(ExperiencestoreApplyClassify esc);
	/**
	 * @Title: getExperApply
	 * @Description: 判断是否已经成为体验店
	 * @param applyuserid
	 * @return
	 * @throws:
	 * @time: 2018年6月30日 上午9:48:26
	 */
	int getExperApply(String applyuserid);
	/**
	 * @Title: getExperApplyByPhone
	 * @Description: 验证手机号是否存在
	 * @param phone 手机号码
	 * @return 
	 * @throws:
	 * @time: 2018年6月30日 下午3:42:38
	 */
	int getExperApplyByPhone(String phoneno);
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
	 * @Title: updateExperApplyRole
	 * @Description: 
	 * @param cam
	 * @throws:
	 * @time: 2018年7月3日 下午7:18:41
	 */
	void updateExperApplyRole(UserRole cam);
	/**
	 * @Title: getVersionInfo
	 * @Description: 获取当前版本信息
	 * @param userid 用户id
	 * @return
	 * @throws:
	 * @time: 2018年7月13日 下午3:12:31
	 */
	@Select("select version from t_experiencestore_apply where apply_user_id=#{userid}")
	int getVersionInfo(String userid);
	/**
	 * @Title: getExperApplyNum
	 * @Description: 统计体验店
	 * @return
	 * @throws:
	 * @time: 2018年7月20日 下午2:18:08
	 */
	int getExperApplyNum(ExperiencestoreApply ea);
	/**
	 * @Title: findBgExperApplyList
	 * @Description: 获取体验店列表
	 * @return
	 * @throws:
	 * @time: 2018年7月20日 下午2:18:08
	 */
	List<ExperiencestoreApply> findBgExperApplyList(ExperiencestoreApply ea);
	/**
	 * @Title: getBgExperApply
	 * @Description: 获取体验店信息
	 * @param id 体验店id
	 * @return
	 * @throws:
	 * @time: 2018年7月30日 上午10:25:45
	 */
	ExperiencestoreApply getBgExperApply(String id);
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
	 * @Title: updataUserExtraId
	 * @Description: 用户表插入资料id
	 * @param param
	 * @throws:
	 * @time: 2018年7月30日 上午10:00:33
	 */
	@Update(" update t_user_userinfo set extra_id = #{extraid},update_by = #{userid},version=version+1 where id = #{id} and version = #{version}")
	void updataUserExtraId(Map<String, Object> param);
	/**
	 * @Title: getSupAreaCode
	 * @Description: 获取城市id
	 * @param areacode 区域code
	 * @return
	 * @throws:
	 * @time: 2018年7月30日 下午12:24:59
	 */
	@Select("select sup_area_code from m_area_code where area_code=#{areacode}")
	String getSupAreaCode(String areacode);
	/**
	 * @Title: getSupUserid
	 * @Description: 
	 * @param citycode
	 * @return
	 * @throws:
	 * @time: 2018年7月30日 下午1:19:58
	 */
	@Select("select DISTINCT tulur.upper_user_id from t_user_lower_upper_relate tulur inner join t_experiencestore_apply tea on tea.apply_user_id=tulur.down_user_id inner join t_user_userinfo tuu on tuu.id=tulur.upper_user_id where tea.entkbn=0 and tuu.entkbn !=9 and tulur.entkbn=0 and tuu.area_code=#{citycode} and tuu.locked=0")
	String getSupUserid(String citycode);
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
	 * @Title: getUpperCity
	 * @Description: 获取扫码申请人绑定人的城市code
	 * @param eaid 体验店id
	 * @return
	 * @throws:
	 * @time: 2018年8月17日 下午3:46:32
	 */
	@Select("select area_code from t_experiencestore_apply tea inner join t_user_userinfo tuu on tea.dev_id = tuu.id and tea.id=#{eaid}")
	String getUpperCity(String eaid);
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
