package dianfan.dao.mapper.base;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import dianfan.entities.role.UserRole;

/**
 * @ClassName ConsumerRelationMapper
 * @Description 消费关系dao
 * @author cjy
 * @date 2018年7月3日 上午9:48:03
 */
@Repository
public interface ConsumerRelationMapper {

	/**
	 * @Title: getRoleDistinguish
	 * @Description: 根据qr_num获取角色区分
	 * @param qr_num
	 *            用户二维码随机数
	 * @return
	 * @throws:
	 * @time: 2018年7月3日 上午9:57:38
	 */
	String getRoleDistinguish(@Param(value = "qr_num") String qr_num, @Param(value = "userid") String userid);

	/**
	 * @Title: updateBindRelation
	 * @Description: 绑定消费关系
	 * @param userid
	 *            用户id
	 * @param qr_num
	 *            需绑定的二维码数据
	 * @param roleDistinguish
	 *            角色区分
	 * @throws:
	 * @time: 2018年7月3日 上午10:17:51
	 */
	void updateBindRelation(@Param(value = "userid") String userid, @Param(value = "qr_num") String qr_num,
			@Param(value = "roleDistinguish") String roleDistinguish);

	/**
	 * @Title: getUserRole
	 * @Description: 获取用户角色
	 * @param userid
	 *            用户id
	 * @return
	 * @throws:
	 * @time: 2018年7月30日 下午1:06:55
	 * @author cjy
	 */
	@Select("select role.role_distinguish from t_user_role urole,m_role role where urole.roleid=role.id and urole.userid=#{userid}")
	String getUserRole(String userid);

	/**
	 * @Title: getRoleDistinguishByUserid
	 * @Description: 根据qr_num获取用户角色
	 * @param qr_num
	 * @param userid
	 * @return
	 * @throws:
	 * @time: 2018年7月30日 下午1:24:14
	 * @author cjy
	 */
	UserRole getRoleDistinguishByQrnum(String qr_num);

	/**
	 * @Title: bindRelation
	 * @Description: 绑定体验店关系
	 * @param userid
	 * @param userid2
	 * @throws:
	 * @time: 2018年7月30日 下午1:44:03
	 * @author cjy
	 */
	@Insert("insert into t_user_lower_upper_relate (id, upper_user_id, down_user_id, create_time) "
			+ "values (replace(uuid(),'-',''), #{storerid}, #{userid}, now())")
	void bindRelation(@Param(value = "userid") String userid, @Param(value = "storerid") String storerid);

	/**
	 * @Title: updateRole
	 * @Description: 更新角色
	 * @param userid
	 * @throws:
	 * @time: 2018年7月30日 下午1:47:02
	 * @author cjy
	 */
	@Update("update t_user_role set roleid='8081bddd7db111e88dd352540054a904',descption='导购' where userid=#{userid}")
	void updateRole(String userid);

	/**
	 * @Title: checkApplyInfo
	 * @Description: 检测有没有申请过
	 * @param userid
	 * @return
	 * @throws:
	 * @time: 2018年8月18日 下午12:00:17
	 * @author cjy
	 */
	@Select("select status from t_experiencestore_apply where apply_user_id=#{userid} and entkbn=0")
	String checkApplyInfo(String userid);

	/**
	 * @Title: cleanRelationToStore
	 * @Description: 清空旧体验店申请记录
	 * @param userid
	 * @throws:
	 * @time: 2018年8月18日 下午12:34:54
	 * @author cjy
	 */
	@Delete("delete from t_experiencestore_apply where apply_user_id=#{userid}")
	void cleanRelationToStore(String userid);

	/**
	 * @Title: bindRelationToStore
	 * @Description: 普通人->体验店（待审核）
	 * @param applyer
	 * @param areaCode
	 * @param targeter
	 * @throws:
	 * @time: 2018年8月18日 上午11:33:58
	 * @author cjy
	 */
	@Insert("insert into t_experiencestore_apply (id, city_code, status, apply_user_id, create_time, create_by, dev_id, dev_time, dev_ways) values "
			+ "(replace(uuid(),'-',''), #{areaCode}, '03', #{applyer}, now(), #{applyer}, #{targeter}, now(), '02')")
	void bindRelationToStore(@Param("applyer") String applyer, @Param("areaCode") String areaCode, @Param("targeter") String targeter);

	/**
	 * @Title: UpdateRelationToDev
	 * @Description: 更新开发人员
	 * @param applyer
	 * @param targeter
	 * @throws:
	 * @time: 2018年8月18日 下午1:28:38
	 * @author cjy
	 */
	@Update("update t_experiencestore_apply set city_code=#{areaCode}, create_time=now(), create_by=#{applyer}, dev_id=#{targeter}, dev_time=now(), dev_ways='02' where apply_user_id=#{applyer}")
	void UpdateRelationToDev(@Param("applyer") String applyer, @Param("areaCode") String areaCode, @Param("targeter") String targeter);

	/**
	 * @Title: getUserAreaCodeByUpper
	 * @Description: 据开发经理的上级（运营服务商），获取市级code
	 * @param userid
	 * @return
	 * @throws:
	 * @time: 2018年8月25日 下午1:10:13
	 * @author cjy
	 */
	@Select("select ext.area_code from t_user_userinfo users,t_userinfo_extra ext,t_user_lower_upper_relate ur "+
			"where users.extra_id=ext.id and users.id=ur.upper_user_id and ur.down_user_id=#{userid}")
	String getUserAreaCodeByUpper(String userid);

	/**
	 * @Title: getUserAreaCode
	 * @Description: 获取用户的市级code
	 * @param userid
	 * @return
	 * @throws:
	 * @time: 2018年8月25日 下午1:10:50
	 * @author cjy
	 */
	@Select("select ext.area_code from t_user_userinfo users,t_userinfo_extra ext where users.extra_id=ext.id and users.id=#{userid}")
	String getUserAreaCode(String userid);

}
