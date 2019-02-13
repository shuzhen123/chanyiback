/**  
* @Title: SettingSwitchsMapper.java
* @Package dianfan.dao.mapper.our
* @Description: TODO
* @author yl
* @date 2018年7月2日 上午11:10:09
* @version V1.0  
*/ 
package dianfan.dao.mapper.our;

import org.springframework.stereotype.Repository;

/** @ClassName SettingSwitchsMapper
 * @Description 
 * @author yl
 * @date 2018年7月2日 上午11:10:09
 */
@Repository
public interface SettingSwitchsMapper {
	
	/**
	 * @Title: getSettingSwitchs
	 * @Description: 
	 * @return 消费商审批开关
	 * @throws:
	 * @time: 2018年7月2日 上午11:10:45
	 */
	Integer getSettingSwitchs();

}
