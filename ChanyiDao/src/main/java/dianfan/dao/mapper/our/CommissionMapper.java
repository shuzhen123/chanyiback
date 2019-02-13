/**  
* @Title: CommissionMapper.java
* @Package dianfan.dao.mapper.our
* @Description: TODO
* @author yl
* @date 2018年6月30日 下午12:45:33
* @version V1.0  
*/ 
package dianfan.dao.mapper.our;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import dianfan.entities.commision.CommisionModel;
import dianfan.entities.our.MoneyEntryExit;
import dianfan.entities.our.UserBounsDetailModel;

/** @ClassName CommissionMapper
 * @Description 
 * @author yl
 * @date 2018年6月30日 下午12:45:33
 */
@Repository
public interface CommissionMapper {
	
	/**
	 * @Title: findCommission
	 * @Description: 获取佣金
	 * @param userid 用户id
	 * @return 佣金列表
	 * @throws:
	 * @time: 2018年6月30日 下午12:46:15
	 */
	List<UserBounsDetailModel> findCommission(String userid);
	/**
	 * @Title: findUserMoneyEntryExit
	 * @Description: 入账出账列表
	 * @param userid 用户id
	 * @return
	 * @throws:
	 * @time: 2018年7月10日 上午10:30:36
	 */
	List<MoneyEntryExit> findUserMoneyEntryExit(String userid);
	/**
	 * @Title: findSceneList
	 * @Description: 
	 * @return
	 * @throws:
	 * @time: 2018年7月25日 下午3:47:16
	 */
	@Select("SELECT DISTINCT scene FROM m_commision where entkbn=0 order by scene+0")
	List<String> findSceneList();
	/**
	 * @Title: findCommision
	 * @Description: 
	 * @return
	 * @throws:
	 * @time: 2018年7月25日 下午3:34:50
	 */
	@Select("select mc.id,mc.role_id roleId,mc.scene,mc.proportion,mr.role_name roleName from m_commision mc INNER JOIN m_role mr ON mc.role_id=mr.id WHERE mr.entkbn=0 AND mc.entkbn=0 order by mc.scene+0,mc.id+0 ")
	List<CommisionModel> findCommision();
	/**
	 * @Title: updateCommision
	 * @Description: 
	 * @param id
	 * @param proportion
	 * @throws:
	 * @time: 2018年7月25日 下午5:19:00
	 */
	@Select("update m_commision set proportion =#{proportion},update_by=#{updateBy} where id=#{id} ")
	void updateCommision(CommisionModel cm);
	/**
	 * @Title: getVersion
	 * @Description: 
	 * @param id
	 * @return
	 * @throws:
	 * @time: 2018年7月25日 下午5:28:42
	 */
	@Select("select version from m_commision where id=#{id}")
	int getVersion(String id);
}
