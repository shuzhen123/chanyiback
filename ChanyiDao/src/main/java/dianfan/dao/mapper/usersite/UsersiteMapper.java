package dianfan.dao.mapper.usersite;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import dianfan.entities.UserAddress;

/**
 * @ClassName UsersiteMapper
 * @Description 用户地址相关dao
 * @author sz
 * @date 2018年6月30日 下午3:35:33
 */
@Repository
public interface UsersiteMapper {

	
	/**
	 * @Title: findUserSiteList
	 * @Description: 获取用户的收货地址列表
	 * @param userid
	 * 			 用户id
	 * @return UserInfo
	 * @throws:
	 * @time: 2018年6月30日 下午4:23:34
	 */
	List<UserAddress> findUserSiteList(String userid);

	/**
	 * @Title: addUserSite
	 * @Description: 添加用户的收货地址
	 * @param param（userid，name....地址信息）
	 * @throws:
	 * @time: 2018年7月1日 下午12:32:27
	 */
	@Insert("INSERT INTO t_user_address (id,user_id,name,telno,area_code,detail_addr,create_time,create_by) VALUES"
			+ " (#{id},#{userid},#{name},#{telno},#{areaCode},#{detailAddr},now(),#{userid} )")
	void addUserSite(Map<String, Object> param);

	/**
	 * @Title: fildUserSiteByid
	 * @Description: 确认数据库中是否存在该用户的收货地址
	 * @param data 收货地址id
	 * @return int
	 * @throws:
	 * @time: 2018年7月1日 下午1:17:46
	 */
	@Select("SELECT count(*) FROM t_user_address WHERE id = #{siteid} AND user_id = #{userid} AND entkbn = 0"  )
	int fildUserSiteByid(Map<String, Object> data);

	/**
	 * @Title: updateUserSite
	 * @Description: 跟新收货地址信息
	 * @param param
	 * @throws:
	 * @time: 2018年7月1日 下午1:26:19
	 */
	@Update("UPDATE t_user_address SET name = #{name}, telno = #{telno}, area_code = #{areaCode}, detail_addr = #{detailAddr}, update_time = NOW(), update_by = #{userid}  "
			+ "WHERE id = #{siteid} AND user_id = #{userid}")
	void updateUserSite(Map<String, Object> param);

	/**
	 * @Title: checkUserSiteByid
	 * @Description: 通过id确认数据库中是否存在对应的收货地址
	 * @param param 收货地址id
	 * @return int
	 * @throws:
	 * @time: 2018年7月1日 下午1:39:24
	 */
	@Select("SELECT count(*) FROM t_user_address WHERE id = #{siteid} AND user_id = #{userid} AND entkbn = 0")
	int checkUserSiteByid(Map<String, Object> param);

	/**
	 * @Title: delUserSiteByid
	 * @Description: 删除对应id的收货地址
	 * @param param 收货地址id
	 * @throws:
	 * @time: 2018年7月1日 下午1:43:10
	 */
	@Update("UPDATE t_user_address SET entkbn = 9, update_time = NOW(), update_by = #{userid} WHERE id = #{siteid} AND user_id = #{userid} ")
	void delUserSiteByid(Map<String, Object> param);
	
	
	
	
	

}
