package dianfan.dao.mapper.userManage;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import dianfan.entities.user.AdminPopedoms;
import dianfan.entities.user.SettingSwitchs;

/**
 * @ClassName BasicsMapper
 * @Description 系统数据
 * @author sz
 * @date 2018年7月23日 下午3:18:48
 */
@Repository
public interface BasicsMapper {

	/**
	 * @Title: findRegisteredData
	 * @Description: 获取今日注册人数
	 * @return int
	 * @throws:
	 * @time: 2018年7月23日 下午3:38:51
	 */
	@Select(" select COUNT(*) from t_user_userinfo where to_days(create_time) = to_days(now()) ")
	int findRegisteredData();

	/**
	 * @Title: findBrowseData
	 * @Description: 获取今日浏览量
	 * @return
	 * @throws:
	 * @time: 2018年7月23日 下午3:41:19
	 */
	@Select(" SELECT COUNT(DISTINCT createid) FROM t_syslogs WHERE logtype = 3 AND to_days(createtime) = to_days(now()) AND createid is not null AND createid != \"\" ")
	int findBrowseData();

	/**
	 * @Title: findOrderData
	 * @Description:  获取今日订单量
	 * @return
	 * @throws:
	 * @time: 2018年7月23日 下午3:44:27
	 */
	@Select(" select COUNT(*) from t_order where to_days(create_time) = to_days(now()) ")
	int findOrderData();

	/**
	 * @Title: findTransactionData
	 * @Description: 获取当天交易额 
	 * @return
	 * @throws:
	 * @time: 2018年7月23日 下午3:47:11
	 */
	@Select("select SUM(deposit_fee) from t_trade_ser where to_days(create_time) = to_days(now()) AND order_status = 01")
	Integer findTransactionData();

	/**
	 * @Title: findSettingSwitchs
	 * @Description: 获取系统设置
	 * @return SettingSwitchs
	 * @throws:
	 * @time: 2018年7月24日 上午9:42:17
	 */
	@Select(" SELECT id, consumer_apply_flag consumerApplyFlag, consumer_des consumerDes FROM m_setting_switchs WHERE entkbn = 0  ")
	List<SettingSwitchs> findSettingSwitchs();

	
	/**
	 * @Title: findPopedomList
	 * @Description: 
	 * @return
	 * @throws:
	 * @time: 2018年7月24日 上午10:42:33
	 */
	List<AdminPopedoms> findPopedomList();

	/**
	 * @Title: getAdminPopedomsCount
	 * @Description: 检测新加的权限是否存在
	 * @param param
	 * @return
	 * @throws:
	 * @time: 2018年7月24日 下午3:33:36
	 */
	@Select(" SELECT COUNT(*) FROM t_popedom WHERE popedomname = #{popedomname} AND kind = 01 ")
	int getAdminPopedomsCount(Map<String, Object> param);

	/**
	 * @Title: addAdminPopedoms
	 * @Description: 添加新的权限
	 * @param param
	 * @throws:
	 * @time: 2018年7月24日 下午3:37:38
	 */
	@Insert(" INSERT INTO t_popedom (popedomid, popedomname, popedomfatherid, popedomurl, sort, kind) "
			+ "VALUES (#{id}, #{popedomname}, #{popedomfatherid}, #{popedomurl}, #{sort}, #{kind}) ")
	void addAdminPopedoms(Map<String, Object> param);

	/**
	 * @Title: getCheckPopedoms
	 * @Description: 检测就修改的权限是否存在 
	 * @param param
	 * @return
	 * @throws:
	 * @time: 2018年7月24日 下午3:47:44
	 */
	@Select(" SELECT COUNT(*) FROM t_popedom WHERE popedomname = #{popedomname} AND kind = 01 AND popedomid != #{id} ")
	int getCheckPopedoms(Map<String, Object> param);

	/**
	 * @Title: updataAdminPopedoms
	 * @Description: 更新权限
	 * @param param
	 * @throws:
	 * @time: 2018年7月24日 下午3:50:56
	 */
	@Update(" UPDATE t_popedom SET popedomname = #{popedomname}, popedomfatherid = #{popedomfatherid}, popedomurl = #{popedomurl}, "
			+ "sort = #{sort}, kind = #{kind} WHERE popedomid = #{id} ")
	void updataAdminPopedoms(Map<String, Object> param);

	/**
	 * @Title: delAdminPopedoms
	 * @Description: 删除权限 自用接口
	 * @param param
	 * @throws:
	 * @time: 2018年7月24日 下午4:03:47
	 */
	void delAdminPopedoms(Map<String, Object> param);

	/**
	 * @Title: editSettingSwitchs
	 * @Description: 修改系统设置状态
	 * @param param
	 * @throws:
	 * @time: 2018年7月24日 下午4:53:02
	 */
	@Update(" UPDATE  m_setting_switchs SET consumer_apply_flag = #{flag}, consumer_des = #{des} , update_time = NOW(), update_by = #{userid} WHERE id = #{ids} ")
	void updataeditSettingSwitchs(Map<String, Object> param);

	/**
	 * @Title: getCheckSwitch
	 * @Description: 确认修改的开关是否重名
	 * @param param
	 * @return
	 * @throws:
	 * @time: 2018年7月24日 下午5:07:22
	 */
	@Select(" SELECT COUNT(*) FROM m_setting_switchs WHERE consumer_des = #{des} AND id != #{ids} ")
	int getCheckSwitch(Map<String, Object> param);
	
	
	

}
