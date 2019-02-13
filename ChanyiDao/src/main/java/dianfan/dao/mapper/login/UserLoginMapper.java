package dianfan.dao.mapper.login;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import dianfan.entities.UserInfo;
import dianfan.entities.goods.CouponModels;
import dianfan.entities.user.UserRoleModel;

/**
 * @ClassName UserLoginMapper
 * @Description 用户登陆dao
 * @author sz
 * @date 2018年6月28日 下午4:20:34
 */
@Repository
public interface UserLoginMapper {

	/**
	 * @Title: checkUserByOpenid
	 * @Description: 查询用户是否注册，通过小程序Openid
	 * @param wxsmallopenid
	 * 				小程序Openid
	 * @return UserInfo
	 * @throws:
	 * @time: 2018年6月28日 下午4:22:06
	 */
	UserInfo checkUserByOpenid(String wxsmallopenid);
	
	/**
	 * @Title: checkUserByUnionId
	 * @Description: 查询用户信息
	 * @param datas 
	 * 				datas
	 * @return
	 * @throws:
	 * @time: 2018年6月28日 下午6:29:36
	 */
	UserInfo checkUserByUnionId(Map<String, Object> datas);

	
	/**
	 * @Title: checkUserByuserUnion
	 * @Description: 查询用户信息 用户用户的Union
	 * @return UserInfo
	 * @throws:
	 * @time: 2018年7月10日 下午12:25:24
	 */
	UserInfo checkUserByuserUnion(String userUnion);
	
	
	
	/**
	 * @Title: addUserInfo
	 * @Description: 新增用户信息
	 * @param param (openId,nickName,avatarUrl,unionId)
	 * @throws:
	 * @time: 2018年6月28日 下午6:50:45
	 */
	@Insert("INSERT INTO t_user_userinfo ( id, union_id, wxsmall_openid, nick_name, avatar_url, sex, qr_num, create_time, country,source ) "
			+ "VALUES ( #{id}, #{unionId}, #{openId}, #{nickName},  #{avatarUrl}, #{gender}, #{qrNum}, now() , #{country}, '01' ) ")
	void addUserInfo(Map<String, Object> param);

	/**
	 * @Title: checkOldPassword
	 * @Description: 验证原密码是否正确
	 * @param param 
	 * 			(userid, oldPassword)
	 * @return
	 * @throws:
	 * @time: 2018年6月29日 上午12:32:51
	 */
	@Select("select count(*) from t_user_userinfo where id = #{userid} and pwd = #{oldPassword} and entkbn = 0 ")
	int checkOldPassword(Map<String, Object> param);

	/**
	 * @Title: updateNewPassword
	 * @Description: 修改密码
	 * @param param
	 * 			(telno, newPassword)
	 * @throws:
	 * @time: 2018年6月29日 上午12:40:48
	 */
	@Update("update t_user_userinfo set pwd = #{newPassword}, update_time = now()  where telno =  #{telno} ")
	void updateNewPassword(Map<String, Object> param);

	/**
	 * @Title: getUserBytelno
	 * @Description: 根据telno获取用户的信息数据
	 * @param data 
	 * 			(手机号码，登录密码)
	 * @return
	 * @throws:
	 * @time: 2018年6月29日 下午12:05:47
	 */
	@Select("select id , locked, union_id unionId from t_user_userinfo where telno = #{telno} and pwd = #{pwd} and entkbn = 0")
	UserInfo getUserBytelno(Map<String, Object> data);

	/**
	 * @Title: getUserCountByPhone
	 * @Description: 根据手机号码获取用户是否存在
	 * @param telno
	 * 			手机号码
	 * @return
	 * @throws:
	 * @time: 2018年6月29日 下午4:03:53
	 */
	@Select("select count(*) from t_user_userinfo where telno = #{telno} and entkbn = 0")
	int getUserCountByPhone(String telno);

	/**
	 * @Title: addUserphone
	 * @Description: 添加用户的手机号码
	 * @param param (用户id ，手机号码)
	 * @throws:
	 * @time: 2018年6月29日 下午5:28:35
	 */
	@Update("update t_user_userinfo set telno = #{telno}, pwd = #{pwd}, update_time = now(), update_by = #{userid}  where id = #{userid} ")
	void updateUserphone(Map<String, Object> param);

	/**
	 * @Title: addUserRole
	 * @Description: 创建用户的角色
	 * @param userRole
	 * @throws:
	 * @time: 2018年7月3日 上午9:28:11
	 */
	@Insert("INSERT INTO t_user_role SET id = replace(uuid(),'-','') , userid = #{userid} , roleid = #{roleid} , descption = #{descption} ")
	void addUserRole(Map<String, Object> userRole);

	/**
	 * @Title: getRole
	 * @Description: 获取普通用户的role 
	 * @return UserRole
	 * @throws:
	 * @time: 2018年7月3日 上午9:35:46
	 */
	UserRoleModel getRole();


	/**
	 * @Title: fildFreeCouponList
	 * @Description: 获取注册赠送优惠券列表
	 * @return
	 * @throws:
	 * @time: 2018年7月4日 上午9:55:36
	 */
	List<CouponModels> fildFreeCouponList();

	/**
	 * @Title: addUserCoupon
	 * @Description: 给用户发送注册优惠券
	 * @param coupondata
	 * @throws:
	 * @time: 2018年7月4日 下午3:55:02
	 */
	void addUserCoupon(Map<String, Object> coupondata);

	/**
	 * @Title: addCouponCount
	 * @Description: 添加优惠券的发放数量  （新用户注册优惠券type = 02）
	 * @throws:
	 * @time: 2018年7月9日 下午3:05:33
	 */
	@Update("UPDATE t_coupon SET coupon_num = coupon_num+1, update_time = now() WHERE coupon_type = 02")
	void addCouponCount();
	
	/**
	 * @Title: updateopenId
	 * @Description: 跟新用户的openId
	 * @param map
	 * @throws:
	 * @time: 2018年7月10日 下午12:36:57
	 */
	@Update("UPDATE t_user_userinfo SET wxsmall_openid = #{openId}, update_time = NOW() WHERE union_id = #{userUnion} ")
	void updateopenId(Map<String, Object> map);

	/**
	 * @Title: getUseridUnionId
	 * @Description: 获取用户的UnionId
	 * @param userid userid
	 * @return
	 * @throws:
	 * @time: 2018年8月17日 下午12:01:06
	 */
	@Select( " SELECT union_id FROM t_user_userinfo WHERE id = #{userid} " )
	String getUseridUnionId(String userid);

	/**
	 * @Title: updateUserInfo
	 * @Description: 补齐用户的信息
	 * @param param
	 * @throws:
	 * @time: 2018年8月17日 下午12:29:16
	 */
	@Update( " UPDATE t_user_userinfo SET union_id = #{unionId} , wxsmall_openid = #{openId} , nick_name = #{nickName}, country = #{country}, sex = #{gender} , avatar_url = #{avatarUrl} ,qr_num = #{qrNum} WHERE id = #{id} " )
	void updateUserInfo(Map<String, Object> param);

	/**
	 * @Title: checkUserLocked
	 * @Description: 检测用户账户是否被锁
	 * @param userid
	 * @return
	 * @throws:
	 * @time: 2018年8月27日 下午1:19:17
	 * @author cjy
	 */
	@Select("select count(*) from t_user_userinfo where id=#{userid} and locked=0 and entkbn=0")
	boolean checkUserLocked(String userid);

	/**
	 * @Title: checkAdminLocked
	 * @Description: 检测管理员账户是否被锁
	 * @param userid
	 * @return
	 * @throws:
	 * @time: 2018年8月27日 下午1:51:18
	 * @author cjy
	 */
	@Select("select count(*) from t_admin where id=#{userid} and entkbn=0")
	boolean checkAdminLocked(String userid);




	

	

}
