/**  
* @Title: ConsumerApplyMapper.java
* @Package dianfan.dao.mapper.our
* @Description: TODO
* @author yl
* @date 2018年6月30日 下午1:29:56
* @version V1.0  
*/ 
package dianfan.dao.mapper.our;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import dianfan.entities.our.ConsumerApplyModel;
import dianfan.entities.role.Role;
import dianfan.entities.role.UserRole;

/** @ClassName ConsumerApplyMapper
 * @Description 
 * @author yl
 * @date 2018年6月30日 下午1:29:56
 */
@Repository
public interface ConsumerApplyMapper {
	
	/**
	 * @Title: addConsumerApply
	 * @Description: 添加消费商
	 * @param ConsumerApplyModel
	 * @throws:
	 * @time: 2018年6月30日 下午1:25:39
	 */
	void addConsumerApply(ConsumerApplyModel cam);
	/**
	 * @Title: updateConsumerApply
	 * @Description: 修改消费商
	 * @param cam
	 * @throws:
	 * @time: 2018年7月11日 下午3:56:26
	 */
	void updateConsumerApply(ConsumerApplyModel cam);
	
	/**
	 * @Title: addConsumerApplyRole
	 * @Description: 
	 * @param cam
	 * @throws:
	 * @time: 2018年7月3日 下午5:49:55
	 */
	void updateConsumerApplyRole(UserRole cam);
	
	/**
	 * @Title: getConsumerApply
	 * @Description: 判断是否已经是消费商
	 * @param userid
	 * @return
	 * @throws:
	 * @time: 2018年6月30日 下午1:26:47
	 */
	int getConsumerApply(String userid);
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
	 * @Title: findConsumerNum
	 * @Description: 统计消费商
	 * @return
	 * @throws:
	 * @time: 2018年7月20日 下午2:18:08
	 */
	int getConsumerNum(ConsumerApplyModel cam);
	/**
	 * @Title: findConsumerList
	 * @Description: 获取消费商列表
	 * @return
	 * @throws:
	 * @time: 2018年7月20日 下午2:18:08
	 */
	List<ConsumerApplyModel> findConsumerList(ConsumerApplyModel cam);
	/**
	 * @Title: getConsumerVersion
	 * @Description: 
	 * @param consumerid
	 * @return
	 * @throws:
	 * @time: 2018年7月23日 下午4:15:45
	 */
	@Select("select version from t_consumer_apply where id=#{consumerid}")
	String getConsumerVersion(String consumerid);

}
