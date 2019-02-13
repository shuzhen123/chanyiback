/**  
* @Title: LogisticsMapper.java
* @Package dianfan.dao.mapper.logistics
* @Description: TODO
* @author yl
* @date 2018年7月20日 上午11:32:38
* @version V1.0  
*/ 
package dianfan.dao.mapper.logistics;

import java.util.List;

import org.springframework.stereotype.Repository;

import dianfan.entities.logistics.LogisticsModel;

/** @ClassName LogisticsMapper
 * @Description 
 * @author yl
 * @date 2018年7月20日 上午11:32:38
 */
@Repository
public interface LogisticsMapper {
	
	/**
	 * @Title: getLogisticsNum
	 * @Description: 根据条件统计物流条数
	 * @param lm
	 * @return
	 * @throws:
	 * @time: 2018年7月20日 下午12:35:26
	 */
	Integer getLogisticsNum(LogisticsModel lm);
	/**
	 * @Title: findLogisticsInfo
	 * @Description: 根据条件获取物流列表
	 * @param lm
	 * @return
	 * @throws:
	 * @time: 2018年7月20日 上午11:33:45
	 */
	List<LogisticsModel> findLogisticsList(LogisticsModel lm);
	/**
	 * @Title: addLogistics
	 * @Description: 
	 * @param lm
	 * @throws:
	 * @time: 2018年7月31日 下午12:31:52
	 */
	void addLogistics(LogisticsModel lm);
	/**
	 * @Title: getLogisticsList
	 * @Description: 
	 * @param id 物流id
	 * @return
	 * @throws:
	 * @time: 2018年8月3日 下午3:20:50
	 */
	LogisticsModel getLogisticsInfo(String id);

}
